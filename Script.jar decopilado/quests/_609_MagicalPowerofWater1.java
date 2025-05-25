/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 */
package quests;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;

public class _609_MagicalPowerofWater1
extends Quest
implements ScriptFile {
    private static final int bpv = 31371;
    private static final int bpw = 31372;
    private static final int bpx = 31684;
    private static final int bpy = 31561;
    private static final int bpz = 21350;
    private static final int bpA = 21351;
    private static final int bpB = 21353;
    private static final int bpC = 21354;
    private static final int bpD = 21355;
    private static final int bpE = 21357;
    private static final int bpF = 21358;
    private static final int bpG = 21360;
    private static final int bpH = 21361;
    private static final int bpI = 21362;
    private static final int bpJ = 21364;
    private static final int bpK = 21365;
    private static final int bpL = 21366;
    private static final int bpM = 21368;
    private static final int bpN = 21369;
    private static final int bpO = 21370;
    private static final int bpP = 21371;
    private static final int bpQ = 21372;
    private static final int bpR = 21373;
    private static final int bpS = 21374;
    private static final int bpT = 21375;
    private static final int bpU = 7237;
    private static final int bpV = 7081;
    private static final int bpW = 7238;
    private static final int bpX = 7212;
    private static final int bpY = 7213;
    private static final int bpZ = 7214;
    private static final int bqa = 7215;
    private static final int bqb = 1661;
    private static final Skill y = SkillTable.getInstance().getInfo(4547, 1);
    private static final int bqc = 4548;

    public _609_MagicalPowerofWater1() {
        super(0);
        this.addStartNpc(31371);
        this.addTalkId(new int[]{31372, 31561});
        this.addQuestItem(new int[]{7237});
        this.addAttackId(new int[]{21350, 21351, 21353, 21354, 21355, 21357, 21358, 21360, 21361, 21362, 21364, 21365, 21366, 21368, 21369, 21370, 21371, 21372, 21373, 21374, 21375});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpw1");
        if (n == 31371) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                questState.setCond(1);
                questState.set("magicalpw1", 1);
                string2 = "herald_wakan_q0609_02.htm";
            }
        } else if (n == 31561) {
            if (string.equalsIgnoreCase("reply_1")) {
                if (questState.getQuestItemsCount(1661) < 1L) {
                    string2 = "udans_box_q0609_02.htm";
                } else if (questState.getQuestItemsCount(1661) >= 1L && n2 == 2) {
                    string2 = "udans_box_q0609_03.htm";
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4548, 1), (Creature)questState.getPlayer(), true);
                    questState.giveItems(7237, 1L);
                    questState.takeItems(1661, 1L);
                    questState.setCond(3);
                    questState.set("magicalpw1", 4);
                    questState.playSound("ItemSound.quest_middle");
                } else if (questState.getQuestItemsCount(1661) >= 1L && n2 == 3) {
                    string2 = "udans_box_q0609_04.htm";
                    questState.takeItems(1661, 1L);
                }
            }
        } else if (string.equalsIgnoreCase("60901")) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"60904", (Object[])new Object[0]);
            if (!questState.isRunningQuestTimer("60901")) {
                questState.cancelQuestTimer("60901");
            }
            return null;
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpw1");
        if (n == 31371) {
            if (n2 == 1) {
                string = "herald_wakan_q0609_03.htm";
            } else if (questState.getPlayer().getLevel() >= 74) {
                if (questState.getQuestItemsCount(7212) > 0L || questState.getQuestItemsCount(7213) > 0L || questState.getQuestItemsCount(7214) > 0L || questState.getQuestItemsCount(7215) > 0L) {
                    string = "herald_wakan_q0609_01.htm";
                } else {
                    string = "herald_wakan_q0609_01a.htm";
                    questState.exitCurrentQuest(true);
                }
            } else if (questState.getPlayer().getLevel() < 74) {
                string = "herald_wakan_q0609_01b.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 31372) {
            if (n2 == 1) {
                string = "shaman_asefa_q0609_01.htm";
                questState.setCond(2);
                questState.set("magicalpw1", 2);
                questState.playSound("ItemSound.quest_middle");
            } else if (n2 == 2) {
                string = "shaman_asefa_q0609_02.htm";
            } else if (n2 == 3) {
                string = "shaman_asefa_q0609_03.htm";
                npcInstance.doCast(SkillTable.getInstance().getInfo(4548, 1), (Creature)questState.getPlayer(), true);
                questState.set("magicalpw1", 2);
            } else if (n2 == 4) {
                string = "shaman_asefa_q0609_04.htm";
                questState.giveItems(7238, 1L);
                questState.giveItems(7081, 1L);
                questState.takeItems(7237, -1L);
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            }
        } else if (n == 31561 && (n2 == 2 || n2 == 3)) {
            string = "udans_box_q0609_01.htm";
        }
        return string;
    }

    public String onAttack(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getInt("magicalpw1");
        if (n == 2) {
            NpcInstance npcInstance2 = questState.addSpawn(31684, questState.getPlayer().getX(), questState.getPlayer().getY(), questState.getPlayer().getZ(), questState.getPlayer().getHeading(), 50, 10000);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"60903", (Object[])new Object[0]);
            questState.startQuestTimer("60901", 9000L, npcInstance2);
            questState.set("magicalpw1", 3);
            npcInstance.doCast(SkillTable.getInstance().getInfo(y.getId(), 1), (Creature)questState.getPlayer(), true);
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
