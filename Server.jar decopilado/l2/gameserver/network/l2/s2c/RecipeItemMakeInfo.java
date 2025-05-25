/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.Recipe;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RecipeItemMakeInfo
extends L2GameServerPacket {
    private int _id;
    private int AJ;
    private int _status;
    private int AK;
    private int AL;

    public RecipeItemMakeInfo(Player player, Recipe recipe, int n) {
        this._id = recipe.getId();
        this.AJ = recipe.getType().ordinal();
        this._status = n;
        this.AK = (int)player.getCurrentMp();
        this.AL = player.getMaxMp();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(221);
        this.writeD(this._id);
        this.writeD(this.AJ);
        this.writeD(this.AK);
        this.writeD(this.AL);
        this.writeD(this._status);
    }
}
