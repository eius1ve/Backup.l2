/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.templates.npc.NpcTemplate;

static class RaidBossSpawnManager.1
extends RunnableImpl {
    final /* synthetic */ int val$npcId;

    RaidBossSpawnManager.1(int n) {
        this.val$npcId = n;
    }

    @Override
    public void runImpl() throws Exception {
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(this.val$npcId);
        if (npcTemplate != null) {
            Announcements.getInstance().announceByCustomMessage("l2.gameserver.instancemanager.RaidBossSpawnManager.AltAnnounceRaidbossSpawnSoon", new String[]{npcTemplate.getName()});
        }
    }
}
