/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.builder.Builder
 *  org.napile.primitive.Containers
 *  org.napile.primitive.maps.IntObjectMap
 */
package l2.gameserver.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import l2.commons.time.cron.SchedulingPattern;
import l2.commons.util.Rnd;
import l2.gameserver.dao.InstanceReuseDAO;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Territory;
import l2.gameserver.templates.DoorTemplate;
import l2.gameserver.templates.InstantZoneEntryType;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.ZoneTemplate;
import l2.gameserver.templates.spawn.SpawnTemplate;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.StringUtils;
import org.napile.primitive.Containers;
import org.napile.primitive.maps.IntObjectMap;

public class InstantZone {
    private static final String gk = "hwidLimit";
    private final int EH;
    private final String gl;
    private final SchedulingPattern d;
    private final int EI;
    private final int EJ;
    private final int EK;
    private final int EL;
    private final int EM;
    private final int EN;
    private final boolean gO;
    private final int EO;
    private final List<Location> dm;
    private final Location Z;
    private final int EP;
    private final int EQ;
    private final IntObjectMap<DoorInfo> p;
    private final Map<String, ZoneInfo> bQ;
    private final Map<String, SpawnInfo2> bR;
    private final List<SpawnInfo> dn;
    private final int ER;
    private final int ES;
    private final int ET;
    private final int EU;
    private final boolean gP;
    private final int EV;
    private final int EW;
    private final int EX;
    private final boolean gQ;
    private final StatsSet e;
    private final InstantZoneEntryType a;
    private final boolean gR;

    private InstantZone(int n, String string, SchedulingPattern schedulingPattern, int n2, int n3, boolean bl, int n4, int n5, int n6, int n7, int n8, boolean bl2, List<Location> list, Location location, int n9, int n10, IntObjectMap<DoorInfo> intObjectMap, Map<String, ZoneInfo> map, Map<String, SpawnInfo2> map2, List<SpawnInfo> list2, int n11, int n12, int n13, int n14, boolean bl3, int n15, int n16, int n17, boolean bl4, StatsSet statsSet) {
        this.EH = n;
        this.gl = string;
        this.d = schedulingPattern;
        this.EI = n2;
        this.EJ = n3;
        this.gR = bl;
        this.EK = n4;
        this.EL = n5;
        this.dm = list;
        this.Z = location;
        this.EM = n6;
        this.EN = n7;
        this.gO = bl2;
        this.EO = n8;
        this.EP = n9;
        this.EQ = n10;
        this.p = intObjectMap;
        this.bQ = map;
        this.dn = list2;
        this.bR = map2;
        this.ER = n11;
        this.ES = n12;
        this.ET = n13;
        this.EU = n14;
        this.gP = bl3;
        this.EV = n15;
        this.EW = n16;
        this.EX = n17;
        this.gQ = bl4;
        this.e = statsSet;
        if (this.getMinParty() == 1) {
            this.a = InstantZoneEntryType.SOLO;
        } else if (this.getMinParty() > 1 && this.getMaxParty() <= 9) {
            this.a = InstantZoneEntryType.PARTY;
        } else if (this.getMaxParty() > 9) {
            this.a = InstantZoneEntryType.COMMAND_CHANNEL;
        } else {
            throw new IllegalArgumentException("Invalid type?: " + this.gl);
        }
    }

    public int getId() {
        return this.EH;
    }

    public String getName() {
        return this.gl;
    }

    public SchedulingPattern getResetReuse() {
        return this.d;
    }

    public boolean isDispelBuffs() {
        return this.gR;
    }

    public int getTimelimit() {
        return this.EJ;
    }

    public int getMinLevel() {
        return this.EK;
    }

    public int getMaxLevel() {
        return this.EL;
    }

    public int getMinParty() {
        return this.EM;
    }

    public int getMaxParty() {
        return this.EN;
    }

    public int getTimerOnCollapse() {
        return this.EO;
    }

    public boolean isCollapseOnPartyDismiss() {
        return this.gO;
    }

    public Location getTeleportCoord() {
        if (this.dm.size() == 1) {
            return this.dm.get(0);
        }
        return this.dm.get(Rnd.get(this.dm.size()));
    }

    public Location getReturnCoords() {
        return this.Z;
    }

    public int getMapX() {
        return this.EP;
    }

    public int getMapY() {
        return this.EQ;
    }

