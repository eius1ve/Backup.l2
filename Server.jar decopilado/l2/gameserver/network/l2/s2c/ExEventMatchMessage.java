/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExEventMatchMessage
extends L2GameServerPacket {
    public static final ExEventMatchMessage FINISH = new ExEventMatchMessage(1);
    public static final ExEventMatchMessage START = new ExEventMatchMessage(2);
    public static final ExEventMatchMessage GAMEOVER = new ExEventMatchMessage(3);
    public static final ExEventMatchMessage COUNT1 = new ExEventMatchMessage(4);
    public static final ExEventMatchMessage COUNT2 = new ExEventMatchMessage(5);
    public static final ExEventMatchMessage COUNT3 = new ExEventMatchMessage(6);
    public static final ExEventMatchMessage COUNT4 = new ExEventMatchMessage(7);
    public static final ExEventMatchMessage COUNT5 = new ExEventMatchMessage(8);
    private int _type;
    private String dL;

    public ExEventMatchMessage(int n) {
        this._type = n;
        this.dL = "";
    }

    public ExEventMatchMessage(String string) {
        this._type = 0;
        this.dL = string;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(15);
        this.writeC(this._type);
        this.writeS(this.dL);
    }
}
