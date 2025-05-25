/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.templates.npc.NpcTemplate;

public class SiegeGuardInstance
extends MonsterInstance {
    public SiegeGuardInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.setHasChatWindow(false);
    }

    public boolean isSiegeGuard() {
        return true;
    }

    public int getAggroRange() {
        return 1200;
    }

    public boolean isAutoAttackable(Creature creature) {
        Player player = creature.getPlayer();
        if (player == null) {
            return false;
        }
        SiegeEvent siegeEvent = (SiegeEvent)this.getEvent(SiegeEvent.class);
        if (siegeEvent == null) {
            return false;
        }
        Clan clan = player.getClan();
        SiegeEvent siegeEvent2 = (SiegeEvent)creature.getEvent(SiegeEvent.class);
        return siegeEvent != siegeEvent2 || siegeEvent.getSiegeClan("defenders", clan) == null;
    }

    public boolean hasRandomAnimation() {
        return false;
    }

    public boolean isFearImmune() {
        return true;
    }

    public boolean isParalyzeImmune() {
        return true;
    }

    public boolean isMonster() {
        return false;
    }
}
