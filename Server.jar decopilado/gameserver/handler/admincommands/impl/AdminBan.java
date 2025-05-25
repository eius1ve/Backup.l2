/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import l2.commons.geometry.Point2D;
import l2.commons.geometry.Point3D;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.gs2as.ChangeAccessLevel;
import l2.gameserver.network.l2.CGModule;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.AutoBan;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;

public class AdminBan
implements IAdminCommandHandler {
    private static final String bP = "jailed";

    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        Commands commands = (Commands)enum_;
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        if (player.getPlayerAccess().CanTradeBanUnban) {
            switch (commands) {
                case admin_trade_ban: {
                    return this.a(stringTokenizer, player);
                }
                case admin_trade_unban: {
                    return this.b(stringTokenizer, player);
                }
            }
        }
        if (player.getPlayerAccess().CanJail) {
            switch (commands) {
                case admin_jail: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        object3 = stringTokenizer.nextToken();
                        object2 = stringTokenizer.nextToken();
                        object = World.getPlayer((String)object4);
                        if (object != null) {
                            long l = System.currentTimeMillis();
                            long l2 = Long.parseLong((String)object3);
                            if (l2 > Integer.MAX_VALUE) {
                                player.sendMessage("Period limit exceeded.");
                                break;
                            }
                            long l3 = TimeUnit.MINUTES.toMillis(l2);
                            if (l2 < 0L) {
                                player.sendMessage("Wrong period.");
                                break;
                            }
                            Location location = ((GameObject)object).getReflection() == ReflectionManager.DEFAULT ? new Location(((GameObject)object).getX(), ((GameObject)object).getY(), ((GameObject)object).getZ()) : new Location(((GameObject)object).getX(), ((GameObject)object).getY(), ((GameObject)object).getZ(), ((GameObject)object).getReflectionId());
                            ((Player)object).setVar(bP, String.format("%d;%s", l + l3, location.toXYZHString()), -1L);
                            ((Player)object).startUnjailTask((Player)object, l3);
                            if (player.isInStoreMode()) {
                                player.setPrivateStoreType(0);
                            }
                            ((Creature)object).teleToLocation(Location.findPointToStay((GameObject)object, Config.SERVICE_JAIL_COORDINATES, 50, 200), ReflectionManager.JAIL);
                            ((Player)object).sitDown(null);
                            if (Config.SERVICE_JAIL_BLOCK_CHARACTER) {
                                ((Creature)object).block();
                            }
                            ((Player)object).sendMessage(new CustomMessage("ServiceMovedPlayerToJail", (Player)object, new Object[0]).addNumber(TimeUnit.MILLISECONDS.toMinutes(l3)).addString((String)object2));
                            player.sendMessage("You jailed " + (String)object4 + ".");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //jail char_name period reason");
                    }
                    break;
                }
                case admin_unjail: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        object3 = World.getPlayer((String)object4);
                        if (object3 != null && (object2 = ((Player)object3).getVar(bP)) != null && !((String)object2).isEmpty()) {
                            object = Location.parseLoc(((String)object2).substring(((String)object2).indexOf(59) + 1));
                            Reflection reflection = null;
                            if (((Location)object).getH() != 0) {
                                reflection = ReflectionManager.getInstance().get(((Location)object).getH());
                            }
                            if (Config.SERVICE_JAIL_BLOCK_CHARACTER) {
                                ((Creature)object3).unblock();
                            }
                            ((Player)object3).standUp();
                            if (reflection != null) {
                                if (reflection.isCollapseStarted()) {
                                    ((Player)object3).teleToClosestTown();
                                } else {
                                    ((Creature)object3).teleToLocation(((Point2D)object).getX(), ((Point2D)object).getY(), ((Point3D)object).getZ(), reflection);
                                }
                            } else {
                                ((Creature)object3).teleToLocation(((Point2D)object).getX(), ((Point2D)object).getY(), ((Point3D)object).getZ(), ReflectionManager.DEFAULT);
                            }
                            ((Player)object3).stopUnjailTask();
                            ((Player)object3).unsetVar(bP);
                            player.sendMessage("You unjailed " + (String)object4 + ".");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                        break;
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //unjail char_name");
                    }
                }
            }
        }
        if (player.getPlayerAccess().CanBan) {
            switch (commands) {
                case admin_ban: {
                    this.c(stringTokenizer, player);
                    break;
                }
                case admin_unban: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        if (AutoBan.Banned((String)object4, 0, 0, "", player.getName())) {
                            player.sendMessage("You unban for " + (String)object4 + ".");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //unban char_name");
                    }
                    break;
                }
                case admin_accban: {
                    Player player2;
                    stringTokenizer.nextToken();
                    int n = 0;
                    int n2 = 0;
                    object2 = stringTokenizer.nextToken();
                    if (stringTokenizer.hasMoreTokens()) {
                        n2 = (int)(System.currentTimeMillis() / 1000L) + Integer.parseInt(stringTokenizer.nextToken()) * 60;
                    } else {
                        n = -100;
                    }
                    AuthServerCommunication.getInstance().sendPacket(new ChangeAccessLevel((String)object2, n, n2));
                    object = AuthServerCommunication.getInstance().getAuthedClient((String)object2);
                    if (object == null || (player2 = ((GameClient)object).getActiveChar()) == null) break;
                    player2.kick();
                    player.sendMessage("Player " + player2.getName() + " kicked.");
                    break;
                }
                case admin_accunban: {
                    stringTokenizer.nextToken();
                    object4 = stringTokenizer.nextToken();
                    AuthServerCommunication.getInstance().sendPacket(new ChangeAccessLevel((String)object4, 0, 0));
                    break;
                }
                case admin_trade_ban: {
                    return this.a(stringTokenizer, player);
                }
                case admin_trade_unban: {
                    return this.b(stringTokenizer, player);
                }
                case admin_hwidban: {
                    try {
                        object4 = null;
                        object3 = null;
                        Object object5 = object2 = player.getTarget() instanceof Player ? (Player)player.getTarget() : null;
                        if (stringTokenizer.hasMoreTokens()) {
                            stringTokenizer.nextToken();
                        }
                        if (stringTokenizer.hasMoreTokens()) {
                            object4 = stringTokenizer.nextToken();
                        }
                        if ((object4 == null || ((String)object4).isEmpty()) && object2 != null) {
                            object4 = ((Creature)object2).getName();
                        }
                        if (object4 == null || ((String)object4).isEmpty()) {
                            player.sendMessage("Please provide a character name or HWID.");
                            break;
                        }
                        object3 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken("").trim() : player.getName() + " ban this player";
                        object = null;
                        GameClient gameClient = null;
                        Player player3 = World.getPlayer((String)object4);
                        if (player3 != null) {
                            gameClient = player3.getNetConnection();
                            if (gameClient != null && gameClient.isConnected()) {
                                object = gameClient.getHwid();
                            }
                        } else {
                            object = object4;
                        }
                        if (object != null && !((String)object).isEmpty()) {
                            CGModule.getInstance().addHWIDBan(gameClient, (String)object, (String)object3);
                            LinkedList<Player> linkedList = new LinkedList<Player>();
                            for (Player player4 : GameObjectsStorage.getAllPlayersForIterate()) {
                                GameClient gameClient2;
                                if (player4 == null || (gameClient2 = player4.getNetConnection()) == null || !gameClient2.isConnected() || !((String)object).equals(gameClient2.getHwid())) continue;
                                linkedList.add(player4);
                            }
                            for (Player player5 : linkedList) {
                                player5.kick();
                                player.sendMessage("Player " + player5.getName() + " kicked.");
                            }
                            player.sendMessage("You banned hwid \"" + (String)object + "\" with comment: \"" + (String)object3 + "\".");
                            break;
                        }
                        player.sendMessage("HWID or player \"" + (String)object4 + "\" not found.");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //hwidban [char_name|hwid] [comment]");
                    }
                    break;
                }
                case admin_chatban: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        object3 = stringTokenizer.nextToken();
                        object2 = "admin_chatban " + (String)object4 + " " + (String)object3 + " ";
                        object = string.substring(((String)object2).length(), string.length());
                        if (AutoBan.ChatBan((String)object4, Integer.parseInt((String)object3), (String)object, player.getName())) {
                            player.sendMessage("You ban chat for " + (String)object4 + ".");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //chatban char_name period reason");
                    }
                    break;
                }
                case admin_nospam: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        object3 = stringTokenizer.nextToken();
                        if (AutoBan.noSpam((String)object4, Integer.parseInt((String)object3))) {
                            player.sendMessage("You ban spam chat for " + (String)object4 + " " + (String)object3 + " min");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //nospam char_name period");
                    }
                    break;
                }
                case admin_unblockspam: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        if (AutoBan.noSpam((String)object4, 0)) {
                            player.sendMessage("You unban chat spammer for " + (String)object4 + ".");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //unblockspam char_name");
                    }
                    break;
                }
                case admin_chatunban: {
                    try {
                        stringTokenizer.nextToken();
                        object4 = stringTokenizer.nextToken();
                        if (AutoBan.ChatUnBan((String)object4, player.getName())) {
                            player.sendMessage("You unban chat for " + (String)object4 + ".");
                            break;
                        }
                        player.sendMessage("Can't find char " + (String)object4 + ".");
                    }
                    catch (Exception exception) {
                        player.sendMessage("Command syntax: //chatunban char_name");
                    }
                    break;
                }
                case admin_cban: {
                    player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/cban.htm"));
                    break;
                }
                case admin_permaban: {
                    if (player.getTarget() == null || !player.getTarget().isPlayer()) {
                        Functions.sendDebugMessage(player, "Target should be set and be a player instance");
                        return false;
                    }
                    object4 = player.getTarget().getPlayer();
                    object3 = ((Player)object4).getAccountName();
                    AuthServerCommunication.getInstance().sendPacket(new ChangeAccessLevel((String)object3, -100, 0));
                    if (((Player)object4).isInOfflineMode()) {
                        ((Player)object4).setOfflineMode(false);
                    }
                    ((Player)object4).kick();
                    Functions.sendDebugMessage(player, "Player account " + (String)object3 + " is banned, player " + ((Creature)object4).getName() + " kicked.");
                }
            }
        }
        return true;
    }

    private boolean a(StringTokenizer stringTokenizer, Player player) {
        if (player.getTarget() == null || !player.getTarget().isPlayer()) {
            return false;
        }
        stringTokenizer.nextToken();
        Player player2 = (Player)player.getTarget();
        long l = -1L;
        long l2 = -1L;
        if (stringTokenizer.hasMoreTokens()) {
            l = Long.parseLong(stringTokenizer.nextToken());
            l2 = l * 24L * 60L * 60L * 1000L + System.currentTimeMillis();
        }
        player2.setVar("tradeBan", String.valueOf(l2), -1L);
        String string = player.getName() + " \u0437\u0430\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043b \u0442\u043e\u0440\u0433\u043e\u0432\u043b\u044e \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u0436\u0443 " + player2.getName() + (String)(l == -1L ? " \u043d\u0430 \u0431\u0435\u0441\u0441\u0440\u043e\u0447\u043d\u044b\u0439 \u043f\u0435\u0440\u0438\u043e\u0434." : " \u043d\u0430 " + l + " \u0434\u043d\u0435\u0439.");
        Log.add(player2.getName() + ":" + l + AdminBan.a(player2, player2.getPrivateStoreType()), "tradeBan", player);
        if (player2.isInOfflineMode()) {
            player2.setOfflineMode(false);
            player2.kick();
        } else if (player2.isInStoreMode()) {
            player2.setPrivateStoreType(0);
            player2.standUp();
            player2.broadcastCharInfo();
            player2.getBuyList().clear();
        }
        if (Config.BANCHAT_ANNOUNCE_FOR_ALL_WORLD) {
            Announcements.getInstance().announceToAll(string);
        } else {
            Announcements.shout(player, string, ChatType.CRITICAL_ANNOUNCE);
        }
        return true;
    }

    private static String a(Player player, int n) {
        switch (n) {
            case 3: {
                List<TradeItem> list = player.getBuyList();
                if (list == null || list.isEmpty()) {
                    return "";
                }
                Object object = ":buy:";
                for (TradeItem tradeItem : list) {
                    object = (String)object + tradeItem.getItemId() + ";" + tradeItem.getCount() + ";" + tradeItem.getOwnersPrice() + ":";
                }
                return object;
            }
            case 1: 
            case 8: {
                List<TradeItem> list = player.getSellList();
                if (list == null || list.isEmpty()) {
                    return "";
                }
                Object object = ":sell:";
                for (TradeItem tradeItem : list) {
                    object = (String)object + tradeItem.getItemId() + ";" + tradeItem.getCount() + ";" + tradeItem.getOwnersPrice() + ":";
                }
                return object;
            }
            case 5: {
                List<ManufactureItem> list = player.getCreateList();
                if (list == null || list.isEmpty()) {
                    return "";
                }
                Object object = ":mf:";
                for (ManufactureItem manufactureItem : list) {
                    object = (String)object + manufactureItem.getRecipeId() + ";" + manufactureItem.getCost() + ":";
                }
                return object;
            }
        }
        return "";
    }

    private boolean b(StringTokenizer stringTokenizer, Player player) {
        if (player.getTarget() == null || !player.getTarget().isPlayer()) {
            return false;
        }
        Player player2 = (Player)player.getTarget();
        player2.unsetVar("tradeBan");
        if (Config.BANCHAT_ANNOUNCE_FOR_ALL_WORLD) {
            Announcements.getInstance().announceToAll(player.getName() + " \u0440\u0430\u0437\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043b \u0442\u043e\u0440\u0433\u043e\u0432\u043b\u044e \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u0436\u0443 " + player2.getName() + ".");
        } else {
            Announcements.shout(player, player.getName() + " \u0440\u0430\u0437\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043b \u0442\u043e\u0440\u0433\u043e\u0432\u043b\u044e \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u0436\u0443 " + player2.getName() + ".", ChatType.CRITICAL_ANNOUNCE);
        }
        Log.add(player + " \u0440\u0430\u0437\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043b \u0442\u043e\u0440\u0433\u043e\u0432\u043b\u044e \u043f\u0435\u0440\u0441\u043e\u043d\u0430\u0436\u0443 " + player2 + ".", "tradeBan", player);
        return true;
    }

    private boolean c(StringTokenizer stringTokenizer, Player player) {
        try {
            Player player2;
            stringTokenizer.nextToken();
            String string = stringTokenizer.nextToken();
            int n = 0;
            Object object = "";
            if (stringTokenizer.hasMoreTokens()) {
                n = Integer.parseInt(stringTokenizer.nextToken());
            }
            if (stringTokenizer.hasMoreTokens()) {
                object = "admin_ban " + string + " " + n + " ";
                while (stringTokenizer.hasMoreTokens()) {
                    object = (String)object + stringTokenizer.nextToken() + " ";
                }
                ((String)object).trim();
            }
            if ((player2 = World.getPlayer(string)) != null) {
                player2.sendMessage(new CustomMessage("admincommandhandlers.YoureBannedByGM", player2, new Object[0]));
                player2.setAccessLevel(-100);
                AutoBan.Banned(player2, n, (String)object, player.getName());
                player2.kick();
                player.sendMessage("You banned " + player2.getName());
            } else if (AutoBan.Banned(string, -100, n, (String)object, player.getName())) {
                player.sendMessage("You banned " + string);
            } else {
                player.sendMessage("Can't find char: " + string);
            }
        }
        catch (Exception exception) {
            player.sendMessage("Command syntax: //ban char_name days reason");
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_ban = new Commands();
        public static final /* enum */ Commands admin_unban = new Commands();
        public static final /* enum */ Commands admin_hwidban = new Commands();
        public static final /* enum */ Commands admin_cban = new Commands();
        public static final /* enum */ Commands admin_chatban = new Commands();
        public static final /* enum */ Commands admin_nospam = new Commands();
        public static final /* enum */ Commands admin_unblockspam = new Commands();
        public static final /* enum */ Commands admin_chatunban = new Commands();
        public static final /* enum */ Commands admin_accban = new Commands();
        public static final /* enum */ Commands admin_accunban = new Commands();
        public static final /* enum */ Commands admin_trade_ban = new Commands();
        public static final /* enum */ Commands admin_trade_unban = new Commands();
        public static final /* enum */ Commands admin_jail = new Commands();
        public static final /* enum */ Commands admin_unjail = new Commands();
        public static final /* enum */ Commands admin_permaban = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_ban, admin_unban, admin_hwidban, admin_cban, admin_chatban, admin_nospam, admin_unblockspam, admin_chatunban, admin_accban, admin_accunban, admin_trade_ban, admin_trade_unban, admin_jail, admin_unjail, admin_permaban};
        }

        static {
            a = Commands.a();
        }
    }
}
