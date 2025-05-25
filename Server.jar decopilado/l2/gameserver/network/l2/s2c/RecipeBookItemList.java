/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collection;
import l2.gameserver.model.Player;
import l2.gameserver.model.Recipe;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RecipeBookItemList
extends L2GameServerPacket {
    private Collection<Recipe> g;
    private final boolean fh;
    private final int AI;

    public RecipeBookItemList(Player player, boolean bl) {
        this.fh = bl;
        this.AI = (int)player.getCurrentMp();
        this.g = bl ? player.getDwarvenRecipeBook() : player.getCommonRecipeBook();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(220);
        this.writeD(this.fh ? 0 : 1);
        this.writeD(this.AI);
        this.writeD(this.g.size());
        for (Recipe recipe : this.g) {
            this.writeD(recipe.getId());
            this.writeD(1);
        }
    }
}
