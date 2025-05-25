/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.pledge.Clan;

private static class CastleSiegeDefenderList.DefenderClan {
    private Clan a;
    private int _type;
    private int _time;

    public CastleSiegeDefenderList.DefenderClan(Clan clan, int n, int n2) {
        this.a = clan;
        this._type = n;
        this._time = n2;
    }
}
