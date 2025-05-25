/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.item.ItemTemplate;

final class ItemTemplate.ReuseType.1
extends ItemTemplate.ReuseType {
    private ItemTemplate.ReuseType.1(SystemMsg ... systemMsgArray) {
    }

    @Override
    public long next(ItemInstance itemInstance) {
        return System.currentTimeMillis() + (long)itemInstance.getTemplate().getReuseDelay();
    }
}
