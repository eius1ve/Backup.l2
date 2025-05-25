/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.mask.IUpdateTypeComponent;

public final class NpcInfoType
extends Enum<NpcInfoType>
implements IUpdateTypeComponent {
    public static final /* enum */ NpcInfoType ID = new NpcInfoType(0, 4);
    public static final /* enum */ NpcInfoType ATTACKABLE = new NpcInfoType(1, 1);
    public static final /* enum */ NpcInfoType UNKNOWN1 = new NpcInfoType(2, 4);
    public static final /* enum */ NpcInfoType NAME = new NpcInfoType(3, 2);
    public static final /* enum */ NpcInfoType POSITION = new NpcInfoType(4, 12);
    public static final /* enum */ NpcInfoType HEADING = new NpcInfoType(5, 4);
    public static final /* enum */ NpcInfoType UNKNOWN2 = new NpcInfoType(6, 4);
    public static final /* enum */ NpcInfoType ATK_CAST_SPEED = new NpcInfoType(7, 8);
    public static final /* enum */ NpcInfoType SPEED_MULTIPLIER = new NpcInfoType(8, 8);
    public static final /* enum */ NpcInfoType EQUIPPED = new NpcInfoType(9, 12);
    public static final /* enum */ NpcInfoType ALIVE = new NpcInfoType(10, 1);
    public static final /* enum */ NpcInfoType RUNNING = new NpcInfoType(11, 1);
    public static final /* enum */ NpcInfoType SWIM_OR_FLY = new NpcInfoType(14, 1);
    public static final /* enum */ NpcInfoType TEAM = new NpcInfoType(15, 1);
    public static final /* enum */ NpcInfoType ENCHANT = new NpcInfoType(16, 4);
    public static final /* enum */ NpcInfoType FLYING = new NpcInfoType(17, 4);
    public static final /* enum */ NpcInfoType CLONE = new NpcInfoType(18, 4);
    public static final /* enum */ NpcInfoType COLOR_EFFECT = new NpcInfoType(19, 4);
    public static final /* enum */ NpcInfoType DISPLAY_EFFECT = new NpcInfoType(22, 4);
    public static final /* enum */ NpcInfoType TRANSFORMATION = new NpcInfoType(23, 4);
    public static final /* enum */ NpcInfoType CURRENT_HP = new NpcInfoType(24, 4);
    public static final /* enum */ NpcInfoType CURRENT_MP = new NpcInfoType(25, 4);
    public static final /* enum */ NpcInfoType MAX_HP = new NpcInfoType(26, 4);
    public static final /* enum */ NpcInfoType MAX_MP = new NpcInfoType(27, 4);
    public static final /* enum */ NpcInfoType SUMMONED = new NpcInfoType(28, 1);
    public static final /* enum */ NpcInfoType UNKNOWN12 = new NpcInfoType(29, 8);
    public static final /* enum */ NpcInfoType TITLE = new NpcInfoType(30, 2);
    public static final /* enum */ NpcInfoType NAME_NPCSTRINGID = new NpcInfoType(31, 4);
    public static final /* enum */ NpcInfoType TITLE_NPCSTRINGID = new NpcInfoType(32, 4);
    public static final /* enum */ NpcInfoType PVP_FLAG = new NpcInfoType(33, 1);
    public static final /* enum */ NpcInfoType REPUTATION = new NpcInfoType(34, 4);
    public static final /* enum */ NpcInfoType CLAN = new NpcInfoType(35, 20);
    public static final /* enum */ NpcInfoType ABNORMALS = new NpcInfoType(36, 0);
    public static final /* enum */ NpcInfoType VISUAL_STATE = new NpcInfoType(37, 1);
    public static final NpcInfoType[] VALUES;
    private final int zK;
    private final int zL;
    private static final /* synthetic */ NpcInfoType[] a;

    public static NpcInfoType[] values() {
        return (NpcInfoType[])a.clone();
    }

    public static NpcInfoType valueOf(String string) {
        return Enum.valueOf(NpcInfoType.class, string);
    }

    private NpcInfoType(int n2, int n3) {
        this.zK = n2;
        this.zL = n3;
    }

    @Override
    public int getMask() {
        return this.zK;
    }

    public int getBlockLength() {
        return this.zL;
    }

    private static /* synthetic */ NpcInfoType[] a() {
        return new NpcInfoType[]{ID, ATTACKABLE, UNKNOWN1, NAME, POSITION, HEADING, UNKNOWN2, ATK_CAST_SPEED, SPEED_MULTIPLIER, EQUIPPED, ALIVE, RUNNING, SWIM_OR_FLY, TEAM, ENCHANT, FLYING, CLONE, COLOR_EFFECT, DISPLAY_EFFECT, TRANSFORMATION, CURRENT_HP, CURRENT_MP, MAX_HP, MAX_MP, SUMMONED, UNKNOWN12, TITLE, NAME_NPCSTRINGID, TITLE_NPCSTRINGID, PVP_FLAG, REPUTATION, CLAN, ABNORMALS, VISUAL_STATE};
    }

    static {
        a = NpcInfoType.a();
        VALUES = NpcInfoType.values();
    }
}
