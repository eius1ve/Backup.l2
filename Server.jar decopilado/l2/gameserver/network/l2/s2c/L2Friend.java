/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class L2Friend
extends L2GameServerPacket {
    private boolean fa;
    private boolean aq;
    private String _name;
    private int zo;

    public L2Friend(Player player, boolean bl) {
        this.fa = bl;
        this._name = player.getName();
        this.zo = player.getObjectId();
        this.aq = true;
    }

    public L2Friend(String string, boolean bl, boolean bl2, int n) {
        this._name = string;
        this.fa = bl;
        this.zo = n;
        this.aq = bl2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(118);
        this.writeD(this.fa ? 1 : 3);
        this.writeD(0);
        this.writeS(this._name);
        this.writeD(this.aq ? 1 : 0);
        this.writeD(this.zo);
    }
}
