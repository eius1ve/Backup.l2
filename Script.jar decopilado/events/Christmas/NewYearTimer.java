/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 */
package events.Christmas;

import java.util.Calendar;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;

public class NewYearTimer
implements ScriptFile {
    private static NewYearTimer a;

    public static NewYearTimer getInstance() {
        if (a == null) {
            new NewYearTimer();
        }
        return a;
    }

    public NewYearTimer() {
        if (a != null) {
            return;
        }
        a = this;
        if (!NewYearTimer.isActive()) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Calendar.getInstance().get(1));
        calendar.set(2, 0);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        while (this.a(calendar) < 0L) {
            calendar.set(1, calendar.get(1) + 1);
        }
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new NewYearAnnouncer("\u0421 \u041d\u043e\u0432\u044b\u043c, " + calendar.get(1) + ", \u0413\u043e\u0434\u043e\u043c!!!")), this.a(calendar));
        calendar.add(13, -1);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new NewYearAnnouncer("1")), this.a(calendar));
        calendar.add(13, -1);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new NewYearAnnouncer("2")), this.a(calendar));
        calendar.add(13, -1);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new NewYearAnnouncer("3")), this.a(calendar));
        calendar.add(13, -1);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new NewYearAnnouncer("4")), this.a(calendar));
        calendar.add(13, -1);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new NewYearAnnouncer("5")), this.a(calendar));
    }

    private long a(Calendar calendar) {
        return calendar.getTime().getTime() - System.currentTimeMillis();
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    private static boolean isActive() {
        return ServerVariables.getString((String)"Christmas", (String)"off").equalsIgnoreCase("on");
    }

    public void onShutdown() {
    }

    private class NewYearAnnouncer
    extends RunnableImpl {
        private final String message;

        private NewYearAnnouncer(String string) {
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
}
