/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.listener;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
import l2.commons.listener.EventListener;
import l2.commons.listener.Listener;

public class ListenerList<T> {
    protected Map<Class<?>, Set<Listener<T>>> listeners = new ConcurrentHashMap();
    protected Map<String, Set<EventListener>> eventListeners = new ConcurrentHashMap<String, Set<EventListener>>();

    public Collection<Listener<T>> getListeners() {
        throw new UnsupportedOperationException();
    }

    public <ListenerT extends Listener<T>> Collection<ListenerT> getListeners(Class<ListenerT> clazz) {
        return this.listeners.getOrDefault(clazz, Collections.emptySet());
    }

    public Set<EventListener> getEventListeners(String string) {
        return this.eventListeners.getOrDefault(string, Collections.emptySet());
    }

    public <ListenerT extends Listener<T>> void forEachListener(Class<ListenerT> clazz, Consumer<ListenerT> consumer) {
        this.getListeners(clazz).forEach(consumer);
    }

    public void forEachEventListener(String string, Consumer<EventListener> consumer) {
        this.getEventListeners(string).forEach(consumer);
    }

    public void fireEvent(String string, Object ... objectArray) {
        this.forEachEventListener(string, eventListener -> {
            try {
                eventListener.onEvent(string, objectArray);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        });
    }

    public void addEventListener(EventListener eventListener) {
        for (String string2 : eventListener.listeningEventTypes()) {
            this.eventListeners.computeIfAbsent(string2, string -> new CopyOnWriteArraySet()).add(eventListener);
        }
    }

    public boolean add(Listener<T> listener) {
        boolean bl = false;
        for (Class<?> clazz2 = listener.getClass(); clazz2 != null && clazz2 != Object.class; clazz2 = clazz2.getSuperclass()) {
            for (Class<?> clazz3 : clazz2.getDeclaredClasses()) {
                if (!this.listeners.computeIfAbsent(clazz3, clazz -> new CopyOnWriteArraySet()).add(listener)) continue;
                bl = true;
            }
            for (Class<?> clazz3 : clazz2.getInterfaces()) {
                if (!this.listeners.computeIfAbsent(clazz3, clazz -> new CopyOnWriteArraySet()).add(listener)) continue;
                bl = true;
            }
            if (!this.listeners.computeIfAbsent(clazz2, clazz -> new CopyOnWriteArraySet()).add(listener)) continue;
            bl = true;
        }
        return bl;
    }

    public boolean remove(Listener<T> listener) {
        boolean bl = false;
        for (Class<?> clazz = listener.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Class<?> clazz2 : clazz.getDeclaredClasses()) {
                if (!this.listeners.getOrDefault(clazz2, Collections.emptySet()).remove(listener)) continue;
                bl = true;
            }
            for (Class<?> clazz2 : clazz.getInterfaces()) {
                if (!this.listeners.getOrDefault(clazz2, Collections.emptySet()).remove(listener)) continue;
                bl = true;
            }
            if (!this.listeners.getOrDefault(clazz, Collections.emptySet()).remove(listener)) continue;
            bl = true;
        }
        return bl;
    }
}
