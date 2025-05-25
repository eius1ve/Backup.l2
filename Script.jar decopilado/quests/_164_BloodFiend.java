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

public class _164_BloodFiend
extends Quest
implements ScriptFile {
    private static final int UC = 30149;
    private static final int UD = 1044;
    private static final int UE = 27021;

    public _164_BloodFiend() {
        super(0);
        this.addStartNpc(30149);
        this.addKillId(new int[]{27021});
        this.addAttackId(new int[]{27021});
        this.addQuestItem(new int[]{1044});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "cel_q0318_04.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        Player player = questState.getPlayer();
        switch (n) {
            case 1: {
                if (player.getRace() == Race.darkelf) {
                    string = "cel_q0318_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 21) {
                    string = "cel_q0318_03.htm";
                    break;
                }
                string = "cel_q0318_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(1044) < 1L) {
                    string = "cel_q0318_05.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1044) < 1L) break;
                string = "cel_q0318_06.htm";
                questState.giveItems(57, 42130L);
                questState.takeItems(1044, 1L);
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(1044) == 0L && questState.getCond() == 1) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.16405", (Object[])new Object[0]);
            questState.giveItems(1044, 1L);
            questState.setCond(2);
            questState.playSound("ItemSound.quest_middle");
        }
        return null;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("kirunak");
        if (n == 0) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.16404", (Object[])new Object[0]);
            questState.set("kirunak", "1");
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
