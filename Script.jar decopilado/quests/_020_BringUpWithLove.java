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

public class _020_BringUpWithLove
extends Quest
implements ScriptFile {
    private static final int JY = 31537;
    private static final int JZ = 7185;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _020_BringUpWithLove() {
        super(0);
        this.addStartNpc(31537);
        this.addTalkId(new int[]{31537});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("givemelove");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31537) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("givemelove", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "beast_herder_tunatun_q0020_09.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "beast_herder_tunatun_q0020_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "beast_herder_tunatun_q0020_04.htm";
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "beast_herder_tunatun_q0020_05.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                string2 = "beast_herder_tunatun_q0020_06.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                string2 = "beast_herder_tunatun_q0020_08.htm";
            } else if (string.equalsIgnoreCase("reply_6") && n == 1 && questState.getQuestItemsCount(7185) >= 1L) {
                questState.takeItems(7185, -1L);
                questState.giveItems(57, 68500L);
                questState.unset("givemelove");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
                string2 = "beast_herder_tunatun_q0020_12.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("givemelove");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31537) break;
                if (questState.getPlayer().getLevel() >= 65) {
                    string = "beast_herder_tunatun_q0020_01.htm";
                    break;
                }
                questState.exitCurrentQuest(true);
                string = "beast_herder_tunatun_q0020_02.htm";
                break;
            }
            case 2: {
                if (n2 != 31537) break;
                if (n == 1 && questState.getQuestItemsCount(7185) == 0L) {
                    string = "beast_herder_tunatun_q0020_10.htm";
                    break;
                }
                if (n != 1 || questState.getQuestItemsCount(7185) < 1L) break;
                string = "beast_herder_tunatun_q0020_11.htm";
            }
        }
        return string;
    }
}
