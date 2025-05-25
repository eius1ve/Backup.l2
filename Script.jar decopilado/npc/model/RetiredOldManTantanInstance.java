/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model;

import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class RetiredOldManTantanInstance
extends NpcInstance {
    private static final Location[] l = new Location[]{new Location(89856, -7248, -3034), new Location(79232, -5904, -2864), new Location(77584, -1120, -3626), new Location(96640, -1296, -3648), new Location(94416, -10256, -3245)};

    public RetiredOldManTantanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onSpawn() {
        super.onSpawn();
        this.teleToLocation(Location.findPointToStay((Location)((Location)Rnd.get((Object[])l)), (int)100, (int)this.getGeoIndex()));
    }
}
