/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.AggroList$HateInfo
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.templates.npc.NpcTemplate;
import npc.model.residences.SiegeGuardInstance;

public class LidiaVonHellmannInstance
extends SiegeGuardInstance {
    public LidiaVonHellmannInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onDeath(Creature creature) {
        SiegeEvent siegeEvent = (SiegeEvent)this.getEvent(SiegeEvent.class);
        if (siegeEvent == null) {
            return;
        }
        siegeEvent.processStep(this.getMostDamagedClan());
        super.onDeath(creature);
    }

    public Clan getMostDamagedClan() {
        int n;
        AggroList.HateInfo hateInfo22;
        ClanHallSiegeEvent clanHallSiegeEvent = (ClanHallSiegeEvent)this.getEvent(ClanHallSiegeEvent.class);
        Player player = null;
        HashMap<Player, Integer> hashMap = new HashMap<Player, Integer>();
        for (AggroList.HateInfo hateInfo22 : this.getAggroList().getPlayableMap().values()) {
            Playable playable = (Playable)hateInfo22.attacker;
            int n2 = hateInfo22.damage;
            if (playable.isPet() || playable.isSummon()) {
                player = playable.getPlayer();
            } else if (playable.isPlayer()) {
                player = (Player)playable;
            }
            if (player == null || clanHallSiegeEvent.getSiegeClan("attackers", player.getClan()) == null) continue;
            if (!hashMap.containsKey(player)) {
                hashMap.put(player, n2);
                continue;
            }
            n = (Integer)hashMap.get(player) + n2;
            hashMap.put(player, n);
        }
        int n3 = 0;
        hateInfo22 = null;
        for (Map.Entry entry : hashMap.entrySet()) {
            n = (Integer)entry.getValue();
            Player player2 = (Player)entry.getKey();
            if (n <= n3) continue;
            n3 = n;
            hateInfo22 = player2;
        }
        return hateInfo22 == null ? null : hateInfo22.getClan();
    }

    public boolean isEffectImmune() {
        return true;
    }
}
