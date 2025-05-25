/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import l2.gameserver.instancemanager.sepulchers.event.EventData;
import l2.gameserver.instancemanager.sepulchers.event.SepulcherEvent;

public class EventHandler {
    private final Map<Class<? extends SepulcherEvent>, ConcurrentLinkedQueue<EventData>> as = new ConcurrentHashMap<Class<? extends SepulcherEvent>, ConcurrentLinkedQueue<EventData>>();

    public <E extends SepulcherEvent> void add(Class<E> clazz2, E e, boolean bl) {
        this.as.computeIfAbsent(clazz2, clazz -> new ConcurrentLinkedQueue()).add(new EventData(e, bl));
    }

    public <E extends SepulcherEvent> void add(Class<E> clazz, E e) {
        this.add(clazz, e, true);
    }

    public <E extends SepulcherEvent> ConcurrentLinkedQueue<EventData> getEvents(Class<E> clazz2) {
        return this.as.computeIfAbsent(clazz2, clazz -> new ConcurrentLinkedQueue());
    }

    public <E extends SepulcherEvent> void trigger(Class<E> clazz, Consumer<E> consumer) {
        ConcurrentLinkedQueue<EventData> concurrentLinkedQueue = this.getEvents(clazz);
        concurrentLinkedQueue.forEach(eventData -> {
            if (eventData.isSingleCall()) {
                if (eventData.getCallFlag().compareAndSet(false, true)) {
                    consumer.accept(eventData.getEvent());
                }
            } else {
                consumer.accept(eventData.getEvent());
            }
        });
    }

    public <E extends SepulcherEvent> void remove(E e) {
        this.as.values().forEach(concurrentLinkedQueue -> concurrentLinkedQueue.removeIf(eventData -> eventData.getEvent() == e));
    }

    public void reset() {
        this.as.values().forEach(concurrentLinkedQueue -> concurrentLinkedQueue.forEach(eventData -> eventData.getCallFlag().set(false)));
    }
}
