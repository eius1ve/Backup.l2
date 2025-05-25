/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.HashMap;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _382_KailsMagicCoin
extends Quest
implements ScriptFile {
    private static int aUJ = 5898;
    private static int aUP = 30687;
    private static final Map<Integer, int[]> cf = new HashMap<Integer, int[]>();

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _382_KailsMagicCoin() {
        super(0);
        this.addStartNpc(aUP);
        for (int n : cf.keySet()) {
            this.addKillId(new int[]{n});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("head_blacksmith_vergara_q0382_03.htm")) {
            if (questState.getPlayer().getLevel() >= 55 && questState.getQuestItemsCount(aUJ) > 0L) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
            } else {
                string2 = "head_blacksmith_vergara_q0382_01.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (string.equalsIgnoreCase("list")) {
            MultiSellHolder.getInstance().SeparateAndSend(382, questState.getPlayer(), 0.0);
            string2 = null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (questState.getQuestItemsCount(aUJ) == 0L || questState.getPlayer().getLevel() < 55) {
            string = "head_blacksmith_vergara_q0382_01.htm";
            questState.exitCurrentQuest(true);
        } else {
            string = n == 0 ? "head_blacksmith_vergara_q0382_02.htm" : "head_blacksmith_vergara_q0382_04.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2 || questState.getQuestItemsCount(aUJ) == 0L) {
            return null;
        }
        int[] nArray = cf.get(npcInstance.getNpcId());
        questState.rollAndGive(nArray[Rnd.get((int)nArray.length)], 1, 10.0);
        return null;
    }

    static {
        cf.put(21017, new int[]{5961});
        cf.put(21019, new int[]{5962});
        cf.put(21020, new int[]{5963});
        cf.put(21022, new int[]{5961, 5962, 5963});
    }
}
