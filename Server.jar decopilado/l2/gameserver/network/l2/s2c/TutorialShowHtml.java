/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class TutorialShowHtml
extends L2GameServerPacket {
    public static final int NORMAL_WINDOW = 1;
    public static final int LARGE_WINDOW = 2;
    private String _html;
    private final int Cs;

    public TutorialShowHtml(String string) {
        this._html = string;
        this.Cs = 1;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(166);
        this.writeD(this.Cs);
        this.writeS(this._html);
    }
}
