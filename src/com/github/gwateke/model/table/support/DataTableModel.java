package com.github.gwateke.model.table.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.ValueHolder;
import com.github.gwateke.data.DataError;
import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.metadata.EntityMetadata;
import com.github.gwateke.data.metadata.FieldMetadata;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.data.query.Order;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.model.table.TableColumn;
import com.github.gwateke.model.table.TableModel;
import com.github.gwateke.model.table.event.TableModelListener;


public class DataTableModel<T> implements TableModel<T> {

	private final Logger log = Logger.getLogger( getClass().getName() );

	public static final int DEFAULT_PAGE_SIZE = 20;
		
	private DataSource<T, ?> dataSource;
	private EntityMetadata metadata;
	private List<TableColumn> columns = new ArrayList<TableColumn>();
	private Map<String, Order> orders;
	private Properties propertiesWithId;
	private ValueModel<Criterion> filterModel;
	private ValueModel<Order> orderModel;
	
    private List<T> items;
    private int itemCount = -1;
    private Criterion baseFilter;
	private int pageNum = 0;         
	private int pageSize = DEFAULT_PAGE_SIZE;
    
    private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
    
	private PropertyChangeListener filterAndOrderListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getSource() == filterModel) {
				log.finer("filter changed: " + evt.getNewValue());
				pageNum = 0;   // bug #0000328
			}
			else if (evt.getSource() == orderModel) {
				log.finer("order changed: " + evt.getNewValue());
			}
			
			fireDataFilteringOrSorting();
			refresh();
		}
	};

	
	public DataTableModel() {
		orderModel = new ValueHolder<Order>();
		orderModel.addValueChangeListener(filterAndOrderListener);

		filterModel = new ValueHolder<Criterion>();
		filterModel.addValueChangeListener(filterAndOrderListener);
	}
	

	public void setDataSource(DataSource<T, ?> dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public void setEntityMetadata(EntityMetadata metadata) {
		this.metadata = metadata;
	}
	
	
	public void setOrders(Map<String,Order> orders) {
		this.orders = orders;
	}
	
    
    public void setProperties(Properties properties) {
    	for (int i = 0; i < properties.size(); i++) {
    		String name = properties.getName(i);
			String type = properties.getType(i);
			String orderBy = orders != null && orders.containsKey(name) ? orders.get(name).getField() : name;
			FieldMetadata field = metadata.getFieldMetadata(name);
			boolean sortable = field.isPersistent() && !field.isAssociation() || orders != null && orders.containsKey(name);
			
			columns.add( new TableColumn(name, type, orderBy, sortable) );
    	}    	
    	
    	propertiesWithId = new Properties();
    	propertiesWithId.add("id");  //TODO use FieldMetadata.isIdentity()
    	propertiesWithId.addAll(properties);
    }
    
    
    public void addColumn(TableColumn column) {
    	columns.add(column);
    }
    
    
    public void setBaseFilter(Criterion baseFilter) {
    	this.baseFilter = baseFilter;
    }
    
    
    public Object getCellValue(int row, int column) {
    	String columnId = getTableColumn(column).getId();
    	return dataSource.getPropertyAccessor( getRowValue(row) ).get( columnId );
    }
    

	public int getRowCount() {
		return itemCount;
	}

	
	public int getColumnCount() {
		return columns.size();
	}
	
	
	public FieldMetadata getColumnMetadata(int column) {
		return metadata.getFieldMetadata( columns.get(column).getId() );
	}

	
	public TableColumn getTableColumn(int column) {
		return columns.get(column);
	}
	
	
	public T getRowValue(int row) {
		return items.get(row);
	}
	
	
	public ValueModel<Order> getOrderModel() {
		return orderModel;
	}
	
	
	public ValueModel<Criterion> getFilterModel() {
		return filterModel;
	}
	

	public void addListener(TableModelListener listener) {
		listeners.add(listener);
	}
	
	
	public void removeListener(TableModelListener listener) {
		listeners.remove(listener);
	}
		
	//----------------------------------------------------------------------------------------------

	public int getPageRowCount() {
		return getPageMaxRow() - getPageFirstRow();
	}
	
	
	public int getPageCount() {
		int maxItem = getRowCount() - 1;                                                                        
		return maxItem / pageSize + 1;        
	}

	
	public int getPageNum() {
		return pageNum;
	}
	

	public void setPageNum(int pageNum) {
		this.pageNum = Math.max( 0, Math.min(pageNum, getPageCount() - 1) ); 
		refresh();		
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {                                                                                                           
		if (this.pageSize != pageSize) {                                                                                                        
			int currentItem = getPageFirstRow();
			this.pageSize = pageSize;                                                                             
			setPageNum(currentItem / pageSize);  
			firePageSizeChanged();
		}	
	}	
	
	
	/**
	 * @return absolute position of the first item in current page.                                                                                                        
	 */                                                                                                        
	public int getPageFirstRow() {                                                                                                          
		return Math.max(0, pageNum * pageSize);                                                            
	}                                                                                                          


	/**                                                                                                        
	 * Devuelve la posición absoluta del siguiente item al último de                                           
	 * la página en curso.                                                                                     
	 */                                                                                                        
	public int getPageMaxRow() {                                                                                                          
		return Math.min(getPageFirstRow() + pageSize, getRowCount());                                               
	}

	//----------------------------------------------------------------------------------------------
	
	public void refresh() {
		fireDataChanging();
		
		Query query = new Query();
		
		if (baseFilter != null) {
			query.setWhere(baseFilter);
		}
		if (filterModel.getValue() != null) {
			query.addCriterion( filterModel.getValue() );
		}
		if (orderModel.getValue() != null) {
			query.setOrderBy( orderModel.getValue() );
		}
		query.setOffset( getPageFirstRow() );
		query.setLimit( pageSize );

		// se invalida la lista de items y se fuerza la recarga
		items = null;
		itemCount = -1;
		executeCount(query);
		executeQuery(propertiesWithId, query);
	}
	
	
	protected void executeQuery(Properties properties, Query query) {
		dataSource.list(query, properties, new DataSource.Callback<List<T>>() {
			public void onSuccess(List<T> result, List<DataError> errors) {
				items = result;                                                                                    
				fireEvents();                                                                                        
			}			
		});
	}
	
	
	protected void executeCount(Query query) {
		dataSource.count(query, new DataSource.Callback<Integer>() {
			public void onSuccess(Integer result, List<DataError> errors) {
				itemCount = result;
				fireEvents();
			}			
		});
	}
	
	
	protected void fireEvents() {                              
		// no se lanzan eventos hasta que no hayan acabado los 2 callbacks
		if (items != null && itemCount > -1) {
			fireDataChanged();
		}                                                                                                                 
	}


	protected final void fireDataChanging() {
		for (TableModelListener listener : listeners) {
			listener.onDataChanging(this);
		}
	}                                                                                                                         


	protected final void fireDataChanged() {
		for (TableModelListener listener : listeners) {
			listener.onDataChanged(this);
		}
	}                                                                                                                         

	
	protected final void firePageSizeChanged() {
		for (TableModelListener listener : listeners) {
			listener.onPageSizeChanged(this);
		}		
	}
	
	
	protected final void fireDataFilteringOrSorting() {
		for (TableModelListener listener : listeners) {
			listener.onDataFilteringOrSorting(this);
		}		
	}
	
}
