/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import java.util.List;
import l2.commons.collections.CollectionUtils;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.dao.SiegePlayerDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.RestartType;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.CTBSiegeClanObject;
import l2.gameserver.model.entity.events.objects.CTBTeamObject;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.Location;

public class ClanHallTeamBattleEvent
extends SiegeEvent<ClanHall, CTBSiegeClanObject> {
    public static final String TRYOUT_PART = "tryout_part";
    public static final String CHALLENGER_RESTART_POINTS = "challenger_restart_points";
    public static final String FIRST_DOORS = "first_doors";
    public static final String SECOND_DOORS = "second_doors";
    public static final String NEXT_STEP = "next_step";

    public ClanHallTeamBattleEvent(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
    }

    @Override
    public void startEvent() {
        this._oldOwner = ((ClanHall)this.getResidence()).getOwner();
        List list = this.getObjects("attackers");
        if (list.isEmpty()) {
            if (this._oldOwner == null) {
                this.broadcastInZone2(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_HAS_BEEN_CANCELED_DUE_TO_LACK_OF_INTEREST).addResidenceName((Residence)this.getResidence())});
            } else {
                this.broadcastInZone2(new L2GameServerPacket[]{new SystemMessage(SystemMsg.S1S_SIEGE_WAS_CANCELED_BECAUSE_THERE_WERE_NO_CLANS_THAT_PARTICIPATED).addResidenceName((Residence)this.getResidence())});
            }
            this.reCalcNextTime(false);
            return;
        }
        if (this._oldOwner != null) {
            this.addObject("defenders", new SiegeClanObject("defenders", this._oldOwner, 0L));
        }
        SiegeClanDAO.getInstance().delete((Residence)this.getResidence());
        SiegePlayerDAO.getInstance().delete((Residence)this.getResidence());
        List list2 = this.getObjects(TRYOUT_PART);
        for (int i = 0; i < 5; ++i) {
            CTBTeamObject cTBTeamObject = (CTBTeamObject)list2.get(i);
            cTBTeamObject.setSiegeClan((CTBSiegeClanObject)CollectionUtils.safeGet(list, i));
        }
        this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_TO_CONQUER_S1_HAS_BEGUN).addResidenceName((Residence)this.getResidence()), "attackers", "defenders");
        this.broadcastTo(SystemMsg.THE_TRYOUTS_ARE_ABOUT_TO_BEGIN, "attackers");
        super.startEvent();
    }

    public void nextStep() {
        this.broadcastTo(SystemMsg.THE_TRYOUTS_HAVE_BEGUN, "attackers", "defenders");
        this.updateParticles(true, "attackers", "defenders");
    }

    public void processStep(CTBTeamObject cTBTeamObject) {
        Object object;
        if (cTBTeamObject.getSiegeClan() != null) {
            object = cTBTeamObject.getSiegeClan();
            ((CTBSiegeClanObject)object).setEvent(false, this);
            this.teleportPlayers("spectators");
        }
        cTBTeamObject.despawnObject(this);
        object = this.getObjects(TRYOUT_PART);
        boolean bl = false;
        CTBTeamObject cTBTeamObject2 = null;
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            CTBTeamObject cTBTeamObject3 = (CTBTeamObject)object2.next();
            if (!cTBTeamObject3.isParticle()) continue;
            bl = cTBTeamObject2 == null;
            cTBTeamObject2 = cTBTeamObject3;
        }
        if (!bl) {
            return;
        }
        object2 = cTBTeamObject2.getSiegeClan();
        if (object2 != null) {
            ((ClanHall)this.getResidence()).changeOwner(((SiegeClanObject)object2).getClan());
        }
        this.stopEvent(true);
    }

    @Override
    public void announce(int n) {
        int n2 = n / 60;
        if (n2 > 0) {
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_CONTEST_WILL_BEGIN_IN_S1_MINUTES).addNumber(n2), "attackers", "defenders");
        } else {
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_PRELIMINARY_MATCH_WILL_BEGIN_IN_S1_SECONDS).addNumber(n), "attackers", "defenders");
        }
    }

    @Override
    public void stopEvent(boolean bl) {
        Clan clan = ((ClanHall)this.getResidence()).getOwner();
        if (clan != null) {
            if (this._oldOwner != clan) {
                clan.broadcastToOnlineMembers(PlaySound.SIEGE_VICTORY);
                clan.incReputation(1700, false, this.toString());
            }
            this.broadcastTo((L2GameServerPacket)((SystemMessage)new SystemMessage(SystemMsg.S1_CLAN_HAS_DEFEATED_S2).addString(clan.getName())).addResidenceName((Residence)this.getResidence()), "attackers", "defenders");
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_IS_FINISHED).addResidenceName((Residence)this.getResidence()), "attackers", "defenders");
        } else {
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_PRELIMINARY_MATCH_OF_S1_HAS_ENDED_IN_A_DRAW).addResidenceName((Residence)this.getResidence()), "attackers");
        }
        this.updateParticles(false, "attackers", "defenders");
        this.removeObjects("defenders");
        this.removeObjects("attackers");
        super.stopEvent(bl);
        this._oldOwner = null;
    }

    @Override
    public void loadSiegeClans() {
        List<SiegeClanObject> list = SiegeClanDAO.getInstance().load((Residence)this.getResidence(), "attackers");
        this.addObjects("attackers", list);
        List list2 = this.getObjects("attackers");
        for (CTBSiegeClanObject cTBSiegeClanObject : list2) {
            cTBSiegeClanObject.select((Residence)this.getResidence());
        }
    }

    @Override
    public CTBSiegeClanObject newSiegeClan(String string, int n, long l, long l2) {
        Clan clan = ClanTable.getInstance().getClan(n);
        return clan == null ? null : new CTBSiegeClanObject(string, clan, l, l2);
    }

    @Override
    public boolean isParticle(Player player) {
        if (!this.isInProgress() || player.getClan() == null) {
            return false;
        }
        CTBSiegeClanObject cTBSiegeClanObject = (CTBSiegeClanObject)this.getSiegeClan("attackers", player.getClan());
        return cTBSiegeClanObject != null && cTBSiegeClanObject.getPlayers().contains(player.getObjectId());
    }

    @Override
    public Location getRestartLoc(Player player, RestartType restartType) {
        if (!this.checkIfInZone(player)) {
            return null;
        }
        Object s = this.getSiegeClan("attackers", player.getClan());
        Location location = null;
        switch (restartType) {
            case TO_VILLAGE: {
                if (s == null || !this.checkIfInZone(player)) break;
                List list = this.getObjects("attackers");
                List list2 = this.getObjects(CHALLENGER_RESTART_POINTS);
                int n = list.indexOf(s);
                location = (Location)list2.get(n);
            }
        }
        return location;
    }

    @Override
    public void action(String string, boolean bl) {
        if (string.equalsIgnoreCase(NEXT_STEP)) {
            this.nextStep();
        } else {
            super.action(string, bl);
        }
    }

    @Override
    public int getUserRelation(Player player, int n) {
        return n;
    }

    @Override
    public int getRelation(Player player, Player player2, int n) {
        return n;
    }
}
