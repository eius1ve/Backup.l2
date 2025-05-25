/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
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

import bosses.AntharasManager;
import bosses.EpicBossState;
import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

private static class AntharasManager.AntharasSpawn
extends RunnableImpl {
    private int bo = 5000;
    private int bp = 0;
    private List<Player> q = AntharasManager.a();

    AntharasManager.AntharasSpawn(int n) {
        this.bp = n;
    }

    public void runImpl() {
        switch (this.bp) {
            case 1: {
                if (Config.ANTHARAS_ANNOUNCE_PREPARING_SPAWN) {
                    Announcements.getInstance().announceByCustomMessage("AntharasWakeUp", null);
                }
                a.set(false);
                a = (BossInstance)Functions.spawn((Location)e, (int)29068);
                a.setAggroRange(0);
                _state.setRespawnDate(AntharasManager.a());
                _state.setState(EpicBossState.State.ALIVE);
                _state.update();
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(2)), 2000L);
                P = true;
                break;
            }
            case 2: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)a) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)a, 700, 13, -19, 0, 20000, 0, 0, 0, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(3)), 3000L);
                break;
            }
            case 3: {
                a.broadcastPacket(new L2GameServerPacket[]{new SocialAction(a.getObjectId(), 1)});
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)a) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)a, 700, 13, 0, 6000, 20000, 0, 0, 0, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(4)), 10000L);
                break;
            }
            case 4: {
                a.broadcastPacket(new L2GameServerPacket[]{new SocialAction(a.getObjectId(), 2)});
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)a) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)a, 3700, 0, -3, 0, 10000, 0, 0, 0, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(5)), 200L);
                break;
            }
            case 5: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)a) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)a, 1100, 0, -3, 22000, 30000, 0, 0, 0, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(6)), 10800L);
                break;
            }
            case 6: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)a) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)a, 1100, 0, -3, 300, 7000, 0, 0, 0, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(7)), 7000L);
                break;
            }
            case 7: {
                for (Player player : this.q) {
                    player.leaveMovieMode();
                }
                AntharasManager.broadcastCustomScreenMessage("AntharasYoucannothope");
                a.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_A", 1, a.getObjectId(), a.getLoc())});
                a.setAggroRange(AntharasManager.a.getTemplate().aggroRange);
                a.setRunning();
                a.moveToLocation(new Location(179011, 114871, -7704), 0, false);
                h = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.CheckLastAttack()), 600000L);
                break;
            }
            case 8: {
                for (Player player : this.q) {
                    if (player.getDistance((GameObject)a) <= (double)this.bo) {
                        player.enterMovieMode();
                        player.specialCamera((GameObject)a, 1200, 20, -10, 0, 13000, 0, 0, 0, 0);
                        continue;
                    }
                    player.leaveMovieMode();
                }
                f = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new AntharasManager.AntharasSpawn(9)), 13000L);
                break;
            }
            case 9: {
                for (Player player : this.q) {
                    player.leaveMovieMode();
                }
                AntharasManager.broadcastCustomScreenMessage("ANTHARAS_THE_EVIL_LAND_DRAGON_ANTHARAS_DEFEATED");
                AntharasManager.l();
            }
        }
    }
}
