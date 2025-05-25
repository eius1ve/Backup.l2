/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import java.util.HashMap;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.InventorySlot;
import l2.gameserver.network.l2.s2c.ShopPreviewInfo;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestPreviewItem
extends L2GameClientPacket {
    private static final Logger cU = LoggerFactory.getLogger(RequestPreviewItem.class);
    private int rP;
    private int fq;
    private int gT;
    private int[] aP;

    @Override
    protected void readImpl() {
        this.rP = this.readD();
        this.fq = this.readD();
        this.gT = this.readD();
        if (this.gT * 4 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
        }
    }

    @Override
    protected void runImpl() {
        boolean bl;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
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
        if (!Config.ALT_GAME_KARMA_PLAYER_CAN_SHOP && player.getKarma() > 0 && !player.isGM()) {
            player.sendActionFailed();
            return;
        }
        if (!Config.ALT_GAME_CURSED_WEAPON_PLAYER_CAN_SHOP && player.isCursedWeaponEquipped() && !player.isGM()) {
            player.sendActionFailed();
            return;
        }
        NpcInstance npcInstance = player.getLastNpc();
        boolean bl2 = bl = npcInstance != null && npcInstance.isMerchantNpc();
        if (!(player.isGM() || npcInstance != null && bl && npcInstance.isInActingRange(player))) {
            player.sendActionFailed();
            return;
        }
        BuyListHolder.NpcTradeList npcTradeList = BuyListHolder.getInstance().getBuyList(this.fq);
        if (npcTradeList == null) {
            player.sendActionFailed();
            return;
        }
        boolean bl3 = false;
        long l = 0L;
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        try {
            for (int i = 0; i < this.gT; ++i) {
                int n;
                int n2 = this.aP[i];
                if (npcTradeList.getItemByItemId(n2) == null) {
                    player.sendActionFailed();
                    return;
                }
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n2);
                if (itemTemplate == null || !itemTemplate.isEquipable() || (long)(n = ItemFunctions.getPaperdollIndex(itemTemplate.getBodyPart())) < 0L) continue;
                if (hashMap.containsKey(n)) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CAN_NOT_TRY_THOSE_ITEMS_ON_AT_THE_SAME_TIME);
                    return;
                }
                hashMap.put(n, n2);
                l += (long)itemTemplate.getItemGrade().getWearPreviewPrice();
            }
            if (!player.reduceAdena(l)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
        }
        catch (ArithmeticException arithmeticException) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        if (!hashMap.isEmpty()) {
            player.sendPacket((IStaticPacket)new ShopPreviewInfo(hashMap));
            ThreadPoolManager.getInstance().schedule(new RemoveWearItemsTask(player), Config.WEAR_DELAY * 1000);
        }
    }

    private static class RemoveWearItemsTask
    extends RunnableImpl {
        private Player c;

        public RemoveWearItemsTask(Player player) {
            this.c = player;
        }

        @Override
        public void runImpl() throws Exception {
            this.c.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NO_LONGER_TRYING_ON_EQUIPMENT);
            this.c.sendUserInfo(true);
            this.c.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(this.c, InventorySlot.VALUES));
        }
    }
}
