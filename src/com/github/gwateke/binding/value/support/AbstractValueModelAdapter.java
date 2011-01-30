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
package com.github.gwateke.binding.value.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.github.gwateke.binding.value.ValueModel;



/**
 * Abstract base class for objects that adapt a value model to some other 
 * model. e.g. a GUI component.
 * 
 * @author Oliver Hutchison
 */
public abstract class AbstractValueModelAdapter<T> {

    private final ValueModelChangeHandler valueModelChangeHandler = new ValueModelChangeHandler();

    private ValueModel<T> valueModel;

    public AbstractValueModelAdapter(ValueModel<T> valueModel) {
        this.valueModel = valueModel;
        this.valueModel.addValueChangeListener(valueModelChangeHandler);
    }
    
    
    public void unbind() {
    	valueModel.removeValueChangeListener(valueModelChangeHandler);
    	valueModel = null;
    }
    
    
    /**
     * Must be called to initialize the adapted value. Usually the
     * last call in the constructor. 
     */
    protected void initalizeAdaptedValue() {
        valueModelValueChanged(valueModel.getValue());
    }

    protected ValueModel<T> getValueModel() {
        return valueModel;
    }
        
    /**
     * Subclasses must called this when the value being adapted has changed.
     * 
     * @param newValue the new adapted value 
     */
    protected void adaptedValueChanged(T newValue) {
        if (valueModel != null) {
            valueModel.setValueSilently(newValue, valueModelChangeHandler);
        }
    }

    /**
     * Called when the value held by the value model has changes
     * 
     * @param newValue the new value held by the value model
     */
    protected abstract void valueModelValueChanged(T newValue);

    private class ValueModelChangeHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            valueModelValueChanged(valueModel.getValue());
        }
    }
}