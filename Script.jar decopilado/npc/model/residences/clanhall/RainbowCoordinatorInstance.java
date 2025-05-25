/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent
 *  l2.gameserver.model.entity.events.objects.CMGSiegeClanObject
 *  l2.gameserver.model.entity.events.objects.SpawnExObject
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.clanhall;

import java.util.List;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent;
import l2.gameserver.model.entity.events.objects.CMGSiegeClanObject;
import l2.gameserver.model.entity.events.objects.SpawnExObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class RainbowCoordinatorInstance
extends NpcInstance {
    public RainbowCoordinatorInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!RainbowCoordinatorInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        ClanHall clanHall = this.getClanHall();
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)clanHall.getSiegeEvent();
        if (clanHallMiniGameEvent == null) {
            return;
        }
        if (clanHallMiniGameEvent.isArenaClosed()) {
            this.showChatWindow(player, "residence2/clanhall/game_manager003.htm", new Object[0]);
            return;
        }
        List list = clanHallMiniGameEvent.getObjects("attackers");
        CMGSiegeClanObject cMGSiegeClanObject = (CMGSiegeClanObject)clanHallMiniGameEvent.getSiegeClan("attackers", player.getClan());
        if (cMGSiegeClanObject == null) {
            this.showChatWindow(player, "residence2/clanhall/game_manager014.htm", new Object[0]);
            return;
        }
        if (cMGSiegeClanObject.getPlayers().isEmpty()) {
            Player player22;
            Party party = player.getParty();
            if (party == null) {
                this.showChatWindow(player, player.isClanLeader() ? "residence2/clanhall/game_manager005.htm" : "residence2/clanhall/game_manager002.htm", new Object[0]);
                return;
            }
            if (!player.isClanLeader()) {
                this.showChatWindow(player, "residence2/clanhall/game_manager004.htm", new Object[0]);
                return;
            }
            if (party.getMemberCount() < 5) {
                this.showChatWindow(player, "residence2/clanhall/game_manager003.htm", new Object[0]);
                return;
            }
            if (party.getPartyLeader() != player) {
                this.showChatWindow(player, "residence2/clanhall/game_manager006.htm", new Object[0]);
                return;
            }
            for (Player player22 : party.getPartyMembers()) {
                if (player22.getClan() == player.getClan()) continue;
                this.showChatWindow(player, "residence2/clanhall/game_manager007.htm", new Object[0]);
                return;
            }
            int n = list.indexOf(cMGSiegeClanObject);
            player22 = (SpawnExObject)clanHallMiniGameEvent.getFirstObject("arena_" + n);
            Location location = (Location)((Spawner)player22.getSpawns().get(0)).getCurrentSpawnRange();
            for (Player player3 : party.getPartyMembers()) {
                cMGSiegeClanObject.addPlayer(player3.getObjectId());
                player3.teleToLocation(Location.coordsRandomize((Location)location, (int)100, (int)200));
            }
        } else {
            this.showChatWindow(player, "residence2/clanhall/game_manager013.htm", new Object[0]);
        }
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, "residence2/clanhall/game_manager001.htm", new Object[0]);
    }
}
