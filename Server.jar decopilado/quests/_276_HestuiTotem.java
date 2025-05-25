/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.ItemHolder
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

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;

public class _276_HestuiTotem
extends Quest
implements ScriptFile {
    private static int auY = 30571;
    private static int auZ = 20479;
    private static int ava = 27044;
    private static int avb = 29;
    private static int avc = 1500;
    private static int avd = 1480;
    private static int ave = 1481;

    public _276_HestuiTotem() {
        super(0);
        this.addStartNpc(auY);
        this.addKillId(new int[]{auZ});
        this.addKillId(new int[]{ava});
        this.addQuestItem(new int[]{avd});
        this.addQuestItem(new int[]{ave});
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        if (string.equalsIgnoreCase("seer_tanapi_q0276_03.htm") && questState.getState() == 1 && questState.getPlayer().getRace() == Race.orc && questState.getPlayer().getLevel() >= 15) {
            questState.setState(2);
            questState.setCond(1);
            questState.playSound("ItemSound.quest_accept");
        }
        return string;
    }

    public String onTalk(NpcInstance npcInstance, QuestState questState) {
        String string = "noquest";
        if (npcInstance.getNpcId() != auY) {
            return string;
        }
        int n = questState.getState();
        if (n == 1) {
            if (questState.getPlayer().getRace() != Race.orc) {
                string = "seer_tanapi_q0276_00.htm";
                questState.exitCurrentQuest(true);
            } else if (questState.getPlayer().getLevel() < 15) {
                string = "seer_tanapi_q0276_01.htm";
                questState.exitCurrentQuest(true);
            } else {
                string = "seer_tanapi_q0276_02.htm";
                questState.setCond(0);
            }
        } else if (n == 2) {
            if (questState.getQuestItemsCount(ave) > 0L) {
                string = "seer_tanapi_q0276_05.htm";
                questState.takeItems(avd, -1L);
                questState.takeItems(ave, -1L);
                questState.giveItems(avb, 1L);
                questState.giveItems(avc, 1L);
                if (questState.getRateQuestsReward() > 1.0) {
                    questState.giveItems(57, Math.round((double)ItemHolder.getInstance().getTemplate(avc).getReferencePrice() * (questState.getRateQuestsAdenaReward() - 1.0) / 2.0), false);
                }
                if (questState.getPlayer().getClassId().getLevel() == 1 && !questState.getPlayer().getVarB("p1q4")) {
                    questState.getPlayer().setVar("p1q4", "1", -1L);
                    questState.getPlayer().sendPacket((IStaticPacket)new ExShowScreenMessage("Now go find the Newbie Guide.", 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
                }
                questState.playSound("ItemSound.quest_finish");
                this.giveExtraReward(questState.getPlayer());
                questState.exitCurrentQuest(true);
            } else {
                string = "seer_tanapi_q0276_04.htm";
            }
        }
        return string;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        if (questState.getState() != 2) {
            return null;
        }
        int n = npcInstance.getNpcId();
        if (n == auZ && questState.getQuestItemsCount(ave) == 0L) {
            if (questState.getQuestItemsCount(avd) < 50L) {
                questState.giveItems(avd, 1L);
                questState.playSound("ItemSound.quest_itemget");
            } else {
                questState.takeItems(avd, -1L);
                questState.addSpawn(ava);
            }
        } else if (n == ava && questState.getQuestItemsCount(ave) == 0L) {
            questState.giveItems(ave, 1L);
            questState.playSound("ItemSound.quest_middle");
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
