/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import services.L2JBrazilService;

class L2JBrazilService.2
implements Runnable {
    final /* synthetic */ String val$playerKey;
    final /* synthetic */ Player val$player;

    L2JBrazilService.2() {
        this.val$playerKey = string;
        this.val$player = player;
    }

    @Override
    public void run() {
        try {
            if (L2JBrazilService.b(Config.L2JBRAZIL_API_KEY, this.val$playerKey) && Config.L2JBRAZIL_REWARD_ITEM_ID.length > 0 && Config.L2JBRAZIL_REWARD_ITEM_COUNT.length > 0) {
                for (int i = 0; i < Config.L2JBRAZIL_REWARD_ITEM_ID.length; ++i) {
                    if (Config.L2JBRAZIL_REWARD_ITEM_ID[i] == 0 || Config.L2JBRAZIL_REWARD_ITEM_COUNT[i] <= 0 || !Rnd.chance((int)Config.L2JBRAZIL_REWARD_CHANCE[i])) continue;
                    ItemFunctions.addItem((Playable)this.val$player, (int)Config.L2JBRAZIL_REWARD_ITEM_ID[i], (long)Config.L2JBRAZIL_REWARD_ITEM_COUNT[i], (boolean)true);
                    this.val$player.sendMessage(new CustomMessage("services.L2JBrazil.Thank", this.val$player, new Object[0]));
                    Log.service((String)"L2JBrazilService", (Player)this.val$player, (String)("received an award for voting " + Config.L2JBRAZIL_REWARD_ITEM_ID[i] + " amount " + Config.L2JBRAZIL_REWARD_ITEM_COUNT[i]));
                }
            } else {
                this.val$player.sendMessage(new CustomMessage("services.L2JBrazil.NoVotes", this.val$player, new Object[0]));
            }
        }
        catch (Throwable throwable) {
            dZ.warn("L2JBrazil: Cant process reward.");
            this.val$player.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
        }
    }
}
