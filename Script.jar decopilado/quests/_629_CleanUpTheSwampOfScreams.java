/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _629_CleanUpTheSwampOfScreams
extends Quest
implements ScriptFile {
    private static final int bxa = 31553;
    private static final int bxb = 21508;
    private static final int bxc = 21509;
    private static final int bxd = 21510;
    private static final int bxe = 21511;
    private static final int bxf = 21512;
    private static final int bxg = 21513;
    private static final int bxh = 21514;
    private static final int bxi = 21515;
    private static final int bxj = 21516;
    private static final int bxk = 21517;
    private static final int bxl = 7250;
    private static final int bxm = 7251;

    public _629_CleanUpTheSwampOfScreams() {
        super(2);
        this.addStartNpc(31553);
        this.addKillId(new int[]{21508, 21509, 21510, 21511, 21512, 21513, 21514, 21515, 21516, 21517});
        this.addQuestItem(new int[]{7250});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("clean_up_swamp_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31553) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("clean_up_swamp", String.valueOf(11), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "merc_cap_peace_q0629_0104.htm";
            } else if (string.equalsIgnoreCase("reply_1") && n == 1) {
                string2 = "merc_cap_peace_q0629_0201.htm";
            } else if (string.equalsIgnoreCase("reply_3") && n == 1) {
                if (questState.getQuestItemsCount(7250) >= 100L) {
                    int n3 = Rnd.get((int)1000);
                    questState.takeItems(7250, 100L);
                    if (n3 < 1000) {
                        questState.giveItems(7251, 20L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "merc_cap_peace_q0629_0202.htm";
                } else {
                    string2 = "merc_cap_peace_q0629_0203.htm";
                }
            } else if (string.equalsIgnoreCase("reply_4") && n == 1) {
                questState.unset("clean_up_swamp");
                questState.unset("clean_up_swamp_cookie");
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "merc_cap_peace_q0629_0204.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("clean_up_swamp");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31553) break;
                if (questState.getPlayer().getLevel() >= 66 && (questState.getQuestItemsCount(7246) > 0L || questState.getQuestItemsCount(7247) > 0L)) {
                    string = "merc_cap_peace_q0629_0101.htm";
                    break;
                }
                string = "merc_cap_peace_q0629_0103.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 31553 || n != 11) break;
                if (questState.getQuestItemsCount(7250) == 0L) {
                    string = "merc_cap_peace_q0629_0106.htm";
                    break;
                }
                questState.set("clean_up_swamp_cookie", String.valueOf(1), true);
                string = "merc_cap_peace_q0629_0105.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("clean_up_swamp");
        int n2 = npcInstance.getNpcId();
        if (n == 11) {
            int n3 = Rnd.get((int)1000);
            if (n2 == 21508) {
                if (n3 < 599) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21509) {
                if (n3 < 524) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21510) {
                if (n3 < 640) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21511) {
                if (n3 < 830) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21512) {
                if (n3 < 970) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21513) {
                if (n3 < 682) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21514) {
                if (n3 < 595) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21515) {
                if (n3 < 727) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21516) {
                if (n3 < 879) {
                    questState.rollAndGive(7250, 1, 100.0);
                    questState.playSound("ItemSound.quest_itemget");
                }
            } else if (n2 == 21517 && n3 < 999) {
                questState.rollAndGive(7250, 1, 100.0);
                questState.playSound("ItemSound.quest_itemget");
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
