/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.model.entity.oly.Stadium;

private class ExReceiveOlympiadGameList.GameRec {
    int stadium_id;
    int type;
    int state;
    String player0name;
    String player1name;

    public ExReceiveOlympiadGameList.GameRec(Stadium stadium, CompetitionType competitionType, int n, String string, String string2) {
        this.stadium_id = stadium.getStadiumId();
        this.type = competitionType.getTypeIdx();
        this.state = n;
        this.player0name = string;
        this.player1name = string2;
    }
}
