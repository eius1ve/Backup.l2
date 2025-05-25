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

public class SpringGirlSookiInstance
extends NpcInstance {
    private static final Location[] o = new Location[]{new Location(69152, 88816, -34080), new Location(75392, 87600, -33440), new Location(67920, 77888, -36160), new Location(76544, 73616, -37090), new Location(66768, 72016, -36770), new Location(71520, 67936, -35640)};

    public SpringGirlSookiInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onSpawn() {
        super.onSpawn();
        this.teleToLocation(Location.findPointToStay((Location)((Location)Rnd.get((Object[])o)), (int)100, (int)this.getGeoIndex()));
    }
}
