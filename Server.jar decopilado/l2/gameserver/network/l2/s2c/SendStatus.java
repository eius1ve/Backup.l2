/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public final class SendStatus
extends L2GameServerPacket {
    private static final long dy = 30000L;
    private static int Bd = 0;
    private static int Be = 0;
    private static int Bf = 0;
    private static long dz = 0L;

    public SendStatus() {
        int n = 0;
        int n2 = 0;
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null) continue;
            ++n;
            if (!player.isInStoreMode() || Config.SENDSTATUS_TRADE_JUST_OFFLINE && !player.isInOfflineMode()) continue;
            ++n2;
        }
        Bd = (int)((double)n * Config.MUL_PLAYERS_ONLINE);
        Bf = (int)Math.floor((double)n2 * Config.SENDSTATUS_TRADE_MOD);
        Be = Math.max(Be, Bd);
    }

    @Override
    protected final void writeImpl() {
        if (System.currentTimeMillis() - dz < 30000L) {
            return;
        }
        dz = System.currentTimeMillis();
        this.writeC(46);
        this.writeD(1);
        this.writeD(Be);
        this.writeD(Bd);
        this.writeD(Bd);
        this.writeD(Bf);
        this.writeH(48);
        this.writeH(44);
        this.writeH(53);
        this.writeH(49);
        this.writeH(48);
        this.writeH(44);
        this.writeH(55);
        this.writeH(55);
        this.writeH(55);
        this.writeH(53);
        this.writeH(56);
        this.writeH(44);
        this.writeH(54);
        this.writeH(53);
        this.writeH(48);
        this.writeD(54);
        this.writeD(119);
        this.writeD(183);
        this.writeQ(159L);
        this.writeD(0);
        this.writeH(65);
        this.writeH(117);
        this.writeH(103);
        this.writeH(32);
        this.writeH(50);
        this.writeH(57);
        this.writeH(32);
        this.writeH(50);
        this.writeH(48);
        this.writeH(48);
        this.writeD(57);
        this.writeH(48);
        this.writeH(50);
        this.writeH(58);
        this.writeH(52);
        this.writeH(48);
        this.writeH(58);
        this.writeH(52);
        this.writeD(51);
        this.writeD(87);
        this.writeC(17);
        this.writeC(93);
        this.writeC(31);
        this.writeC(96);
    }
}
