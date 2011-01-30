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
package com.github.gwateke.binding.value.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.github.gwateke.binding.value.DerivedValueModel;
import com.github.gwateke.binding.value.ValueModel;


/**
 * Abstract base class for value models that derive their value from one or
 * more "source" value model. Provides a hook to notify when any of the 
 * "source" value models change.
 * 
 * @author  Oliver Hutchison
 *
 * @param <S> type of source value models.
 * @param <T> type of derived value model.
 */
public abstract class AbstractDerivedValueModel<S, T> extends AbstractValueModel<T> implements DerivedValueModel<T> {

    private ValueModel<S>[] sourceValueModels;

    private final PropertyChangeListener sourceChangeHandler;

    
    protected AbstractDerivedValueModel() {
        this.sourceChangeHandler = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                sourceValuesChanged((ValueModel<S>) evt.getSource());
            }
        };
    }
    
    
    protected AbstractDerivedValueModel(ValueModel<S>[] sourceValueModels) {
    	this();
        setSourceValueModels(sourceValueModels);
    }
    

    public ValueModel<S>[] getSourceValueModels() {
        return sourceValueModels;
    }

    
    protected void setSourceValueModels(ValueModel<S>[] sourceValueModels) {
    	this.sourceValueModels = sourceValueModels;
        for (int i = 0; i < sourceValueModels.length; i++) {
            sourceValueModels[i].addValueChangeListener(sourceChangeHandler);
        }
    }
    
    
    @SuppressWarnings("unchecked")
	protected S[] getSourceValues() {
        S[] values = (S[]) new Object[sourceValueModels.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = sourceValueModels[i].getValue();
        }
        return values;
    }

    protected abstract void sourceValuesChanged(ValueModel<S> source);

    public boolean isReadOnly() {
        return true;
    }

    public void setValue(T newValue) {
        throw new UnsupportedOperationException("This value model is read only");
    }
}