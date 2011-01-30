package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.user.client.ui.HTML;


public class HtmlAdapter extends AbstractValueModelAdapter<String> implements Binding<HTML> {

	private HTML html;
	
	
	public HtmlAdapter(HTML html, ValueModel<String> valueModel) {
		super(valueModel);
		this.html = html;
		initalizeAdaptedValue();
	}
	

	protected void valueModelValueChanged(String newValue) {
		html.setHTML(newValue != null ? newValue : "");
	}
	

	public HTML getWidget() {
		return html;
	}

}
