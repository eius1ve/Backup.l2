/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExEnSoulExtractionShow;
import l2.gameserver.network.l2.s2c.ExShowEnsoulWindow;

public class AdminEnsoul
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        switch (commands) {
            case admin_ensoul: {
                player.sendPacket((IStaticPacket)ExShowEnsoulWindow.STATIC);
                break;
            }
            case admin_ensoul_extract: {
                player.sendPacket((IStaticPacket)ExEnSoulExtractionShow.STATIC);
            }
        }
        return false;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_ensoul = new Commands();
        public static final /* enum */ Commands admin_ensoul_extract = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_ensoul, admin_ensoul_extract};
        }

        static {
            a = Commands.a();
        }
    }
}
