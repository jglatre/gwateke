/*
 * Copyright 2004-2005 the original author or authors.
 */
package com.github.gwateke.binding.value.support;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.gwateke.binding.value.ValueModel;



/**
 * 
 * @author HP
 */
public class MapKeyAdapter<K,V> extends AbstractValueModel<V> {

	protected static final Log log = LogFactory.getLog(MapKeyAdapter.class);
	
    private ValueModel<Map<K,V>> mapValueModel;
    private K key;
    

    public MapKeyAdapter(ValueModel<Map<K, V>> valueModel, K key) {
        super();
        this.mapValueModel = valueModel;
        setKey(key);
    }

    
    public void setKey(K key) {
        this.key = key;
    }

    
    public V getValue() {
        Map<K,V> map = mapValueModel.getValue();
        if (map == null) {
            return null;
        }
        return get(map, key);
    }

    
    public void setValue(V value) {
        Map<K,V> map = mapValueModel.getValue();
        if (map == null) {
            return;
        }
        
        V oldValue = get(map, key);
        if (hasValueChanged(oldValue, value)) {
        	log.debug("MapKeyAdapter for " + key + ": setting value from '" + oldValue + "' to '" + value + "'", null);
        	put(map, key, value);
        	fireValueChange(oldValue, value);
        }
    }

    
    @SuppressWarnings("unchecked")
	private V get(Map<K,V> map, K key) {
    	String path = key.toString();
    	int pos = path.indexOf('.');
    	if (pos == -1) {
    		return map.get(key);
    	}
    	else {
    		K parent = (K) path.substring(0, pos);
    		K child = (K) path.substring(pos + 1);
    		return get((Map<K,V>) map.get(parent), child);
    	}
    }
    
    
    @SuppressWarnings("unchecked")
	private void put(Map map, K key, V value) {
    	String path = key.toString();
    	int pos = path.indexOf('.');
    	if (pos == -1) {
    		map.put(key, value);
    	}
    	else {
    		K parent = (K) path.substring(0, pos);
    		K child = (K) path.substring(pos + 1);
    		put((Map) map.get(parent), child, value);
    	}
    }
}