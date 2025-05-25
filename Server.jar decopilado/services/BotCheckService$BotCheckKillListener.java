/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.OnKillListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.actor.instances.player.AutoFarmContext
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.AutoFarmContext;
import services.BotCheckService;

private static class BotCheckService.BotCheckKillListener
implements OnKillListener {
    private BotCheckService.BotCheckKillListener() {
    }

    public void onKill(Creature creature, Creature creature2) {
        AutoFarmContext autoFarmContext = creature.getFarmSystem();
        if (!Config.SERVICE_CAPTCHA_BOT_CHECK || !creature.isPlayer() || !creature2.isMonster() || autoFarmContext != null && autoFarmContext.isAutofarming() || creature.isInAnyZone(new Zone.ZoneType[]{Zone.ZoneType.peace_zone, Zone.ZoneType.fun, Zone.ZoneType.epic, Zone.ZoneType.battle_zone, Zone.ZoneType.RESIDENCE})) {
            return;
        }
        BotCheckService.aq(creature.getPlayer());
    }

    public boolean ignorePetOrSummon() {
        return true;
    }
}
