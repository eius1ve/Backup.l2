/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ReflectionUtils;

public final class BatracosInstance
extends NpcInstance {
    private static final int Ho = 505;

    public BatracosInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        if (n == 0) {
            String string = null;
            string = this.getReflection().isDefault() ? "default/32740.htm" : "default/32740-4.htm";
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, string, n));
        } else {
            super.showChatWindow(player, n, new Object[0]);
        }
    }

    public void onBypassFeedback(Player player, String string) {
        if (!BatracosInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equalsIgnoreCase("request_seer")) {
            Reflection reflection = player.getActiveReflection();
            if (reflection != null) {
                if (player.canReenterInstance(505)) {
                    player.teleToLocation(reflection.getTeleportLoc(), reflection);
                }
            } else if (player.canEnterInstance(505)) {
                ReflectionUtils.enterReflection((Player)player, (int)505);
            }
        } else if (string.equalsIgnoreCase("leave")) {
            if (!this.getReflection().isDefault()) {
                this.getReflection().collapse();
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
