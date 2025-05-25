/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.listener.actor.OnCurrentHpDamageListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.stats.Stats
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.stats.Stats;
import services.ACP;

private static final class ACP.CPACPHelper
extends ACP.ACPHelper
implements OnCurrentHpDamageListener {
    protected ACP.CPACPHelper(Player player) {
        super(player, ACP.ACPType.CPACP);
    }

    @Override
    protected boolean canUse(Player player) {
        if (super.canUse(player)) {
            double d = player.calcStat(Stats.CP_LIMIT, null, null) * (double)player.getMaxCp() / 100.0 * ((double)this.getType().getActPercent(player) / 100.0);
            return player.getCurrentCp() < d;
        }
        return false;
    }

    public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
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
