/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.PetDataHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.tables.SkillTable
 */
package handler.items;

import handler.items.ScriptItemHandler;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.tables.SkillTable;

public class PetSummon
extends ScriptItemHandler {
    private static int[] H;
    private static final int _skillId = 2046;

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        player.setPetControlItem(itemInstance);
        player.getAI().Cast(SkillTable.getInstance().getInfo(2046, 1), (Creature)player, false, true);
        return true;
    }

    public final int[] getItemIds() {
        if (H == null) {
            H = PetDataHolder.getInstance().getAllControlItemIds();
        }
        return H;
    }
}
