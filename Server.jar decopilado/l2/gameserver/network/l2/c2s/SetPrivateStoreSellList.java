/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.c2s;

import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.math.SafeMath;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPrivateStoreSetWholeMsg;
import l2.gameserver.network.l2.s2c.PrivateStoreManageListSell;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgSell;
import l2.gameserver.utils.TradeHelper;
import org.apache.commons.lang3.ArrayUtils;

public class SetPrivateStoreSellList
extends L2GameClientPacket {
    private int gT;
    private boolean ec;
    private int[] aP;
    private long[] c;
    private long[] d;

    @Override
    protected void readImpl() {
        this.ec = this.readD() == 1;
        this.gT = this.readD();
        if (this.gT * 20 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        this.d = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            this.c[i] = this.readQ();
            this.d[i] = this.readQ();
            if (this.c[i] >= 1L && this.d[i] >= 0L && ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
            this.gT = 0;
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        if (!TradeHelper.checksIfCanOpenStore(player, this.ec ? 8 : 1)) {
            if (player.isInStoreMode()) {
                player.setPrivateStoreType(0);
            }
            player.sendPacket(new PrivateStoreManageListSell(true, player, this.ec), new PrivateStoreManageListSell(false, player, this.ec));
            player.sendActionFailed();
            return;
        }
        CopyOnWriteArrayList<TradeItem> copyOnWriteArrayList = new CopyOnWriteArrayList<TradeItem>();
        long l = 0L;
        player.getInventory().writeLock();
        try {
            for (int i = 0; i < this.gT; ++i) {
                int n = this.aP[i];
                long l2 = this.c[i];
                long l3 = this.d[i];
                ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
                if (itemInstance == null || itemInstance.getCount() < l2 || !itemInstance.canBeTraded(player) || itemInstance.getItemId() == 57) continue;
                TradeItem tradeItem = new TradeItem(itemInstance);
                tradeItem.setCount(l2);
                tradeItem.setOwnersPrice(l3);
                l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(l2, l3));
                copyOnWriteArrayList.add(tradeItem);
            }
        }
        catch (ArithmeticException arithmeticException) {
            copyOnWriteArrayList.clear();
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        finally {
            player.getInventory().writeUnlock();
        }
        if (copyOnWriteArrayList.size() > player.getTradeLimit() || SafeMath.addAndCheck(l, player.getAdena()) >= Long.MAX_VALUE) {
            player.sendPacket(new PrivateStoreManageListSell(true, player, this.ec), new PrivateStoreManageListSell(false, player, this.ec));
            return;
        }
        if (!copyOnWriteArrayList.isEmpty()) {
            player.setSellList(this.ec, copyOnWriteArrayList);
            player.saveTradeList();
            player.setPrivateStoreType(this.ec ? 8 : 1);
            player.broadcastPacket(this.ec ? new ExPrivateStoreSetWholeMsg(player) : new PrivateStoreMsgSell(player));
        }
        player.sendActionFailed();
    }
}
