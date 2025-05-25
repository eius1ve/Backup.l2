/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events;

public final class EventType
extends Enum<EventType> {
    public static final /* enum */ EventType MAIN_EVENT = new EventType();
    public static final /* enum */ EventType SIEGE_EVENT = new EventType();
    public static final /* enum */ EventType PVP_EVENT = new EventType();
    public static final /* enum */ EventType BOAT_EVENT = new EventType();
    public static final /* enum */ EventType FUN_EVENT = new EventType();
    private int lr = this.ordinal() * 1000;
    private static final /* synthetic */ EventType[] a;

    public static EventType[] values() {
        return (EventType[])a.clone();
    }

    public static EventType valueOf(String string) {
        return Enum.valueOf(EventType.class, string);
    }

    public int step() {
        return this.lr;
    }

    private static /* synthetic */ EventType[] a() {
        return new EventType[]{MAIN_EVENT, SIEGE_EVENT, PVP_EVENT, BOAT_EVENT, FUN_EVENT};
    }

    static {
        a = EventType.a();
    }
}
