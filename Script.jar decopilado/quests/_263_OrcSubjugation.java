/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _263_OrcSubjugation
extends Quest
implements ScriptFile {
    private static final int atY = 30346;
    private static final int atZ = 20385;
    private static final int aua = 20386;
    private static final int aub = 20387;
    private static final int auc = 20388;
    private static final int aud = 1116;
    private static final int aue = 1117;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _263_OrcSubjugation() {
        super(0);
        this.addStartNpc(30346);
        this.addKillId(new int[]{20385, 20386, 20387, 20388});
        this.addQuestItem(new int[]{1116, 1117});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("sentry_kayleen_q0263_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("sentry_kayleen_q0263_06.htm")) {
            questState.setCond(0);
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() >= 8 && questState.getPlayer().getRace() == Race.darkelf) {
                string = "sentry_kayleen_q0263_02.htm";
                return string;
            }
            if (questState.getPlayer().getRace() != Race.darkelf) {
                string = "sentry_kayleen_q0263_00.htm";
                questState.exitCurrentQuest(true);
            } else if (questState.getPlayer().getLevel() < 8) {
                string = "sentry_kayleen_q0263_01.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1) {
            if (questState.getQuestItemsCount(1116) == 0L && questState.getQuestItemsCount(1117) == 0L) {
                string = "sentry_kayleen_q0263_04.htm";
            } else if (questState.getQuestItemsCount(1116) + questState.getQuestItemsCount(1117) >= 10L) {
                string = "sentry_kayleen_q0263_05.htm";
                questState.giveItems(57, questState.getQuestItemsCount(1116) * 20L + questState.getQuestItemsCount(1117) * 30L + 1100L);
                questState.takeItems(1116, -1L);
                questState.takeItems(1117, -1L);
                this.giveExtraReward(questState.getPlayer());
            } else {
                string = "sentry_kayleen_q0263_05.htm";
                questState.giveItems(57, questState.getQuestItemsCount(1116) * 20L + questState.getQuestItemsCount(1117) * 30L);
                questState.takeItems(1116, -1L);
                questState.takeItems(1117, -1L);
                this.giveExtraReward(questState.getPlayer());
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (questState.getCond() == 1 && Rnd.chance((int)60)) {
            if (n == 20385) {
                questState.giveItems(1116, 1L);
            } else if (n == 20386 || n == 20387 || n == 20388) {
                questState.giveItems(1117, 1L);
            }
            questState.playSound("ItemSound.quest_itemget");
        }
        return null;
    }
}
