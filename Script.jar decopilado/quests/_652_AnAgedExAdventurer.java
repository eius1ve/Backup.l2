/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _652_AnAgedExAdventurer
extends Quest
implements ScriptFile {
    private static final int bCn = 32012;
    private static final int bCo = 30180;
    private static final int bCp = 1464;
    private static final int bCq = 956;
    boolean already_talking = false;
    NpcInstance retired_oldman_tantan_obj = GameObjectsStorage.getByNpcId((int)32012);

    public _652_AnAgedExAdventurer() {
        super(0);
        this.addStartNpc(32012);
        this.addTalkId(new int[]{30180});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getQuestItemsCount(1464) >= 100L) {
                if (!this.already_talking) {
                    questState.takeItems(1464, 100L);
                    questState.setCond(1);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = "retired_oldman_tantan_q0652_04.htm";
                    questState.startQuestTimer("65201", 3000L);
                    this.already_talking = true;
                } else {
                    string2 = "retired_oldman_tantan_q0652_06.htm";
                }
            } else {
                string2 = questState.getQuestItemsCount(1464) <= 0L ? "retired_oldman_tantan_q0652_05.htm" : "retired_oldman_tantan_q0652_05a.htm";
            }
        } else if (string.equalsIgnoreCase("65201")) {
            if (this.retired_oldman_tantan_obj != null) {
                this.retired_oldman_tantan_obj.doDie((Creature)questState.getPlayer());
                this.already_talking = false;
            }
            string2 = null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "no-quest";
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 32012) break;
                if (questState.getPlayer().getLevel() >= 46) {
                    string = "retired_oldman_tantan_q0652_01.htm";
                    break;
                }
                string = "retired_oldman_tantan_q0652_01a.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 32012) {
                    string = "retired_oldman_tantan_q0652_02.htm";
                    break;
                }
                if (n != 30180) break;
                int n3 = Rnd.get((int)10);
                if (n3 <= 4) {
                    string = "sara_q0652_01.htm";
                    questState.giveItems(956, 1L);
                    questState.giveItems(57, 5026L);
                } else {
                    string = "sara_q0652_02.htm";
                    questState.giveItems(57, 10000L);
                }
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            }
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
