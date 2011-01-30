package com.github.gwateke.ui;

import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;


public class HoveringStyler extends MouseListenerAdapter {

	public static final String DEFAULT_STYLE_NAME = "hovering";
	
	private UIObject target;
	private String styleName = DEFAULT_STYLE_NAME;
	
	
	public HoveringStyler() {
		this(null, DEFAULT_STYLE_NAME);
	}
	
	
	public HoveringStyler(UIObject target) {
		this(target, DEFAULT_STYLE_NAME);
	}
	
	
	public HoveringStyler(UIObject target, String styleName) {
		this.target = target;
		this.styleName = styleName;
	}
	
	
	public void onMouseEnter(Widget sender) {
		(target != null ? target : sender).addStyleDependentName(styleName);
	}
	
	
	public void onMouseLeave(Widget sender) {
		(target != null ? target : sender).removeStyleDependentName(styleName);
	}

}
