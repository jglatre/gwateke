package com.github.gwateke.ui.table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.context.MessageSourceAware;
import com.github.gwateke.context.UiContext;
import com.github.gwateke.core.closure.Closure;
import com.github.gwateke.data.query.Order;
import com.github.gwateke.model.table.TableColumn;
import com.github.gwateke.model.table.TableModel;
import com.github.gwateke.model.table.TableModelAware;
import com.github.gwateke.model.table.TableSelectionModel;
import com.github.gwateke.model.table.event.TableModelListener;
import com.github.gwateke.model.table.support.DataTableModel;
import com.github.gwateke.ui.popup.Tooltip;
import com.github.gwateke.ui.table.binding.CheckBoxBinding;
import com.github.gwateke.ui.table.binding.ColumnHeaderOrderAdapter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;


/**
 * 
 * @author juanjo
 */
public class Table extends Composite implements TableModelListener {
	
	private static TableCellRenderer defaultCellRenderer = new DefaultCellRenderer();
	
	private final Logger log = Logger.getLogger( getClass().getName() );
	
	private BasicTable table = new BasicTable();
	private RowSelectionMenu selectionMenu;
	private DataTableModel<?> model;
	private RowDecorator rowDecorator;
	private TableSelectionModel<?> selectionModel;
	private List<Binding<?>> bindings = new ArrayList<Binding<?>>();
	private Map<String, TableCellRenderer> columnRenderers = new HashMap<String, TableCellRenderer>();
	private UiContext uiContext;
	private RowHighlighter rowHighlighter = new RowHighlighter();
	private Closure<String, Object> rowTooltipBuilder;
	private Map<Integer, String> rowTooltipCache = new HashMap<Integer, String>();
	private int oldPageSize = 0;
	
	
	public Table(UiContext uiContext) {
		this.uiContext = uiContext;
		
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.setWidth("100%");
		table.addClickHandler( new ColumnHeaderClickListener() );
		table.getMouseOverRow().addValueChangeListener( new SingleRowHighlighter(table, "table-MouseOverRow") );
		
		initWidget(table);		
	}
	
	
	public DataTableModel<?> getModel() {
		return model;
	}
	
	
	public void setModel(DataTableModel<?> model) {
		if (this.model != null) {
			//TODO	this.model.removeListener(this);
		}		
		
		this.model = model;
		this.model.addListener(this);
	}
	
	
//	public void setCellValueGetter(CellValueGetter cellValueGetter) {
//		this.cellValueGetter = cellValueGetter;
//	}
	
	
	public void setRowDecorator(RowDecorator rowDecorator) {
		this.rowDecorator = rowDecorator;
	}
	
	
	public void setSelectionModel(TableSelectionModel<?> selectionModel) {
		this.selectionModel = selectionModel;
		this.selectionModel.addListener( rowHighlighter  );
	}
	
	
	/**
	 * Asigna un conversor de los datos de una fila a String, para ser visualizado en un tooltip.
	 * La asignación del conversor activa ese tipo de tooltip.
	 */
	public void setRowTooltipBuilder(Closure<String, Object> rowTooltipBuilder) {
		this.rowTooltipBuilder = rowTooltipBuilder;
		if (rowTooltipBuilder != null) {	
			table.getMouseOverRow().addValueChangeListener( new RowTooltipHandler() );
//			RowTooltipHandler tooltipHandler = new RowTooltipHandler();
//			addDomHandler( tooltipHandler, MouseOverEvent.getType() );
//			addDomHandler( tooltipHandler, MouseOutEvent.getType() );
		}
	}
	

	public HTMLTable getHtmlTable() {
		return table;
	}

	
	/**
	 * Asigna el renderizador para todas las celdas de una columna.
	 * 
	 * @param column identificador de columna.
	 * @param renderer objeto de tipo {@link TableCellRenderer}.
	 */
	public void setCellRenderer(String column, TableCellRenderer renderer) {
		if (renderer instanceof TableModelAware) {
			((TableModelAware) renderer).setTableModel(model);
		}
		if (renderer instanceof MessageSourceAware) {
			((MessageSourceAware) renderer).setMessageSource( uiContext.getMessageSource() );
		}
		columnRenderers.put(column, renderer);
	}
	
	
	public void initialize() {
		// preparar columnas
		table.getRowFormatter().setStyleName(0, "table-ColumnHeader");
		
		// crear cabeceras columna
		if (selectionModel != null) {
			selectionMenu = new RowSelectionMenu( uiContext );
			selectionMenu.setSelectionModel(selectionModel);
			selectionMenu.setTableModel(model);
			table.setWidget(0, 0, selectionMenu);
		}
		
		ValueModel<Order> orderModel = model.getOrderModel();
		
		for (int i = 0; i < model.getColumnCount(); i++) {
			TableColumn column = model.getTableColumn(i);
			ColumnHeader header = new ColumnHeader( column, uiContext );			
			if (column.isSortable()) {
				new ColumnHeaderOrderAdapter(header, orderModel);
			}

			table.setWidget(0, i + getColumnOffset(), header);
		}
	}
	
	
	public void onDataChanging(TableModel<?> sender) {
		final int pageSize = model.getPageSize();
		
		if (pageSize != oldPageSize) {
			clearRows(0, oldPageSize);
			oldPageSize = pageSize;
		}
		
		if (selectionModel != null) {
			for (Binding<?> binding : bindings) {
				binding.unbind();
			}
			bindings.clear();
		}
	}

	
	public void onDataChanged(TableModel<?> sender) {
		
		log.finer("data changed, start render");
		
		final int pageSize = model.getPageSize();
		final int max = Math.min( model.getPageRowCount(), pageSize );
		RowFormatter rowFormatter = table.getRowFormatter();
		
		for (int j = 0; j < max; j++) {
			final int row = j + 1;
			rowFormatter.setStyleName(row, "table-Row");
			
			if (selectionModel != null) {
				table.setWidget( row, 0, createRowSelector(model.getPageFirstRow() + j) );
			}
			
			if (rowDecorator != null) {
				Object rowValue = model.getRowValue(j);
				rowDecorator.decorate(rowFormatter, row, rowValue);
			}
			
			for (int i = 0; i < model.getColumnCount(); i++) {
				Object cellValue = model.getCellValue(j, i); //cellValueGetter.get( rowValue, model.getTableColumn(i).getId() );
				getCellRenderer(j, i).render(table, cellValue, row, i + getColumnOffset());
			}
		}
		
		int emptyRows = pageSize - max;
		clearRows(max, emptyRows);
		
		if (selectionModel != null) {
			rowHighlighter.refresh( model.getPageFirstRow(), model.getPageMaxRow() - 1 );
		}
		
		rowTooltipCache.clear();

		log.finer("data changed, end render");
	}
	
	
	public void onPageSizeChanged(TableModel<?> model) {
	}
	

