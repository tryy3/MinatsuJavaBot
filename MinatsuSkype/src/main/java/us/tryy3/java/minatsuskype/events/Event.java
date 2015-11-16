package us.tryy3.java.minatsuskype.events;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public abstract class Event {
    private HashMap<Method, Class<?>> registeredEvents;

    public void registerEvents(Listener listener) {
        Class<?> clazz = listener.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Parameter[] parameters = methods[i].getParameters();
            for (int p = 0; p < parameters.length; p++) {
                if (parameters[p].getType().equals(Listener.class)) {
                    registeredEvents.put(methods[i], parameters[p].getType());
                }
            }
        }
    }

    public void callEvents(Event event) {
        try {
            for (Map.Entry<Method, Class<?>> entry : registeredEvents.entrySet()) {
                if (entry.getValue().equals(event.getClass())) {
                    entry.getKey().invoke(event);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
