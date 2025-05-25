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

public class _157_RecoverSmuggled
extends Quest
implements ScriptFile {
    private static final int TU = 30005;
    private static final int TV = 20121;
    private static final int TW = 1024;
    private static final int TX = 20;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _157_RecoverSmuggled() {
        super(0);
        this.addStartNpc(30005);
        this.addTalkId(new int[]{30005});
        this.addKillId(new int[]{20121});
        this.addQuestItem(new int[]{1024});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("quest_accept")) {
            questState.setCond(1);
            questState.set("recover_smuggled", String.valueOf(1), true);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "wilph_q0157_05.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("recover_smuggled");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30005) break;
                if (questState.getPlayer().getLevel() >= 5) {
                    string = "wilph_q0157_03.htm";
                    break;
                }
                string = "wilph_q0157_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 30005 || n != 1) break;
                if (questState.getQuestItemsCount(1024) < 20L) {
                    string = "wilph_q0157_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1024) < 20L) break;
                questState.takeItems(1024, -1L);
                questState.giveItems(20, 1L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.unset("recover_smuggled");
                string = "wilph_q0157_07.htm";
                questState.exitCurrentQuest(false);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("recover_smuggled");
        if (n == 20121 && n2 == 1 && questState.getQuestItemsCount(1024) < 20L && Rnd.get((int)10) < 4) {
            questState.giveItems(1024, 1L);
            if (questState.getQuestItemsCount(1024) >= 20L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }
}
