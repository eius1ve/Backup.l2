/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.concurrent.CopyOnWriteArrayList;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.RecipeShopMsg;
import l2.gameserver.utils.TradeHelper;

public class RequestRecipeShopListSet
extends L2GameClientPacket {
    private int[] aV;
    private long[] e;
    private int gT;

    @Override
    protected void readImpl() {
        this.gT = this.readD();
        if (this.gT * 12 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aV = new int[this.gT];
        this.e = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aV[i] = this.readD();
            this.e[i] = this.readQ();
            if (this.e[i] >= 0L) continue;
            this.gT = 0;
            return;
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        if (!TradeHelper.checksIfCanOpenStore(player, 5)) {
            player.sendActionFailed();
            return;
        }
        if (this.gT > Config.MAX_PVTCRAFT_SLOTS) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        CopyOnWriteArrayList<ManufactureItem> copyOnWriteArrayList = new CopyOnWriteArrayList<ManufactureItem>();
        for (int i = 0; i < this.gT; ++i) {
            int n = this.aV[i];
            long l = this.e[i];
            if (!player.findRecipe(n)) continue;
            ManufactureItem manufactureItem = new ManufactureItem(n, l);
            copyOnWriteArrayList.add(manufactureItem);
        }
        if (!copyOnWriteArrayList.isEmpty()) {
            player.setCreateList(copyOnWriteArrayList);
            player.saveTradeList();
            player.setPrivateStoreType(5);
            player.broadcastPacket(new RecipeShopMsg(player));
        }
        player.sendActionFailed();
    }
}
