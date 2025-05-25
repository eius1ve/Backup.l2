/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.CastleSiegeInfo
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.CastleSiegeInfo;
import l2.gameserver.templates.npc.NpcTemplate;

public class MessengerInstance
extends NpcInstance {
    private String _siegeDialog;
    private String hf;

    public MessengerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this._siegeDialog = npcTemplate.getAIParams().getString((Object)"siege_dialog");
        this.hf = npcTemplate.getAIParams().getString((Object)"owner_dialog");
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        ClanHall clanHall = this.getClanHall();
        ClanHallSiegeEvent clanHallSiegeEvent = (ClanHallSiegeEvent)clanHall.getSiegeEvent();
        if (clanHall.getOwner() != null && clanHall.getOwner() == player.getClan()) {
            this.showChatWindow(player, this.hf, new Object[0]);
        } else if (clanHallSiegeEvent.isInProgress()) {
            this.showChatWindow(player, this._siegeDialog, new Object[0]);
        } else {
            player.sendPacket((IStaticPacket)new CastleSiegeInfo(clanHall, player));
        }
    }
}