	public void onDataFilteringOrSorting(TableModel<?> sender) {
		if (selectionModel != null) {
			selectionModel.clearSelection();
		}
	}

	
	public TableCellRenderer getCellRenderer(int row, int column) {
		TableColumn tableColumn = model.getTableColumn(column);
		TableCellRenderer renderer = columnRenderers.get( tableColumn.getId() );
		if (renderer == null) {
			renderer = defaultCellRenderer;
		}
		return renderer;
	}
	
	
//	public interface CellValueGetter {
//		Object get(Object rowValue, String columnId);
//	}
	
	
	public interface RowDecorator {
		void decorate(RowFormatter formatter, int row, Object rowValue);
	}
	
	//----------------------------------------------------------------
	
	
	protected Widget createRowSelector(int absoluteRow) {
		CheckBox checkBox = new CheckBox("");
		bindings.add( new CheckBoxBinding(checkBox, selectionModel, absoluteRow) );
		return checkBox;
	}
	

	protected void clearRows(int from, int count) {
		RowFormatter rowFormatter = table.getRowFormatter();
		
		for (int j = from + 1; j < from + count + 1; j++) {
			rowFormatter.setStyleName(j, "table-EmptyRow");
			
			if (selectionModel != null) {
				if (table.isCellPresent(j, 0)) {
					table.clearCell(j, 0);
				}
			}
			
			for (int i = 0; i < model.getColumnCount(); i++) {
				int column = i + getColumnOffset();
				if (table.isCellPresent(j, column)) {
					table.clearCell(j, column);
				}
			}
		}
	}
	
	
	protected String getRowTooltipText(int row) {
		String text = rowTooltipCache.get(row);
		if (text == null) {
			text = rowTooltipBuilder.call( model.getRowValue(row) );
		}
		return text;
	}
	
	
	private int getColumnOffset() {
		return selectionModel != null ? 1 : 0;
	}
	
	
	/**
	 * Oyente del modelo de selección que cambia el estilo de las filas para indicar
	 * si están seleccionadas o no.
	 */
	private class RowHighlighter implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			refresh( event.getFirstIndex(), event.getLastIndex() );
		}
		
		public void refresh(int first, int last) {
			for (int row = first; row <= last; row++) {
				if (model.getPageFirstRow() <= row && row < model.getPageMaxRow()) {
					final int relative = row - model.getPageFirstRow() + 1;
					if (selectionModel.isSelected(row)) {
						table.getRowFormatter().addStyleName(relative, "table-SelectedRow");
					}
					else {
						table.getRowFormatter().removeStyleName(relative, "table-SelectedRow");
					}
				}
			}
		}
	}
	

	private class ColumnHeaderClickListener implements ClickHandler {		
		public void onClick(ClickEvent event) {
			Cell cell = table.getCellForEvent(event);
			int row = cell.getRowIndex();
			int column = cell.getCellIndex();
			
			if (row == 0 && column >= getColumnOffset()) {
				ColumnHeader header = (ColumnHeader) table.getWidget(row, column);
				header.toggle();
			}
		}
	}
	
	
	private class RowTooltipHandler implements PropertyChangeListener {
		private Tooltip tooltip;

		public void propertyChange(PropertyChangeEvent event) {
			if (tooltip != null) {
				tooltip.hide();
			}
			
			int row = (Integer) event.getNewValue(); 
			if (row > 0) {
				Element rowElement = table.getRowElement(row);
				int left = rowElement.getAbsoluteLeft();
				int top = rowElement.getAbsoluteTop() + rowElement.getClientHeight();
				tooltip = new Tooltip( left, top, getRowTooltipText(row - 1) );
				tooltip.show();
			}
		}
	}

}