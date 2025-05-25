/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestAutoSoulShot
extends L2GameClientPacket {
    private static final Logger cM = LoggerFactory.getLogger(RequestAutoSoulShot.class);
    private int _itemId;
    private boolean dR;
    private int type;

    @Override
    protected void readImpl() {
        this._itemId = this.readD();
        this.dR = this.readD() == 1;
        this.type = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.getPrivateStoreType() != 0 || player.isDead()) {
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(this._itemId);
        if (itemInstance == null) {
            return;
        }
        if (!itemInstance.getTemplate().isShotItem()) {
            return;
        }
        if (!itemInstance.getTemplate().testCondition(player, itemInstance, false)) {
            String string = "Player " + player.getName() + " trying illegal item use, ban this player!";
            Log.add(string, "illegal-actions");
            cM.warn(string);
            return;
        }
        if (this.dR) {
            player.addAutoSoulShot(this._itemId);
            player.sendPacket((IStaticPacket)new ExAutoSoulShot(this._itemId, true, this.type));
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_AUTOMATIC_USE_OF_S1_HAS_BEEN_ACTIVATED).addString(itemInstance.getName()));
            IItemHandler iItemHandler = itemInstance.getTemplate().getHandler();
            iItemHandler.useItem(player, itemInstance, false);
        } else {
            player.removeAutoSoulShot(this._itemId);
            player.sendPacket((IStaticPacket)new ExAutoSoulShot(this._itemId, false, this.type));
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_AUTOMATIC_USE_OF_S1_HAS_BEEN_DEACTIVATED).addItemName(this._itemId));
        }
    }
}
