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

public class _372_LegacyOfInsolence
extends Quest
implements ScriptFile {
    private static final int aPW = 30844;
    private static final int aPX = 30839;
    private static final int aPY = 30855;
    private static final int aPZ = 30929;
    private static final int aQa = 31001;
    private static final int aQb = 20817;
    private static final int aQc = 20821;
    private static final int aQd = 20825;
    private static final int aQe = 20829;
    private static final int aQf = 21069;
    private static final int aQg = 21063;
    private static final int aQh = 5989;
    private static final int aQi = 5990;
    private static final int aQj = 5991;
    private static final int aQk = 5992;
    private static final int aQl = 5993;
    private static final int aQm = 5994;
    private static final int aQn = 5995;
    private static final int aQo = 5996;
    private static final int aQp = 5997;
    private static final int aQq = 5998;
    private static final int aQr = 5999;
    private static final int aQs = 6000;
    private static final int aQt = 6001;
    private static final int aQu = 5496;
    private static final int aQv = 5508;
    private static final int aQw = 5525;
    private static final int aQx = 5368;
    private static final int aQy = 5392;
    private static final int aQz = 5426;
    private static final int aQA = 5497;
    private static final int aQB = 5509;
    private static final int aQC = 5526;
    private static final int aQD = 5370;
    private static final int aQE = 5394;
    private static final int aQF = 5428;
    private static final int aQG = 5502;
    private static final int aQH = 5514;
    private static final int aQI = 5527;
    private static final int aQJ = 5380;
    private static final int aQK = 5404;
    private static final int aQL = 5430;
    private static final int aQM = 5503;
    private static final int aQN = 5515;
    private static final int aQO = 5528;
    private static final int aQP = 5382;
    private static final int aQQ = 5406;
    private static final int aQR = 5432;
    private static final int aQS = 5984;
    private static final int aQT = 5985;
    private static final int aQU = 5986;
    private static final int aQV = 5987;
    private static final int aQW = 5988;
    private static final int aQX = 5972;
    private static final int aQY = 5973;
    private static final int aQZ = 5974;
    private static final int aRa = 5975;
    private static final int aRb = 5976;
    private static final int aRc = 5977;
    private static final int aRd = 5978;
    private static final int aRe = 5979;
    private static final int aRf = 5980;
    private static final int aRg = 5981;
    private static final int aRh = 5982;
    private static final int aRi = 5983;
    private static final int aRj = 5966;
    private static final int aRk = 5967;
    private static final int aRl = 5968;
    private static final int aRm = 5969;

    public _372_LegacyOfInsolence() {
        super(1);
        this.addStartNpc(30844);
        this.addTalkId(new int[]{30839, 30855, 30929, 31001});
        this.addKillId(new int[]{20817, 20821, 20825, 20829, 21069, 21063});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30844) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("legacy_of_insolence", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "whouse_keeper_walderal_q0372_04.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=1")) {
                string2 = "whouse_keeper_walderal_q0372_03.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=3")) {
                if (questState.getQuestItemsCount(5989) < 1L || questState.getQuestItemsCount(5990) < 1L || questState.getQuestItemsCount(5991) < 1L || questState.getQuestItemsCount(5992) < 1L || questState.getQuestItemsCount(5993) < 1L || questState.getQuestItemsCount(5994) < 1L || questState.getQuestItemsCount(5995) < 1L || questState.getQuestItemsCount(5996) < 1L || questState.getQuestItemsCount(5997) < 1L || questState.getQuestItemsCount(5998) < 1L || questState.getQuestItemsCount(5999) < 1L || questState.getQuestItemsCount(6000) < 1L || questState.getQuestItemsCount(6001) < 1L) {
                    string2 = "whouse_keeper_walderal_q0372_06.htm";
                } else if (questState.getQuestItemsCount(5989) >= 1L && questState.getQuestItemsCount(5990) >= 1L && questState.getQuestItemsCount(5991) >= 1L && questState.getQuestItemsCount(5992) >= 1L && questState.getQuestItemsCount(5993) >= 1L && questState.getQuestItemsCount(5994) >= 1L && questState.getQuestItemsCount(5995) >= 1L && questState.getQuestItemsCount(5996) >= 1L && questState.getQuestItemsCount(5997) >= 1L && questState.getQuestItemsCount(5998) >= 1L && questState.getQuestItemsCount(5999) >= 1L && questState.getQuestItemsCount(6000) >= 1L && questState.getQuestItemsCount(6001) >= 1L) {
                    string2 = "whouse_keeper_walderal_q0372_07.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=4")) {
                string2 = "whouse_keeper_walderal_q0372_08.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=5")) {
                questState.playSound("ItemSound.quest_finish");
                questState.exitCurrentQuest(true);
                string2 = "whouse_keeper_walderal_q0372_09.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=6")) {
                string2 = "whouse_keeper_walderal_q0372_11.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=7")) {
                if (questState.getQuestItemsCount(5989) < 1L || questState.getQuestItemsCount(5990) < 1L || questState.getQuestItemsCount(5991) < 1L || questState.getQuestItemsCount(5992) < 1L || questState.getQuestItemsCount(5993) < 1L || questState.getQuestItemsCount(5994) < 1L || questState.getQuestItemsCount(5995) < 1L || questState.getQuestItemsCount(5996) < 1L || questState.getQuestItemsCount(5997) < 1L || questState.getQuestItemsCount(5998) < 1L || questState.getQuestItemsCount(5999) < 1L || questState.getQuestItemsCount(6000) < 1L || questState.getQuestItemsCount(6001) < 1L) {
                    string2 = "whouse_keeper_walderal_q0372_07e.htm";
                } else if (questState.getQuestItemsCount(5989) >= 1L && questState.getQuestItemsCount(5990) >= 1L && questState.getQuestItemsCount(5991) >= 1L && questState.getQuestItemsCount(5992) >= 1L && questState.getQuestItemsCount(5993) >= 1L && questState.getQuestItemsCount(5994) >= 1L && questState.getQuestItemsCount(5995) >= 1L && questState.getQuestItemsCount(5996) >= 1L && questState.getQuestItemsCount(5997) >= 1L && questState.getQuestItemsCount(5998) >= 1L && questState.getQuestItemsCount(5999) >= 1L && questState.getQuestItemsCount(6000) >= 1L && questState.getQuestItemsCount(6001) >= 1L) {
                    questState.takeItems(5989, 1L);
                    questState.takeItems(5990, 1L);
                    questState.takeItems(5991, 1L);
                    questState.takeItems(5992, 1L);
                    questState.takeItems(5993, 1L);
                    questState.takeItems(5994, 1L);
                    questState.takeItems(5995, 1L);
                    questState.takeItems(5996, 1L);
                    questState.takeItems(5997, 1L);
                    questState.takeItems(5998, 1L);
                    questState.takeItems(5999, 1L);
                    questState.takeItems(6000, 1L);
                    questState.takeItems(6001, 1L);
                    int n2 = Rnd.get((int)100);
                    if (n2 < 10) {
                        questState.giveItems(5496, 1L);
                    } else if (n2 < 20) {
                        questState.giveItems(5508, 1L);
                    } else if (n2 < 30) {
                        questState.giveItems(5525, 1L);
                    } else if (n2 < 40) {
                        questState.giveItems(5496, 1L);
                        questState.giveItems(5508, 1L);
                        questState.giveItems(5525, 1L);
                    } else if (n2 < 51) {
                        questState.giveItems(5368, 1L);
                    } else if (n2 < 62) {
                        questState.giveItems(5392, 1L);
                    } else if (n2 < 79) {
                        questState.giveItems(5426, 1L);
                    } else if (n2 < 100) {
                        questState.giveItems(5368, 1L);
                        questState.giveItems(5392, 1L);
                        questState.giveItems(5426, 1L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "whouse_keeper_walderal_q0372_07a.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=8")) {
                if (questState.getQuestItemsCount(5989) < 1L || questState.getQuestItemsCount(5990) < 1L || questState.getQuestItemsCount(5991) < 1L || questState.getQuestItemsCount(5992) < 1L || questState.getQuestItemsCount(5993) < 1L || questState.getQuestItemsCount(5994) < 1L || questState.getQuestItemsCount(5995) < 1L || questState.getQuestItemsCount(5996) < 1L || questState.getQuestItemsCount(5997) < 1L || questState.getQuestItemsCount(5998) < 1L || questState.getQuestItemsCount(5999) < 1L || questState.getQuestItemsCount(6000) < 1L || questState.getQuestItemsCount(6001) < 1L) {
                    string2 = "whouse_keeper_walderal_q0372_07e.htm";
                } else if (questState.getQuestItemsCount(5989) >= 1L && questState.getQuestItemsCount(5990) >= 1L && questState.getQuestItemsCount(5991) >= 1L && questState.getQuestItemsCount(5992) >= 1L && questState.getQuestItemsCount(5993) >= 1L && questState.getQuestItemsCount(5994) >= 1L && questState.getQuestItemsCount(5995) >= 1L && questState.getQuestItemsCount(5996) >= 1L && questState.getQuestItemsCount(5997) >= 1L && questState.getQuestItemsCount(5998) >= 1L && questState.getQuestItemsCount(5999) >= 1L && questState.getQuestItemsCount(6000) >= 1L && questState.getQuestItemsCount(6001) >= 1L) {
                    questState.takeItems(5989, 1L);
                    questState.takeItems(5990, 1L);
                    questState.takeItems(5991, 1L);
                    questState.takeItems(5992, 1L);
                    questState.takeItems(5993, 1L);
                    questState.takeItems(5994, 1L);
                    questState.takeItems(5995, 1L);
                    questState.takeItems(5996, 1L);
                    questState.takeItems(5997, 1L);
                    questState.takeItems(5998, 1L);
                    questState.takeItems(5999, 1L);
                    questState.takeItems(6000, 1L);
                    questState.takeItems(6001, 1L);
                    int n3 = Rnd.get((int)100);
                    if (n3 < 10) {
                        questState.giveItems(5497, 1L);
                    } else if (n3 < 20) {
                        questState.giveItems(5509, 1L);
                    } else if (n3 < 30) {
                        questState.giveItems(5526, 1L);
                    } else if (n3 < 40) {
                        questState.giveItems(5497, 1L);
                        questState.giveItems(5509, 1L);
                        questState.giveItems(5526, 1L);
                    } else if (n3 < 51) {
                        questState.giveItems(5370, 1L);
                    } else if (n3 < 62) {
                        questState.giveItems(5394, 1L);
                    } else if (n3 < 79) {
                        questState.giveItems(5428, 1L);
                    } else if (n3 < 100) {
                        questState.giveItems(5370, 1L);
                        questState.giveItems(5394, 1L);
                        questState.giveItems(5428, 1L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "whouse_keeper_walderal_q0372_07b.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=9")) {
                if (questState.getQuestItemsCount(5989) < 1L || questState.getQuestItemsCount(5990) < 1L || questState.getQuestItemsCount(5991) < 1L || questState.getQuestItemsCount(5992) < 1L || questState.getQuestItemsCount(5993) < 1L || questState.getQuestItemsCount(5994) < 1L || questState.getQuestItemsCount(5995) < 1L || questState.getQuestItemsCount(5996) < 1L || questState.getQuestItemsCount(5997) < 1L || questState.getQuestItemsCount(5998) < 1L || questState.getQuestItemsCount(5999) < 1L || questState.getQuestItemsCount(6000) < 1L || questState.getQuestItemsCount(6001) < 1L) {
                    string2 = "whouse_keeper_walderal_q0372_07e.htm";
                } else if (questState.getQuestItemsCount(5989) >= 1L && questState.getQuestItemsCount(5990) >= 1L && questState.getQuestItemsCount(5991) >= 1L && questState.getQuestItemsCount(5992) >= 1L && questState.getQuestItemsCount(5993) >= 1L && questState.getQuestItemsCount(5994) >= 1L && questState.getQuestItemsCount(5995) >= 1L && questState.getQuestItemsCount(5996) >= 1L && questState.getQuestItemsCount(5997) >= 1L && questState.getQuestItemsCount(5998) >= 1L && questState.getQuestItemsCount(5999) >= 1L && questState.getQuestItemsCount(6000) >= 1L && questState.getQuestItemsCount(6001) >= 1L) {
                    questState.takeItems(5989, 1L);
                    questState.takeItems(5990, 1L);
                    questState.takeItems(5991, 1L);
                    questState.takeItems(5992, 1L);
                    questState.takeItems(5993, 1L);
                    questState.takeItems(5994, 1L);
                    questState.takeItems(5995, 1L);
                    questState.takeItems(5996, 1L);
                    questState.takeItems(5997, 1L);
                    questState.takeItems(5998, 1L);
                    questState.takeItems(5999, 1L);
                    questState.takeItems(6000, 1L);
                    questState.takeItems(6001, 1L);
                    int n4 = Rnd.get((int)100);
                    if (n4 < 17) {
                        questState.giveItems(5502, 1L);
                    } else if (n4 < 34) {
                        questState.giveItems(5514, 1L);
                    } else if (n4 < 49) {
                        questState.giveItems(5527, 1L);
                    } else if (n4 < 58) {
                        questState.giveItems(5502, 1L);
                        questState.giveItems(5514, 1L);
                        questState.giveItems(5527, 1L);
                    } else if (n4 < 70) {
                        questState.giveItems(5380, 1L);
                    } else if (n4 < 82) {
                        questState.giveItems(5404, 1L);
                    } else if (n4 < 92) {
                        questState.giveItems(5430, 1L);
                    } else if (n4 < 100) {
                        questState.giveItems(5380, 1L);
                        questState.giveItems(5404, 1L);
                        questState.giveItems(5430, 1L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "whouse_keeper_walderal_q0372_07c.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=10")) {
                if (questState.getQuestItemsCount(5989) < 1L || questState.getQuestItemsCount(5990) < 1L || questState.getQuestItemsCount(5991) < 1L || questState.getQuestItemsCount(5992) < 1L || questState.getQuestItemsCount(5993) < 1L || questState.getQuestItemsCount(5994) < 1L || questState.getQuestItemsCount(5995) < 1L || questState.getQuestItemsCount(5996) < 1L || questState.getQuestItemsCount(5997) < 1L || questState.getQuestItemsCount(5998) < 1L || questState.getQuestItemsCount(5999) < 1L || questState.getQuestItemsCount(6000) < 1L || questState.getQuestItemsCount(6001) < 1L) {
                    string2 = "whouse_keeper_walderal_q0372_07e.htm";
                } else if (questState.getQuestItemsCount(5989) >= 1L && questState.getQuestItemsCount(5990) >= 1L && questState.getQuestItemsCount(5991) >= 1L && questState.getQuestItemsCount(5992) >= 1L && questState.getQuestItemsCount(5993) >= 1L && questState.getQuestItemsCount(5994) >= 1L && questState.getQuestItemsCount(5995) >= 1L && questState.getQuestItemsCount(5996) >= 1L && questState.getQuestItemsCount(5997) >= 1L && questState.getQuestItemsCount(5998) >= 1L && questState.getQuestItemsCount(5999) >= 1L && questState.getQuestItemsCount(6000) >= 1L && questState.getQuestItemsCount(6001) >= 1L) {
                    questState.takeItems(5989, 1L);
                    questState.takeItems(5990, 1L);
                    questState.takeItems(5991, 1L);
                    questState.takeItems(5992, 1L);
                    questState.takeItems(5993, 1L);
                    questState.takeItems(5994, 1L);
                    questState.takeItems(5995, 1L);
                    questState.takeItems(5996, 1L);
                    questState.takeItems(5997, 1L);
                    questState.takeItems(5998, 1L);
                    questState.takeItems(5999, 1L);
                    questState.takeItems(6000, 1L);
                    questState.takeItems(6001, 1L);
                    int n5 = Rnd.get((int)100);
                    if (n5 < 17) {
                        questState.giveItems(5503, 1L);
                    } else if (n5 < 34) {
                        questState.giveItems(5515, 1L);
                    } else if (n5 < 49) {
                        questState.giveItems(5528, 1L);
                    } else if (n5 < 58) {
                        questState.giveItems(5503, 1L);
                        questState.giveItems(5515, 1L);
                        questState.giveItems(5528, 1L);
                    } else if (n5 < 70) {
                        questState.giveItems(5382, 1L);
                    } else if (n5 < 82) {
                        questState.giveItems(5406, 1L);
                    } else if (n5 < 92) {
                        questState.giveItems(5432, 1L);
                    } else if (n5 < 100) {
                        questState.giveItems(5382, 1L);
                        questState.giveItems(5406, 1L);
                        questState.giveItems(5432, 1L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string2 = "whouse_keeper_walderal_q0372_07d.htm";
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=372&reply=99")) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
                string2 = "whouse_keeper_walderal_q0372_05b.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("legacy_of_insolence");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30844) break;
                if (questState.getPlayer().getLevel() < 59) {
                    string = "whouse_keeper_walderal_q0372_01.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getPlayer().getLevel() < 59) break;
                string = "whouse_keeper_walderal_q0372_02.htm";
                break;
            }
            case 2: {
                if (n2 == 30844) {
                    if (n != 1) break;
                    string = "whouse_keeper_walderal_q0372_05.htm";
                    break;
                }
                if (n2 == 30839) {
                    if (questState.getQuestItemsCount(5984) < 1L || questState.getQuestItemsCount(5985) < 1L || questState.getQuestItemsCount(5986) < 1L || questState.getQuestItemsCount(5987) < 1L || questState.getQuestItemsCount(5988) < 1L) {
                        string = "trader_holly_q0372_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(5984) < 1L || questState.getQuestItemsCount(5985) < 1L || questState.getQuestItemsCount(5986) < 1L || questState.getQuestItemsCount(5987) < 1L || questState.getQuestItemsCount(5988) < 1L) break;
                    questState.takeItems(5984, 1L);
                    questState.takeItems(5985, 1L);
                    questState.takeItems(5986, 1L);
                    questState.takeItems(5987, 1L);
                    questState.takeItems(5988, 1L);
                    int n4 = Rnd.get((int)100);
                    if (n4 < 30) {
                        questState.giveItems(5496, 1L);
                    } else if (n4 < 60) {
                        questState.giveItems(5508, 1L);
                    } else if (n4 < 80) {
                        questState.giveItems(5525, 1L);
                    } else if (n4 < 90) {
                        questState.giveItems(5496, 1L);
                        questState.giveItems(5508, 1L);
                        questState.giveItems(5525, 1L);
                    } else if (n4 < 100) {
                        questState.giveItems(57, 4000L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string = "trader_holly_q0372_02.htm";
                    break;
                }
                if (n2 == 30855) {
                    if (questState.getQuestItemsCount(5972) < 1L || questState.getQuestItemsCount(5973) < 1L || questState.getQuestItemsCount(5974) < 1L || questState.getQuestItemsCount(5975) < 1L || questState.getQuestItemsCount(5976) < 1L || questState.getQuestItemsCount(5977) < 1L || questState.getQuestItemsCount(5978) < 1L) {
                        string = "magister_desmond_q0372_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(5972) < 1L || questState.getQuestItemsCount(5973) < 1L || questState.getQuestItemsCount(5974) < 1L || questState.getQuestItemsCount(5975) < 1L || questState.getQuestItemsCount(5976) < 1L || questState.getQuestItemsCount(5977) < 1L || questState.getQuestItemsCount(5978) < 1L) break;
                    questState.takeItems(5972, 1L);
                    questState.takeItems(5973, 1L);
                    questState.takeItems(5974, 1L);
                    questState.takeItems(5975, 1L);
                    questState.takeItems(5976, 1L);
                    questState.takeItems(5977, 1L);
                    questState.takeItems(5978, 1L);
                    int n5 = Rnd.get((int)100);
                    if (n5 < 31) {
                        questState.giveItems(5503, 1L);
                    } else if (n5 < 62) {
                        questState.giveItems(5515, 1L);
                    } else if (n5 < 75) {
                        questState.giveItems(5528, 1L);
                    } else if (n5 < 83) {
                        questState.giveItems(5503, 1L);
                        questState.giveItems(5515, 1L);
                        questState.giveItems(5528, 1L);
                    } else if (n5 < 100) {
                        questState.giveItems(57, 4000L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string = "magister_desmond_q0372_02.htm";
                    break;
                }
                if (n2 == 30929) {
                    if (questState.getQuestItemsCount(5979) < 1L || questState.getQuestItemsCount(5980) < 1L || questState.getQuestItemsCount(5981) < 1L || questState.getQuestItemsCount(5982) < 1L || questState.getQuestItemsCount(5983) < 1L) {
                        string = "patrin_q0372_01.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(5979) < 1L || questState.getQuestItemsCount(5980) < 1L || questState.getQuestItemsCount(5981) < 1L || questState.getQuestItemsCount(5982) < 1L || questState.getQuestItemsCount(5983) < 1L) break;
                    questState.takeItems(5979, 1L);
                    questState.takeItems(5980, 1L);
                    questState.takeItems(5981, 1L);
                    questState.takeItems(5982, 1L);
                    questState.takeItems(5983, 1L);
                    int n6 = Rnd.get((int)100);
                    if (n6 < 30) {
                        questState.giveItems(5497, 1L);
                    } else if (n6 < 60) {
                        questState.giveItems(5509, 1L);
                    } else if (n6 < 80) {
                        questState.giveItems(5526, 1L);
                    } else if (n6 < 90) {
                        questState.giveItems(5497, 1L);
                        questState.giveItems(5509, 1L);
                        questState.giveItems(5526, 1L);
                    } else if (n6 < 100) {
                        questState.giveItems(57, 4000L);
                    }
                    this.giveExtraReward(questState.getPlayer());
                    string = "patrin_q0372_02.htm";
                    break;
                }
                if (n2 != 31001) break;
                if (questState.getQuestItemsCount(5972) < 1L || questState.getQuestItemsCount(5973) < 1L || questState.getQuestItemsCount(5974) < 1L || questState.getQuestItemsCount(5975) < 1L || questState.getQuestItemsCount(5976) < 1L || questState.getQuestItemsCount(5977) < 1L || questState.getQuestItemsCount(5978) < 1L) {
                    string = "claudia_a_q0372_01.htm";
                    break;
                }
                if (questState.getQuestItemsCount(5972) < 1L || questState.getQuestItemsCount(5973) < 1L || questState.getQuestItemsCount(5974) < 1L || questState.getQuestItemsCount(5975) < 1L || questState.getQuestItemsCount(5976) < 1L || questState.getQuestItemsCount(5977) < 1L || questState.getQuestItemsCount(5978) < 1L) break;
                questState.takeItems(5972, 1L);
                questState.takeItems(5973, 1L);
                questState.takeItems(5974, 1L);
                questState.takeItems(5975, 1L);
                questState.takeItems(5976, 1L);
                questState.takeItems(5977, 1L);
                questState.takeItems(5978, 1L);
                int n7 = Rnd.get((int)100);
                if (n7 < 31) {
                    questState.giveItems(5502, 1L);
                } else if (n7 < 62) {
                    questState.giveItems(5514, 1L);
                } else if (n7 < 75) {
                    questState.giveItems(5527, 1L);
                } else if (n7 < 83) {
                    questState.giveItems(5502, 1L);
                    questState.giveItems(5514, 1L);
                    questState.giveItems(5527, 1L);
                } else if (n7 < 100) {
                    questState.giveItems(57, 4000L);
                }
                this.giveExtraReward(questState.getPlayer());
                string = "claudia_a_q0372_02.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 20817) {
            questState.rollAndGive(5966, 1, 32.0);
        } else if (n == 20821) {
            questState.rollAndGive(5966, 1, 41.0);
        } else if (n == 20825) {
            questState.rollAndGive(5966, 1, 44.7);
        } else if (n == 20829) {
            questState.rollAndGive(5967, 1, 45.1);
        } else if (n == 21069) {
            questState.rollAndGive(5968, 1, 28.0);
        } else if (n == 21063) {
            questState.rollAndGive(5969, 1, 29.0);
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
