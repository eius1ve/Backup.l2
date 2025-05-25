/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 */
package quests;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;

public class _635_InTheDimensionalRift
extends Quest
implements ScriptFile {
    private static final int bxC = 7079;
    private static final int[][] Y = new int[][]{new int[0], {-41572, 209731, -5087}, {42950, 143934, -5381}, {45256, 123906, -5411}, {46192, 170290, -4981}, {111273, 174015, -5437}, {-20221, -250795, -8160}, {-21726, 77385, -5171}, {140405, 79679, -5427}, {-52366, 79097, -4741}, {118311, 132797, -4829}, {172185, -17602, -4901}, {83000, 209213, -5439}, {-19500, 13508, -4901}, {113865, 84543, -6541}};

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _635_InTheDimensionalRift() {
        super(0);
        int n;
        for (n = 31494; n < 31508; ++n) {
            this.addStartNpc(n);
        }
        for (n = 31095; n <= 31126; ++n) {
            if (n == 31111 || n == 31112 || n == 31113) continue;
            this.addStartNpc(n);
        }
        for (n = 31127; n <= 31141; ++n) {
            if (n == 31132 || n == 31133 || n == 31134 || n == 31135 || n == 31136) continue;
            this.addStartNpc(n);
        }
        n = 31488;
        while (n < 31494) {
            this.addTalkId(new int[]{n++});
        }
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = questState.getInt("id");
        String string3 = questState.get("loc");
        if (string.equals("5.htm")) {
            if (n > 0 || string3 != null) {
                if (this.g(questState.getPlayer().getLastNpc().getNpcId()) && !this.b(questState)) {
                    string2 = "Sorry...";
                    questState.exitCurrentQuest(true);
                    return string2;
                }
                questState.setState(2);
                questState.setCond(1);
                questState.getPlayer().teleToLocation(-114790, -180576, -6781);
            } else {
                string2 = "What are you trying to do?";
                questState.exitCurrentQuest(true);
            }
        } else if (string.equalsIgnoreCase("6.htm")) {
            questState.exitCurrentQuest(true);
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("id");
        String string2 = questState.get("loc");
        if (this.g(n) || this.h(n) || this.i(n)) {
            if (questState.getPlayer().getLevel() < 20) {
                questState.exitCurrentQuest(true);
                string = "1.htm";
            } else if (questState.getQuestItemsCount(7079) == 0L) {
                string = this.h(n) || this.i(n) ? "3.htm" : "3-ziggurat.htm";
            } else {
                questState.set("loc", questState.getPlayer().getLoc().toString());
                string = this.h(n) ? "4.htm" : "4-ziggurat.htm";
            }
        } else if (n2 > 0) {
            int[] nArray = Y[n2];
            questState.getPlayer().teleToLocation(nArray[0], nArray[1], nArray[2]);
            string = "7.htm";
            questState.exitCurrentQuest(true);
        } else if (string2 != null) {
            questState.getPlayer().teleToLocation(Location.parseLoc((String)string2));
            string = "7.htm";
            questState.exitCurrentQuest(true);
        } else {
            string = "Where are you from?";
            questState.exitCurrentQuest(true);
        }
        return string;
    }

    private boolean b(QuestState questState) {
        int n = questState.getPlayer().getLevel();
        int n2 = 0;
        n2 = n < 30 ? 2000 : (n < 40 ? 4500 : (n < 50 ? 8000 : (n < 60 ? 12500 : (n < 70 ? 18000 : 24500))));
        if (!questState.getPlayer().reduceAdena((long)n2, true)) {
            questState.getPlayer().sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            return false;
        }
        return true;
    }

    private boolean g(int n) {
        return n >= 31095 && n <= 31126;
    }

    private boolean h(int n) {
        return n >= 31494 && n <= 31508;
    }

    private boolean i(int n) {
        return n >= 31127 && n <= 31141;
    }
}
