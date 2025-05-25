/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.WarehouseInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.WarehouseInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;

public class ArenaManagerInstance
extends WarehouseInstance {
    private static final int Hi = 4380;

    public ArenaManagerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!ArenaManagerInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (!player.isInPeaceZone() || player.isCursedWeaponEquipped()) {
            return;
        }
        if (string.startsWith("CPRecovery")) {
            if (Functions.getItemCount((Playable)player, (int)57) >= 100L) {
                Functions.removeItem((Playable)player, (int)57, (long)100L);
                this.doCast(SkillTable.getInstance().getInfo(4380, 1), (Creature)player, true);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
