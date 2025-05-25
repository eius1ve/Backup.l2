/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _604_DaimontheWhiteEyedPart2
extends Quest
implements ScriptFile {
    private static final int bnv = 31683;
    private static final int bnw = 31541;
    private static final int bnx = 25290;
    private static final int bny = 7192;
    private static final int bnz = 7193;
    private static final int bnA = 7194;
    private static final int bnB = 4595;
    private static final int bnC = 4596;
    private static final int bnD = 4597;
    private static final int bnE = 4598;
    private static final int bnF = 4599;
    private static final int bnG = 4600;
    private ScheduledFuture<?> a;
    private static final int bnH = 20;

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _604_DaimontheWhiteEyedPart2() {
        super(1);
        this.addStartNpc(31683);
        this.addTalkId(new int[]{31541});
        this.addKillId(new int[]{25290});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)25290);
        int n = questState.getInt("daemon_of_hundred_eyes_second_cookie");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31683) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("daemon_of_hundred_eyes_second", String.valueOf(11), true);
                questState.takeItems(7192, -1L);
                questState.giveItems(7193, 1L);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "eye_of_argos_q0604_0104.htm";
            } else if (string.equalsIgnoreCase("reply_3") && n == 2) {
                if (questState.getQuestItemsCount(7194) >= 1L) {
                    int n3 = Rnd.get((int)1000);
                    questState.takeItems(7194, 1L);
                    if (n3 < 167) {
                        questState.giveItems(4595, 5L);
                    } else if (n3 < 334) {
                        questState.giveItems(4596, 5L);
                    } else if (n3 < 501) {
                        questState.giveItems(4597, 5L);
                    } else if (n3 < 668) {
                        questState.giveItems(4598, 5L);
                    } else if (n3 < 835) {
                        questState.giveItems(4599, 5L);
                    } else if (n3 < 1000) {
                        questState.giveItems(4600, 5L);
                    }
                    questState.unset("daemon_of_hundred_eyes_second");
                    questState.unset("daemon_of_hundred_eyes_second_cookie");
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    string2 = "eye_of_argos_q0604_0301.htm";
                } else {
                    string2 = "eye_of_argos_q0604_0302.htm";
                }
            }
        } else if (n2 == 31541 && string.equalsIgnoreCase("reply_1") && n == 1) {
            if (questState.getQuestItemsCount(7193) >= 1L) {
                if (npcInstance2 == null) {
                    questState.setCond(2);
                    questState.set("daemon_of_hundred_eyes_second", String.valueOf(21), true);
                    questState.takeItems(7193, 1L);
                    NpcInstance npcInstance3 = questState.addSpawn(25290, 186320, -43904, -3175);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"60403", (Object[])new Object[0]);
                    questState.playSound("ItemSound.quest_middle");
                    string2 = "daimons_altar_q0604_0201.htm";
                    npcInstance.doDie(null);
                } else {
                    string2 = "daimons_altar_q0604_0202.htm";
                }
            } else {
                string2 = "daimons_altar_q0604_0203.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getInt("daemon_of_hundred_eyes_second");
        NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)25290);
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                if (n2 != 31683) break;
                if (questState.getPlayer().getLevel() >= 73) {
                    if (questState.getQuestItemsCount(7192) >= 1L) {
                        string = "eye_of_argos_q0604_0101.htm";
                        break;
                    }
                    string = "eye_of_argos_q0604_0102.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "eye_of_argos_q0604_0103.htm";
                questState.exitCurrentQuest(true);
                break;
            }
            case 2: {
                if (n2 == 31683) {
                    if (n == 11) {
                        string = "eye_of_argos_q0604_0105.htm";
                        break;
                    }
                    if (n < 22) break;
                    if (questState.getQuestItemsCount(7194) >= 1L) {
                        questState.set("daemon_of_hundred_eyes_second_cookie", String.valueOf(2), true);
                        string = "eye_of_argos_q0604_0201.htm";
                        break;
                    }
                    string = "eye_of_argos_q0604_0202.htm";
                    break;
                }
                if (n2 != 31541) break;
                if (questState.getQuestItemsCount(7193) >= 1L && n == 11) {
                    questState.set("daemon_of_hundred_eyes_second_cookie", String.valueOf(1), true);
                    string = "daimons_altar_q0604_0101.htm";
                    break;
                }
                if (n == 21) {
                    if (npcInstance2 == null) {
                        NpcInstance npcInstance3 = questState.addSpawn(25290, 186320, -43904, -3175);
                        this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"60403", (Object[])new Object[0]);
                        string = "daimons_altar_q0604_0201.htm";
                        npcInstance.doDie(null);
                        break;
                    }
                    string = "daimons_altar_q0604_0202.htm";
                    break;
                }
                if (n < 22) break;
                string = "daimons_altar_q0604_0204.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n;
        int n2 = questState.getInt("daemon_of_hundred_eyes_second");
        int n3 = npcInstance.getNpcId();
        if (n2 >= 11 && n2 <= 21 && n3 == 25290 && (n = Rnd.get((int)1000)) < 1000) {
            if (questState.getQuestItemsCount(7194) + 1L >= 1L) {
                questState.setCond(3);
                questState.set("daemon_of_hundred_eyes_second", String.valueOf(22), true);
                questState.giveItems(7194, 1L - questState.getQuestItemsCount(7194));
                questState.playSound("ItemSound.quest_middle");
            } else {
                questState.giveItems(7194, 1L);
                questState.playSound("ItemSound.quest_itemget");
            }
        }
        return null;
    }

    private class UnspawnTask
    implements Runnable {
        private UnspawnTask() {
        }

        @Override
        public void run() {
            NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25290);
            if (npcInstance != null) {
                npcInstance.deleteMe();
                NpcInstance npcInstance2 = _604_DaimontheWhiteEyedPart2.this.addSpawn(31541, 186304, -43744, -3193, 57000, 0, 0);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"60404", (Object[])new Object[0]);
            }
            if (_604_DaimontheWhiteEyedPart2.this.a != null) {
                _604_DaimontheWhiteEyedPart2.this.a.cancel(true);
                _604_DaimontheWhiteEyedPart2.this.a = null;
            }
        }
    }
}
