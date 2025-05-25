/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExUseSharedGroupItem;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.TimeStamp;

public class UseItem
extends L2GameClientPacket {
    private int fW;
    private boolean dP;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.dP = this.readD() == 1;
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        long l = System.currentTimeMillis();
        player.setActive();
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.fW);
        if (itemInstance == null) {
            player.sendActionFailed();
            return;
        }
        if (!itemInstance.getTemplate().isEquipable() && l - gameClient.getLastIncomePacketTimeStamp(UseItem.class) < (long)Config.USE_ITEM_PACKET_DELAY) {
            player.sendActionFailed();
            return;
        }
        gameClient.setLastIncomePacketTimeStamp(UseItem.class, l);
        if (player.isInTrade()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_PICK_UP_OR_USE_ITEMS_WHILE_TRADING);
            return;
        }
        if (player.isSitting() && itemInstance.getTemplate().isEquipable() && Config.ALT_DELETE_SA_BUFFS) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            if (PetDataHolder.getInstance().getByControlItemId(itemInstance.getItemId()) != null) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_SUMMON_DURING_A_TRADE_OR_WHILE_USING_A_PRIVATE_STORE);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_USE_ITEMS_IN_A_PRIVATE_STORE_OR_PRIVATE_WORK_SHOP);
            }
            return;
        }
        int n = itemInstance.getItemId();
        if (player.isFishing() && (n < 6535 || n > 6540)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING_2);
            return;
        }
        if (player.isSharedGroupDisabled(itemInstance.getTemplate().getReuseGroup())) {
            player.sendReuseMessage(itemInstance);
            return;
        }
        if (!itemInstance.getTemplate().testCondition(player, itemInstance, true)) {
            return;
        }
        if (player.getInventory().isLockedItem(itemInstance)) {
            return;
        }
        if (itemInstance.getTemplate().isForPet()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_EQUIP_A_PET_ITEM);
            return;
        }
        if (Config.ALT_IMPROVED_PETS_LIMITED_USE && player.isMageClass() && itemInstance.getItemId() == 10311) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(n));
            return;
        }
        if (Config.ALT_IMPROVED_PETS_LIMITED_USE && !player.isMageClass() && itemInstance.getItemId() == 10313) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(n));
            return;
        }
        if (player.isOutOfControl() || player.isDead() || player.isStunned() || player.isSleeping() || player.isParalyzed()) {
            player.sendActionFailed();
            return;
        }
        boolean bl = itemInstance.getTemplate().getHandler().useItem(player, itemInstance, this.dP);
        if (bl) {
            long l2 = itemInstance.getTemplate().getReuseType().next(itemInstance);
            if (l2 > System.currentTimeMillis()) {
                TimeStamp timeStamp = new TimeStamp(itemInstance.getItemId(), l2, (long)itemInstance.getTemplate().getReuseDelay());
                player.addSharedGroupReuse(itemInstance.getTemplate().getReuseGroup(), timeStamp);
                if (itemInstance.getTemplate().getReuseDelay() > 0) {
                    player.sendPacket((IStaticPacket)new ExUseSharedGroupItem(itemInstance.getTemplate().getDisplayReuseGroup(), timeStamp));
                }
            }
        } else {
            player.sendActionFailed();
        }
    }
}
