/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.instancemanager.RaidBossSpawnManager
 *  l2.gameserver.instancemanager.RaidBossSpawnManager$Status
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.templates.npc.NpcTemplate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package handler.admincommands;

import bosses.AntharasManager;
import bosses.BaiumManager;
import bosses.EpicBossState;
import bosses.FrintezzaManager;
import bosses.ValakasManager;
import handler.admincommands.ScriptAdminCommand;
import java.text.SimpleDateFormat;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
import l2.gameserver.templates.npc.NpcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminBossStatus
extends ScriptAdminCommand {
    private static final Logger F = LoggerFactory.getLogger(AdminBossStatus.class);
    private static SimpleDateFormat a = new SimpleDateFormat("HH:mm dd.MM.yyyy");

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

    private static BossStatusInfo b(int n) {
        BossStatusInfo bossStatusInfo = AdminBossStatus.a(n);
        if (bossStatusInfo != null) {
            return bossStatusInfo;
        }
        if (RaidBossSpawnManager.getInstance().getRaidBossStatusId(n) != RaidBossSpawnManager.Status.UNDEFINED) {
            return new RaidBossStatusInfo(n);
        }
        return null;
    }

    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        switch (commands) {
            case admin_boss_status: {
                if (stringArray.length != 2) {
                    player.sendMessage("Usage: //boss_status <npc_id>");
                    return false;
                }
                int n = Integer.parseInt(stringArray[1]);
                try {
                    this.d(player, n);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return true;
            }
        }
        return false;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void d(Player player, int n) {
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n);
        BossStatusInfo bossStatusInfo = AdminBossStatus.b(n);
        if (bossStatusInfo == null) {
            player.sendMessage(String.format("No boss status for npc with id %d", n));
            return;
        }
        BossStatusInfo.BossStatus bossStatus = bossStatusInfo.getStatus();
        long l = bossStatusInfo.getRespawnDate();
        if (l > 0L) {
            player.sendMessage(String.format("Boss %d[%s] status: %s. Respawn date: %s", n, npcTemplate.getName(), bossStatus.toString(), a.format(l * 1000L)));
        } else {
            player.sendMessage(String.format("Boss %d[%s] status: %s", n, npcTemplate.getName(), bossStatus.toString()));
        }
    }

    public static final class EpicBossStatusInfo
    extends BossStatusInfo {
        private final EpicBossState a;

        public EpicBossStatusInfo(int n, EpicBossState epicBossState) {
            super(n);
            this.a = epicBossState;
        }

        @Override
        public BossStatusInfo.BossStatus getStatus() {
            switch (this.a.getState()) {
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
            return this.a.getRespawnDate() / 1000L;
        }
    }

    private static abstract class BossStatusInfo {
        private final int cC;

        public BossStatusInfo(int n) {
            this.cC = n;
        }

        public int getNpcId() {
            return this.cC;
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

    public static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_boss_status = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_boss_status};
        }

        static {
            a = Commands.a();
        }
    }
}
