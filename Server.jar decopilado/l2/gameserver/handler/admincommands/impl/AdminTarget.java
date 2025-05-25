/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;

public class AdminTarget
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanViewChar) {
            return false;
        }
        try {
            String string2 = stringArray[1];
            Player player2 = World.getPlayer(string2);
            if (player2 != null && ((GameObject)player2).isPlayer()) {
                ((GameObject)player2).onAction(player, false);
            } else {
                player.sendMessage("Player " + string2 + " not found");
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            player.sendMessage("Please specify correct name.");
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_target = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_target};
        }

        static {
            a = Commands.a();
        }
    }
}
