/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 */
package quests;

import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;

public class _615_MagicalPowerofFire1
extends Quest
implements ScriptFile {
    private static final int brU = 31378;
    private static final int brV = 31379;
    private static final int brW = 31559;
    private static final int brX = 31685;
    private static final int brY = 7242;
    private static final int brZ = 7081;
    private static final int bsa = 7243;
    private static final int bsb = 7222;
    private static final int bsc = 7223;
    private static final int bsd = 7224;
    private static final int bse = 7225;
    private static final int bsf = 1661;
    private static final int bsg = 21344;
    private static final int bsh = 21346;
    private static final int bsi = 21345;
    private static final int bsj = 21332;
    private static final int bsk = 21336;
    private static final int bsl = 21335;
    private static final int bsm = 21324;
    private static final int bsn = 21343;
    private static final int bso = 21334;
    private static final int bsp = 21339;
    private static final int bsq = 21342;
    private static final int bsr = 21340;
    private static final int bss = 21328;
    private static final int bst = 21338;
    private static final int bsu = 21329;
    private static final int bsv = 21327;
    private static final int bsw = 21331;
    private static final int bsx = 21347;
    private static final int bsy = 21325;
    private static final int bsz = 4548;

    public _615_MagicalPowerofFire1() {
        super(0);
        this.addStartNpc(31378);
        this.addTalkId(new int[]{31379, 31685, 31559});
        this.addAttackId(new int[]{21344, 21346, 21345, 21332, 21336, 21335, 21324, 21343, 21334, 21339, 21342, 21340, 21328, 21338, 21329, 21327, 21331, 21347, 21325});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpf1");
        if (n == 31378) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.set("magicalpf1", 1);
                string2 = "herald_naran_q0615_02.htm";
                questState.setCond(1);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
            }
        } else if (n == 31559) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(1661) < 1L) {
                    string2 = "asefas_box_q0615_02.htm";
                } else if (questState.getQuestItemsCount(1661) >= 1L && n2 == 2) {
                    string2 = "asefas_box_q0615_03.htm";
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4548, 1), (Creature)questState.getPlayer(), true);
                    questState.giveItems(7242, 1L);
                    questState.takeItems(1661, 1L);
                    questState.setCond(3);
                    questState.set("magicalpf1", 4);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(1661) >= 1L && n2 == 3) {
                    string2 = "asefas_box_q0615_04.htm";
                    questState.takeItems(1661, 1L);
                }
            }
        } else if (string.equalsIgnoreCase("61501")) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"61504", (Object[])new Object[0]);
            if (!questState.isRunningQuestTimer("61501")) {
                questState.cancelQuestTimer("61501");
            }
            return null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpf1");
        if (n == 31378) {
            if (n2 == 1) {
                string = "herald_naran_q0615_03.htm";
            } else if (questState.getPlayer().getLevel() >= 74) {
                if (questState.getQuestItemsCount(7222) > 0L || questState.getQuestItemsCount(7223) > 0L || questState.getQuestItemsCount(7224) > 0L || questState.getQuestItemsCount(7225) > 0L) {
                    string = "herald_naran_q0615_01.htm";
                } else {
                    string = "herald_naran_q0615_01a.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (questState.getPlayer().getLevel() < 74) {
                string = "herald_naran_q0615_01b.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 31379) {
            if (n2 == 1) {
                string = "shaman_udan_q0615_01.htm";
                questState.setCond(2);
                questState.set("magicalpf1", 2);
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 2) {
                string = "shaman_udan_q0615_02.htm";
            } else if (n2 == 3) {
                string = "shaman_udan_q0615_03.htm";
                npcInstance.doCast(SkillTable.getInstance().getInfo(4548, 1), (Creature)questState.getPlayer(), true);
                questState.set("magicalpf1", 2);
            } else if (n2 == 4) {
                string = "shaman_udan_q0615_04.htm";
                questState.giveItems(7243, 1L);
                questState.giveItems(7081, 1L);
                questState.takeItems(7242, -1L);
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            }
        } else if (n == 31559 && (n2 == 2 || n2 == 3)) {
            string = "asefas_box_q0615_01.htm";
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("magicalpf1");
        if (n == 2) {
            NpcInstance npcInstance2 = questState.addSpawn(31685, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ(), questState.getPlayer().getHeading(), 50, 10000);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61503", (Object[])new Object[0]);
            questState.startQuestTimer("61501", 9000L, npcInstance2);
            questState.set("magicalpf1", 3);
            npcInstance.doCast(SkillTable.getInstance().getInfo(4548, 1), (Creature)questState.getPlayer(), true);
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
