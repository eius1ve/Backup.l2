/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.c2s;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.data.xml.holder.EnsoulFeeHolder;
import l2.gameserver.data.xml.holder.EnsoulOptionHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.c2s.RequestExItemEnsoul;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.ExEnSoulExtractionResult;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;
import l2.gameserver.templates.item.support.EnsoulOption;
import l2.gameserver.templates.item.support.Grade;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.tuple.Pair;

public class RequestTryEnSoulExtraction
extends L2GameClientPacket {
    private int qO;
    private int slotType;
    private int slotIndex;

    @Override
    protected void readImpl() throws Exception {
        this.qO = this.readD();
        this.slotType = this.readC();
        this.slotIndex = this.readC();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() throws Exception {
        int n;
        EnsoulFeeSlotType ensoulFeeSlotType;
        GameClient gameClient = (GameClient)this.getClient();
        if (gameClient == null) {
            return;
        }
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - gameClient.getLastIncomePacketTimeStamp(RequestTryEnSoulExtraction.class) <= 1500L) {
            player.sendActionFailed();
            return;
        }
        gameClient.setLastIncomePacketTimeStamp(RequestTryEnSoulExtraction.class, l);
        if (!RequestExItemEnsoul.CheckPlayerConditions(player) || (ensoulFeeSlotType = EnsoulFeeSlotType.getSlotType(this.slotType)) == null) {
            player.sendPacket(ExEnSoulExtractionResult.ENSOUL_EXTRACT_FAILED_STATIC, ActionFail.STATIC);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.qO);
        LinkedHashMap<Integer, Long> linkedHashMap = new LinkedHashMap<Integer, Long>();
        Grade grade = itemInstance.getCrystalType();
        for (Pair<Integer, Long> object : EnsoulFeeHolder.getInstance().getRemoveFee(grade, ensoulFeeSlotType)) {
            linkedHashMap.put((Integer)object.getKey(), linkedHashMap.getOrDefault(object.getKey(), 0L) + (Long)object.getValue());
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            if (player.getInventory().getCountOf((Integer)entry.getKey()) >= (Long)entry.getValue()) continue;
            player.sendPacket(ExEnSoulExtractionResult.ENSOUL_EXTRACT_FAILED_STATIC, SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS_CHECK_UP_THEIR_NUMBER, ActionFail.STATIC);
            return;
        }
        boolean bl = false;
        int n2 = itemInstance.getEnsoulSlotN1();
        int n3 = itemInstance.getEnsoulSlotN2();
        int n4 = itemInstance.getEnsoulSlotBm();
        EnsoulOption ensoulOption = null;
        if (ensoulFeeSlotType == EnsoulFeeSlotType.Normal) {
            switch (this.slotIndex) {
                case 1: {
                    if (n2 <= 0) break;
                    ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(n2);
                    n = n3;
                    n3 = 0;
                    bl = true;
                    break;
                }
                case 2: {
                    if (n3 <= 0) break;
                    ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(n3);
                    n3 = 0;
                    bl = true;
                }
            }
        } else if (ensoulFeeSlotType == EnsoulFeeSlotType.Bm && this.slotIndex == 1 && n4 > 0) {
            ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(n4);
            n4 = 0;
            bl = true;
        }
        if (!bl || ensoulOption == null) {
            player.sendPacket(ExEnSoulExtractionResult.ENSOUL_EXTRACT_FAILED_STATIC, new SystemMessage(SystemMsg.CHOOSE_THE_EFFECT_OF_THE_RUNE_FROM_THE_EFFECTS_LIST_FIRST), ActionFail.STATIC);
            return;
        }
        boolean bl2 = false;
        try {
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                if (player.getInventory().destroyItemByItemId((Integer)entry.getKey(), (Long)entry.getValue())) continue;
                player.sendPacket(ExEnSoulExtractionResult.ENSOUL_EXTRACT_FAILED_STATIC, ActionFail.STATIC);
                return;
            }
            bl2 = itemInstance.isEquipped();
            if (bl2) {
                player.getInventory().unEquipItem(itemInstance);
            }
            itemInstance.setEnsoulSlotN1(n);
            itemInstance.setEnsoulSlotN2(n3);
            itemInstance.setEnsoulSlotBm(n4);
            Iterator<ShortCut> iterator = ItemHolder.getInstance().getTemplate(ensoulOption.getStoneId());
            if (ItemFunctions.canAddItem(player, (ItemTemplate)((Object)iterator), 1L)) {
                ItemFunctions.addItem((Playable)player, (ItemTemplate)((Object)iterator), 1L, true);
            }
        }
        finally {
            if (bl2) {
                player.getInventory().equipItem(itemInstance);
            }
        }
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        player.sendPacket(new ExEnSoulExtractionResult(true, itemInstance), new InventoryUpdate().addModifiedItem(itemInstance));
    }
}
