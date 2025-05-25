/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 */
package services;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import services.L2HopZoneService;

class L2HopZoneService.2
extends RunnableImpl {
    final /* synthetic */ HardReference val$playerRef;
    final /* synthetic */ String val$playerKey;
    final /* synthetic */ long val$now;

    L2HopZoneService.2(HardReference hardReference, String string, long l) {
        this.val$playerRef = hardReference;
        this.val$playerKey = string;
        this.val$now = l;
    }

    public void runImpl() throws Exception {
        Player player = (Player)this.val$playerRef.get();
        if (player == null) {
            return;
        }
        try {
            if (L2HopZoneService.b(Config.L2HOPZONE_API_KEY, this.val$playerKey) && Config.L2HOPZONE_REWARD_ITEM_ID.length > 0 && Config.L2HOPZONE_REWARD_ITEM_COUNT.length > 0) {
                for (int i = 0; i < Config.L2HOPZONE_REWARD_ITEM_ID.length; ++i) {
                    if (Config.L2HOPZONE_REWARD_ITEM_ID[i] == 0 || Config.L2HOPZONE_REWARD_ITEM_COUNT[i] <= 0 || !Rnd.chance((int)Config.L2HOPZONE_REWARD_CHANCE[i])) continue;
                    ItemFunctions.addItem((Playable)player, (int)Config.L2HOPZONE_REWARD_ITEM_ID[i], (long)Config.L2HOPZONE_REWARD_ITEM_COUNT[i], (boolean)true);
                    player.sendMessage(new CustomMessage("services.L2HopZone.Thank", player, new Object[0]));
                    Log.service((String)"L2HopZoneService", (Player)player, (String)("received an award for voting " + Config.L2HOPZONE_REWARD_ITEM_ID[i] + " amount " + Config.L2HOPZONE_REWARD_ITEM_COUNT[i]));
                }
                L2HopZoneService.a(this.val$playerKey, this.val$now);
            } else {
                player.sendMessage(new CustomMessage("services.L2HopZone.NoVotes", player, new Object[0]));
            }
        }
        catch (Throwable throwable) {
            dY.warn("L2HopZoneService: Cant process reward.");
            player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
        }
    }
}
