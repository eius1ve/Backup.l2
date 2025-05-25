/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

public final class NextAction
extends Enum<NextAction> {
    public static final /* enum */ NextAction ATTACK = new NextAction();
    public static final /* enum */ NextAction CAST = new NextAction();
    public static final /* enum */ NextAction MOVE = new NextAction();
    public static final /* enum */ NextAction REST = new NextAction();
    public static final /* enum */ NextAction PICKUP = new NextAction();
    public static final /* enum */ NextAction INTERACT = new NextAction();
    public static final /* enum */ NextAction EQUIP = new NextAction();
    private static final /* synthetic */ NextAction[] a;

    public static NextAction[] values() {
        return (NextAction[])a.clone();
    }

    public static NextAction valueOf(String string) {
        return Enum.valueOf(NextAction.class, string);
    }

    private static /* synthetic */ NextAction[] a() {
        return new NextAction[]{ATTACK, CAST, MOVE, REST, PICKUP, INTERACT, EQUIP};
    }

    static {
        a = NextAction.a();
    }
}
