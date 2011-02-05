package com.github.gwateke.ui.list;

import java.util.Map;

import com.github.gwateke.model.list.ListModel;
import com.github.gwateke.model.list.ListModelDependent;
import com.github.gwateke.model.list.support.ListUtils;
import com.github.gwateke.ui.NamedLabel;



/**
 * Etiqueta que muestra una lista de abreviaturas separadas por espacios. Las abreviaturas
 * se resuelven como valores clave en un ListModel para crear los correspondientes "tool tips".
 * 
 * @author juanjogarcia
 */
public class AbbreviationList extends NamedLabel implements ListModelDependent {

	private Map<?, ?> model;
	
	
	public void setListModel(ListModel<?, ?> model) {
		this.model = ListUtils.asMap(model);
	}
	
	
	@Override
	public void setHTML(String text) {
		String html = "";
		
		if (model != null) {
			for (String name : text.split("\\s+") ) {
				if (model.containsKey(name)) {
					html += "<abbr title=\"" + model.get(name) + "\">" + name + "</abbr>";
				}
				else {
					html += name;
				}
				html += "&nbsp;";
			}
		}
		else {
			html = text;
		}

		super.setHTML(html);
	}

}
