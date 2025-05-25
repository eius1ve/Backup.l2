/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;

public class AdminEvents
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().IsEventGm) {
            return false;
        }
        switch (commands) {
            case admin_events: {
                if (stringArray.length == 1) {
                    player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/events/events.htm"));
                    break;
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/events/" + stringArray[1].trim()));
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
        public static final /* enum */ Commands admin_events = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_events};
        }

        static {
            a = Commands.a();
        }
    }
}
