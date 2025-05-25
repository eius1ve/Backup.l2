/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntArrayList
 *  gnu.trove.TIntObjectHashMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.instances;

import gnu.trove.TIntArrayList;
import gnu.trove.TIntObjectHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.TamedBeastInstance;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedableBeastInstance
extends MonsterInstance {
    private static final Logger ch;
    private static int mh;
    private static int mi;
    private static int mj;
    private static int mk;
    public static final TIntObjectHashMap<growthInfo> growthCapableMobs;
    public static final TIntArrayList tamedBeasts;
    public static final TIntArrayList feedableBeasts;
    public static Map<Integer, Integer> feedInfo;

    private boolean e(int n) {
        return n == 2188;
    }

    private boolean f(int n) {
        return n == 2189;
    }

    private int h(int n) {
        if (this.e(n)) {
            return 6643;
        }
        return 6644;
    }

    public int getItemIdBySkillId(int n) {
        int n2 = 0;
        switch (n) {
            case 2188: {
                n2 = 6643;
                break;
            }
            case 2189: {
                n2 = 6644;
                break;
            }
            default: {
                n2 = 0;
            }
        }
        return n2;
    }

    public FeedableBeastInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    private void b(Player player, int n, int n2) {
        int n3 = this.getNpcId();
        int n4 = 0;
        n4 = ((growthInfo)FeedableBeastInstance.growthCapableMobs.get((int)n3)).spice[n2][Rnd.get(((growthInfo)FeedableBeastInstance.growthCapableMobs.get((int)n3)).spice[n2].length)];
        feedInfo.remove(this.getObjectId());
        if (((growthInfo)FeedableBeastInstance.growthCapableMobs.get((int)n3)).growth_level == 0) {
            this.onDecay();
        } else {
            this.deleteMe();
        }
        if (tamedBeasts.contains(n4)) {
            if (player.getTrainedBeast() != null) {
                player.getTrainedBeast().doDespawn();
            }
            NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n4);
            TamedBeastInstance tamedBeastInstance = new TamedBeastInstance(IdFactory.getInstance().getNextId(), npcTemplate);
            Location location = player.getLoc();
            location.x += Rnd.get(-50, 50);
            location.y += Rnd.get(-50, 50);
            tamedBeastInstance.spawnMe(location);
            tamedBeastInstance.setTameType(player);
            tamedBeastInstance.setFoodType(this.h(n2 == mh ? mj : mk));
            tamedBeastInstance.setRunning();
            tamedBeastInstance.setOwner(player);
            QuestState questState = player.getQuestState("_020_BringUpWithLove");
            if (questState != null && !questState.isCompleted() && Rnd.chance(5) && questState.getQuestItemsCount(7185) == 0L) {
                questState.giveItems(7185, 1L);
                questState.setCond(2);
            }
            if ((questState = player.getQuestState("_655_AGrandPlanForTamingWildBeasts")) != null && !questState.isCompleted() && questState.getCond() == 1 && questState.getQuestItemsCount(8084) < 10L) {
                questState.giveItems(8084, 1L);
            }
        } else {
            MonsterInstance monsterInstance = this.spawn(n4, this.getX(), this.getY(), this.getZ());
            feedInfo.put(monsterInstance.getObjectId(), player.getObjectId());
            player.setObjectTarget(monsterInstance);
            ThreadPoolManager.getInstance().schedule(new AggrPlayer(monsterInstance, player), 3000L);
        }
    }

    @Override
    protected void onDeath(Creature creature) {
        feedInfo.remove(this.getObjectId());
        super.onDeath(creature);
    }

    public MonsterInstance spawn(int n, int n2, int n3, int n4) {
        try {
            MonsterInstance monsterInstance = (MonsterInstance)NpcHolder.getInstance().getTemplate(n).getInstanceConstructor().newInstance(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(n));
            monsterInstance.setSpawnedLoc(new Location(n2, n3, n4));
            monsterInstance.spawnMe(monsterInstance.getSpawnedLoc());
            return monsterInstance;
        }
        catch (Exception exception) {
            ch.error("Could not spawn Npc " + n, (Throwable)exception);
            return null;
        }
    }

    public void onSkillUse(Player player, int n) {
        int n2 = this.getNpcId();
        if (!feedableBeasts.contains(n2)) {
            return;
        }
        if (this.e(n) && this.f(n)) {
            return;
        }
        int n3 = this.e(n) ? 0 : 1;
        int n4 = this.getObjectId();
        this.broadcastPacket(new SocialAction(n4, 2));
        if (growthCapableMobs.containsKey(n2)) {
            if (((growthInfo)FeedableBeastInstance.growthCapableMobs.get((int)n2)).spice[n3].length == 0) {
                return;
            }
            int n5 = ((growthInfo)FeedableBeastInstance.growthCapableMobs.get((int)n2)).growth_level;
            if (n5 > 0 && feedInfo.get(n4) != null && feedInfo.get(n4).intValue() != player.getObjectId()) {
                return;
            }
            if (Rnd.chance(((growthInfo)FeedableBeastInstance.growthCapableMobs.get((int)n2)).growth_chance)) {
                this.b(player, n5, n3);
            }
        } else if (Rnd.chance(60)) {
            this.dropItem(player, this.getItemIdBySkillId(n), 1L);
        }
    }

    static {
        Integer n;
        ch = LoggerFactory.getLogger(NpcInstance.class);
        mh = 0;
        mi = 1;
        mj = 2188;
        mk = 2189;
        growthCapableMobs = new TIntObjectHashMap();
        tamedBeasts = new TIntArrayList();
        feedableBeasts = new TIntArrayList();
        growthCapableMobs.put(21451, (Object)new growthInfo(0, new int[][]{{21452, 21453, 21454, 21455}, {21456, 21457, 21458, 21459}}, 100));
        growthCapableMobs.put(21452, (Object)new growthInfo(1, new int[][]{{21460, 21462}, new int[0]}, 40));
        growthCapableMobs.put(21453, (Object)new growthInfo(1, new int[][]{{21461, 21463}, new int[0]}, 40));
        growthCapableMobs.put(21454, (Object)new growthInfo(1, new int[][]{{21460, 21462}, new int[0]}, 40));
        growthCapableMobs.put(21455, (Object)new growthInfo(1, new int[][]{{21461, 21463}, new int[0]}, 40));
        growthCapableMobs.put(21456, (Object)new growthInfo(1, new int[][]{new int[0], {21464, 21466}}, 40));
        growthCapableMobs.put(21457, (Object)new growthInfo(1, new int[][]{new int[0], {21465, 21467}}, 40));
        growthCapableMobs.put(21458, (Object)new growthInfo(1, new int[][]{new int[0], {21464, 21466}}, 40));
        growthCapableMobs.put(21459, (Object)new growthInfo(1, new int[][]{new int[0], {21465, 21467}}, 40));
        growthCapableMobs.put(21460, (Object)new growthInfo(2, new int[][]{{21468, 16017}, new int[0]}, 25));
        growthCapableMobs.put(21461, (Object)new growthInfo(2, new int[][]{{21469, 16018}, new int[0]}, 25));
        growthCapableMobs.put(21462, (Object)new growthInfo(2, new int[][]{{21468, 16017}, new int[0]}, 25));
        growthCapableMobs.put(21463, (Object)new growthInfo(2, new int[][]{{21469, 16018}, new int[0]}, 25));
        growthCapableMobs.put(21464, (Object)new growthInfo(2, new int[][]{new int[0], {21468, 16017}}, 25));
        growthCapableMobs.put(21465, (Object)new growthInfo(2, new int[][]{new int[0], {21469, 16018}}, 25));
        growthCapableMobs.put(21466, (Object)new growthInfo(2, new int[][]{new int[0], {21468, 16017}}, 25));
        growthCapableMobs.put(21467, (Object)new growthInfo(2, new int[][]{new int[0], {21469, 16018}}, 25));
        growthCapableMobs.put(21470, (Object)new growthInfo(0, new int[][]{{21472, 21474, 21471, 21473}, {21475, 21476, 21477, 21478}}, 100));
        growthCapableMobs.put(21471, (Object)new growthInfo(1, new int[][]{{21479, 21481}, new int[0]}, 40));
        growthCapableMobs.put(21472, (Object)new growthInfo(1, new int[][]{{21480, 21482}, new int[0]}, 40));
        growthCapableMobs.put(21473, (Object)new growthInfo(1, new int[][]{{21479, 21481}, new int[0]}, 40));
        growthCapableMobs.put(21474, (Object)new growthInfo(1, new int[][]{{21480, 21482}, new int[0]}, 40));
        growthCapableMobs.put(21475, (Object)new growthInfo(1, new int[][]{new int[0], {21483, 21485}}, 40));
        growthCapableMobs.put(21476, (Object)new growthInfo(1, new int[][]{new int[0], {21484, 21486}}, 40));
        growthCapableMobs.put(21477, (Object)new growthInfo(1, new int[][]{new int[0], {21483, 21485}}, 40));
        growthCapableMobs.put(21478, (Object)new growthInfo(1, new int[][]{new int[0], {21484, 21486}}, 40));
        growthCapableMobs.put(21479, (Object)new growthInfo(2, new int[][]{{21487, 16014}, new int[0]}, 25));
        growthCapableMobs.put(21480, (Object)new growthInfo(2, new int[][]{{21488, 16013}, new int[0]}, 25));
        growthCapableMobs.put(21481, (Object)new growthInfo(2, new int[][]{{21487, 16014}, new int[0]}, 25));
        growthCapableMobs.put(21482, (Object)new growthInfo(2, new int[][]{{21488, 16013}, new int[0]}, 25));
        growthCapableMobs.put(21483, (Object)new growthInfo(2, new int[][]{new int[0], {21487, 16014}}, 25));
        growthCapableMobs.put(21484, (Object)new growthInfo(2, new int[][]{new int[0], {21488, 16013}}, 25));
        growthCapableMobs.put(21485, (Object)new growthInfo(2, new int[][]{new int[0], {21487, 16014}}, 25));
        growthCapableMobs.put(21486, (Object)new growthInfo(2, new int[][]{new int[0], {21488, 16013}}, 25));
        growthCapableMobs.put(21489, (Object)new growthInfo(0, new int[][]{{21491, 21493, 21490, 21492}, {21495, 21497, 21494, 21496}}, 100));
        growthCapableMobs.put(21490, (Object)new growthInfo(1, new int[][]{{21498, 21500}, new int[0]}, 40));
        growthCapableMobs.put(21491, (Object)new growthInfo(1, new int[][]{{21499, 21501}, new int[0]}, 40));
        growthCapableMobs.put(21492, (Object)new growthInfo(1, new int[][]{{21498, 21500}, new int[0]}, 40));
        growthCapableMobs.put(21493, (Object)new growthInfo(1, new int[][]{{21499, 21501}, new int[0]}, 40));
        growthCapableMobs.put(21494, (Object)new growthInfo(1, new int[][]{new int[0], {21502, 21504}}, 40));
        growthCapableMobs.put(21495, (Object)new growthInfo(1, new int[][]{new int[0], {21503, 21505}}, 40));
        growthCapableMobs.put(21496, (Object)new growthInfo(1, new int[][]{new int[0], {21502, 21504}}, 40));
        growthCapableMobs.put(21497, (Object)new growthInfo(1, new int[][]{new int[0], {21503, 21505}}, 40));
        growthCapableMobs.put(21498, (Object)new growthInfo(2, new int[][]{{21506, 16015}, new int[0]}, 25));
        growthCapableMobs.put(21499, (Object)new growthInfo(2, new int[][]{{21507, 16016}, new int[0]}, 25));
        growthCapableMobs.put(21500, (Object)new growthInfo(2, new int[][]{{21506, 16015}, new int[0]}, 25));
        growthCapableMobs.put(21501, (Object)new growthInfo(2, new int[][]{{21507, 16015}, new int[0]}, 25));
        growthCapableMobs.put(21502, (Object)new growthInfo(2, new int[][]{new int[0], {21506, 16015}}, 25));
        growthCapableMobs.put(21503, (Object)new growthInfo(2, new int[][]{new int[0], {21507, 16016}}, 25));
        growthCapableMobs.put(21504, (Object)new growthInfo(2, new int[][]{new int[0], {21506, 16015}}, 25));
        growthCapableMobs.put(21505, (Object)new growthInfo(2, new int[][]{new int[0], {21507, 16016}}, 25));
        Integer n2 = 16013;
        while (n2 <= 16018) {
            tamedBeasts.add(n2.intValue());
            n = n2;
            n2 = n2 + 1;
        }
        n2 = 16013;
        while (n2 <= 16019) {
            feedableBeasts.add(n2.intValue());
            n = n2;
            n2 = n2 + 1;
        }
        n2 = 21451;
        while (n2 <= 21507) {
            feedableBeasts.add(n2.intValue());
            n = n2;
            n2 = n2 + 1;
        }
        n2 = 21824;
        while (n2 <= 21829) {
            feedableBeasts.add(n2.intValue());
            n = n2;
            n2 = n2 + 1;
        }
        feedInfo = new ConcurrentHashMap<Integer, Integer>();
    }

    private static class growthInfo {
        public int growth_level;
        public int growth_chance;
        public int[][] spice;

        public growthInfo(int n, int[][] nArray, int n2) {
            this.growth_level = n;
            this.spice = nArray;
            this.growth_chance = n2;
        }
    }

    public static class AggrPlayer
    extends RunnableImpl {
        private NpcInstance w;
        private Player l;

        public AggrPlayer(NpcInstance npcInstance, Player player) {
            this.w = npcInstance;
            this.l = player;
        }

        @Override
        public void runImpl() throws Exception {
            this.w.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, this.l, 1000);
        }
    }
}
