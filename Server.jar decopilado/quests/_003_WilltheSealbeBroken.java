/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _003_WilltheSealbeBroken
extends Quest
implements ScriptFile {
    private static final int IT = 30141;
    private static final int IU = 20031;
    private static final int IV = 20041;
    private static final int IW = 20046;
    private static final int IX = 20048;
    private static final int IY = 20052;
    private static final int IZ = 20057;
    private static final int Ja = 956;
    private static final int Jb = 1081;
    private static final int Jc = 1082;
    private static final int Jd = 1083;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _003_WilltheSealbeBroken() {
        super(0);
        this.addStartNpc(30141);
        this.addKillId(new int[]{20031, 20041, 20046, 20048, 20052, 20057});
        this.addQuestItem(new int[]{1081, 1082, 1083});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30141 && string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.set("release_darkelf_elder1", String.valueOf(1), true);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "redry_q0003_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("release_darkelf_elder1");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30141) break;
                if (questState.getPlayer().getLevel() >= 16) {
                    if (questState.getPlayer().getRace() != Race.darkelf) {
                        questState.exitCurrentQuest(true);
                        string = "redry_q0003_00.htm";
                        break;
                    }
                    string = "redry_q0003_02.htm";
                    break;
                }
                string = "redry_q0003_01.htm";
                break;
            }
            case 2: {
                if (n2 != 30141) break;
                if (n == 1 && questState.getQuestItemsCount(1081) >= 1L && questState.getQuestItemsCount(1082) >= 1L && questState.getQuestItemsCount(1083) >= 1L) {
                    questState.giveItems(956, 1L);
                    questState.takeItems(1081, -1L);
                    questState.takeItems(1082, -1L);
                    questState.takeItems(1083, -1L);
                    questState.unset("release_darkelf_elder1");
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    string = "redry_q0003_06.htm";
                    break;
                }
                string = "redry_q0003_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("release_darkelf_elder1");
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 == 20031) {
                questState.giveItems(1081, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1082) >= 1L && questState.getQuestItemsCount(1083) >= 1L) {
                    questState.setCond(2);
                }
            } else if (n2 == 20041 || n2 == 20046) {
                questState.giveItems(1082, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1081) >= 1L && questState.getQuestItemsCount(1083) >= 1L) {
                    questState.setCond(2);
                }
            } else if (n2 == 20048 || n2 == 20052 || n2 == 20057) {
                questState.giveItems(1083, 1L);
                questState.playSound("ItemSound.quest_middle");
                if (questState.getQuestItemsCount(1081) >= 1L && questState.getQuestItemsCount(1082) >= 1L) {
                    questState.setCond(2);
                }
            }
        }
        return null;
    }
}
