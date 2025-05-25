/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.skills.skillclasses.Call;
import l2.gameserver.utils.Location;

public class Relocate
extends Functions
implements IVoicedCommandHandler {
    private static final String bZ = "summonClanLastUse";
    private static final String ca = "Clan Summon";
    private final String[] aG = new String[]{"summon_clan", "km-all-to-me", "rcm"};

    @Override
    public String[] getVoicedCommandList() {
        return this.aG;
    }

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        long l;
        if (!Config.SERVICES_CLAN_SUMMON_COMMAND_ENABLE) {
            return false;
        }
        Clan clan = player.getClan();
        if (clan == null) {
            player.sendMessage(new CustomMessage("voicecommands.Relocate.NotMember", player, new Object[0]));
            return false;
        }
        if (clan.getLeaderId() != player.getObjectId()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            return false;
        }
        SystemMessage systemMessage = Call.canSummonHere(player);
        if (systemMessage != null) {
            player.sendMessage("Clan Summon is started");
            player.sendPacket((IStaticPacket)systemMessage);
            return false;
        }
        List<Player> list = clan.getOnlineMembers(player.getObjectId());
        if (list.size() < 1) {
            player.sendMessage(new CustomMessage("voicecommands.Relocate.NoClanMember", player, new Object[0]));
            return false;
        }
        if (Relocate.getItemCount(player, Config.SERVICES_CLAN_SUMMON_COMMAND_SELL_ITEM) < (long)Config.SERVICES_CLAN_SUMMON_COMMAND_SELL_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return false;
        }
        long l2 = System.currentTimeMillis() / 1000L;
        if (l2 - (l = player.getVarLong(bZ, 0L)) < Config.REUSE_DELAY_FOR_CLAN_SUMMON) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_AVAILABLE_AT_THIS_TIME_BEING_PREPARED_FOR_REUSE).addString(ca));
            return false;
        }
        Relocate.removeItem(player, Config.SERVICES_CLAN_SUMMON_COMMAND_SELL_ITEM, Config.SERVICES_CLAN_SUMMON_COMMAND_SELL_PRICE);
        player.sendMessage("Clan Summon is started");
        player.setVar(bZ, l2, -1L);
        for (Player player2 : list) {
            if (Call.canBeSummoned(player2) != null) continue;
            player2.summonCharacterRequest(player, Location.findPointToStay(player.getX(), player.getY(), player.getZ(), 100, 150, player.getReflection().getGeoIndex()), Config.SERVICES_CLAN_SUMMON_COMMAND_SUMMON_CRYSTAL_COUNT);
        }
        return true;
    }
}
