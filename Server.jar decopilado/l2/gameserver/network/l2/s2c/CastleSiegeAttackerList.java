/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.List;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class CastleSiegeAttackerList
extends L2GameServerPacket {
    private int _id;
    private int sO;
    private List<SiegeClanObject> bW = Collections.emptyList();

    public CastleSiegeAttackerList(Residence residence) {
        this._id = residence.getId();
        this.sO = !((SiegeEvent)residence.getSiegeEvent()).isRegistrationOver() ? 1 : 0;
        this.bW = ((GlobalEvent)residence.getSiegeEvent()).getObjects("attackers");
    }

    @Override
    protected final void writeImpl() {
        this.writeC(202);
        this.writeD(this._id);
        this.writeD(0);
        this.writeD(this.sO);
        this.writeD(0);
        this.writeD(this.bW.size());
        this.writeD(this.bW.size());
        for (SiegeClanObject siegeClanObject : this.bW) {
            Clan clan = siegeClanObject.getClan();
            this.writeD(clan.getClanId());
            this.writeS(clan.getName());
            this.writeS(clan.getLeaderName());
            this.writeD(clan.getCrestId());
            this.writeD((int)(siegeClanObject.getDate() / 1000L));
            Alliance alliance = clan.getAlliance();
            this.writeD(clan.getAllyId());
            if (alliance != null) {
                this.writeS(alliance.getAllyName());
                this.writeS(alliance.getAllyLeaderName());
                this.writeD(alliance.getAllyCrestId());
                continue;
            }
            this.writeS("");
            this.writeS("");
            this.writeD(0);
        }
    }
}
