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

import java.beans.PropertyChangeListener;

/**
 * Interface implemented by domain objects that can publish property change
 * events. Clients can use this interface to subscribe to the object for change
 * notifications.
 * 
 * @author Keith Donald
 */
public interface PropertyChangePublisher {
    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener);
}