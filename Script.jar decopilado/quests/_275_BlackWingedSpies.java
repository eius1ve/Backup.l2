/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _275_BlackWingedSpies
extends Quest
implements ScriptFile {
    private static int anD = 30567;
    private static int auT = 20316;
    private static int auU = 27043;
    private static int auV = 1478;
    private static int auW = 1479;
    private static int auXX = 10;

    public _275_BlackWingedSpies() {
        super(0);
        this.addStartNpc(anD);
        this.addKillId(new int[]{auT});
        this.addKillId(new int[]{auU});
        this.addQuestItem(new int[]{auV});
        this.addQuestItem(new int[]{auW});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("neruga_chief_tantus_q0275_03.htm") && questState.getState() == 1) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (npcInstance.getNpcId() != anD) {
            return "noquest";
        }
        int n = questState.getState();
        if (n == 1) {
            if (questState.getPlayer().getRace() != Race.orc) {
                questState.exitCurrentQuest(true);
                return "neruga_chief_tantus_q0275_00.htm";
            }
            if (questState.getPlayer().getLevel() < 11) {
                questState.exitCurrentQuest(true);
                return "neruga_chief_tantus_q0275_01.htm";
            }
            questState.setCond(0);
            return "neruga_chief_tantus_q0275_02.htm";
        }
        if (n != 2) {
            return "noquest";
        }
        int n2 = questState.getCond();
        if (questState.getQuestItemsCount(auV) < 70L) {
            if (n2 != 1) {
                questState.setCond(1);
            }
            return "neruga_chief_tantus_q0275_04.htm";
        }
        if (n2 == 2) {
            questState.giveItems(57, 4550L);
            questState.playSound("ItemSound.quest_finish");
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            return "neruga_chief_tantus_q0275_05.htm";
        }
        return "noquest";
    }

    private static void d(QuestState questState) {
        if (questState.getQuestItemsCount(auW) > 0L) {
            questState.takeItems(auW, -1L);
        }
        questState.giveItems(auW, 1L);
        questState.addSpawn(auU);
    }

    public static void give_Darkwing_Bat_Fang(QuestState questState, long l) {
        long l2 = 70L - questState.getQuestItemsCount(auV);
        if (l2 < 1L) {
            return;
        }
        if (l > l2) {
            l = l2;
        }
        questState.giveItems(auV, l);
        questState.playSound(l == l2 ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
        if (l == l2) {
            questState.setCond(2);
        }
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        long l = questState.getQuestItemsCount(auV);
        if (n == auT && l < 70L) {
            if (l > 10L && l < 65L && Rnd.chance((int)auXX)) {
                _275_BlackWingedSpies.d(questState);
                return null;
            }
            _275_BlackWingedSpies.give_Darkwing_Bat_Fang(questState, 1L);
        } else if (n == auU && l < 70L && questState.getQuestItemsCount(auW) > 0L) {
            questState.takeItems(auW, -1L);
            _275_BlackWingedSpies.give_Darkwing_Bat_Fang(questState, 5L);
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
