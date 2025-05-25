/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.Location;

public class RequestDropItem
extends L2GameClientPacket {
    private int fW;
    private long aT;
    private Location _loc;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.aT = this.readQ();
        this._loc = new Location(this.readD(), this.readD(), this.readD());
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.aT < 1L || this._loc.isNull()) {
            player.sendActionFailed();
            return;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (!Config.ALLOW_DISCARDITEM || player.getPlayerAccess().BlockInventory) {
            player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestDropItem.Disallowed", player, new Object[0]));
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
            return;
        }
        if (player.isSitting() || player.isDropDisabled()) {
            player.sendActionFailed();
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
        if (player.isActionBlocked("drop_item")) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DISCARD_THOSE_ITEMS_HERE);
            return;
        }
        if (!player.isInRangeSq(this._loc, 22500L) || Math.abs(this._loc.z - player.getZ()) > 50) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DISCARD_SOMETHING_THAT_FAR_AWAY_FROM_YOU);
            return;
        }
        if (player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.fW);
        if (itemInstance == null) {
            player.sendActionFailed();
            return;
        }
        if (!itemInstance.canBeDropped(player, false)) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_ITEM_CANNOT_BE_DISCARDED);
            return;
        }
        itemInstance.getTemplate().getHandler().dropItem(player, itemInstance, this.aT, this._loc);
    }
}
