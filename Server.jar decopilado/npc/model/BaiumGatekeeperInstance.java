/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 */
package npc.model;

import bosses.BaiumManager;
import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;

public final class BaiumGatekeeperInstance
extends NpcInstance {
    private static final int Hl = 29020;
    private static final int Hm = 29025;
    private static final int Hn = 4295;
    private static final Location ac = new Location(113100, 14500, 10077);

    public BaiumGatekeeperInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!BaiumGatekeeperInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.startsWith("request_entrance")) {
            if (ItemFunctions.getItemCount((Playable)player, (int)4295) > 0L) {
                NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)29020);
                if (npcInstance != null) {
                    this.showChatWindow(player, "default/31862-1.htm", new Object[0]);
                    return;
                }
                NpcInstance npcInstance2 = GameObjectsStorage.getByNpcId((int)29025);
                if (npcInstance2 == null) {
                    this.showChatWindow(player, "default/31862-2.htm", new Object[0]);
                    return;
                }
                ItemFunctions.removeItem((Playable)player, (int)4295, (long)1L, (boolean)true);
                if (Config.BAIUM_CHECK_QUEST_ON_AWAKE) {
                    player.setVar("baiumPermission", "granted", -1L);
                }
                player.teleToLocation(ac);
            } else {
                this.showChatWindow(player, "default/31862-3.htm", new Object[0]);
            }
        } else if (string.startsWith("request_wakeup")) {
            if (!player.isInZone(Zone.ZoneType.epic)) {
                return;
            }
            if (Config.BAIUM_CHECK_QUEST_ON_AWAKE && (player.getVar("baiumPermission") == null || !player.getVar("baiumPermission").equalsIgnoreCase("granted"))) {
                this.showChatWindow(player, "default/29025-1.htm", new Object[0]);
                return;
            }
            if (this.isBusy()) {
                this.showChatWindow(player, "default/29025-2.htm", new Object[0]);
            }
            this.setBusy(true);
            Functions.npcSayCustomMessage((NpcInstance)this, (String)"22937", (Object[])new Object[0]);
            BaiumManager.spawnBaium(this, player);
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
