/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Snoop
extends L2GameServerPacket {
    private int fW;
    private int _type;
    private String _name;
    private String fM;
    private String eE;

    public Snoop(int n, String string, ChatType chatType, String string2, String string3) {
        this.fW = n;
        this._name = string;
        this._type = chatType.ordinal();
        this.fM = string2;
        this.eE = string3;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(219);
        this.writeD(this.fW);
        this.writeS(this._name);
        this.writeD(this.fW);
        this.writeD(this._type);
        this.writeS(this.fM);
        this.writeS(this.eE);
    }
}
