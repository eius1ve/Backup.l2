/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.petition.IPetitionHandler
 *  l2.gameserver.model.Player
 */
package handler.petition;

import l2.gameserver.handler.petition.IPetitionHandler;
import l2.gameserver.model.Player;

public class SimplePetitionHandler
implements IPetitionHandler {
    public void handle(Player player, int n, String string) {
        player.sendMessage(string);
    }
}
