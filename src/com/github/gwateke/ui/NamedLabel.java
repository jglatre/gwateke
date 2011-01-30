package com.github.gwateke.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasName;


/**
 * Etiqueta con nombre para ser usada como campo de sólo lectura.
 */
public class NamedLabel extends HTML implements HasName {
	
	private String name;
	
	public NamedLabel() {
	}
	
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
}