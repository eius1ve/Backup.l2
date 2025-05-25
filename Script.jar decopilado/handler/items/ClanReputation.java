/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 */
package handler.items;

import handler.items.SimpleItemHandler;
import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;

public class ClanReputation
extends SimpleItemHandler {
    @Override
    protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
        int n = itemInstance.getItemId();
        if (player == null) {
            return false;
        }
        if (!Config.ITEM_CLAN_REPUTATION_ENABLE) {
            player.sendMessage(new CustomMessage("items.clanreputation.disabled", player, new Object[0]));
            return false;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("items.clanreputation.getClanFirst", player, new Object[0]));
            return false;
        }
        if (clan.getLevel() < Config.MIN_CLAN_LEVEL_FOR_REPUTATION) {
            player.sendMessage(new CustomMessage("items.clanreputation.GetClanLevelFirst", player, new Object[0]));
            return false;
        }
        for (int i = 0; i < Config.ITEM_CLAN_REPUTATION_ID.length; ++i) {
            if (n != Config.ITEM_CLAN_REPUTATION_ID[i] || Functions.removeItem((Playable)player, (int)n, (long)1L) <= 0L) continue;
            clan.incReputation(Config.ITEM_CLAN_REPUTATION_AMOUNT[i], true, "ClanReputationItemAdd");
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_SUCCESSFULLY_COMPLETED_A_CLAN_QUEST_S1_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addNumber(Config.ITEM_CLAN_REPUTATION_AMOUNT[i]));
            return true;
        }
        return false;
    }

    public int[] getItemIds() {
        return Config.ITEM_CLAN_REPUTATION_ID;
    }
}
