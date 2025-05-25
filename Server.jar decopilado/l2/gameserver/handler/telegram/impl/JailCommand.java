/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package l2.gameserver.handler.telegram.impl;

import com.google.gson.Gson;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import l2.gameserver.Config;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Location;

public class JailCommand
implements ITelegramCommandHandler {
    private static final Gson c = new Gson();
    private static final String bS = "jailed";
    private final String[] X = new String[]{"/jail"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /jail <name> <period> <reason>", "Enter player name, period (in minutes) and reason");
        } else {
            StringTokenizer stringTokenizer = new StringTokenizer(string2);
            try {
                String string3 = stringTokenizer.nextToken();
                String string4 = stringTokenizer.nextToken();
                String string5 = stringTokenizer.nextToken();
                Player player = GameObjectsStorage.getPlayer(string3);
                if (player != null) {
                    long l = System.currentTimeMillis();
                    long l2 = TimeUnit.MINUTES.toMillis(Long.parseLong(string4));
                    Location location = player.getReflection() == ReflectionManager.DEFAULT ? new Location(player.getX(), player.getY(), player.getZ()) : new Location(player.getX(), player.getY(), player.getZ(), player.getReflectionId());
                    player.setVar(bS, String.format("%d;%s", l + l2, location.toXYZHString()), -1L);
                    player.startUnjailTask(player, (int)l2);
                    if (player.isInStoreMode()) {
                        player.setPrivateStoreType(0);
                    }
                    player.teleToLocation(Location.findPointToStay(player, Config.SERVICE_JAIL_COORDINATES, 50, 200), ReflectionManager.JAIL);
                    player.sitDown(null);
                    player.block();
                    player.sendMessage("You moved to jail, time to escape - " + TimeUnit.MILLISECONDS.toMinutes(l2) + " minutes, reason - " + string5 + " .");
                    TelegramApi.sendMessage(string, "You jailed " + string3 + ".");
                } else {
                    TelegramApi.sendMessage(string, "Can't find char " + string3 + ".");
                }
            }
            catch (Exception exception) {
                TelegramApi.sendMessage(string, "Command syntax: /jail <name> <period> <reason>");
            }
        }
    }

    @Override
    public String[] getCommands() {
        return this.X;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
