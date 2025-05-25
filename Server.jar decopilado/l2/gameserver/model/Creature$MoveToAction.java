/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.geodata.GeoMove;
import l2.gameserver.model.Creature;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;

public static abstract class Creature.MoveToAction
extends Creature.MoveActionBase {
    protected final int indent;
    protected final boolean pathFind;
    protected final boolean ignoreGeo;
    protected Queue<List<Location>> geoPathLines;
    protected List<Location> currentGeoPathLine;
    protected Location moveFrom;
    protected Location moveTo;
    protected double prevMoveLen;
    protected boolean prevIncZ;

    protected Creature.MoveToAction(Creature creature, boolean bl, int n, boolean bl2) {
        super(creature);
        this.indent = n;
        this.pathFind = bl2;
        this.ignoreGeo = bl;
        this.geoPathLines = new LinkedList<List<Location>>();
        this.currentGeoPathLine = Collections.emptyList();
        this.moveFrom = creature.getLoc();
        this.moveTo = creature.getLoc();
        this.prevMoveLen = 0.0;
        this.prevIncZ = false;
    }

    protected boolean buildPathLines(Location location, Location location2) {
        Creature creature = this.getActor();
        if (creature == null) {
            return false;
        }
        LinkedList<List<Location>> linkedList = new LinkedList<List<Location>>();
        if (!GeoMove.buildGeoPath(linkedList, location.clone().world2geo(), location2.clone().world2geo(), creature.getGeoIndex(), (int)creature.getColRadius(), (int)creature.getColHeight(), this.indent, this.pathFind && !this.ignoreGeo && !this.isRelativeMove(), this.isForPlayable(), creature.isFlying(), creature.isInWater(), creature.getWaterZ(), this.ignoreGeo)) {
            return false;
        }
        this.geoPathLines.clear();
        this.geoPathLines.addAll(linkedList);
        return true;
    }

    protected Queue<List<Location>> getGeoPathLines() {
        return this.geoPathLines;
    }

    public List<Location> currentGeoPathLine(List<Location> list) {
        this.currentGeoPathLine = list;
        return this.currentGeoPathLine;
    }

    public List<Location> currentGeoPathLine() {
        return this.currentGeoPathLine;
    }

    public boolean isPrevIncZ() {
        return this.prevIncZ;
    }

    public void setPrevIncZ(boolean bl) {
        this.prevIncZ = bl;
    }

    public double getPrevMoveLen() {
        return this.prevMoveLen;
    }

    public void setPrevMoveLen(double d) {
        this.prevMoveLen = d;
    }

    protected boolean pollPathLine() {
        if (this.currentGeoPathLine(this.getGeoPathLines().poll()) != null) {
            Creature creature = this.getActor();
            Location location = this.currentGeoPathLine().get(0).clone().geo2world();
            Location location2 = this.currentGeoPathLine().get(this.currentGeoPathLine().size() - 1).clone().geo2world();
            this.setMoveFrom(location);
            this.setMoveTo(location2);
            this.setPrevIncZ(this.includeMoveZ());
            this.setPrevMoveLen(PositionUtils.calculateDistance(location, location2, this.isPrevIncZ()));
            this.setPassDist(0.0);
            this.setPrevTick(System.currentTimeMillis());
            if (this.getPrevMoveLen() > 16.0) {
                creature.setHeading(PositionUtils.calculateHeadingFrom(location.getX(), location.getY(), location2.getX(), location2.getY()));
            }
            return true;
        }
        return false;
    }

    protected int remainingLinesCount() {
        return this.geoPathLines.size();
    }

    protected abstract boolean isRelativeMove();

    @Override
    protected boolean calcMidDest(Creature creature, Location location, boolean bl, double d, double d2, double d3) {
        if (this.currentGeoPathLine == null) {
            return false;
        }
        Location location2 = creature.getLoc();
        if (d3 < 16.0 || d == 0.0 || d2 == 0.0 || this.currentGeoPathLine.isEmpty()) {
            location.set(location2);
            return true;
        }
        int n = this.currentGeoPathLine.size() - 1;
        location.set(this.moveFrom).indent(this.moveTo, (int)(d2 + 0.5), bl).setZ(this.currentGeoPathLine.get(Math.min(n, (int)((double)n * d + 0.5))).getZ());
        if (location.equalsGeo(location2) || this.ignoreGeo || !Config.ALLOW_GEODATA) {
            return true;
        }
        if (bl) {
            return true;
        }
        return GeoEngine.canMoveToCoord(location2.getX(), location2.getY(), location2.getZ(), location.getX(), location.getY(), location.getZ(), creature.getGeoIndex());
    }

    @Override
    public Location moveFrom() {
        return this.moveFrom;
    }

    @Override
    public Location moveTo() {
        return this.moveTo;
    }

    protected void setMoveFrom(Location location) {
        this.moveFrom = location;
    }

    protected void setMoveTo(Location location) {
        this.moveTo = location;
    }

    @Override
    protected double getMoveLen() {
        boolean bl = this.includeMoveZ();
        if (bl != this.prevIncZ) {
            this.prevMoveLen = PositionUtils.calculateDistance(this.moveFrom, this.moveTo, bl);
            this.prevIncZ = bl;
        }
        return this.prevMoveLen;
    }
}
