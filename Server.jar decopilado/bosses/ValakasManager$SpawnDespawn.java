/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.BossInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.network.l2.s2c.PlaySound$Type
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package bosses;

import bosses.EpicBossState;
import bosses.ValakasManager;
import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

private static class ValakasManager.SpawnDespawn
extends RunnableImpl {
    private int bo = 2550;
    private int bp;
    private List<Player> q = ValakasManager.a();

    ValakasManager.SpawnDespawn(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        switch (this.bp) {
            case 1: {
                if (Config.VALAKAS_ANNOUNCE_PREPARING_SPAWN) {
                    Announcements.getInstance().announceByCustomMessage("ValakasWakeUp", null);
                }
                d = (BossInstance)Functions.spawn((Location)new Location(212852, -114842, -1632, 833), (int)29028);
                a.set(false);
                d.block();
                d.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS03_A", 1, d.getObjectId(), d.getLoc())});
                _state.setRespawnDate((long)(Config.ALT_RAID_RESPAWN_MULTIPLIER * (double)(Config.FWV_FIXINTERVALOFVALAKAS + Rnd.get((long)0L, (long)Config.FWV_RANDOMINTERVALOFVALAKAS))));
                _state.setState(EpicBossState.State.ALIVE);
                _state.update();
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(2)), 16L);
                P = true;
                break;
            }
            case 2: {
                d.broadcastPacket(new L2GameServerPacket[]{new SocialAction(d.getObjectId(), 1)});
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1800, 180, -1, 1500, 15000, 0, 0, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(3)), 1500L);
                break;
            }
            case 3: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1300, 180, -5, 3000, 15000, 0, -5, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(4)), 3300L);
                break;
            }
            case 4: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 500, 180, -8, 600, 15000, 0, 60, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(5)), 2900L);
                break;
            }
            case 5: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 800, 180, -8, 2700, 15000, 0, 30, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(6)), 2700L);
                break;
            }
            case 6: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 200, 250, 70, 0, 15000, 30, 80, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(7)), 1L);
                break;
            }
            case 7: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1100, 250, 70, 2500, 15000, 30, 80, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(8)), 3200L);
                break;
            }
            case 8: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 700, 150, 30, 0, 15000, -10, 60, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(9)), 1400L);
                break;
            }
            case 9: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1200, 150, 20, 2900, 15000, -10, 30, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(10)), 6700L);
                break;
            }
            case 10: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 750, 170, -10, 3400, 15000, 10, -15, 1, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(11)), 5700L);
                break;
            }
            case 11: {
                for (Player player : this.q) {
                    player.leaveMovieMode();
                }
                d.unblock();
                ValakasManager.broadcastCustomScreenMessage("ValakasFool");
                if (d.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                    d.moveToLocation(new Location(Rnd.get((int)211080, (int)214909), Rnd.get((int)-115841, (int)-112822), -1662, 0), 0, false);
                }
                h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.CheckLastAttack()), 600000L);
                break;
            }
            case 12: {
                d.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "B03_D", 1, d.getObjectId(), d.getLoc())});
                ValakasManager.broadcastCustomScreenMessage("VALAKAS_THE_EVIL_FIRE_DRAGON_VALAKAS_DEFEATED");
                ValakasManager.w();
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 2000, 130, -1, 0, 15000, 0, 0, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(13)), 500L);
                break;
            }
            case 13: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1100, 210, -5, 3000, 15000, -13, 0, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(14)), 3500L);
                break;
            }
            case 14: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1300, 200, -8, 3000, 15000, 0, 15, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(15)), 4500L);
                break;
            }
            case 15: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1000, 190, 0, 500, 15000, 0, 10, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(16)), 500L);
                break;
            }
            case 16: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1700, 120, 0, 2500, 15000, 12, 40, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(17)), 4600L);
                break;
            }
            case 17: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1700, 20, 0, 700, 15000, 10, 10, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(18)), 750L);
                break;
            }
            case 18: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)d) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)d, 1700, 10, 0, 1000, 15000, 20, 70, 1, 1);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ValakasManager.SpawnDespawn(19)), 2500L);
                break;
            }
            case 19: {
                for (Player player : this.q) {
                    player.leaveMovieMode();
                }
                break;
            }
        }
    }
}
