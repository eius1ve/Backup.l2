/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _381_LetsBecomeARoyalMember
extends Quest
implements ScriptFile {
    private static int aUG = 5899;
    private static int aUH = 5900;
    private static int aFs = 3813;
    private static int aUI = 7569;
    private static int aUJ = 5898;
    private static int aFW = 30232;
    private static int aUK = 30090;
    private static int aUL = 21018;
    private static int aUM = 27316;
    private static int aUN = 5;
    private static int aUO = 100;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _381_LetsBecomeARoyalMember() {
        super(0);
        this.addStartNpc(aFW);
        this.addTalkId(new int[]{aUK});
        this.addKillId(new int[]{aUL});
        this.addKillId(new int[]{aUM});
        this.addQuestItem(new int[]{aUG});
        this.addQuestItem(new int[]{aUH});
        this.addQuestItem(new int[]{aUI});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("warehouse_keeper_sorint_q0381_02.htm")) {
            if (questState.getPlayer().getLevel() >= 55 && questState.getQuestItemsCount(aFs) > 0L) {
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "warehouse_keeper_sorint_q0381_03.htm";
            } else {
                string2 = "warehouse_keeper_sorint_q0381_02.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (string.equalsIgnoreCase("sandra_q0381_02.htm") && questState.getCond() == 1) {
            questState.set("id", "1");
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        int n2 = npcInstance.getNpcId();
        long l = questState.getQuestItemsCount(aUH);
        if (n2 == aFW) {
            if (n == 0) {
                string = "warehouse_keeper_sorint_q0381_01.htm";
            } else if (n == 1) {
                long l2 = questState.getQuestItemsCount(aUG);
                if (l2 > 0L && l > 0L) {
                    questState.takeItems(aUG, -1L);
                    questState.takeItems(aUH, -1L);
                    questState.giveItems(aUJ, 1L);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string = "warehouse_keeper_sorint_q0381_06.htm";
                } else if (l == 0L) {
                    string = "warehouse_keeper_sorint_q0381_05.htm";
                } else if (l2 == 0L) {
                    string = "warehouse_keeper_sorint_q0381_04.htm";
                }
            }
        } else {
            long l3 = questState.getQuestItemsCount(aUI);
            if (l > 0L) {
                string = "sandra_q0381_05.htm";
            } else if (l3 > 0L) {
                questState.takeItems(aUI, -1L);
                questState.giveItems(aUH, 1L);
                questState.playSound("ItemSound.quest_itemget");
                string = "sandra_q0381_04.htm";
            } else {
                string = questState.getInt("id") == 0 ? "sandra_q0381_01.htm" : "sandra_q0381_03.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        long l = questState.getQuestItemsCount(aUH);
        long l2 = questState.getQuestItemsCount(aUG);
        long l3 = questState.getQuestItemsCount(aUI);
        if (n == aUL && l2 == 0L) {
            if (Rnd.chance((int)aUN)) {
                questState.giveItems(aUG, 1L);
                if (l > 0L || l3 > 0L) {
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (n == aUM && l3 + l == 0L && questState.getInt("id") != 0 && Rnd.chance((int)aUO)) {
            questState.giveItems(aUI, 1L);
            if (l2 > 0L) {
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
