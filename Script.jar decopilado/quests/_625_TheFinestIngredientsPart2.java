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

public class _625_TheFinestIngredientsPart2
extends Quest
implements ScriptFile {
    private static final int bwb = 31521;
    private static final int bwc = 31542;
    private static final int bwd = 25296;
    private ScheduledFuture<?> a;
    private static final int bwe = 20;
    private static final int bwf = 7205;
    private static final int bwg = 7209;
    private static final int bwh = 7210;
    private static final int bwi = 4589;
    private static final int bwj = 4590;
    private static final int bwk = 4591;
    private static final int bwl = 4592;
    private static final int bwm = 4593;
    private static final int bwn = 4594;

    public _625_TheFinestIngredientsPart2() {
        super(1);
        this.addStartNpc(31521);
        this.addTalkId(new int[]{31542});
        this.addKillId(new int[]{25296});
        this.addQuestItem(new int[]{7209, 7210});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        if (string.equalsIgnoreCase("quest_accept")) {
            questState.takeItems(7205, 1L);
            questState.setCond(1);
            questState.setState(2);
            questState.set("finest_ingredients_part2", String.valueOf(11), true);
            questState.playSound("ItemSound.quest_accept");
            string2 = "jeremy_q0625_0104.htm";
            questState.giveItems(7209, 1L);
        } else if (string.equalsIgnoreCase("reply_1")) {
            if (questState.getQuestItemsCount(7209) >= 1L) {
                if (GameObjectsStorage.getByNpcId((int)25296) == null) {
                    questState.takeItems(7209, 1L);
                    string2 = "yetis_table_q0625_0201.htm";
                    NpcInstance npcInstance2 = questState.addSpawn(25296, 158240, -121536, -2222, 1200000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"NpcString.62503", (Object[])new Object[0]);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    questState.set("finest_ingredients_part2", String.valueOf(21), true);
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    npcInstance.doDie(null);
                } else {
                    string2 = "yetis_table_q0625_0202.htm";
                }
            } else {
                string2 = "yetis_table_q0625_0203.htm";
            }
        } else if (string.equalsIgnoreCase("reply_3")) {
            if (questState.getQuestItemsCount(7210) >= 1L) {
                int n = Rnd.get((int)1000);
                questState.takeItems(7210, 1L);
                if (n < 167) {
                    questState.giveItems(4589, 5L);
                } else if (n < 334) {
                    questState.giveItems(4590, 5L);
                } else if (n < 501) {
                    questState.giveItems(4591, 5L);
                } else if (n < 668) {
                    questState.giveItems(4592, 5L);
                } else if (n < 835) {
                    questState.giveItems(4593, 5L);
                } else if (n < 1000) {
                    questState.giveItems(4594, 5L);
                }
                string2 = "jeremy_q0625_0301.htm";
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
                questState.playSound("ItemSound.quest_finish");
            } else {
                string2 = "jeremy_q0625_0302.htm";
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        int n = questState.getState();
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getInt("finest_ingredients_part2");
        switch (n) {
            case 1: {
                if (n2 != 31521) break;
                if (questState.getPlayer().getLevel() >= 73) {
                    if (questState.getQuestItemsCount(7205) >= 1L) {
                        string = "jeremy_q0625_0101.htm";
                        break;
                    }
                    string = "jeremy_q0625_0102.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "jeremy_q0625_0105.htm";
                break;
            }
            case 2: {
                if (n2 == 31521) {
                    if (n3 == 11) {
                        string = "jeremy_q0625_0105.htm";
                        break;
                    }
                    if (n3 < 22) break;
                    if (questState.getQuestItemsCount(7210) >= 1L) {
                        string = "jeremy_q0625_0201.htm";
                        break;
                    }
                    string = "jeremy_q0625_0202.htm";
                    break;
                }
                if (n2 != 31542) break;
                if (questState.getQuestItemsCount(7209) >= 1L && n3 == 11) {
                    string = "yetis_table_q0625_0101.htm";
                    break;
                }
                if (n3 == 21) {
                    if (GameObjectsStorage.getByNpcId((int)25296) != null) {
                        string = "yetis_table_q0625_0202.htm";
                        break;
                    }
                    NpcInstance npcInstance2 = questState.addSpawn(25296, 158240, -121536, -2222, 1200000);
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"NpcString.62503", (Object[])new Object[0]);
                    this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(), (long)((int)TimeUnit.MINUTES.toMillis(20L)));
                    string = "yetis_table_q0625_0201.htm";
                    npcInstance.doDie(null);
                    break;
                }
                if (n3 < 22) break;
                string = "yetis_table_q0625_0204.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        int n2 = questState.getInt("finest_ingredients_part2");
        if (n == 25296 && n2 >= 11 && n2 <= 21) {
            if (n2 == 21) {
                questState.giveItems(7210, 1L);
                questState.playSound("ItemSound.quest_itemget");
                questState.setCond(3);
                questState.set("finest_ingredients_part2", String.valueOf(22), true);
            } else if (questState.getQuestItemsCount(7209) >= 1L) {
                questState.takeItems(7209, 1L);
                questState.giveItems(7210, 1L);
                questState.playSound("ItemSound.quest_itemget");
                questState.setCond(3);
                questState.set("finest_ingredients_part2", String.valueOf(22), true);
            } else {
                questState.giveItems(7210, 1L);
                questState.playSound("ItemSound.quest_itemget");
                questState.setCond(3);
                questState.set("finest_ingredients_part2", String.valueOf(22), true);
            }
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"NpcString.62504", (Object[])new Object[0]);
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
            NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25296);
            if (npcInstance != null) {
                npcInstance.deleteMe();
                NpcInstance npcInstance2 = _625_TheFinestIngredientsPart2.this.addSpawn(31542, 157136, -121456, -2363, 40000, 0, 0);
                Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61651", (Object[])new Object[0]);
            }
            if (_625_TheFinestIngredientsPart2.this.a != null) {
                _625_TheFinestIngredientsPart2.this.a.cancel(true);
                _625_TheFinestIngredientsPart2.this.a = null;
            }
        }
    }
}
