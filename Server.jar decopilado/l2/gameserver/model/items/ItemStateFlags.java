/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

public final class ItemStateFlags
extends Enum<ItemStateFlags> {
    public static final /* enum */ ItemStateFlags STATE_CHANGED = new ItemStateFlags();
    private static final /* synthetic */ ItemStateFlags[] a;

    public static ItemStateFlags[] values() {
        return (ItemStateFlags[])a.clone();
    }

    public static ItemStateFlags valueOf(String string) {
        return Enum.valueOf(ItemStateFlags.class, string);
    }

    private static /* synthetic */ ItemStateFlags[] a() {
        return new ItemStateFlags[]{STATE_CHANGED};
    }

    static {
        a = ItemStateFlags.a();
    }
}
