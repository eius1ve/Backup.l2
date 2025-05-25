/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ExGMViewQuestItemList
extends AbstractItemListPacket {
    private final int vu;
    private final int vv;
    private final ItemInstance[] c;
    private int jf;
    private String _name;

    public ExGMViewQuestItemList(int n, Player player, ItemInstance[] itemInstanceArray, int n2) {
        this.vu = n;
        this.c = itemInstanceArray;
        this.vv = n2;
        this._name = player.getName();
        this.jf = Config.QUEST_INVENTORY_MAXIMUM;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(200);
        this.writeC(this.vu);
        if (this.vu == 1) {
            this.writeS(this._name);
            this.writeD(this.jf);
            this.writeD(this.vv);
        } else if (this.vu == 2) {
            this.writeD(this.vv);
            this.writeD(this.vv);
            for (ItemInstance itemInstance : this.c) {
                if (!itemInstance.getTemplate().isQuest()) continue;
                this.writeItemInfo(new ItemInfo(itemInstance));
            }
        }
    }
}
