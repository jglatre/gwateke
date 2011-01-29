package java.util;


public abstract class EventListenerProxy implements EventListener {
    private final EventListener listener;

    /**
     * @param listener The listener object.
     */ 
    public EventListenerProxy(EventListener listener) {
        this.listener = listener;
    }

    /**
     * @return The listener associated with this proxy.
     */
    public EventListener getListener() {
        return listener;
    }
}