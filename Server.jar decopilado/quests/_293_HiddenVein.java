/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
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

import l2.commons.util.Rnd;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;
import quests._255_Tutorial;

public class _293_HiddenVein
extends Quest
implements ScriptFile {
    private static final int avs = 30535;
    private static final int avt = 30539;
    private static final int avu = 20446;
    private static final int avv = 20447;
    private static final int avw = 20448;
    private static final int avx = 1488;
    private static final int avy = 1489;
    private static final int avz = 1490;

    public _293_HiddenVein() {
        super(0);
        this.addStartNpc(30535);
        this.addTalkId(new int[]{30539});
        this.addKillId(new int[]{20446, 20447, 20448});
        this.addQuestItem(new int[]{1488, 1489, 1490});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30535) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                questState.setCond(1);
                string2 = "elder_filaur_q0293_03.htm";
            } else if (string.equalsIgnoreCase("reply_1")) {
                string2 = "elder_filaur_q0293_06.htm";
                questState.takeItems(1489, -1L);
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            } else if (string.equalsIgnoreCase("reply_2")) {
                string2 = "elder_filaur_q0293_07.htm";
            }
        } else if (n == 30539) {
            if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(1489) < 4L) {
                string2 = "chichirin_q0293_02.htm";
            } else if (string.equalsIgnoreCase("reply_1") && questState.getQuestItemsCount(1489) >= 4L) {
                string2 = "chichirin_q0293_03.htm";
                questState.giveItems(1490, 1L);
                questState.takeItems(1489, 4L);
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        String string = "no-quest";
        switch (n) {
            case 1: {
                if (n2 != 30535) break;
                if (questState.getPlayer().getRace() != Race.dwarf) {
                    string = "elder_filaur_q0293_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState.getPlayer().getLevel() >= 6) {
                    string = "elder_filaur_q0293_02.htm";
                    break;
                }
                string = "elder_filaur_q0293_01.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 30535) {
                    if (questState.getQuestItemsCount(1488) < 1L && questState.getQuestItemsCount(1490) < 1L) {
                        string = "elder_filaur_q0293_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1488) < 1L && questState.getQuestItemsCount(1490) >= 1L) {
                        string = "elder_filaur_q0293_08.htm";
                        if (questState.getQuestItemsCount(1490) >= 10L) {
                            questState.giveItems(57, questState.getQuestItemsCount(1490) * 500L + 2000L);
                        } else {
                            questState.giveItems(57, questState.getQuestItemsCount(1490) * 500L);
                        }
                        questState.takeItems(1490, -1L);
                        this.giveExtraReward(questState.getPlayer());
                        if (questState.getPlayer().getLevel() >= 25 || questState.getPlayer().getClassId().getLevel() != 1 || questState.getPlayer().getVarB("p1q2")) break;
                        questState.getPlayer().setVar("p1q2", "1", -1L);
                        questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Acquisition of Soulshot for beginners complete.\n Go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                        QuestState questState2 = questState.getPlayer().getQuestState(_255_Tutorial.class);
                        if (questState2 == null || questState2.getInt("tutorial_quest_ex") == 10) break;
                        questState.showQuestionMark(26);
                        questState2.set("tutorial_quest_ex", "10");
                        questState.playTutorialVoice("tutorial_voice_026");
                        questState.giveItems(5789, 6000L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1488) >= 1L && questState.getQuestItemsCount(1490) < 1L) {
                        string = "elder_filaur_q0293_05.htm";
                        if (questState.getQuestItemsCount(1488) >= 10L) {
                            questState.giveItems(57, questState.getQuestItemsCount(1488) * 5L + 2000L);
                        } else {
                            questState.giveItems(57, questState.getQuestItemsCount(1488) * 5L);
                        }
                        questState.takeItems(1488, -1L);
                        this.giveExtraReward(questState.getPlayer());
                        if (questState.getPlayer().getLevel() >= 25 || questState.getPlayer().getClassId().getLevel() != 1 || questState.getPlayer().getVarB("p1q2")) break;
                        questState.getPlayer().setVar("p1q2", "1", -1L);
                        questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Acquisition of Soulshot for beginners complete.\n Go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                        QuestState questState3 = questState.getPlayer().getQuestState(_255_Tutorial.class);
                        if (questState3 == null || questState3.getInt("tutorial_quest_ex") == 10) break;
                        questState.showQuestionMark(26);
                        questState3.set("tutorial_quest_ex", "10");
                        questState.playTutorialVoice("tutorial_voice_026");
                        questState.giveItems(5789, 6000L);
                        break;
                    }
                    if (questState.getQuestItemsCount(1488) < 1L || questState.getQuestItemsCount(1490) < 1L) break;
                    string = "elder_filaur_q0293_09.htm";
                    if (questState.getQuestItemsCount(1488) + questState.getQuestItemsCount(1490) >= 10L) {
                        questState.giveItems(57, questState.getQuestItemsCount(1488) * 5L + questState.getQuestItemsCount(1490) * 500L + 2000L);
                    } else {
                        questState.giveItems(57, questState.getQuestItemsCount(1488) * 5L + questState.getQuestItemsCount(1490) * 500L);
                    }
                    questState.takeItems(1490, -1L);
                    questState.takeItems(1488, -1L);
                    this.giveExtraReward(questState.getPlayer());
                    if (questState.getPlayer().getLevel() >= 25 || questState.getPlayer().getClassId().getLevel() != 1 || questState.getPlayer().getVarB("p1q2")) break;
                    questState.getPlayer().setVar("p1q2", "1", -1L);
                    questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Acquisition of Soulshot for beginners complete.\n Go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                    QuestState questState4 = questState.getPlayer().getQuestState(_255_Tutorial.class);
                    if (questState4 == null || questState4.getInt("tutorial_quest_ex") == 10) break;
                    questState.showQuestionMark(26);
                    questState4.set("tutorial_quest_ex", "10");
                    questState.playTutorialVoice("tutorial_voice_026");
                    questState.giveItems(5789, 6000L);
                    break;
                }
                if (n2 != 30539) break;
                string = "chichirin_q0293_01.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = Rnd.get((int)100);
        if (n > 50) {
            questState.rollAndGive(1488, 1, 100.0);
        } else if (n < 5) {
            questState.rollAndGive(1489, 1, 100.0);
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
