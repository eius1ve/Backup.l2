/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.data.xml.holder.MultiSellHolder$MultiSellListContainer
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.MultiSellEntry
 *  l2.gameserver.model.base.MultiSellIngredient
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.MultiSellEntry;
import l2.gameserver.model.base.MultiSellIngredient;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import org.apache.commons.lang3.ArrayUtils;

public class doCrystallize
extends Functions
implements INpcHtmlAppendHandler {
    public String getHtmlAppends(Integer n) {
        if (n != 0 || !Config.ALT_BS_CRYSTALLIZE) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getSelf().isLangRus()) {
            if (Config.ALT_BS_CRYSTALLIZE) {
                stringBuilder.append("<br1>[scripts_services.doCrystallize:doCrystallize|\u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u0438\u0437\u0430\u0446\u0438\u044f]");
            }
        } else if (Config.ALT_BS_CRYSTALLIZE) {
            stringBuilder.append("<br1>[scripts_services.doCrystallize:doCrystallize|Crystallize]");
        }
        return stringBuilder.toString();
    }

    public void doCrystallize() {
        Player player = this.getSelf();
        NpcInstance npcInstance = player.getLastNpc();
        Castle castle = npcInstance != null ? npcInstance.getCastle(player) : null;
        MultiSellHolder.MultiSellListContainer multiSellListContainer = new MultiSellHolder.MultiSellListContainer();
        multiSellListContainer.setShowAll(false);
        multiSellListContainer.setKeepEnchant(true);
        multiSellListContainer.setNoTax(false);
        int n = 0;
        PcInventory pcInventory = player.getInventory();
        for (ItemInstance itemInstance : pcInventory.getItems()) {
            if (!itemInstance.canBeCrystallized(player)) continue;
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(itemInstance.getCrystalItemId());
            MultiSellEntry multiSellEntry = new MultiSellEntry(++n, itemTemplate.getItemId(), itemInstance.getTemplate().getCrystalCount(), 0, 0);
            multiSellEntry.addIngredient(new MultiSellIngredient(itemInstance.getItemId(), 1L, itemInstance.getEnchantLevel(), 0));
            multiSellEntry.addIngredient(new MultiSellIngredient(57, Math.round((double)((long)itemInstance.getTemplate().getCrystalCount() * itemTemplate.getReferencePrice()) * 0.05), 0, 0));
            multiSellListContainer.addEntry(multiSellEntry);
        }
        MultiSellHolder.getInstance().SeparateAndSend(multiSellListContainer, player, castle == null ? 0.0 : castle.getTaxRate());
    }

    public int[] getNpcIds() {
        if (!Config.ALT_BS_CRYSTALLIZE) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.ALT_BS_CRYSTALLIZE_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        doCrystallize doCrystallize2 = new doCrystallize();
        doCrystallize2.self = player.getRef();
        return doCrystallize2.getHtmlAppends(n2);
    }
}
