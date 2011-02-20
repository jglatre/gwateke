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
package com.github.gwateke.binding.value;

import com.github.gwateke.binding.value.event.CommitEvent;
import com.github.gwateke.binding.value.event.CommitTriggerEvent;
import com.github.gwateke.binding.value.event.CommitTriggerHandler;
import com.github.gwateke.binding.value.event.RevertEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * A class that can be used to trigger an event on a group of objects. Mainly 
 * intended to be used to trigger flush/revert in <code>BufferedValueModel</code>
 * but is useful in general.
 *  
 */
public class CommitTrigger {
  
	private HandlerManager handlerManager;

	
    /**
     * Constructs a <code>CommitTrigger</code>. 
     */
    public CommitTrigger() {
    	handlerManager = new HandlerManager(this);
    }

    
    /**
     * Triggers a commit event.
     */
    public void commit() {
    	handlerManager.fireEvent( new CommitEvent() );
    }

    
    /**
     * Triggers a revert event.
     */
    public void revert() {
    	handlerManager.fireEvent( new RevertEvent() );
    }
    
    
    /**
     * Adds the provided listener to the list of listeners that will be notified whenever
     * a commit or revert event is fired.
     * @param listener the <code>CommitTriggerHandler</code> to add
     * @return 
     */
    public HandlerRegistration addCommitTriggerHandler(CommitTriggerHandler handler) {
    	return handlerManager.addHandler( CommitTriggerEvent.TYPE, handler );
    }
    
}
