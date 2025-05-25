/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model;

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class GatekeeperMozzarellaInstance
extends MerchantInstance {
    private static final Location[] k = new Location[]{new Location(17776, 113968, -11671), new Location(17680, 113968, -11671)};

    public GatekeeperMozzarellaInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, "teleporter/gatekeeper_mozzarella001.htm", new Object[0]);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!GatekeeperMozzarellaInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equals("teleport_request")) {
            if (player.getLevel() > Config.CRUMA_TELEPORT_MAX_LEVEL) {
                this.showChatWindow(player, "teleporter/gatekeeper_mozzarella005.htm", new Object[0]);
            } else {
                player.teleToLocation(Location.findPointToStay((Location)((Location)Rnd.get((Object[])k)), (int)100, (int)player.getGeoIndex()));
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
