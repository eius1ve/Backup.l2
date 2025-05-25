/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.pfilter;

import l2.commons.util.RateTracker;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.pfilter.Limit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RateLimit {
    private final Limit a;
    private final RateTracker a;

    public RateLimit(Limit limit) {
        this.a = limit;
        this.a = new RateTracker(limit.getCount(), limit.getPerMs());
    }

    public Limit getLimit() {
        return this.a;
    }

    public RateTracker getRateTracker() {
        return this.a;
    }

    public boolean tryPass(GameClient gameClient) {
        if (this.a.tryPass()) {
            return true;
        }
        this.a.enforce(gameClient);
        return false;
    }
}
