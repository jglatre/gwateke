package com.github.gwateke.model.support;

import java.util.Arrays;
import java.util.logging.Logger;

import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.command.ActionCommandInterceptor;
import com.github.gwateke.command.CommandRegistry;
import com.github.gwateke.command.support.DefaultCommandRegistry;
import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.model.ManagementModel;
import com.github.gwateke.model.event.ActionEvent;
import com.github.gwateke.model.event.ActionHandler;
import com.github.gwateke.model.event.ConfirmationEvent;
import com.github.gwateke.model.event.ConfirmationHandler;
import com.github.gwateke.model.event.ErrorEvent;
import com.github.gwateke.model.event.ErrorHandler;
import com.github.gwateke.model.event.MessageEvent;
import com.github.gwateke.model.event.MessageHandler;
import com.github.gwateke.model.event.MessageEvent.MessageType;
import com.github.gwateke.model.table.TableSelectionModel;
import com.github.gwateke.model.table.support.DataTableModel;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;


/**
 * @param <T> item type.
 * @param <I> id type.
 * 
 * @author juanjo
*/
public abstract class AbstractDataManagementModel<T, I> implements ManagementModel {
	
	protected final Logger log = Logger.getLogger( getClass().getName() );

	private DataSource<T, I> dataSource;
	private HandlerManager handlerManager;
	private CommandRegistry commandRegistry;
	private DataTableModel<T> tableModel;
	private TableSelectionModel<I> selectionModel;

	
	public AbstractDataManagementModel() {
	}
	
	
	public AbstractDataManagementModel(DataSource<T, I> dataSource) {
		setDataSource(dataSource);
	}
	
	
	public void setDataSource(DataSource<T, I> dataSource) {
		this.dataSource = dataSource;
	}

	
	public HandlerRegistration addErrorHandler(ErrorHandler handler) {
		return getHandlerManager().addHandler(ErrorEvent.getType(), handler);
	}
	
	
	public HandlerRegistration addActionHandler(ActionHandler handler) {
		return getHandlerManager().addHandler(ActionEvent.getType(), handler);
	}
	
	
	public HandlerRegistration addMessageHandler(MessageHandler handler) {
		return getHandlerManager().addHandler(MessageEvent.getType(), handler);
	}

	
	public HandlerRegistration addConfirmationHandler(ConfirmationHandler handler) {
		return getHandlerManager().addHandler(ConfirmationEvent.getType(), handler);
	}
	
	
//	public HandlerRegistration addApplicationEventHandler(Type<ApplicationEventHandler> type, ApplicationEventHandler handler) {
//		return getHandlerManager().addHandler(type, handler);
//	}

	
	public CommandRegistry getCommandRegistry() {
		if (commandRegistry == null) {
			commandRegistry = createCommandRegistry();
		}
		return commandRegistry;
	}
	
	
	public ActionCommand getActionCommand(Action action) {
		return getCommandRegistry().getActionCommand( action.toString() );
	}

	
	public DataTableModel<T> getTableModel() {
		if (tableModel == null) {
			tableModel = createDataTableModel();
		}
		return tableModel;
	}
	
	
	public DataSource<T, I> getDataSource() {
		return dataSource;
	}
	

	public TableSelectionModel<I> getSelectionModel() {
		if (selectionModel == null) {
			selectionModel = createSelectionModel();
		}
		return selectionModel;
	}

	
	public void delete() {
	}
	
	
	/**
	 * Cancela la selección en curso y fuerza la ejecución de la última consulta.
	 */
	public void refresh() {
		getSelectionModel().clearSelection();
		getTableModel().refresh();
	}
	
	
	public void startBrowse() {	
	}	
	
	
	public void addNew() {
	}
	
	
	public void startEdit(I id) {
	}
	
	
	public void acceptEdit() {
	}
	
	
	public void cancelEdit() {
	}
	
	public boolean cancelEdit(Command postCancel) {
		return true;
	}
		
	//----------------------------------------------------------------------------------------------
	
	protected HandlerManager getHandlerManager() {
		if (handlerManager == null) {
			handlerManager = new HandlerManager(this);
		}
		return handlerManager;
	}
	
	
	protected CommandRegistry createCommandRegistry() {
		DataManegementCommandRegistry registry = new DataManegementCommandRegistry();
		
		registry.registerCommand( new ActionCommand(Action.addNew) {
			@Override protected void doExecuteCommand() {
				addNew();
			}
		});		
		registry.registerRequiredSelectionCommand( new ActionCommand(Action.delete) {
			@Override protected void doExecuteCommand() {
				delete();					
			}				
		});		
		registry.registerCommand( new ActionCommand(Action.refresh) {
			@Override protected void doExecuteCommand() {
				refresh();					
			}				
		});		
		registry.registerCommand( new ActionCommand(Action.search) {
			@Override
			protected void doExecuteCommand() {
				final String text = (String) getParameter("text");
				Criterion criterion = null;
				if (text != null && text.trim().length() > 0) {
					criterion = createDefaultSearchCriterion(text);
				}
				getTableModel().getFilterModel().setValue(criterion);
			}
		});
		registry.registerCommand( new ActionCommand(Action.clearSearch) {
			@Override
			protected void doExecuteCommand() {
				getTableModel().getFilterModel().setValue(null);
			}
		});
		registry.registerCommand( new ActionCommand(Action.startEdit) {
			@SuppressWarnings("unchecked")
			@Override protected void doExecuteCommand() {
				startEdit( (I) getParameter("id") );					
			}
		});
		registry.registerCommand( new ActionCommand(Action.acceptEdit) {
			@Override protected void doExecuteCommand() {
				acceptEdit();
			}				
		});
		registry.registerCommand( new ActionCommand(Action.cancelEdit) {
			@Override protected void doExecuteCommand() {
				cancelEdit();
			}				
		});

		return registry;
	}
	

	protected abstract DataTableModel<T> createDataTableModel();

	protected abstract TableSelectionModel<I> createSelectionModel();


	protected Criterion createDefaultSearchCriterion(Object value) {
		return null;
	}
	
	
	/**
	 * Ejecuta un comando previa confirmación.
	 */
	protected void executeWithConfirmation(Command command, String messageKey) {
		fireEvent( new ConfirmationEvent(messageKey, command) );
	}
	
	
	protected void fireEvent(GwtEvent<?> event) {
		getHandlerManager().fireEvent(event);
	}
	
	
	protected void enableActions(String... actionIds) {
		enableActions( Arrays.asList( actionIds ) );
	}
	
	
	protected void enableActions(Iterable<String> actionIds) {
		for (String actionId : actionIds) {
			ActionCommand command = getCommandRegistry().getActionCommand(actionId);
			command.setVisible(true);
			command.setEnabled(true);
		}
	}
	
	
	protected class DataManegementCommandRegistry extends DefaultCommandRegistry {
		
		private ActionCommandInterceptor requiredSelection = new RequiredSelectionCommandInterceptor();
		
		public void registerRequiredSelectionCommand(ActionCommand command) {
			command.addCommandInterceptor(requiredSelection);
			registerCommand(command);
		}
	}
		
	
	/**
	 * Intercepta la ejecución de una acción para comprobar si hay items seleccionados,
	 * y si no es así lanza un mensaje para el usuario.
	 */
	protected class RequiredSelectionCommandInterceptor implements ActionCommandInterceptor {

		public boolean preExecution(ActionCommand command) {
			if (getSelectionModel().getSelectedRowCount() > 0) {
				return true;
			}

			fireEvent( new MessageEvent("noselection", MessageType.AUTO) );
			return false;
		}
		
		public void postExecution(ActionCommand command) {
		}
	}

}
