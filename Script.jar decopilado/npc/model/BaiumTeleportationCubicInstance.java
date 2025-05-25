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

public class BaiumTeleportationCubicInstance
extends MerchantInstance {
    private static final Location[] j = new Location[]{new Location(108784, 16000, -4928), new Location(113824, 10448, -5164), new Location(115488, 22096, -5168)};

    public BaiumTeleportationCubicInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!BaiumTeleportationCubicInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        player.teleToLocation(Location.findPointToStay((Location)((Location)Rnd.get((Object[])j)), (int)100, (int)player.getGeoIndex()));
    }
}
