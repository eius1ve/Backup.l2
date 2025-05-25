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

public class _645_GhostsOfBatur
extends Quest
implements ScriptFile {
    private static final int bBh = 32017;
    private static final int bBi = 22007;
    private static final int bBj = 22009;
    private static final int bBk = 22010;
    private static final int bBl = 22011;
    private static final int bBm = 22012;
    private static final int bBn = 22013;
    private static final int bBo = 22014;
    private static final int bBp = 22015;
    private static final int bBq = 22016;
    private static final int bBr = 8089;
    private static final int bBs = 1878;
    private static final int bBt = 1879;
    private static final int bBu = 1880;
    private static final int bBv = 1881;
    private static final int bBw = 1882;
    private static final int bBx = 1883;

    public _645_GhostsOfBatur() {
        super(0);
        this.addStartNpc(32017);
        this.addKillId(new int[]{22007, 22009, 22010, 22011, 22012, 22013, 22014, 22015, 22016});
        this.addQuestItem(new int[]{8089});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n;
        String string2 = string;
        int n2 = questState.getInt("ghosts_of_batur");
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setState(2);
            questState.setCond(1);
            questState.set("ghosts_of_batur", 11);
            questState.playSound("ItemSound.quest_accept");
            string2 = "karuda_q0645_0103.htm";
        }
        if (string.equalsIgnoreCase("reply_3") && n2 >= 11) {
            string2 = "karuda_q0645_0201.htm";
        }
        if (string.toLowerCase().startsWith("reply_") && (n = Integer.parseInt(string.substring(6))) >= 11 && n <= 16 && n2 >= 11 && questState.getQuestItemsCount(8089) >= 180L) {
            questState.takeItems(8089, 180L);
            if (n == 11) {
                questState.giveItems(1878, 18L);
            }
            if (n == 12) {
                questState.giveItems(1879, 7L);
            }
            if (n == 13) {
                questState.giveItems(1880, 4L);
            }
            if (n == 14) {
                questState.giveItems(1881, 6L);
            }
            if (n == 15) {
                questState.giveItems(1882, 10L);
            }
            if (n == 16) {
                questState.giveItems(1883, 2L);
            }
            this.giveExtraReward(questState.getPlayer());
            questState.exitCurrentQuest(true);
            questState.playSound("ItemSound.quest_finish");
            string2 = "karuda_q0645_0202.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        int n2 = questState.getInt("ghosts_of_batur");
        switch (n) {
            case 1: {
                if (questState.getPlayer().getLevel() >= 23) {
                    string = "karuda_q0645_0101.htm";
                    break;
                }
                string = "karuda_q0645_0102.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 < 11 || n2 > 12) break;
                string = n2 == 12 && questState.getQuestItemsCount(8089) >= 180L ? "karuda_q0645_0105.htm" : "karuda_q0645_0106.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        long l = questState.getQuestItemsCount(8089);
        if (n == 22007) {
            if (l < 180L) {
                questState.rollAndGive(8089, 1, 83.0);
                l = questState.getQuestItemsCount(8089);
            }
            if (l >= 180L) {
                questState.set("ghosts_of_batur", 12);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 22009) {
            if (l < 180L) {
                questState.rollAndGive(8089, 1, 92.0);
                l = questState.getQuestItemsCount(8089);
            }
            if (l >= 180L) {
                questState.set("ghosts_of_batur", 12);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 22010) {
            if (l < 180L) {
                questState.rollAndGive(8089, 1, 98.4);
                l = questState.getQuestItemsCount(8089);
            }
            if (l >= 180L) {
                questState.set("ghosts_of_batur", 12);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 22011 || n == 22012 || n == 22013 || n == 22014 || n == 22015 || n == 22016) {
            if (l < 180L) {
                questState.rollAndGive(8089, 1, 1, 12.5);
                l = questState.getQuestItemsCount(8089);
            }
            if (l >= 180L) {
                questState.set("ghosts_of_batur", 12);
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
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
