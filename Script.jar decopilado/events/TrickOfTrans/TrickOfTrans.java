/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.TrickOfTrans;

import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrickOfTrans
extends Functions
implements OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static final Logger w = LoggerFactory.getLogger(TrickOfTrans.class);
    private static String S = "[trick_of_trans_spawn]";
    private static int bT = 9162;
    private static int bU = 9163;
    private static int bV = 9164;
    private static int bW = 9165;
    private static int bX = 9166;
    private static int bY = 9167;
    private static int bZ = 9171;
    private static int ca = 9172;
    private static int cb = 9173;
    private static int cc = 9174;
    private static int cd = 9175;
    private static int ce = 9176;
    private static int cf = 9205;
    private static boolean T = false;
    private static int cg = 9168;
    private static int ch = 17;
    private static int ci = 9169;
    private static int cj = 9170;
    private static int ck = 30;

    public void onLoad() {
        CharListenerList.addGlobal((Listener)this);
        if (TrickOfTrans.isActive()) {
            T = true;
            this.spawnEventManagers();
            w.info("Loaded Event: Trick of Trnasmutation [state: activated]");
        } else {
            w.info("Loaded Event: Trick of Trnasmutation [state: deactivated]");
        }
    }

    private static boolean isActive() {
        return TrickOfTrans.IsActive((String)"trickoftrans");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TrickOfTrans.SetActive((String)"trickoftrans", (boolean)true)) {
            this.spawnEventManagers();
            System.out.println("Event 'Trick of Transmutation' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.TrickOfTrans.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'Trick of Transmutation' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TrickOfTrans.SetActive((String)"trickoftrans", (boolean)false)) {
            this.unSpawnEventManagers();
            System.out.println("Event 'Trick of Transmutation' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.TrickOfTrans.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Trick of Transmutation' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.TrickOfTrans.AnnounceEventStarted", null);
        }
    }

    public void onReload() {
        this.unSpawnEventManagers();
    }

    public void onShutdown() {
        this.unSpawnEventManagers();
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(S);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(S);
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && TrickOfTrans.simpleCheckDrop((Creature)creature, (Creature)creature2) && Rnd.chance((double)(Config.EVENT_TRICK_OF_TRANS_CHANCE * creature2.getPlayer().getRateItems() * Config.RATE_DROP_ITEMS * ((NpcInstance)creature).getTemplate().rateHp))) {
            ((NpcInstance)creature).dropItem(creature2.getPlayer(), cf, 1L);
        }
    }

    public void accept() {
        Player player = this.getSelf();
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (!player.findRecipe(bZ)) {
            TrickOfTrans.addItem((Playable)player, (int)bT, (long)1L);
        }
        if (!player.findRecipe(cc)) {
            TrickOfTrans.addItem((Playable)player, (int)bW, (long)1L);
        }
        if (!player.findRecipe(ca)) {
            TrickOfTrans.addItem((Playable)player, (int)bU, (long)1L);
        }
        if (!player.findRecipe(ce)) {
            TrickOfTrans.addItem((Playable)player, (int)bY, (long)1L);
        }
        if (!player.findRecipe(cb)) {
            TrickOfTrans.addItem((Playable)player, (int)bV, (long)1L);
        }
        if (!player.findRecipe(cd)) {
            TrickOfTrans.addItem((Playable)player, (int)bX, (long)1L);
        }
        this.show("scripts/events/TrickOfTrans/TrickOfTrans_01.htm", player);
    }

    public void open() {
        Player player = this.getSelf();
        if (TrickOfTrans.getItemCount((Playable)player, (int)cf) > 0L) {
            TrickOfTrans.removeItem((Playable)player, (int)cf, (long)1L);
            TrickOfTrans.addItem((Playable)player, (int)cg, (long)Rnd.get((int)1, (int)ch));
            TrickOfTrans.addItem((Playable)player, (int)cj, (long)Rnd.get((int)1, (int)ck));
            if (Rnd.chance((int)80)) {
                TrickOfTrans.addItem((Playable)player, (int)ci, (long)1L);
            }
            this.show("scripts/events/TrickOfTrans/TrickOfTrans_02.htm", player);
        } else {
            this.show("scripts/events/TrickOfTrans/TrickOfTrans_03.htm", player);
        }
    }
}
