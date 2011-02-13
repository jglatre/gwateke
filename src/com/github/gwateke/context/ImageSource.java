package com.github.gwateke.context;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public interface ImageSource extends ClientBundle {		
	ImageResource upArrow();	
	ImageResource downArrow();	
	ImageResource previous();	
	ImageResource next();
	ImageResource first();	
	ImageResource last();			
}
