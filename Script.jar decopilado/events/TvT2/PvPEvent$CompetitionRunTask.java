/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.s2c.ExEventMatchMessage
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.UserInfoType
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.ExEventMatchMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.UserInfoType;

private class PvPEvent.CompetitionRunTask
extends RunnableImpl {
    private int cr;

    public PvPEvent.CompetitionRunTask(int n) {
        this.cr = n;
    }

    public void runImpl() throws Exception {
        switch (this.cr) {
            case 30: {
                PvPEvent.getInstance().a((Runnable)((Object)new PvPEvent.CompetitionRunTask(this.cr - 25)), 25000L);
                return;
            }
            case 0: {
                PvPEvent.getInstance().a(PvPEvent.PvPEventState.COMPETITION, 100L);
                if (PvPEvent.this.X) {
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.START});
                }
                return;
            }
            case 5: {
                if (PvPEvent.this.X) {
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT5});
                }
                for (Player player : PvPEvent.getInstance().getPlayers()) {
                    player.broadcastUserInfo(true, new UserInfoType[0]);
                }
                break;
            }
            case 4: {
                if (!PvPEvent.this.X) break;
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT4});
                break;
            }
            case 3: {
                if (!PvPEvent.this.X) break;
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT3});
                break;
            }
            case 2: {
                if (!PvPEvent.this.X) break;
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT2});
                break;
            }
            case 1: {
                if (!PvPEvent.this.X) break;
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT1});
            }
        }
        PvPEvent.getInstance().a((Runnable)((Object)new PvPEvent.CompetitionRunTask(this.cr - 1)), 1000L);
    }
}
