/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.listener.actor.OnCurrentMpReduceListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.stats.Stats
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.listener.actor.OnCurrentMpReduceListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Stats;
import services.ACP;

private static final class ACP.MPACPHelper
extends ACP.ACPHelper
implements OnCurrentMpReduceListener {
    protected ACP.MPACPHelper(Player player) {
        super(player, ACP.ACPType.MPACP);
    }

    @Override
    protected boolean canUse(Player player) {
        if (super.canUse(player)) {
            double d = player.calcStat(Stats.MP_LIMIT, null, null) * (double)player.getMaxMp() / 100.0 * ((double)this.getType().getActPercent(player) / 100.0);
            return player.getCurrentMp() < d;
        }
        return false;
    }

    public void onCurrentMpReduce(Creature creature, double d, Creature creature2) {
        Player player = creature.getPlayer();
        if (player == null) {
            creature.removeListener((Listener)this);
            return;
        }
        if (!this.getType().isEnabled(player)) {
            creature.removeListener((Listener)this);
        }
        this.act(player);
    }
}
