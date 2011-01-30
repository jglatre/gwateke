package com.github.gwateke.context;

import com.google.gwt.i18n.client.Dictionary;


public class DictionaryMessageSource extends AbstractMessageSource {

	private final Dictionary dictionary;

	
	public DictionaryMessageSource(String dictionaryName) {
		dictionary = Dictionary.getDictionary(dictionaryName);
	}
	
	
	@Override
	public String getRequiredMessage(String key) {
		return dictionary.get(key);
	}

}
