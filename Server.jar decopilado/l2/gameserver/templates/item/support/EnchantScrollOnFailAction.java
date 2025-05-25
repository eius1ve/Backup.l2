/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public final class EnchantScrollOnFailAction
extends Enum<EnchantScrollOnFailAction> {
    public static final /* enum */ EnchantScrollOnFailAction NONE = new EnchantScrollOnFailAction();
    public static final /* enum */ EnchantScrollOnFailAction CRYSTALIZE = new EnchantScrollOnFailAction();
    public static final /* enum */ EnchantScrollOnFailAction RESET = new EnchantScrollOnFailAction();
    private static final /* synthetic */ EnchantScrollOnFailAction[] a;

    public static EnchantScrollOnFailAction[] values() {
        return (EnchantScrollOnFailAction[])a.clone();
    }

    public static EnchantScrollOnFailAction valueOf(String string) {
        return Enum.valueOf(EnchantScrollOnFailAction.class, string);
    }

    private static /* synthetic */ EnchantScrollOnFailAction[] a() {
        return new EnchantScrollOnFailAction[]{NONE, CRYSTALIZE, RESET};
    }

    static {
        a = EnchantScrollOnFailAction.a();
    }
}
