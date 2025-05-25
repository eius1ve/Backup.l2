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

public class _653_WildMaiden
extends Quest
implements ScriptFile {
    private static final int bCr = 32013;
    private static final int bCs = 30181;
    NpcInstance sooki = GameObjectsStorage.getByNpcId((int)32013);
    boolean already_talk = false;
    private static final int bCt = 736;

    public _653_WildMaiden() {
        super(0);
        this.addStartNpc(32013);
        this.addTalkId(new int[]{32013});
        this.addTalkId(new int[]{30181});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            if (questState.getQuestItemsCount(736) >= 1L) {
                if (!this.already_talk) {
                    questState.takeItems(736, 1L);
                    questState.setCond(1);
                    questState.setState(2);
                    questState.playSound("ItemSound.quest_accept");
                    string2 = Rnd.get((int)2) == 0 ? "spring_girl_sooki_q0653_04.htm" : "spring_girl_sooki_q0653_04a.htm";
                    questState.startQuestTimer("65301", 3000L);
                    this.already_talk = true;
                } else {
                    string2 = "spring_girl_sooki_q0653_06.htm";
                    questState.exitCurrentQuest(true);
                }
            } else {
                string2 = "spring_girl_sooki_q0653_05.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (string.equalsIgnoreCase("65301")) {
            if (this.sooki != null) {
                this.sooki.doDie((Creature)questState.getPlayer());
                this.already_talk = false;
            }
            string2 = null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        if (n == 32013) {
            if (questState.getCond() == 0) {
                string = questState.getPlayer().getLevel() >= 36 ? "spring_girl_sooki_q0653_01.htm" : "spring_girl_sooki_q0653_01a.htm";
            } else if (questState.getCond() == 1) {
                string = "spring_girl_sooki_q0653_02.htm";
            }
        } else if (n == 30181 && questState.getCond() == 1) {
            questState.giveItems(57, 2553L);
            questState.playSound("ItemSound.quest_finish");
            string = "galicbredo_q0653_01.htm";
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
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
