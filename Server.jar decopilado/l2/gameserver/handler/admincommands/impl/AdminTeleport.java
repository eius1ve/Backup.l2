/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import l2.commons.dbutils.DbUtils;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Util;

public class AdminTeleport
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        int n;
        int n2;
        Player player2;
        String string2;
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanTeleport) {
            return false;
        }
        switch (commands) {
            case admin_show_moves: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/teleports.htm"));
                break;
            }
            case admin_show_moves_other: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/tele/other.htm"));
                break;
            }
            case admin_show_teleport: {
                this.L(player);
                break;
            }
            case admin_teleport_to_character: {
                this.a(player, player.getTarget());
                break;
            }
            case admin_teleport_to: 
            case admin_teleportto: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //teleportto charName");
                    return false;
                }
                string2 = Util.joinStrings(" ", stringArray, 1);
                player2 = GameObjectsStorage.getPlayer(string2);
                if (player2 == null) {
                    player.sendMessage("Player '" + string2 + "' not found in world");
                    return false;
                }
                this.a(player, player2);
                break;
            }
            case admin_move_to: 
            case admin_moveto: 
            case admin_teleport: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //teleport x y z [ref]");
                    return false;
                }
                n2 = Integer.parseInt(stringArray[1]);
                n = Integer.parseInt(stringArray[2]);
                int n3 = stringArray.length > 3 ? Integer.parseInt(stringArray[3]) : 0;
                int n4 = ArrayUtils.valid(stringArray, 4) != null && !ArrayUtils.valid(stringArray, 4).isEmpty() ? Integer.parseInt(stringArray[4]) : 0;
                String string3 = n2 + " " + n + " " + n3;
                this.a(player, player, string3, n4);
                break;
            }
            case admin_walk: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //walk x y z");
                    return false;
                }
                try {
                    player.moveToLocation(Location.parseLoc(Util.joinStrings(" ", stringArray, 1)), 0, true);
                    break;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    player.sendMessage("USAGE: //walk x y z");
                    return false;
                }
            }
            case admin_gonorth: 
            case admin_gosouth: 
            case admin_goeast: 
            case admin_gowest: 
            case admin_goup: 
            case admin_godown: {
                int n5 = stringArray.length < 2 ? 150 : Integer.parseInt(stringArray[1]);
                int n6 = player.getX();
                int n7 = player.getY();
                int n8 = player.getZ();
                if (commands == Commands.admin_goup) {
                    n8 += n5;
                } else if (commands == Commands.admin_godown) {
                    n8 -= n5;
                } else if (commands == Commands.admin_goeast) {
                    n6 += n5;
                } else if (commands == Commands.admin_gowest) {
                    n6 -= n5;
                } else if (commands == Commands.admin_gosouth) {
                    n7 += n5;
                } else if (commands == Commands.admin_gonorth) {
                    n7 -= n5;
                }
                player.teleToLocation(n6, n7, n8);
                this.K(player);
                break;
            }
            case admin_tele: {
                this.K(player);
                break;
            }
            case admin_teleto: 
            case admin_tele_to: 
            case admin_instant_move: {
                if (stringArray.length > 1 && stringArray[1].equalsIgnoreCase("r")) {
                    player.setTeleMode(2);
                    break;
                }
                if (stringArray.length > 1 && stringArray[1].equalsIgnoreCase("end")) {
                    player.setTeleMode(0);
                    break;
                }
                player.setTeleMode(1);
                break;
            }
            case admin_tonpc: 
            case admin_to_npc: {
                NpcInstance npcInstance;
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //tonpc npcId|npcName");
                    return false;
                }
                String string4 = Util.joinStrings(" ", stringArray, 1);
                try {
                    npcInstance = GameObjectsStorage.getByNpcId(Integer.parseInt(string4));
                    if (npcInstance != null) {
                        this.a(player, npcInstance);
                        return true;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                npcInstance = GameObjectsStorage.getNpc(string4);
                if (npcInstance != null) {
                    this.a(player, npcInstance);
                    return true;
                }
                player.sendMessage("Npc " + string4 + " not found");
                break;
            }
            case admin_toobject: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //toobject objectId");
                    return false;
                }
                Integer n9 = Integer.parseInt(stringArray[1]);
                GameObject gameObject = GameObjectsStorage.findObject(n9);
                if (gameObject != null) {
                    this.a(player, gameObject);
                    return true;
                }
                player.sendMessage("Object " + n9 + " not found");
            }
        }
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        switch (commands) {
            case admin_teleport_character: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //teleport_character x y z");
                    return false;
                }
                this.k(player, Util.joinStrings(" ", stringArray, 1));
                this.L(player);
                break;
            }
            case admin_recall: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //recall charName");
                    return false;
                }
                string2 = Util.joinStrings(" ", stringArray, 1);
                player2 = GameObjectsStorage.getPlayer(string2);
                if (player2 != null) {
                    this.a(player, player2, player.getLoc(), player.getReflectionId());
                    return true;
                }
                n2 = CharacterDAO.getInstance().getObjectIdByName(string2);
                if (n2 > 0) {
                    this.a(n2, player.getLoc());
                    player.sendMessage(string2 + " is offline. Offline teleport used...");
                    break;
                }
                player.sendMessage("->" + string2 + "<- is incorrect.");
                break;
            }
            case admin_sendhome: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //sendhome");
                    return false;
                }
                this.j(player, Util.joinStrings(" ", stringArray, 1));
                break;
            }
            case admin_setref: {
                if (stringArray.length < 2) {
                    player.sendMessage("Usage: //setref <reflection>");
                    return false;
                }
                n = Integer.parseInt(stringArray[1]);
                if (n != 0 && ReflectionManager.getInstance().get(n) == null) {
                    player.sendMessage("Reflection <" + n + "> not found.");
                    return false;
                }
                GameObject gameObject = player;
                GameObject gameObject2 = player.getTarget();
                if (gameObject2 != null) {
                    gameObject = gameObject2;
                }
                gameObject.setReflection(n);
                gameObject.decayMe();
                gameObject.spawnMe();
                break;
            }
            case admin_getref: {
                if (stringArray.length < 2) {
                    player.sendMessage("Usage: //getref <char_name>");
                    return false;
                }
                Player player3 = GameObjectsStorage.getPlayer(stringArray[1]);
                if (player3 == null) {
                    player.sendMessage("Player '" + stringArray[1] + "' not found in world");
                    return false;
                }
                player.sendMessage("Player '" + stringArray[1] + "' in reflection: " + player.getReflectionId() + ", name: " + player.getReflection().getName());
                break;
            }
            case admin_recall_party: {
                if (stringArray.length < 2) {
                    player.sendMessage("Usage: //recall_party <party_leader_name>");
                    return false;
                }
                Player player4 = GameObjectsStorage.getPlayer(stringArray[1]);
                if (player4 == null) {
                    player.sendMessage("Player '" + stringArray[1] + "' not found in world");
                    return false;
                }
                Party party = player4.getParty();
                if (party == null) {
                    player.sendMessage("Player '" + stringArray[1] + "' have no party.");
                    return false;
                }
                for (Player player5 : party.getPartyMembers()) {
                    Location location = Location.findPointToStay(player, 128);
                    player5.teleToLocation(location, player.getReflection());
                    player5.sendMessage("Your party have been recalled by Admin.");
                }
                player.sendMessage("Party recalled.");
            }
        }
        if (!player.getPlayerAccess().CanEditNPC) {
            return false;
        }
        switch (commands) {
            case admin_recall_npc: {
                this.M(player);
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void K(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><title>Teleport Menu</title>");
        stringBuilder.append("<body>");
        stringBuilder.append("<br>");
        stringBuilder.append("<center><table>");
        stringBuilder.append("<tr><td><button value=\"  \" action=\"bypass -h admin_tele\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"North\" action=\"bypass -h admin_gonorth\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Up\" action=\"bypass -h admin_goup\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("<tr><td><button value=\"West\" action=\"bypass -h admin_gowest\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"  \" action=\"bypass -h admin_tele\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"East\" action=\"bypass -h admin_goeast\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("<tr><td><button value=\"  \" action=\"bypass -h admin_tele\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"South\" action=\"bypass -h admin_gosouth\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Down\" action=\"bypass -h admin_godown\" width=70 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("</table></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void L(Player player) {
        GameObject gameObject = player.getTarget();
        Player player2 = null;
        if (!gameObject.isPlayer()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        player2 = (Player)gameObject;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><title>Teleport Character</title>");
        stringBuilder.append("<body>");
        stringBuilder.append("The character you will teleport is " + player2.getName() + ".");
        stringBuilder.append("<br>");
        stringBuilder.append("Co-ordinate x");
        stringBuilder.append("<edit var=\"char_cord_x\" width=110>");
        stringBuilder.append("Co-ordinate y");
        stringBuilder.append("<edit var=\"char_cord_y\" width=110>");
        stringBuilder.append("Co-ordinate z");
        stringBuilder.append("<edit var=\"char_cord_z\" width=110>");
        stringBuilder.append("<button value=\"Teleport\" action=\"bypass -h admin_teleport_character $char_cord_x $char_cord_y $char_cord_z\" width=60 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">");
        stringBuilder.append("<button value=\"Teleport near you\" action=\"bypass -h admin_teleport_character " + player.getX() + " " + player.getY() + " " + player.getZ() + "\" width=115 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">");
        stringBuilder.append("<center><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void a(Player player, Player player2, String string, int n) {
        try {
            this.a(player, player2, Location.parseLoc(string), n);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            player.sendMessage("You must define 3 coordinates required to teleport");
            return;
        }
    }

    private void j(Player player, String string) {
        Player player2 = GameObjectsStorage.getPlayer(string);
        if (player2 != null) {
            player2.teleToClosestTown();
            player2.sendMessage("The GM has sent you to the nearest town");
            player.sendMessage("You have sent " + string + " to the nearest town");
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
        }
    }

    private void a(Player player, Player player2, Location location, int n) {
        if (!player2.equals(player)) {
            player2.sendMessage("Admin is teleporting you.");
        }
        player2.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        player2.teleToLocation(location, n);
        if (player2.equals(player)) {
            player.sendMessage("You have been teleported to " + location + ", reflection id: " + n);
        }
    }

    private void k(Player player, String string) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        if (gameObject.getObjectId() == player.getObjectId()) {
            player.sendMessage("You cannot teleport yourself.");
            return;
        }
        this.a(player, (Player)gameObject, string, player.getReflectionId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void a(int n, Location location) {
        if (n == 0) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `x`=?,`y`=?,`z`=? WHERE `obj_Id`=? LIMIT 1");
            preparedStatement.setInt(1, location.x);
            preparedStatement.setInt(2, location.y);
            preparedStatement.setInt(3, location.z);
            preparedStatement.setInt(4, n);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            DbUtils.closeQuietly(connection, preparedStatement);
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    private void a(Player player, GameObject gameObject) {
        if (gameObject == null) {
            return;
        }
        player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        player.teleToLocation(gameObject.getLoc(), gameObject.getReflectionId());
        player.sendMessage("You have teleported to " + gameObject);
    }

    private void M(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject != null && gameObject.isNpc()) {
            gameObject.setLoc(player.getLoc());
            ((NpcInstance)gameObject).broadcastCharInfo();
            player.sendMessage("You teleported npc " + gameObject.getName() + " to " + player.getLoc().toString() + ".");
        } else {
            player.sendMessage("Target is't npc.");
        }
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_show_moves = new Commands();
        public static final /* enum */ Commands admin_show_moves_other = new Commands();
        public static final /* enum */ Commands admin_show_teleport = new Commands();
        public static final /* enum */ Commands admin_teleport_to_character = new Commands();
        public static final /* enum */ Commands admin_teleportto = new Commands();
        public static final /* enum */ Commands admin_teleport_to = new Commands();
        public static final /* enum */ Commands admin_move_to = new Commands();
        public static final /* enum */ Commands admin_moveto = new Commands();
        public static final /* enum */ Commands admin_teleport = new Commands();
        public static final /* enum */ Commands admin_teleport_character = new Commands();
        public static final /* enum */ Commands admin_recall = new Commands();
        public static final /* enum */ Commands admin_walk = new Commands();
        public static final /* enum */ Commands admin_recall_npc = new Commands();
        public static final /* enum */ Commands admin_gonorth = new Commands();
        public static final /* enum */ Commands admin_gosouth = new Commands();
        public static final /* enum */ Commands admin_goeast = new Commands();
        public static final /* enum */ Commands admin_gowest = new Commands();
        public static final /* enum */ Commands admin_goup = new Commands();
        public static final /* enum */ Commands admin_godown = new Commands();
        public static final /* enum */ Commands admin_tele = new Commands();
        public static final /* enum */ Commands admin_teleto = new Commands();
        public static final /* enum */ Commands admin_tele_to = new Commands();
        public static final /* enum */ Commands admin_instant_move = new Commands();
        public static final /* enum */ Commands admin_tonpc = new Commands();
        public static final /* enum */ Commands admin_to_npc = new Commands();
        public static final /* enum */ Commands admin_toobject = new Commands();
        public static final /* enum */ Commands admin_setref = new Commands();
        public static final /* enum */ Commands admin_getref = new Commands();
        public static final /* enum */ Commands admin_recall_party = new Commands();
        public static final /* enum */ Commands admin_sendhome = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_show_moves, admin_show_moves_other, admin_show_teleport, admin_teleport_to_character, admin_teleportto, admin_teleport_to, admin_move_to, admin_moveto, admin_teleport, admin_teleport_character, admin_recall, admin_walk, admin_recall_npc, admin_gonorth, admin_gosouth, admin_goeast, admin_gowest, admin_goup, admin_godown, admin_tele, admin_teleto, admin_tele_to, admin_instant_move, admin_tonpc, admin_to_npc, admin_toobject, admin_setref, admin_getref, admin_recall_party, admin_sendhome};
        }

        static {
            a = Commands.a();
        }
    }
}
