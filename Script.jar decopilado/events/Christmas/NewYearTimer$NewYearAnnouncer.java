/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.tables.SkillTable
 */
package events.Christmas;

import events.Christmas.NewYearTimer;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.tables.SkillTable;

private class NewYearTimer.NewYearAnnouncer
extends RunnableImpl {
    private final String message;

    private NewYearTimer.NewYearAnnouncer(String string) {
        this.message = string;
    }

    public void runImpl() throws Exception {
        Announcements.getInstance().announceToAll(this.message);
        if (this.message.length() == 1) {
            return;
        }
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            Skill skill = SkillTable.getInstance().getInfo(3266, 1);
            MagicSkillUse magicSkillUse = new MagicSkillUse((Creature)player, (Creature)player, 3266, 1, skill.getHitTime(), 0L);
            player.broadcastPacket(new L2GameServerPacket[]{magicSkillUse});
        }
        a = null;
        new NewYearTimer();
    }
}
