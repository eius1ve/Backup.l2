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

public class RunawayBoyIvanInstance
extends NpcInstance {
    private static final Location[] m = new Location[]{new Location(104000, -156496, -1968), new Location(108912, -150000, -2416), new Location(107888, -144688, -3660), new Location(123248, -147680, -3520), new Location(118176, -161008, -1072), new Location(120880, -156720, -1673), new Location(117024, -150304, -2320)};

    public RunawayBoyIvanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onSpawn() {
        super.onSpawn();
        this.teleToLocation(Location.findPointToStay((Location)((Location)Rnd.get((Object[])m)), (int)100, (int)this.getGeoIndex()));
    }
}
