package com.github.gwateke.ui.form;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.constants.NumberConstants;
import com.google.gwt.user.client.ui.HasValue;


/**
 * Filtro de teclado que sólo acepta dígitos y un separador decimal.
 * 
 * @author juanjogarcia
 */
public class DecimalDigitKeyboardFilter extends DigitKeyboardFilter {

	private static final NumberConstants defaultNumberConstants = LocaleInfo.getCurrentLocale().getNumberConstants();
	private static final char DECIMAL_SEPARATOR = defaultNumberConstants.decimalSeparator().charAt(0); 

	
	@Override
	protected boolean isValid(HasValue<String> widget, char keyCode) {
		return super.isValid(widget, keyCode) || 
				(keyCode == DECIMAL_SEPARATOR && widget.getValue().indexOf(DECIMAL_SEPARATOR) == -1);
	}	
	
}
