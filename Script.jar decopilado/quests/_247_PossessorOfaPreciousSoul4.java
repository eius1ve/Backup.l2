/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 */
package quests;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import quests._246_PossessorOfaPreciousSoul3;

public class _247_PossessorOfaPreciousSoul4
extends Quest
implements ScriptFile {
    private static final int ass = 31740;
    private static final int ast = 31745;
    private static final int asu = 7679;
    private static final int asv = 7694;

    public _247_PossessorOfaPreciousSoul4() {
        super(0);
        this.addStartNpc(31740);
        this.addTalkId(new int[]{31745});
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        String string2 = string;
        Player player = questState.getPlayer();
        int n = questState.getInt("noble_soul_noblesse_4");
        int n2 = npcInstance.getNpcId();
        if (n2 == 31740) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("noble_soul_noblesse_4", String.valueOf(1), true);
                questState.takeItems(7679, 1L);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                string2 = "caradine_q0247_03.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=247&reply=1")) {
                questState.setCond(2);
                questState.set("noble_soul_noblesse_4", String.valueOf(2), true);
                questState.getPlayer().teleToLocation(143180, 43930, -3024);
                questState.playSound("ItemSound.quest_middle");
                string2 = "caradine_q0247_05.htm";
            }
        } else if (n2 == 31745) {
            if (string.equalsIgnoreCase("menu_select?ask=247&reply=1") && n == 2) {
                string2 = "lady_of_the_lake_q0247_02.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=247&reply=2") && n == 2) {
                string2 = "lady_of_the_lake_q0247_03.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=247&reply=3") && n == 2) {
                string2 = "lady_of_the_lake_q0247_04.htm";
            } else if (string.equalsIgnoreCase("menu_select?ask=247&reply=4") && n == 2) {
                if (questState.getPlayer().getLevel() >= 75) {
                    questState.giveItems(7694, 1L);
                    questState.playSound("ItemSound.quest_finish");
                    this.giveExtraReward(questState.getPlayer());
                    questState.exitCurrentQuest(false);
                    string2 = "lady_of_the_lake_q0247_05.htm";
                    player.sendPacket((IStaticPacket)new SocialAction(questState.getPlayer().getObjectId(), 3));
                    npcInstance.doCast(SkillTable.getInstance().getInfo(4339, 1), (Creature)questState.getPlayer(), true);
                    NoblesController.getInstance().addNoble(questState.getPlayer());
                    questState.getPlayer().setNoble(true);
                    questState.getPlayer().updatePledgeClass();
                    questState.getPlayer().updateNobleSkills();
                    questState.getPlayer().sendSkillList();
                    questState.getPlayer().broadcastUserInfo(false, new UserInfoType[0]);
                } else {
                    string2 = "lady_of_the_lake_q0247_06.htm";
                }
            }
        }
        return string2;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        if (!questState.getPlayer().isSubClassActive()) {
            return "quest_not_subclass001.htm";
        }
        String string = "no-quest";
        int n = questState.getInt("noble_soul_noblesse_4");
        int n2 = npcInstance.getNpcId();
        int n3 = questState.getState();
        switch (n3) {
            case 1: {
                QuestState questState2 = questState.getPlayer().getQuestState(_246_PossessorOfaPreciousSoul3.class);
                if (n2 != 31740 || questState2 == null || questState2.getState() != 3) break;
                if (questState.getPlayer().getLevel() < 75) {
                    string = "caradine_q0247_02.htm";
                    questState.exitCurrentQuest(true);
                    break;
                }
                string = "caradine_q0247_01.htm";
                break;
            }
            case 2: {
                if (n2 == 31740) {
                    if (n == 1) {
                        string = "caradine_q0247_04.htm";
                        break;
                    }
                    if (n != 2) break;
                    string = "caradine_q0247_06.htm";
                    break;
                }
                if (n2 != 31745 || n != 2) break;
                string = "lady_of_the_lake_q0247_01.htm";
            }
        }
        return string;
    }
}
