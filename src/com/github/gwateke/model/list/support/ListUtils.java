package com.github.gwateke.model.list.support;

import java.util.HashMap;
import java.util.Map;

import com.github.gwateke.model.list.ListModel;



public abstract class ListUtils {

	public static <K, E> Map<K, E> asMap(ListModel<K, E> listModel) {
		Map<K, E> map = new HashMap<K, E>();
		for (int i = 0; i < listModel.getSize(); i++) {
			map.put(listModel.getKeyAt(i), listModel.getElementAt(i));
		}
		return map;
	}
}
