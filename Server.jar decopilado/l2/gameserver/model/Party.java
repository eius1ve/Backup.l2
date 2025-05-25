/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import l2.commons.collections.LazyArrayList;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.CommandChannel;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Territory;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.entity.DimensionalRift;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.SevenSignsFestival.DarknessFestival;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExAskModifyPartyLooting;
import l2.gameserver.network.l2.s2c.ExMPCCClose;
import l2.gameserver.network.l2.s2c.ExMPCCOpen;
import l2.gameserver.network.l2.s2c.ExPartyPetWindowAdd;
import l2.gameserver.network.l2.s2c.ExPartyPetWindowDelete;
import l2.gameserver.network.l2.s2c.ExSetPartyLooting;
import l2.gameserver.network.l2.s2c.ExTacticalSign;
import l2.gameserver.network.l2.s2c.GetItem;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PartyMemberPosition;
import l2.gameserver.network.l2.s2c.PartySmallWindowAdd;
import l2.gameserver.network.l2.s2c.PartySmallWindowAll;
import l2.gameserver.network.l2.s2c.PartySmallWindowDelete;
import l2.gameserver.network.l2.s2c.PartySmallWindowDeleteAll;
import l2.gameserver.network.l2.s2c.PartySpelled;
import l2.gameserver.network.l2.s2c.RelationChanged;
import l2.gameserver.network.l2.s2c.SetPartyDismiss;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Formulas;
import l2.gameserver.taskmanager.LazyPrecisionTaskManager;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;

