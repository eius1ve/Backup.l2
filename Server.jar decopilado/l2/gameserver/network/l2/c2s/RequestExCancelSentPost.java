/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExShowSentPostList;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.Log;

public class RequestExCancelSentPost
extends L2GameClientPacket {
    private int qM;

    @Override
    protected void readImpl() {
        this.qM = this.readD();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CANCEL_BECAUSE_THE_PRIVATE_SHOP_OR_WORKSHOP_IS_IN_PROGRESS);
            return;
        }
        if (player.isInTrade()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CANCEL_DURING_AN_EXCHANGE);
            return;
        }
        if (player.getEnchantScroll() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CANCEL_DURING_AN_ITEM_ENHANCEMENT_OR_ATTRIBUTE_ENHANCEMENT);
            return;
        }
        if (!player.isInPeaceZone() && Config.MAIL_ALLOW_AT_PEACE_ZONE) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_IN_A_NONPEACE_ZONE_LOCATION);
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
            return;
        }
        Mail mail = MailDAO.getInstance().getSentMailByMailId(player.getObjectId(), this.qM);
        if (mail != null) {
            if (mail.getAttachments().isEmpty()) {
                player.sendActionFailed();
                return;
            }
            player.getInventory().writeLock();
            try {
                ItemInstance[] itemInstanceArray;
                int n = 0;
                long l = 0L;
                for (ItemInstance itemInstanceArray2 : mail.getAttachments()) {
                    l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(itemInstanceArray2.getCount(), (long)itemInstanceArray2.getTemplate().getWeight()));
                    if (itemInstanceArray2.getTemplate().isStackable() && player.getInventory().getItemByItemId(itemInstanceArray2.getItemId()) != null) continue;
                    ++n;
                }
                if (!player.getInventory().validateWeight(l)) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_COULD_NOT_CANCEL_RECEIPT_BECAUSE_YOUR_INVENTORY_IS_FULL);
                    return;
                }
                if (!player.getInventory().validateCapacity(n)) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_COULD_NOT_CANCEL_RECEIPT_BECAUSE_YOUR_INVENTORY_IS_FULL);
                    return;
                }
                ItemInstance[] itemInstanceArray2 = itemInstanceArray = mail.getAttachments();
                synchronized (itemInstanceArray) {
                    ItemInstance[] itemInstanceArray4 = mail.getAttachments().toArray(new ItemInstance[itemInstanceArray.size()]);
                    itemInstanceArray.clear();
                    // ** MonitorExit[var8_9] (shouldn't be in output)
                    for (ItemInstance itemInstance : itemInstanceArray4) {
                        player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_ACQUIRED_S2_S1).addItemName(itemInstance.getItemId())).addNumber(itemInstance.getCount()));
                        Log.LogItem(player, Log.ItemLog.PostCancel, itemInstance);
                        player.getInventory().addItem(itemInstance);
                    }
                    mail.update();
                    MailDAO.getInstance().deleteSentMailByMailId(player.getObserverMode(), this.qM);
                    MailDAO.getInstance().deleteReceivedMailByMailId(player.getObserverMode(), this.qM);
                    mail.delete();
                    player.sendPacket((IStaticPacket)SystemMsg.MAIL_SUCCESSFULLY_CANCELLED);
                }
            }
            catch (ArithmeticException arithmeticException) {
            }
            finally {
                player.getInventory().writeUnlock();
            }
        }
        {
            player.sendPacket((IStaticPacket)new ExShowSentPostList(player));
            return;
        }
    }
}
