/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;
import quests._111_ElrokianHuntersProof;

public class AsamahInstance
extends NpcInstance {
    private static final int Hj = 8763;
    private static final int Hk = 8764;

    public AsamahInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!AsamahInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equals("buyTrap")) {
            String string2 = null;
            QuestState questState = player.getQuestState(_111_ElrokianHuntersProof.class);
            if (player.getLevel() >= 75 && questState != null && questState.isCompleted() && Functions.getItemCount((Playable)player, (int)57) > 1000000L) {
                if (Functions.getItemCount((Playable)player, (int)8763) > 0L) {
                    string2 = this.getNpcId() + "-alreadyhave.htm";
                } else {
                    Functions.removeItem((Playable)player, (int)57, (long)1000000L);
                    Functions.addItem((Playable)player, (int)8763, (long)1L);
                    string2 = this.getNpcId() + "-given.htm";
                }
            } else {
                string2 = this.getNpcId() + "-cant.htm";
            }
            this.showChatWindow(player, "default/" + string2, new Object[0]);
        } else if (string.equals("buyStones")) {
            String string3 = null;
            QuestState questState = player.getQuestState(_111_ElrokianHuntersProof.class);
            if (player.getLevel() >= 75 && questState != null && questState.isCompleted() && Functions.getItemCount((Playable)player, (int)57) > 1000000L) {
                Functions.removeItem((Playable)player, (int)57, (long)1000000L);
                Functions.addItem((Playable)player, (int)8764, (long)100L);
                string3 = this.getNpcId() + "-given.htm";
            } else {
                string3 = this.getNpcId() + "-cant.htm";
            }
            this.showChatWindow(player, "default/" + string3, new Object[0]);
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
