package com.github.gwateke.command.adapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.command.ActionCommand;
import com.github.gwateke.core.closure.Closure;


public abstract class AbstractActionCommandBinding<W> implements Binding<W> {

	private W widget;
	private ActionCommand command;
	private Closure<Map<?,?>, ?> parameterProvider;
	private final PropertyChangeListener actionCommandChangeHandler = new ActionCommandChangeHandler();

	
	public AbstractActionCommandBinding(W widget, ActionCommand command, Closure<Map<?,?>, ?> parameterProvider) {
		this.widget = widget;
		this.command = command;
		this.command.addPropertyChangeListener(actionCommandChangeHandler);
		this.parameterProvider = parameterProvider;

		initializeAdaptedWidget();
	}
	
	
	public W getWidget() {
		return widget;
	}

	
	public void unbind() {
		command.removePropertyChangeListener(actionCommandChangeHandler);
		command = null;
	}

	
    protected void initializeAdaptedWidget() {
    	actionCommandChanged(command);
	}

    
    protected void executeCommand() {
		if (parameterProvider != null) {
			Map<?,?> parameters = parameterProvider.call(null);
			command.execute(parameters);
		}
		else {
			command.execute();
		}
    }
    
    
	protected abstract void actionCommandChanged(ActionCommand command);

	
	private class ActionCommandChangeHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
        	assert (ActionCommand) evt.getSource() == command;
        	actionCommandChanged(command);
        }
    }

}
