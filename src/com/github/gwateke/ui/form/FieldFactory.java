package com.github.gwateke.ui.form;


import com.github.gwateke.binding.form.FieldMetadata;
import com.github.gwateke.context.MessageSource;
import com.google.gwt.user.client.ui.Widget;


public interface FieldFactory {
	Widget createWidget(FieldMetadata field, MessageSource messageSource);
}
