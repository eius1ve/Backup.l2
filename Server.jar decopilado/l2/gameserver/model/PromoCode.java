/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.List;
import l2.gameserver.model.promoCode.PromoCodeReward;

public class PromoCode {
    private final String cT;
    private final int jf;
    private final long bG;
    private final long bH;
    private final List<PromoCodeReward> bb;
    private final boolean co;
    private final boolean cp;
    private final boolean cq;
    private final int jg;
    private final int jh;

    public PromoCode(String string, int n, long l, long l2, List<PromoCodeReward> list, boolean bl, boolean bl2, boolean bl3, int n2, int n3) {
        this.cT = string;
        this.jf = n;
        this.bG = l;
        this.bH = l2;
        this.bb = list;
        this.co = bl;
        this.cp = bl2;
        this.cq = bl3;
        this.jg = n2;
        this.jh = n3;
    }

    public String getName() {
        return this.cT;
    }

    public long getFromDate() {
        return this.bG;
    }

    public long getToDate() {
        return this.bH;
    }

    public List<PromoCodeReward> getRewards() {
        return this.bb;
    }

    public int getLimit() {
        return this.jf;
    }

    public boolean isLimitByUser() {
        return this.co;
    }

    public boolean isLimitByHWID() {
        return this.cp;
    }

    public boolean isLimitByIP() {
        return this.cq;
    }

    public int getMinLevel() {
        return this.jg;
    }

    public int getMaxLevel() {
        return this.jh;
    }
}
