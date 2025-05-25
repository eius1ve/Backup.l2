/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;

public class _151_CureforFeverDisease
extends Quest
implements ScriptFile {
    int POISON_SAC = 703;
    int FEVER_MEDICINE = 704;
    int ROUND_SHIELD = 102;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _151_CureforFeverDisease() {
        super(0);
        this.addStartNpc(30050);
        this.addTalkId(new int[]{30032});
        this.addKillId(new int[]{20103, 20106, 20108});
        this.addQuestItem(new int[]{this.FEVER_MEDICINE, this.POISON_SAC});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("30050-03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getState();
        int n3 = 0;
        if (n2 != 1) {
            n3 = questState.getCond();
        }
        if (n == 30050) {
            if (n3 == 0) {
                if (questState.getPlayer().getLevel() >= 15) {
                    string = "30050-02.htm";
                } else {
                    string = "30050-01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n3 == 1 && questState.getQuestItemsCount(this.POISON_SAC) == 0L && questState.getQuestItemsCount(this.FEVER_MEDICINE) == 0L) {
                string = "30050-04.htm";
            } else if (n3 == 1 && questState.getQuestItemsCount(this.POISON_SAC) == 1L) {
                string = "30050-05.htm";
            } else if (n3 == 3 && questState.getQuestItemsCount(this.FEVER_MEDICINE) == 1L) {
                questState.takeItems(this.FEVER_MEDICINE, -1L);
                questState.giveItems(this.ROUND_SHIELD, 1L);
                questState.getPlayer().addExpAndSp(13106L, 613L);
                this.giveExtraReward(questState.getPlayer());
                if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("p1q4")) {
                    questState.getPlayer().setVar("p1q4", "1", -1L);
                    questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Now go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                }
                string = "30050-06.htm";
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(false);
            }
        } else if (n == 30032) {
            if (n3 == 2 && questState.getQuestItemsCount(this.POISON_SAC) > 0L) {
                questState.giveItems(this.FEVER_MEDICINE, 1L);
                questState.takeItems(this.POISON_SAC, -1L);
                questState.setCond(3);
                string = "30032-01.htm";
            } else if (n3 == 3 && questState.getQuestItemsCount(this.FEVER_MEDICINE) > 0L) {
                string = "30032-02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if ((n == 20103 || n == 20106 || n == 20108) && questState.getQuestItemsCount(this.POISON_SAC) == 0L && questState.getCond() == 1 && Rnd.chance((int)50)) {
            questState.setCond(2);
            questState.giveItems(this.POISON_SAC, 1L);
            questState.playSound("ItemSound.quest_middle");
        }
        return null;
    }
}
