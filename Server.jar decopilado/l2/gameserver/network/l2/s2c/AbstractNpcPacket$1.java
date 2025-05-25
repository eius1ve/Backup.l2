/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.NpcInfoType;

static class AbstractNpcPacket.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$network$l2$s2c$NpcInfoType;

    static {
        $SwitchMap$l2$gameserver$network$l2$s2c$NpcInfoType = new int[NpcInfoType.values().length];
        try {
            AbstractNpcPacket.1.$SwitchMap$l2$gameserver$network$l2$s2c$NpcInfoType[NpcInfoType.ATTACKABLE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractNpcPacket.1.$SwitchMap$l2$gameserver$network$l2$s2c$NpcInfoType[NpcInfoType.UNKNOWN1.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractNpcPacket.1.$SwitchMap$l2$gameserver$network$l2$s2c$NpcInfoType[NpcInfoType.TITLE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractNpcPacket.1.$SwitchMap$l2$gameserver$network$l2$s2c$NpcInfoType[NpcInfoType.NAME.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
