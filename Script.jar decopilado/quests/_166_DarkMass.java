/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;

public class _166_DarkMass
extends Quest
implements ScriptFile {
    private static final int UM = 30130;
    private static final int UN = 30135;
    private static final int UO = 30139;
    private static final int UP = 30143;
    private static final int UQ = 1088;
    private static final int UR = 1089;
    private static final int US = 1090;
    private static final int UT = 1091;

    public _166_DarkMass() {
        super(0);
        this.addStartNpc(30130);
        this.addTalkId(new int[]{30135, 30139, 30143});
        this.addQuestItem(new int[]{1088, 1089, 1090, 1091});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(1088, 1L);
            string2 = "undres_q0322_04.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        Player player = questState.getPlayer();
        int n2 = npcInstance.getNpcId();
        switch (n) {
            case 1: {
                if (n2 != 30130) break;
                if (player.getRace() != Race.darkelf) {
                    string = "undres_q0322_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (player.getLevel() >= 2) {
                    string = "undres_q0322_03.htm";
                    break;
                }
                string = "undres_q0322_02.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 30130) {
                    if (questState.getQuestItemsCount(1088) == 1L && (questState.getQuestItemsCount(1091) < 1L || questState.getQuestItemsCount(1090) < 1L || questState.getQuestItemsCount(1089) < 1L)) {
                        string = "undres_q0322_05.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1088) != 1L || questState.getQuestItemsCount(1089) != 1L || questState.getQuestItemsCount(1090) != 1L || questState.getQuestItemsCount(1091) != 1L) break;
                    string = "undres_q0322_06.htm";
                    questState.takeItems(1089, 1L);
                    questState.takeItems(1090, 1L);
                    questState.takeItems(1091, 1L);
                    questState.takeItems(1088, 1L);
                    questState.giveItems(57, 500L);
                    if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("ng1")) {
                        questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("  Delivery duty complete.\nGo find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                    }
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    break;
                }
                if (n2 == 30135) {
                    if (questState.getQuestItemsCount(1088) != 1L || questState.getQuestItemsCount(1089) != 0L) break;
                    questState.giveItems(1089, 1L);
                    string = "iria_q0322_01.htm";
                    if (questState.getQuestItemsCount(1090) <= 0L || questState.getQuestItemsCount(1091) <= 0L) break;
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n2 == 30139) {
                    if (questState.getQuestItemsCount(1088) != 1L || questState.getQuestItemsCount(1090) != 0L) break;
                    questState.giveItems(1090, 1L);
                    string = "doran_q0322_01.htm";
                    if (questState.getQuestItemsCount(1089) <= 0L || questState.getQuestItemsCount(1091) <= 0L) break;
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n2 != 30143 || questState.getQuestItemsCount(1088) != 1L || questState.getQuestItemsCount(1091) != 0L) break;
                questState.giveItems(1091, 1L);
                string = "trudy_q0322_01.htm";
                if (questState.getQuestItemsCount(1089) <= 0L || questState.getQuestItemsCount(1090) <= 0L) break;
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return string;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
