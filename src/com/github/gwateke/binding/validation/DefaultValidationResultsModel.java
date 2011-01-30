/*
 * Copyright 2002-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.gwateke.binding.validation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.gwateke.util.CachingMapDecorator;



/**
 * Default implementation of ValidationResultsModel
 * 
 * @author  Oliver Hutchison
 */
public class DefaultValidationResultsModel implements ValidationResultsModel {

    private final List<ValidationListener> validationListeners = new ArrayList<ValidationListener>();

    private final Map<String, List<ValidationListener>> propertyValidationListeners = new CachingMapDecorator<String, List<ValidationListener>>() {
        protected List<ValidationListener> create(Object propertyName) {
            return new ArrayList<ValidationListener>();
        }
    };

    private final Map<String, List<PropertyChangeListener>> propertyChangeListeners = new CachingMapDecorator<String, List<PropertyChangeListener>>() {
        protected List<PropertyChangeListener> create(Object propertyName) {
            return new ArrayList<PropertyChangeListener>();
        }
    };

    private final ValidationResultsModel delegateFor;
    private ValidationResults validationResults = EmptyValidationResults.INSTANCE;
    

    public DefaultValidationResultsModel() {
        delegateFor = this;
    }

    
    public DefaultValidationResultsModel(ValidationResultsModel delegateFor) {
        this.delegateFor = delegateFor;
    }

    
    public void updateValidationResults(ValidationResults newValidationResults) {
//        Assert.required(newValidationResults, "newValidationResults");
        ValidationResults oldValidationResults = validationResults;
        validationResults = newValidationResults;
        if (oldValidationResults.getMessageCount() == 0 && validationResults.getMessageCount() == 0) {
            return;
        }
        for (String propertyName : propertyValidationListeners.keySet()) {
            if (oldValidationResults.getMessageCount(propertyName) > 0
                    || validationResults.getMessageCount(propertyName) > 0) {
                fireValidationResultsChanged(propertyName);
            }
        }
        fireChangedEvents(oldValidationResults);
    }
    
    
    // TODO: test
    public void addMessage(ValidationMessage validationMessage) {
        if (!validationResults.getMessages().contains(validationMessage)) {
            ValidationResults oldValidationResults = validationResults;
            List<ValidationMessage> newMessages = new ArrayList<ValidationMessage>( oldValidationResults.getMessages() );
            newMessages.add(validationMessage);
            validationResults = new DefaultValidationResults(newMessages);
            fireValidationResultsChanged(validationMessage.getProperty());
            fireChangedEvents(oldValidationResults);
        }
    }

    
    // TODO: test
    public void removeMessage(ValidationMessage validationMessage) {
        if (validationResults.getMessages().contains(validationMessage)) {
            ValidationResults oldValidationResults = validationResults;
            List<ValidationMessage> newMessages = new ArrayList<ValidationMessage>(oldValidationResults.getMessages());
            newMessages.remove(validationMessage);
            validationResults = new DefaultValidationResults(newMessages);
            fireValidationResultsChanged(validationMessage.getProperty());
            fireChangedEvents(oldValidationResults);
        }
    }

    
    // TODO: test
/*    public void replaceMessage(ValidationMessage messageToReplace, ValidationMessage replacementMessage) {        
        ValidationResults oldValidationResults = validationResults;
        List newMessages = new ArrayList(oldValidationResults.getMessages());
        final boolean containsMessageToReplace = validationResults.getMessages().contains(messageToReplace);
        if (containsMessageToReplace) {
            newMessages.remove(messageToReplace);
        }
        newMessages.add(replacementMessage);
        validationResults = new DefaultValidationResults(newMessages);
        if (containsMessageToReplace && !ObjectUtils.nullSafeEquals(messageToReplace.getProperty(), replacementMessage.getProperty())) {
            fireValidationResultsChanged(messageToReplace.getProperty());
        }
        fireValidationResultsChanged(replacementMessage.getProperty());
        fireChangedEvents(oldValidationResults);
    }
*/
    
    public void clearAllValidationResults() {
        updateValidationResults(EmptyValidationResults.INSTANCE);
    }

    
    public boolean getHasErrors() {
        return validationResults.getHasErrors();
    }

    public boolean getHasInfo() {
        return validationResults.getHasInfo();
    }

    public boolean getHasWarnings() {
        return validationResults.getHasWarnings();
    }

    public int getMessageCount() {
        return validationResults.getMessageCount();
    }

    public int getMessageCount(Severity severity) {
        return validationResults.getMessageCount(severity);
    }

    public int getMessageCount(String propertyName) {
        return validationResults.getMessageCount(propertyName);
    }

    public Set<ValidationMessage> getMessages() {
        return validationResults.getMessages();
    }

    public Set<ValidationMessage> getMessages(Severity severity) {
        return validationResults.getMessages(severity);
    }

    public Set<ValidationMessage> getMessages(String propertyName) {
        return validationResults.getMessages(propertyName);
    }

    public void addValidationListener(ValidationListener listener) {
        validationListeners.add(listener);
    }

    public void removeValidationListener(ValidationListener listener) {
        validationListeners.remove(listener);
    }

    public void addValidationListener(String propertyName, ValidationListener listener) {
        getValidationListeners(propertyName).add(listener);
    }

    public void removeValidationListener(String propertyName, ValidationListener listener) {
        getValidationListeners(propertyName).remove(listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChangeListeners(propertyName).add(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChangeListeners(propertyName).remove(listener);
    }
    

    protected void fireChangedEvents(ValidationResults oldValidationResults) {
        fireValidationResultsChanged();
        firePropertyChange(HAS_ERRORS_PROPERTY, oldValidationResults.getHasErrors(), getHasErrors());
        firePropertyChange(HAS_WARNINGS_PROPERTY, oldValidationResults.getHasWarnings(), getHasWarnings());
        firePropertyChange(HAS_INFO_PROPERTY, oldValidationResults.getHasInfo(), getHasInfo());
    }

    
    protected void fireValidationResultsChanged() {
    	for (ValidationListener validationListener : validationListeners) {
			validationListener.validationResultsChanged(delegateFor);
		}
    }

    
    protected void fireValidationResultsChanged(String propertyName) {
        for (ValidationListener validationListener : getValidationListeners(propertyName)) {
            validationListener.validationResultsChanged(delegateFor);            
        }
    }

    
    protected List<ValidationListener> getValidationListeners(String propertyName) {
        return propertyValidationListeners.get(propertyName);
    }

    
    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        if (oldValue != newValue) {
            List<PropertyChangeListener> propertyChangeListeners = getPropertyChangeListeners(propertyName);
            if (! propertyChangeListeners.isEmpty()) {
                PropertyChangeEvent event = new PropertyChangeEvent(delegateFor, propertyName, oldValue, newValue);
                for (PropertyChangeListener propertyChangeListener : propertyChangeListeners) {
					propertyChangeListener.propertyChange(event);
				}
            }
        }
    }

    
    protected List<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
        return propertyChangeListeners.get(propertyName);
    }
    
//    public String toString() {
//        return new ToStringCreator(this).append("messages", getMessages()).toString();
//    }
}