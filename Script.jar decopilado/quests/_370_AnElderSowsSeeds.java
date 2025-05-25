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

public class _370_AnElderSowsSeeds
extends Quest
implements ScriptFile {
    private static final int aPB = 30612;
    private static final int aPC = 20082;
    private static final int aPD = 20084;
    private static final int aPE = 20086;
    private static final int aPF = 20089;
    private static final int aPG = 20090;
    private static final int aPH = 5916;
    private static final int aPI = 5917;
    private static final int aPJ = 5918;
    private static final int aPK = 5919;
    private static final int aPL = 5920;

    public _370_AnElderSowsSeeds() {
        super(0);
        this.addStartNpc(30612);
        this.addKillId(new int[]{20082, 20084, 20086, 20089, 20090});
        this.addQuestItem(new int[]{5916});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30612) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("sage_seeds_the_wasteland", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "sage_kasian_q0370_05.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "sage_kasian_q0370_03.htm";
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "sage_kasian_q0370_04.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(5918) == 0L || questState.getQuestItemsCount(5920) == 0L || questState.getQuestItemsCount(5919) == 0L || questState.getQuestItemsCount(5917) == 0L) {
                    string2 = "sage_kasian_q0370_07.htm";
                } else if (questState.getQuestItemsCount(5918) >= 1L && questState.getQuestItemsCount(5920) >= 1L && questState.getQuestItemsCount(5919) >= 1L && questState.getQuestItemsCount(5917) >= 1L) {
                    long l = questState.getQuestItemsCount(5918);
                    if (l > questState.getQuestItemsCount(5920)) {
                        l = questState.getQuestItemsCount(5920);
                    }
                    if (l > questState.getQuestItemsCount(5919)) {
                        l = questState.getQuestItemsCount(5919);
                    }
                    if (l > questState.getQuestItemsCount(5917)) {
                        l = questState.getQuestItemsCount(5917);
                    }
                    questState.giveItems(57, l * 3600L);
                    questState.takeItems(5918, l);
                    questState.takeItems(5920, l);
                    questState.takeItems(5919, l);
                    questState.takeItems(5917, l);
                    string2 = "sage_kasian_q0370_08.htm";
                }
            } else if (string.equalsIgnoreCase("reply_4")) {
                string2 = "sage_kasian_q0370_09.htm";
            } else if (string.equalsIgnoreCase("reply_5")) {
                if (questState.getQuestItemsCount(5918) == 0L || questState.getQuestItemsCount(5920) == 0L || questState.getQuestItemsCount(5919) == 0L || questState.getQuestItemsCount(5917) == 0L) {
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(true);
                    string2 = "sage_kasian_q0370_10.htm";
                } else if (questState.getQuestItemsCount(5918) >= 1L && questState.getQuestItemsCount(5920) >= 1L && questState.getQuestItemsCount(5919) >= 1L && questState.getQuestItemsCount(5917) >= 1L) {
                    long l = questState.getQuestItemsCount(5918);
                    if (l > questState.getQuestItemsCount(5920)) {
                        l = questState.getQuestItemsCount(5920);
                    }
                    if (l > questState.getQuestItemsCount(5919)) {
                        l = questState.getQuestItemsCount(5919);
                    }
                    if (l > questState.getQuestItemsCount(5917)) {
                        l = questState.getQuestItemsCount(5917);
                    }
                    questState.giveItems(57, l * 3600L);
                    questState.takeItems(5918, l);
                    questState.takeItems(5920, l);
                    questState.takeItems(5919, l);
                    questState.takeItems(5917, l);
                    questState.playSound("ItemSound.quest_finish");
                    questState.exitCurrentQuest(true);
                    string2 = "sage_kasian_q0370_11.htm";
                }
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("sage_seeds_the_wasteland");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 30612) break;
                if (questState.getPlayer().getLevel() >= 28) {
                    string = "sage_kasian_q0370_02.htm";
                    break;
                }
                string = "sage_kasian_q0370_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 != 30612 || n != 1) break;
                string = "sage_kasian_q0370_06.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("sage_seeds_the_wasteland");
        int n2 = npcInstance.getNpcId();
        if (n == 1) {
            if (n2 == 20082 || n2 == 20086) {
                questState.rollAndGive(5916, 1, 9.0);
            } else if (n2 == 20084) {
                questState.rollAndGive(5916, 1, 10.0);
            } else if (n2 == 20089) {
                questState.rollAndGive(5916, 1, 10.0);
            } else if (n2 == 20090) {
                questState.rollAndGive(5916, 1, 22.0);
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
