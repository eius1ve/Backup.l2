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

public class _616_MagicalPowerofFire2
extends Quest
implements ScriptFile {
    private static final int bsA = 31379;
    private static final int bsB = 31558;
    private static final int bsC = 7243;
    private static final int bsD = 7244;
    private static final int bsE = 25306;
    private static final int bsF = 20;
    private ScheduledFuture<?> a;

    public _616_MagicalPowerofFire2() {
        super(2);
        this.addStartNpc(31379);
        this.addTalkId(new int[]{31558});
        this.addKillId(new int[]{25306});
        this.addQuestItem(new int[]{7244});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)25306);
        int n = npcInstance.getNpcId();
        String string2 = string;
        if (n == 31379) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setState(2);
                questState.setCond(1);
                questState.playSound("ItemSound.quest_accept");
                questState.set("magicalpf2", 11);
                string2 = "shaman_udan_q0616_0104.htm";
            } else if (string.equalsIgnoreCase("reply_3")) {
                if (questState.getQuestItemsCount(7244) >= 1L) {
                    questState.takeItems(7244, -1L);
                    questState.addExpAndSp(10000L, 0L);
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(true);
                    questState.playSound("ItemSound.quest_finish");
                    string2 = "shaman_udan_q0616_0301.htm";
                } else {
                    string2 = "shaman_udan_q0616_0302.htm";
                }
            }
        } else if (n == 31558 && string.equalsIgnoreCase("reply_1")) {
            if (questState.getQuestItemsCount(7243) >= 1L) {
                if (npcInstance2 == null) {
                    questState.takeItems(7243, 1L);
                    string2 = "totem_of_ketra_q0616_0201.htm";
                    NpcInstance npcInstance3 = questState.addSpawn(25306, 142528, -82528, -6496);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"61650", (Object[])new Object[0]);
                    questState.set("magicalpf2", 21);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    npcInstance.doDie(null);
                } else {
                    string2 = "totem_of_ketra_q0616_0202.htm";
                }
            } else {
                string2 = "totem_of_ketra_q0616_0203.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)25306);
        String string = "no-quest";
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpf2");
        if (n == 31379) {
            if (n2 == 11) {
                string = "shaman_udan_q0616_0105.htm";
            } else if (n2 >= 22) {
                string = questState.getQuestItemsCount(7244) >= 1L ? "shaman_udan_q0616_0201.htm" : "shaman_udan_q0616_0202.htm";
            } else if (questState.getPlayer().getLevel() < 75) {
                string = "shaman_udan_q0616_0103.htm";
            } else if (questState.getPlayer().getLevel() >= 75) {
                string = questState.getQuestItemsCount(7243) >= 1L ? "shaman_udan_q0616_0101.htm" : "shaman_udan_q0616_0102.htm";
            }
        } else if (n == 31558) {
            if (n2 == 11) {
                string = "totem_of_ketra_q0616_0101.htm";
            }
            if (n2 == 21) {
                if (npcInstance2 == null) {
                    npcInstance.doDie(null);
                    NpcInstance npcInstance3 = questState.addSpawn(25306, 142528, -82528, -6496);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance3, (String)"61650", (Object[])new Object[0]);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    string = "totem_of_ketra_q0616_0201.htm";
                } else {
                    string = "totem_of_ketra_q0616_0202.htm";
                }
            }
            if (n2 >= 22) {
                string = "totem_of_ketra_q0616_0204.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("magicalpf2");
        if (n == 25306 && n2 >= 11 && n2 <= 21) {
            if (n2 != 21 && questState.getQuestItemsCount(7243) >= 1L) {
                questState.takeItems(7243, 1L);
            }
            questState.giveItems(7244, 1L);
            questState.playSound("ItemSound.quest_middle");
            questState.setCond(3);
            questState.set("magicalpf2", 22);
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
            NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25306);
            if (npcInstance != null) {
                npcInstance.deleteMe();
                NpcInstance npcInstance2 = _616_MagicalPowerofFire2.this.addSpawn(31558, 142368, -82512, -6487, 58000, 0, 0);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61651", (Object[])new Object[0]);
            }
            if (_616_MagicalPowerofFire2.this.a != null) {
                _616_MagicalPowerofFire2.this.a.cancel(true);
                _616_MagicalPowerofFire2.this.a = null;
            }
        }
    }
}
