/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;

public class _610_MagicalPowerofWater2
extends Quest
implements ScriptFile {
    private static final int bqd = 31372;
    private static final int bqe = 31560;
    private static final int bqf = 7238;
    private static final int bqg = 7239;
    private static final int bqh = 25316;
    private static final int bqi = 20;
    private ScheduledFuture<?> a;

    public _610_MagicalPowerofWater2() {
        super(2);
        this.addStartNpc(31372);
        this.addTalkId(new int[]{31560});
        this.addKillId(new int[]{25316});
        this.addQuestItem(new int[]{7239});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)25316);
        int n = npcInstance.getNpcId();
        String string2 = string;
        if (n == 31372) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setState(2);
                questState.setCond(1);
                questState.playSound("ItemSound.quest_accept");
                questState.set("magicalpw2", 11);
                string2 = "shaman_asefa_q0610_0104.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(7239) >= 1L) {
                    questState.takeItems(7239, -1L);
                    questState.addExpAndSp(10000L, 0L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    questState.playSound("ItemSound.quest_finish");
                    string2 = "shaman_asefa_q0610_0301.htm";
                } else {
                    string2 = "shaman_asefa_q0610_0302.htm";
                }
            }
        } else if (n == 31560 && string.equalsIgnoreCase("reply_1")) {
            if (questState.getQuestItemsCount(7238) >= 1L) {
                if (npcInstance2 == null && this.a == null) {
                    questState.takeItems(7238, 1L);
                    string2 = "totem_of_barka_q0610_0201.htm";
                    NpcInstance npcInstance3 = questState.addSpawn(25316, 104825, -36926, -1136);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"61050", (Object[])new Object[0]);
                    questState.set("magicalpw2", 21);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    npcInstance.doDie(null);
                } else {
                    string2 = "totem_of_barka_q0610_0202.htm";
                }
            } else {
                string2 = "totem_of_barka_q0610_0203.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)25316);
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpw2");
        if (n == 31372) {
            if (n2 == 11) {
                string = "shaman_asefa_q0610_0105.htm";
            } else if (n2 >= 22) {
                string = questState.getQuestItemsCount(7239) >= 1L ? "shaman_asefa_q0610_0201.htm" : "shaman_asefa_q0610_0202.htm";
            } else if (questState.getPlayer().getLevel() < 75) {
                string = "shaman_asefa_q0610_0103.htm";
            } else if (questState.getPlayer().getLevel() >= 75) {
                string = questState.getQuestItemsCount(7238) >= 1L ? "shaman_asefa_q0610_0101.htm" : "shaman_asefa_q0610_0102.htm";
            }
        } else if (n == 31560) {
            if (n2 == 11) {
                string = "totem_of_barka_q0610_0101.htm";
            }
            if (n2 == 21) {
                if (npcInstance2 == null && this.a == null) {
                    NpcInstance npcInstance3 = questState.addSpawn(25316, 104825, -36926, -1136);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"61050", (Object[])new Object[0]);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    string = "totem_of_barka_q0610_0201.htm";
                    npcInstance.doDie(null);
                } else {
                    string = "totem_of_barka_q0610_0202.htm";
                }
            }
            if (n2 >= 22) {
                string = "totem_of_barka_q0610_0204.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpw2");
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"71551", (Object[])new Object[]{questState.getPlayer().getName()});
        if (n == 25316 && n2 >= 11 && n2 <= 21) {
            if (n2 != 21 && questState.getQuestItemsCount(7238) >= 1L) {
                questState.takeItems(7238, 1L);
            }
            questState.giveItems(7239, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(3);
            questState.set("magicalpw2", 22);
        }
        return null;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private class UnspawnTask
    implements Runnable {
        private UnspawnTask() {
        }

        @Override
        public void run() {
            NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25316);
            if (npcInstance != null) {
                npcInstance.deleteMe();
                NpcInstance npcInstance2 = _610_MagicalPowerofWater2.this.addSpawn(31560, 105452, -36775, -1050, 34000, 0, 0);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61051", (Object[])new Object[0]);
            }
            if (_610_MagicalPowerofWater2.this.a != null) {
                _610_MagicalPowerofWater2.this.a.cancel(true);
                _610_MagicalPowerofWater2.this.a = null;
            }
        }
    }
}
