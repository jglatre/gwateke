/*
 * Copyright 2002-2005 the original author or authors.
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
package com.github.gwateke.binding.value.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

import com.github.gwateke.binding.value.CommitTrigger;
import com.github.gwateke.binding.value.CommitTriggerListener;
import com.github.gwateke.binding.value.ValueModel;



/**
 * A value model that wraps another value model; delaying or buffering changes
 * until a commit is triggered.
 * 
 * TODO: more class docs...
 * 
 * @author Karsten Lentzsch
 * @author Keith Donald
 * @author Oliver Hutchison  
 */
public class BufferedValueModel<T> extends AbstractValueModel<T> implements ValueModelWrapper<T> {

	protected static final Logger log = Logger.getLogger(BufferedValueModel.class.getName());
	
    /**
     * Name of the bound property <em>buffering</em>.
     */
    public static final String BUFFERING_PROPERTY = "buffering";

    private final ValueModel<T> wrappedModel;

    private final PropertyChangeListener wrappedModelChangeHandler;

    private T bufferedValue;

    private CommitTrigger commitTrigger;

    private CommitTriggerListener commitTriggerHandler;

    private boolean buffering;

    /**
     * Constructs a <code>BufferedValueHolder</code> that wraps the given wrappedModel.  
     * 
     * @param wrappedModel the value model to be buffered
     */
    public BufferedValueModel(ValueModel<T> wrappedModel) {
        this(wrappedModel, null);
    }

    /**
     * Constructs a <code>BufferedValueHolder</code> that wraps the given wrappedModel
     * and listens to the provided commitTrigger for commit and revert events.  
     * 
     * @param wrappedModel the value model to be buffered
     * @param commitTrigger the commit trigger that triggers the commit or flush event
     */
    public BufferedValueModel(ValueModel<T> wrappedModel, CommitTrigger commitTrigger) {
//        Assert.notNull(wrappedModel, "Wrapped value model can not be null.");
        this.wrappedModel = wrappedModel;
        this.wrappedModelChangeHandler = new WrappedModelValueChangeHandler();
        this.wrappedModel.addValueChangeListener(wrappedModelChangeHandler);
        this.bufferedValue = wrappedModel.getValue();
        setCommitTrigger(commitTrigger);
    }

    /**
     * Returns the CommitTrigger that is used to trigger commit and flush events.
     * 
     * @return the CommitTrigger that is used to trigger commit and flush events
     */
    public final CommitTrigger getCommitTrigger() {
        return commitTrigger;
    }

    /**
     * Sets the <code>CommitTrigger</code> that triggers the commit and flush events.
     *  
     * @param commitTrigger  the commit trigger; or null to deregister the 
     * existing trigger. 
     */
    public final void setCommitTrigger(CommitTrigger commitTrigger) {
        if (this.commitTrigger == commitTrigger) {
            return;
        }
        if (this.commitTrigger != null) {
            this.commitTrigger.removeCommitTriggerListener(commitTriggerHandler);
            this.commitTrigger = null;
        }
        if (commitTrigger != null) {
            if (this.commitTriggerHandler == null) {
                this.commitTriggerHandler = new CommitTriggerHandler();
            }
            this.commitTrigger = commitTrigger;
            this.commitTrigger.addCommitTriggerListener(commitTriggerHandler);
        }
    }

    /**
     * Returns whether this model buffers a value or not, that is, whether
     * a value has been assigned since the last commit or flush. 
     * 
     * @return true if a value has been assigned since the last commit or revert
     */
    public boolean isBuffering() {
        return buffering;
    }

    /**
     * Returns the wrappedModel value if no value has been set since the last
     * commit or flush, and returns the buffered value otherwise.
     * 
     * @return the buffered value
     */
    public T getValue() {
        return bufferedValue;
    }

