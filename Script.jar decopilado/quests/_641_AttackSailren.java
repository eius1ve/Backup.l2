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
import quests._126_IntheNameofEvilPart2;

public class _641_AttackSailren
extends Quest
implements ScriptFile {
    private static int bzJ = 32109;
    private static int bzK = 22196;
    private static int bzL = 22197;
    private static int bzM = 22198;
    private static int bzN = 22218;
    private static int bzO = 22223;
    private static int bzP = 22199;
    private static int bzQ = 8782;
    private static int HL = 8784;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _641_AttackSailren() {
        super(1);
        this.addStartNpc(bzJ);
        this.addKillId(new int[]{bzK});
        this.addKillId(new int[]{bzL});
        this.addKillId(new int[]{bzM});
        this.addKillId(new int[]{bzN});
        this.addKillId(new int[]{bzO});
        this.addKillId(new int[]{bzP});
        this.addQuestItem(new int[]{bzQ});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("statue_of_shilen_q0641_05.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("statue_of_shilen_q0641_08.htm")) {
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.takeItems(bzQ, -1L);
            questState.giveItems(HL, 1L);
            questState.exitCurrentQuest(true);
            questState.unset("cond");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            QuestState questState2 = questState.getPlayer().getQuestState(_126_IntheNameofEvilPart2.class);
            if (questState2 == null || !questState2.isCompleted()) {
                string = "statue_of_shilen_q0641_02.htm";
            } else if (questState.getPlayer().getLevel() >= 77) {
                string = "statue_of_shilen_q0641_01.htm";
            } else {
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1) {
            string = "statue_of_shilen_q0641_05.htm";
        } else if (n == 2) {
            string = "statue_of_shilen_q0641_07.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(bzQ) < 30L) {
            questState.giveItems(bzQ, 1L);
            if (questState.getQuestItemsCount(bzQ) == 30L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(2);
                questState.setState(2);
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
