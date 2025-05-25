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

public class _606_WarwithVarkaSilenos
extends Quest
implements ScriptFile {
    private static final int boL = 21371;
    private static final int boM = 21365;
    private static final int boN = 21369;
    private static final int boO = 21350;
    private static final int boP = 21354;
    private static final int boQ = 21360;
    private static final int boR = 21366;
    private static final int boS = 21368;
    private static final int boT = 21357;
    private static final int boU = 21353;
    private static final int boV = 21364;
    private static final int boW = 21362;
    private static final int boX = 21355;
    private static final int boY = 21358;
    private static final int boZ = 21373;
    private static final int bpa = 31370;
    private static final int bpb = 7233;
    private static final int bpc = 7186;

    public _606_WarwithVarkaSilenos() {
        super(1);
        this.addStartNpc(31370);
        this.addKillId(new int[]{21350, 21353, 21354, 21355, 21357, 21358, 21360, 21362, 21364, 21365, 21366, 21368, 21369, 21371, 21373});
        this.addQuestItem(new int[]{7233});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equals("quest_accept")) {
            string2 = "elder_kadun_zu_ketra_q0606_0104.htm";
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
        } else if (string.equals("606_3")) {
            long l = questState.getQuestItemsCount(7233) / 5L;
            if (l > 0L) {
                string2 = "elder_kadun_zu_ketra_q0606_0202.htm";
                questState.takeItems(7233, l * 5L);
                questState.giveItems(7186, l);
            } else {
                string2 = "elder_kadun_zu_ketra_q0606_0203.htm";
            }
        } else if (string.equals("606_4")) {
            string2 = "elder_kadun_zu_ketra_q0606_0204.htm";
            questState.takeItems(7233, -1L);
            questState.playSound("ItemSound.quest_finish");
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = questState.getCond();
        if (n == 0) {
            if (questState.getPlayer().getLevel() >= 74) {
                string = "elder_kadun_zu_ketra_q0606_0101.htm";
            } else {
                string = "elder_kadun_zu_ketra_q0606_0103.htm";
                questState.exitCurrentQuest(true);
            }
        } else if (n == 1 && questState.getQuestItemsCount(7233) == 0L) {
            string = "elder_kadun_zu_ketra_q0606_0106.htm";
        } else if (n == 1 && questState.getQuestItemsCount(7233) > 0L) {
            string = "elder_kadun_zu_ketra_q0606_0105.htm";
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (questState.getCond() == 1) {
            if (n == 21371) {
                questState.rollAndGive(7233, 1, 71.3);
            } else if (n == 21365 || n == 21366 || n == 21368) {
                questState.rollAndGive(7233, 1, 56.8);
            } else if (n == 21369) {
                questState.rollAndGive(7233, 1, 66.4);
            } else if (n == 21350) {
                questState.rollAndGive(7233, 1, 50.0);
            } else if (n == 21354) {
                questState.rollAndGive(7233, 1, 52.2);
            } else if (n == 21360 || n == 21362) {
                questState.rollAndGive(7233, 1, 53.9);
            } else if (n == 21357 || n == 21358) {
                questState.rollAndGive(7233, 1, 52.9);
            } else if (n == 21353) {
                questState.rollAndGive(7233, 1, 51.0);
            } else if (n == 21364) {
                questState.rollAndGive(7233, 1, 55.8);
            } else if (n == 21355) {
                questState.rollAndGive(7233, 1, 51.9);
            } else if (n == 21373) {
                questState.rollAndGive(7233, 1, 73.8);
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
