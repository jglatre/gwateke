package com.github.gwateke.binding;


/**
 * @author juanjo
 */
public interface Binding<W> {

	W getWidget();
	void unbind();
}
