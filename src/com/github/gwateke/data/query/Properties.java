package com.github.gwateke.data.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Properties implements Iterable<Map<String,?>> {
	
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String PROPERTIES = "properties";

	private List<Map<String,?>> list = new ArrayList<Map<String,?>>();
	
	
	public Properties() {
	}
	
	
	public Properties(String... properties) {
		for (String property : properties) {
			add(property);
		}
	}
	
	
	public void add(String name) {
		Map<String,Object> property = new HashMap<String, Object>();
		property.put(NAME, name);
		list.add(property);
	}
	
	
	public void add(String name, String type) {
		Map<String,Object> property = new HashMap<String, Object>();
		property.put(NAME, name);
		property.put(TYPE, type);
		list.add(property);
	}
	
	
	public void add(String name, String type, Properties properties) {
		Map<String,Object> property = new HashMap<String, Object>();
		property.put(NAME, name);
		property.put(TYPE, type);
		property.put(PROPERTIES, properties.toList());
		list.add(property);
	}
	
	
	public void add(String name, String type, String[] properties) {
		Properties subproperties = new Properties(properties);
		add(name, type, subproperties);
	}
	
	
	public void addAll(Properties properties) {
		list.addAll(properties.list);
	}
	
	
	public void remove(String name) {
		list.remove( findProperty(name) );
	}
	
	
	public String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (Map<String,?> property : this) {
			names.add( (String) property.get(NAME) );
		}
		return names.toArray(new String[names.size()]);
	}
	
	
	public String getName(int pos) {
		return (String) list.get(pos).get(NAME);
	}
	
	
	public String getType(int pos) {
		return (String) list.get(pos).get(TYPE);
	}
	
	
	public Properties getSubproperties(int pos) {
		Properties subprops = new Properties(); 
		subprops.list.addAll( (List) list.get(pos).get(PROPERTIES) );
		return subprops;
	}
	
	
	public int size() {
		return list.size();
	}
	
	
	public void clear() {
		list.clear();
	}
	
	
	public List<Map<String,?>> toList() {
		return list;
	}
	

	public Iterator<Map<String, ?>> iterator() {
		return list.iterator();
	}

	
	public void setType(String name, String type) {
		Map<String, Object> property = (Map<String, Object>) findProperty(name);
		if (property != null) {
			property.put(TYPE, type);
		}
	}
	
	
	public void setSubproperties(String name, String[] props) {
		setSubproperties(name, new Properties(props));
	}
	
	
	public void setSubproperties(String name, Properties props) {
		Map<String, Object> property = (Map<String, Object>) findProperty(name);
		if (property != null) {
			property.put(PROPERTIES, props.toList());
		}
	}
	
	
	public String[] getDeepNames() {
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < size(); i++) {
			String name = getName(i);
			String type = getType(i);
			if (type != null && "Object".equals(type)) {
				for (String subname: getSubproperties(i).getDeepNames()) {
					names.add(name + '.' + subname);
				}
			}
			else {
				names.add(name);
			}
		}
		return names.toArray(new String[names.size()]);
	}
	
	//----------------------------------------------------------------------------------------------
	
	private Map<String, ?> findProperty(String name) {
		for (Map<String, ?> prop : this) {
			if (name.equals(prop.get(NAME))) {
				return prop;
			}
		}
		return null;
	}

}
