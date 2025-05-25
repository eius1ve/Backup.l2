/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.builder.Builder;
import org.napile.primitive.Containers;
import org.napile.primitive.maps.IntObjectMap;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public static class InstantZone.Builder
implements Builder<InstantZone> {
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
    private InstantZone.SpawnInfo a = null;
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
    private List<InstantZone.SpawnInfo> spawns = new ArrayList<InstantZone.SpawnInfo>();
    private IntObjectMap<InstantZone.DoorInfo> q = Containers.emptyIntObjectMap();
    private Map<String, InstantZone.ZoneInfo> bS = Collections.emptyMap();
    private Map<String, InstantZone.SpawnInfo2> bT = Collections.emptyMap();

    public int getInstanceId() {
        return this.EY;
    }

    public InstantZone.Builder setInstanceId(int n) {
        this.EY = n;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public InstantZone.Builder setName(String string) {
        this.name = string;
        return this;
    }

    public SchedulingPattern getResetReuse() {
        return this.e;
    }

    public InstantZone.Builder setResetReuse(SchedulingPattern schedulingPattern) {
        this.e = schedulingPattern;
        return this;
    }

    public int getTimelimit() {
        return this.EZ;
    }

    public InstantZone.Builder setTimelimit(int n) {
        this.EZ = n;
        return this;
    }

    public int getTimer() {
        return this.Fa;
    }

    public InstantZone.Builder setTimer(int n) {
        this.Fa = n;
        return this;
    }

    public int getMapx() {
        return this.Fb;
    }

    public InstantZone.Builder setMapx(int n) {
        this.Fb = n;
        return this;
    }

    public int getMapy() {
        return this.Fc;
    }

    public InstantZone.Builder setMapy(int n) {
        this.Fc = n;
        return this;
    }

    public IntObjectMap<InstantZone.DoorInfo> getDoors() {
        return this.q;
    }

    public InstantZone.Builder setDoors(IntObjectMap<InstantZone.DoorInfo> intObjectMap) {
        this.q = intObjectMap;
        return this;
    }

    public Map<String, InstantZone.ZoneInfo> getZones() {
        return this.bS;
    }

    public InstantZone.Builder setZones(Map<String, InstantZone.ZoneInfo> map) {
        this.bS = map;
        return this;
    }

    public Map<String, InstantZone.SpawnInfo2> getSpawns2() {
        return this.bT;
    }

    public InstantZone.Builder setSpawns2(Map<String, InstantZone.SpawnInfo2> map) {
        this.bT = map;
        return this;
    }

    public List<InstantZone.SpawnInfo> getSpawns() {
        return this.spawns;
    }

    public InstantZone.Builder setSpawns(List<InstantZone.SpawnInfo> list) {
        this.spawns = list;
        return this;
    }

    public boolean isDispelBuffs() {
        return this.gS;
    }

    public InstantZone.Builder setDispelBuffs(boolean bl) {
        this.gS = bl;
        return this;
    }

    public boolean isOnPartyDismiss() {
        return this.gT;
    }

    public InstantZone.Builder setOnPartyDismiss(boolean bl) {
        this.gT = bl;
        return this;
    }

    public List<Location> getTeleportLocs() {
        return this.do;
    }

    public InstantZone.Builder setTeleportLocs(List<Location> list) {
        this.do = list;
        return this;
    }

    public InstantZone.Builder addTeleportLocs(List<Location> list) {
        this.do.addAll(list);
        return this;
    }

    public int getMobId() {
        return this.Fd;
    }

    public InstantZone.Builder setMobId(int n) {
        this.Fd = n;
        return this;
    }

    public int getRespawn() {
        return this.Fe;
    }

    public InstantZone.Builder setRespawn(int n) {
        this.Fe = n;
        return this;
    }

    public int getRespawnRnd() {
        return this.Ff;
    }

    public InstantZone.Builder setRespawnRnd(int n) {
        this.Ff = n;
        return this;
    }

    public int getCount() {
        return this.count;
    }

    public InstantZone.Builder setCount(int n) {
        this.count = n;
        return this;
    }

    public int getSharedReuseGroup() {
        return this.Fg;
    }

    public InstantZone.Builder setSharedReuseGroup(int n) {
        this.Fg = n;
        return this;
    }

    public int getCollapseIfEmpty() {
        return this.Fh;
    }

    public InstantZone.Builder setCollapseIfEmpty(int n) {
        this.Fh = n;
        return this;
    }

    public int getSpawnType() {
        return this.Fi;
    }

    public InstantZone.Builder setSpawnType(int n) {
        this.Fi = n;
        return this;
    }

    public InstantZone.SpawnInfo getSpawnDat() {
        return this.a;
    }

    public InstantZone.Builder setSpawnDat(InstantZone.SpawnInfo spawnInfo) {
        this.a = spawnInfo;
        return this;
    }

    public int getRemovedItemId() {
        return this.Fj;
    }

    public InstantZone.Builder setRemovedItemId(int n) {
        this.Fj = n;
        return this;
    }

    public int getRemovedItemCount() {
        return this.Fk;
    }

    public InstantZone.Builder setRemovedItemCount(int n) {
        this.Fk = n;
        return this;
    }

    public int getGiveItemId() {
        return this.Fl;
    }

    public InstantZone.Builder setGiveItemId(int n) {
        this.Fl = n;
        return this;
    }

    public int getGivedItemCount() {
        return this.Fm;
    }

    public InstantZone.Builder setGivedItemCount(int n) {
        this.Fm = n;
        return this;
    }

    public int getRequiredQuestId() {
        return this.Fn;
    }

    public InstantZone.Builder setRequiredQuestId(int n) {
        this.Fn = n;
        return this;
    }

    public int getMaxChannels() {
        return this.Fo;
    }

    public InstantZone.Builder setMaxChannels(int n) {
        this.Fo = n;
        return this;
    }

    public boolean isRemovedItemNecessity() {
        return this.gU;
    }

    public InstantZone.Builder setRemovedItemNecessity(boolean bl) {
        this.gU = bl;
        return this;
    }

    public boolean isSetReuseUponEntry() {
        return this.gV;
    }

    public InstantZone.Builder setSetReuseUponEntry(boolean bl) {
        this.gV = bl;
        return this;
    }

    public StatsSet getParams() {
        return this.f;
    }

    public InstantZone.Builder setParams(StatsSet statsSet) {
        this.f = statsSet;
        return this;
    }

    public int getMinLevel() {
        return this.bG;
    }

    public InstantZone.Builder setMinLevel(int n) {
        this.bG = n;
        return this;
    }

    public int getMaxLevel() {
        return this.Fp;
    }

    public InstantZone.Builder setMaxLevel(int n) {
        this.Fp = n;
        return this;
    }

    public int getMinParty() {
        return this.Fq;
    }

    public InstantZone.Builder setMinParty(int n) {
        this.Fq = n;
        return this;
    }

    public int getMaxParty() {
        return this.Fr;
    }

    public InstantZone.Builder setMaxParty(int n) {
        this.Fr = n;
        return this;
    }

    public Location getRet() {
        return this.aa;
    }

    public InstantZone.Builder setRet(Location location) {
        this.aa = location;
        return this;
    }

    public InstantZone build() {
        return new InstantZone(this.EY, this.name, this.e, this.Fg, this.EZ, this.gS, this.bG, this.Fp, this.Fq, this.Fr, this.Fa, this.gT, this.do, this.aa, this.Fb, this.Fc, this.q, this.bS, this.bT, this.spawns, this.Fh, this.Fo, this.Fj, this.Fk, this.gU, this.Fl, this.Fm, this.Fn, this.gV, this.f);
    }
}
