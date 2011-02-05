package com.github.gwateke.model.support;

import static com.github.gwateke.model.event.MessageEvent.MessageType.AUTO;
import static com.github.gwateke.model.event.MessageEvent.MessageType.CLOSE;
import static com.github.gwateke.model.event.MessageEvent.MessageType.MODAL;
import static com.github.gwateke.model.event.MessageEvent.MessageType.OPEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwateke.binding.form.CommitListener;
import com.github.gwateke.binding.form.ConfigurableFormModel;
import com.github.gwateke.binding.form.FormModel;
import com.github.gwateke.binding.form.support.DefaultFormModel;
import com.github.gwateke.binding.validation.DefaultValidationResultsModel;
import com.github.gwateke.binding.validation.ValidationResults;
import com.github.gwateke.binding.validation.ValidationResultsModel;
import com.github.gwateke.binding.validation.ValidationUtils;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.ValueHolder;
import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.context.support.DefaultMessageSourceResolvable;
import com.github.gwateke.core.closure.Closure;
import com.github.gwateke.data.DataError;
import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.PropertyAccessor;
import com.github.gwateke.data.metadata.EntityMetadata;
import com.github.gwateke.data.metadata.FieldMetadata;
import com.github.gwateke.data.query.Comparison;
import com.github.gwateke.data.query.Conjunction;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.data.query.Order;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.model.Status;
import com.github.gwateke.model.event.MessageEvent;
import com.github.gwateke.model.list.ListModel;
import com.github.gwateke.model.list.ListModelResolver;
import com.github.gwateke.model.list.support.DataListModel;
import com.github.gwateke.model.table.TableSelectionModel;
import com.github.gwateke.model.table.support.DataTableModel;
import com.github.gwateke.model.table.support.RowIdsSelectionHolder;
import com.google.gwt.user.client.Command;


/**
 * 
 * 
 * @param <T> managed domain object type.
 * @param <K> key type.
 * 
 * @author juanjo
 */
public class DataManagementModel<T, K> extends AbstractDataManagementModel<T, K> 
		implements ListModelResolver {
	
	private final EntityMetadata metadata;
	private Properties tableProperties;
	private Properties formProperties;
	private Map<String, Order> tableOrders;
	private Criterion baseFilter;
	private ValueModel<Status> status = new ValueHolder<Status>(Status.INITIALIZING);
	private DefaultValidationResultsModel validationResults = new DefaultValidationResultsModel();
	private ConfigurableFormModel<T> formModel; 	
	private Map<String, ListModel<?,?>> listModels = new HashMap<String, ListModel<?,?>>();
	private Map<String, DataManagementModel<?,K>> detailModels = new HashMap<String, DataManagementModel<?,K>>();
	private String foreignKey;

	
	public DataManagementModel(DataSource<T, K> dataSource, EntityMetadata metadata) {
		super(dataSource);
		this.metadata = metadata;
		
		enableActions( metadata.getAllowedActions() );
	}

	
	/**
	 * Asigna las propiedades que se usarán en la consulta que rellena la tabla.
	 */
	public void setTableProperties(Properties tableProperties) {
		this.tableProperties = tableProperties;
		postProcessTableProperties();
	}
	
	
	public void setTableOrders(Map<String, Order> tableOrders) {
		this.tableOrders = tableOrders;
	}
	
	
	/**
	 * Asigna las propiedades que se usarán en la consulta que rellena el formulario. 
	 */
	public void setFormProperties(Properties formProperties) {
		this.formProperties = formProperties;
	}
	
	
	/**
	 * Asigna un filtro que no puede ser deshecho por el usuario.
	 */
	public void setBaseFilter(Criterion baseFilter) {
		this.baseFilter = baseFilter;

		getTableModel().setBaseFilter(baseFilter);  // TODO peligro efectos secundarios
//		if (tableModel != null) {
//			tableModel.setBaseFilter(baseFilter);
//		}
	}
	
	
	/**
	 * Asigna el campo que hace de clave foránea cuando este modelo está subordinado a 
	 * otro modelo de cabecera. 
	 */
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
		this.formProperties.add(foreignKey, "ObjectId");
	}
	
	
	public ValueModel<Status> getStatus() {
		return status;
	}
	
	
	/**
	 * Inicia el estado de consulta.
	 */
	public void startBrowse() {
		status.setValue(Status.BROWSE);
		getTableModel().refresh();
		refreshListModels();
	}
	
	
	@Override
	public void addNew() {
		startEdit( getDataSource().getNullId() );
	}
	
	
	/**
	 * Iniciar la edición de una entidad, dado su idenficador.
	 */
	@Override
	public void startEdit(K id) {
		getDataSource().load(id, formProperties, new DataSource.Callback<T>() {
			public void onSuccess(T result, List<DataError> errors) {
				getFormModel().setFormObject( createFormBuffer(result) );				
				status.setValue(Status.EDIT);		
			}			
		});
		refreshListModels();
	}
	
	
	/**
	 * Acepta la edición en curso.
	 */
	@Override
	public void acceptEdit() {
		getFormModel().commit();
	}

	
	/**
	 * Cancela la edición en curso. Si la acción queda pendiente de confirmación este método no hace nada.
	 */
	@Override
	public void cancelEdit() {
		final Command cancel = new CancelCommand();
		
		if (getFormModel().isDirty()) {
			executeWithConfirmation(cancel, "cancel.confirm");
		}
		else {
			cancel.execute();
		}
	}
	
	
	/**
	 * Cancela la edición y ejecuta una acción diferida si el usuario accepta la confirmación
	 * de perder los cambios.
	 * 
	 * @return si la cancelación queda pendiente de confirmación o no.
	 */
	public boolean cancelEdit(final Command postCancel) {
		final Command cancel = new CancelCommand();
		
		if (getFormModel().isDirty()) {
			Command acceptCommand = new Command() {
				public void execute() {
					cancel.execute();
					postCancel.execute();
				}				
			};
			executeWithConfirmation(acceptCommand, "cancel.confirm");
			return false;
		}
		else {
			cancel.execute();
			return true;
		}
	}
	
	
	/**
	 * Borra las entidades seleccionadas. Si no hay selección no hace nada.
	 */
	@Override
	public void delete() {
		final int selectedCount = getSelectionModel().getSelectedRowCount();
		
		if (selectedCount > 0) {
			K[] ids = (K[]) getSelectionModel().getSelectedIds().toArray( new Object[selectedCount] );
			
			// lanzar evento confirmación
			executeWithConfirmation( new DeleteCommand(ids), "delete.confirm" );
		}
	}
	
	
	/**
	 * Cancela la selección en curso y fuerza la ejecución de la última consulta.
	 */