    public List<SpawnInfo> getSpawnsInfo() {
        return this.dn;
    }

    public int getSharedReuseGroup() {
        return this.EI;
    }

    public int getCollapseIfEmpty() {
        return this.ER;
    }

    public int getRemovedItemId() {
        return this.ET;
    }

    public int getRemovedItemCount() {
        return this.EU;
    }

    public boolean getRemovedItemNecessity() {
        return this.gP;
    }

    public int getGiveItemId() {
        return this.EV;
    }

    public int getGiveItemCount() {
        return this.EW;
    }

    public int getRequiredQuestId() {
        return this.EX;
    }

    public boolean getSetReuseUponEntry() {
        return this.gQ;
    }

    public int getMaxChannels() {
        return this.ES;
    }

    public InstantZoneEntryType getEntryType() {
        return this.a;
    }

    public IntObjectMap<DoorInfo> getDoors() {
        return this.p;
    }

    public Map<String, ZoneInfo> getZones() {
        return this.bQ;
    }

    public List<Location> getTeleportCoords() {
        return this.dm;
    }

    public Map<String, SpawnInfo2> getSpawns() {
        return this.bR;
    }

    public StatsSet getAddParams() {
        return this.e;
    }

    public boolean isHwidLimited() {
        return this.getAddParams().isSet(gk) && this.getAddParams().getBool(gk);
    }

