/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class TutorialShowQuestionMark
extends L2GameServerPacket {
    private final int Ct;
    private final int Cu;

    public TutorialShowQuestionMark(int n, int n2) {
        this.Ct = n;
        this.Cu = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(167);
        this.writeC(this.Cu);
        this.writeD(this.Ct);
    }
}
