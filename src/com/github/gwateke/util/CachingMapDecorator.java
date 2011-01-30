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
package com.github.gwateke.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


/**
 * A simple decorator for a Map, encapsulating the workflow for caching
 * expensive values in a target Map. Supports caching weak or strong keys.
 *
 * <p>This class is also an abstract template. Caching Map implementations
 * should subclass and override the <code>create(key)</code> method which
 * encapsulates expensive creation of a new object.
 * 
 * @author Keith Donald
 * @since 1.2.2
 */
public abstract class CachingMapDecorator<K, V> implements Map<K, V> {
	
	protected static final Logger log = Logger.getLogger(CachingMapDecorator.class.getName());
	
//	private static Object NULL_VALUE = new Object();

	private final Map<K, V> targetMap;


	/**
	 * Create a CachingMapDecorator with strong keys,
	 * using an underlying synchronized Map.
	 */
	public CachingMapDecorator() {
		this(new HashMap<K, V>());
	}


	/**
	 * Create a CachingMapDecorator for the given Map.
	 * <p>The passed-in Map won't get synchronized explicitly,
	 * so make sure to pass in a properly synchronized Map, if desired.
	 * @param targetMap the Map to decorate
	 */
	public CachingMapDecorator(Map<K ,V> targetMap) {
		assert targetMap != null : "Target Map is required";
		this.targetMap = targetMap;
	}


	public int size() {
		return this.targetMap.size();
	}

	public boolean isEmpty() {
		return this.targetMap.isEmpty();
	}

	public boolean containsKey(Object key) {
		return this.targetMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.targetMap.containsValue(value);
	}

	public V put(K key, V value) {
		return this.targetMap.put(key, value);
	}

	public V remove(Object key) {
		return this.targetMap.remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		this.targetMap.putAll(t);
	}

	public void clear() {
		this.targetMap.clear();
	}

	public Set<K> keySet() {
		return this.targetMap.keySet();
	}

	public Collection<V> values() {
		return this.targetMap.values();
	}

	public Set<Entry<K, V>> entrySet() {
		return this.targetMap.entrySet();
	}


	/**
	 * Get value for key.
	 * Creates and caches value if it doesn't already exist in the cache.
	 * <p>This implementation is <i>not</i> synchronized: This is highly
	 * concurrent but does not guarantee unique instances in the cache,
	 * as multiple values for the same key could get created in parallel.
	 * Consider overriding this method to synchronize it, if desired.
	 * @see #create(Object)
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V value = this.targetMap.get(key);
		if (value == null) {
			log.fine("Creating new expensive value for key '" + key + "'");
			
			value = create(key);
			
//			if (value == null) {
//				value = NULL_VALUE;
//			}
			
			log.fine("Caching expensive value: " + value);
		
			put((K) key, value);
		}
		else {
			log.fine("For key '" + key + "', returning cached value: " + value);
		}
		return value;
//		return (value == NULL_VALUE) ? null : value;
	}

	/**
	 * Create a value to cache for the given key.
	 * Called by <code>get</code> if there is no value cached already.
	 * @param key the cache key
	 * @see #get(Object)
	 */
	protected abstract V create(Object key);


	public String toString() {
		return "CachingMapDecorator [" + getClass() + "]:" + this.targetMap;
	}

}
