/*
 * Copyright 2002-2004 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.gwateke.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public abstract class ActionCommand extends AbstractCommand implements ActionCommandExecutor,
        ParameterizableActionCommandExecutor {
	
//    public static final String ACTION_COMMAND_PROPERTY = "actionCommand";
//    public static final String ACTION_EVENT_PARAMETER_KEY = "actionEvent";
    public static final String MODIFIERS_PARAMETER_KEY = "modifiers";

//    private static final String ELLIPSES = "...";

    private List<ActionCommandInterceptor> commandInterceptors;
//    private String actionCommand;
//    private SwingActionAdapter swingActionAdapter;
    private Map<Object, Object> parameters = new HashMap<Object, Object>(6);
//    private boolean displaysInputDialog;

    
    public ActionCommand(Enum<?> e) {
    	this(e.toString());
    }

    public ActionCommand(String commandId) {
        super(commandId);
    }

//    public ActionCommand(String id, CommandFaceDescriptor face) {
//        super(id, face);
//    }

//    public ActionCommand(String id, String encodedLabel) {
//        super(id, encodedLabel);
//    }

//    public ActionCommand(String id, String encodedLabel, Icon icon, String caption) {
//        super(id, encodedLabel, icon, caption);
//    }


    public void addParameter(Object key, Object value) {
        parameters.put(key, value);
    }


    protected Object getParameter(Object key) {
        return parameters.get(key);
    }


    protected Map<?,?> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }


    protected Object getParameter(Object key, Object defaultValue) {
        Object value = parameters.get(key);
        return value != null ? value : defaultValue;
    }


    public void addCommandInterceptor(ActionCommandInterceptor l) {
        if (commandInterceptors == null) {
            commandInterceptors = new ArrayList<ActionCommandInterceptor>(6);
        }
        commandInterceptors.add(l);
    }


    public void removeCommandInterceptor(ActionCommandInterceptor l) {
        assert commandInterceptors != null : "The command interceptors list has not yet been initialized";
        commandInterceptors.remove(l);
    }


//    protected void onButtonAttached(AbstractButton button) {
//        super.onButtonAttached(button);
//        button.setActionCommand(actionCommand);
//        button.addActionListener(actionPerformedHandler);
//        if (displaysInputDialog) {
//            if (!button.getText().endsWith(ELLIPSES)) {
//                button.setText(getText() + ELLIPSES);
//            }
//        }
//    }

//    ActionListener actionPerformedHandler = new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            addParameter(ACTION_EVENT_PARAMETER_KEY, e);
//            addParameter(MODIFIERS_PARAMETER_KEY, new Integer(e.getModifiers()));
//            execute();
//        }
//    };

    protected int getModifiers() {
        return ((Integer) getParameter(MODIFIERS_PARAMETER_KEY, new Integer(0))).intValue();
    }

//    public Action getActionAdapter() {
//        if (swingActionAdapter == null) {
//            this.swingActionAdapter = new SwingActionAdapter(this);
//        }
//        return swingActionAdapter;
//    }

//    public String getActionCommand() {
//        return actionCommand;
//    }

//    public void setActionCommand(String newCommandName) {
//        if (!ObjectUtils.nullSafeEquals(actionCommand, newCommandName)) {
//            String old = actionCommand;
//            actionCommand = newCommandName;
//            Iterator iter = buttonIterator();
//            while (iter.hasNext()) {
//                AbstractButton button = (AbstractButton)iter.next();
//                button.setActionCommand(actionCommand);
//            }
//            firePropertyChange(ACTION_COMMAND_PROPERTY, old, newCommandName);
//        }
//    }

//    public void setDefaultButtonIn(RootPaneContainer container) {
//        JRootPane rootPane = container.getRootPane();
//        JButton button = (JButton)getButtonIn(rootPane);
//        if (button != null) {
//            rootPane.setDefaultButton(button);
//        }
//    }

//    public void setDefaultButton() {
//        Iterator it = buttonIterator();
//        while (it.hasNext()) {
//            Object o = it.next();
//            if (o instanceof JButton) {
//                JButton button = (JButton)o;
//                JRootPane pane = SwingUtilities.getRootPane(button);
//                if (pane != null) {
//                    pane.setDefaultButton(button);
//                }
//            }
//        }
//    }

//    public void setDisplaysInputDialog(boolean displaysInputDialog) {
//        this.displaysInputDialog = displaysInputDialog;
//    }

//    public boolean isDisplaysInputDialog() {
//        return displaysInputDialog;
//    }
    

    public final void execute(Map<?,?> parameters) {
        this.parameters.putAll(parameters);
        execute();
    }

    
    public final void execute() {
        if (onPreExecute()) {
            doExecuteCommand();
            onPostExecute();
        }
        parameters.clear();
    }

    
    protected final boolean onPreExecute() {
        if (commandInterceptors == null) {
            return true;
        }
        for (ActionCommandInterceptor listener : commandInterceptors) {
            if (!listener.preExecution(this)) {
                return false;
            }
        }
        return true;
    }

    
    protected abstract void doExecuteCommand();


    protected final void onPostExecute() {
        if (commandInterceptors == null) {
            return;
        }
        for (ActionCommandInterceptor interceptor : commandInterceptors) {
            interceptor.postExecution(this);
        }
    }

}