/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.sets.impl.TreeIntSet
 */
package l2.gameserver.model.entity.events.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TimeZone;
import l2.commons.collections.MultiValueSet;
import l2.commons.dao.JdbcEntityState;
import l2.commons.lang.ArrayUtils;
import l2.commons.time.cron.NextTime;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Config;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.napile.primitive.sets.impl.TreeIntSet;

public class ClanHallSiegeEvent
extends SiegeEvent<ClanHall, SiegeClanObject> {
    public static final String BOSS = "boss";
    private NextTime[] a = new NextTime[0];
    private final long ca;
    private final boolean dh;
    public static final long DAY_IN_MILISECONDS = 86400000L;

    public ClanHallSiegeEvent(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
        String string = multiValueSet.getString("siege_schedule");
        StringTokenizer stringTokenizer = new StringTokenizer(string, "|;");
        while (stringTokenizer.hasMoreTokens()) {
            this.a = ArrayUtils.add(this.a, new SchedulingPattern(stringTokenizer.nextToken()));
        }
        this.ca = multiValueSet.getLong("next_siege_date_set_delay", 86400L) * 1000L;
        this.dh = multiValueSet.getBool("next_siege_date_drop_owner", false);
    }

    @Override
    public void startEvent() {
        this._oldOwner = ((ClanHall)this.getResidence()).getOwner();
        if (this._oldOwner != null) {
            ((ClanHall)this.getResidence()).changeOwner(null);
            this.addObject("attackers", new SiegeClanObject("attackers", this._oldOwner, 0L));
        }
        if (this.getObjects("attackers").size() == 0) {
            this.broadcastInZone2(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_HAS_BEEN_CANCELED_DUE_TO_LACK_OF_INTEREST).addResidenceName((Residence)this.getResidence())});
            this.reCalcNextTime(false);
            return;
        }
        SiegeClanDAO.getInstance().delete((Residence)this.getResidence());
        this.updateParticles(true, "attackers");
        this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_TO_CONQUER_S1_HAS_BEGUN).addResidenceName((Residence)this.getResidence()), "attackers");
        super.startEvent();
    }

    @Override
    public void stopEvent(boolean bl) {
        Clan clan = ((ClanHall)this.getResidence()).getOwner();
        if (clan != null) {
            clan.broadcastToOnlineMembers(PlaySound.SIEGE_VICTORY);
            clan.incReputation(1700, false, this.toString());
            this.broadcastTo((L2GameServerPacket)((SystemMessage)new SystemMessage(SystemMsg.S1_CLAN_HAS_DEFEATED_S2).addString(clan.getName())).addResidenceName((Residence)this.getResidence()), "attackers");
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_IS_FINISHED).addResidenceName((Residence)this.getResidence()), "attackers");
        } else {
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_HAS_ENDED_IN_A_DRAW).addResidenceName((Residence)this.getResidence()), "attackers");
        }
        this.updateParticles(false, "attackers");
        this.removeObjects("attackers");
        super.stopEvent(bl);
        this._oldOwner = null;
    }

    @Override
    public void setRegistrationOver(boolean bl) {
        if (bl) {
            this.broadcastTo((L2GameServerPacket)new SystemMessage(SystemMsg.THE_DEADLINE_TO_REGISTER_FOR_THE_SIEGE_OF_S1_HAS_PASSED).addResidenceName((Residence)this.getResidence()), "attackers");
        }
        super.setRegistrationOver(bl);
    }

    @Override
    public void processStep(Clan clan) {
        if (clan != null) {
            ((ClanHall)this.getResidence()).changeOwner(clan);
        }
        this.stopEvent(true);
    }

    @Override
    public void reCalcNextTime(boolean bl) {
        this.clearActions();
        long l = System.currentTimeMillis();
        Calendar calendar = ((ClanHall)this.getResidence()).getSiegeDate();
        Calendar calendar2 = ((ClanHall)this.getResidence()).getOwnDate();
        if (bl) {
            if (calendar.getTimeInMillis() > l) {
                this.registerActions();
            } else if (calendar.getTimeInMillis() == 0L) {
                if (l - calendar2.getTimeInMillis() > this.ca) {
                    this.bA();
                } else {
                    this.generateNextSiegeDates();
                }
            } else if (calendar.getTimeInMillis() <= l) {
                this.bA();
            }
        } else if (((ClanHall)this.getResidence()).getOwner() != null) {
            if (this.dh) {
                ((ClanHall)this.getResidence()).changeOwner(null);
            }
            ((ClanHall)this.getResidence()).getSiegeDate().setTimeInMillis(0L);
            ((ClanHall)this.getResidence()).setJdbcState(JdbcEntityState.UPDATED);
            ((ClanHall)this.getResidence()).update();
            this.generateNextSiegeDates();
        } else {
            this.bA();
        }
    }

    public void generateNextSiegeDates() {
        if (((ClanHall)this.getResidence()).getSiegeDate().getTimeInMillis() != 0L) {
            return;
        }
        Calendar calendar = (Calendar)Config.CASTLE_VALIDATION_DATE.clone();
        calendar.set(7, 1);
        if (calendar.before(Config.CASTLE_VALIDATION_DATE)) {
            calendar.add(3, 1);
        }
        TreeIntSet treeIntSet = new TreeIntSet();
        for (NextTime nextTime : this.a) {
            treeIntSet.add((int)(this.scheduleNextTime(calendar.getTimeInMillis(), nextTime) / 1000L));
        }
    }

    protected long scheduleNextTime(long l, NextTime nextTime) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
        gregorianCalendar.setTimeInMillis(l);
        gregorianCalendar.set(13, 0);
        gregorianCalendar.set(14, 0);
        long l2 = gregorianCalendar.getTimeInMillis();
        while (l2 < System.currentTimeMillis()) {
            l2 = nextTime.next(l2);
        }
        return l2;
    }

    private void bA() {
        Calendar calendar = (Calendar)Config.CASTLE_VALIDATION_DATE.clone();
        calendar.set(7, 1);
        calendar.set(11, ((ClanHall)this.getResidence()).getLastSiegeDate().get(11));
        if (calendar.before(Config.CASTLE_VALIDATION_DATE)) {
            calendar.add(3, 1);
        }
        calendar.setTimeInMillis(this.scheduleNextTime(calendar.getTimeInMillis(), this.a[0]));
        this.b(calendar.getTimeInMillis());
    }

    private void b(long l) {
        this.clearActions();
        ((ClanHall)this.getResidence()).getSiegeDate().setTimeInMillis(l);
        ((ClanHall)this.getResidence()).setJdbcState(JdbcEntityState.UPDATED);
        ((ClanHall)this.getResidence()).update();
        this.registerActions();
    }

    @Override
    public void loadSiegeClans() {
        this.addObjects("attackers", SiegeClanDAO.getInstance().load((Residence)this.getResidence(), "attackers"));
    }

    @Override
    public int getUserRelation(Player player, int n) {
        return n;
    }

    @Override
    public int getRelation(Player player, Player player2, int n) {
        return n;
    }

    @Override
    public boolean canResurrect(Player player, Creature creature, boolean bl) {
        boolean bl2 = player.isInZone(Zone.ZoneType.SIEGE);
        boolean bl3 = creature.isInZone(Zone.ZoneType.SIEGE);
        if (!bl2 && !bl3) {
            return true;
        }
        if (!bl3) {
            return false;
        }
        Player player2 = creature.getPlayer();
        ClanHallSiegeEvent clanHallSiegeEvent = creature.getEvent(ClanHallSiegeEvent.class);
        if (clanHallSiegeEvent != this) {
            if (bl) {
                player2.sendPacket((IStaticPacket)SystemMsg.IT_IS_NOT_POSSIBLE_TO_RESURRECT_IN_BATTLEFIELDS_WHERE_A_SIEGE_WAR_IS_TAKING_PLACE);
            }
            player.sendPacket((IStaticPacket)(bl ? SystemMsg.IT_IS_NOT_POSSIBLE_TO_RESURRECT_IN_BATTLEFIELDS_WHERE_A_SIEGE_WAR_IS_TAKING_PLACE : SystemMsg.INVALID_TARGET));
            return false;
        }
        Object s = clanHallSiegeEvent.getSiegeClan("attackers", player2.getClan());
        if (((SiegeClanObject)s).getFlag() == null) {
            if (bl) {
                player2.sendPacket((IStaticPacket)SystemMsg.IF_A_BASE_CAMP_DOES_NOT_EXIST_RESURRECTION_IS_NOT_POSSIBLE);
            }
            player.sendPacket((IStaticPacket)(bl ? SystemMsg.IF_A_BASE_CAMP_DOES_NOT_EXIST_RESURRECTION_IS_NOT_POSSIBLE : SystemMsg.INVALID_TARGET));
            return false;
        }
        if (bl) {
            return true;
        }
        player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
        return false;
    }
}
