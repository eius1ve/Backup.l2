/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;

public class StaticObjectTemplate {
    private final int FJ;
    private final int FK;
    private final String gm;
    private final int FL;
    private final int FM;
    private final String gn;
    private final int FN;
    private final int FO;
    private final int FP;
    private final boolean hb;

    public StaticObjectTemplate(StatsSet statsSet) {
        this.FJ = statsSet.getInteger("uid");
        this.FK = statsSet.getInteger("stype");
        this.FL = statsSet.getInteger("map_x");
        this.FM = statsSet.getInteger("map_y");
        this.gm = statsSet.getString("path");
        this.gn = statsSet.getString("name");
        this.FN = statsSet.getInteger("x");
        this.FO = statsSet.getInteger("y");
        this.FP = statsSet.getInteger("z");
        this.hb = statsSet.getBool("spawn");
    }

    public int getUId() {
        return this.FJ;
    }

    public int getType() {
        return this.FK;
    }

    public String getFilePath() {
        return this.gm;
    }

    public int getMapX() {
        return this.FL;
    }

    public int getMapY() {
        return this.FM;
    }

    public String getName() {
        return this.gn;
    }

    public int getX() {
        return this.FN;
    }

    public int getY() {
        return this.FO;
    }

    public int getZ() {
        return this.FP;
    }

    public boolean isSpawn() {
        return this.hb;
    }

    public StaticObjectInstance newInstance() {
        StaticObjectInstance staticObjectInstance = new StaticObjectInstance(IdFactory.getInstance().getNextId(), this);
        staticObjectInstance.spawnMe(new Location(this.getX(), this.getY(), this.getZ()));
        return staticObjectInstance;
    }
}
