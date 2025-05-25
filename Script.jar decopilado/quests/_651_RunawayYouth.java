/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _651_RunawayYouth
extends Quest
implements ScriptFile {
    private static final int bCk = 32014;
    private static final int bCl = 31989;
    NpcInstance ivan = GameObjectsStorage.getByNpcId((int)32014);
    private static final int bCm = 736;
    boolean already_talk = false;

    public _651_RunawayYouth() {
        super(0);
        this.addStartNpc(32014);
        this.addTalkId(new int[]{31989});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getQuestItemsCount(736) >= 1L) {
                if (!this.already_talk) {
                    questState.giveItems(736, 1L);
                    questState.setCond(1);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "runaway_boy_ivan_q0651_04.htm";
                    questState.startQuestTimer("65101", 3000L);
                    this.already_talk = true;
                } else {
                    string2 = "runaway_boy_ivan_q0651_06.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string2 = "runaway_boy_ivan_q0651_05.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (string.equalsIgnoreCase("65101")) {
            if (this.ivan != null) {
                this.ivan.doDie((Creature)questState.getPlayer());
                this.already_talk = false;
            }
            string2 = null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 32014) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 26) {
                    string = "runaway_boy_ivan_q0651_01.htm";
                } else {
                    string = "runaway_boy_ivan_q0651_01a.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "runaway_boy_ivan_q0651_02.htm";
            }
        } else if (n == 31989 && n2 == 1) {
            questState.giveItems(57, 2883L);
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
            string = "fisher_batidae_q0651_01.htm";
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
