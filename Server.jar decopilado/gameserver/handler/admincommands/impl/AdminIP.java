/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;

public class AdminIP
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        switch (commands) {
            case admin_charip: {
                if (stringArray.length != 2) {
                    player.sendMessage("Command syntax: //charip <char_name>");
                    player.sendMessage(" Gets character's IP.");
                    break;
                }
                if (!player.getPlayerAccess().CanSeeIp) {
                    player.sendMessage("You do not have the permission to view the IP.");
                    break;
                }
                Player player2 = World.getPlayer(stringArray[1]);
                if (player2 == null) {
                    player.sendMessage("Character " + stringArray[1] + " not found.");
                    break;
                }
                String string2 = player2.getIP();
                if (string2.equalsIgnoreCase("<not connected>")) {
                    player.sendMessage("Character " + stringArray[1] + " not found.");
                    break;
                }
                player.sendMessage("Character's " + player2.getName() + " IP: " + string2);
                break;
            }
            case admin_charhwid: {
                if (stringArray.length != 2) {
                    player.sendMessage("Command syntax: //charhwid <char_name>");
                    player.sendMessage(" Gets character's IP.");
                    break;
                }
                if (!player.getPlayerAccess().CanSeeHwid) {
                    player.sendMessage("You do not have the permission to view the HWID.");
                    break;
                }
                Player player3 = World.getPlayer(stringArray[1]);
                if (player3 == null) {
                    player.sendMessage("Character " + stringArray[1] + " not found.");
                    break;
                }
                String string3 = player3.getNetConnection().getHwid();
                if (string3 == null || string3.isEmpty()) {
                    player.sendMessage(player3.getName() + " Character " + stringArray[1] + " HWID not found or HWID is Empty.");
                    break;
                }
                player.sendMessage("Character's " + player3.getName() + " IP: " + string3);
                break;
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_charip = new Commands();
        public static final /* enum */ Commands admin_charhwid = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_charip, admin_charhwid};
        }

        static {
            a = Commands.a();
        }
    }
}
