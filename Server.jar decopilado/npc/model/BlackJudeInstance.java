/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;

public class BlackJudeInstance
extends NpcInstance {
    public BlackJudeInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!BlackJudeInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equals("tryRemovePenalty")) {
            if (player.getDeathPenalty().getLevel() > 0) {
                this.showChatWindow(player, 2, new Object[]{"%price%", this.getPrice(player)});
            } else {
                this.showChatWindow(player, 1, new Object[0]);
            }
        } else if (string.equals("removePenalty")) {
            if (player.getDeathPenalty().getLevel() > 0) {
                if (player.getAdena() >= (long)this.getPrice(player)) {
                    player.reduceAdena((long)this.getPrice(player), true);
                    this.doCast(SkillTable.getInstance().getInfo(5077, 1), (Creature)player, false);
                } else {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                }
            } else {
                this.showChatWindow(player, 1, new Object[0]);
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    public int getPrice(Player player) {
        int n = player.getLevel();
        if (n <= 19) {
            return 3600;
        }
        if (n >= 20 && n <= 39) {
            return 16400;
        }
        if (n >= 40 && n <= 51) {
            return 36200;
        }
        if (n >= 52 && n <= 60) {
            return 50400;
        }
        if (n >= 61 && n <= 75) {
            return 78200;
        }
        return 102800;
    }
}
