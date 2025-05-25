/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.scripts.ScriptFile;

public class _111_ElrokianHuntersProof
extends Quest
implements ScriptFile {
    private static final int Qy = 32113;
    private static final int Qz = 32115;
    private static final int QA = 32116;
    private static final int[] br = new int[]{22196, 22197, 22198, 22218, 22223};
    private static final int[] bs = new int[]{22200, 22201, 22202, 22219, 22224};
    private static final int[] bt = new int[]{22203, 22204, 22205, 22220, 22225};
    private static final int[] bu = new int[]{22208, 22209, 22210, 22221, 22226};
    private static final int QB = 8768;
    private static final int QC = 8770;
    private static final int QD = 8771;
    private static final int QE = 8772;
    private static final int QF = 8763;
    private static final int QG = 8764;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _111_ElrokianHuntersProof() {
        super(1);
        this.addStartNpc(32113);
        this.addTalkId(new int[]{32115});
        this.addTalkId(new int[]{32116});
        this.addKillId(br);
        this.addKillId(bs);
        this.addKillId(bt);
        this.addKillId(bu);
        this.addQuestItem(new int[]{8768, 8770, 8771, 8772});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getCond();
        Player player = questState.getPlayer();
        if (string.equalsIgnoreCase("marquez_q111_2.htm") && n == 0) {
            questState.setCond(2);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("asamah_q111_2.htm")) {
            questState.setCond(3);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("marquez_q111_4.htm")) {
            questState.setCond(4);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("marquez_q111_6.htm")) {
            questState.setCond(6);
            questState.takeItems(8768, -1L);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("kirikachin_q111_2.htm")) {
            questState.setCond(7);
            player.sendPacket((IStaticPacket)new PlaySound("EtcSound.elcroki_song_full"));
        } else if (string.equalsIgnoreCase("kirikachin_q111_3.htm")) {
            questState.setCond(8);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("asamah_q111_4.htm")) {
            questState.setCond(9);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("asamah_q111_5.htm")) {
            questState.setCond(10);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("asamah_q111_7.htm")) {
            questState.takeItems(8770, -1L);
            questState.takeItems(8771, -1L);
            questState.takeItems(8772, -1L);
            questState.setCond(12);
            questState.playSound("ItemSound.quest_middle");
        } else if (string.equalsIgnoreCase("asamah_q111_8.htm")) {
            questState.giveItems(57, 1022636L);
            questState.giveItems(8763, 1L);
            questState.giveItems(8764, 100L);
            questState.setState(3);
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(false);
            questState.playSound("ItemSound.quest_finish");
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n == 32113) {
            if (questState.getPlayer().getLevel() >= 75 && n2 == 0) {
                string = "marquez_q111_1.htm";
            } else if (questState.getPlayer().getLevel() < 75 && n2 == 0) {
                string = "marquez_q111_0.htm";
            } else if (n2 == 3) {
                string = "marquez_q111_3.htm";
            } else if (n2 == 5) {
                string = "marquez_q111_5.htm";
            }
        } else if (n == 32115) {
            if (n2 == 2) {
                string = "asamah_q111_1.htm";
            } else if (n2 == 8) {
                string = "asamah_q111_3.htm";
            } else if (n2 == 11) {
                string = "asamah_q111_6.htm";
            }
        } else if (n == 32116 && n2 == 6) {
            string = "kirikachin_q111_1.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getCond();
        if (n2 == 4) {
            for (int n3 : br) {
                if (n != n3 || questState.getQuestItemsCount(8768) >= 50L) continue;
                questState.giveItems(8768, 1L, false);
                if (questState.getQuestItemsCount(8768) == 50L) {
                    questState.playSound("ItemSound.quest_middle");
                    questState.setCond(5);
                    return null;
                }
                questState.playSound("ItemSound.quest_itemget");
            }
        } else if (n2 == 10) {
            for (int n4 : bs) {
                if (n != n4 || questState.getQuestItemsCount(8770) >= 10L) continue;
                questState.giveItems(8770, 1L, false);
                return null;
            }
            for (int n4 : bt) {
                if (n != n4 || questState.getQuestItemsCount(8771) >= 10L) continue;
                questState.giveItems(8771, 1L, false);
                return null;
            }
            for (int n4 : bu) {
                if (n != n4 || questState.getQuestItemsCount(8772) >= 10L) continue;
                questState.giveItems(8772, 1L, false);
                return null;
            }
            if (questState.getQuestItemsCount(8770) >= 10L && questState.getQuestItemsCount(8771) >= 10L && questState.getQuestItemsCount(8772) >= 10L) {
                questState.setCond(11);
                questState.playSound("ItemSound.quest_middle");
                return null;
            }
        }
        return null;
    }
}