public class Party
implements PlayerGroup {
    public static final int MAX_SIZE = 9;
    public static final int ITEM_LOOTER = 0;
    public static final int ITEM_RANDOM = 1;
    public static final int ITEM_RANDOM_SPOIL = 2;
    public static final int ITEM_ORDER = 3;
    public static final int ITEM_ORDER_SPOIL = 4;
    private final List<Player> aS = new CopyOnWriteArrayList<Player>();
    private int hr = 0;
    private int hs = 0;
    private int ht = 0;
    private int hu;
    private Reflection _reflection;
    private CommandChannel _commandChannel;
    public double _rateExp;
    public double _rateSp;
    public double _rateRaidExp;
    public double _rateRaidSp;
    public double _rateDrop;
    public double _rateAdena;
    public double _rateSpoil;
    public double _rateSealStones;
    private ScheduledFuture<?> Q;
    private int hv = -1;
    private long bi = 0L;
    private Set<Integer> l = null;
    private static final int[] aA = new int[]{487, 488, 798, 799, 800};
    private Future<?> k = null;
    private static final int[] aB = new int[]{0, 2664, 2665, 2666, 2667};
    private Map<Integer, HardReference<? extends Creature>> aC = new ConcurrentHashMap<Integer, HardReference<? extends Creature>>(1);

    public Party(Player player, int n) {
        this.hs = n;
        this.aS.add(player);
        this.hr = player.getLevel();
        this._rateExp = player.getBonus().getRateXp();
        this._rateSp = player.getBonus().getRateSp();
        this._rateRaidExp = player.getBonus().getRateRaidXp();
        this._rateRaidSp = player.getBonus().getRateRaidSp();
        this._rateAdena = player.getBonus().getDropAdena();
        this._rateDrop = player.getBonus().getDropItems();
        this._rateSealStones = player.getBonus().getDropSealStones();
        this._rateSpoil = player.getBonus().getDropSpoil();
    }

    public int getMemberCount() {
        return this.aS.size();
    }

    public int getMemberCountInRange(Player player, int n) {
        int n2 = 0;
        for (Player player2 : this.aS) {
            if (player2 != player && !player2.isInRangeZ(player, (long)n)) continue;
            ++n2;
        }
        return n2;
    }

    public List<Player> getPartyMembers() {
        return this.aS;
    }

    public List<Integer> getPartyMembersObjIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(this.aS.size());
        for (Player player : this.aS) {
            arrayList.add(player.getObjectId());
        }
        return arrayList;
    }

    public List<Playable> getPartyMembersWithPets() {
        ArrayList<Playable> arrayList = new ArrayList<Playable>();
        for (Player player : this.aS) {
            arrayList.add(player);
            if (player.getPet() == null) continue;
            arrayList.add(player.getPet());
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Player a(Player player, ItemInstance itemInstance, int n) {
        List<Player> list = this.aS;
        synchronized (list) {
            int n2 = this.aS.size();
            while (--n2 > 0) {
                Player player2;
                int n3 = this.ht++;
                if (this.ht > this.aS.size() - 1) {
                    this.ht = 0;
                }
                if ((player2 = n3 < this.aS.size() ? this.aS.get(n3) : player) == null || player2.isDead() || !player2.isInRangeZ(player, (long)n) || !player2.getInventory().validateCapacity(itemInstance) || !player2.getInventory().validateWeight(itemInstance)) continue;
                return player2;
            }
        }
        return player;
    }

    public boolean isLeader(Player player) {
        return this.getPartyLeader() == player;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Player getPartyLeader() {
        List<Player> list = this.aS;
        synchronized (list) {
            if (this.aS.size() == 0) {
                return null;
            }
            return this.aS.get(0);
        }
    }

    @Override
    public void broadCast(IStaticPacket ... iStaticPacketArray) {
        for (Player player : this.aS) {
            player.sendPacket(iStaticPacketArray);
        }
    }

    public void broadcastMessageToPartyMembers(String string) {
        this.broadCast(new IStaticPacket[]{new SystemMessage(SystemMsg.S1).addString(string)});
    }

    public void broadcastToPartyMembers(Player player, L2GameServerPacket l2GameServerPacket) {
        for (Player player2 : this.aS) {
            if (player == player2) continue;
            player2.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public void broadcastToPartyMembersInRange(Player player, L2GameServerPacket l2GameServerPacket, int n) {
        for (Player player2 : this.aS) {
            if (!player.isInRangeZ(player2, (long)n)) continue;
            player2.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public boolean containsMember(Player player) {
        return this.aS.contains(player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean addPartyMember(Player player) {
        Player player2 = this.getPartyLeader();
        if (player2 == null) {
            return false;
        }
        Object object = this.aS;
        synchronized (object) {
            if (this.aS.isEmpty()) {
                return false;
            }
            if (this.aS.contains(player)) {
                return false;
            }
            if (this.aS.size() == Config.ALT_MAX_PARTY_SIZE) {
                return false;
            }
            this.aS.add(player);
        }
        if (this.hv != -1) {
            this.c(false);
        }
        player.setParty(this);
        player.getListeners().onPartyInvite();
        ArrayList<L2GameServerPacket> arrayList = new ArrayList<L2GameServerPacket>(20);
        arrayList.add(new PartySmallWindowAll(this, player));
        arrayList.add((L2GameServerPacket)new SystemMessage(SystemMsg.YOU_HAVE_JOINED_S1S_PARTY).addName(player2));
        ArrayList<Object> arrayList2 = new ArrayList<Object>(4 + this.aS.size() * 4);
        arrayList2.add(new SystemMessage(SystemMsg.C1_HAS_JOINED_THE_PARTY).addName(player));
        arrayList2.add(new PartySmallWindowAdd(this, player));
        arrayList2.add(new PartySpelled(player, true));
        object = player.getPet();
        if (object != null) {
            arrayList2.add(new ExPartyPetWindowAdd((Summon)object));
            arrayList2.add(new PartySpelled((Playable)object, true));
        }
        PartyMemberPosition partyMemberPosition = new PartyMemberPosition();
        for (Player player3 : this.aS) {
            if (player3 == player) continue;
            ArrayList<Object> arrayList3 = new ArrayList<Object>(arrayList2.size() + 4);
            arrayList3.addAll(arrayList2);
            arrayList3.add(new PartyMemberPosition().add(player));
            arrayList3.add(new RelationChanged().add(player, player3));
            player3.sendPacket(arrayList3);
            arrayList.add(new PartySpelled(player3, true));
            object = player3.getPet();
            if (object != null) {
                arrayList.add(new PartySpelled((Playable)object, true));
                ((Summon)object).broadcastCharInfoImpl(player);
            }
            arrayList.add(new RelationChanged().add(player3, player));
            partyMemberPosition.add(player3);
        }
        arrayList.add(partyMemberPosition);
        if (this.isInCommandChannel()) {
            arrayList.add(ExMPCCOpen.STATIC);
        }
        player.sendPacket(arrayList);
        if (player.getPet() != null) {
            player.getPet().broadcastCharInfoImpl(this);
        }
        this.bf();
        this.recalculatePartyData();
        this.S(player);
        if (this.isInReflection() && this.getReflection() instanceof DimensionalRift) {
            ((DimensionalRift)this.getReflection()).partyMemberInvited();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dissolveParty() {
        for (Player player : this.aS) {
            player.sendPacket((IStaticPacket)PartySmallWindowDeleteAll.STATIC);
            player.sendPacket((IStaticPacket)SetPartyDismiss.STATIC);
            player.setParty(null);
            this.T(player);
        }
        List<Player> list = this.aS;
        synchronized (list) {
            this.aS.clear();
            this.aC.clear();
        }
        this.setDimensionalRift(null);
        this.setCommandChannel(null);
        this.bg();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removePartyMember(Player player, boolean bl) {
        Player player22;
        boolean bl2 = this.isLeader(player);
        boolean bl3 = false;
        List<Player> list = this.aS;
        synchronized (list) {
            if (!this.aS.remove(player)) {
                return false;
            }
            bl3 = this.aS.size() == 1;
        }
        player.getListeners().onPartyLeave();
        player.setParty(null);
        this.recalculatePartyData();
        list = new ArrayList<Player>(4 + this.aS.size() * 2);
        if (this.isInCommandChannel()) {
            list.add((Player)((Object)ExMPCCClose.STATIC));
        }
        if (bl) {
            list.add((Player)((Object)new SystemMessage(SystemMsg.YOU_HAVE_BEEN_EXPELLED_FROM_THE_PARTY)));
        } else {
            list.add((Player)((Object)new SystemMessage(SystemMsg.YOU_HAVE_WITHDRAWN_FROM_THE_PARTY)));
        }
        list.add((Player)((Object)PartySmallWindowDeleteAll.STATIC));
        list.add((Player)((Object)SetPartyDismiss.STATIC));
        ArrayList<L2GameServerPacket> arrayList = new ArrayList<L2GameServerPacket>(3);
        Summon summon = player.getPet();
        if (summon != null) {
            arrayList.add(new ExPartyPetWindowDelete(summon));
        }
        arrayList.add(new PartySmallWindowDelete(player));
        if (bl) {
            arrayList.add((L2GameServerPacket)new SystemMessage(SystemMsg.C1_WAS_EXPELLED_FROM_THE_PARTY).addName(player));
        } else {
            arrayList.add((L2GameServerPacket)new SystemMessage(SystemMsg.C1_HAS_LEFT_THE_PARTY).addName(player));
        }
        arrayList.add(SetPartyDismiss.STATIC);
        for (Player player22 : this.aS) {
            ArrayList<L2GameServerPacket> arrayList2 = new ArrayList<L2GameServerPacket>(2 + arrayList.size());
            arrayList2.addAll(arrayList);
            arrayList2.add(new RelationChanged().add(player, player22));
            player22.sendPacket(arrayList2);
            list.add((Player)((Object)new RelationChanged().add(player22, player)));
        }
        player.sendPacket(list);
        Reflection reflection = this.getReflection();
        if (reflection instanceof DarknessFestival) {
            ((DarknessFestival)reflection).partyMemberExited();
        } else if (this.isInReflection() && this.getReflection() instanceof DimensionalRift) {
            ((DimensionalRift)this.getReflection()).partyMemberExited(player);
        }
        if (reflection != null && player.getReflection() == reflection && reflection.getReturnLoc() != null) {
            player.teleToLocation(reflection.getReturnLoc(), ReflectionManager.DEFAULT);
        }
        player22 = this.getPartyLeader();
        if (bl3) {
            if (this.isInCommandChannel()) {
                this._commandChannel.removeParty(this);
            } else if (reflection != null && reflection.getInstancedZone() != null && reflection.getInstancedZone().isCollapseOnPartyDismiss()) {
                if (reflection.getParty() == this) {
                    reflection.startCollapseTimer(reflection.getInstancedZone().getTimerOnCollapse() * 1000);
                }
                if (player22 != null && player22.getReflection() == reflection) {
                    player22.broadcastPacket(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THIS_DUNGEON_WILL_EXPIRE_IN_S1_MINUTES).addNumber(1)});
                }
            }
            this.T(player);
            this.dissolveParty();
        } else {
            if (this.isInCommandChannel() && this._commandChannel.getChannelLeader() == player) {
                this._commandChannel.setChannelLeader(player22);
            }
            if (bl2) {
                this.be();
            }
            this.T(player);
        }
        if (this.k != null) {
            this.k.cancel(true);
            this.k = null;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean changePartyLeader(Player player) {
        Player player2 = this.getPartyLeader();
        List<Player> list = this.aS;
        synchronized (list) {
            int n = this.aS.indexOf(player);
            if (n == -1) {
                return false;
            }
            this.aS.set(0, player);
            this.aS.set(n, player2);
        }
        this.be();
        if (this.isInCommandChannel() && this._commandChannel.getChannelLeader() == player2) {
            this._commandChannel.setChannelLeader(player);
        }
        return true;
    }

    private void be() {
        Player player = this.getPartyLeader();
        if (player == null) {
            return;
        }
        SystemMessage systemMessage = (SystemMessage)new SystemMessage(SystemMsg.C1_HAS_BECOME_THE_PARTY_LEADER).addName(player);
        for (Player player2 : this.aS) {
            player2.sendPacket(PartySmallWindowDeleteAll.STATIC, new PartySmallWindowAll(this, player2), systemMessage);
        }
        for (Player player2 : this.aS) {
            this.broadcastToPartyMembers(player2, new PartySpelled(player2, true));
            if (player2.getPet() == null) continue;
            this.broadCast(new ExPartyPetWindowAdd(player2.getPet()));
        }
    }

    public Player getPlayerByName(String string) {
        for (Player player : this.aS) {
            if (!string.equalsIgnoreCase(player.getName())) continue;
            return player;
        }
        return null;
    }

    public void distributeItem(Player player, ItemInstance itemInstance, NpcInstance npcInstance) {
        switch (itemInstance.getItemId()) {
            case 57: {
                this.b(player, itemInstance, npcInstance);
                break;
            }
            default: {
                this.a(player, itemInstance, npcInstance);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(Player player, ItemInstance itemInstance, NpcInstance npcInstance) {
        Player player2 = null;
        List<Object> list = null;
        switch (this.hs) {
            case 1: 
            case 2: {
                list = new ArrayList(this.aS.size());
                for (Player player3 : this.aS) {
                    if (!player3.isInRangeZ(player, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) || player3.isDead() || !player3.getInventory().validateCapacity(itemInstance) || !player3.getInventory().validateWeight(itemInstance)) continue;
                    list.add(player3);
                }
                player2 = list.isEmpty() ? null : (Player)list.get(Rnd.get(list.size()));
                break;
            }
            case 3: 
            case 4: {
                List<Player> list2 = this.aS;
                synchronized (list2) {
                    list = new CopyOnWriteArrayList<Player>(this.aS);
                    while (player2 == null && !list.isEmpty()) {
                        Player player4;
                        int n = this.ht++;
                        if (this.ht > list.size() - 1) {
                            this.ht = 0;
                        }
                        if ((player4 = n < list.size() ? (Player)list.get(n) : null) == null) continue;
                        if (!player4.isDead() && player4.isInRangeZ(player, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) && ItemFunctions.canAddItem(player4, itemInstance)) {
                            player2 = player4;
                            continue;
                        }
                        list.remove(player4);
                    }
                }
                if (player2 != null) break;
                return;
            }
            default: {
                player2 = player;
            }
        }
        if (player2 == null) {
            player2 = player;
        }
        if (player2.pickupItem(itemInstance, Log.ItemLog.PartyPickup)) {
            if (npcInstance == null) {
                player.broadcastPacket(new GetItem(itemInstance, player.getObjectId()));
            }
            player.broadcastPickUpMsg(itemInstance);
            itemInstance.pickupMe();
            this.broadcastToPartyMembers(player2, SystemMessage.obtainItemsBy(itemInstance, player2));
        } else {
            itemInstance.dropToTheGround(player, npcInstance);
        }
    }

    private void b(Player player, ItemInstance itemInstance, NpcInstance npcInstance) {
        if (player == null) {
            return;
        }
        ArrayList<Player> arrayList = new ArrayList<Player>();
        if (itemInstance.getCount() < (long)this.aS.size()) {
            arrayList.add(player);
        } else {
            for (Player player2 : this.aS) {
                if (player2.isDead() || player2 != player && !player.isInRangeZ(player2, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) || !ItemFunctions.canAddItem(player, itemInstance)) continue;
                arrayList.add(player2);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(player);
        }
        long l = itemInstance.getCount();
        long l2 = l / (long)arrayList.size();
        long l3 = l % (long)arrayList.size();
        for (Player player3 : arrayList) {
            long l4 = player3.equals(player) ? l2 + l3 : l2;
            player3.getInventory().addAdena(l4);
            player3.getListeners().onItemPickup(itemInstance);
            player3.sendPacket((IStaticPacket)SystemMessage.obtainItems(57, l4, 0));
        }
        if (npcInstance == null) {
            player.broadcastPacket(new GetItem(itemInstance, player.getObjectId()));
        }
        itemInstance.pickupMe();
    }

    public void distributeXpAndSp(double d, double d2, List<Player> list, Creature creature, MonsterInstance monsterInstance) {
        this.recalculatePartyData();
        ArrayList<Player> arrayList = new ArrayList<Player>();
        int n = creature.getLevel();
        int n2 = 0;
        for (Player player : list) {
            if (!monsterInstance.isInRangeZ(player, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE)) continue;
            n = Math.max(n, player.getLevel());
        }
        for (Player player : list) {
            if (!monsterInstance.isInRangeZ(player, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) || player.getLevel() <= n - Config.ALT_PARTY_DISTRIBUTION_DIFF_LEVEL_LIMIT) continue;
            n2 += player.getLevel();
            arrayList.add(player);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        double d3 = Config.ALT_PARTY_BONUS[arrayList.size() - 1];
        double d4 = d * d3;
        double d5 = d2 * d3;
        for (Player player : arrayList) {
            double d6 = Experience.penaltyModifier(monsterInstance.calculateLevelDiffForDrop(player.getLevel()), 9.0);
            int n3 = n - player.getLevel();
            if (n3 >= Config.PARTY_PENALTY_EXP_SP_MAX_LEVEL && n3 <= Config.PARTY_PENALTY_EXP_SP_MIN_LEVEL) {
                d6 *= 0.3;
            }
            double d7 = d4 * d6 * (double)player.getLevel() / (double)n2;
            double d8 = d5 * d6 * (double)player.getLevel() / (double)n2;
            d7 = Math.min(d7, d);
            d8 = Math.min(d8, d2);
            if (!player.isCursedWeaponEquipped() && d7 > 0.0 && player.getKarma() > 0) {
                int n4 = Formulas.calculateKarmaLost(player, (long)d7);
                player.decreaseKarma(Math.max(n4, 0));
            }
            player.addExpAndCheckBonus(monsterInstance, (long)d7, (long)d8, d7 / d);
        }
        this.recalculatePartyData();
    }

    public void recalculatePartyData() {
        this.hr = 0;
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        double d4 = 0.0;
        double d5 = 0.0;
        double d6 = 0.0;
        double d7 = 0.0;
        double d8 = 0.0;
        double d9 = Double.MAX_VALUE;
        double d10 = Double.MAX_VALUE;
        double d11 = Double.MAX_VALUE;
        double d12 = Double.MAX_VALUE;
        double d13 = Double.MAX_VALUE;
        double d14 = Double.MAX_VALUE;
        double d15 = Double.MAX_VALUE;
        double d16 = Double.MAX_VALUE;
        int n = 0;
        for (Player player : this.aS) {
            int n2 = player.getLevel();
            this.hr = Math.max(this.hr, n2);
            ++n;
            d += (double)player.getBonus().getRateXp();
            d2 += (double)player.getBonus().getRateSp();
            d3 += (double)player.getBonus().getRateRaidXp();
            d4 += (double)player.getBonus().getRateRaidSp();
            d5 += (double)player.getBonus().getDropItems();
            d8 += (double)player.getBonus().getDropSealStones();
            d6 += (double)player.getBonus().getDropAdena();
            d7 += (double)player.getBonus().getDropSpoil();
            d9 = Math.min(d9, (double)player.getBonus().getRateXp());
            d10 = Math.min(d10, (double)player.getBonus().getRateSp());
            d11 = Math.min(d9, (double)player.getBonus().getRateRaidXp());
            d12 = Math.min(d10, (double)player.getBonus().getRateRaidSp());
            d13 = Math.min(d13, (double)player.getBonus().getDropItems());
            d14 = Math.min(d14, (double)player.getBonus().getDropAdena());
            d15 = Math.min(d15, (double)player.getBonus().getDropSpoil());
            d16 = Math.min(d16, (double)player.getBonus().getDropSealStones());
        }
        this._rateExp = Config.RATE_PARTY_MIN ? d9 : d / (double)n;
        this._rateSp = Config.RATE_PARTY_MIN ? d10 : d2 / (double)n;
        this._rateRaidExp = Config.RATE_PARTY_MIN ? d11 : d3 / (double)n;
        this._rateRaidSp = Config.RATE_PARTY_MIN ? d12 : d4 / (double)n;
        this._rateDrop = Config.RATE_PARTY_MIN ? d13 : d5 / (double)n;
        this._rateSealStones = Config.RATE_PARTY_MIN ? d16 : d8 / (double)n;
        this._rateAdena = Config.RATE_PARTY_MIN ? d14 : d6 / (double)n;
        this._rateSpoil = Config.RATE_PARTY_MIN ? d15 : d7 / (double)n;
    }

    public int getLevel() {
        return this.hr;
    }

    public int getLootDistribution() {
        return this.hs;
    }

    public boolean isDistributeSpoilLoot() {
        boolean bl = false;
        if (this.hs == 2 || this.hs == 4) {
            bl = true;
        }
        return bl;
    }

    public boolean isInDimensionalRift() {
        return this.hu > 0 && this.getDimensionalRift() != null;
    }

    public void setDimensionalRift(DimensionalRift dimensionalRift) {
        this.hu = dimensionalRift == null ? 0 : dimensionalRift.getId();
    }

    public DimensionalRift getDimensionalRift() {
        return this.hu == 0 ? null : (DimensionalRift)ReflectionManager.getInstance().get(this.hu);
    }

    public boolean isInReflection() {
        if (this._reflection != null) {
            return true;
        }
        if (this._commandChannel != null) {
            return this._commandChannel.isInReflection();
        }
        return false;
    }

    public void setReflection(Reflection reflection) {
        this._reflection = reflection;
    }

    public Reflection getReflection() {
        if (this._reflection != null) {
            return this._reflection;
        }
        if (this._commandChannel != null) {
            return this._commandChannel.getReflection();
        }
        return null;
    }

    public boolean isInCommandChannel() {
        return this._commandChannel != null;
    }

    public CommandChannel getCommandChannel() {
        return this._commandChannel;
    }

    public void setCommandChannel(CommandChannel commandChannel) {
        this._commandChannel = commandChannel;
    }

    public void Teleport(int n, int n2, int n3) {
        Party.TeleportParty(this.getPartyMembers(), new Location(n, n2, n3));
    }

    public void Teleport(Location location) {
        Party.TeleportParty(this.getPartyMembers(), location);
    }

    public void Teleport(Territory territory) {
        Party.RandomTeleportParty(this.getPartyMembers(), territory);
    }

    public void Teleport(Territory territory, Location location) {
        Party.TeleportParty(this.getPartyMembers(), territory, location);
    }

    public static void TeleportParty(List<Player> list, Location location) {
        for (Player player : list) {
            if (player == null) continue;
            player.teleToLocation(location);
        }
    }

    public static void TeleportParty(List<Player> list, Territory territory, Location location) {
        if (!territory.isInside(location.x, location.y)) {
            Log.add("TeleportParty: dest is out of territory", "errors");
            Thread.dumpStack();
            return;
        }
        int n = list.get(0).getX();
        int n2 = list.get(0).getY();
        for (Player player : list) {
            if (player == null) continue;
            int n3 = player.getX() - n;
            int n4 = player.getY() - n2;
            Location location2 = new Location(location.x + n3, location.y + n4, location.z);
            while (!territory.isInside(location2.x, location2.y)) {
                n3 = location2.x - location.x;
                n4 = location2.y - location.y;
                if (n3 != 0) {
                    location2.x -= n3 / Math.abs(n3);
                }
                if (n4 == 0) continue;
                location2.y -= n4 / Math.abs(n4);
            }
            player.teleToLocation(location2);
        }
    }

    public static void RandomTeleportParty(List<Player> list, Territory territory) {
        for (Player player : list) {
            player.teleToLocation(Territory.getRandomLoc(territory, player.getGeoIndex()));
        }
    }

    private void bf() {
        if (this.Q == null) {
            this.Q = LazyPrecisionTaskManager.getInstance().scheduleAtFixedRate(new UpdatePositionTask(), 1000L, 1000L);
        }
    }

    private void bg() {
        if (this.Q != null) {
            this.Q.cancel(false);
        }
    }

    public void requestLootChange(byte by) {
        if (this.hv != -1) {
            if (System.currentTimeMillis() > this.bi) {
                this.c(false);
            } else {
                return;
            }
        }
        this.hv = by;
        int n = 45000;
        this.bi = System.currentTimeMillis() + (long)n;
        this.l = new CopyOnWriteArraySet<Integer>();
        this.k = ThreadPoolManager.getInstance().scheduleAtFixedRate(new ChangeLootCheck(this), n + 1000, 5000L);
        this.broadcastToPartyMembers(this.getPartyLeader(), new ExAskModifyPartyLooting(this.getPartyLeader().getName(), by));
        SystemMessage systemMessage = new SystemMessage(SystemMsg.REQUESTING_APPROVAL_FOR_CHANGING_PARTY_LOOT_TO_S1);
        systemMessage.addSysString(aA[by]);
        this.getPartyLeader().sendPacket((IStaticPacket)systemMessage);
    }

    public synchronized void answerLootChangeRequest(Player player, boolean bl) {
        if (this.hv == -1) {
            return;
        }
        if (this.l.contains(player.getObjectId())) {
            return;
        }
        if (!bl) {
            this.c(false);
            return;
        }
        this.l.add(player.getObjectId());
        if (this.l.size() >= this.getMemberCount() - 1) {
            this.c(true);
        }
    }

    private synchronized void c(boolean bl) {
        if (this.hv == -1) {
            return;
        }
        if (this.k != null) {
            this.k.cancel(false);
            this.k = null;
        }
        if (bl) {
            this.broadCast(new ExSetPartyLooting(1, this.hv));
            this.hs = this.hv;
            SystemMessage systemMessage = new SystemMessage(SystemMsg.PARTY_LOOT_WAS_CHANGED_TO_S1);
            systemMessage.addSysString(aA[this.hv]);
            this.broadCast(systemMessage);
        } else {
            this.broadCast(new ExSetPartyLooting(0, 0));
            this.broadCast(new SystemMessage(SystemMsg.PARTY_LOOT_CHANGE_WAS_CANCELLED));
        }
        this.l = null;
        this.hv = -1;
        this.bi = 0L;
    }

    @Override
    public Iterator<Player> iterator() {
        return this.aS.iterator();
    }

    private void S(Player player) {
        for (Map.Entry<Integer, HardReference<? extends Creature>> entry : this.aC.entrySet()) {
            Creature creature = entry.getValue().get();
            if (creature == null) continue;
            player.sendPacket((IStaticPacket)new ExTacticalSign(creature, entry.getKey()));
        }
    }

    private void T(Player player) {
        for (HardReference<? extends Creature> hardReference : this.aC.values()) {
            Creature creature = hardReference.get();
            if (creature == null) continue;
            player.sendPacket((IStaticPacket)new ExTacticalSign(creature, 0));
        }
    }

    public void addTacticalSign(Player player, int n, Creature creature) {
        HardReference<? extends Creature> hardReference = this.aC.get(n);
        if (hardReference == null || hardReference.get() == null) {
            this.aC.values().remove(creature.getRef());
            this.aC.put(n, creature.getRef());
            this.broadCast(new IStaticPacket[]{new ExTacticalSign(creature, n), ((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_USED_S3_ON_C2).addName(player)).addString(creature.getName())).addSysString(aB[n])});
        } else if (hardReference == creature.getRef()) {
            this.aC.remove(n);
            this.broadCast(new ExTacticalSign(creature, 0));
        } else {
            this.aC.replace(n, creature.getRef());
            this.broadCast(new IStaticPacket[]{new ExTacticalSign(hardReference.get(), 0), new ExTacticalSign(creature, n), ((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_USED_S3_ON_C2).addName(player)).addString(creature.getName())).addSysString(aB[n])});
        }
    }

    public void setTargetBasedOnTacticalSignId(Player player, int n) {
        Creature creature;
        if (this.aC.isEmpty()) {
            return;
        }
        HardReference<? extends Creature> hardReference = this.aC.get(n);
        if (hardReference != null && (creature = hardReference.get()) != null && !creature.isInvisible() && creature.isTargetable()) {
            player.setObjectTarget(creature);
        }
    }

    private class UpdatePositionTask
    extends RunnableImpl {
        private UpdatePositionTask() {
        }

        @Override
        public void runImpl() throws Exception {
            Object object;
            LazyArrayList<Player> lazyArrayList = LazyArrayList.newInstance();
            for (Player player : Party.this.aS) {
                object = player.getLastPartyPosition();
                if (object != null && !(player.getDistance((Location)object) > 256.0)) continue;
                player.setLastPartyPosition(player.getLoc());
                lazyArrayList.add(player);
            }
            if (!lazyArrayList.isEmpty()) {
                for (Player player : Party.this.aS) {
                    object = new PartyMemberPosition();
                    for (Player player2 : lazyArrayList) {
                        if (player2 == player) continue;
                        ((PartyMemberPosition)object).add(player2);
                    }
                    if (((PartyMemberPosition)object).size() <= 0) continue;
                    player.sendPacket((IStaticPacket)object);
                }
            }
            LazyArrayList.recycle(lazyArrayList);
        }
    }

    private static class ChangeLootCheck
    extends RunnableImpl {
        private final Party _party;

        private ChangeLootCheck(Party party) {
            this._party = party;
        }

        @Override
        public void runImpl() throws Exception {
            if (System.currentTimeMillis() > this._party.bi) {
                this._party.c(false);
            }
        }
    }
}
