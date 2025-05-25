/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 */
package bosses;

import bosses.FrintezzaManager;
import handler.items.SimpleItemHandler;
import java.util.Collections;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;

private class FrintezzaManager.FrintezzaItemHandler
extends SimpleItemHandler {
    private FrintezzaManager.FrintezzaItemHandler() {
    }

    @Override
    protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
        int n = itemInstance.getItemId();
        if (!SimpleItemHandler.useItem(player, itemInstance, 1L)) {
            return false;
        }
        switch (n) {
            case 8192: {
                NpcInstance npcInstance;
                if (player.getTarget() == null || !player.getTarget().isNpc() || player.getTarget() != FrintezzaManager.getInstance().h || (npcInstance = (NpcInstance)player.getTarget()).getNpcId() != 29045) break;
                player.callSkill(FrintezzaManager.this.n, Collections.singletonList(FrintezzaManager.getInstance().h), false);
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)FrintezzaManager.getInstance().h, 2234, 1, 1000, 0L)});
                FrintezzaManager.getInstance().u();
                break;
            }
            case 8556: {
                NpcInstance npcInstance;
                if (player.getTarget() == null || !player.getTarget().isNpc() || (npcInstance = (NpcInstance)player.getTarget()).getNpcId() != 29048 && npcInstance.getNpcId() != 29049) break;
                npcInstance.doDie((Creature)player);
            }
        }
        return false;
    }

    public int[] getItemIds() {
        return new int[]{8556, 8192};
    }
}
