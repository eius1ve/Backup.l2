/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _170_DangerousSeduction
extends Quest
implements ScriptFile {
    private static final int Vo = 30305;
    private static final int Vp = 1046;
    private static final int Vq = 27022;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _170_DangerousSeduction() {
        super(1);
        this.addStartNpc(30305);
        this.addKillId(new int[]{27022});
        this.addAttackId(new int[]{27022});
        this.addQuestItem(new int[]{1046});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "tetrarch_vellior_q0327_04.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        int n = questState.getState();
        switch (n) {
            case 1: {
                if (player.getRace() != Race.darkelf) {
                    string = "tetrarch_vellior_q0327_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 21) {
                    string = "tetrarch_vellior_q0327_03.htm";
                    break;
                }
                string = "tetrarch_vellior_q0327_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(1046) < 1L) {
                    string = "tetrarch_vellior_q0327_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1046) < 1L) break;
                string = "tetrarch_vellior_q0327_06.htm";
                questState.giveItems(57, 102680L);
                questState.takeItems(1046, questState.getQuestItemsCount(1046));
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
                questState.playSound("ItemSound.quest_finish");
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getCond();
        if (n == 1 && questState.getQuestItemsCount(1046) == 0L) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.17005", (Object[])new Object[0]);
            questState.giveItems(1046, 1L);
            questState.setCond(2);
            questState.playSound("ItemSound.quest_middle");
        }
        return null;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("merkenis");
        if (n == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.17004", (Object[])new Object[0]);
            questState.set("merkenis", "1");
        }
        return null;
    }
}
