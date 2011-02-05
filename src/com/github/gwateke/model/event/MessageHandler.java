package com.github.gwateke.model.event;

import com.google.gwt.event.shared.EventHandler;


/**
 * Interface que han de implementar los componentes interesados
 * en recibir los mensajes para el usuario generados por un
 * <code>DataManagementModel</code>. 
 * 
 * @author juanjo
 *
 */
public interface MessageHandler extends EventHandler {

	void onMessage(MessageEvent event);
}
