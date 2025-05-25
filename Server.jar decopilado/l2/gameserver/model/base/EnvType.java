/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class EnvType
extends Enum<EnvType> {
    public static final /* enum */ EnvType GROUND = new EnvType();
    public static final /* enum */ EnvType WATER = new EnvType();
    public static final /* enum */ EnvType AIR = new EnvType();
    public static final /* enum */ EnvType HOVER = new EnvType();
    private static final /* synthetic */ EnvType[] a;

    public static EnvType[] values() {
        return (EnvType[])a.clone();
    }

    public static EnvType valueOf(String string) {
        return Enum.valueOf(EnvType.class, string);
    }

    private static /* synthetic */ EnvType[] a() {
        return new EnvType[]{GROUND, WATER, AIR, HOVER};
    }

    static {
        a = EnvType.a();
    }
}
