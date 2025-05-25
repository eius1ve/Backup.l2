/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.entity.oly.ParticipantPool
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.ClanTable
 *  l2.gameserver.tables.SkillTable
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.tuple.Pair;

public class ClanBuffService
extends RunnableImpl
implements ScriptFile {
    private static final ClanBuffService a = new ClanBuffService();
    private static ScheduledFuture<?> ag;
    private static int bFC;
    private static List<Pair<Integer, Skill>> dL;

    private static final List<Pair<Integer, Skill>> e(String string) {
        ArrayList<Pair<Integer, Skill>> arrayList = new ArrayList<Pair<Integer, Skill>>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken().trim();
            if (string2.isEmpty()) continue;
            int n = Math.max(Math.max(string2.indexOf(44), string2.indexOf(45)), string2.indexOf(58));
            if (n <= 0) {
                throw new IllegalArgumentException("Can't parse \"" + string2 + "\"");
            }
            arrayList.add((Pair<Integer, Skill>)Pair.of((Object)Integer.parseInt(string2.substring(0, n).trim()), (Object)SkillTable.getInstance().getInfo(bFC, Integer.parseInt(string2.substring(n + 1).trim()))));
        }
        arrayList.sort(new Comparator<Pair<Integer, Skill>>(){

            @Override
            public int compare(Pair<Integer, Skill> pair, Pair<Integer, Skill> pair2) {
                return Integer.compare((Integer)pair.getKey(), (Integer)pair2.getKey());
            }
        });
        return arrayList;
    }

    private static Skill a(int n, List<Pair<Integer, Skill>> list) {
        Skill skill = null;
        for (Pair<Integer, Skill> pair : list) {
            if (n < (Integer)pair.getLeft()) {
                return skill;
            }
            skill = (Skill)pair.getRight();
        }
        return skill;
    }

    public void onLoad() {
        if (Config.SERVICES_CLAN_BUFF_ENABLED) {
            bFC = Config.SERVICES_CLAN_BUFF_SKILL_ID;
            dL = ClanBuffService.e(Config.SERVICES_CLAN_BUFF_LEVELS_MIN_ONLINE);
            ag = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)a), 60000L, Config.SERVICES_CLAN_BUFF_TASK_DELAY * 60L * 1000L);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        if (ag != null) {
            ag.cancel(false);
            ag = null;
        }
    }

    public void runImpl() throws Exception {
        for (Clan clan : ClanTable.getInstance().getClans()) {
            List list = clan.getOnlineMembers(0);
            if (list.isEmpty()) continue;
            Skill skill = ClanBuffService.a(list.size(), dL);
            if (skill == null) {
                for (Player player : list) {
                    player.getEffectList().stopEffect(bFC);
                }
                continue;
            }
            for (Player player : list) {
                if (player.isDead() || player.isInDuel() || player.isAlikeDead() || player.isOlyParticipant() || ParticipantPool.getInstance().isRegistred(player)) continue;
                skill.getEffects((Creature)player, (Creature)player, false, false, 0L, 0.0, false);
            }
        }
    }
}
