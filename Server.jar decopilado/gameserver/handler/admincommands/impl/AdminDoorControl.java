/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminDoorControl
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().Door) {
            return false;
        }
        switch (commands) {
            case admin_open: {
                GameObject gameObject = stringArray.length > 1 ? World.getAroundObjectById(player, Integer.parseInt(stringArray[1])) : player.getTarget();
                if (gameObject != null && gameObject.isDoor()) {
                    ((DoorInstance)gameObject).openMe();
                    break;
                }
                player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                break;
            }
            case admin_close: {
                GameObject gameObject = stringArray.length > 1 ? World.getAroundObjectById(player, Integer.parseInt(stringArray[1])) : player.getTarget();
                if (gameObject != null && gameObject.isDoor()) {
                    ((DoorInstance)gameObject).closeMe();
                    break;
                }
                player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
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
        public static final /* enum */ Commands admin_open = new Commands();
        public static final /* enum */ Commands admin_close = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_open, admin_close};
        }

        static {
            a = Commands.a();
        }
    }
}
