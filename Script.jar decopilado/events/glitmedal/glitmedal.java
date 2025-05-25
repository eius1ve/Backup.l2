/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Util
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.glitmedal;

import java.io.File;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class glitmedal
extends Functions
implements OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static String ah = "[event_giltmedal_roy_spawn]";
    private static String ai = "[event_giltmedal_winnie_spawn]";
    private static final Logger C = LoggerFactory.getLogger(glitmedal.class);
    private int cv;
    private static int cw = 6392;
    private static int cx = 6393;
    private static int cy = 6399;
    private static int cz = 6400;
    private static int cA = 6401;
    private static int cB = 6402;
    private static boolean T = false;
    private static boolean U = false;
    private static File[] a = new File[]{new File(Config.DATAPACK_ROOT, "data/html-en/scripts/events/glitmedal/502.xml"), new File(Config.DATAPACK_ROOT, "data/html-en/scripts/events/glitmedal/503.xml"), new File(Config.DATAPACK_ROOT, "data/html-en/scripts/events/glitmedal/504.xml"), new File(Config.DATAPACK_ROOT, "data/html-en/scripts/events/glitmedal/505.xml"), new File(Config.DATAPACK_ROOT, "data/html-en/scripts/events/glitmedal/506.xml")};

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (glitmedal.isActive()) {
            T = true;
            glitmedal.x();
            this.spawnEventManagers();
            C.info("Loaded Event: L2 Medal Collection Event [state: activated]");
        } else {
            C.info("Loaded Event: L2 Medal Collection Event [state: deactivated]");
        }
    }

    private static boolean isActive() {
        return glitmedal.IsActive((String)"glitter");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (glitmedal.SetActive((String)"glitter", (boolean)true)) {
            glitmedal.x();
            this.spawnEventManagers();
            System.out.println("Event 'L2 Medal Collection Event' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.glitmedal.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'L2 Medal Collection Event' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (glitmedal.SetActive((String)"glitter", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'L2 Medal Collection Event' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.glitmedal.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'L2 Medal Collection Event' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.glitmedal.AnnounceEventStarted", null);
        }
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(ah);
        SpawnManager.getInstance().spawn(ai);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(ah);
        SpawnManager.getInstance().despawn(ai);
    }

    private static void x() {
        if (U) {
            return;
        }
        for (File file : a) {
            MultiSellHolder.getInstance().parseFile(file);
        }
        U = true;
    }

    public void onReload() {
        this.unSpawnEventManagers();
        if (U) {
            for (File file : a) {
                MultiSellHolder.getInstance().remove(file);
            }
            U = false;
        }
    }

    public void onShutdown() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && glitmedal.simpleCheckDrop((Creature)creature, (Creature)creature2)) {
            long l = Util.rollDrop((long)1L, (long)1L, (double)(Config.EVENT_GLITTMEDAL_NORMAL_CHANCE * creature2.getPlayer().getRateItems() * ((MonsterInstance)creature).getTemplate().rateHp * 10000.0), (boolean)true);
            if (l > 0L) {
                glitmedal.addItem((Playable)creature2.getPlayer(), (int)cw, (long)l);
            }
            if (creature2.getPlayer().getInventory().getCountOf(cB) == 0L && Rnd.chance((double)(Config.EVENT_GLITTMEDAL_GLIT_CHANCE * creature2.getPlayer().getRateItems() * ((MonsterInstance)creature).getTemplate().rateHp))) {
                glitmedal.addItem((Playable)creature2.getPlayer(), (int)cx, (long)1L);
            }
        }
    }

    public void glitchang() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cw) >= 1000L) {
            glitmedal.removeItem((Playable)player, (int)cw, (long)1000L);
            glitmedal.addItem((Playable)player, (int)cx, (long)10L);
            return;
        }
        player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
    }

    public void medal() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cB) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent1_q0996_05.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cA) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent1_q0996_04.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cz) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent1_q0996_03.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cy) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent1_q0996_02.htm", player);
            return;
        }
        this.show("scripts/events/glitmedal/event_col_agent1_q0996_01.htm", player);
    }

    public void medalb() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cB) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_05.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cA) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_04.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cz) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_03.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cy) >= 1L) {
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_02.htm", player);
            return;
        }
        this.show("scripts/events/glitmedal/event_col_agent2_q0996_01.htm", player);
    }

    public void game() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cA) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 40L) {
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player);
                return;
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cz) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 20L) {
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player);
                return;
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cy) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 10L) {
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player);
                return;
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cx) >= 5L) {
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player);
            return;
        }
        this.show("scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player);
    }

    public void gamea() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        this.cv = Rnd.get((int)2);
        if (glitmedal.getItemCount((Playable)player, (int)cA) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 40L) {
                if (this.cv == 1) {
                    glitmedal.removeItem((Playable)player, (int)cA, (long)1L);
                    glitmedal.removeItem((Playable)player, (int)cx, (long)glitmedal.getItemCount((Playable)player, (int)cx));
                    glitmedal.addItem((Playable)player, (int)cB, (long)1L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_24.htm", player);
                    return;
                }
                if (this.cv == 0) {
                    glitmedal.removeItem((Playable)player, (int)cx, (long)40L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player);
                    return;
                }
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cz) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 20L) {
                if (this.cv == 1) {
                    glitmedal.removeItem((Playable)player, (int)cz, (long)1L);
                    glitmedal.removeItem((Playable)player, (int)cx, (long)20L);
                    glitmedal.addItem((Playable)player, (int)cA, (long)1L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_23.htm", player);
                    return;
                }
                if (this.cv == 0) {
                    glitmedal.removeItem((Playable)player, (int)cx, (long)20L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player);
                    return;
                }
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cy) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 10L) {
                if (this.cv == 1) {
                    glitmedal.removeItem((Playable)player, (int)cy, (long)1L);
                    glitmedal.removeItem((Playable)player, (int)cx, (long)10L);
                    glitmedal.addItem((Playable)player, (int)cz, (long)1L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_22.htm", player);
                    return;
                }
                if (this.cv == 0) {
                    glitmedal.removeItem((Playable)player, (int)cx, (long)10L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player);
                    return;
                }
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cx) >= 5L) {
            if (this.cv == 1) {
                glitmedal.removeItem((Playable)player, (int)cx, (long)5L);
                glitmedal.addItem((Playable)player, (int)cy, (long)1L);
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_21.htm", player);
                return;
            }
            if (this.cv == 0) {
                glitmedal.removeItem((Playable)player, (int)cx, (long)5L);
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player);
                return;
            }
        }
        this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
    }

    public void gameb() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        this.cv = Rnd.get((int)2);
        if (glitmedal.getItemCount((Playable)player, (int)cA) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 40L) {
                if (this.cv == 1) {
                    glitmedal.removeItem((Playable)player, (int)cA, (long)1L);
                    glitmedal.removeItem((Playable)player, (int)cx, (long)40L);
                    glitmedal.addItem((Playable)player, (int)cB, (long)1L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_34.htm", player);
                    return;
                }
                if (this.cv == 0) {
                    glitmedal.removeItem((Playable)player, (int)cx, (long)40L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player);
                    return;
                }
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cz) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 20L) {
                if (this.cv == 1) {
                    glitmedal.removeItem((Playable)player, (int)cz, (long)1L);
                    glitmedal.removeItem((Playable)player, (int)cx, (long)20L);
                    glitmedal.addItem((Playable)player, (int)cA, (long)1L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_33.htm", player);
                    return;
                }
                if (this.cv == 0) {
                    glitmedal.removeItem((Playable)player, (int)cx, (long)20L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player);
                    return;
                }
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cy) >= 1L) {
            if (glitmedal.getItemCount((Playable)player, (int)cx) >= 10L) {
                if (this.cv == 1) {
                    glitmedal.removeItem((Playable)player, (int)cy, (long)1L);
                    glitmedal.removeItem((Playable)player, (int)cx, (long)10L);
                    glitmedal.addItem((Playable)player, (int)cz, (long)1L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_32.htm", player);
                    return;
                }
                if (this.cv == 0) {
                    glitmedal.removeItem((Playable)player, (int)cx, (long)10L);
                    this.show("scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player);
                    return;
                }
            }
            this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
            return;
        }
        if (glitmedal.getItemCount((Playable)player, (int)cx) >= 5L) {
            if (this.cv == 1) {
                glitmedal.removeItem((Playable)player, (int)cx, (long)5L);
                glitmedal.addItem((Playable)player, (int)cy, (long)1L);
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_31.htm", player);
                return;
            }
            if (this.cv == 0) {
                glitmedal.removeItem((Playable)player, (int)cx, (long)5L);
                this.show("scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player);
                return;
            }
        }
        this.show("scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player);
    }
}
