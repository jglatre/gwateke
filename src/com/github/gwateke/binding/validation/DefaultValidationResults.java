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

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.gwateke.util.CachingMapDecorator;
import com.github.gwateke.util.ObjectUtils;



public class DefaultValidationResults implements ValidationResults {

    private final Set<ValidationMessage> messages = new HashSet<ValidationMessage>();

    private Map<?, Set<ValidationMessage>> messagesSubSets = new CachingMapDecorator<Object, Set<ValidationMessage>>() {

        protected Set<ValidationMessage> create(Object key) {
            Set<ValidationMessage> messagesSubSet = new HashSet<ValidationMessage>();
            for (ValidationMessage message : messages) {
                if (key instanceof Severity && message.getSeverity().equals(key)) {
                    messagesSubSet.add(message);
                }
                else if (ObjectUtils.nullSafeEquals(message.getProperty(), key)) {
                    messagesSubSet.add(message);
                }
            }
            return messagesSubSet;
        }
    };

    
    public DefaultValidationResults() {
    }

    
    public DefaultValidationResults(ValidationResults validationResults) {
        addAllMessages(validationResults);
    }
    

    public DefaultValidationResults(Collection<ValidationMessage> validationMessages) {
        addAllMessages(validationMessages);
    }

    
    public void addAllMessages(ValidationResults validationResults) {
        addAllMessages(validationResults.getMessages());
    }

    
    public void addAllMessages(Collection<ValidationMessage> validationMessages) {        
        if (messages.addAll(validationMessages)) {
            messagesSubSets.clear();
        }
    }

    
    public void addMessage(ValidationMessage validationMessage) {
        if (messages.add(validationMessage)) {            
            messagesSubSets.clear();
        }
    }

    
    public void addMessage(String field, Severity severity, String message) {
        addMessage(new DefaultValidationMessage(field, severity, message));
    }

    
    public void removeMessage(ValidationMessage message) {
        messages.remove(message);
        messagesSubSets.clear();
    }
    

    public boolean getHasErrors() {
        return getMessageCount(Severity.ERROR) > 0;
    }

    public boolean getHasWarnings() {
        return getMessageCount(Severity.WARNING) > 0;
    }

    public boolean getHasInfo() {
        return getMessageCount(Severity.INFO) > 0;
    }

    public int getMessageCount() {
        return messages.size();
    }

    public int getMessageCount(Severity severity) {
        return getMessages(severity).size();
    }

    public int getMessageCount(String fieldName) {
        return getMessages(fieldName).size();
    }

    public Set<ValidationMessage> getMessages() {
        return messages;
    }

    public Set<ValidationMessage> getMessages(Severity severity) {
        return messagesSubSets.get(severity);
    }

    public Set<ValidationMessage> getMessages(String fieldName) {
        return messagesSubSets.get(fieldName);
    }

//    public String toString() {
//        return new ToStringCreator(this).append("messages", getMessages()).toString();
//    }
    
    /**
     * Clear all messages.
     * 
     * @see RulesValidator#clearMessages()
     */
    public void clearMessages() {
        messages.clear();
        messagesSubSets.clear();
    }

}
