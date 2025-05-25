/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.tables.SkillTable
 */
package handler.items;

import handler.items.SimpleItemHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;

public class Potions
extends SimpleItemHandler {
    private static final int[] P = new int[]{7906, 7907, 7908, 7909, 7910, 7911};

    public int[] getItemIds() {
        return P;
    }

    @Override
    protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
        int n = itemInstance.getItemId();
        if (player.isOlyParticipant()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(n));
            return false;
        }
        if (!Potions.useItem(player, itemInstance, 1L)) {
            return false;
        }
        switch (n) {
            case 7906: {
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2248, 1, 0, 0L)});
                player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(2248, 1));
                break;
            }
            case 7907: {
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2249, 1, 0, 0L)});
                player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(2249, 1));
                break;
            }
            case 7908: {
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2250, 1, 0, 0L)});
                player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(2250, 1));
                break;
            }
            case 7909: {
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2251, 1, 0, 0L)});
                player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(2251, 1));
                break;
            }
            case 7910: {
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2252, 1, 0, 0L)});
                player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(2252, 1));
                break;
            }
            case 7911: {
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2253, 1, 0, 0L)});
                player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(2253, 1));
                break;
            }
            default: {
                return false;
            }
        }
        return true;
    }
}
