/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.mask.IUpdateTypeComponent;

public final class PartySmallWindowUpdateType
extends Enum<PartySmallWindowUpdateType>
implements IUpdateTypeComponent {
    public static final /* enum */ PartySmallWindowUpdateType CURRENT_CP = new PartySmallWindowUpdateType(1);
    public static final /* enum */ PartySmallWindowUpdateType MAX_CP = new PartySmallWindowUpdateType(2);
    public static final /* enum */ PartySmallWindowUpdateType CURRENT_HP = new PartySmallWindowUpdateType(4);
    public static final /* enum */ PartySmallWindowUpdateType MAX_HP = new PartySmallWindowUpdateType(8);
    public static final /* enum */ PartySmallWindowUpdateType CURRENT_MP = new PartySmallWindowUpdateType(16);
    public static final /* enum */ PartySmallWindowUpdateType MAX_MP = new PartySmallWindowUpdateType(32);
    public static final /* enum */ PartySmallWindowUpdateType LEVEL = new PartySmallWindowUpdateType(64);
    public static final /* enum */ PartySmallWindowUpdateType CLASS_ID = new PartySmallWindowUpdateType(128);
    public static final /* enum */ PartySmallWindowUpdateType PARTY_SUBSTITUTE = new PartySmallWindowUpdateType(256);
    public static final /* enum */ PartySmallWindowUpdateType VITALITY_POINTS = new PartySmallWindowUpdateType(512);
    public static final PartySmallWindowUpdateType[] VALUES;
    private final int zR;
    private static final /* synthetic */ PartySmallWindowUpdateType[] a;

    public static PartySmallWindowUpdateType[] values() {
        return (PartySmallWindowUpdateType[])a.clone();
    }

    public static PartySmallWindowUpdateType valueOf(String string) {
        return Enum.valueOf(PartySmallWindowUpdateType.class, string);
    }

    private PartySmallWindowUpdateType(int n2) {
        this.zR = n2;
    }

    @Override
    public int getMask() {
        return this.zR;
    }

    private static /* synthetic */ PartySmallWindowUpdateType[] a() {
        return new PartySmallWindowUpdateType[]{CURRENT_CP, MAX_CP, CURRENT_HP, MAX_HP, CURRENT_MP, MAX_MP, LEVEL, CLASS_ID, PARTY_SUBSTITUTE, VITALITY_POINTS};
    }

    static {
        a = PartySmallWindowUpdateType.a();
        VALUES = PartySmallWindowUpdateType.values();
    }
}
