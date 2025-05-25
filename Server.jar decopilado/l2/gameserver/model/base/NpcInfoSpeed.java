/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.network.l2.s2c.mask.IUpdateTypeComponent;

public final class NpcInfoSpeed
extends Enum<NpcInfoSpeed>
implements IUpdateTypeComponent {
    public static final /* enum */ NpcInfoSpeed MOVE_SPEED_MUL = new NpcInfoSpeed();
    public static final /* enum */ NpcInfoSpeed ATTACK_SPEED_MUL = new NpcInfoSpeed();
    public static final /* enum */ NpcInfoSpeed CAST_M_SKILL_SPEED = new NpcInfoSpeed();
    public static final /* enum */ NpcInfoSpeed CAST_P_SKILL_SPEED = new NpcInfoSpeed();
    private static final /* synthetic */ NpcInfoSpeed[] a;

    public static NpcInfoSpeed[] values() {
        return (NpcInfoSpeed[])a.clone();
    }

    public static NpcInfoSpeed valueOf(String string) {
        return Enum.valueOf(NpcInfoSpeed.class, string);
    }

    @Override
    public int getMask() {
        return this.ordinal();
    }

    private static /* synthetic */ NpcInfoSpeed[] a() {
        return new NpcInfoSpeed[]{MOVE_SPEED_MUL, ATTACK_SPEED_MUL, CAST_M_SKILL_SPEED, CAST_P_SKILL_SPEED};
    }

    static {
        a = NpcInfoSpeed.a();
    }
}
