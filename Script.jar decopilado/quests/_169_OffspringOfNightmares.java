/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
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
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;

public class _169_OffspringOfNightmares
extends Quest
implements ScriptFile {
    private static final int Vi = 30145;
    private static final int Vj = 1030;
    private static final int Vk = 1031;
    private static final int Vl = 31;
    private static final int Vm = 20105;
    private static final int Vn = 20025;

    public _169_OffspringOfNightmares() {
        super(0);
        this.addStartNpc(30145);
        this.addKillId(new int[]{20105, 20025});
        this.addQuestItem(new int[]{1030, 1031});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "vlasti_q0326_04.htm";
        } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(1031) >= 1L) {
            string2 = "vlasti_q0326_08.htm";
            questState.giveItems(31, 1L);
            questState.giveItems(57, 17030L + 10L * questState.getQuestItemsCount(1030));
            questState.takeItems(1030, questState.getQuestItemsCount(1030));
            questState.takeItems(1031, questState.getQuestItemsCount(1031));
            if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("p1q4")) {
                questState.getPlayer().setVar("p1q4", "1", -1L);
                questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Now go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
            }
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(false);
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
                    string = "vlasti_q0326_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 15) {
                    string = "vlasti_q0326_03.htm";
                    break;
                }
                string = "vlasti_q0326_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (questState.getQuestItemsCount(1030) >= 1L && questState.getQuestItemsCount(1031) == 0L) {
                    string = "vlasti_q0326_06.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1031) >= 1L) {
                    string = "vlasti_q0326_07.htm";
                    break;
                }
                if (questState.getQuestItemsCount(1030) != 0L || questState.getQuestItemsCount(1031) != 0L) break;
                string = "vlasti_q0326_05.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20105 || n == 20025 && questState.getCond() == 1) {
            if (Rnd.get((int)10) > 7 && questState.getQuestItemsCount(1031) == 0L) {
                questState.rollAndGive(1031, 1, 100.0);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
            if (Rnd.get((int)10) > 4) {
                questState.rollAndGive(1030, 1, 100.0);
            }
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
