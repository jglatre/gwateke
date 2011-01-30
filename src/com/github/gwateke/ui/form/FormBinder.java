package com.github.gwateke.ui.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.adapter.Bindings;
import com.github.gwateke.binding.adapter.CheckBoxAdapter;
import com.github.gwateke.binding.adapter.DateBoxAdapter;
import com.github.gwateke.binding.adapter.HiddenAdapter;
import com.github.gwateke.binding.adapter.HtmlAdapter;
import com.github.gwateke.binding.adapter.RadioButtonGroupAdapter;
import com.github.gwateke.binding.adapter.SuggestBoxAdapter;
import com.github.gwateke.binding.form.FormModel;
import com.github.gwateke.ui.RadioButtonGroup;
import com.github.gwateke.ui.list.model.ListModel;
import com.github.gwateke.ui.list.model.ListModelResolver;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;


public class FormBinder implements FieldVisitor {

	protected final Log log = LogFactory.getLog(getClass());
	
	private Map<String, Binding<?>> bindings = new HashMap<String, Binding<?>>();
	private FormModel<?> formModel;
	private ListModelResolver listModelResolver;


	public FormBinder(FormModel<?> formModel, ListModelResolver listModelResolver) {
		this.formModel = formModel;
		this.listModelResolver = listModelResolver;
	}

	
	public void visit(Widget field) {
		if (field instanceof HasName) {
			final String name = ((HasName) field).getName();
			if (formModel.hasValueModel(name)) {
				Binding<?> binding = bind(name, field);
				if (binding != null) {
					log.debug(name + " bound");
					bindings.put( name, binding );
				}
			}
		}			
	}

	//----------------------------------------------------------------------------------------------
	
	protected Binding<?> bind(String name, Widget widget) {
		Binding<?> binding = null;

		if (widget instanceof TextBoxBase) {
			binding = Bindings.bind((TextBoxBase) widget, formModel.getValueModel(name, String.class));
		}
		else if (widget instanceof ListBox) {
			ListModel<?, ?> listModel = listModelResolver.getListModel(name);
			if (listModel != null) {
				binding = Bindings.bind((ListBox) widget, formModel.getValueModel(name), listModel);
			}
			else {
				binding = Bindings.bind((ListBox) widget, formModel.getValueModel(name));
			}
		}
		else if (widget instanceof CheckBox) {
			binding = new CheckBoxAdapter((CheckBox) widget, formModel.getValueModel(name, Boolean.class));
		}
		else if (widget instanceof HTML) {
			binding = new HtmlAdapter((HTML) widget, formModel.getValueModel(name, String.class));
		}
		else if (widget instanceof Label) {
			binding = Bindings.bind((Label) widget, formModel.getValueModel(name, String.class));
		}
		else if (widget instanceof Hidden) {
			binding = new HiddenAdapter((Hidden) widget, formModel.getValueModel(name, String.class));
		}
		else if (widget instanceof DateBox) {
			binding = new DateBoxAdapter((DateBox) widget, formModel.getValueModel(name, Date.class));
		}
		else if (widget instanceof SuggestBox) {
			binding = new SuggestBoxAdapter((SuggestBox) widget, formModel.getValueModel(name, String.class));
		}
		else if (widget instanceof RadioButtonGroup) {
			binding = new RadioButtonGroupAdapter((RadioButtonGroup) widget, formModel.getValueModel(name));
		}

		return binding;
	}

}
