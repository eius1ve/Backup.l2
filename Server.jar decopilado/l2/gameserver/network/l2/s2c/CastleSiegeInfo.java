/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.s2c;

import java.util.Calendar;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.CastleSiegeEvent;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.apache.commons.lang3.ArrayUtils;

public class CastleSiegeInfo
extends L2GameServerPacket {
    private long db;
    private int _id;
    private int sP;
    private int oP;
    private boolean ed;
    private String _ownerName = "NPC";
    private String eL = "";
    private String dJ = "";
    private int[] aY = ArrayUtils.EMPTY_INT_ARRAY;

    public CastleSiegeInfo(Castle castle, Player player) {
        this((Residence)castle, player);
        CastleSiegeEvent castleSiegeEvent = (CastleSiegeEvent)castle.getSiegeEvent();
        long l = castle.getSiegeDate().getTimeInMillis();
        if (l == 0L) {
            this.aY = castleSiegeEvent.getNextSiegeTimes();
        } else {
            this.db = (int)(l / 1000L);
        }
    }

    public CastleSiegeInfo(ClanHall clanHall, Player player) {
        this((Residence)clanHall, player);
        this.db = (int)(clanHall.getSiegeDate().getTimeInMillis() / 1000L);
    }

    protected CastleSiegeInfo(Residence residence, Player player) {
        this._id = residence.getId();
        this.sP = residence.getOwnerId();
        Clan clan = residence.getOwner();
        if (clan != null) {
            this.ed = player.isGM() || clan.getLeaderId(0) == player.getObjectId();
            this._ownerName = clan.getName();
            this.eL = clan.getLeaderName(0);
            Alliance alliance = clan.getAlliance();
            if (alliance != null) {
                this.oP = alliance.getAllyId();
                this.dJ = alliance.getAllyName();
            }
        }
    }

    @Override
    protected void writeImpl() {
        this.writeC(201);
        this.writeD(this._id);
        this.writeD(this.ed ? 1 : 0);
        this.writeD(this.sP);
        this.writeS(this._ownerName);
        this.writeS(this.eL);
        this.writeD(this.oP);
        this.writeS(this.dJ);
        this.writeD((int)(Calendar.getInstance().getTimeInMillis() / 1000L));
        this.writeD((int)this.db);
        if (this.db == 0L) {
            this.writeDD(this.aY, true);
        }
    }
}
