/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.ScriptFile;
import quests._118_ToLeadAndBeLed;

public class _123_TheLeaderAndTheFollower
extends Quest
implements ScriptFile {
    private static final int Sl = 31961;
    private static final int Sm = 27321;
    private static final int Sn = 27322;
    private static final int So = 8550;
    private static final int Sp = 8549;
    private static final int Sq = 1458;
    private static final int Sr = 7850;
    private static final int Ss = 7851;
    private static final int St = 7852;
    private static final int Su = 7853;
    private static final int Sv = 7854;
    private static final int Sw = 7855;
    private static final int Sx = 7856;
    private static final int Sy = 7857;
    private static final int Sz = 7858;
    private static final int SA = 7859;

    public _123_TheLeaderAndTheFollower() {
        super(0);
        this.addStartNpc(31961);
        this.addKillId(new int[]{27321, 27322});
        this.addQuestItem(new int[]{8549, 8550});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        Player player = questState.getPlayer();
        int n = questState.getInt("leader_and_the_follower");
        QuestState questState2 = questState.getPlayer().getQuestState(_118_ToLeadAndBeLed.class);
        if (string.equalsIgnoreCase("quest_accept") && (questState2 == null || !questState2.isStarted()) && questState.getPlayer().getLevel() >= 19 && questState.getPlayer().getSponsor() > 0) {
            questState.setCond(1);
            questState.set("leader_and_the_follower", String.valueOf(1), true);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            string2 = "head_blacksmith_newyear_q0123_03.htm";
        } else if (string.equalsIgnoreCase("reply_1")) {
            int n2;
            Player player2;
            if (questState.getPlayer().getApprentice() > 0 && (player2 = World.getPlayer((int)(n2 = questState.getPlayer().getApprentice()))) != null && player2.isOnline() && player.getDistance((GameObject)player2) <= 1500.0) {
                if (player2.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2 && player2.getQuestState((Quest)this).getInt("leader_and_the_follower_ex") == 1 && questState.getQuestItemsCount(1458) >= 922L) {
                    questState.takeItems(1458, 922L);
                    player2.getQuestState((Quest)this).set("leader_and_the_follower", String.valueOf(3), true);
                    player2.getQuestState((Quest)this).setCond(6);
                    player2.getQuestState((Quest)this).playSound("ItemSound.quest_middle");
                    string2 = "head_blacksmith_newyear_q0123_10.htm";
                } else if (player2.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2 && player2.getQuestState((Quest)this).getInt("leader_and_the_follower_ex") == 2 && questState.getQuestItemsCount(1458) >= 771L) {
                    questState.takeItems(1458, 771L);
                    player2.getQuestState((Quest)this).set("leader_and_the_follower", String.valueOf(3), true);
                    player2.getQuestState((Quest)this).setCond(6);
                    player2.getQuestState((Quest)this).playSound("ItemSound.quest_middle");
                    string2 = "head_blacksmith_newyear_q0123_10.htm";
                } else if (player2.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2 && player2.getQuestState((Quest)this).getInt("leader_and_the_follower_ex") == 3 && questState.getQuestItemsCount(1458) >= 771L) {
                    questState.takeItems(1458, 771L);
                    player2.getQuestState((Quest)this).set("leader_and_the_follower", String.valueOf(3), true);
                    player2.getQuestState((Quest)this).setCond(6);
                    player2.getQuestState((Quest)this).playSound("ItemSound.quest_middle");
                    string2 = "head_blacksmith_newyear_q0123_10.htm";
                } else if (player2.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2 && player2.getQuestState((Quest)this).getInt("leader_and_the_follower_ex") == 1 && questState.getQuestItemsCount(1458) < 922L) {
                    string2 = "head_blacksmith_newyear_q0123_11.htm";
                } else if (player2.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2 && player2.getQuestState((Quest)this).getInt("leader_and_the_follower_ex") == 2 && questState.getQuestItemsCount(1458) < 771L) {
                    string2 = "head_blacksmith_newyear_q0123_11a.htm";
                } else if (player2.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2 && player2.getQuestState((Quest)this).getInt("leader_and_the_follower_ex") == 3 && questState.getQuestItemsCount(1458) < 771L) {
                    string2 = "head_blacksmith_newyear_q0123_11a.htm";
                }
            }
        } else if (string.equalsIgnoreCase("reply_21")) {
            string2 = "head_blacksmith_newyear_q0123_05a.htm";
        } else if (string.equalsIgnoreCase("reply_22")) {
            string2 = "head_blacksmith_newyear_q0123_05b.htm";
        } else if (string.equalsIgnoreCase("reply_23")) {
            string2 = "head_blacksmith_newyear_q0123_05c.htm";
        } else if (string.equalsIgnoreCase("reply_24")) {
            if (n == 1 && questState.getQuestItemsCount(8549) >= 10L) {
                questState.takeItems(8549, questState.getQuestItemsCount(8549));
                questState.set("leader_and_the_follower", String.valueOf(2), true);
                questState.set("leader_and_the_follower_ex", String.valueOf(1), true);
                questState.setCond(3);
                questState.playSound("ItemSound.quest_middle");
                string2 = "head_blacksmith_newyear_q0123_05d.htm";
            }
        } else if (string.equalsIgnoreCase("reply_25")) {
            if (n == 1 && questState.getQuestItemsCount(8549) >= 10L) {
                questState.takeItems(8549, questState.getQuestItemsCount(8549));
                questState.set("leader_and_the_follower", String.valueOf(2), true);
                questState.set("leader_and_the_follower_ex", String.valueOf(2), true);
                questState.setCond(4);
                questState.playSound("ItemSound.quest_middle");
                string2 = "head_blacksmith_newyear_q0123_05e.htm";
            }
        } else if (string.equalsIgnoreCase("reply_26")) {
            if (n == 1 && questState.getQuestItemsCount(8549) >= 10L) {
                questState.takeItems(8549, questState.getQuestItemsCount(8549));
                questState.set("leader_and_the_follower", "2", true);
                questState.set("leader_and_the_follower_ex", String.valueOf(3), true);
                questState.setCond(5);
                questState.playSound("ItemSound.quest_middle");
                string2 = "head_blacksmith_newyear_q0123_05f.htm";
            }
        } else if (string.equalsIgnoreCase("reply_27")) {
            string2 = "head_blacksmith_newyear_q0123_05g.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        int n = questState.getInt("leader_and_the_follower");
        int n2 = questState.getInt("leader_and_the_follower_ex");
        int n3 = questState.getState();
        QuestState questState2 = questState.getPlayer().getQuestState(_118_ToLeadAndBeLed.class);
        int n4 = questState.getPlayer().getSponsor();
        Player player2 = World.getPlayer((int)n4);
        int n5 = questState.getPlayer().getApprentice();
        Player player3 = World.getPlayer((int)n5);
        switch (n3) {
            case 1: {
                if (player3 != null && player.getApprentice() > 0 && player3.isOnline() && player3.getQuestState((Quest)this) != null && player3.getQuestState((Quest)this).isStarted()) {
                    if (player.getDistance((GameObject)player3) > 1500.0) {
                        string = "head_blacksmith_newyear_q0123_09.htm";
                        break;
                    }
                    if (!(player.getDistance((GameObject)player3) <= 1500.0)) break;
                    if (player3.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2) {
                        string = "head_blacksmith_newyear_q0123_08.htm";
                        break;
                    }
                    if (player3.getQuestState((Quest)this).getInt("leader_and_the_follower") == 3) {
                        string = "head_blacksmith_newyear_q0123_12.htm";
                        break;
                    }
                    string = "head_blacksmith_newyear_q0123_14.htm";
                    break;
                }
                if (questState2 == null && player.getLevel() >= 19 && player.getSponsor() > 0 && player2 != null && player2.isOnline()) {
                    string = "head_blacksmith_newyear_q0123_01.htm";
                    break;
                }
                if (!(questState2 != null || player.getLevel() >= 19 && player.getSponsor() != 0 && player2 != null && player2.isOnline())) {
                    string = "head_blacksmith_newyear_q0123_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState2 != null && questState2.isStarted()) {
                    string = "head_blacksmith_newyear_q0123_02b.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (questState2 == null || !questState2.isCompleted()) break;
                string = "head_blacksmith_newyear_q0123_02a.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n == 1 && questState.getQuestItemsCount(8549) < 10L) {
                    string = "head_blacksmith_newyear_q0123_04.htm";
                    break;
                }
                if (n == 1 && questState.getQuestItemsCount(8549) >= 10L) {
                    string = "head_blacksmith_newyear_q0123_05.htm";
                    break;
                }
                if (n == 2) {
                    if (questState.getPlayer().getSponsor() == 0) {
                        if (n2 == 1) {
                            string = "head_blacksmith_newyear_q0123_06a.htm";
                            break;
                        }
                        if (n2 == 2) {
                            string = "head_blacksmith_newyear_q0123_06b.htm";
                            break;
                        }
                        if (n2 != 3) break;
                        string = "head_blacksmith_newyear_q0123_06c.htm";
                        break;
                    }
                    if (player2 == null || !player2.isOnline() || player.getDistance((GameObject)player2) <= 1500.0) {
                        if (n2 == 1) {
                            string = "head_blacksmith_newyear_q0123_06.htm";
                            break;
                        }
                        if (n2 == 2) {
                            string = "head_blacksmith_newyear_q0123_06d.htm";
                            break;
                        }
                        if (n2 != 3) break;
                        string = "head_blacksmith_newyear_q0123_06e.htm";
                        break;
                    }
                    if (!(player.getDistance((GameObject)player2) > 1500.0)) break;
                    string = "head_blacksmith_newyear_q0123_07.htm";
                    break;
                }
                if (player.getApprentice() > 0) {
                    if (player3 == null || player.getDistance((GameObject)player3) > 1500.0) {
                        string = "head_blacksmith_newyear_q0123_09.htm";
                        break;
                    }
                    if (!(player.getDistance((GameObject)player3) <= 1500.0)) break;
                    if (player3.getQuestState((Quest)this).getInt("leader_and_the_follower") == 2) {
                        string = "head_blacksmith_newyear_q0123_08.htm";
                        break;
                    }
                    if (player3.getQuestState((Quest)this).getInt("leader_and_the_follower") == 3) {
                        string = "head_blacksmith_newyear_q0123_12.htm";
                        break;
                    }
                    string = "head_blacksmith_newyear_q0123_14.htm";
                    break;
                }
                if (n == 3) {
                    questState.set("leader_and_the_follower", String.valueOf(4), true);
                    questState.setCond(7);
                    questState.playSound("ItemSound.quest_middle");
                    string = "head_blacksmith_newyear_q0123_15.htm";
                    break;
                }
                if (n == 4 && questState.getQuestItemsCount(8550) < 8L) {
                    string = "head_blacksmith_newyear_q0123_16.htm";
                    break;
                }
                if (n != 4 || questState.getQuestItemsCount(8550) < 8L) break;
                if (n2 == 1) {
                    questState.giveItems(7850, 1L);
                    questState.giveItems(7851, 1L);
                    questState.giveItems(7852, 1L);
                    questState.giveItems(7853, 1L);
                    questState.takeItems(8550, questState.getQuestItemsCount(8550));
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                } else if (n2 == 2) {
                    questState.giveItems(7850, 1L);
                    questState.giveItems(7854, 1L);
                    questState.giveItems(7855, 1L);
                    questState.giveItems(7856, 1L);
                    questState.takeItems(8550, questState.getQuestItemsCount(8550));
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                } else if (n2 == 3) {
                    questState.giveItems(7850, 1L);
                    questState.giveItems(7857, 1L);
                    questState.giveItems(7858, 1L);
                    questState.giveItems(7859, 1L);
                    questState.takeItems(8550, questState.getQuestItemsCount(8550));
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                }
                string = "head_blacksmith_newyear_q0123_17.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        Player player;
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getInt("leader_and_the_follower");
        if (n2 == 27321 && Rnd.get((int)10) < 7 && n3 == 1 && questState.getQuestItemsCount(8549) < 10L) {
            questState.rollAndGive(8549, 1, 100.0);
            if (questState.getQuestItemsCount(8549) >= 10L) {
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n2 == 27322 && n3 == 4 && questState.getQuestItemsCount(8550) < 8L && Rnd.get((int)10) < 7 && questState.getPlayer().getSponsor() > 0 && (player = World.getPlayer((int)(n = questState.getPlayer().getSponsor()))) != null && player.isOnline() && questState.getPlayer().getDistance((GameObject)player) <= 1500.0) {
            questState.rollAndGive(8550, 1, 100.0);
            if (questState.getQuestItemsCount(8550) >= 8L) {
                questState.playSound("ItemSound.quest_middle");
                questState.setCond(8);
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