    public int getMinutesToNextEntrance(Player player) {
        Object object;
        Object object2;
        SchedulingPattern schedulingPattern = InstantZoneHolder.getInstance().getResetReuseById(this.getId());
        if (schedulingPattern == null) {
            return 0;
        }
        Object object3 = null;
        if (InstantZoneHolder.getInstance().getSharedReuseInstanceIds(this.getId()) != null && !InstantZoneHolder.getInstance().getSharedReuseInstanceIds(this.getId()).isEmpty()) {
            object2 = new ArrayList();
            object = InstantZoneHolder.getInstance().getSharedReuseInstanceIds(this.getId()).iterator();
            while (object.hasNext()) {
                int n = object.next();
                if (player.getInstanceReuse(n) == null) continue;
                object2.add(player.getInstanceReuse(n));
            }
            if (!object2.isEmpty()) {
                Collections.sort(object2);
                object3 = (Long)object2.get(object2.size() - 1);
            }
        } else {
            object3 = player.getInstanceReuse(this.getId());
        }
        if (this.isHwidLimited() && player.getNetConnection() != null && (object2 = player.getNetConnection().getHwid()) != null && !StringUtils.isBlank((CharSequence)object2) && (Long)(object = InstanceReuseDAO.getInstance().getHwidReuse(this.getId(), (String)object2)) > 0L) {
            if (object3 == null) {
                object3 = object;
            } else if ((Long)object > (Long)object3) {
                object3 = object;
            }
        }
        if (object3 == null) {
            return 0;
        }
        return (int)Math.max((schedulingPattern.next((Long)object3) - System.currentTimeMillis()) / 60000L, 0L);
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    public static class Builder
    implements org.apache.commons.lang3.builder.Builder<InstantZone> {
        private int EY;
        private String name;
        private SchedulingPattern e = new SchedulingPattern("30 6 * * *");
        private int EZ = -1;
        private int Fa = 60;
        private int Fb = -1;
        private int Fc = -1;
        private boolean gS = false;
        private boolean gT = true;
        private int Fd;
        private int Fe;
        private int Ff;
        private int count;
        private int Fg = 0;
        private int Fh = 0;
        private int Fi = 0;
        private SpawnInfo a = null;
        private int Fj = 0;
        private int Fk = 0;
        private int Fl = 0;
        private int Fm = 0;
        private int Fn = 0;
        private int Fo = 20;
        private boolean gU = false;
        private boolean gV = true;
        private StatsSet f = new StatsSet();
        private int bG = 0;
        private int Fp = 0;
        private int Fq = 1;
        private int Fr = 9;
        private List<Location> do = new ArrayList<Location>();
        private Location aa;
        private List<SpawnInfo> spawns = new ArrayList<SpawnInfo>();
        private IntObjectMap<DoorInfo> q = Containers.emptyIntObjectMap();
        private Map<String, ZoneInfo> bS = Collections.emptyMap();
        private Map<String, SpawnInfo2> bT = Collections.emptyMap();

        public int getInstanceId() {
            return this.EY;
        }

        public Builder setInstanceId(int n) {
            this.EY = n;
            return this;
        }

        public String getName() {
            return this.name;
        }

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public SchedulingPattern getResetReuse() {
            return this.e;
        }

        public Builder setResetReuse(SchedulingPattern schedulingPattern) {
            this.e = schedulingPattern;
            return this;
        }

        public int getTimelimit() {
            return this.EZ;
        }

        public Builder setTimelimit(int n) {
            this.EZ = n;
            return this;
        }

        public int getTimer() {
            return this.Fa;
        }

        public Builder setTimer(int n) {
            this.Fa = n;
            return this;
        }

        public int getMapx() {
            return this.Fb;
        }

        public Builder setMapx(int n) {
            this.Fb = n;
            return this;
        }

        public int getMapy() {
            return this.Fc;
        }

        public Builder setMapy(int n) {
            this.Fc = n;
            return this;
        }

        public IntObjectMap<DoorInfo> getDoors() {
            return this.q;
        }

        public Builder setDoors(IntObjectMap<DoorInfo> intObjectMap) {
            this.q = intObjectMap;
            return this;
        }

        public Map<String, ZoneInfo> getZones() {
            return this.bS;
        }

        public Builder setZones(Map<String, ZoneInfo> map) {
            this.bS = map;
            return this;
        }

        public Map<String, SpawnInfo2> getSpawns2() {
            return this.bT;
        }

        public Builder setSpawns2(Map<String, SpawnInfo2> map) {
            this.bT = map;
            return this;
        }

        public List<SpawnInfo> getSpawns() {
            return this.spawns;
        }

        public Builder setSpawns(List<SpawnInfo> list) {
            this.spawns = list;
            return this;
        }

        public boolean isDispelBuffs() {
            return this.gS;
        }

        public Builder setDispelBuffs(boolean bl) {
            this.gS = bl;
            return this;
        }

        public boolean isOnPartyDismiss() {
            return this.gT;
        }

        public Builder setOnPartyDismiss(boolean bl) {
            this.gT = bl;
            return this;
        }

        public List<Location> getTeleportLocs() {
            return this.do;
        }

        public Builder setTeleportLocs(List<Location> list) {
            this.do = list;
            return this;
        }

        public Builder addTeleportLocs(List<Location> list) {
            this.do.addAll(list);
            return this;
        }

        public int getMobId() {
            return this.Fd;
        }

        public Builder setMobId(int n) {
            this.Fd = n;
            return this;
        }

        public int getRespawn() {
            return this.Fe;
        }

        public Builder setRespawn(int n) {
            this.Fe = n;
            return this;
        }

        public int getRespawnRnd() {
            return this.Ff;
        }

        public Builder setRespawnRnd(int n) {
            this.Ff = n;
            return this;
        }

        public int getCount() {
            return this.count;
        }

        public Builder setCount(int n) {
            this.count = n;
            return this;
        }

        public int getSharedReuseGroup() {
            return this.Fg;
        }

        public Builder setSharedReuseGroup(int n) {
            this.Fg = n;
            return this;
        }

        public int getCollapseIfEmpty() {
            return this.Fh;
        }

        public Builder setCollapseIfEmpty(int n) {
            this.Fh = n;
            return this;
        }

        public int getSpawnType() {
            return this.Fi;
        }

        public Builder setSpawnType(int n) {
            this.Fi = n;
            return this;
        }

        public SpawnInfo getSpawnDat() {
            return this.a;
        }

        public Builder setSpawnDat(SpawnInfo spawnInfo) {
            this.a = spawnInfo;
            return this;
        }

        public int getRemovedItemId() {
            return this.Fj;
        }

        public Builder setRemovedItemId(int n) {
            this.Fj = n;
            return this;
        }

        public int getRemovedItemCount() {
            return this.Fk;
        }

        public Builder setRemovedItemCount(int n) {
            this.Fk = n;
            return this;
        }

        public int getGiveItemId() {
            return this.Fl;
        }

        public Builder setGiveItemId(int n) {
            this.Fl = n;
            return this;
        }

        public int getGivedItemCount() {
            return this.Fm;
        }

        public Builder setGivedItemCount(int n) {
            this.Fm = n;
            return this;
        }

        public int getRequiredQuestId() {
            return this.Fn;
        }

        public Builder setRequiredQuestId(int n) {
            this.Fn = n;
            return this;
        }

        public int getMaxChannels() {
            return this.Fo;
        }

        public Builder setMaxChannels(int n) {
            this.Fo = n;
            return this;
        }

        public boolean isRemovedItemNecessity() {
            return this.gU;
        }

        public Builder setRemovedItemNecessity(boolean bl) {
            this.gU = bl;
            return this;
        }

        public boolean isSetReuseUponEntry() {
            return this.gV;
        }

        public Builder setSetReuseUponEntry(boolean bl) {
            this.gV = bl;
            return this;
        }

        public StatsSet getParams() {
            return this.f;
        }

        public Builder setParams(StatsSet statsSet) {
            this.f = statsSet;
            return this;
        }

        public int getMinLevel() {
            return this.bG;
        }

        public Builder setMinLevel(int n) {
            this.bG = n;
            return this;
        }

        public int getMaxLevel() {
            return this.Fp;
        }

        public Builder setMaxLevel(int n) {
            this.Fp = n;
            return this;
        }

        public int getMinParty() {
            return this.Fq;
        }

        public Builder setMinParty(int n) {
            this.Fq = n;
            return this;
        }

        public int getMaxParty() {
            return this.Fr;
        }

        public Builder setMaxParty(int n) {
            this.Fr = n;
            return this;
        }

        public Location getRet() {
            return this.aa;
        }

        public Builder setRet(Location location) {
            this.aa = location;
            return this;
        }

        public InstantZone build() {
            return new InstantZone(this.EY, this.name, this.e, this.Fg, this.EZ, this.gS, this.bG, this.Fp, this.Fq, this.Fr, this.Fa, this.gT, this.do, this.aa, this.Fb, this.Fc, this.q, this.bS, this.bT, this.spawns, this.Fh, this.Fo, this.Fj, this.Fk, this.gU, this.Fl, this.Fm, this.Fn, this.gV, this.f);
        }
    }

    @Deprecated
    public static class SpawnInfo {
        private final int Fs;
        private final int Ft;
        private final int Fu;
        private final int Fv;
        private final int Fw;
        private final List<Location> dp;
        private final Territory j;

        public SpawnInfo(int n, int n2, int n3, int n4, int n5, Territory territory) {
            this(n, n2, n3, n4, n5, null, territory);
        }

        public SpawnInfo(int n, int n2, int n3, int n4, int n5, List<Location> list) {
            this(n, n2, n3, n4, n5, list, null);
        }

        public SpawnInfo(int n, int n2, int n3, int n4, int n5, List<Location> list, Territory territory) {
            this.Fs = n;
            this.Ft = n2;
            this.Fu = n3;
            this.Fv = n4;
            this.Fw = n5;
            this.dp = list;
            this.j = territory;
        }

        public int getSpawnType() {
            return this.Fs;
        }

        public int getNpcId() {
            return this.Ft;
        }

        public int getCount() {
            return this.Fu;
        }

        public int getRespawnDelay() {
            return this.Fv;
        }

        public int getRespawnRnd() {
            return this.Fw;
        }

        public List<Location> getCoords() {
            return this.dp;
        }

        public Territory getLoc() {
            return this.j;
        }
    }

    public static class SpawnInfo2 {
        private final List<SpawnTemplate> dq;
        private final boolean gY;

        public SpawnInfo2(List<SpawnTemplate> list, boolean bl) {
            this.dq = list;
            this.gY = bl;
        }

        public List<SpawnTemplate> getTemplates() {
            return this.dq;
        }

        public boolean isSpawned() {
            return this.gY;
        }
    }

    public static class ZoneInfo {
        private final ZoneTemplate b;
        private final boolean gZ;

        public ZoneInfo(ZoneTemplate zoneTemplate, boolean bl) {
            this.b = zoneTemplate;
            this.gZ = bl;
        }

        public ZoneTemplate getTemplate() {
            return this.b;
        }

        public boolean isActive() {
            return this.gZ;
        }
    }

    public static class DoorInfo {
        private final DoorTemplate a;
        private final boolean gW;
        private final boolean gX;

        public DoorInfo(DoorTemplate doorTemplate, boolean bl, boolean bl2) {
            this.a = doorTemplate;
            this.gW = bl;
            this.gX = bl2;
        }

        public DoorTemplate getTemplate() {
            return this.a;
        }

        public boolean isOpened() {
            return this.gW;
        }

        public boolean isInvul() {
            return this.gX;
        }
    }
}
