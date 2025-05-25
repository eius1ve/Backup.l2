/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import l2.commons.collections.LazyArrayList;
import l2.commons.collections.MultiValueSet;
import l2.commons.dao.JdbcEntityState;
import l2.commons.lang.reference.HardReference;
import l2.gameserver.Config;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.RestartType;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.events.objects.ZoneObject;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.SummonInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.templates.DoorTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.TimeUtils;

public abstract class SiegeEvent<R extends Residence, S extends SiegeClanObject>
extends GlobalEvent {
    public static final String OWNER = "owner";
    public static final String OLD_OWNER = "old_owner";
    public static final String ATTACKERS = "attackers";
    public static final String DEFENDERS = "defenders";
    public static final String SPECTATORS = "spectators";
    public static final String SIEGE_ZONES = "siege_zones";
    public static final String FLAG_ZONES = "flag_zones";
    public static final String DAY_OF_WEEK = "day_of_week";
    public static final String HOUR_OF_DAY = "hour_of_day";
    public static final String REGISTRATION = "registration";
    public static final String DOORS = "doors";
    protected R _residence;
    private boolean di;
    private boolean dj;
    protected int _dayOfWeek;
    protected int _hourOfDay;
    protected Clan _oldOwner;
    protected OnDeathListener _doorDeathListener = new DoorDeathListener();
    protected List<HardReference<SummonInstance>> _siegeSummons = new ArrayList<HardReference<SummonInstance>>();

    public SiegeEvent(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
        this._dayOfWeek = multiValueSet.getInteger(DAY_OF_WEEK, 0);
        this._hourOfDay = multiValueSet.getInteger(HOUR_OF_DAY, 0);
    }

    @Override
    public void startEvent() {
        this.setInProgress(true);
        super.startEvent();
    }

    @Override
    public final void stopEvent() {
        this.stopEvent(false);
    }

    public void stopEvent(boolean bl) {
        this.despawnSiegeSummons();
        this.setInProgress(false);
        this.reCalcNextTime(false);
        super.stopEvent();
    }

    public void processStep(Clan clan) {
    }

    @Override
    public void reCalcNextTime(boolean bl) {
        this.clearActions();
        Calendar calendar = ((Residence)this.getResidence()).getSiegeDate();
        if (bl) {
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.set(7, this._dayOfWeek);
                calendar.set(11, this._hourOfDay);
                this.validateSiegeDate(calendar, 2);
                ((Residence)this.getResidence()).setJdbcState(JdbcEntityState.UPDATED);
            }
        } else {
            calendar.add(3, 2);
            ((Residence)this.getResidence()).setJdbcState(JdbcEntityState.UPDATED);
        }
        this.registerActions();
        this.getResidence().update();
    }

    protected void validateSiegeDate(Calendar calendar, int n) {
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        while (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(3, n);
        }
    }

    @Override
    protected long startTimeMillis() {
        return ((Residence)this.getResidence()).getSiegeDate().getTimeInMillis();
    }

    @Override
    public void teleportPlayers(String string) {
        Location location;
        List<Object> list = new ArrayList();
        Clan clan = ((Residence)this.getResidence()).getOwner();
        if (string.equalsIgnoreCase(OWNER)) {
            if (clan != null) {
                for (Player player : this.getPlayersInZone()) {
                    if (player.getClan() != clan) continue;
                    list.add(player);
                }
            }
        } else if (string.equalsIgnoreCase(ATTACKERS)) {
            for (Player player : this.getPlayersInZone()) {
                location = (Location)this.getSiegeClan(ATTACKERS, player.getClan());
                if (location == null || !((SiegeClanObject)((Object)location)).isParticle(player)) continue;
                list.add(player);
            }
        } else if (string.equalsIgnoreCase(DEFENDERS)) {
            for (Player player : this.getPlayersInZone()) {
                if (clan != null && player.getClan() != null && player.getClan() == clan || (location = this.getSiegeClan(DEFENDERS, player.getClan())) == null || !((SiegeClanObject)((Object)location)).isParticle(player)) continue;
                list.add(player);
            }
        } else if (string.equalsIgnoreCase(SPECTATORS)) {
            for (Player player : this.getPlayersInZone()) {
                if (clan != null && player.getClan() != null && player.getClan() == clan || player.getClan() != null && (this.getSiegeClan(ATTACKERS, player.getClan()) != null || this.getSiegeClan(DEFENDERS, player.getClan()) != null)) continue;
                list.add(player);
            }
        } else {
            list = this.getPlayersInZone();
        }
        for (Player player : list) {
            location = null;
            location = string.equalsIgnoreCase(OWNER) || string.equalsIgnoreCase(DEFENDERS) ? ((Residence)this.getResidence()).getOwnerRestartPoint() : ((Residence)this.getResidence()).getNotOwnerRestartPoint(player);
            player.teleToLocation(location, ReflectionManager.DEFAULT);
        }
    }

    public List<Player> getPlayersInZone() {
        List list = this.getObjects(SIEGE_ZONES);
        LazyArrayList<Player> lazyArrayList = new LazyArrayList<Player>();
        for (ZoneObject zoneObject : list) {
            lazyArrayList.addAll(zoneObject.getInsidePlayers());
        }
        return lazyArrayList;
    }

    public void broadcastInZone(L2GameServerPacket ... l2GameServerPacketArray) {
        for (Player player : this.getPlayersInZone()) {
            player.sendPacket(l2GameServerPacketArray);
        }
    }

    public void broadcastInZone(IStaticPacket ... iStaticPacketArray) {
        for (Player player : this.getPlayersInZone()) {
            player.sendPacket(iStaticPacketArray);
        }
    }

    public boolean checkIfInZone(Creature creature) {
        List list = this.getObjects(SIEGE_ZONES);
        for (ZoneObject zoneObject : list) {
            if (!zoneObject.checkIfInZone(creature)) continue;
            return true;
        }
        return false;
    }

    public void broadcastInZone2(IStaticPacket ... iStaticPacketArray) {
        for (Player player : ((Residence)this.getResidence()).getZone().getInsidePlayers()) {
            player.sendPacket(iStaticPacketArray);
        }
    }

    public void broadcastInZone2(L2GameServerPacket ... l2GameServerPacketArray) {
        for (Player player : ((Residence)this.getResidence()).getZone().getInsidePlayers()) {
            player.sendPacket(l2GameServerPacketArray);
        }
    }

    public void loadSiegeClans() {
        this.addObjects(ATTACKERS, SiegeClanDAO.getInstance().load((Residence)this.getResidence(), ATTACKERS));
        this.addObjects(DEFENDERS, SiegeClanDAO.getInstance().load((Residence)this.getResidence(), DEFENDERS));
    }

    public S newSiegeClan(String string, int n, long l, long l2) {
        Clan clan = ClanTable.getInstance().getClan(n);
        return (S)(clan == null ? null : new SiegeClanObject(string, clan, l, l2));
    }

    public void updateParticles(boolean bl, String ... stringArray) {
        for (String string : stringArray) {
            List list = this.getObjects(string);
            for (SiegeClanObject siegeClanObject : list) {
                siegeClanObject.setEvent(bl, this);
            }
        }
    }

    public S getSiegeClan(String string, Clan clan) {
        if (clan == null) {
            return null;
        }
        return this.getSiegeClan(string, clan.getClanId());
    }

    public S getSiegeClan(String string, int n) {
        List list = this.getObjects(string);
        if (list.isEmpty()) {
            return null;
        }
        for (int i = 0; i < list.size(); ++i) {
            SiegeClanObject siegeClanObject = (SiegeClanObject)list.get(i);
            if (siegeClanObject.getObjectId() != n) continue;
            return (S)siegeClanObject;
        }
        return null;
    }

    public void broadcastTo(IStaticPacket iStaticPacket, String ... stringArray) {
        for (String string : stringArray) {
            List list = this.getObjects(string);
            for (SiegeClanObject siegeClanObject : list) {
                siegeClanObject.broadcast(iStaticPacket);
            }
        }
    }

    public void broadcastTo(L2GameServerPacket l2GameServerPacket, String ... stringArray) {
        for (String string : stringArray) {
            List list = this.getObjects(string);
            for (SiegeClanObject siegeClanObject : list) {
                siegeClanObject.broadcast(l2GameServerPacket);
            }
        }
    }

    @Override
    public void initEvent() {
        this._residence = ResidenceHolder.getInstance().getResidence(this.getId());
        this.loadSiegeClans();
        this.clearActions();
        super.initEvent();
    }

    @Override
    protected void printInfo() {
        long l = this.startTimeMillis();
        if (l == 0L) {
            this.info(this.getName() + " time - undefined");
        } else {
            this.info(this.getName() + " time - " + TimeUtils.toSimpleFormat(l));
        }
    }

    @Override
    public boolean ifVar(String string) {
        if (string.equals(OWNER)) {
            return ((Residence)this.getResidence()).getOwner() != null;
        }
        if (string.equals(OLD_OWNER)) {
            return this._oldOwner != null;
        }
        return false;
    }

    @Override
    public boolean isParticle(Player player) {
        if (!this.isInProgress() || player.getClan() == null) {
            return false;
        }
        return this.getSiegeClan(ATTACKERS, player.getClan()) != null || this.getSiegeClan(DEFENDERS, player.getClan()) != null;
    }

    @Override
    public void checkRestartLocs(Player player, Map<RestartType, Boolean> map) {
        if (this.getObjects(FLAG_ZONES).isEmpty()) {
            return;
        }
        S s = this.getSiegeClan(ATTACKERS, player.getClan());
        if (s != null && ((SiegeClanObject)s).getFlag() != null) {
            map.put(RestartType.TO_FLAG, Boolean.TRUE);
        }
    }

    @Override
    public Location getRestartLoc(Player player, RestartType restartType) {
        S s = this.getSiegeClan(ATTACKERS, player.getClan());
        Location location = null;
        switch (restartType) {
            case TO_FLAG: {
                if (!this.getObjects(FLAG_ZONES).isEmpty() && s != null && ((SiegeClanObject)s).getFlag() != null) {
                    location = Location.findPointToStay(((SiegeClanObject)s).getFlag(), 50, 75);
                    break;
                }
                player.sendPacket((IStaticPacket)SystemMsg.IF_A_BASE_CAMP_DOES_NOT_EXIST_RESURRECTION_IS_NOT_POSSIBLE);
            }
        }
        return location;
    }

    @Override
    public int getRelation(Player player, Player player2, int n) {
        Clan clan = player.getClan();
        Clan clan2 = player2.getClan();
        if (clan == null || clan2 == null) {
            return n;
        }
        SiegeEvent siegeEvent = player2.getEvent(SiegeEvent.class);
        if (this == siegeEvent) {
            n |= 0x200;
            S s = this.getSiegeClan(ATTACKERS, clan);
            S s2 = this.getSiegeClan(ATTACKERS, clan2);
            n = s == null && s2 == null || s != null && s2 != null && (s == s2 || this.isAttackersInAlly()) ? (n |= 0x800) : (n |= 0x1000);
            if (s != null) {
                n |= 0x400;
            }
        }
        return n;
    }

    @Override
    public int getUserRelation(Player player, int n) {
        n |= 0x80;
        S s = this.getSiegeClan(ATTACKERS, player.getClan());
        if (s != null) {
            n |= 0x100;
        }
        return n;
    }

    @Override
    public SystemMsg checkForAttack(Creature creature, Creature creature2, Skill skill, boolean bl) {
        SiegeEvent siegeEvent = creature.getEvent(SiegeEvent.class);
        if (this != siegeEvent) {
            return null;
        }
        if (!this.checkIfInZone(creature) || !this.checkIfInZone(creature2)) {
            return null;
        }
        Player player = creature.getPlayer();
        if (player == null) {
            return null;
        }
        S s = this.getSiegeClan(ATTACKERS, player.getClan());
        if (s == null && creature2.isSiegeGuard()) {
            return SystemMsg.INVALID_TARGET;
        }
        Player player2 = creature2.getPlayer();
        if (player2 == null) {
            return SystemMsg.INVALID_TARGET;
        }
        S s2 = this.getSiegeClan(ATTACKERS, player2.getClan());
        if (Config.ALLOW_TEMPORARILY_ALLY_ON_FIRST_SIEGE && (s == null && s2 == null || s != null && s2 != null && (s == s2 || this.isAttackersInAlly()))) {
            return SystemMsg.INVALID_TARGET;
        }
        if (s == null && s2 == null) {
            return SystemMsg.INVALID_TARGET;
        }
        return null;
    }

    @Override
    public boolean isInProgress() {
        return this.di;
    }

    @Override
    public void action(String string, boolean bl) {
        if (string.equalsIgnoreCase(REGISTRATION)) {
            this.setRegistrationOver(!bl);
        } else {
            super.action(string, bl);
        }
    }

    public boolean isAttackersInAlly() {
        return false;
    }

    @Override
    public List<Player> broadcastPlayers(int n) {
        return this.itemObtainPlayers();
    }

    @Override
    public List<Player> itemObtainPlayers() {
        List<Player> list = this.getPlayersInZone();
        LazyArrayList<Player> lazyArrayList = new LazyArrayList<Player>(list.size());
        for (Player player : this.getPlayersInZone()) {
            if (player.getEvent(this.getClass()) != this) continue;
            lazyArrayList.add(player);
        }
        return lazyArrayList;
    }

    public Location getEnterLoc(Player player) {
        S s = this.getSiegeClan(ATTACKERS, player.getClan());
        if (s != null) {
            if (((SiegeClanObject)s).getFlag() != null) {
                return Location.findAroundPosition(((SiegeClanObject)s).getFlag(), 50, 75);
            }
            return ((Residence)this.getResidence()).getNotOwnerRestartPoint(player);
        }
        return ((Residence)this.getResidence()).getOwnerRestartPoint();
    }

    public R getResidence() {
        return this._residence;
    }

    public void setInProgress(boolean bl) {
        this.di = bl;
    }

    public boolean isRegistrationOver() {
        return this.dj;
    }

    public void setRegistrationOver(boolean bl) {
        this.dj = bl;
    }

    public void addSiegeSummon(SummonInstance summonInstance) {
        this._siegeSummons.add(summonInstance.getRef());
    }

    public boolean containsSiegeSummon(SummonInstance summonInstance) {
        return this._siegeSummons.contains(summonInstance.getRef());
    }

    public void despawnSiegeSummons() {
        for (HardReference<SummonInstance> hardReference : this._siegeSummons) {
            SummonInstance summonInstance = hardReference.get();
            if (summonInstance == null) continue;
            summonInstance.unSummon();
        }
        this._siegeSummons.clear();
    }

    public class DoorDeathListener
    implements OnDeathListener {
        @Override
        public void onDeath(Creature creature, Creature creature2) {
            if (!SiegeEvent.this.isInProgress()) {
                return;
            }
            DoorInstance doorInstance = (DoorInstance)creature;
            if (doorInstance.getDoorType() == DoorTemplate.DoorType.WALL) {
                return;
            }
            SiegeEvent.this.broadcastTo(SystemMsg.THE_CASTLE_GATE_HAS_BEEN_DESTROYED, SiegeEvent.ATTACKERS, SiegeEvent.DEFENDERS);
        }
    }
}
