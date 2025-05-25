/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.ClanTable
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import java.util.Arrays;
import java.util.Comparator;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.ClanTable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class TopClanService
extends Functions
implements IVoicedCommandHandler,
ScriptFile {
    private static String il = "scripts/services/";
    private String[] o = new String[]{"topclans", "topclan"};

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICES_TOP_CLANS_STATISTIC) {
            return false;
        }
        if (this.o[0].equalsIgnoreCase(string) || this.o[1].equalsIgnoreCase(string)) {
            this.topClans(player);
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.SERVICES_TOP_CLANS_STATISTIC) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return this.o;
    }

    public void topClans() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        this.topClans(player);
    }

    public void topClansCb() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        this.topClansCb(player);
    }

    public void topClansCb(Player player) {
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_TOP_CLANS_STATISTIC) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        Clan[] clanArray = (Clan[])ArrayUtils.clone((Object[])ClanTable.getInstance().getClans());
        Arrays.sort(clanArray, Comparator.comparingInt(Clan::getCustomPoints).reversed());
        Arrays.sort(clanArray, Comparator.comparingInt(Clan::getReputationScore).reversed());
        Arrays.sort(clanArray, Comparator.comparingInt(Clan::getLevel).reversed());
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/top_clans.htm", player);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Math.min(clanArray.length, Config.SERVICES_TOP_CLANS_STATISTIC_LIST); ++i) {
            Clan clan = clanArray[i];
            String string2 = StringHolder.getInstance().getNotNull(player, "TopClanService.StringAddressCb");
            string2 = string2.replace("%clanName%", StringUtils.trimToEmpty((String)clan.getName()));
            string2 = string2.replace("%clanLeaderName%", StringUtils.trimToEmpty((String)clan.getLeaderName()));
            string2 = string2.replace("%clanRP%", String.valueOf(clan.getCustomPoints()));
            string2 = string2.replace("%clan\u0421RP%", String.valueOf(clan.getReputationScore()));
            string2 = string2.replace("%clanLvL%", String.valueOf(clan.getLevel()));
            stringBuilder.append(string2);
        }
        string = string.replace("%content%", stringBuilder.toString());
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void topClans(Player player) {
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_TOP_CLANS_STATISTIC) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        Clan[] clanArray = (Clan[])ArrayUtils.clone((Object[])ClanTable.getInstance().getClans());
        Arrays.sort(clanArray, Comparator.comparingInt(Clan::getCustomPoints).reversed());
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile(il + "top_clans.htm");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Math.min(clanArray.length, Config.SERVICES_TOP_CLANS_STATISTIC_LIST); ++i) {
            Clan clan = clanArray[i];
            String string = StringHolder.getInstance().getNotNull(player, "TopClanService.StringAddress");
            string = string.replace("%clanName%", StringUtils.trimToEmpty((String)clan.getName()));
            string = string.replace("%clanLeaderName%", StringUtils.trimToEmpty((String)clan.getLeaderName()));
            string = string.replace("%clanRP%", String.valueOf(clan.getCustomPoints()));
            string = string.replace("%clan\u0421RP%", String.valueOf(clan.getReputationScore()));
            string = string.replace("%clanLvL%", String.valueOf(clan.getLevel()));
            stringBuilder.append(string);
        }
        npcHtmlMessage.replace("%content%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void onLoad() {
        if (Config.SERVICES_TOP_CLANS_STATISTIC) {
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
