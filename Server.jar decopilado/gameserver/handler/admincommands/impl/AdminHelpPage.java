/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;

public class AdminHelpPage
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().Menu) {
            return false;
        }
        switch (commands) {
            case admin_showhtml: {
                if (stringArray.length != 2) {
                    player.sendMessage("Usage: //showhtml <file>");
                    return false;
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/" + stringArray[1]));
            }
        }
        return true;
    }

    public static void showHelpHtml(Player player, String string) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        npcHtmlMessage.setHtml(string);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_showhtml = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_showhtml};
        }

        static {
            a = Commands.a();
        }
    }
}
