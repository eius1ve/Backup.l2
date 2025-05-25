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

public class _051_OFullesSpecialBait
extends Quest
implements ScriptFile {
    int OFulle = 31572;
    int FetteredSoul = 20552;
    int LostBaitIngredient = 7622;
    int IcyAirFishingLure = 7611;
    Integer FishSkill = 1315;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _051_OFullesSpecialBait() {
        super(0);
        this.addStartNpc(this.OFulle);
        this.addTalkId(new int[]{this.OFulle});
        this.addKillId(new int[]{this.FetteredSoul});
        this.addQuestItem(new int[]{this.LostBaitIngredient});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("fisher_ofulle_q0051_0104.htm")) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("fisher_ofulle_q0051_0201.htm")) {
            if (questState.getQuestItemsCount(this.LostBaitIngredient) < 100L) {
                string2 = "fisher_ofulle_q0051_0202.htm";
            } else {
                questState.unset("cond");
                questState.takeItems(this.LostBaitIngredient, -1L);
                questState.giveItems(this.IcyAirFishingLure, 4L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        int n3 = questState.getState();
        if (n == this.OFulle) {
            if (n3 == 1) {
                if (questState.getPlayer().getLevel() < 36) {
                    string = "fisher_ofulle_q0051_0103.htm";
                    questState.exitCurrentQuest(true);
                } else if (questState.getPlayer().getSkillLevel(this.FishSkill) >= 11) {
                    string = "fisher_ofulle_q0051_0101.htm";
                } else {
                    string = "fisher_ofulle_q0051_0102.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1 || n2 == 2) {
                if (questState.getQuestItemsCount(this.LostBaitIngredient) < 100L) {
                    string = "fisher_ofulle_q0051_0106.htm";
                    questState.setCond(1);
                } else {
                    string = "fisher_ofulle_q0051_0105.htm";
                }
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == this.FetteredSoul && questState.getCond() == 1 && questState.getQuestItemsCount(this.LostBaitIngredient) < 100L && Rnd.chance((int)30)) {
            questState.giveItems(this.LostBaitIngredient, 1L);
            if (questState.getQuestItemsCount(this.LostBaitIngredient) == 100L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
