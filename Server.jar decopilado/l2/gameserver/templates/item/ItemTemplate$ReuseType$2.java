/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import java.util.Calendar;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.item.ItemTemplate;

final class ItemTemplate.ReuseType.2
extends ItemTemplate.ReuseType {
    private ItemTemplate.ReuseType.2(SystemMsg ... systemMsgArray) {
    }

    @Override
    public long next(ItemInstance itemInstance) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(11) > 6 || calendar.get(11) == 6 && calendar.get(12) >= 30) {
            calendar.add(5, 1);
        }
        calendar.set(11, 6);
        calendar.set(12, 30);
        return calendar.getTimeInMillis();
    }
}
