/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import java.util.List;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.pfilter.LimitAction;

public class Limit {
    private final List<LimitAction> dg;
    private final boolean fw;
    private final int CO;
    private final int CP;

    public Limit(List<LimitAction> list, boolean bl, int n, int n2) {
        this.dg = list;
        this.fw = bl;
        this.CO = n;
        this.CP = n2;
    }

    public int getCount() {
        return this.CO;
    }

    public int getPerMs() {
        return this.CP;
    }

    public boolean isDropPacket() {
        return this.fw;
    }

    public void enforce(GameClient gameClient) {
        for (LimitAction limitAction : this.dg) {
            limitAction.doIt(gameClient);
        }
    }
}
