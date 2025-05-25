/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.napile.primitive.Containers
 *  org.napile.primitive.sets.IntSet
 *  org.napile.primitive.sets.impl.TreeIntSet
 */
package l2.gameserver.model.entity.events.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.Future;
import l2.commons.collections.MultiValueSet;
import l2.commons.dao.JdbcEntityState;
import l2.commons.lang.ArrayUtils;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.NextTime;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.dao.CastleDamageZoneDAO;
import l2.gameserver.dao.CastleDoorUpgradeDAO;
import l2.gameserver.dao.CastleHiredGuardDAO;
import l2.gameserver.dao.SiegeClanDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.RestartType;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.DoorObject;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.events.objects.SiegeToggleNpcObject;
import l2.gameserver.model.entity.events.objects.SpawnExObject;
import l2.gameserver.model.entity.events.objects.SpawnSimpleObject;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oneDayReward.requirement.BattleInCastleSiegeRequirement;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.residences.SiegeToggleNpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.taskmanager.DelayedItemsManager;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.support.MerchantGuard;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.TeleportUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.napile.primitive.Containers;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.TreeIntSet;

public class CastleSiegeEvent
extends SiegeEvent<Castle, SiegeClanObject> {
    public static final int MAX_SIEGE_CLANS = 20;
    public static final long DAY_IN_MILISECONDS = 86400000L;
    public static final String DEFENDERS_WAITING = "defenders_waiting";
    public static final String DEFENDERS_REFUSED = "defenders_refused";
    public static final String CONTROL_TOWERS = "control_towers";
    public static final String FLAME_TOWERS = "flame_towers";
    public static final String BOUGHT_ZONES = "bought_zones";
    public static final String GUARDS = "guards";
    public static final String HIRED_GUARDS = "hired_guards";
    private IntSet b = Containers.EMPTY_INT_SET;
    private Future<?> J = null;
    private boolean de = false;
    private NextTime[] a = new NextTime[0];
    private long bZ;
    private boolean df;
    private List<Pair<ItemTemplate, Long>> bz = null;
    private List<Pair<ItemTemplate, Long>> bA = null;

    public CastleSiegeEvent(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
        Object object;
        Object object2;
        Object object3;
        String string = multiValueSet.getString("siege_schedule");
        StringTokenizer stringTokenizer = new StringTokenizer(string, "|;");
        while (stringTokenizer.hasMoreTokens()) {
            this.a = ArrayUtils.add(this.a, new SchedulingPattern(stringTokenizer.nextToken()));
        }
        String string2 = multiValueSet.getString("on_siege_end_attacker_owned_leader_reward", null);
        if (string2 != null && !string2.isEmpty()) {
            object3 = new ArrayList();
            object2 = new StringTokenizer(string2, ";");
            while (((StringTokenizer)object2).hasMoreTokens()) {
                object = ((StringTokenizer)object2).nextToken();
                int n = StringUtils.indexOf((CharSequence)object, (CharSequence)":");
                if (n <= 0) {
                    throw new IllegalArgumentException("Illformat: " + (String)object);
                }
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(StringUtils.trimToEmpty((String)((String)object).substring(0, n))));
                long l = Long.parseLong(StringUtils.trimToEmpty((String)((String)object).substring(n + 1)));
                object3.add(Pair.of((Object)itemTemplate, (Object)l));
            }
            this.bz = object3;
        }
        if ((object3 = multiValueSet.getString("on_siege_end_defender_owned_leader_reward", null)) != null && !((String)object3).isEmpty()) {
            object2 = new ArrayList();
            object = new StringTokenizer((String)object3, ";");
            while (((StringTokenizer)object).hasMoreTokens()) {
                String string3 = ((StringTokenizer)object).nextToken();
                int n = StringUtils.indexOf((CharSequence)string3, (CharSequence)":");
                if (n <= 0) {
                    throw new IllegalArgumentException("Illformat: " + string3);
                }
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(StringUtils.trimToEmpty((String)string3.substring(0, n))));
                long l = Long.parseLong(StringUtils.trimToEmpty((String)string3.substring(n + 1)));
                object2.add(Pair.of((Object)itemTemplate, (Object)l));
            }
            this.bA = object2;
        }
        this.bZ = multiValueSet.getLong("next_siege_date_set_delay", 86400L) * 1000L;
        this.df = multiValueSet.getBool("next_siege_date_drop_owner", false);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        List list = this.getObjects("doors");
        this.addObjects(BOUGHT_ZONES, CastleDamageZoneDAO.getInstance().load((Residence)this.getResidence()));
        for (DoorObject doorObject : list) {
            doorObject.setUpgradeValue(this, CastleDoorUpgradeDAO.getInstance().load(doorObject.getUId()));
            doorObject.getDoor().addListener(this._doorDeathListener);
        }
    }

    @Override
    public void processStep(Clan clan) {
        List list;
        Object s;
        Clan clan2 = ((Castle)this.getResidence()).getOwner();
        ((Castle)this.getResidence()).changeOwner(clan);
        if (clan2 != null) {
            s = this.getSiegeClan("defenders", clan2);
            this.removeObject("defenders", (Serializable)s);
            ((SiegeClanObject)s).setType("attackers");
            this.addObject("attackers", (Serializable)s);
        } else {
            if (this.getObjects("attackers").size() == 1) {
                this.stopEvent();
                return;
            }
            int n = clan.getAllyId();
            if (n > 0) {
                list = this.getObjects("attackers");
                boolean bl = true;
                for (Serializable serializable : list) {
                    if (serializable == null || ((SiegeClanObject)serializable).getClan().getAllyId() == n) continue;
                    bl = false;
                }
                if (bl) {
                    this.stopEvent();
                    return;
                }
            }
        }
        s = this.getSiegeClan("attackers", clan);
        ((SiegeClanObject)s).deleteFlag();
        ((SiegeClanObject)s).setType("defenders");
        this.removeObject("attackers", (Serializable)s);
        list = this.removeObjects("defenders");
        for (Iterator iterator : list) {
            ((SiegeClanObject)((Object)iterator)).setType("attackers");
        }
        this.addObject("defenders", (Serializable)s);
        this.addObjects("attackers", list);
        this.updateParticles(true, "attackers", "defenders");
        this.teleportPlayers("attackers");
        this.teleportPlayers("spectators");
        if (!this.de) {
            Iterator iterator;
            this.de = true;
            this.broadcastTo(SystemMsg.THE_TEMPORARY_ALLIANCE_OF_THE_CASTLE_ATTACKER_TEAM_HAS_BEEN_DISSOLVED, "attackers", "defenders");
            if (this._oldOwner != null) {
                this.spawnAction(HIRED_GUARDS, false);
                this.d(false);
                this.removeObjects(HIRED_GUARDS);
                this.removeObjects(BOUGHT_ZONES);
                CastleDamageZoneDAO.getInstance().delete((Residence)this.getResidence());
            } else {
                this.spawnAction(GUARDS, false);
            }
            List list2 = this.getObjects("doors");
            iterator = list2.iterator();
            while (iterator.hasNext()) {
                Serializable serializable;
                serializable = (DoorObject)iterator.next();
                ((DoorObject)serializable).setWeak(true);
                ((DoorObject)serializable).setUpgradeValue(this, 0);
                CastleDoorUpgradeDAO.getInstance().delete(((DoorObject)serializable).getUId());
            }
        }
        this.spawnAction("doors", true);
        this.spawnAction(CONTROL_TOWERS, true);
        this.spawnAction(FLAME_TOWERS, true);
        this.despawnSiegeSummons();
    }

    @Override
    public void startEvent() {
        Object object;
        this._oldOwner = ((Castle)this.getResidence()).getOwner();
        if (this._oldOwner != null) {
            this.addObject("defenders", new SiegeClanObject("defenders", this._oldOwner, 0L));
            if (((Castle)this.getResidence()).getSpawnMerchantTickets().size() > 0) {
                object = ((Castle)this.getResidence()).getSpawnMerchantTickets().iterator();
                while (object.hasNext()) {
                    ItemInstance itemInstance = (ItemInstance)object.next();
                    MerchantGuard merchantGuard = ((Castle)this.getResidence()).getMerchantGuard(itemInstance.getItemId());
                    this.addObject(HIRED_GUARDS, new SpawnSimpleObject(merchantGuard.getNpcId(), itemInstance.getLoc()));
                    itemInstance.deleteMe();
                }
                CastleHiredGuardDAO.getInstance().delete((Residence)this.getResidence());
                this.spawnAction(HIRED_GUARDS, true);
            }
        }
        if ((object = this.getObjects("attackers")).isEmpty()) {
            if (this._oldOwner == null) {
                this.broadcastToWorld((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_HAS_BEEN_CANCELED_DUE_TO_LACK_OF_INTEREST).addResidenceName((Residence)this.getResidence()));
            } else {
                this.broadcastToWorld((L2GameServerPacket)new SystemMessage(SystemMsg.S1S_SIEGE_WAS_CANCELED_BECAUSE_THERE_WERE_NO_CLANS_THAT_PARTICIPATED).addResidenceName((Residence)this.getResidence()));
            }
            this.reCalcNextTime(false);
            return;
        }
        SiegeClanDAO.getInstance().delete((Residence)this.getResidence());
        this.updateParticles(true, "attackers", "defenders");
        this.broadcastTo(SystemMsg.THE_TEMPORARY_ALLIANCE_OF_THE_CASTLE_ATTACKER_TEAM_IS_IN_EFFECT, "attackers");
        this.f("attackers");
        this.f("defenders");
        super.startEvent();
        if (this._oldOwner == null) {
            this.bz();
        } else {
            this.d(true);
        }
    }

    private void f(String string) {
        List list = this.getObjects(string);
        for (SiegeClanObject siegeClanObject : list) {
            Clan clan = siegeClanObject.getClan();
            if (clan == null) continue;
            for (Player player : clan.getOnlineMembers(0)) {
                OneDayRewardHolder.getInstance().fireRequirements(player, null, BattleInCastleSiegeRequirement.class);
            }
        }
    }

    @Override
    public void stopEvent(boolean bl) {
        Object object;
        Object object22;
        List list = this.getObjects("doors");
        for (Object object22 : list) {
            ((DoorObject)object22).setWeak(false);
        }
        this.d(false);
        this.updateParticles(false, "attackers", "defenders");
        List list2 = this.removeObjects("attackers");
        object22 = list2.iterator();
        while (object22.hasNext()) {
            object = (SiegeClanObject)object22.next();
            ((SiegeClanObject)object).deleteFlag();
        }
        this.broadcastToWorld((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_IS_FINISHED).addResidenceName((Residence)this.getResidence()));
        this.removeObjects("defenders");
        this.removeObjects(DEFENDERS_WAITING);
        this.removeObjects(DEFENDERS_REFUSED);
        object22 = ((Castle)this.getResidence()).getOwner();
        if (object22 != null) {
            if (this._oldOwner == object22) {
                ((Clan)object22).broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.SINCE_YOUR_CLAN_EMERGED_VICTORIOUS_FROM_THE_SIEGE_S1_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addNumber(((Clan)object22).incReputation(Config.SIEGE_CLAN_REPUTATION_REWARD, false, this.toString()))});
                if (Config.EVENT_CLAN_CASTLE_POINTS.containsKey(((Castle)this.getResidence()).getId())) {
                    ((Clan)object22).setCustomPoints(((Clan)object22).getCustomPoints() + Config.EVENT_CLAN_CASTLE_POINTS.get(((Castle)this.getResidence()).getId()));
                    if (Config.SERVICES_TOP_CLANS_STATISTIC_ANNOUNCE) {
                        Announcements.getInstance().announceByCustomMessage("scripts.services.CustomClanPointsCastle", new String[]{String.valueOf(((Clan)object22).getName()), String.valueOf(Config.EVENT_CLAN_CASTLE_POINTS.get(((Castle)this.getResidence()).getId()))});
                    }
                }
                object = ((Clan)object22).getLeader();
                if (this.bA != null && !this.bA.isEmpty()) {
                    Player player = World.getPlayer(((UnitMember)object).getObjectId());
                    if (player != null && player.isOnline()) {
                        for (Pair<ItemTemplate, Long> pair : this.bA) {
                            ItemFunctions.addItem((Playable)player, (ItemTemplate)pair.getLeft(), (long)((Long)pair.getRight()), true);
                            Log.LogItem(player, Log.ItemLog.PostSend, ((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()));
                        }
                    } else {
                        for (Pair<ItemTemplate, Long> pair : this.bA) {
                            DelayedItemsManager.getInstance().addDelayed(((UnitMember)object).getObjectId(), ((ItemTemplate)pair.getLeft()).getItemId(), ((Long)pair.getRight()).intValue(), 0, 0, 0, "Siege owner Castle Defeated leader reward item " + pair.getLeft() + "(" + pair.getRight() + ")");
                        }
                    }
                }
            } else {
                this.broadcastToWorld((L2GameServerPacket)((SystemMessage)new SystemMessage(SystemMsg.CLAN_S1_IS_VICTORIOUS_OVER_S2S_CASTLE_SIEGE).addString(((Clan)object22).getName())).addResidenceName((Residence)this.getResidence()));
                ((Clan)object22).broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.SINCE_YOUR_CLAN_EMERGED_VICTORIOUS_FROM_THE_SIEGE_S1_POINTS_HAVE_BEEN_ADDED_TO_YOUR_CLANS_REPUTATION_SCORE).addNumber(((Clan)object22).incReputation(Config.SIEGE_CLAN_REPUTATION_DEFEND_REWARD, false, this.toString()))});
                if (Config.EVENT_CLAN_CASTLE_POINTS.containsKey(((Castle)this.getResidence()).getId())) {
                    ((Clan)object22).setCustomPoints(((Clan)object22).getCustomPoints() + Config.EVENT_CLAN_CASTLE_POINTS.get(((Castle)this.getResidence()).getId()));
                    if (Config.SERVICES_TOP_CLANS_STATISTIC_ANNOUNCE) {
                        Announcements.getInstance().announceByCustomMessage("scripts.services.CustomClanPointsCastle", new String[]{String.valueOf(((Clan)object22).getName()), String.valueOf(Config.EVENT_CLAN_CASTLE_POINTS.get(((Castle)this.getResidence()).getId()))});
                    }
                }
                if (this._oldOwner != null) {
                    this._oldOwner.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.YOUR_CLAN_HAS_FAILED_TO_DEFEND_THE_CASTLE_S1_POINTS_HAVE_BEEN_DEDUCTED_FROM_YOU_CLAN_REPUTATION_SCORE_AND_ADDED_TO_YOUR_OPPONENTS).addNumber(-this._oldOwner.incReputation(Config.SIEGE_CLAN_REPUTATION_DEFEND_CONSUME, false, this.toString()))});
                }
                object = ((Clan)object22).getLeader();
                Object object3 = ((Clan)object22).iterator();
                while (object3.hasNext()) {
                    UnitMember unitMember = object3.next();
                    Pair<ItemTemplate, Long> pair = unitMember.getPlayer();
                    if (pair == null) continue;
                    pair.sendPacket((IStaticPacket)PlaySound.SIEGE_VICTORY);
                    if (!pair.isOnline() || !pair.isNoble()) continue;
                    HeroController.getInstance().addHeroDiary(pair.getObjectId(), 3, ((Castle)this.getResidence()).getId());
                }
                if (this.bz != null && !this.bz.isEmpty()) {
                    object3 = World.getPlayer(((UnitMember)object).getObjectId());
                    if (object3 != null && ((Player)object3).isOnline()) {
                        for (Pair<ItemTemplate, Long> pair : this.bz) {
                            ItemFunctions.addItem((Playable)object3, (ItemTemplate)pair.getLeft(), (long)((Long)pair.getRight()), true);
                            Log.LogItem((Player)object3, Log.ItemLog.PostSend, ((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()));
                        }
                    } else {
                        for (Pair<ItemTemplate, Long> pair : this.bz) {
                            DelayedItemsManager.getInstance().addDelayed(((UnitMember)object).getObjectId(), ((ItemTemplate)pair.getLeft()).getItemId(), ((Long)pair.getRight()).intValue(), 0, 0, 0, "End siege owner leader reward item " + pair.getLeft() + "(" + pair.getRight() + ")");
                        }
                    }
                }
            }
            ((Castle)this.getResidence()).getOwnDate().setTimeInMillis(System.currentTimeMillis());
            ((Castle)this.getResidence()).getLastSiegeDate().setTimeInMillis(((Castle)this.getResidence()).getSiegeDate().getTimeInMillis());
        } else {
            this.broadcastToWorld((L2GameServerPacket)new SystemMessage(SystemMsg.THE_SIEGE_OF_S1_HAS_ENDED_IN_A_DRAW).addResidenceName((Residence)this.getResidence()));
            ((Castle)this.getResidence()).getOwnDate().setTimeInMillis(0L);
            ((Castle)this.getResidence()).getLastSiegeDate().setTimeInMillis(0L);
        }
        this.despawnSiegeSummons();
        if (this._oldOwner != null) {
            this.spawnAction(HIRED_GUARDS, false);
            this.removeObjects(HIRED_GUARDS);
        }
        super.stopEvent(bl);
    }

    @Override
    public void reCalcNextTime(boolean bl) {
        this.clearActions();
        long l = System.currentTimeMillis();
        Calendar calendar = ((Castle)this.getResidence()).getSiegeDate();
        Calendar calendar2 = ((Castle)this.getResidence()).getOwnDate();
        if (bl) {
            if (calendar.getTimeInMillis() > l) {
                this.registerActions();
            } else if (calendar.getTimeInMillis() == 0L) {
                if (l - calendar2.getTimeInMillis() > this.bZ) {
                    this.bA();
                } else {
                    this.generateNextSiegeDates();
                }
            } else if (calendar.getTimeInMillis() <= l) {
                this.bA();
            }
        } else if (((Castle)this.getResidence()).getOwner() != null) {
            if (this.df) {
                ((Castle)this.getResidence()).changeOwner(null);
            }
            ((Castle)this.getResidence()).getSiegeDate().setTimeInMillis(0L);
            ((Castle)this.getResidence()).setJdbcState(JdbcEntityState.UPDATED);
            ((Castle)this.getResidence()).update();
            this.generateNextSiegeDates();
        } else {
            this.bA();
        }
    }

    @Override
    public void loadSiegeClans() {
        super.loadSiegeClans();
        this.addObjects(DEFENDERS_WAITING, SiegeClanDAO.getInstance().load((Residence)this.getResidence(), DEFENDERS_WAITING));
        this.addObjects(DEFENDERS_REFUSED, SiegeClanDAO.getInstance().load((Residence)this.getResidence(), DEFENDERS_REFUSED));
    }

    @Override
    public void setRegistrationOver(boolean bl) {
        if (bl) {
            this.broadcastToWorld((L2GameServerPacket)new SystemMessage(SystemMsg.THE_DEADLINE_TO_REGISTER_FOR_THE_SIEGE_OF_S1_HAS_PASSED).addResidenceName((Residence)this.getResidence()));
        }
        super.setRegistrationOver(bl);
    }

    @Override
    public void announce(int n) {
        int n2 = n / 60;
        int n3 = n2 / 60;
        SystemMessage systemMessage = n3 > 0 ? (SystemMessage)new SystemMessage(SystemMsg.S1_HOURS_UNTIL_CASTLE_SIEGE_CONCLUSION).addNumber(n3) : (n2 > 0 ? (SystemMessage)new SystemMessage(SystemMsg.S1_MINUTES_UNTIL_CASTLE_SIEGE_CONCLUSION).addNumber(n2) : (SystemMessage)new SystemMessage(SystemMsg.THIS_CASTLE_SIEGE_WILL_END_IN_S1_SECONDS).addNumber(n));
        this.broadcastTo(systemMessage, "attackers", "defenders");
    }

    private void bz() {
        List list = this.getObjects(GUARDS);
        ArrayList<Spawner> arrayList = new ArrayList<Spawner>();
        for (Serializable serializable : list) {
            arrayList.addAll(((SpawnExObject)serializable).getSpawns());
        }
        List list2 = this.getObjects(CONTROL_TOWERS);
        for (Spawner spawner : arrayList) {
            Serializable serializable;
            Location location = spawner.getCurrentSpawnRange().getRandomLoc(ReflectionManager.DEFAULT.getGeoIndex());
            serializable = null;
            double d = 0.0;
            Iterator iterator = list2.iterator();
            while (iterator.hasNext()) {
                SiegeToggleNpcObject siegeToggleNpcObject = (SiegeToggleNpcObject)iterator.next();
                SiegeToggleNpcInstance siegeToggleNpcInstance = siegeToggleNpcObject.getToggleNpc();
                double d2 = siegeToggleNpcInstance.getDistance(location);
                if (serializable == null || d2 < d) {
                    serializable = siegeToggleNpcInstance;
                    d = d2;
                }
                ((SiegeToggleNpcInstance)serializable).register(spawner);
            }
        }
    }

    private void d(boolean bl) {
        this.zoneAction(BOUGHT_ZONES, bl);
    }

    private void bA() {
        Calendar calendar = (Calendar)Config.CASTLE_VALIDATION_DATE.clone();
        calendar.set(7, 1);
        calendar.set(11, ((Castle)this.getResidence()).getLastSiegeDate().get(11));
        if (calendar.before(Config.CASTLE_VALIDATION_DATE)) {
            calendar.add(3, 1);
        }
        calendar.setTimeInMillis(this.scheduleNextTime(calendar.getTimeInMillis(), this.a[0]));
        this.b(calendar.getTimeInMillis());
    }

    public void generateNextSiegeDates() {
        if (((Castle)this.getResidence()).getSiegeDate().getTimeInMillis() != 0L) {
            return;
        }
        Calendar calendar = (Calendar)Config.CASTLE_VALIDATION_DATE.clone();
        calendar.set(7, 1);
        if (calendar.before(Config.CASTLE_VALIDATION_DATE)) {
            calendar.add(3, 1);
        }
        this.b = new TreeIntSet();
        for (NextTime nextTime : this.a) {
            this.b.add((int)(this.scheduleNextTime(calendar.getTimeInMillis(), nextTime) / 1000L));
        }
        long l = ((Castle)this.getResidence()).getOwnDate().getTimeInMillis() + this.bZ - System.currentTimeMillis();
        this.J = ThreadPoolManager.getInstance().schedule(new NextSiegeDateSet(), l);
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

    public void setNextSiegeTime(int n) {
        if (!this.b.contains(n) || this.J == null) {
            return;
        }
        this.b = Containers.EMPTY_INT_SET;
        this.J.cancel(false);
        this.J = null;
        this.b((long)n * 1000L);
    }

    private void b(long l) {
        this.broadcastToWorld((L2GameServerPacket)new SystemMessage(SystemMsg.S1_HAS_ANNOUNCED_THE_NEXT_CASTLE_SIEGE_TIME).addResidenceName((Residence)this.getResidence()));
        this.clearActions();
        ((Castle)this.getResidence()).getSiegeDate().setTimeInMillis(l);
        ((Castle)this.getResidence()).setJdbcState(JdbcEntityState.UPDATED);
        ((Castle)this.getResidence()).update();
        this.registerActions();
    }

    @Override
    public boolean isAttackersInAlly() {
        return !this.de;
    }

    public int[] getNextSiegeTimes() {
        return this.b.toArray();
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
        CastleSiegeEvent castleSiegeEvent = creature.getEvent(CastleSiegeEvent.class);
        if (castleSiegeEvent != this) {
            if (bl) {
                player2.sendPacket((IStaticPacket)SystemMsg.IT_IS_NOT_POSSIBLE_TO_RESURRECT_IN_BATTLEFIELDS_WHERE_A_SIEGE_WAR_IS_TAKING_PLACE);
            }
            player.sendPacket((IStaticPacket)(bl ? SystemMsg.IT_IS_NOT_POSSIBLE_TO_RESURRECT_IN_BATTLEFIELDS_WHERE_A_SIEGE_WAR_IS_TAKING_PLACE : SystemMsg.INVALID_TARGET));
            return false;
        }
        Object s = castleSiegeEvent.getSiegeClan("attackers", player2.getClan());
        if (s == null) {
            s = castleSiegeEvent.getSiegeClan("defenders", player2.getClan());
        }
        if (((SiegeClanObject)s).getType() == "attackers") {
            if (((SiegeClanObject)s).getFlag() == null) {
                if (bl) {
                    player2.sendPacket((IStaticPacket)SystemMsg.IF_A_BASE_CAMP_DOES_NOT_EXIST_RESURRECTION_IS_NOT_POSSIBLE);
                }
                player.sendPacket((IStaticPacket)(bl ? SystemMsg.IF_A_BASE_CAMP_DOES_NOT_EXIST_RESURRECTION_IS_NOT_POSSIBLE : SystemMsg.INVALID_TARGET));
                return false;
            }
        } else {
            List list = this.getObjects(CONTROL_TOWERS);
            int n = 0;
            for (SiegeToggleNpcObject siegeToggleNpcObject : list) {
                if (siegeToggleNpcObject.isAlive()) continue;
                ++n;
            }
            if (n > 1) {
                if (bl) {
                    player2.sendPacket((IStaticPacket)SystemMsg.THE_GUARDIAN_TOWER_HAS_BEEN_DESTROYED_AND_RESURRECTION_IS_NOT_POSSIBLE);
                }
                player.sendPacket((IStaticPacket)(bl ? SystemMsg.THE_GUARDIAN_TOWER_HAS_BEEN_DESTROYED_AND_RESURRECTION_IS_NOT_POSSIBLE : SystemMsg.INVALID_TARGET));
                return false;
            }
        }
        return true;
    }

    @Override
    public Location getRestartLoc(Player player, RestartType restartType) {
        return switch (restartType) {
            case RestartType.TO_VILLAGE -> TeleportUtils.getRestartLocation(player, RestartType.TO_VILLAGE);
            default -> super.getRestartLoc(player, restartType);
        };
    }

    private class NextSiegeDateSet
    extends RunnableImpl {
        private NextSiegeDateSet() {
        }

        @Override
        public void runImpl() throws Exception {
            CastleSiegeEvent.this.bA();
        }
    }
}
