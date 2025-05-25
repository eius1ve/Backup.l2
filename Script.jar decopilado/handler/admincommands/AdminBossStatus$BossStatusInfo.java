/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package handler.admincommands;

import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.templates.npc.NpcTemplate;

private static abstract class AdminBossStatus.BossStatusInfo {
    private final int cC;

    public AdminBossStatus.BossStatusInfo(int n) {
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
