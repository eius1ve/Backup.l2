/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.model;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnHandler;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnSelector;
import l2.gameserver.model.instances.DoorInstance;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SepulcherRoom {
    private static final int fS = 15000;
    private final SepulcherSpawnSelector a;
    private final List<SepulcherSpawnHandler> aG = new ArrayList<SepulcherSpawnHandler>();
    private DoorInstance a;

    public SepulcherRoom(SepulcherSpawnHandler sepulcherSpawnHandler) {
        this.a = new SepulcherSpawnSelector(sepulcherSpawnHandler);
    }

    public SepulcherRoom(SepulcherSpawnSelector sepulcherSpawnSelector) {
        this.a = sepulcherSpawnSelector;
    }

    public void spawn(SepulcherSpawnSelector sepulcherSpawnSelector) {
        sepulcherSpawnSelector.get().spawn();
    }

    public void spawn(SepulcherSpawnHandler sepulcherSpawnHandler) {
        sepulcherSpawnHandler.spawn();
    }

    public List<SepulcherSpawnHandler> getResponsibleMobSpawns() {
        return this.aG;
    }

    public void activate() {
        if (this.a != null) {
            this.aK();
        }
        this.a.get().spawn();
    }

    public void deactivate() {
        this.aG.forEach(SepulcherSpawnHandler::despawn);
    }

    public void setEnterDoor(DoorInstance doorInstance) {
        this.a = doorInstance;
    }

    private void aK() {
        this.a.openMe();
        ThreadPoolManager.getInstance().schedule(this.a::closeMe, 15000L);
    }
}
