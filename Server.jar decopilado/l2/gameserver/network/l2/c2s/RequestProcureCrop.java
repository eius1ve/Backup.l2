/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.Collections;
import java.util.List;
import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Manor;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.ManorManagerInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.manor.CropProcure;

public class RequestProcureCrop
extends L2GameClientPacket {
    private int hp;
    private int gT;
    private int[] aP;
    private long[] c;
    private List<CropProcure> bP = Collections.emptyList();

    @Override
    protected void readImpl() {
        this.hp = this.readD();
        this.gT = this.readD();
        if (this.gT * 16 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.readD();
            this.aP[i] = this.readD();
            this.c[i] = this.readD();
            if (this.c[i] >= 1L) continue;
            this.gT = 0;
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        long l;
        int n;
        int n2;
        ManorManagerInstance manorManagerInstance;
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
        GameObject gameObject = player.getTarget();
        ManorManagerInstance manorManagerInstance2 = manorManagerInstance = gameObject != null && gameObject instanceof ManorManagerInstance ? (ManorManagerInstance)gameObject : null;
        if (!(player.isGM() || manorManagerInstance != null && player.isInActingRange(manorManagerInstance))) {
            player.sendActionFailed();
            return;
        }
        Castle castle = ResidenceHolder.getInstance().getResidence(Castle.class, this.hp);
        if (castle == null) {
            return;
        }
        int n3 = 0;
        long l2 = 0L;
        try {
            for (n2 = 0; n2 < this.gT; ++n2) {
                n = this.aP[n2];
                l = this.c[n2];
                CropProcure cropProcure = castle.getCrop(n, 0);
                if (cropProcure == null) {
                    return;
                }
                int n4 = Manor.getInstance().getRewardItem(n, castle.getCrop(n, 0).getReward());
                long l3 = Manor.getInstance().getRewardAmountPerCrop(castle.getId(), n, castle.getCropRewardType(n));
                l3 = SafeMath.mulAndCheck(l, l3);
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n4);
                if (itemTemplate == null) {
                    return;
                }
                l2 = SafeMath.addAndCheck(l2, SafeMath.mulAndCheck(l, (long)itemTemplate.getWeight()));
                if (itemTemplate.isStackable() && player.getInventory().getItemByItemId(n) != null) continue;
                ++n3;
            }
        }
        catch (ArithmeticException arithmeticException) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        player.getInventory().writeLock();
        try {
            if (!player.getInventory().validateWeight(l2)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                return;
            }
            if (!player.getInventory().validateCapacity(n3)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                return;
            }
            this.bP = castle.getCropProcure(0);
            for (n2 = 0; n2 < this.gT; ++n2) {
                ItemInstance itemInstance;
                n = this.aP[n2];
                l = this.c[n2];
                int n5 = Manor.getInstance().getRewardItem(n, castle.getCrop(n, 0).getReward());
                long l4 = Manor.getInstance().getRewardAmountPerCrop(castle.getId(), n, castle.getCropRewardType(n));
                l4 = SafeMath.mulAndCheck(l, l4);
                if (!player.getInventory().destroyItemByItemId(n, l) || (itemInstance = player.getInventory().addItem(n5, l4)) == null) continue;
                player.sendPacket((IStaticPacket)SystemMessage.obtainItems(n5, l4, 0));
            }
        }
        catch (ArithmeticException arithmeticException) {
            this.gT = 0;
        }
        finally {
            player.getInventory().writeUnlock();
        }
        player.sendChanges();
    }
}
