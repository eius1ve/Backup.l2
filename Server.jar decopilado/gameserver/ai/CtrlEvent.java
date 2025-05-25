/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

public final class CtrlEvent
extends Enum<CtrlEvent> {
    public static final /* enum */ CtrlEvent EVT_THINK = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_ATTACKED = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_CLAN_ATTACKED = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_AGGRESSION = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_READY_TO_ACT = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_ARRIVED = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_ARRIVED_TARGET = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_ARRIVED_BLOCKED = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_FORGET_OBJECT = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_DEAD = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_FAKE_DEATH = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_FINISH_CASTING = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_SEE_SPELL = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_SPAWN = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_DESPAWN = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_TIMER = new CtrlEvent();
    public static final /* enum */ CtrlEvent EVT_TELEPORTED = new CtrlEvent();
    private static final /* synthetic */ CtrlEvent[] a;

    public static CtrlEvent[] values() {
        return (CtrlEvent[])a.clone();
    }

    public static CtrlEvent valueOf(String string) {
        return Enum.valueOf(CtrlEvent.class, string);
    }

    private static /* synthetic */ CtrlEvent[] a() {
        return new CtrlEvent[]{EVT_THINK, EVT_ATTACKED, EVT_CLAN_ATTACKED, EVT_AGGRESSION, EVT_READY_TO_ACT, EVT_ARRIVED, EVT_ARRIVED_TARGET, EVT_ARRIVED_BLOCKED, EVT_FORGET_OBJECT, EVT_DEAD, EVT_FAKE_DEATH, EVT_FINISH_CASTING, EVT_SEE_SPELL, EVT_SPAWN, EVT_DESPAWN, EVT_TIMER, EVT_TELEPORTED};
    }

    static {
        a = CtrlEvent.a();
    }
}
