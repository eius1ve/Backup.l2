/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.events.impl.ClanHallTeamBattleEvent
 *  l2.gameserver.model.entity.events.objects.CTBTeamObject
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.clanhall;

import java.util.List;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.events.impl.ClanHallTeamBattleEvent;
import l2.gameserver.model.entity.events.objects.CTBTeamObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class MatchMassTeleporterInstance
extends NpcInstance {
    private int HU;
    private long ap;

    public MatchMassTeleporterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.HU = npcTemplate.getAIParams().getInteger((Object)"flag");
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        ClanHall clanHall = this.getClanHall();
        ClanHallTeamBattleEvent clanHallTeamBattleEvent = (ClanHallTeamBattleEvent)clanHall.getSiegeEvent();
        if (this.ap > System.currentTimeMillis()) {
            this.showChatWindow(player, "residence2/clanhall/agit_mass_teleporter001.htm", new Object[0]);
            return;
        }
        if (this.isInActingRange((GameObject)player)) {
            this.ap = System.currentTimeMillis() + 60000L;
            List list = clanHallTeamBattleEvent.getObjects("tryout_part");
            CTBTeamObject cTBTeamObject = (CTBTeamObject)list.get(this.HU);
            if (cTBTeamObject.getFlag() != null) {
                for (Player player2 : World.getAroundPlayers((GameObject)this, (int)400, (int)100)) {
                    player2.teleToLocation(Location.findPointToStay((GameObject)cTBTeamObject.getFlag(), (int)100, (int)125));
                }
            }
        }
    }
}
