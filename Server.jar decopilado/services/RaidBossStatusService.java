/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.instancemanager.RaidBossSpawnManager
 *  l2.gameserver.instancemanager.RaidBossSpawnManager$Status
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.TimeUtils
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.TimeUtils;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RaidBossStatusService
extends Functions
implements IVoicedCommandHandler,
ScriptFile {
    private String[] o = new String[]{"raid", "rb"};
    private static BossStatusInfo[] a = null;
    private static SimpleDateFormat a = new SimpleDateFormat(Config.SERVICES_RAID_STATUS_FORMAT);
    private static final int bFW = 22;

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICES_RAID_STATUS_ENABLE) {
            return false;
        }
        if (this.o[0].equalsIgnoreCase(string) || this.o[1].equalsIgnoreCase(string)) {
            this.listBossStatusesWithMsg(player, 1, false);
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.SERVICES_RAID_STATUS_ENABLE) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return this.o;
    }

    private static BossStatusInfo[] a() {
        ArrayList<RaidBossStatusInfo> arrayList = new ArrayList<RaidBossStatusInfo>();
        for (int n : Config.SERVICES_RAID_STATUS_ADDITIONAL_IDS) {
            arrayList.add(new RaidBossStatusInfo(n));
        }
        return arrayList.toArray(new BossStatusInfo[arrayList.size()]);
    }

    private static BossStatusInfo[] b() {
        if (a == null) {
            a = RaidBossStatusService.a();
            return a;
        }
        return a;
    }

    private static String a(Player player, BossStatusInfo bossStatusInfo) {
        String string = StringHolder.getInstance().getNotNull(player, "scripts.services.RaidStatusService." + bossStatusInfo.getStatus());
        long l = bossStatusInfo.getRespawnDate();
        string = string.replace("%name%", bossStatusInfo.getName());
        string = string.replace("%npc_id%", String.valueOf(bossStatusInfo.getNpcId()));
        string = string.replace("%respawn_date%", l > 0L ? a.format(l * 1000L) : "");
        return string;
    }

    public void listBossStatusesWithMsg(Player player, int n, boolean bl) {
        if (!Config.SERVICES_RAID_STATUS_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        BossStatusInfo[] bossStatusInfoArray = RaidBossStatusService.b();
        int n2 = (int)Math.ceil((double)bossStatusInfoArray.length / (double)Config.SERVICES_RAID_STATUS_BOSS_PER_PAGE);
        n = Math.max(1, Math.min(n, n2));
        StringBuilder stringBuilder = new StringBuilder();
        int n3 = (n - 1) * Config.SERVICES_RAID_STATUS_BOSS_PER_PAGE;
        int n4 = Math.min(n3 + Config.SERVICES_RAID_STATUS_BOSS_PER_PAGE, bossStatusInfoArray.length);
        for (int i = n3; i < n4; ++i) {
            stringBuilder.append(RaidBossStatusService.a(player, bossStatusInfoArray[i]));
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/command_raidstatus.htm");
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/raid_boss_status.htm", player);
        StringBuilder stringBuilder2 = new StringBuilder("<table width=270><tr>");
        if (n > 1) {
            if (bl) {
                stringBuilder2.append("<td align=left><button action=\"bypass scripts_services.RaidBossStatusService:listBossStatusesBBSWithPagination ").append(n - 1).append("\" value=\"Prev.\" width=50 height=15back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>");
            } else {
                stringBuilder2.append("<td align=left><button action=\"bypass -h scripts_services.RaidBossStatusService:listBossStatusesWithPagination ").append(n - 1).append("\" value=\"Prev.\" width=50 height=15back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>");
            }
        } else {
            stringBuilder2.append("<td width=50></td>");
        }
        stringBuilder2.append("<td align=center>Page ").append(n).append(" of ").append(n2).append("</td>");
        if (n < n2) {
            if (bl) {
                stringBuilder2.append("<td align=right><button action=\"bypass scripts_services.RaidBossStatusService:listBossStatusesBBSWithPagination ").append(n + 1).append("\" value=\"Next\" width=50 height=15back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>");
            } else {
                stringBuilder2.append("<td align=right><button action=\"bypass -h scripts_services.RaidBossStatusService:listBossStatusesWithPagination ").append(n + 1).append("\" value=\"Next\" width=50 height=15back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>");
            }
        } else {
            stringBuilder2.append("<td width=50></td>");
        }
        stringBuilder2.append("</tr></table>");
        if (bl) {
            string = string.replace("%list%", stringBuilder.toString());
            string = string.replace("%navigation%", stringBuilder2.toString());
            string = string.replace("%current_date%", TimeUtils.toSimpleFormat((long)System.currentTimeMillis()));
            ShowBoard.separateAndSend((String)string, (Player)player);
        } else {
            npcHtmlMessage.replace("%list%", stringBuilder.toString());
            npcHtmlMessage.replace("%navigation%", stringBuilder2.toString());
            npcHtmlMessage.replace("%current_date%", TimeUtils.toSimpleFormat((long)System.currentTimeMillis()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        }
    }

    public void listBossStatusesWithPagination(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        int n = 1;
        try {
            if (stringArray.length > 0) {
                n = Integer.parseInt(stringArray[0]);
            }
        }
        catch (NumberFormatException numberFormatException) {
            player.sendMessage("Invalid page number. Showing page 1.");
        }
        this.listBossStatusesWithMsg(player, n, false);
    }

    public void listBossStatusesBBSWithPagination(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        int n = 1;
        try {
            if (stringArray.length > 0) {
                n = Integer.parseInt(stringArray[0]);
            }
        }
        catch (NumberFormatException numberFormatException) {
            player.sendMessage("Invalid page number. Showing page 1.");
        }
        this.listBossStatusesWithMsg(player, n, true);
    }

    public void listRaidBossStatusesCb() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        this.listBossStatusesWithMsg(player, 1, true);
    }

    public void listRaidBossStatuses() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        NpcInstance npcInstance = this.getNpc();
        if (!Config.SERVICES_RAID_STATUS_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = RaidBossStatusService.b();
        StringBuilder stringBuilder = new StringBuilder();
        for (BossStatusInfo bossStatusInfo : npcHtmlMessage) {
            stringBuilder.append(RaidBossStatusService.a(player, bossStatusInfo));
        }
        NpcHtmlMessage npcHtmlMessage2 = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage2.setFile("scripts/services/raidstatus.htm");
        npcHtmlMessage2.replace("%list%", stringBuilder.toString());
        npcHtmlMessage2.replace("%current_date%", TimeUtils.toSimpleFormat((long)System.currentTimeMillis()));
        player.sendPacket((IStaticPacket)npcHtmlMessage2);
    }

    public void onLoad() {
        if (Config.SERVICES_RAID_STATUS_ENABLE) {
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }

    public static final class RaidBossStatusInfo
    extends BossStatusInfo {
        public RaidBossStatusInfo(int n) {
            super(n);
        }

        @Override
        public BossStatusInfo.BossStatus getStatus() {
            RaidBossSpawnManager.Status status = RaidBossSpawnManager.getInstance().getRaidBossStatusId(this.getNpcId());
            if (status == RaidBossSpawnManager.Status.DEAD) {
                long l = this.getRespawnDate();
                if (l * 1000L > System.currentTimeMillis()) {
                    return BossStatusInfo.BossStatus.RESPAWN;
                }
                return BossStatusInfo.BossStatus.DEAD;
            }
            if (status == RaidBossSpawnManager.Status.UNDEFINED) {
                return BossStatusInfo.BossStatus.DEAD;
            }
            return BossStatusInfo.BossStatus.ALIVE;
        }

        @Override
        public long getRespawnDate() {
            Spawner spawner = (Spawner)RaidBossSpawnManager.getInstance().getSpawnTable().get(this.getNpcId());
            if (spawner == null) {
                return -1L;
            }
            return spawner.getRespawnTime();
        }
    }

    private static abstract class BossStatusInfo {
        private final int bFX;

        public BossStatusInfo(int n) {
            this.bFX = n;
        }

        public int getNpcId() {
            return this.bFX;
        }

        public String getName() {
            NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(this.getNpcId());
            if (npcTemplate == null) {
                return "";
            }
            return npcTemplate.getName();
        }

        public abstract BossStatus getStatus();

        public abstract long getRespawnDate();

        public static final class BossStatus
        extends Enum<BossStatus> {
            public static final /* enum */ BossStatus ALIVE = new BossStatus();
            public static final /* enum */ BossStatus DEAD = new BossStatus();
            public static final /* enum */ BossStatus READY = new BossStatus();
            public static final /* enum */ BossStatus RESPAWN = new BossStatus();
            private static final /* synthetic */ BossStatus[] a;

            public static BossStatus[] values() {
                return (BossStatus[])a.clone();
            }

            public static BossStatus valueOf(String string) {
                return Enum.valueOf(BossStatus.class, string);
            }

            private static /* synthetic */ BossStatus[] a() {
                return new BossStatus[]{ALIVE, DEAD, READY, RESPAWN};
            }

            static {
                a = BossStatus.a();
            }
        }
    }
}
