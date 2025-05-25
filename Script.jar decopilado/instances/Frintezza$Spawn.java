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
package instances;

import instances.Frintezza;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SocialAction;

private class Frintezza.Spawn
extends RunnableImpl {
    private int bp = 0;

    public Frintezza.Spawn(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    Frintezza.this.g = Frintezza.this.a(new Frintezza.NpcLocation(174232, -89816, -5016, 16048, 29059));
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(2)), 1000L);
                    break;
                }
                case 2: {
                    Frintezza.this.closeDoor(T[1]);
                    Frintezza.this.h = Frintezza.this.a(a);
                    Frintezza.this.a(Frintezza.this.h, 500, 90, 0, 6500, 8000, 0);
                    for (int i = 0; i < 4; ++i) {
                        Frintezza.this.a[i] = Frintezza.this.a(a[i]);
                        Frintezza.this.a[i].startImmobilized();
                        Frintezza.this.b[i] = Frintezza.this.a(b[i]);
                    }
                    Frintezza.this.a(true);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(3)), 6500L);
                    break;
                }
                case 3: {
                    Frintezza.this.a(Frintezza.this.g, 1800, 90, 8, 6500, 7000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(4)), 900L);
                    break;
                }
                case 4: {
                    Frintezza.this.a(Frintezza.this.g, 140, 90, 10, 2500, 4500, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(5)), 4000L);
                    break;
                }
                case 5: {
                    Frintezza.this.a(Frintezza.this.h, 40, 75, -10, 0, 1000, 0);
                    Frintezza.this.a(Frintezza.this.h, 40, 75, -10, 0, 12000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(6)), 1350L);
                    break;
                }
                case 6: {
                    Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(Frintezza.this.h.getObjectId(), 2)});
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(7)), 7000L);
                    break;
                }
                case 7: {
                    Frintezza.this.g.deleteMe();
                    Frintezza.this.g = null;
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(8)), 1000L);
                    break;
                }
                case 8: {
                    Frintezza.this.a(Frintezza.this.b[0], 140, 0, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(9)), 2800L);
                    break;
                }
                case 9: {
                    Frintezza.this.a(Frintezza.this.b[1], 140, 0, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(10)), 2800L);
                    break;
                }
                case 10: {
                    Frintezza.this.a(Frintezza.this.b[2], 140, 180, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(11)), 2800L);
                    break;
                }
                case 11: {
                    Frintezza.this.a(Frintezza.this.b[3], 140, 180, 3, 22000, 3000, 1);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(12)), 3000L);
                    break;
                }
                case 12: {
                    Frintezza.this.a(Frintezza.this.h, 240, 90, 0, 0, 1000, 0);
                    Frintezza.this.a(Frintezza.this.h, 240, 90, 25, 5500, 10000, 3);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(13)), 3000L);
                    break;
                }
                case 13: {
                    Frintezza.this.a(Frintezza.this.h, 100, 195, 35, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(14)), 700L);
                    break;
                }
                case 14: {
                    Frintezza.this.a(Frintezza.this.h, 100, 195, 35, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(15)), 1300L);
                    break;
                }
                case 15: {
                    Frintezza.this.a(Frintezza.this.h, 120, 180, 45, 1500, 10000, 0);
                    Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.h, (Creature)Frintezza.this.h, 5006, 1, 34000, 0L)});
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(16)), 1500L);
                    break;
                }
                case 16: {
                    Frintezza.this.a(Frintezza.this.h, 520, 135, 45, 8000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(17)), 7500L);
                    break;
                }
                case 17: {
                    Frintezza.this.a(Frintezza.this.h, 1500, 110, 25, 10000, 13000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(18)), 9500L);
                    break;
                }
                case 18: {
                    Frintezza.this.i = Frintezza.this.a(b);
                    Frintezza.this.a(Frintezza.this.i, true);
                    Frintezza.this.i.addListener((Listener)Frintezza.this.a);
                    Frintezza.this.i.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.i, (Creature)Frintezza.this.i, 5016, 1, 3000, 0L)});
                    Earthquake earthquake = new Earthquake(Frintezza.this.i.getLoc(), 50, 6);
                    for (Player player : Frintezza.this.getPlayers()) {
                        player.broadcastPacket(new L2GameServerPacket[]{earthquake});
                    }
                    Frintezza.this.a(Frintezza.this.i, 1000, 160, 20, 6000, 6000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(19)), 5500L);
                    break;
                }
                case 19: {
                    Frintezza.this.a(Frintezza.this.i, 800, 160, 5, 1000, 10000, 2);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(20)), 2100L);
                    break;
                }
                case 20: {
                    Frintezza.this.a(Frintezza.this.i, 300, 60, 8, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(21)), 2000L);
                    break;
                }
                case 21: {
                    Frintezza.this.a(Frintezza.this.i, 1000, 90, 10, 3000, 5000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(22)), 3000L);
                    break;
                }
                case 22: {
                    for (Player player : Frintezza.this.getPlayers()) {
                        player.leaveMovieMode();
                    }
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(23)), 2000L);
                    break;
                }
                case 23: {
                    Frintezza.this.a(false);
                    Frintezza.this.a(new Frintezza.NpcLocation(174056, -76024, -5104, 0, 29061));
                    Frintezza.this.bu = 1;
                    Frintezza.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Music(Frintezza.this)), 5000L);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
