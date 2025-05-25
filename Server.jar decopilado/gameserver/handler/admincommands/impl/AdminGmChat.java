/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.tables.GmListTable;

public class AdminGmChat
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanAnnounce) {
            return false;
        }
        switch (commands) {
            case admin_gmchat: {
                try {
                    String string2 = string.replaceFirst(Commands.admin_gmchat.name(), "");
                    Say2 say2 = new Say2(0, ChatType.ALLIANCE, player.getName(), string2);
                    GmListTable.broadcastToGMs(say2);
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
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
        public static final /* enum */ Commands admin_gmchat = new Commands();
        public static final /* enum */ Commands admin_snoop = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_gmchat, admin_snoop};
        }

        static {
            a = Commands.a();
        }
    }
}
