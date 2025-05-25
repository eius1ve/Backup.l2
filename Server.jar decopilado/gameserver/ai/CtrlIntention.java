/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

public final class CtrlIntention
extends Enum<CtrlIntention> {
    public static final /* enum */ CtrlIntention AI_INTENTION_IDLE = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_ACTIVE = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_REST = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_ATTACK = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_CAST = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_PICK_UP = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_INTERACT = new CtrlIntention();
    public static final /* enum */ CtrlIntention AI_INTENTION_FOLLOW = new CtrlIntention();
    private static final /* synthetic */ CtrlIntention[] a;

    public static CtrlIntention[] values() {
        return (CtrlIntention[])a.clone();
    }

    public static CtrlIntention valueOf(String string) {
        return Enum.valueOf(CtrlIntention.class, string);
    }

    private static /* synthetic */ CtrlIntention[] a() {
        return new CtrlIntention[]{AI_INTENTION_IDLE, AI_INTENTION_ACTIVE, AI_INTENTION_REST, AI_INTENTION_ATTACK, AI_INTENTION_CAST, AI_INTENTION_PICK_UP, AI_INTENTION_INTERACT, AI_INTENTION_FOLLOW};
    }

    static {
        a = CtrlIntention.a();
    }
}
