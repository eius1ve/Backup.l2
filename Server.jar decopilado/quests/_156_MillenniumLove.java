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

public class _156_MillenniumLove
extends Quest
implements ScriptFile {
    int LILITHS_LETTER = 1022;
    int THEONS_DIARY = 1023;
    int GR_COMP_PACKAGE_SS = 5250;
    int GR_COMP_PACKAGE_SPS = 5256;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _156_MillenniumLove() {
        super(0);
        this.addStartNpc(30368);
        this.addTalkId(new int[]{30368});
        this.addTalkId(new int[]{30368});
        this.addTalkId(new int[]{30368});
        this.addTalkId(new int[]{30369});
        this.addQuestItem(new int[]{this.LILITHS_LETTER, this.THEONS_DIARY});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("30368-06.htm")) {
            questState.giveItems(this.LILITHS_LETTER, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("156_1")) {
            questState.takeItems(this.LILITHS_LETTER, -1L);
            if (questState.getQuestItemsCount(this.THEONS_DIARY) == 0L) {
                questState.giveItems(this.THEONS_DIARY, 1L);
                questState.setCond(2);
            }
            string2 = "30369-03.htm";
        } else if (string.equals("156_2")) {
            questState.takeItems(this.LILITHS_LETTER, -1L);
            questState.playSound("ItemSound.quest_finish");
            string2 = "30369-04.htm";
            questState.exitCurrentQuest(false);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30368) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 15) {
                    string = "30368-02.htm";
                } else {
                    string = "30368-05.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1 && questState.getQuestItemsCount(this.LILITHS_LETTER) == 1L) {
                string = "30368-07.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(this.THEONS_DIARY) == 1L) {
                questState.takeItems(this.THEONS_DIARY, -1L);
                if (questState.getPlayer().getClassId().isMage()) {
                    questState.giveItems(this.GR_COMP_PACKAGE_SPS, 1L);
                } else {
                    questState.giveItems(this.GR_COMP_PACKAGE_SS, 1L);
                }
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                string = "30368-08.htm";
                questState.exitCurrentQuest(false);
            }
        } else if (n == 30369) {
            if (n2 == 1 && questState.getQuestItemsCount(this.LILITHS_LETTER) == 1L) {
                string = "30369-02.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(this.THEONS_DIARY) == 1L) {
                string = "30369-05.htm";
            }
        }
        return string;
    }
}
