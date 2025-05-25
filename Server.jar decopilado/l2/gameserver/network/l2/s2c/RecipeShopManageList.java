/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collection;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.Recipe;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RecipeShopManageList
extends L2GameServerPacket {
    private List<ManufactureItem> cY;
    private Collection<Recipe> h;
    private int AP;
    private long de;
    private boolean fi;

    public RecipeShopManageList(Player player, boolean bl) {
        this.AP = player.getObjectId();
        this.de = player.getAdena();
        this.fi = bl;
        this.h = this.fi ? player.getDwarvenRecipeBook() : player.getCommonRecipeBook();
        this.cY = player.getCreateList();
        for (ManufactureItem manufactureItem : this.cY) {
            if (player.findRecipe(manufactureItem.getRecipeId())) continue;
            this.cY.remove(manufactureItem);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(222);
        this.writeD(this.AP);
        this.writeD((int)Math.min(this.de, Integer.MAX_VALUE));
        this.writeD(this.fi ? 0 : 1);
        this.writeD(this.h.size());
        int n = 1;
        for (Recipe object : this.h) {
            this.writeD(object.getId());
            this.writeD(n++);
        }
        this.writeD(this.cY.size());
        for (ManufactureItem manufactureItem : this.cY) {
            this.writeD(manufactureItem.getRecipeId());
            this.writeD(0);
            this.writeQ(manufactureItem.getCost());
        }
    }
}
