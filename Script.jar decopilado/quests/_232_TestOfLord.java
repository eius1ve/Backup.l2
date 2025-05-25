/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Drop
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.HashMap;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Drop;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _232_TestOfLord
extends Quest
implements ScriptFile {
    private static int anx = 30510;
    private static int any = 30515;
    private static int anz = 30558;
    private static int anA = 30564;
    private static int anB = 30565;
    private static int anC = 30566;
    private static int anD = 30567;
    private static int anE = 30568;
    private static int anF = 30641;
    private static int anG = 30642;
    private static int anH = 30643;
    private static int anI = 30649;
    private static int anJ = 20233;
    private static int anK = 20269;
    private static int anL = 20270;
    private static int anM = 20564;
    private static int anN = 20583;
    private static int anO = 20584;
    private static int anP = 20585;
    private static int anQ = 20586;
    private static int anR = 20587;
    private static int anS = 20588;
    private static int anT = 20778;
    private static int anU = 20779;
    private static int anV = 3390;
    private static int anW = 1341;
    private static int anX = 7562;
    private static int anY = 3403;
    private static int anZ = 3398;
    private static int aoa = 3414;
    private static int aob = 3415;
    private static int aoc = 3407;
    private static int aod = 3408;
    private static int aoe = 3410;
    private static int aof = 3391;
    private static int aog = 3392;
    private static int aoh = 3393;
    private static int aoi = 3394;
    private static int aoj = 3395;
    private static int aok = 3396;
    private static int aol = 3397;
    private static int aom = 3399;
    private static int aon = 3400;
    private static int aoo = 3401;
    private static int aop = 3402;
    private static int aoq = 3404;
    private static int aor = 3405;
    private static int aos = 3406;
    private static int aot = 3409;
    private static int aou = 3411;
    private static int aov = 3412;
    private static int aow = 3413;
    private static int aox = 3416;
    private static Map<Integer, Drop> cd = new HashMap<Integer, Drop>();

    public _232_TestOfLord() {
        super(0);
        this.addStartNpc(anB);
        this.addTalkId(new int[]{anx});
        this.addTalkId(new int[]{any});
        this.addTalkId(new int[]{anz});
        this.addTalkId(new int[]{anA});
        this.addTalkId(new int[]{anC});
        this.addTalkId(new int[]{anD});
        this.addTalkId(new int[]{anE});
        this.addTalkId(new int[]{anF});
        this.addTalkId(new int[]{anG});
        this.addTalkId(new int[]{anH});
        this.addTalkId(new int[]{anI});
        cd.put(anN, new Drop(1, 10, 50).addItem(anY));
        cd.put(anO, new Drop(1, 10, 55).addItem(anY));
        cd.put(anP, new Drop(1, 10, 60).addItem(anY));
        cd.put(anQ, new Drop(1, 10, 65).addItem(anY));
        cd.put(anR, new Drop(1, 10, 70).addItem(anY));
        cd.put(anS, new Drop(1, 10, 75).addItem(anY));
        cd.put(anK, new Drop(1, 20, 40).addItem(anZ));
        cd.put(anL, new Drop(1, 20, 50).addItem(anZ));
        cd.put(anT, new Drop(4, 1, 100).addItem(aoa));
        cd.put(anU, new Drop(4, 1, 100).addItem(aob));
        cd.put(anJ, new Drop(1, 10, 100).addItem(aoc).addItem(aod));
        cd.put(anM, new Drop(1, 20, 90).addItem(aoe));
        for (int n : cd.keySet()) {
            this.addKillId(new int[]{n});
        }
        for (Drop drop : cd.values()) {
            for (int n : drop.itemList) {
                if (this.isQuestItem(n)) continue;
                this.addQuestItem(new int[]{n});
            }
        }
        this.addQuestItem(new int[]{aof});
        this.addQuestItem(new int[]{aog});
        this.addQuestItem(new int[]{aoh});
        this.addQuestItem(new int[]{aoi});
        this.addQuestItem(new int[]{aoj});
        this.addQuestItem(new int[]{aok});
        this.addQuestItem(new int[]{aol});
        this.addQuestItem(new int[]{aom});
        this.addQuestItem(new int[]{aon});
        this.addQuestItem(new int[]{aoo});
        this.addQuestItem(new int[]{aop});
        this.addQuestItem(new int[]{aoq});
        this.addQuestItem(new int[]{aor});
        this.addQuestItem(new int[]{aos});
        this.addQuestItem(new int[]{aot});
        this.addQuestItem(new int[]{aou});
        this.addQuestItem(new int[]{aov});
        this.addQuestItem(new int[]{aow});
        this.addQuestItem(new int[]{aox});
    }

    private static void c(QuestState questState) {
        questState.addSpawn(anH, 21036, -107690, -3038);
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = questState.getState();
        if (n == 1) {
            if (string.equalsIgnoreCase("30565-05.htm")) {
                questState.giveItems(aof, 1L);
                questState.setState(2);
                questState.setCond(1);
                questState.playSound("ItemSound.quest_accept");
            }
        } else if (n == 2) {
            if (string.equalsIgnoreCase("30565-12.htm") && questState.getQuestItemsCount(aox) > 0L) {
                questState.takeItems(aox, -1L);
                questState.giveItems(anV, 1L);
                if (!questState.getPlayer().getVarB("prof2.3")) {
                    questState.giveItems(anX, 8L);
                    questState.addExpAndSp(92955L, 16250L);
                    questState.getPlayer().setVar("prof2.3", "1", -1L);
                    this.giveExtraReward(questState.getPlayer());
                }
                questState.playSound("ItemSound.quest_finish");
                questState.unset("cond");
                questState.exitCurrentQuest(true);
            } else if (string.equalsIgnoreCase("30565-08.htm")) {
                questState.takeItems(aoq, -1L);
                questState.takeItems(aos, -1L);
                questState.takeItems(aou, -1L);
                questState.takeItems(aot, -1L);
                questState.takeItems(aof, -1L);
                questState.takeItems(aon, -1L);
                questState.giveItems(aov, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(3);
            } else if (string.equalsIgnoreCase("30566-02.htm")) {
                questState.giveItems(aog, 1L);
            } else if (string.equalsIgnoreCase("30567-02.htm")) {
                questState.giveItems(aoh, 1L);
            } else if (string.equalsIgnoreCase("30558-02.htm") && questState.getQuestItemsCount(57) >= 1000L) {
                questState.takeItems(57, 1000L);
                questState.giveItems(aor, 1L);
                questState.playSound("ItemSound.quest_middle");
            } else if (string.equalsIgnoreCase("30568-02.htm")) {
                questState.giveItems(aoi, 1L);
            } else if (string.equalsIgnoreCase("30641-02.htm")) {
                questState.giveItems(aoj, 1L);
            } else if (string.equalsIgnoreCase("30642-02.htm")) {
                questState.giveItems(aok, 1L);
            } else if (string.equalsIgnoreCase("30649-04.htm") && questState.getQuestItemsCount(aov) > 0L) {
                questState.takeItems(aov, -1L);
                questState.giveItems(aow, 1L);
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(4);
            } else if (string.equalsIgnoreCase("30649-07.htm")) {
                questState.setCond(6);
                _232_TestOfLord.c(questState);
            }
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (questState.getQuestItemsCount(anV) > 0L) {
            questState.exitCurrentQuest(true);
            return "completed";
        }
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getCond();
        if (n == 1) {
            if (n2 != anB) {
                return "noquest";
            }
            if (questState.getPlayer().getRace() != Race.orc) {
                questState.exitCurrentQuest(true);
                return "30565-01.htm";
            }
            if (questState.getPlayer().getClassId().getId() != 50) {
                questState.exitCurrentQuest(true);
                return "30565-02.htm";
            }
            if (questState.getPlayer().getLevel() < 39) {
                questState.exitCurrentQuest(true);
                return "30565-03.htm";
            }
            questState.setCond(0);
            return "30565-04.htm";
        }
        if (n != 2 || n3 < 1) {
            return "noquest";
        }
        long l = questState.getQuestItemsCount(aof);
        long l2 = questState.getQuestItemsCount(aon);
        long l3 = questState.getQuestItemsCount(aoq);
        long l4 = questState.getQuestItemsCount(aos);
        long l5 = questState.getQuestItemsCount(aou);
        long l6 = questState.getQuestItemsCount(aot);
        long l7 = questState.getQuestItemsCount(aov);
        long l8 = questState.getQuestItemsCount(aow);
        long l9 = questState.getQuestItemsCount(aox);
        long l10 = questState.getQuestItemsCount(aog);
        long l11 = questState.getQuestItemsCount(aom);
        long l12 = questState.getQuestItemsCount(aol);
        long l13 = questState.getQuestItemsCount(anZ);
        long l14 = questState.getQuestItemsCount(aoh);
        long l15 = questState.getQuestItemsCount(aor);
        long l16 = questState.getQuestItemsCount(aoi);
        long l17 = questState.getQuestItemsCount(aop);
        long l18 = questState.getQuestItemsCount(anY);
        long l19 = questState.getQuestItemsCount(aoo);
        long l20 = questState.getQuestItemsCount(aoj);
        if (n2 == anB) {
            if (l > 0L) {
                return this.a(questState) ? "30565-07.htm" : "30565-06.htm";
            }
            if (l7 > 0L) {
                return "30565-09.htm";
            }
            if (l8 > 0L) {
                return "30565-10.htm";
            }
            if (l9 > 0L) {
                return "30565-11.htm";
            }
        }
        if (n2 == anC && l > 0L) {
            if (l2 > 0L) {
                return "30566-05.htm";
            }
            if (l10 == 0L) {
                return "30566-01.htm";
            }
            if (l11 == 0L) {
                return "30566-03.htm";
            }
            questState.takeItems(aog, -1L);
            questState.takeItems(aom, -1L);
            questState.giveItems(aon, 1L);
            if (this.a(questState)) {
                questState.playSound("ItemSound.quest_jackpot");
                questState.setCond(2);
            } else {
                questState.playSound("ItemSound.quest_middle");
            }
            return "30566-04.htm";
        }
        if (n2 == any && l > 0L) {
            if (l10 > 0L && l2 == 0L) {
                if (l11 == 0L) {
                    if (l12 == 0L) {
                        questState.giveItems(aol, 1L);
                        return "30515-01.htm";
                    }
                    if (l13 < 20L) {
                        return "30515-02.htm";
                    }
                    questState.takeItems(aol, -1L);
                    questState.takeItems(anZ, -1L);
                    questState.giveItems(aom, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    return "30515-03.htm";
                }
                if (l12 == 0L) {
                    return "30515-04.htm";
                }
            } else if (l10 == 0L && l2 > 0L && l11 == 0L && l12 == 0L) {
                return "30515-05.htm";
            }
        }
        if (n2 == anD) {
            if (l4 == 0L) {
                if (l14 == 0L) {
                    return "30567-01.htm";
                }
                if (l15 == 0L || questState.getQuestItemsCount(anW) < 1000L) {
                    return "30567-03.htm";
                }
                questState.takeItems(aoh, -1L);
                questState.takeItems(aor, -1L);
                questState.takeItems(anW, 1000L);
                questState.giveItems(aos, 1L);
                if (this.a(questState)) {
                    questState.playSound("ItemSound.quest_jackpot");
                    questState.setCond(2);
                } else {
                    questState.playSound("ItemSound.quest_middle");
                }
                return "30567-04.htm";
            }
            if (l14 == 0L) {
                return "30567-05.htm";
            }
        }
        if (n2 == anz) {
            if (l14 > 0L && l4 == 0L) {
                if (l15 > 0L) {
                    return "30558-04.htm";
                }
                return questState.getQuestItemsCount(57) < 1000L ? "30558-03.htm" : "30558-01.htm";
            }
            if (l14 == 0L && l4 > 0L) {
                return "30558-05.htm";
            }
        }
        if (n2 == anE) {
            if (l3 == 0L) {
                if (l16 == 0L) {
                    return "30568-01.htm";
                }
                if (l17 == 0L || l18 < 10L) {
                    return "30568-03.htm";
                }
                questState.takeItems(aoi, -1L);
                questState.takeItems(aop, -1L);
                questState.takeItems(anY, -1L);
                questState.giveItems(aoq, 1L);
                if (this.a(questState)) {
                    questState.playSound("ItemSound.quest_jackpot");
                    questState.setCond(2);
                } else {
                    questState.playSound("ItemSound.quest_middle");
                }
                return "30568-04.htm";
            }
            if (l16 == 0L) {
                return "30568-05.htm";
            }
        }
        if (n2 == anA) {
            if (l16 > 0L && l3 == 0L) {
                if (l17 == 0L) {
                    if (l19 > 0L) {
                        return "30564-02.htm";
                    }
                    questState.giveItems(aoo, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    return "30564-01.htm";
                }
                if (l19 == 0L) {
                    return "30564-03.htm";
                }
            } else if (l16 == 0L && l3 > 0L && l17 == 0L && l19 == 0L) {
                return "30564-04.htm";
            }
        }
        if (n2 == anx) {
            if (l3 == 0L) {
                if (l17 == 0L && l16 > 0L && l19 > 0L) {
                    questState.takeItems(aoo, -1L);
                    questState.giveItems(aop, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    return "30510-01.htm";
                }
                if (l17 > 0L && l16 > 0L && l19 == 0L) {
                    return "30510-02.htm";
                }
            } else if (l17 == 0L && l16 == 0L && l19 == 0L) {
                return "30510-03.htm";
            }
        }
        if (n2 == anF) {
            if (l6 == 0L) {
                if (l20 == 0L) {
                    return "30641-01.htm";
                }
                if (questState.getQuestItemsCount(aoc) < 10L || questState.getQuestItemsCount(aod) < 10L) {
                    return "30641-03.htm";
                }
                questState.takeItems(aoc, -1L);
                questState.takeItems(aod, -1L);
                questState.takeItems(aoj, -1L);
                questState.giveItems(aot, 1L);
                if (this.a(questState)) {
                    questState.playSound("ItemSound.quest_jackpot");
                    questState.setCond(2);
                } else {
                    questState.playSound("ItemSound.quest_middle");
                }
                return "30641-04.htm";
            }
            if (l20 == 0L) {
                return "30641-05.htm";
            }
        }
        if (n2 == anG) {
            long l21 = questState.getQuestItemsCount(aok);
            if (l5 == 0L) {
                if (l21 == 0L) {
                    return "30642-01.htm";
                }
                if (questState.getQuestItemsCount(aoe) < 20L) {
                    return "30642-03.htm";
                }
                questState.takeItems(aoe, -1L);
                questState.takeItems(aok, -1L);
                questState.giveItems(aou, 1L);
                if (this.a(questState)) {
                    questState.playSound("ItemSound.quest_jackpot");
                    questState.setCond(2);
                } else {
                    questState.playSound("ItemSound.quest_middle");
                }
                return "30642-04.htm";
            }
            if (l21 == 0L) {
                return "30642-05.htm";
            }
        }
        if (n2 == anI) {
            if (l7 > 0L) {
                return "30649-01.htm";
            }
            if (l8 > 0L) {
                if (n3 == 5 || questState.getQuestItemsCount(aob) > 0L && questState.getQuestItemsCount(aoa) > 0L) {
                    questState.takeItems(aow, -1L);
                    questState.takeItems(aoa, -1L);
                    questState.takeItems(aob, -1L);
                    questState.giveItems(aox, 1L);
                    questState.playSound("ItemSound.quest_middle");
                    return "30649-06.htm";
                }
                return "30649-05.htm";
            }
            if (n3 == 6 || n3 == 7) {
                return "30649-08.htm";
            }
        }
        if (n2 == anH && questState.getQuestItemsCount(aox) > 0L) {
            questState.setCond(7);
            return "30643-01.htm";
        }
        return "noquest";
    }

    private boolean a(QuestState questState) {
        long l = questState.getQuestItemsCount(aon);
        long l2 = questState.getQuestItemsCount(aoq);
        long l3 = questState.getQuestItemsCount(aos);
        long l4 = questState.getQuestItemsCount(aou);
        long l5 = questState.getQuestItemsCount(aot);
        return l > 0L && l2 > 0L && l3 > 0L && l4 > 0L && l5 > 0L;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        Drop drop = cd.get(n);
        if (drop == null) {
            return null;
        }
        int n2 = questState.getCond();
        for (int n3 : drop.itemList) {
            long l = questState.getQuestItemsCount(aof);
            if (n3 == anY && (l <= 0L || questState.getQuestItemsCount(aoi) <= 0L || questState.getQuestItemsCount(aoq) != 0L) || n3 == anZ && (l <= 0L || questState.getQuestItemsCount(aog) <= 0L || questState.getQuestItemsCount(aol) <= 0L) || n == anJ && (l <= 0L || questState.getQuestItemsCount(aoj) <= 0L) || n == anM && (l <= 0L || questState.getQuestItemsCount(aok) <= 0L)) continue;
            long l2 = questState.getQuestItemsCount(n3);
            if (n2 != drop.condition || l2 >= (long)drop.maxcount || !Rnd.chance((int)drop.chance)) continue;
            questState.giveItems(n3, 1L);
            if (l2 + 1L == (long)drop.maxcount) {
                if (n2 == 4 && questState.getQuestItemsCount(aoa) > 0L && questState.getQuestItemsCount(aob) > 0L) {
                    questState.setCond(5);
                }
                questState.playSound("ItemSound.quest_middle");
                continue;
            }
            questState.playSound("ItemSound.quest_itemget");
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
