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

import java.beans.PropertyChangeListener;

import com.github.gwateke.binding.value.ValueModel;



/**
 * @author Keith Donald
 */
public class AbstractValueModelWrapper<T> implements ValueModel<T>, ValueModelWrapper<T> {
    private final ValueModel<T> wrappedModel;

    public AbstractValueModelWrapper(ValueModel<T> valueModel) {
        this.wrappedModel = valueModel;
    }

    public T getValue() {
        return wrappedModel.getValue();
    }

    public final void setValue(T value) {
        setValueSilently(value, null);
    }
    
    public void setValueSilently(T value, PropertyChangeListener listenerToSkip) {
        wrappedModel.setValueSilently(value, listenerToSkip);
    }

    public ValueModel<T> getWrappedValueModel() {
        return wrappedModel;
    }

    public ValueModel<T> getInnerMostWrappedValueModel() {
        if (wrappedModel instanceof ValueModelWrapper)
            return ((ValueModelWrapper<T>) wrappedModel).getInnerMostWrappedValueModel();

        return wrappedModel;
    }

    public T getInnerMostValue() {
        if (wrappedModel instanceof ValueModelWrapper)
            return ((ValueModelWrapper<T>) wrappedModel).getInnerMostWrappedValueModel().getValue();

        return wrappedModel.getValue();
    }

    public void addValueChangeListener(PropertyChangeListener listener) {
        wrappedModel.addValueChangeListener(listener);
    }

    public void removeValueChangeListener(PropertyChangeListener listener) {
        wrappedModel.removeValueChangeListener(listener);
    }
}