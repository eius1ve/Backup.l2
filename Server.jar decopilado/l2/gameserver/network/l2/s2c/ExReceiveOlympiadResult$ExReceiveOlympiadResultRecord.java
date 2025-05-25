/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;

private class ExReceiveOlympiadResult.ExReceiveOlympiadResultRecord {
    String name;
    String clan;
    int class_id;
    int crest_id;
    int dmg;
    int points;
    int delta;

    public ExReceiveOlympiadResult.ExReceiveOlympiadResultRecord(Player player, int n, int n2, int n3) {
        this.name = player.getName();
        this.class_id = player.getClassId().getId();
        this.clan = player.getClan() != null ? player.getClan().getName() : "";
        this.crest_id = player.getClanId();
        this.dmg = n;
        this.points = n2;
        this.delta = n3;
    }
}
