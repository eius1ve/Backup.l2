/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.pledge.Clan;

public class AuctionSiegeClanObject
extends SiegeClanObject {
    private long cc;

    public AuctionSiegeClanObject(String string, Clan clan, long l) {
        this(string, clan, l, System.currentTimeMillis());
    }

    public AuctionSiegeClanObject(String string, Clan clan, long l, long l2) {
        super(string, clan, l, l2);
        this.cc = l;
    }

    @Override
    public long getParam() {
        return this.cc;
    }

    public void setParam(long l) {
        this.cc = l;
    }
}
