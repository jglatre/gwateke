package com.github.gwateke.model.event;

import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.context.support.DefaultMessageSourceResolvable;
import com.google.gwt.event.shared.GwtEvent;


/**
 * Mensaje para el usuario que puede generar un DataManagementModel.
 * 
 * Existen tres tipos de mensajes según como se cierren:
 * 1. cierre por el sistema, requiere dos eventos, un OPEN y un CLOSE
 * 2. cierre automático, por timeout (tipo AUTO)
 * 3. cierre por el usuario (tipo MODAL)
 * 
 * @author juanjogarcia
 */
public class MessageEvent extends GwtEvent<MessageHandler> {
	
	private static final Type<MessageHandler> TYPE = new Type<MessageHandler>();
	
	public static Type<MessageHandler> getType() {
		return TYPE;
	}

	
	public enum MessageType {
		OPEN, 
		CLOSE, 
		AUTO,
		MODAL
	}
	
	private final MessageSourceResolvable resolvable;
	private final MessageType messageType;

	
	public MessageEvent(String messageCode) {
		this(messageCode, MessageType.AUTO);
	}
	
	
	public MessageEvent(MessageType type) {
		this((MessageSourceResolvable) null, type);
	}
	
	
	public MessageEvent(String messageCode, MessageType type) {
		this(new DefaultMessageSourceResolvable(messageCode), type);
	}
	
	
	public MessageEvent(MessageSourceResolvable resolvable, MessageType messageType) {
		this.resolvable = resolvable;
		this.messageType = messageType;
	}
	
	
	public MessageSourceResolvable getMessageSourceResolvable() {
		return resolvable;
	}
	
	
	public MessageType getMessageType() {
		return messageType;
	}


	@Override
	protected void dispatch(MessageHandler handler) {
		handler.onMessage(this);
	}


	@Override
	public Type<MessageHandler> getAssociatedType() {
		return getType();
	}
}
