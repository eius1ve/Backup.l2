/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.model;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.instancemanager.sepulchers.model.SepulcherRoom;
import l2.gameserver.instancemanager.sepulchers.model.WayData;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnHandler;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.SepulcherDoormanInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.scripts.Functions;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SepulcherWay {
    private final SepulcherSpawnHandler a;
    private final SepulcherSpawnHandler b;
    private final List<SepulcherRoom> aH = new ArrayList<SepulcherRoom>();
    private final WayData a;

    public SepulcherWay(SepulcherSpawnHandler sepulcherSpawnHandler, SepulcherSpawnHandler sepulcherSpawnHandler2, WayData wayData) {
        this.a = sepulcherSpawnHandler;
        this.b = sepulcherSpawnHandler2;
        this.a = wayData;
    }

    public void init() {
        this.a.spawn();
        this.b.spawn();
        List<NpcInstance> list = this.b.collectAlive();
        for (int i = 0; i < list.size() && i + 1 < this.aH.size(); ++i) {
            ((SepulcherDoormanInstance)list.get(i)).setHandlingRoom(this.aH.get(i + 1));
            this.aH.get(i + 1).setEnterDoor(this.a.getDoors().get(i));
        }
    }

    public List<SepulcherRoom> getRooms() {
        return this.aH;
    }

    public void activate() {
        this.aH.get(0).activate();
    }

    public void deactivate() {
        this.aH.forEach(SepulcherRoom::deactivate);
    }

    public WayData getWayData() {
        return this.a;
    }

    public SepulcherSpawnHandler getEntranceNpcSpawn() {
        return this.a;
    }

    public void sayByEntrance(String string) {
        Functions.npcSayInRangeCustomMessage(this.getEntranceNpc(), ChatType.SHOUT, 15000, string, new Object[0]);
    }

    public NpcInstance getEntranceNpc() {
        return this.a.collectAlive().get(0);
    }
}
