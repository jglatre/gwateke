package com.github.gwateke.ui.form.support;

import com.github.gwateke.ui.form.KeyboardFilter;
import com.google.gwt.user.client.ui.HasValue;


public class DigitKeyboardFilter extends KeyboardFilter {

	@Override
	protected boolean isValid(HasValue<String> widget, char keyCode) {
		return Character.isDigit(keyCode);
	}

}
