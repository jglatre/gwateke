package com.github.gwateke.command;


import com.github.gwateke.binding.value.support.AbstractPropertyChangePublisher;
import com.google.gwt.user.client.Command;



public abstract class AbstractCommand extends AbstractPropertyChangePublisher implements Command, ActionCommandExecutor {

	public static final String ENABLED_PROPERTY = "enabled";
	public static final String VISIBLE_PROPERTY = "visible";

	private String id;
	private boolean enabled;
	private boolean visible;
	
	
	public AbstractCommand(String id) {
		this.id = id;
	}

	
	public String getId() {
		return this.id;
	}
	
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	
	public void setEnabled(boolean enabled) {
    	if (hasChanged(this.enabled, enabled)) {
            this.enabled = enabled;
            firePropertyChange(ENABLED_PROPERTY, !enabled, enabled);
    	}
	}

	
	public boolean isVisible() {
		return this.visible;
	}
	
	
	public void setVisible(boolean visible) {
    	if (hasChanged(this.visible, visible)) {
            this.visible = visible;
            firePropertyChange(VISIBLE_PROPERTY, !visible, visible);
    	}
	}
}
