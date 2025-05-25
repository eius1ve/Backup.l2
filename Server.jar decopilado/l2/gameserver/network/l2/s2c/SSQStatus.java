/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.SevenSignsFestival.SevenSignsFestival;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.StatsSet;

public class SSQStatus
extends L2GameServerPacket {
    private Player _player;
    private int kl;
    private int AU;

    public SSQStatus(Player player, int n) {
        this._player = player;
        this.kl = n;
        this.AU = SevenSigns.getInstance().getCurrentPeriod();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(251);
        this.writeC(this.kl);
        this.writeC(this.AU);
        switch (this.kl) {
            case 1: {
                this.writeD(SevenSigns.getInstance().getCurrentCycle());
                switch (this.AU) {
                    case 0: {
                        this.writeD(1183);
                        break;
                    }
                    case 1: {
                        this.writeD(1176);
                        break;
                    }
                    case 2: {
                        this.writeD(1184);
                        break;
                    }
                    case 3: {
                        this.writeD(1177);
                    }
                }
                switch (this.AU) {
                    case 0: 
                    case 2: {
                        this.writeD(1287);
                        break;
                    }
                    case 1: 
                    case 3: {
                        this.writeD(1286);
                    }
                }
                this.writeC(SevenSigns.getInstance().getPlayerCabal(this._player));
                this.writeC(SevenSigns.getInstance().getPlayerSeal(this._player));
                this.writeQ(SevenSigns.getInstance().getPlayerStoneContrib(this._player));
                this.writeQ(SevenSigns.getInstance().getPlayerAdenaCollect(this._player));
                long l = SevenSigns.getInstance().getCurrentStoneScore(2);
                long l2 = SevenSigns.getInstance().getCurrentFestivalScore(2);
                long l3 = SevenSigns.getInstance().getCurrentScore(2);
                long l4 = SevenSigns.getInstance().getCurrentStoneScore(1);
                long l5 = SevenSigns.getInstance().getCurrentFestivalScore(1);
                long l6 = SevenSigns.getInstance().getCurrentScore(1);
                long l7 = l4 + l;
                l7 = l7 == 0L ? 1L : l7;
                long l8 = Math.round((double)l4 * 500.0 / (double)l7);
                long l9 = Math.round((double)l * 500.0 / (double)l7);
                long l10 = l6 + l3;
                l10 = l10 == 0L ? 1L : l10;
                long l11 = Math.round((double)l3 * 110.0 / (double)l10);
                long l12 = Math.round((double)l6 * 110.0 / (double)l10);
                this.writeQ(l8);
                this.writeQ(l5);
                this.writeQ(l6);
                this.writeC((int)l12);
                this.writeQ(l9);
                this.writeQ(l2);
                this.writeQ(l3);
                this.writeC((int)l11);
                break;
            }
            case 2: {
                this.writeH(1);
                this.writeC(5);
                for (int i = 0; i < 5; ++i) {
                    String[] stringArray;
                    this.writeC(i + 1);
                    this.writeD(SevenSignsFestival.FESTIVAL_LEVEL_SCORES[i]);
                    long l = SevenSignsFestival.getInstance().getHighestScore(1, i);
                    long l13 = SevenSignsFestival.getInstance().getHighestScore(2, i);
                    this.writeQ(l);
                    StatsSet statsSet = SevenSignsFestival.getInstance().getHighestScoreData(1, i);
                    if (l > 0L) {
                        stringArray = statsSet.getString("names").split(",");
                        this.writeC(stringArray.length);
                        for (String string : stringArray) {
                            this.writeS(string);
                        }
                    } else {
                        this.writeC(0);
                    }
                    this.writeQ(l13);
                    statsSet = SevenSignsFestival.getInstance().getHighestScoreData(2, i);
                    if (l13 > 0L) {
                        stringArray = statsSet.getString("names").split(",");
                        this.writeC(stringArray.length);
                        for (String string : stringArray) {
                            this.writeS(string);
                        }
                        continue;
                    }
                    this.writeC(0);
                }
                break;
            }
            case 3: {
                int n;
                this.writeC(10);
                this.writeC(35);
                this.writeC(3);
                int n2 = 1;
                int n3 = 1;
                for (n = 1; n <= 3; ++n) {
                    n2 += SevenSigns.getInstance().getSealProportion(n, 2);
                    n3 += SevenSigns.getInstance().getSealProportion(n, 1);
                }
                n2 = Math.max(1, n2);
                n3 = Math.max(1, n3);
                for (n = 1; n <= 3; ++n) {
                    int n4 = SevenSigns.getInstance().getSealProportion(n, 2);
                    int n5 = SevenSigns.getInstance().getSealProportion(n, 1);
                    this.writeC(n);
                    this.writeC(SevenSigns.getInstance().getSealOwner(n));
                    this.writeC(n5 * 100 / n3);
                    this.writeC(n4 * 100 / n2);
                }
                break;
            }
            case 4: {
                int n = SevenSigns.getInstance().getCabalHighestScore();
                this.writeC(n);
                this.writeC(3);
                int n6 = SevenSigns.getInstance().getTotalMembers(2);
                int n7 = SevenSigns.getInstance().getTotalMembers(1);
                for (int i = 1; i < 4; ++i) {
                    this.writeC(i);
                    int n8 = SevenSigns.getInstance().getSealProportion(i, 2);
                    int n9 = SevenSigns.getInstance().getSealProportion(i, 1);
                    int n10 = n6 > 0 ? n8 * 100 / n6 : 0;
                    int n11 = n7 > 0 ? n9 * 100 / n7 : 0;
                    int n12 = SevenSigns.getInstance().getSealOwner(i);
                    if (Math.max(n10, n11) < 10) {
                        this.writeC(0);
                        if (n12 == 0) {
                            this.writeD(SystemMsg.SINCE_THE_SEAL_WAS_NOT_OWNED_DURING_THE_PREVIOUS_PERIOD_AND_SINCE_LESS_THAN_35_PERCENT_OF_PEOPLE_HAVE_VOTED.id());
                            continue;
                        }
                        this.writeD(SystemMsg.ALTHOUGH_THE_SEAL_WAS_OWNED_DURING_THE_PREVIOUS_PERIOD_LESS_THAN_10_OF_PEOPLE_HAVE_VOTED.id());
                        continue;
                    }
                    if (Math.max(n10, n11) < 35) {
                        this.writeC(n12);
                        if (n12 == 0) {
                            this.writeD(SystemMsg.SINCE_THE_SEAL_WAS_NOT_OWNED_DURING_THE_PREVIOUS_PERIOD_AND_SINCE_LESS_THAN_35_PERCENT_OF_PEOPLE_HAVE_VOTED.id());
                            continue;
                        }
                        this.writeD(SystemMsg.SINCE_THE_SEAL_WAS_OWNED_DURING_THE_PREVIOUS_PERIOD_AND_10_OR_MORE_PEOPLE_HAVE_PARTICIPATED.id());
                        continue;
                    }
                    if (n10 == n11) {
                        this.writeC(0);
                        this.writeD(SystemMsg.IF_CURRENT_TRENDS_CONTINUE_IT_WILL_END_IN_A_TIE.id());
                        continue;
                    }
                    int n13 = n10 > n11 ? 2 : 1;
                    this.writeC(n13);
                    if (n13 == n12) {
                        this.writeD(SystemMsg.SINCE_THE_SEAL_WAS_OWNED_DURING_THE_PREVIOUS_PERIOD_AND_10_OR_MORE_PEOPLE_HAVE_PARTICIPATED.id());
                        continue;
                    }
                    this.writeD(SystemMsg.ALTHOUGH_THE_SEAL_WAS_NOT_OWNED_35_OR_MORE_PEOPLE_HAVE_PARTICIPATED.id());
                }
                break;
            }
        }
        this._player = null;
    }
}
