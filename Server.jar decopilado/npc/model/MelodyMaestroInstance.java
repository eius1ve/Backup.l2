/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 */
package npc.model;

import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;

public class MelodyMaestroInstance
extends NpcInstance {
    public MelodyMaestroInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!MelodyMaestroInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equalsIgnoreCase("reply_1")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4410) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4411, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_01.htm" : "quests/_362_BardsMandolin/muzyk_q0362_01.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4410) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_02.htm" : "quests/_362_BardsMandolin/muzyk_q0362_02.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4410) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_03.htm" : "quests/_362_BardsMandolin/muzyk_q0362_03.htm", new Object[0]);
            }
        }
        if (string.equalsIgnoreCase("reply_2")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4409) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4412, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_04.htm" : "quests/_362_BardsMandolin/muzyk_q0362_04.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4409) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_05.htm" : "quests/_362_BardsMandolin/muzyk_q0362_05.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4409) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_06.htm" : "quests/_362_BardsMandolin/muzyk_q0362_06.htm", new Object[0]);
            }
        }
        if (string.equalsIgnoreCase("reply_3")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4408) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4413, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_07.htm" : "quests/_362_BardsMandolin/muzyk_q0362_07.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4408) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_08.htm" : "quests/_362_BardsMandolin/muzyk_q0362_08.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4408) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_09.htm" : "quests/_362_BardsMandolin/muzyk_q0362_09.htm", new Object[0]);
            }
        }
        if (string.equalsIgnoreCase("reply_4")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4420) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4414, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_10.htm" : "quests/_362_BardsMandolin/muzyk_q0362_10.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4420) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_11.htm" : "quests/_362_BardsMandolin/muzyk_q0362_11.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4420) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_12.htm" : "quests/_362_BardsMandolin/muzyk_q0362_12.htm", new Object[0]);
            }
        }
        if (string.equalsIgnoreCase("reply_5")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4421) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4415, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_13.htm" : "quests/_362_BardsMandolin/muzyk_q0362_13.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4421) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_14.htm" : "quests/_362_BardsMandolin/muzyk_q0362_14.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4421) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_15.htm" : "quests/_362_BardsMandolin/muzyk_q0362_15.htm", new Object[0]);
            }
        }
        if (string.equalsIgnoreCase("reply_6")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4419) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4417, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_16.htm" : "quests/_362_BardsMandolin/muzyk_q0362_16.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4419) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_05.htm" : "quests/_362_BardsMandolin/muzyk_q0362_05.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4419) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_06.htm" : "quests/_362_BardsMandolin/muzyk_q0362_06.htm", new Object[0]);
            }
        }
        if (string.equalsIgnoreCase("reply_7")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4418) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) >= 200L) {
                ItemFunctions.addItem((Playable)player, (int)4416, (long)1L, (boolean)true);
                ItemFunctions.removeItem((Playable)player, (int)57, (long)200L, (boolean)true);
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_17.htm" : "quests/_362_BardsMandolin/muzyk_q0362_17.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4418) > 0L && ItemFunctions.getItemCount((Playable)player, (int)57) < 200L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_05.htm" : "quests/_362_BardsMandolin/muzyk_q0362_05.htm", new Object[0]);
            } else if (ItemFunctions.getItemCount((Playable)player, (int)4418) == 0L) {
                this.showChatWindow(player, this.getNpcId() == 31043 ? "quests/_362_BardsMandolin/muzyko_q0362_06.htm" : "quests/_362_BardsMandolin/muzyk_q0362_06.htm", new Object[0]);
            }
        }
        super.onBypassFeedback(player, string);
    }
}
