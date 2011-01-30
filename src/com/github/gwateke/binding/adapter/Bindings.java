package com.github.gwateke.binding.adapter;


import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.ui.list.model.ListModel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBoxBase;


public class Bindings {

	public static Binding<TextBoxBase> bind(TextBoxBase textBox, ValueModel<String> valueModel) {
		return new TextBoxAdapter(textBox, valueModel);
	}
	
	
	public static Binding<ListBox> bind(ListBox listBox, ValueModel<?> valueModel) {
		return new ListBoxAdapter(listBox, valueModel);
	}
	
	
	public static Binding<ListBox> bind(ListBox listBox, ValueModel<?> valueModel, ListModel<?,?> listModel) {
		return new ListBoxBinding(listBox, valueModel, listModel);
	}
	
	
	public static Binding<CheckBox> bind(CheckBox checkBox, ValueModel<Boolean> valueModel) {
		return new CheckBoxAdapter(checkBox, valueModel);
	}
	
	
	public static Binding<Label> bind(Label label, ValueModel<String> valueModel) {
		return new LabelAdapter(label, valueModel);
	}
	
	
	public static Binding<HTML> bind(HTML html, ValueModel<String> valueModel) {
		return new HtmlAdapter(html, valueModel);
	}
	
}
