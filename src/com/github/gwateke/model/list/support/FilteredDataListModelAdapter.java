package com.github.gwateke.model.list.support;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.github.gwateke.data.query.Comparison;


/**
 * Escucha los cambios en un ValueModel para aplicarle un filtro a un ListModel.
 * 
 * @author juanjo
 */
public class FilteredDataListModelAdapter extends AbstractValueModelAdapter<Object> {

	private DataListModel<?> listModel;
	private String fieldName;

	
	@SuppressWarnings("unchecked")
	public FilteredDataListModelAdapter(DataListModel<?> listModel, String fieldName, ValueModel<?> valueModel) {
		super((ValueModel<Object>) valueModel);
		this.listModel = listModel;
		this.fieldName = fieldName;
		initalizeAdaptedValue();
	}
	

	@Override
	protected void valueModelValueChanged(Object newValue) {
		if (newValue != null) {
			listModel.setBaseFilter( new Comparison.Equals<Object>(fieldName, newValue) );
			listModel.refresh();
		}
	}		
}