/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;

public class _004_LongLivethePaagrioLord
extends Quest
implements ScriptFile {
    private static final int Je = 30578;
    private static final int Jf = 30585;
    private static final int Jg = 30566;
    private static final int Jh = 30562;
    private static final int Ji = 30560;
    private static final int Jj = 30559;
    private static final int Jk = 30587;
    private static final int Jl = 1541;
    private static final int Jm = 1542;
    private static final int Jn = 1543;
    private static final int Jo = 1544;
    private static final int Jp = 1545;
    private static final int Jq = 1546;
    private static final int Jr = 4;

    public _004_LongLivethePaagrioLord() {
        super(0);
        this.addStartNpc(30578);
        this.addTalkId(new int[]{30585, 30566, 30562, 30560, 30559, 30587});
        this.addQuestItem(new int[]{1541, 1542, 1543, 1544, 1545, 1546});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "centurion_nakusin_q0004_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getState();
        switch (n2) {
            case 1: {
                if (n != 30578) break;
                if (questState.getPlayer().getRace() != Race.orc) {
                    string = "centurion_nakusin_q0004_00.htm";
                    break;
                }
                if (questState.getPlayer().getLevel() >= 2) {
                    string = "centurion_nakusin_q0004_02.htm";
                    break;
                }
                string = "centurion_nakusin_q0004_01.htm";
                break;
            }
            case 2: {
                if (n == 30578) {
                    if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) < 6L) {
                        string = "centurion_nakusin_q0004_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) < 6L) break;
                    string = "centurion_nakusin_q0004_06.htm";
                    questState.giveItems(4, 1L);
                    questState.addExpAndSp(4254L, 335L);
                    questState.giveItems(57, 1850L);
                    questState.takeAllItems(new int[]{1541, 1542, 1543, 1544, 1545, 1546});
                    if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("ng1")) {
                        questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Delivery duty complete.\nGo find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                    break;
                }
                if (n == 30585) {
                    if (questState.getQuestItemsCount(1542) < 1L) {
                        string = "tataru_zu_hestui_q0004_01.htm";
                        questState.giveItems(1542, 1L);
                        if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) >= 6L) {
                            questState.setCond(2);
                        }
                        questState.playSound("ItemSound.quest_itemget");
                        break;
                    }
                    if (questState.getQuestItemsCount(1542) < 1L) break;
                    string = "tataru_zu_hestui_q0004_02.htm";
                    break;
                }
                if (n == 30566) {
                    if (questState.getQuestItemsCount(1541) < 1L) {
                        string = "atuba_chief_varkees_q0004_01.htm";
                        questState.giveItems(1541, 1L);
                        if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) >= 6L) {
                            questState.setCond(2);
                        }
                        questState.playSound("ItemSound.quest_itemget");
                        break;
                    }
                    if (questState.getQuestItemsCount(1541) < 1L) break;
                    string = "atuba_chief_varkees_q0004_02.htm";
                    break;
                }
                if (n == 30562) {
                    if (questState.getQuestItemsCount(1543) < 1L) {
                        string = "warehouse_grookin_q0004_01.htm";
                        questState.giveItems(1543, 1L);
                        if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) >= 6L) {
                            questState.setCond(2);
                        }
                        questState.playSound("ItemSound.quest_itemget");
                        break;
                    }
                    if (questState.getQuestItemsCount(1543) < 1L) break;
                    string = "warehouse_grookin_q0004_02.htm";
                    break;
                }
                if (n == 30560) {
                    if (questState.getQuestItemsCount(1544) < 1L) {
                        string = "trader_uska_q0004_01.htm";
                        questState.giveItems(1544, 1L);
                        if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) >= 6L) {
                            questState.setCond(2);
                        }
                        questState.playSound("ItemSound.quest_itemget");
                        break;
                    }
                    if (questState.getQuestItemsCount(1544) < 1L) break;
                    string = "trader_uska_q0004_02.htm";
                    break;
                }
                if (n == 30559) {
                    if (questState.getQuestItemsCount(1545) < 1L) {
                        string = "trader_kunai_q0004_01.htm";
                        questState.giveItems(1545, 1L);
                        if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) >= 6L) {
                            questState.setCond(2);
                        }
                        questState.playSound("ItemSound.quest_itemget");
                        break;
                    }
                    if (questState.getQuestItemsCount(1545) < 1L) break;
                    string = "trader_kunai_q0004_02.htm";
                    break;
                }
                if (n != 30587) break;
                if (questState.getQuestItemsCount(1546) < 1L) {
                    string = "gantaki_zu_urutu_q0004_01.htm";
                    questState.giveItems(1546, 1L);
                    if (questState.getQuestItemsCount(1541) + questState.getQuestItemsCount(1542) + questState.getQuestItemsCount(1543) + questState.getQuestItemsCount(1544) + questState.getQuestItemsCount(1545) + questState.getQuestItemsCount(1546) >= 6L) {
                        questState.setCond(2);
                    }
                    questState.playSound("ItemSound.quest_itemget");
                    break;
                }
                if (questState.getQuestItemsCount(1546) < 1L) break;
                string = "gantaki_zu_urutu_q0004_02.htm";
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
