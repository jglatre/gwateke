package com.github.gwateke.ui.list.model;

import java.util.HashMap;
import java.util.Map;



public abstract class ListUtils {

	public static <K, E> Map<K, E> asMap(ListModel<K, E> listModel) {
		Map<K, E> map = new HashMap<K, E>();
		for (int i = 0; i < listModel.getSize(); i++) {
			map.put(listModel.getKeyAt(i), listModel.getElementAt(i));
		}
		return map;
	}
}
