/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeShowInfoUpdate
extends L2GameServerPacket {
    private int clan_id;
    private int yV;
    private int Ap;
    private int Aq;
    private int crest_id;
    private int ally_id;
    private int ally_crest;
    private int Ar;
    private int zb;
    private String ally_name = "";
    private int As;
    private int At;
    private int Au;
    private boolean fc;

    public PledgeShowInfoUpdate(Clan clan) {
        this.clan_id = clan.getClanId();
        this.yV = clan.getLevel();
        this.As = clan.getCastle();
        this.At = clan.getHasHideout();
        this.Ap = clan.getRank();
        this.Aq = clan.getReputationScore();
        this.crest_id = clan.getCrestId();
        this.ally_id = clan.getAllyId();
        this.Ar = clan.isAtWar();
        this.fc = clan.isPlacedForDisband();
        Alliance alliance = clan.getAlliance();
        if (alliance != null) {
            this.ally_name = alliance.getAllyName();
            this.ally_crest = alliance.getAllyCrestId();
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(142);
        this.writeD(this.clan_id);
        this.writeD(Config.REQUEST_ID);
        this.writeD(this.crest_id);
        this.writeD(this.yV);
        this.writeD(this.As);
        this.writeD(0);
        this.writeD(this.At);
        this.writeD(0);
        this.writeD(this.Ap);
        this.writeD(this.Aq);
        this.writeD(this.fc ? 3 : 0);
        this.writeD(0);
        this.writeD(this.ally_id);
        this.writeS(this.ally_name);
        this.writeD(this.ally_crest);
        this.writeD(this.Ar);
        this.writeD(0);
        this.writeD(0);
    }
}
