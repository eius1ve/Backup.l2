/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.FishermanInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.FishermanInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;

public class MultiClassMasterInstance
extends FishermanInstance {
    public MultiClassMasterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        if (!MultiClassMasterInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (player.getClassId().getLevel() == 1) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this).setFile("scripts/services/multi_newbie_class.htm"));
            return;
        }
        if (player.getClassId().getLevel() == 2) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this).setFile("scripts/services/multi_first_class.htm"));
            return;
        }
        if (player.getClassId().getLevel() == 3) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this).setFile("scripts/services/multi_second_class.htm"));
            return;
        }
        if (player.getClassId().getLevel() == 4) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this).setFile("scripts/services/multi_third_class.htm"));
            return;
        }
        super.showChatWindow(player, n, new Object[0]);
    }
}