//	public void refresh() {
//		getSelectionModel().clearSelection();
//		getTableModel().refresh();
//	}
	
	
	/**
	 * Invoca un método sobre las entidades seleccionadas. Si no hay selección no hace nada. 
	 */
	public void invoke(String method, Object... args) {
		final int selectedCount = getSelectionModel().getSelectedRowCount();
		
		if (selectedCount > 0) {
			K[] ids = (K[]) getSelectionModel().getSelectedIds().toArray( new Object[selectedCount] );
			executeWithConfirmation( new InvokeMethodCommand(method, args, ids), method + ".confirm" );
		}
	}
	
	
	public void startDetailEdit(K masterId, String detailName, Status status) {
		if (detailModels.containsKey(detailName)) {
			DataManagementModel<?, K> model = detailModels.get(detailName);
			model.setBaseFilter( model.createDetailFilter(masterId) );
			model.getTableModel().setPageNum(0);   // bug #0000363
//			model.refresh();
			
			this.status.setValue(status);
		}
	}
	
	
	// TODO mover más abajo
	protected Criterion createDetailFilter(K masterId) {
		return new Comparison.Equals<K>(foreignKey + ".id", masterId);
	}
	
	
	public void loadForm(K id) {		
		getDataSource().load(id, formProperties, new DataSource.Callback<T>() {
			public void onSuccess(T result, List<DataError> errors) {
				getFormModel().setFormObject( createFormBuffer(result) );
			}			
		});
	}
	
	
	public FormModel<T> getFormModel() {
		if (formModel == null) {
			formModel = new DefaultFormModel<T>(null, metadata);
			formModel.addCommitListener( createCommitListener() );

			for (String name : formProperties.getDeepNames()) {
				// si es modelo de detalle omitimos clave foránea
				if (!name.equals(foreignKey)) {
					formModel.add(name);			
				}
			}
		}
		return formModel;
	}
	
	
	protected DataTableModel<T> createDataTableModel() {
		DataTableModel<T> tableModel = new DataTableModel<T>();
		tableModel.setDataSource( getDataSource() );
		tableModel.setEntityMetadata(metadata);
		tableModel.setOrders(tableOrders);
		tableModel.setProperties(tableProperties);
		tableModel.setBaseFilter(baseFilter);
		return tableModel;
	}
	
	
	public ListModel<?, ?> getListModel(String field) {
		ListModel<?, ?> listModel = listModels.get(field);
		
		if (listModel == null) {
			// crear ListModels basados en metadatos automáticamente bajo demanda
			FieldMetadata fieldMetadata = metadata.getFieldMetadata(field);
			if (fieldMetadata.isAssociation() && (fieldMetadata.isManyToOne() || fieldMetadata.isOneToOne())) {
				listModel = getDataSource().createListModel( fieldMetadata.getReferencedType() );
				listModels.put(field, listModel);
			}
//			if (fieldMetadata.getInList() != null) {
//				listModel = new DefaultListModel<Object, Object>( fieldMetadata.getInList() );
//				addListModel(field, listModel);
//			}
		}
		return listModel;
	}
	
	
	public DataManagementModel<?, K> getDetailModel(String field) {
		return detailModels.get(field);
	}
	
	
	public List<DataManagementModel<?, K>> getDetailModels() {
		return new ArrayList<DataManagementModel<?, K>>( detailModels.values() );
	}
	
	
	protected TableSelectionModel<K> createSelectionModel() {
		return new RowIdsSelectionHolder<K>( new Closure<K, Integer>() {
			public K call(Integer absoluteRow) {
				final DataTableModel<T> model = getTableModel();
				final int relativeRow = absoluteRow - model.getPageFirstRow();
				PropertyAccessor<T, K> accessor = getDataSource().getPropertyAccessor( model.getRowValue(relativeRow) );
				return accessor.getId();
//				try {
//					return (K) model.getRowValue(relativeRow).get("id");
//				} 
//				catch (Exception e) {
//					return null;
//				}
			}				
		} ); 
	}
	
	
	public ValidationResultsModel getValidationResultsModel() {
		return validationResults;
	}

	
	public void addListModel(String field, ListModel<?,?> listModel) {
		listModels.put(field, listModel);
	}
	
	
	public void addDetailModel(String field, String foreignKey, DataManagementModel<?, K> detailModel) {
		detailModel.setForeignKey(foreignKey);
		detailModels.put(field, detailModel);
	}
	
	//-----------------------------------------------------------------	
	
	@Override 
	protected Criterion createDefaultSearchCriterion(Object value) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		for (FieldMetadata field : metadata) {
			if (field.isPersistent() && field.getType().equals("java.lang.String")) {
				criteria.add( new Comparison.Like(field.getName(), String.valueOf(value)) );
			}
		}
		
		return criteria.size() == 1 ? criteria.get(0) : new Conjunction.Or(criteria.toArray(new Criterion[0]));
	}
	
	
	protected void refreshListModels() {
		for (ListModel<?, ?> model : listModels.values()) {
			if (model instanceof DataListModel) {
				((DataListModel<?>) model).refresh();
			}
		}
	}
	
	
	/**
	 * Ajustar propiedades de la tabla según metadatos recibidos del servidor.
	 */
	protected void postProcessTableProperties() {
		if (tableProperties == null) {
			throw new IllegalStateException("No se han asignado las propiedades para la tabla");
		}

		for (String name : tableProperties.getNames()) {
			Boolean display = (Boolean) metadata.getUserMetadata(name, "display");
			if (! metadata.isReadable(name) || display == null || display == false) {
				tableProperties.remove(name);
			}
		}
	}
	
	
	/**
	 * Finalizar la edición en curso.
	 */
	protected void finishEdit() {
		validationResults.clearAllValidationResults();
		status.setValue(Status.BROWSE);
	}
	
	
	protected CommitListener<T> createCommitListener() {
		return new FormModelCommitListener();
	}
	
	
	protected Object getMasterId() {
		return baseFilter != null ? ((Comparison<?>) baseFilter).getValue() : getDataSource().getNullId();
	}
	
	
	/**
	 * Construye un Map con las entradas ordenadas en el mismo orden que los campos
	 * del formulario, para que cuando se haga el commit el servidor los reciba en 
	 * ese orden.
	 */
	private T createFormBuffer(T source) {
		return source; //TODO clone source
		
/*		Map<String, Object> buffer = new LinkedHashMap<String, Object>();	
		
		// si es un modelo de detalle nos aseguramos de que el formulario contenga el id
		// del master. Manipulamos directamente el buffer para evitar ensuciar el formulario.
		if (foreignKey != null && baseFilter != null) {
			buffer.put(foreignKey, getMasterId());
		}				

		for (String name : formProperties.getNames()) {
			if (! buffer.containsKey(name)) {
				buffer.put(name, source.get(name));
			}
		}
		
		return buffer;
		*/
	}
	
	
	private class FormModelCommitListener implements CommitListener<T> {

		public void preCommit(FormModel<T> formModel) {
			log.finer( "pre : " + String.valueOf(formModel.getFormObject()) );		
		}
		
		public void postCommit(FormModel<T> formModel) {
			T value = formModel.getFormObject();
			
			// TODO no enviar campos de sólo lectura (servidor no da metainformación de "read only")
/*			for (String field : value.keySet()) {
				if (formModel.getFieldMetadata(field).isReadOnly()) {
					value.remove(field);
					log.debug(field + " removed");
				}
			}*/
			
			log.finer( "post : " + String.valueOf(value) );

			fireEvent( new MessageEvent("saving", OPEN) );

			getDataSource().save(value, new DataSource.Callback<Object>() {
				public void onSuccess(Object result, List<DataError> errors) {
					fireEvent( new MessageEvent(CLOSE) );

					if (errors != null && !errors.isEmpty()) {
						ValidationResults newResults = ValidationUtils.createValidationMessages(errors);
						validationResults.updateValidationResults(newResults);
					}
					else {
						fireEvent( new MessageEvent("save.info", AUTO) );
						refresh();
						finishEdit();
					}
				}
				
//				public void onFailure(List<E> errors) {
//					fireEvent( new MessageEvent(CLOSE) );
////					validationResults.clearAllValidationResults();
//					ValidationResults newResults = ValidationUtils.createValidationMessages(errors);
//					validationResults.updateValidationResults(newResults);
//				}
			});
		}
	}

	
	private class DeleteCommand implements Command {		
		private K[] ids;
		private MessageSourceResolvable message;
		
		public DeleteCommand(K[] ids) {
			this.ids = ids;
			this.message = new DefaultMessageSourceResolvable( "delete.info", ids.length );
		}
		
		public void execute() {
			getDataSource().delete(ids, new DataSource.Callback<Object>() {
				public void onSuccess(Object result, List<DataError> errors) {
					if (errors != null && !errors.isEmpty()) {
						final String message = ValidationUtils.toString( errors );
						fireEvent( new MessageEvent(message, MODAL) );
					}
					else {
						fireEvent( new MessageEvent(message, AUTO) );
					}
					refresh();
				}

//				public void onFailure(List<E> errors) {
//					final String message = ValidationUtils.toString( errors );
//					fireEvent( new MessageEvent(message, MODAL) );
//					refresh();
//				}
			});
		}				
	}

	
	private class CancelCommand implements Command {
		public void execute() {
			getFormModel().revert();
			finishEdit();
		}	
	}
	
	
	private class InvokeMethodCommand implements Command {		
		private String method;
		private List<Object> args;
		private K[] ids;
		private MessageSourceResolvable message;

		public InvokeMethodCommand(String method, Object[] args, K[] ids2) {
			this.method = method;
			this.args = new ArrayList<Object>( Arrays.asList(args) );
			this.ids = ids2;
			this.message = new DefaultMessageSourceResolvable( method + ".info", ids2.length );
		}
		
		public void execute() {
			fireEvent( new MessageEvent("processing", OPEN) );
			
			getDataSource().invoke(method, ids, args, new DataSource.Callback<Object>() {
				public void onSuccess(Object result, List<DataError> errors) {
					if (errors != null && !errors.isEmpty()) {
						validationResults.clearAllValidationResults();
						ValidationResults newResults = ValidationUtils.createValidationMessages( errors );
						validationResults.updateValidationResults(newResults);
						fireEvent( new MessageEvent(CLOSE) );
					}
					else {
						fireEvent( new MessageEvent(CLOSE) );
						fireEvent( new MessageEvent(message, AUTO) );
						refresh();
					}					
				}
			});
		}
	}

}
