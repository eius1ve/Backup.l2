/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _259_RanchersPlea
extends Quest
implements ScriptFile {
    private static final int atH = 1495;
    private static final int atI = 1061;
    private static final int atJ = 17;
    private static final int atK = 1835;
    private static final int atL = 2509;
    private static final int atM = 30497;
    private static final int atN = 30405;
    private static final int atO = 20103;
    private static final int atP = 20106;
    private static final int atQ = 20108;

    public _259_RanchersPlea() {
        super(0);
        this.addStartNpc(30497);
        this.addTalkId(new int[]{30405});
        this.addKillId(new int[]{20103, 20106, 20108});
        this.addQuestItem(new int[]{1495});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30497) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.playSound("ItemSound.quest_accept");
                questState.setState(2);
                string2 = "edmond_q0259_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "edmond_q0259_06.htm";
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "edmond_q0259_07.htm";
            }
        } else if (n == 30405) {
            if (string.equalsIgnoreCase("reply_1")) {
                string2 = "marius_q0259_03.htm";
            } else if (string.equalsIgnoreCase("reply_2") && questState.getQuestItemsCount(1495) >= 10L) {
                string2 = "marius_q0259_04.htm";
                questState.giveItems(1061, 2L);
                questState.takeItems(1495, 10L);
                this.giveExtraReward(questState.getPlayer());
            } else if (string.equalsIgnoreCase("reply_3") && questState.getQuestItemsCount(1495) >= 10L) {
                string2 = "marius_q0259_05.htm";
                questState.giveItems(17, 250L);
                questState.takeItems(1495, 10L);
                this.giveExtraReward(questState.getPlayer());
            } else if (string.equalsIgnoreCase("reply_4")) {
                if (questState.getQuestItemsCount(1495) >= 10L) {
                    string2 = "marius_q0259_06.htm";
                } else if (questState.getQuestItemsCount(1495) < 10L) {
                    string2 = "marius_q0259_07.htm";
                }
            } else if (string.equalsIgnoreCase("reply_5") && questState.getQuestItemsCount(1495) >= 10L) {
                string2 = "marius_q0259_05a.htm";
                questState.giveItems(1835, 60L);
                questState.takeItems(1495, 10L);
                this.giveExtraReward(questState.getPlayer());
            } else if (string.equalsIgnoreCase("reply_6") && questState.getQuestItemsCount(1495) >= 10L) {
                string2 = "marius_q0259_05c.htm";
                questState.giveItems(2509, 30L);
                questState.takeItems(1495, 10L);
                this.giveExtraReward(questState.getPlayer());
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30497) break;
                if (questState.getPlayer().getLevel() >= 15) {
                    string = "edmond_q0259_02.htm";
                    break;
                }
                string = "edmond_q0259_01.htm";
                break;
            }
            case 2: {
                if (n == 30497) {
                    if (questState.getCond() == 1 && questState.getQuestItemsCount(1495) < 1L) {
                        string = "edmond_q0259_04.htm";
                        break;
                    }
                    if (questState.getCond() != 1 || questState.getQuestItemsCount(1495) < 1L) break;
                    string = "edmond_q0259_05.htm";
                    if (questState.getQuestItemsCount(1495) >= 10L) {
                        questState.giveItems(57, questState.getQuestItemsCount(1495) * 25L + 250L);
                    } else {
                        questState.giveItems(57, questState.getQuestItemsCount(1495) * 25L);
                    }
                    questState.takeItems(1495, -1L);
                    break;
                }
                if (n != 30405) break;
                if (questState.getCond() == 1 && questState.getQuestItemsCount(1495) < 10L) {
                    string = "marius_q0259_01.htm";
                    break;
                }
                if (questState.getCond() != 1 || questState.getQuestItemsCount(1495) < 10L) break;
                string = "marius_q0259_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getCond() > 0) {
            questState.rollAndGive(1495, 1, 100.0);
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
