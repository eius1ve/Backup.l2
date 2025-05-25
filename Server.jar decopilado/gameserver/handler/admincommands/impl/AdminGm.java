/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;

public class AdminGm
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (Boolean.TRUE.booleanValue()) {
            return false;
        }
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        switch (commands) {
            case admin_gm: {
                this.v(player);
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void v(Player player) {
        if (player.isGM()) {
            player.getPlayerAccess().IsGM = false;
            player.sendMessage("You no longer have GM status.");
        } else {
            player.getPlayerAccess().IsGM = true;
            player.sendMessage("You have GM status now.");
        }
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_gm = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_gm};
        }

        static {
            a = Commands.a();
        }
    }
}
