/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.item.support.Grade;

public class ExShowBaseAttributeCancelWindow
extends L2GameServerPacket {
    private final List<ItemInstance> cw = new ArrayList<ItemInstance>();

    public ExShowBaseAttributeCancelWindow(Player player) {
        for (ItemInstance itemInstance : player.getInventory().getItems()) {
            if (itemInstance.getAttributeElement() == Element.NONE || !itemInstance.canBeEnchanted(true) || ExShowBaseAttributeCancelWindow.getAttributeRemovePrice(itemInstance) == 0L) continue;
            this.cw.add(itemInstance);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(116);
        this.writeD(this.cw.size());
        for (ItemInstance itemInstance : this.cw) {
            this.writeD(itemInstance.getObjectId());
            this.writeQ(ExShowBaseAttributeCancelWindow.getAttributeRemovePrice(itemInstance));
        }
    }

    public static long getAttributeRemovePrice(ItemInstance itemInstance) {
        if (itemInstance.getCrystalType() == Grade.S_GRADE) {
            return itemInstance.getTemplate().getType2() == 0 ? 50000L : 40000L;
        }
        return 0L;
    }
}
