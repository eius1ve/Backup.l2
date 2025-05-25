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

import java.util.ArrayList;
import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _233_TestOfWarspirit
extends Quest
implements ScriptFile {
    private static int anx = 30510;
    private static int aoy = 30030;
    private static int aoz = 30436;
    private static int aoA = 30507;
    private static int any = 30515;
    private static int alQ = 30630;
    private static int anI = 30649;
    private static int aoB = 30682;
    private static int aoC = 20213;
    private static int ahx = 20214;
    private static int aoD = 20215;
    private static int aoE = 20089;
    private static int aoF = 20090;
    private static int aoG = 20581;
    private static int aoH = 20582;
    private static int adV = 20158;
    private static int aoI = 27108;
    private static int aoJ = 20601;
    private static int aoK = 20602;
    private static int anX = 7562;
    private static int aoL = 2879;
    private static int aoM = 2880;
    private static int aoN = 2881;
    private static int aoO = 2882;
    private static int aoP = 2883;
    private static int aoQ = 2884;
    private static int aoR = 2885;
    private static int aoS = 2886;
    private static int aoT = 2887;
    private static int aoU = 2888;
    private static int aoV = 2889;
    private static int aoW = 2890;
    private static int aoX = 2891;
    private static int aoY = 2892;
    private static int aoZ = 2893;
    private static int apa = 2894;
    private static int apb = 2895;
    private static int apc = 2896;
    private static int apd = 2897;
    private static int ape = 2898;
    private static int apf = 2899;
    private static int apg = 2900;
    private static int aph = 2901;
    private static int api = 2902;
    private static int apj = 2903;
    private static int apk = 2904;
    private static int apl = 2905;
    private static int apm = 2906;
    private static int apn = 2907;
    private static int apo = 2908;
    private static int app = 2909;
    private static int apq = 2910;
    private static int apr = 2911;
    private static int aps = 2912;
    private static int apt = 2913;
    private static int apu = 2914;
    private static int[] bv = new int[]{app, apo, apn, apm, apl};
    private static int[] bw = new int[]{aoV, aoW, aoX, aoY, aoZ};
    private static int[] bx = new int[]{apd, ape, apg, apf};

    public _233_TestOfWarspirit() {
        super(0);
        this.addStartNpc(anx);
        this.addTalkId(new int[]{aoy});
        this.addTalkId(new int[]{aoz});
        this.addTalkId(new int[]{aoA});
        this.addTalkId(new int[]{any});
        this.addTalkId(new int[]{alQ});
        this.addTalkId(new int[]{anI});
        this.addTalkId(new int[]{aoB});
        this.addKillId(new int[]{aoC});
        this.addKillId(new int[]{ahx});
        this.addKillId(new int[]{aoD});
        this.addKillId(new int[]{aoE});
        this.addKillId(new int[]{aoF});
        this.addKillId(new int[]{aoG});
        this.addKillId(new int[]{aoH});
        this.addKillId(new int[]{adV});
        this.addKillId(new int[]{aoI});
        this.addKillId(new int[]{aoJ});
        this.addKillId(new int[]{aoK});
        this.addQuestItem(new int[]{aoM});
        this.addQuestItem(new int[]{aoN});
        this.addQuestItem(new int[]{aoO});
        this.addQuestItem(new int[]{aoP});
        this.addQuestItem(new int[]{aoQ});
        this.addQuestItem(new int[]{aoR});
        this.addQuestItem(new int[]{aoS});
        this.addQuestItem(new int[]{aoT});
        this.addQuestItem(new int[]{aoU});
        this.addQuestItem(new int[]{aoV});
        this.addQuestItem(new int[]{aoW});
        this.addQuestItem(new int[]{aoX});
        this.addQuestItem(new int[]{aoY});
        this.addQuestItem(new int[]{aoZ});
        this.addQuestItem(new int[]{apa});
        this.addQuestItem(new int[]{apb});
        this.addQuestItem(new int[]{apc});
        this.addQuestItem(new int[]{apd});
        this.addQuestItem(new int[]{ape});
        this.addQuestItem(new int[]{apf});
        this.addQuestItem(new int[]{apg});
        this.addQuestItem(new int[]{aph});
        this.addQuestItem(new int[]{api});
        this.addQuestItem(new int[]{apj});
        this.addQuestItem(new int[]{apk});
        this.addQuestItem(new int[]{apl});
        this.addQuestItem(new int[]{apm});
        this.addQuestItem(new int[]{apn});
        this.addQuestItem(new int[]{apo});
        this.addQuestItem(new int[]{app});
        this.addQuestItem(new int[]{apq});
        this.addQuestItem(new int[]{apr});
        this.addQuestItem(new int[]{aps});
        this.addQuestItem(new int[]{apt});
        this.addQuestItem(new int[]{apu});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        if (string.equalsIgnoreCase("30510-05.htm") && n == 1) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30630-04.htm") && n == 2) {
            questState.giveItems(aoP, 1L);
        } else if (string.equalsIgnoreCase("30682-02.htm") && n == 2) {
            questState.giveItems(aoU, 1L);
        } else if (string.equalsIgnoreCase("30515-02.htm") && n == 2) {
            questState.giveItems(apb, 1L);
        } else if (string.equalsIgnoreCase("30507-02.htm") && n == 2) {
            questState.giveItems(api, 1L);
        } else if (string.equalsIgnoreCase("30030-04.htm") && n == 2) {
            questState.giveItems(apj, 1L);
        } else if (string.equalsIgnoreCase("30649-03.htm") && n == 2 && questState.getQuestItemsCount(aoO) > 0L) {
            questState.takeItems(aoO, -1L);
            questState.takeItems(apr, -1L);
            questState.takeItems(apt, -1L);
            questState.takeItems(apu, -1L);
            questState.takeItems(aoN, -1L);
            questState.takeItems(aps, -1L);
            questState.giveItems(aoL, 1L);
            if (!questState.getPlayer().getVarB("prof2.3")) {
                questState.giveItems(anX, 8L);
                questState.addExpAndSp(63483L, 17500L);
                questState.getPlayer().setVar("prof2.3", "1", -1L);
                this.giveExtraReward(questState.getPlayer());
            }
            questState.playSound("ItemSound.quest_finish");
            questState.unset("cond");
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(aoL) > 0L) {
            questState.exitCurrentQuest(true);
            return "completed";
        }
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 != anx) {
                return "noquest";
            }
            if (questState.getPlayer().getRace() != Race.orc) {
                questState.exitCurrentQuest(true);
                return "30510-01.htm";
            }
            if (questState.getPlayer().getClassId().getId() != 50) {
                questState.exitCurrentQuest(true);
                return "30510-02.htm";
            }
            if (questState.getPlayer().getLevel() < 39) {
                questState.exitCurrentQuest(true);
                return "30510-03.htm";
            }
            questState.setCond(0);
            return "30510-04.htm";
        }
        if (n != 2 || questState.getCond() != 1) {
            return "noquest";
        }
        if (n2 == anx) {
            if (questState.getQuestItemsCount(aoM) > 0L) {
                if (questState.getQuestItemsCount(aoN) < 13L) {
                    return "30510-08.htm";
                }
                questState.takeItems(aoM, -1L);
                questState.giveItems(aoO, 1L);
                questState.giveItems(apr, 1L);
                questState.giveItems(apt, 1L);
                questState.giveItems(apu, 1L);
                questState.giveItems(aps, 1L);
                questState.playSound("ItemSound.quest_middle");
                return "30510-09.htm";
            }
            if (questState.getQuestItemsCount(aoO) > 0L) {
                return "30510-10.htm";
            }
            if (questState.getQuestItemsCount(aoT) == 0L || questState.getQuestItemsCount(aph) == 0L || questState.getQuestItemsCount(apq) == 0L || questState.getQuestItemsCount(apa) == 0L) {
                return "30510-06.htm";
            }
            questState.takeItems(aoT, -1L);
            questState.takeItems(aph, -1L);
            questState.takeItems(apq, -1L);
            questState.takeItems(apa, -1L);
            questState.giveItems(aoM, 1L);
            questState.playSound("ItemSound.quest_middle");
            return "30510-07.htm";
        }
        if (n2 == alQ) {
            if (questState.getQuestItemsCount(aoP) > 0L) {
                if (questState.getQuestItemsCount(aoQ) < 10L || questState.getQuestItemsCount(aoR) < 10L || questState.getQuestItemsCount(aoS) < 10L) {
                    return "30630-05.htm";
                }
                questState.takeItems(aoP, -1L);
                questState.takeItems(aoQ, -1L);
                questState.takeItems(aoR, -1L);
                questState.takeItems(aoS, -1L);
                questState.giveItems(aoT, 1L);
                questState.playSound("ItemSound.quest_middle");
                return "30630-06.htm";
            }
            if (questState.getQuestItemsCount(aoT) == 0L && questState.getQuestItemsCount(apr) == 0L && questState.getQuestItemsCount(aoM) == 0L) {
                return "30630-01.htm";
            }
            return "30630-07.htm";
        }
        if (n2 == aoB) {
            if (questState.getQuestItemsCount(aoU) > 0L) {
                for (int n3 : bw) {
                    if (questState.getQuestItemsCount(n3) != 0L) continue;
                    return "30682-03.htm";
                }
                questState.takeItems(aoU, -1L);
                for (int n3 : bw) {
                    if (questState.getQuestItemsCount(n3) != 0L) continue;
                    questState.takeItems(n3, -1L);
                }
                questState.giveItems(apa, 1L);
                questState.playSound("ItemSound.quest_middle");
                return "30682-04.htm";
            }
            if (questState.getQuestItemsCount(apa) == 0L && questState.getQuestItemsCount(aps) == 0L && questState.getQuestItemsCount(aoM) == 0L) {
                return "30682-01.htm";
            }
            return "30682-05.htm";
        }
        if (n2 == any) {
            if (questState.getQuestItemsCount(apb) > 0L) {
                if (questState.getQuestItemsCount(apc) == 0L) {
                    return "30515-03.htm";
                }
                for (int n4 : bx) {
                    if (questState.getQuestItemsCount(n4) != 0L) continue;
                    return "30515-03.htm";
                }
                questState.takeItems(apb, -1L);
                questState.takeItems(apc, -1L);
                for (int n4 : bx) {
                    if (questState.getQuestItemsCount(n4) != 0L) continue;
                    questState.takeItems(n4, -1L);
                }
                questState.giveItems(aph, 1L);
                questState.playSound("ItemSound.quest_middle");
                return "30515-04.htm";
            }
            if (questState.getQuestItemsCount(aph) == 0L && questState.getQuestItemsCount(apt) == 0L && questState.getQuestItemsCount(aoM) == 0L) {
                return "30515-01.htm";
            }
            if (questState.getQuestItemsCount(api) == 0L && (questState.getQuestItemsCount(apu) > 0L || questState.getQuestItemsCount(aoO) > 0L || questState.getQuestItemsCount(apr) > 0L || questState.getQuestItemsCount(apt) > 0L || questState.getQuestItemsCount(aoN) > 0L || questState.getQuestItemsCount(aps) > 0L)) {
                return "30515-05.htm";
            }
        }
        if (n2 == aoA) {
            if (questState.getQuestItemsCount(api) > 0L) {
                if (questState.getQuestItemsCount(apk) == 0L) {
                    return questState.getQuestItemsCount(apj) == 0L ? "30507-03.htm" : "30507-04.htm";
                }
                if (questState.getQuestItemsCount(apj) == 0L) {
                    for (int n5 : bv) {
                        if (questState.getQuestItemsCount(n5) != 0L) continue;
                        return "30507-05.htm";
                    }
                    questState.takeItems(api, -1L);
                    questState.takeItems(apk, -1L);
                    for (int n5 : bv) {
                        if (questState.getQuestItemsCount(n5) != 0L) continue;
                        questState.takeItems(n5, -1L);
                    }
                    questState.giveItems(apq, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    return "30507-06.htm";
                }
            } else {
                if (questState.getQuestItemsCount(apq) == 0L && questState.getQuestItemsCount(apu) == 0L && questState.getQuestItemsCount(aoM) == 0L) {
                    return "30507-01.htm";
                }
                return "30507-07.htm";
            }
        }
        if (n2 == aoy) {
            if (questState.getQuestItemsCount(api) > 0L) {
                if (questState.getQuestItemsCount(apk) == 0L) {
                    return questState.getQuestItemsCount(apj) == 0L ? "30030-01.htm" : "30030-05.htm";
                }
                if (questState.getQuestItemsCount(apj) == 0L) {
                    return "30030-06.htm";
                }
            } else if (questState.getQuestItemsCount(apq) == 0L && questState.getQuestItemsCount(apu) == 0L && questState.getQuestItemsCount(aoM) == 0L) {
                return "30030-07.htm";
            }
        }
        if (n2 == aoz) {
            if (questState.getQuestItemsCount(api) > 0L) {
                if (questState.getQuestItemsCount(apk) == 0L && questState.getQuestItemsCount(apj) > 0L) {
                    questState.takeItems(apj, -1L);
                    questState.giveItems(apk, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    return "30436-01.htm";
                }
                if (questState.getQuestItemsCount(apj) == 0L && questState.getQuestItemsCount(apk) > 0L) {
                    return "30436-02.htm";
                }
            } else if (questState.getQuestItemsCount(apq) == 0L && questState.getQuestItemsCount(apu) == 0L && questState.getQuestItemsCount(aoM) == 0L) {
                return "30436-03.htm";
            }
        }
        if (n2 == anI && questState.getQuestItemsCount(aoO) > 0L) {
            return "30649-01.htm";
        }
        return "noquest";
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2 || questState.getCond() < 1) {
            return null;
        }
        int n = npcInstance.getNpcId();
        if (n == aoC && questState.getQuestItemsCount(aoP) > 0L && questState.getQuestItemsCount(aoQ) < 10L) {
            questState.giveItems(aoQ, 1L);
            questState.playSound(questState.getQuestItemsCount(aoQ) == 10L ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
        } else if (n == ahx && questState.getQuestItemsCount(aoP) > 0L && questState.getQuestItemsCount(aoR) < 10L) {
            questState.giveItems(aoR, 1L);
            questState.playSound(questState.getQuestItemsCount(aoR) == 10L ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
        } else if (n == aoD && questState.getQuestItemsCount(aoP) > 0L && questState.getQuestItemsCount(aoS) < 10L) {
            questState.giveItems(aoS, 1L);
            questState.playSound(questState.getQuestItemsCount(aoS) == 10L ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
        } else if ((n == aoE || n == aoF) && questState.getQuestItemsCount(api) > 0L) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int n2 : bv) {
                if (questState.getQuestItemsCount(n2) != 0L) continue;
                arrayList.add(n2);
            }
            if (arrayList.size() > 0 && Rnd.chance((int)30)) {
                int n3 = (Integer)arrayList.get(Rnd.get((int)arrayList.size()));
                questState.giveItems(n3, 1L);
                questState.playSound(arrayList.size() == 1 ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
            }
            arrayList.clear();
            arrayList = null;
        } else if ((n == aoG || n == aoH) && questState.getQuestItemsCount(aoU) > 0L) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int n4 : bw) {
                if (questState.getQuestItemsCount(n4) != 0L) continue;
                arrayList.add(n4);
            }
            if (arrayList.size() > 0 && Rnd.chance((int)25)) {
                int n5 = (Integer)arrayList.get(Rnd.get((int)arrayList.size()));
                questState.giveItems(n5, 1L);
                questState.playSound(arrayList.size() == 1 ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
            }
            arrayList.clear();
            arrayList = null;
        } else if (n == adV && questState.getQuestItemsCount(apb) > 0L) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int n6 : bx) {
                if (questState.getQuestItemsCount(n6) != 0L) continue;
                arrayList.add(n6);
            }
            if (arrayList.size() > 0 && Rnd.chance((int)30)) {
                int n7 = (Integer)arrayList.get(Rnd.get((int)arrayList.size()));
                questState.giveItems(n7, 1L);
                questState.playSound(arrayList.size() == 1 && questState.getQuestItemsCount(apc) > 0L ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
            }
            arrayList.clear();
            arrayList = null;
        } else if (n == aoI && questState.getQuestItemsCount(apb) > 0L && questState.getQuestItemsCount(apc) == 0L && Rnd.chance((int)30)) {
            questState.giveItems(apc, 1L);
            boolean bl = true;
            for (int n8 : bx) {
                if (questState.getQuestItemsCount(n8) != 0L) continue;
                bl = false;
                break;
            }
            questState.playSound(bl ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
        } else if ((n == aoJ || n == aoK) && questState.getQuestItemsCount(aoM) > 0L && questState.getQuestItemsCount(aoN) < 13L && Rnd.chance((int)(n == aoJ ? 30 : 50))) {
            questState.giveItems(aoN, 1L);
            questState.playSound(questState.getQuestItemsCount(aoN) == 13L ? "ItemSound.quest_middle" : "ItemSound.quest_itemget");
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