    /**
     * Sets a new buffered value and turns this BufferedValueModel into
     * the buffering state. The buffered value is not provided to the
     * underlying model until the trigger channel indicates a commit.
     *
     * @param value  the value to be buffered
     */
    public void setValue(T value) {
        log.fine("[BufferedValueModel] Setting buffered value to '" + value + "'");

        final Object oldValue = getValue();
        this.bufferedValue = value;
        updateBuffering();
        fireValueChange(oldValue, bufferedValue);
    }

    /**
     * Returns the wrappedModel, i.e. the underlying ValueModel that provides 
     * the unbuffered value.
     * 
     * @return the ValueModel that provides the unbuffered value  
     */
    public final ValueModel<T> getWrappedValueModel() {
        return wrappedModel;
    }
    
    /**
     * Returns the inner most wrappedModel; i.e. the root ValueModel that provides 
     * the unbuffered value. This is found by repeatedly unwrapping any ValueModelWrappers
     * until we find the inner most value model.
     * 
     * @return the inner most ValueModel that provides the unbuffered value
     * 
     * @see ValueModelWrapper#getInnerMostWrappedValueModel()  
     */
    @SuppressWarnings("unchecked")
	public final ValueModel<T> getInnerMostWrappedValueModel() {
        if (wrappedModel instanceof ValueModelWrapper)
            return ((ValueModelWrapper) wrappedModel).getInnerMostWrappedValueModel();

        return wrappedModel;
    }

    /**
     * Called when the value held by the wrapped value model changes.
     */
    protected void onWrappedValueChanged() {
    	log.fine("[BufferedValueModel] Wrapped model value has changed; new value is '" + wrappedModel.getValue() + "'");
       
        setValue(wrappedModel.getValue());
    }

    /**
     * Commits the value buffered by this value model back to the 
     * wrapped value model. 
     */
    public void commit() {
        if (isBuffering()) {
        	log.fine("[BufferedValueModel] Committing buffered value '" + getValue() + "' to wrapped value model '" + 
        			wrappedModel + "'");
      
            wrappedModel.setValueSilently(getValueToCommit(), wrappedModelChangeHandler);
            setValue(wrappedModel.getValue()); // check if the wrapped model changed the committed value
        }
        else {
        	log.fine("[BufferedValueModel] No buffered edit to commit; nothing to do...");
        }
    }

    /**
     * Provides a hook that allows for modification of the value that is committed 
     * to the underlying value model. 
     */
    protected T getValueToCommit() {
        return bufferedValue;
    }

    /**
     * Updates the value of the buffering property. Fires a property change event 
     * if there's been a change.
     */
    private void updateBuffering() {
        boolean wasBuffering = isBuffering();
        buffering = hasValueChanged(wrappedModel.getValue(), bufferedValue);
        firePropertyChange(BUFFERING_PROPERTY, wasBuffering, buffering);
    }

    /**
     * Reverts the value held by the value model back to the value held by the 
     * wrapped value model.
     */
    public final void revert() {
    	updateBuffering();   // TODO explicar
    	
        if (isBuffering()) {
        	log.fine("[BufferedValueModel] Reverting buffered value '" + getValue() + "' to value '" + 
        			wrappedModel.getValue() + "'");
            setValue(wrappedModel.getValue());
        }
        else {
        	log.fine("[BufferedValueModel] No buffered edit to commit; nothing to do...");
        }
    }

    public String toString() {
        return "bufferedValue = " + bufferedValue;
    }

    /**
     * Listens for changes to the wrapped value model.
     */
    private class WrappedModelValueChangeHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            onWrappedValueChanged();
        }
    }

    /**
     * Listens for commit/revert on the commitTrigger.
     */
    private class CommitTriggerHandler implements CommitTriggerListener {
        public void commit() {
        	log.fine("[BufferedValueModel] Commit trigger fired commit event.");
            BufferedValueModel.this.commit();
        }

        public void revert() {
        	log.fine("[BufferedValueModel] Commit trigger fired revert event.");
            BufferedValueModel.this.revert();
        }
    }

}