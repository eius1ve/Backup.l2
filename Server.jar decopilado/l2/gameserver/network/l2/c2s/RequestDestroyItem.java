/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.dao.PetDAO;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class RequestDestroyItem
extends L2GameClientPacket {
    private int fW;
    private long aT;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.aT = this.readQ();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
            return;
        }
        if (player.isInTrade()) {
            player.sendActionFailed();
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
            return;
        }
        long l = this.aT;
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.fW);
        if (itemInstance == null) {
            player.sendActionFailed();
            return;
        }
        if (l < 1L) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DESTROY_IT_BECAUSE_THE_NUMBER_IS_INCORRECT);
            return;
        }
        if (player.getPet() != null && player.getPet().getControlItemObjId() == itemInstance.getObjectId()) {
            player.sendPacket((IStaticPacket)SystemMsg.AS_YOUR_PET_IS_CURRENTLY_OUT_ITS_SUMMONING_ITEM_CANNOT_BE_DESTROYED);
            return;
        }
        if (!player.isGM() && !itemInstance.canBeDestroyed(player)) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_ITEM_CANNOT_BE_DISCARDED);
            return;
        }
        if (this.aT > itemInstance.getCount()) {
            l = itemInstance.getCount();
        }
        boolean bl = itemInstance.canBeCrystallized(player);
        int n = itemInstance.getTemplate().getCrystalItemId();
        int n2 = itemInstance.getTemplate().getCrystalCount();
        if (bl) {
            if (Config.DWARF_AUTOMATICALLY_CRYSTALLIZE_ON_ITEM_DELETE) {
                int n3 = player.getSkillLevel(248);
                if (n3 < 1 || itemInstance.getTemplate().getCrystalType().gradeOrd() > n3) {
                    bl = false;
                }
            } else {
                bl = false;
            }
        }
        Log.LogItem(player, Log.ItemLog.Delete, itemInstance, l);
        if (!player.getInventory().destroyItemByObjectId(this.fW, l)) {
            player.sendActionFailed();
            return;
        }
        if (PetDataHolder.getInstance().getByControlItemId(itemInstance) != null) {
            PetDAO.deletePet(itemInstance, player);
        }
        if (bl) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_ITEM_HAS_BEEN_SUCCESSFULLY_CRYSTALLIZED);
            if (n > 0) {
                ItemFunctions.addItem((Playable)player, n, (long)n2, true);
            }
        } else {
            player.sendPacket((IStaticPacket)SystemMessage.removeItems(itemInstance.getItemId(), l));
        }
        player.sendChanges();
    }
}
