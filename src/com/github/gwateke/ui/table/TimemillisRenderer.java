package com.github.gwateke.ui.table;

import java.util.Date;



public class TimemillisRenderer extends DateTimeRenderer {

	@Override
	protected String convertToString(Object value) {
		return super.convertToString( new Date(((Number) value).longValue()) );
	}

}
