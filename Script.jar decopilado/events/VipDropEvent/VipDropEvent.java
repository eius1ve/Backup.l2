/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package events.VipDropEvent;

import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class VipDropEvent
extends Functions
implements OnDeathListener,
ScriptFile {
    private static boolean T;

    public void onDeath(Creature creature, Creature creature2) {
        if (T && creature2 != null && this.a(creature2) && VipDropEvent.simpleCheckDrop((Creature)creature, (Creature)creature2)) {
            this.a(creature, creature2);
        }
    }

    private boolean a(Creature creature) {
        Player player = creature.getPlayer();
        if (player == null) {
            return false;
        }
        return player.getVipBonusGoldDrop() > 0 || player.getVipBonusSilverDrop() > 0;
    }

    private void a(Creature creature, Creature creature2) {
        int n;
        int n2;
        int n3;
        Player player = creature2.getPlayer();
        if (player == null) {
            return;
        }
        if (player.getVipBonusSilverDrop() > 0) {
            n3 = player.getVipBonusSilverDrop();
            n2 = n3 / 100;
            n = n3 % 100;
            if (Rnd.chance((int)n)) {
                ++n2;
            }
            if (n2 > 0) {
                this.a(creature, player, Config.PRIME_SHOP_SILVER_ITEM_ID, n2);
            }
        }
        if (player.getVipBonusGoldDrop() > 0) {
            n3 = player.getVipBonusGoldDrop();
            n2 = n3 / 100;
            n = n3 % 100;
            if (Rnd.chance((int)n)) {
                ++n2;
            }
            if (n2 > 0) {
                this.a(creature, player, Config.PRIME_SHOP_GOLD_ITEM_ID, n2);
            }
        }
    }

    private void a(Creature creature, Player player, int n, int n2) {
        ((NpcInstance)creature).dropItem(player, n, (long)n2);
    }

    public void onLoad() {
        if (Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            CharListenerList.addGlobal((Listener)this);
            T = true;
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
