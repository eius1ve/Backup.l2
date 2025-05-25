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

public class _612_WarwithKetraOrcs
extends Quest
implements ScriptFile {
    private static final int bro = 31377;
    private static final int brp = 21324;
    private static final int brq = 21327;
    private static final int brr = 21328;
    private static final int brs = 21329;
    private static final int brt = 21331;
    private static final int bru = 21332;
    private static final int brv = 21334;
    private static final int brw = 21336;
    private static final int brx = 21338;
    private static final int bry = 21339;
    private static final int brz = 21340;
    private static final int brA = 21342;
    private static final int brB = 21343;
    private static final int brC = 21345;
    private static final int brD = 21347;
    private static final int brE = 7234;
    private static final int brF = 7187;

    public _612_WarwithKetraOrcs() {
        super(1);
        this.addStartNpc(31377);
        this.addKillId(new int[]{21324, 21327, 21328, 21329, 21331, 21332, 21334, 21336, 21338, 21339, 21340, 21342, 21343, 21345, 21347});
        this.addQuestItem(new int[]{7234});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.set("war_with_ketra_orcs", String.valueOf(11), true);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "elder_ashas_barka_durai_q0612_0104.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            string2 = "elder_ashas_barka_durai_q0612_0201.htm";
        } else if (string.equalsIgnoreCase("reply_3")) {
            if (questState.getQuestItemsCount(7234) >= 100L) {
                questState.takeItems(7234, 100L);
                questState.giveItems(7187, 20L);
                string2 = "elder_ashas_barka_durai_q0612_0202.htm";
            } else {
                string2 = "elder_ashas_barka_durai_q0612_0203.htm";
            }
        } else if (string.equalsIgnoreCase("reply_4")) {
            questState.takeItems(7234, -1L);
            questState.unset("war_with_ketra_orcs");
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
            string2 = "elder_ashas_barka_durai_q0612_0204.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("war_with_ketra_orcs");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31377) break;
                if (questState.getPlayer().getLevel() >= 74) {
                    string = "elder_ashas_barka_durai_q0612_0101.htm";
                    break;
                }
                string = "elder_ashas_barka_durai_q0612_0103.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 31377 || n != 11) break;
                string = questState.getQuestItemsCount(7234) == 0L ? "elder_ashas_barka_durai_q0612_0106.htm" : "elder_ashas_barka_durai_q0612_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("war_with_ketra_orcs");
        int n2 = npcInstance.getNpcId();
        if (n == 11) {
            if (n2 == 21324) {
                questState.rollAndGive(7234, 1, 50.0);
            } else if (n2 == 21327) {
                questState.rollAndGive(7234, 1, 51.0);
            } else if (n2 == 21328) {
                questState.rollAndGive(7234, 1, 52.2);
            } else if (n2 == 21329) {
                questState.rollAndGive(7234, 1, 52.0);
            } else if (n2 == 21331 || n2 == 21332) {
                questState.rollAndGive(7234, 1, 52.9);
            } else if (n2 == 21334) {
                questState.rollAndGive(7234, 1, 54.0);
            } else if (n2 == 21336) {
                questState.rollAndGive(7234, 1, 54.8);
            } else if (n2 == 21338) {
                questState.rollAndGive(7234, 1, 55.8);
            } else if (n2 == 21339 || n2 == 21340) {
                questState.rollAndGive(7234, 1, 56.8);
            } else if (n2 == 21342) {
                questState.rollAndGive(7234, 1, 57.8);
            } else if (n2 == 21343) {
                questState.rollAndGive(7234, 1, 66.4);
            } else if (n2 == 21345) {
                questState.rollAndGive(7234, 1, 71.3);
            } else if (n2 == 21347) {
                questState.rollAndGive(7234, 1, 73.8);
            }
        }
        return null;
    }
}
