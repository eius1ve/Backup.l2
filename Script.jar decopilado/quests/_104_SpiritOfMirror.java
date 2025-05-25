/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.ItemFunctions
 */
package quests;

import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.ItemFunctions;

public class _104_SpiritOfMirror
extends Quest
implements ScriptFile {
    private static final int Pb = 30017;
    private static final int Pc = 30041;
    private static final int Pd = 30043;
    private static final int Pe = 30045;
    private static final int Pf = 27003;
    private static final int Pg = 27004;
    private static final int Ph = 27005;
    private static final int Pi = 1135;
    private static final int Pj = 1136;
    private static final int Pk = 1137;
    private static final int Pl = 5790;
    private static final int Pm = 1060;
    private static final int Pn = 4414;
    private static final int Po = 4415;
    private static final int Pp = 4416;
    private static final int Pq = 4413;
    private static final int Pr = 4412;
    private static final int Ps = 748;
    private static final int Pt = 747;

    public _104_SpiritOfMirror() {
        super(0);
        this.addStartNpc(30017);
        this.addTalkId(new int[]{30041, 30043, 30045});
        this.addKillId(new int[]{27003, 27004, 27005});
        this.addQuestItem(new int[]{1135, 1136, 1137});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        int n = npcInstance.getNpcId();
        if (n == 30017 && string.equalsIgnoreCase("quest_accept")) {
            questState.setCond(1);
            questState.setState(2);
            questState.playSound("ItemSound.quest_accept");
            questState.giveItems(748, 3L);
            string2 = "gallin_q0104_03.htm";
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "no-quest";
        Player player = questState.getPlayer();
        if (player == null) {
            return null;
        }
        int n = questState.getPlayer().getLevel();
        boolean bl = questState.getPlayer().getClassId().isMage();
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        int n4 = questState.getInt("spirit_of_mirror_ex");
        switch (n3) {
            case 1: {
                if (n2 != 30017) break;
                if (questState.getPlayer().getRace() != Race.human) {
                    string = "gallin_q0104_00.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                if (n < 10) {
                    string = "gallin_q0104_06.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "gallin_q0104_02.htm";
                break;
            }
            case 2: {
                if (n2 == 30017) {
                    if (questState.getQuestItemsCount(748) >= 1L && (questState.getQuestItemsCount(1135) == 0L || questState.getQuestItemsCount(1136) == 0L || questState.getQuestItemsCount(1137) == 0L)) {
                        string = "gallin_q0104_04.htm";
                        break;
                    }
                    if (questState.getQuestItemsCount(1135) != 1L || questState.getQuestItemsCount(1136) != 1L || questState.getQuestItemsCount(1137) != 1L) break;
                    questState.giveItems(1060, 100L);
                    questState.giveItems(4414, 10L);
                    questState.giveItems(4415, 10L);
                    questState.giveItems(4416, 10L);
                    questState.giveItems(4413, 10L);
                    questState.giveItems(4412, 10L);
                    if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("p1q3")) {
                        questState.getPlayer().setVar("p1q3", "1", -1L);
                        questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Now go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                        if (bl) {
                            questState.playTutorialVoice("tutorial_voice_027");
                            questState.giveItems(5790, 3000L);
                            questState.giveItems(2509, 500L);
                        } else {
                            questState.playTutorialVoice("tutorial_voice_026");
                            questState.giveItems(5789, 7000L);
                            questState.giveItems(1835, 1000L);
                        }
                    }
                    this.giveExtraReward(questState.getPlayer());
                    questState.takeItems(1135, 1L);
                    questState.takeItems(1136, 1L);
                    questState.takeItems(1137, 1L);
                    questState.giveItems(747, 1L);
                    questState.unset("spirit_of_mirror_ex");
                    string = "gallin_q0104_05.htm";
                    questState.getPlayer().sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                    questState.exitCurrentQuest(false);
                    questState.playSound("ItemSound.quest_finish");
                    break;
                }
                if (n2 == 30043) {
                    string = "johnson_q0104_01.htm";
                    if (questState.getInt("johnson_talk") == 0) {
                        questState.set("spirit_of_mirror_ex", String.valueOf(n4 + 1), true);
                        questState.set("johnson_talk", 1);
                    }
                    if (n4 != 110) break;
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n2 == 30045) {
                    string = "ken_q0104_01.htm";
                    if (questState.getInt("ken_talk") == 0) {
                        questState.set("spirit_of_mirror_ex", String.valueOf(n4 + 10), true);
                        questState.set("ken_talk", 1);
                    }
                    if (n4 != 101) break;
                    questState.setCond(2);
                    questState.playSound("ItemSound.quest_middle");
                    break;
                }
                if (n2 != 30041) break;
                string = "arnold_q0104_01.htm";
                if (questState.getInt("arnold_talk") == 0) {
                    questState.set("spirit_of_mirror_ex", String.valueOf(n4 + 100), true);
                    questState.set("arnold_talk", 1);
                }
                if (n4 != 11) break;
                questState.setCond(2);
                questState.playSound("ItemSound.quest_middle");
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        int n = npcInstance.getNpcId();
        if (n == 27003) {
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 748 && questState.getQuestItemsCount(1135) == 0L && questState.getQuestItemsCount(748) > 0L) {
                if (questState.getQuestItemsCount(1135) + questState.getQuestItemsCount(1136) + questState.getQuestItemsCount(1137) >= 2L) {
                    questState.setCond(3);
                }
                questState.takeItems(748, 1L);
                questState.giveItems(1135, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 27004) {
            if (questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 748 && questState.getQuestItemsCount(1136) == 0L && questState.getQuestItemsCount(748) > 0L) {
                if (questState.getQuestItemsCount(1135) + questState.getQuestItemsCount(1136) + questState.getQuestItemsCount(1137) >= 2L) {
                    questState.setCond(3);
                }
                questState.takeItems(748, 1L);
                questState.giveItems(1136, 1L);
                questState.playSound("ItemSound.quest_middle");
            }
        } else if (n == 27005 && questState.getPlayer().getActiveWeaponInstance() != null && questState.getPlayer().getActiveWeaponInstance().getItemId() == 748 && questState.getQuestItemsCount(1137) == 0L && questState.getQuestItemsCount(748) > 0L) {
            if (questState.getQuestItemsCount(1135) + questState.getQuestItemsCount(1136) + questState.getQuestItemsCount(1137) >= 2L) {
                questState.setCond(3);
            }
            questState.takeItems(748, 1L);
            questState.giveItems(1137, 1L);
            questState.playSound("ItemSound.quest_middle");
        }
        return null;
    }

    public void onAbort(QuestState questState) {
        long l = ItemFunctions.getItemCount((Playable)questState.getPlayer(), (int)748);
        ItemFunctions.removeItem((Playable)questState.getPlayer(), (int)748, (long)l, (boolean)true);
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
