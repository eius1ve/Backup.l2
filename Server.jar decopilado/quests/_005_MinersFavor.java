/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;

public class _005_MinersFavor
extends Quest
implements ScriptFile {
    private static final int Js = 30554;
    private static final int Jt = 30517;
    private static final int Ju = 30518;
    private static final int REED = 30520;
    private static final int Jv = 30526;
    private static final int Jw = 1547;
    private static final int Jx = 1548;
    private static final int Jy = 1549;
    private static final int Jz = 1550;
    private static final int JA = 1551;
    private static final int JB = 1552;
    private static final int JC = 906;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _005_MinersFavor() {
        super(0);
        this.addStartNpc(30554);
        this.addTalkId(new int[]{30517});
        this.addTalkId(new int[]{30518});
        this.addTalkId(new int[]{30520});
        this.addTalkId(new int[]{30526});
        this.addQuestItem(new int[]{1547, 1552, 1548, 1549, 1550, 1551});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("miner_bolter_q0005_03.htm")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(1547, 1L, false);
            questState.giveItems(1552, 1L, false);
        } else if (string.equalsIgnoreCase("blacksmith_bronp_q0005_02.htm")) {
            questState.takeItems(1552, -1L);
            questState.giveItems(1549, 1L, false);
            if (questState.getQuestItemsCount(1547) > 0L && questState.getQuestItemsCount(1548) + questState.getQuestItemsCount(1549) + questState.getQuestItemsCount(1550) + questState.getQuestItemsCount(1551) == 4L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        String string = "noquest";
        int n2 = questState.getCond();
        if (n == 30554) {
            if (n2 == 0) {
                if (questState.getPlayer().getLevel() >= 2) {
                    string = "miner_bolter_q0005_02.htm";
                } else {
                    string = "miner_bolter_q0005_01.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (n2 == 1) {
                string = "miner_bolter_q0005_04.htm";
            } else if (n2 == 2 && questState.getQuestItemsCount(1548) + questState.getQuestItemsCount(1549) + questState.getQuestItemsCount(1550) + questState.getQuestItemsCount(1551) == 4L) {
                string = "miner_bolter_q0005_06.htm";
                questState.takeItems(1548, -1L);
                questState.takeItems(1549, -1L);
                questState.takeItems(1550, -1L);
                questState.takeItems(1551, -1L);
                questState.takeItems(1547, -1L);
                questState.giveItems(906, 1L, false);
                if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("ng1")) {
                    questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("  Delivery duty complete.\nGo find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                }
                questState.giveItems(57, 2466L);
                questState.unset("cond");
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(false);
            }
        } else if (n2 == 1 && questState.getQuestItemsCount(1547) > 0L) {
            if (n == 30517) {
                if (questState.getQuestItemsCount(1550) == 0L) {
                    string = "trader_chali_q0005_01.htm";
                    questState.giveItems(1550, 1L, false);
                    questState.playSound("ItemSound.quest_itemget");
                } else {
                    string = "trader_chali_q0005_02.htm";
                }
            } else if (n == 30518) {
                if (questState.getQuestItemsCount(1548) == 0L) {
                    string = "trader_garita_q0005_01.htm";
                    questState.giveItems(1548, 1L, false);
                    questState.playSound("ItemSound.quest_itemget");
                } else {
                    string = "trader_garita_q0005_02.htm";
                }
            } else if (n == 30520) {
                if (questState.getQuestItemsCount(1551) == 0L) {
                    string = "warehouse_chief_reed_q0005_01.htm";
                    questState.giveItems(1551, 1L, false);
                    questState.playSound("ItemSound.quest_itemget");
                } else {
                    string = "warehouse_chief_reed_q0005_02.htm";
                }
            } else if (n == 30526 && questState.getQuestItemsCount(1552) > 0L) {
                string = questState.getQuestItemsCount(1549) == 0L ? "blacksmith_bronp_q0005_01.htm" : "blacksmith_bronp_q0005_03.htm";
            }
            if (questState.getQuestItemsCount(1547) > 0L && questState.getQuestItemsCount(1548) + questState.getQuestItemsCount(1549) + questState.getQuestItemsCount(1550) + questState.getQuestItemsCount(1551) == 4L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return string;
    }
}
