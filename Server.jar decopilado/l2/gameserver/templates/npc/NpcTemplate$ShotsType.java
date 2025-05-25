/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.npc;

public static final class NpcTemplate.ShotsType
extends Enum<NpcTemplate.ShotsType> {
    public static final /* enum */ NpcTemplate.ShotsType NONE = new NpcTemplate.ShotsType();
    public static final /* enum */ NpcTemplate.ShotsType SOUL = new NpcTemplate.ShotsType();
    public static final /* enum */ NpcTemplate.ShotsType SPIRIT = new NpcTemplate.ShotsType();
    public static final /* enum */ NpcTemplate.ShotsType BSPIRIT = new NpcTemplate.ShotsType();
    public static final /* enum */ NpcTemplate.ShotsType SOUL_SPIRIT = new NpcTemplate.ShotsType();
    public static final /* enum */ NpcTemplate.ShotsType SOUL_BSPIRIT = new NpcTemplate.ShotsType();
    private static final /* synthetic */ NpcTemplate.ShotsType[] a;

    public static NpcTemplate.ShotsType[] values() {
        return (NpcTemplate.ShotsType[])a.clone();
    }

    public static NpcTemplate.ShotsType valueOf(String string) {
        return Enum.valueOf(NpcTemplate.ShotsType.class, string);
    }

    private static /* synthetic */ NpcTemplate.ShotsType[] a() {
        return new NpcTemplate.ShotsType[]{NONE, SOUL, SPIRIT, BSPIRIT, SOUL_SPIRIT, SOUL_BSPIRIT};
    }

    static {
        a = NpcTemplate.ShotsType.a();
    }
}
