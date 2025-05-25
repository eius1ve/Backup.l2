/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.PremiumItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExGetPremiumItemList;
import l2.gameserver.network.l2.s2c.SystemMessage;

public final class RequestWithDrawPremiumItem
extends L2GameClientPacket {
    private int sh;
    private int fX;
    private long cW;

    @Override
    protected void readImpl() {
        this.sh = this.readD();
        this.fX = this.readD();
        this.cW = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.cW <= 0L) {
            return;
        }
        if (player.getObjectId() != this.fX) {
            return;
        }
        if (player.getPremiumItemList().isEmpty()) {
            return;
        }
        if (player.getWeightPenalty() >= 3 || (double)player.getInventoryLimit() * 0.8 <= (double)player.getInventory().getSize()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_THE_DIMENSIONAL_ITEM_BECAUSE_YOU_HAVE_EXCEED_YOUR_INVENTORY_WEIGHTQUANTITY_LIMIT);
            return;
        }
        if (player.isProcessingRequest()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_A_DIMENSIONAL_ITEM_DURING_AN_EXCHANGE);
            return;
        }
        PremiumItem premiumItem = player.getPremiumItemList().get(this.sh);
        if (premiumItem == null) {
            return;
        }
        boolean bl = ItemHolder.getInstance().getTemplate(premiumItem.getItemId()).isStackable();
        if (premiumItem.getCount() < this.cW) {
            return;
        }
        if (!bl) {
            int n = 0;
            while ((long)n < this.cW) {
                this.a(player, premiumItem.getItemId(), 1L);
                ++n;
            }
        } else {
            this.a(player, premiumItem.getItemId(), this.cW);
        }
        if (this.cW < premiumItem.getCount()) {
            player.getPremiumItemList().get(this.sh).updateCount(premiumItem.getCount() - this.cW);
            player.updatePremiumItem(this.sh, premiumItem.getCount() - this.cW);
        } else {
            player.getPremiumItemList().remove(this.sh);
            player.deletePremiumItem(this.sh);
        }
        if (player.getPremiumItemList().isEmpty()) {
            player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_MORE_DIMENSIONAL_ITEMS_TO_BE_FOUND);
        } else {
            player.sendPacket((IStaticPacket)new ExGetPremiumItemList(player));
        }
    }

    private void a(Player player, int n, long l) {
        player.getInventory().addItem(n, l);
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems(n, l, 0));
    }
}
