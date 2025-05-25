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

import bosses.AntharasManager;
import bosses.BaiumManager;
import bosses.EpicBossState;
import bosses.FrintezzaManager;
import bosses.ValakasManager;
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
public class BossStatusService
extends Functions
implements IVoicedCommandHandler,
ScriptFile {
    private String[] o = new String[]{"boss_status", "epic"};
    private static BossStatusInfo[] a = null;
    private static SimpleDateFormat a = new SimpleDateFormat(Config.SERVICES_BOSS_STATUS_FORMAT);

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICES_BOSS_STATUS_ENABLE) {
            return false;
        }
        if (this.o[0].equalsIgnoreCase(string) || this.o[1].equalsIgnoreCase(string)) {
            this.listBossStatusesWithMsg(player);
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.SERVICES_BOSS_STATUS_ENABLE) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return this.o;
    }

    private static BossStatusInfo a(int n) {
        switch (n) {
            case 29020: {
                return new EpicBossStatusInfo(n, BaiumManager.getEpicBossState());
            }
            case 29028: {
                return new EpicBossStatusInfo(n, ValakasManager.getEpicBossState());
            }
            case 29045: {
                return new EpicBossStatusInfo(n, FrintezzaManager.getInstance().getEpicBossState());
            }
            case 29068: {
                return new EpicBossStatusInfo(n, AntharasManager.getEpicBossState());
            }
        }
        return null;
    }

    private static BossStatusInfo[] a() {
        ArrayList<BossStatusInfo> arrayList = new ArrayList<BossStatusInfo>();
        for (int n : Config.SERVICES_BOSS_STATUS_ADDITIONAL_IDS) {
            BossStatusInfo bossStatusInfo = BossStatusService.a(n);
            if (null != bossStatusInfo) {
                arrayList.add(bossStatusInfo);
                continue;
            }
            arrayList.add(new RaidBossStatusInfo(n));
        }
        return arrayList.toArray(new BossStatusInfo[arrayList.size()]);
    }

    private static BossStatusInfo[] b() {
        if (a == null) {
            a = BossStatusService.a();
            return a;
        }
        return a;
    }

    private static String a(Player player, BossStatusInfo bossStatusInfo) {
        String string = StringHolder.getInstance().getNotNull(player, "scripts.services.BossStatusService." + bossStatusInfo.getStatus());
        long l = bossStatusInfo.getRespawnDate();
        string = string.replace("%name%", bossStatusInfo.getName());
        string = string.replace("%npc_id%", String.valueOf(bossStatusInfo.getNpcId()));
        string = string.replace("%respawn_date%", l > 0L ? a.format(l * 1000L) : "");
        return string;
    }

    public void listBossStatusesWithMsg(Player player) {
        if (!Config.SERVICES_BOSS_STATUS_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = BossStatusService.b();
        StringBuilder stringBuilder = new StringBuilder();
        for (BossStatusInfo bossStatusInfo : npcHtmlMessage) {
            stringBuilder.append(BossStatusService.a(player, bossStatusInfo));
        }
        NpcHtmlMessage npcHtmlMessage2 = new NpcHtmlMessage(player, null);
        npcHtmlMessage2.setFile("scripts/services/command_bossstatus.htm");
        npcHtmlMessage2.replace("%list%", stringBuilder.toString());
        npcHtmlMessage2.replace("%current_date%", TimeUtils.toSimpleFormat((long)System.currentTimeMillis()));
        player.sendPacket((IStaticPacket)npcHtmlMessage2);
    }

    public void listBossStatuses() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        NpcInstance npcInstance = this.getNpc();
        if (!Config.SERVICES_BOSS_STATUS_ENABLE) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = BossStatusService.b();
        StringBuilder stringBuilder = new StringBuilder();
        for (BossStatusInfo bossStatusInfo : npcHtmlMessage) {
            stringBuilder.append(BossStatusService.a(player, bossStatusInfo));
        }
        NpcHtmlMessage npcHtmlMessage2 = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage2.setFile("scripts/services/bossstatus.htm");
        npcHtmlMessage2.replace("%list%", stringBuilder.toString());
        npcHtmlMessage2.replace("%current_date%", TimeUtils.toSimpleFormat((long)System.currentTimeMillis()));
        player.sendPacket((IStaticPacket)npcHtmlMessage2);
    }

    public void listBossStatusesCommunity() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_BOSS_STATUS_ENABLE) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/service_disabled.htm");
            return;
        }
        BossStatusInfo[] bossStatusInfoArray = BossStatusService.b();
        StringBuilder stringBuilder = new StringBuilder();
        for (BossStatusInfo bossStatusInfo : bossStatusInfoArray) {
            stringBuilder.append(BossStatusService.a(player, bossStatusInfo));
        }
        Object object = HtmCache.getInstance().getNotNull("scripts/services/community/services/boss_status.htm", player);
        object = ((String)object).replace("%list%", stringBuilder.toString());
        object = ((String)object).replace("%current_date%", TimeUtils.toSimpleFormat((long)System.currentTimeMillis()));
        ShowBoard.separateAndSend((String)object, (Player)player);
    }

    public void onLoad() {
        if (Config.SERVICES_BOSS_STATUS_ENABLE) {
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }

    public static final class EpicBossStatusInfo
    extends BossStatusInfo {
        private final EpicBossState b;

        public EpicBossStatusInfo(int n, EpicBossState epicBossState) {
            super(n);
            this.b = epicBossState;
        }

        @Override
        public BossStatusInfo.BossStatus getStatus() {
            switch (this.b.getState()) {
                case ALIVE: {
                    return BossStatusInfo.BossStatus.ALIVE;
                }
                case NOTSPAWN: {
                    return BossStatusInfo.BossStatus.READY;
                }
                case DEAD: {
                    return BossStatusInfo.BossStatus.DEAD;
                }
            }
            return BossStatusInfo.BossStatus.RESPAWN;
        }

        @Override
        public long getRespawnDate() {
            return this.b.getRespawnDate() / 1000L;
        }
    }

    private static abstract class BossStatusInfo {
        private final int bFp;

        public BossStatusInfo(int n) {
            this.bFp = n;
        }

        public int getNpcId() {
            return this.bFp;
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
}
