/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.s2c.Earthquake
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SocialAction
 */
package bosses;

import bosses.EpicBossState;
import bosses.FrintezzaManager;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SocialAction;

private class FrintezzaManager.Spawn
extends RunnableImpl {
    private int bp = 0;

    public FrintezzaManager.Spawn(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    FrintezzaManager.this.g = FrintezzaManager.this.a(new FrintezzaManager.NpcLocation(174232, -89816, -5016, 16048, 29059));
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(2)), 1000L);
                    break;
                }
                case 2: {
                    FrintezzaManager.this._state.setRespawnDate(FrintezzaManager.a());
                    FrintezzaManager.this._state.setState(EpicBossState.State.ALIVE);
                    FrintezzaManager.this._state.update();
                    FrintezzaManager.this.a.compareAndSet(4, 5);
                    FrintezzaManager.this.b(FrintezzaManager.this.A);
                    FrintezzaManager.this.h = FrintezzaManager.this.a(a);
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 500, 90, 0, 6500, 8000, 0);
                    for (int i = 0; i < 4; ++i) {
                        FrintezzaManager.this.a[i] = FrintezzaManager.this.a(a[i]);
                        FrintezzaManager.this.a[i].startImmobilized();
                        FrintezzaManager.this.b[i] = FrintezzaManager.this.a(b[i]);
                    }
                    FrintezzaManager.this.a(true);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(3)), 6500L);
                    break;
                }
                case 3: {
                    FrintezzaManager.this.a(FrintezzaManager.this.g, 1800, 90, 8, 6500, 7000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(4)), 900L);
                    break;
                }
                case 4: {
                    FrintezzaManager.this.a(FrintezzaManager.this.g, 140, 90, 10, 2500, 4500, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(5)), 4000L);
                    break;
                }
                case 5: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 40, 75, -10, 0, 1000, 0);
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 40, 75, -10, 0, 12000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(6)), 1350L);
                    break;
                }
                case 6: {
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.h.getObjectId(), 2)});
                    FrintezzaManager.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Music(FrintezzaManager.this, 0)), 5000L);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(7)), 7000L);
                    break;
                }
                case 7: {
                    FrintezzaManager.this.g.deleteMe();
                    FrintezzaManager.this.g = null;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(8)), 1000L);
                    break;
                }
                case 8: {
                    FrintezzaManager.this.a(FrintezzaManager.this.b[0], 140, 0, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(9)), 2800L);
                    break;
                }
                case 9: {
                    FrintezzaManager.this.a(FrintezzaManager.this.b[1], 140, 0, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(10)), 2800L);
                    break;
                }
                case 10: {
                    FrintezzaManager.this.a(FrintezzaManager.this.b[2], 140, 180, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(11)), 2800L);
                    break;
                }
                case 11: {
                    FrintezzaManager.this.a(FrintezzaManager.this.b[3], 140, 180, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(12)), 3000L);
                    break;
                }
                case 12: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 240, 90, 0, 0, 1000, 0);
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 240, 90, 25, 5500, 10000, 3);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(13)), 3000L);
                    break;
                }
                case 13: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 195, 35, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(14)), 700L);
                    break;
                }
                case 14: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 100, 195, 35, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(15)), 1300L);
                    break;
                }
                case 15: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 120, 180, 45, 1500, 10000, 0);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5006, 1, 34000, 0L)});
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(16)), 1500L);
                    break;
                }
                case 16: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 520, 135, 45, 8000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(17)), 7500L);
                    break;
                }
                case 17: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 1500, 110, 25, 10000, 13000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(18)), 9500L);
                    break;
                }
                case 18: {
                    FrintezzaManager.this.i = FrintezzaManager.this.a(b);
                    FrintezzaManager.this.a(FrintezzaManager.this.i, true);
                    FrintezzaManager.this.i.addListener((Listener)FrintezzaManager.this.a);
                    FrintezzaManager.this.i.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.i, (Creature)FrintezzaManager.this.i, 5016, 1, 3000, 0L)});
                    FrintezzaManager.this.f.broadcastPacket((L2GameServerPacket)new Earthquake(FrintezzaManager.this.i.getLoc(), 50, 6), false);
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 1000, 160, 20, 6000, 6000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(19)), 5500L);
                    break;
                }
                case 19: {
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 800, 160, 5, 1000, 10000, 2);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(20)), 2100L);
                    break;
                }
                case 20: {
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 300, 60, 8, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(21)), 2000L);
                    break;
                }
                case 21: {
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 1000, 90, 10, 3000, 5000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(22)), 3000L);
                    break;
                }
                case 22: {
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Spawn(23)), 2000L);
                    for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                        player.leaveMovieMode();
                    }
                    break;
                }
                case 23: {
                    FrintezzaManager.this.a(false);
                    FrintezzaManager.this.bu = 1;
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
