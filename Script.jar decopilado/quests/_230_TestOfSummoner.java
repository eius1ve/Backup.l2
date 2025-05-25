/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.HashMap;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;

public class _230_TestOfSummoner
extends Quest
implements ScriptFile {
    static int MARK_OF_SUMMONER_ID = 3336;
    static int LETOLIZARDMAN_AMULET_ID = 3337;
    static int SAC_OF_REDSPORES_ID = 3338;
    static int KARULBUGBEAR_TOTEM_ID = 3339;
    static int SHARDS_OF_MANASHEN_ID = 3340;
    static int BREKAORC_TOTEM_ID = 3341;
    static int CRIMSON_BLOODSTONE_ID = 3342;
    static int TALONS_OF_TYRANT_ID = 3343;
    static int WINGS_OF_DRONEANT_ID = 3344;
    static int TUSK_OF_WINDSUS_ID = 3345;
    static int FANGS_OF_WYRM_ID = 3346;
    static int LARS_LIST1_ID = 3347;
    static int LARS_LIST2_ID = 3348;
    static int LARS_LIST3_ID = 3349;
    static int LARS_LIST4_ID = 3350;
    static int LARS_LIST5_ID = 3351;
    static int GALATEAS_LETTER_ID = 3352;
    static int BEGINNERS_ARCANA_ID = 3353;
    static int ALMORS_ARCANA_ID = 3354;
    static int CAMONIELL_ARCANA_ID = 3355;
    static int BELTHUS_ARCANA_ID = 3356;
    static int BASILLIA_ARCANA_ID = 3357;
    static int CELESTIEL_ARCANA_ID = 3358;
    static int BRYNTHEA_ARCANA_ID = 3359;
    static int CRYSTAL_OF_PROGRESS1_ID = 3360;
    static int CRYSTAL_OF_INPROGRESS1_ID = 3361;
    static int CRYSTAL_OF_FOUL1_ID = 3362;
    static int CRYSTAL_OF_DEFEAT1_ID = 3363;
    static int CRYSTAL_OF_VICTORY1_ID = 3364;
    static int CRYSTAL_OF_PROGRESS2_ID = 3365;
    static int CRYSTAL_OF_INPROGRESS2_ID = 3366;
    static int CRYSTAL_OF_FOUL2_ID = 3367;
    static int CRYSTAL_OF_DEFEAT2_ID = 3368;
    static int CRYSTAL_OF_VICTORY2_ID = 3369;
    static int CRYSTAL_OF_PROGRESS3_ID = 3370;
    static int CRYSTAL_OF_INPROGRESS3_ID = 3371;
    static int CRYSTAL_OF_FOUL3_ID = 3372;
    static int CRYSTAL_OF_DEFEAT3_ID = 3373;
    static int CRYSTAL_OF_VICTORY3_ID = 3374;
    static int CRYSTAL_OF_PROGRESS4_ID = 3375;
    static int CRYSTAL_OF_INPROGRESS4_ID = 3376;
    static int CRYSTAL_OF_FOUL4_ID = 3377;
    static int CRYSTAL_OF_DEFEAT4_ID = 3378;
    static int CRYSTAL_OF_VICTORY4_ID = 3379;
    static int CRYSTAL_OF_PROGRESS5_ID = 3380;
    static int CRYSTAL_OF_INPROGRESS5_ID = 3381;
    static int CRYSTAL_OF_FOUL5_ID = 3382;
    static int CRYSTAL_OF_DEFEAT5_ID = 3383;
    static int CRYSTAL_OF_VICTORY5_ID = 3384;
    static int CRYSTAL_OF_PROGRESS6_ID = 3385;
    static int CRYSTAL_OF_INPROGRESS6_ID = 3386;
    static int CRYSTAL_OF_FOUL6_ID = 3387;
    static int CRYSTAL_OF_DEFEAT6_ID = 3388;
    static int CRYSTAL_OF_VICTORY6_ID = 3389;
    static int[] npc = new int[]{30063, 30634, 30635, 30636, 30637, 30638, 30639, 30640};
    static int Lara = npc[0];
    static int Galatea = npc[1];
    static int Almors = npc[2];
    static int Camoniell = npc[3];
    static int Belthus = npc[4];
    static int Basilla = npc[5];
    static int Celestiel = npc[6];
    static int Brynthea = npc[7];
    static int[][] SUMMONERS = new int[][]{{30635, ALMORS_ARCANA_ID, CRYSTAL_OF_VICTORY1_ID}, {30636, CAMONIELL_ARCANA_ID, CRYSTAL_OF_VICTORY2_ID}, {30637, BELTHUS_ARCANA_ID, CRYSTAL_OF_VICTORY3_ID}, {30638, BASILLIA_ARCANA_ID, CRYSTAL_OF_VICTORY4_ID}, {30639, CELESTIEL_ARCANA_ID, CRYSTAL_OF_VICTORY5_ID}, {30640, BRYNTHEA_ARCANA_ID, CRYSTAL_OF_VICTORY6_ID}};
    static Map<Integer, String> NAMES = new HashMap<Integer, String>();
    static Map<Integer, Integer[]> DROPLIST_LARA;
    static String[] STATS;
    static int[][] LISTS;
    static Map<Integer, Integer[]> DROPLIST_SUMMON;
    static Map<Integer, String> DROPLIST_SUMMON_VARS;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _230_TestOfSummoner() {
        super(0);
        int n;
        this.addStartNpc(Galatea);
        for (int n2 : npc) {
            this.addTalkId(new int[]{n2});
        }
        Object object = DROPLIST_LARA.keySet().iterator();
        while (object.hasNext()) {
            n = (Integer)object.next();
            this.addKillId(new int[]{n});
        }
        object = DROPLIST_SUMMON.keySet().iterator();
        while (object.hasNext()) {
            n = (Integer)object.next();
            this.addKillId(new int[]{n});
            this.addAttackId(new int[]{n});
        }
        int n3 = 3337;
        while (n3 <= 3389) {
            this.addQuestItem(new int[]{n3++});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("30634-08.htm")) {
            for (String string3 : STATS) {
                if (string3.equalsIgnoreCase("Arcanas") || string3.equalsIgnoreCase("Lara_Part")) continue;
                if (string3.equalsIgnoreCase("cond")) {
                    questState.setCond(1);
                    continue;
                }
                questState.set(string3, "1");
            }
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equalsIgnoreCase("30634-07.htm")) {
            questState.giveItems(GALATEAS_LETTER_ID, 1L, false);
        } else if (string.equalsIgnoreCase("30063-02.htm")) {
            int n = Rnd.get((int)5) + 1;
            questState.giveItems(LISTS[n][0], 1L, false);
            questState.takeItems(GALATEAS_LETTER_ID, 1L);
            questState.set("Lara_Part", this.str(n));
            questState.set("step", "2");
            questState.setCond(2);
        } else if (string.equalsIgnoreCase("30063-04.htm")) {
            int n = Rnd.get((int)5) + 1;
            questState.giveItems(LISTS[n][0], 1L, false);
            questState.set("Lara_Part", this.str(n));
        } else if (string.equalsIgnoreCase("30635-02.htm")) {
            if (questState.getQuestItemsCount(BEGINNERS_ARCANA_ID) > 0L) {
                string2 = "30635-03.htm";
                questState.set("Almors", "2");
            }
        } else if (string.equalsIgnoreCase("30635-04.htm")) {
            questState.giveItems(CRYSTAL_OF_PROGRESS1_ID, 1L, false);
            questState.takeItems(CRYSTAL_OF_FOUL1_ID, -1L);
            questState.takeItems(CRYSTAL_OF_DEFEAT1_ID, -1L);
            questState.takeItems(BEGINNERS_ARCANA_ID, 1L);
        } else if (string.equalsIgnoreCase("30636-02.htm")) {
            if (questState.getQuestItemsCount(BEGINNERS_ARCANA_ID) > 0L) {
                string2 = "30636-03.htm";
                questState.set("Camoniell", "2");
            }
        } else if (string.equalsIgnoreCase("30636-04.htm")) {
            questState.giveItems(CRYSTAL_OF_PROGRESS2_ID, 1L, false);
            questState.takeItems(CRYSTAL_OF_FOUL2_ID, -1L);
            questState.takeItems(CRYSTAL_OF_DEFEAT2_ID, -1L);
            questState.takeItems(BEGINNERS_ARCANA_ID, 1L);
        } else if (string.equalsIgnoreCase("30637-02.htm")) {
            if (questState.getQuestItemsCount(BEGINNERS_ARCANA_ID) > 0L) {
                string2 = "30637-03.htm";
                questState.set("Belthus", "2");
            }
        } else if (string.equalsIgnoreCase("30637-04.htm")) {
            questState.giveItems(CRYSTAL_OF_PROGRESS3_ID, 1L, false);
            questState.takeItems(CRYSTAL_OF_FOUL3_ID, -1L);
            questState.takeItems(CRYSTAL_OF_DEFEAT3_ID, -1L);
            questState.takeItems(BEGINNERS_ARCANA_ID, 1L);
        } else if (string.equalsIgnoreCase("30638-02.htm")) {
            if (questState.getQuestItemsCount(BEGINNERS_ARCANA_ID) > 0L) {
                string2 = "30638-03.htm";
                questState.set("Basilla", "2");
            }
        } else if (string.equalsIgnoreCase("30638-04.htm")) {
            questState.giveItems(CRYSTAL_OF_PROGRESS4_ID, 1L, false);
            questState.takeItems(CRYSTAL_OF_FOUL4_ID, -1L);
            questState.takeItems(CRYSTAL_OF_DEFEAT4_ID, -1L);
            questState.takeItems(BEGINNERS_ARCANA_ID, 1L);
        } else if (string.equalsIgnoreCase("30639-02.htm")) {
            if (questState.getQuestItemsCount(BEGINNERS_ARCANA_ID) > 0L) {
                string2 = "30639-03.htm";
                questState.set("Celestiel", "2");
            }
        } else if (string.equalsIgnoreCase("30639-04.htm")) {
            questState.giveItems(CRYSTAL_OF_PROGRESS5_ID, 1L, false);
            questState.takeItems(CRYSTAL_OF_FOUL5_ID, -1L);
            questState.takeItems(CRYSTAL_OF_DEFEAT5_ID, -1L);
            questState.takeItems(BEGINNERS_ARCANA_ID, 1L);
        } else if (string.equalsIgnoreCase("30640-02.htm")) {
            if (questState.getQuestItemsCount(BEGINNERS_ARCANA_ID) > 0L) {
                string2 = "30640-03.htm";
                questState.set("Brynthea", "2");
            }
        } else if (string.equalsIgnoreCase("30640-04.htm")) {
            questState.giveItems(CRYSTAL_OF_PROGRESS6_ID, 1L, false);
            questState.takeItems(CRYSTAL_OF_FOUL6_ID, -1L);
            questState.takeItems(CRYSTAL_OF_DEFEAT6_ID, -1L);
            questState.takeItems(BEGINNERS_ARCANA_ID, 1L);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        Object object;
        block21: {
            int n;
            int n2;
            int n3;
            block23: {
                long l;
                long l2;
                int n4;
                block26: {
                    block25: {
                        block24: {
                            block22: {
                                int n5;
                                block20: {
                                    if (questState.getQuestItemsCount(MARK_OF_SUMMONER_ID) > 0L) {
                                        questState.exitCurrentQuest(true);
                                        return "completed";
                                    }
                                    object = "noquest";
                                    n3 = npcInstance.getNpcId();
                                    n5 = questState.getState();
                                    if (n5 != 1 || n3 != 30634) break block20;
                                    for (String string : STATS) {
                                        if (string.equalsIgnoreCase("cond")) {
                                            questState.setCond(0);
                                            continue;
                                        }
                                        questState.set(string, "0");
                                    }
                                    if (questState.getPlayer().getClassId() == ClassId.wizard || questState.getPlayer().getClassId() == ClassId.elven_wizard || questState.getPlayer().getClassId() == ClassId.dark_wizard) {
                                        if (questState.getPlayer().getLevel() > 38) {
                                            object = "30634-03.htm";
                                        } else {
                                            object = "30634-02.htm";
                                            questState.exitCurrentQuest(true);
                                        }
                                    } else {
                                        object = "30634-01.htm";
                                        questState.exitCurrentQuest(true);
                                    }
                                    break block21;
                                }
                                if (n5 != 2) break block21;
                                n4 = questState.getInt("Lara_Part");
                                n2 = questState.getInt("Arcanas");
                                n = questState.getInt("step");
                                if (n3 != 30634) break block22;
                                if (n == 1) {
                                    object = "30634-09.htm";
                                } else if (n == 2) {
                                    if (n2 == 6) {
                                        object = "30634-12.htm";
                                        questState.playSound("ItemSound.quest_finish");
                                        questState.takeItems(LARS_LIST1_ID, -1L);
                                        questState.takeItems(LARS_LIST2_ID, -1L);
                                        questState.takeItems(LARS_LIST3_ID, -1L);
                                        questState.takeItems(LARS_LIST4_ID, -1L);
                                        questState.takeItems(LARS_LIST5_ID, -1L);
                                        questState.takeItems(ALMORS_ARCANA_ID, -1L);
                                        questState.takeItems(BASILLIA_ARCANA_ID, -1L);
                                        questState.takeItems(CAMONIELL_ARCANA_ID, -1L);
                                        questState.takeItems(CELESTIEL_ARCANA_ID, -1L);
                                        questState.takeItems(BELTHUS_ARCANA_ID, -1L);
                                        questState.takeItems(BRYNTHEA_ARCANA_ID, -1L);
                                        questState.giveItems(MARK_OF_SUMMONER_ID, 1L);
                                        if (!questState.getPlayer().getVarB("prof2.3")) {
                                            questState.giveItems(7562, 8L);
                                            questState.addExpAndSp(148409L, 30000L);
                                            questState.getPlayer().setVar("prof2.3", "1", -1L);
                                            this.giveExtraReward(questState.getPlayer());
                                        }
                                        questState.playSound("ItemSound.quest_finish");
                                        questState.exitCurrentQuest(true);
                                    }
                                } else {
                                    object = "30634-10.htm";
                                }
                                break block21;
                            }
                            if (n3 != Lara) break block23;
                            if (n != 1) break block24;
                            object = "30063-01.htm";
                            break block21;
                        }
                        if (n4 != 0) break block25;
                        object = "30063-03.htm";
                        break block21;
                    }
                    l2 = questState.getQuestItemsCount(LISTS[n4][1]);
                    l = questState.getQuestItemsCount(LISTS[n4][2]);
                    if (l2 >= 30L && l >= 30L) break block26;
                    object = "30063-05.htm";
                    break block21;
                }
                if (l2 <= 29L || l <= 29L) break block21;
                object = "30063-06.htm";
                questState.giveItems(BEGINNERS_ARCANA_ID, 2L, false);
                questState.takeItems(LISTS[n4][0], 1L);
                questState.takeItems(LISTS[n4][1], -1L);
                questState.takeItems(LISTS[n4][2], -1L);
                questState.setCond(3);
                questState.set("Lara_Part", "0");
                break block21;
            }
            for (int[] nArray : SUMMONERS) {
                if (nArray[0] != n3) continue;
                Integer[] integerArray = DROPLIST_SUMMON.get(n3 - 30635 + 27102);
                int n6 = questState.getInt(NAMES.get(nArray[0]));
                if (n <= 1) continue;
                if (questState.getQuestItemsCount(integerArray[0].intValue()) > 0L) {
                    object = this.str(n3) + "-08.htm";
                    continue;
                }
                if (questState.getQuestItemsCount(integerArray[1].intValue()) > 0L) {
                    questState.addNotifyOfDeath(questState.getPlayer(), true);
                    object = this.str(n3) + "-09.htm";
                    continue;
                }
                if (questState.getQuestItemsCount(integerArray[3].intValue()) > 0L) {
                    object = this.str(n3) + "-05.htm";
                    continue;
                }
                if (questState.getQuestItemsCount(integerArray[2].intValue()) > 0L) {
                    object = this.str(n3) + "-06.htm";
                    continue;
                }
                if (questState.getQuestItemsCount(integerArray[4].intValue()) > 0L) {
                    object = this.str(n3) + "-07.htm";
                    questState.takeItems(SUMMONERS[n3 - 30635][2], -1L);
                    questState.giveItems(SUMMONERS[n3 - 30635][1], 1L, false);
                    if (questState.getQuestItemsCount(3354) + questState.getQuestItemsCount(3355) + questState.getQuestItemsCount(3356) + questState.getQuestItemsCount(3357) + questState.getQuestItemsCount(3358) + questState.getQuestItemsCount(3359) >= 6L) {
                        questState.setCond(4);
                    }
                    questState.set(NAMES.get(nArray[0]), "7");
                    questState.set("Arcanas", this.str(n2 + 1));
                    continue;
                }
                object = n6 == 7 ? this.str(n3) + "-10.htm" : this.str(n3) + "-01.htm";
            }
        }
        return object;
    }

    public String onDeath(Creature creature, Creature creature2, QuestState questState) {
        if (creature == null || creature2 == null) {
            return null;
        }
        int n = creature.getNpcId();
        if ((creature2 == questState.getPlayer() || creature2 == questState.getPlayer().getPet()) && n >= 27102 && n <= 27107) {
            String[] stringArray = new String[]{"Almors", "Camoniell", "Belthus", "Basilla", "Celestiel", "Brynthea"};
            String string = stringArray[n - 27102];
            Integer[] integerArray = DROPLIST_SUMMON.get(n);
            int n2 = integerArray[3];
            if (questState.getInt(string) == 3) {
                questState.set(string, "4");
                questState.giveItems(n2, 1L, false);
            }
        }
        return null;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n >= 27102 && n <= 27107) {
            String[] stringArray = new String[]{"Almors", "Camoniell", "Belthus", "Basilla", "Celestiel", "Brynthea"};
            String string = stringArray[n - 27102];
            Integer[] integerArray = DROPLIST_SUMMON.get(n);
            int n2 = integerArray[0];
            int n3 = integerArray[1];
            if (questState.getInt(string) == 2) {
                questState.set(string, "3");
                questState.giveItems(n3, 1L, false);
                questState.takeItems(n2, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
            if (questState.getQuestItemsCount(integerArray[2].intValue()) != 0L) {
                return null;
            }
            Summon summon = questState.getPlayer().getPet();
            if (summon == null || summon.isPet()) {
                questState.giveItems(integerArray[2].intValue(), 1L, false);
            }
        }
        return null;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (DROPLIST_LARA.containsKey(n)) {
            Integer[] integerArray = DROPLIST_LARA.get(n);
            String string = "Lara_Part";
            int n2 = integerArray[0];
            int n3 = integerArray[1];
            int n4 = integerArray[2];
            long l = questState.getQuestItemsCount(n4);
            if (questState.getInt(string) == n2 && l < 30L && Rnd.chance((int)n3)) {
                questState.giveItems(n4, 1L, true);
                if (l == 29L) {
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.playSound("ItemSound.quest_itemget");
                }
            }
        } else if (DROPLIST_SUMMON.containsKey(n)) {
            String[] stringArray = new String[]{"Almors", "Camoniell", "Belthus", "Basilla", "Celestiel", "Brynthea"};
            String string = stringArray[n - 27102];
            Integer[] integerArray = DROPLIST_SUMMON.get(n);
            int n5 = integerArray[1];
            int n6 = integerArray[2];
            int n7 = integerArray[4];
            if (questState.getInt(string) == 3) {
                boolean bl = questState.getQuestItemsCount(n6) == 0L;
                boolean bl2 = true;
                for (Integer n8 : DROPLIST_SUMMON.get(n)) {
                    if (!bl2) {
                        questState.takeItems(n8.intValue(), -1L);
                    }
                    bl2 = false;
                }
                questState.takeItems(n5, -1L);
                if (bl) {
                    questState.set(string, "6");
                    questState.giveItems(n7, 1L, false);
                    questState.playSound("ItemSound.quest_middle");
                } else {
                    questState.set(string, "5");
                }
            }
        }
        return null;
    }

    static {
        NAMES.put(30635, "Almors");
        NAMES.put(30636, "Camoniell");
        NAMES.put(30637, "Belthus");
        NAMES.put(30638, "Basilla");
        NAMES.put(30639, "Celestiel");
        NAMES.put(30640, "Brynthea");
        DROPLIST_LARA = new HashMap<Integer, Integer[]>();
        DROPLIST_LARA.put(20555, new Integer[]{1, 80, SAC_OF_REDSPORES_ID});
        DROPLIST_LARA.put(20557, new Integer[]{1, 25, LETOLIZARDMAN_AMULET_ID});
        DROPLIST_LARA.put(20558, new Integer[]{1, 25, LETOLIZARDMAN_AMULET_ID});
        DROPLIST_LARA.put(20559, new Integer[]{1, 25, LETOLIZARDMAN_AMULET_ID});
        DROPLIST_LARA.put(20580, new Integer[]{1, 50, LETOLIZARDMAN_AMULET_ID});
        DROPLIST_LARA.put(20581, new Integer[]{1, 75, LETOLIZARDMAN_AMULET_ID});
        DROPLIST_LARA.put(20582, new Integer[]{1, 75, LETOLIZARDMAN_AMULET_ID});
        DROPLIST_LARA.put(20600, new Integer[]{2, 80, KARULBUGBEAR_TOTEM_ID});
        DROPLIST_LARA.put(20563, new Integer[]{2, 80, SHARDS_OF_MANASHEN_ID});
        DROPLIST_LARA.put(20552, new Integer[]{3, 60, CRIMSON_BLOODSTONE_ID});
        DROPLIST_LARA.put(20267, new Integer[]{3, 25, BREKAORC_TOTEM_ID});
        DROPLIST_LARA.put(20268, new Integer[]{3, 25, BREKAORC_TOTEM_ID});
        DROPLIST_LARA.put(20271, new Integer[]{3, 25, BREKAORC_TOTEM_ID});
        DROPLIST_LARA.put(20269, new Integer[]{3, 50, BREKAORC_TOTEM_ID});
        DROPLIST_LARA.put(20270, new Integer[]{3, 50, BREKAORC_TOTEM_ID});
        DROPLIST_LARA.put(20553, new Integer[]{4, 70, TUSK_OF_WINDSUS_ID});
        DROPLIST_LARA.put(20192, new Integer[]{4, 50, TALONS_OF_TYRANT_ID});
        DROPLIST_LARA.put(20193, new Integer[]{4, 50, TALONS_OF_TYRANT_ID});
        DROPLIST_LARA.put(20089, new Integer[]{5, 30, WINGS_OF_DRONEANT_ID});
        DROPLIST_LARA.put(20090, new Integer[]{5, 60, WINGS_OF_DRONEANT_ID});
        DROPLIST_LARA.put(20176, new Integer[]{5, 50, FANGS_OF_WYRM_ID});
        STATS = new String[]{"cond", "step", "Lara_Part", "Arcanas", "Belthus", "Brynthea", "Celestiel", "Camoniell", "Basilla", "Almors"};
        LISTS = new int[][]{new int[0], {LARS_LIST1_ID, SAC_OF_REDSPORES_ID, LETOLIZARDMAN_AMULET_ID}, {LARS_LIST2_ID, KARULBUGBEAR_TOTEM_ID, SHARDS_OF_MANASHEN_ID}, {LARS_LIST3_ID, CRIMSON_BLOODSTONE_ID, BREKAORC_TOTEM_ID}, {LARS_LIST4_ID, TUSK_OF_WINDSUS_ID, TALONS_OF_TYRANT_ID}, {LARS_LIST5_ID, WINGS_OF_DRONEANT_ID, FANGS_OF_WYRM_ID}};
        DROPLIST_SUMMON = new HashMap<Integer, Integer[]>();
        DROPLIST_SUMMON.put(27102, new Integer[]{CRYSTAL_OF_PROGRESS1_ID, CRYSTAL_OF_INPROGRESS1_ID, CRYSTAL_OF_FOUL1_ID, CRYSTAL_OF_DEFEAT1_ID, CRYSTAL_OF_VICTORY1_ID});
        DROPLIST_SUMMON.put(27103, new Integer[]{CRYSTAL_OF_PROGRESS2_ID, CRYSTAL_OF_INPROGRESS2_ID, CRYSTAL_OF_FOUL2_ID, CRYSTAL_OF_DEFEAT2_ID, CRYSTAL_OF_VICTORY2_ID});
        DROPLIST_SUMMON.put(27104, new Integer[]{CRYSTAL_OF_PROGRESS3_ID, CRYSTAL_OF_INPROGRESS3_ID, CRYSTAL_OF_FOUL3_ID, CRYSTAL_OF_DEFEAT3_ID, CRYSTAL_OF_VICTORY3_ID});
        DROPLIST_SUMMON.put(27105, new Integer[]{CRYSTAL_OF_PROGRESS4_ID, CRYSTAL_OF_INPROGRESS4_ID, CRYSTAL_OF_FOUL4_ID, CRYSTAL_OF_DEFEAT4_ID, CRYSTAL_OF_VICTORY4_ID});
        DROPLIST_SUMMON.put(27106, new Integer[]{CRYSTAL_OF_PROGRESS5_ID, CRYSTAL_OF_INPROGRESS5_ID, CRYSTAL_OF_FOUL5_ID, CRYSTAL_OF_DEFEAT5_ID, CRYSTAL_OF_VICTORY5_ID});
        DROPLIST_SUMMON.put(27107, new Integer[]{CRYSTAL_OF_PROGRESS6_ID, CRYSTAL_OF_INPROGRESS6_ID, CRYSTAL_OF_FOUL6_ID, CRYSTAL_OF_DEFEAT6_ID, CRYSTAL_OF_VICTORY6_ID});
        DROPLIST_SUMMON_VARS = new HashMap<Integer, String>();
        NAMES.put(27102, "Almors");
        NAMES.put(27103, "Camoniell");
        NAMES.put(27104, "Belthus");
        NAMES.put(27105, "Basilla");
        NAMES.put(27106, "Celestiel");
        NAMES.put(27107, "Brynthea");
    }
}
