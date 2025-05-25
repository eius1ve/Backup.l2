/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model;

import l2.commons.util.Rnd;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class SailrenTeleportationCubicInstance
extends MerchantInstance {
    private static final Location[] n = new Location[]{new Location(10610, -24035, -3676), new Location(10703, -24041, -3673), new Location(10769, -24107, -3672)};

    public SailrenTeleportationCubicInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!SailrenTeleportationCubicInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        player.teleToLocation(Location.findPointToStay((Location)((Location)Rnd.get((Object[])n)), (int)100, (int)player.getGeoIndex()));
    }
}
