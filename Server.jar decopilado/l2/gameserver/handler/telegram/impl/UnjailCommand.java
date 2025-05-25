/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import java.util.StringTokenizer;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.utils.Location;

public class UnjailCommand
implements ITelegramCommandHandler {
    private static final String bX = "jailed";
    private final String[] au = new String[]{"/unjail"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /unjail <name>", "Enter player name to unjail");
        } else {
            StringTokenizer stringTokenizer = new StringTokenizer(string2);
            try {
                String string3 = stringTokenizer.nextToken();
                Player player = GameObjectsStorage.getPlayer(string3);
                if (player != null && player.getVar(bX) != null) {
                    String string4 = player.getVar(bX);
                    Location location = Location.parseLoc(string4.substring(string4.indexOf(59) + 1));
                    Reflection reflection = location.getH() != 0 ? ReflectionManager.getInstance().get(location.getH()) : null;
                    player.unblock();
                    player.standUp();
                    if (reflection != null) {
                        if (reflection.isCollapseStarted()) {
                            player.teleToClosestTown();
                        } else {
                            player.teleToLocation(location, reflection);
                        }
                    } else {
                        player.teleToLocation(location, ReflectionManager.DEFAULT);
                    }
                    player.stopUnjailTask();
                    player.unsetVar(bX);
                    TelegramApi.sendMessage(string, "You unjailed " + string3 + ".");
                } else {
                    TelegramApi.sendMessage(string, "Can't find char " + string3 + " or the player is not jailed.");
                }
            }
            catch (Exception exception) {
                TelegramApi.sendMessage(string, "Command syntax: /unjail <name>");
            }
        }
    }

    @Override
    public String[] getCommands() {
        return this.au;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
