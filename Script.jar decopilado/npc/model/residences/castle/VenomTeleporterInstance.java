/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.castle;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class VenomTeleporterInstance
extends NpcInstance {
    public VenomTeleporterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        Castle castle = this.getCastle();
        if (castle.getSiegeEvent().isInProgress()) {
            this.showChatWindow(player, "residence2/castle/rune_massymore_teleporter002.htm", new Object[0]);
        } else {
            player.teleToLocation(12589, -49044, -3008);
        }
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, "residence2/castle/rune_massymore_teleporter001.htm", new Object[0]);
    }
}
