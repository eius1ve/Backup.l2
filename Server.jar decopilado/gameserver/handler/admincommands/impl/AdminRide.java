/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class AdminRide
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Player player2;
        Commands commands = (Commands)enum_;
        Player player3 = player.getTarget() != null ? player.getTarget().getPlayer() : (player2 = stringArray.length > 1 ? GameObjectsStorage.getPlayer(stringArray[1]) : null);
        if (!player.getPlayerAccess().Rider) {
            return false;
        }
        switch (commands) {
            case admin_ride: {
                if (player2.isMounted() || player2.getPet() != null) {
                    player.sendMessage("Already Have a Pet or Mounted.");
                    return false;
                }
                if (stringArray.length != 2) {
                    player.sendMessage("Incorrect id.");
                    return false;
                }
                int n = Integer.parseInt(stringArray[1]);
                player2.setMount(n, 0);
                break;
            }
            case admin_ride_wyvern: 
            case admin_wr: {
                if (player2.isMounted() || player2.getPet() != null) {
                    player.sendMessage("Already Have a Pet or Mounted.");
                    return false;
                }
                player2.setMount(12621, 0);
                break;
            }
            case admin_ride_strider: 
            case admin_sr: {
                if (player2.isMounted() || player2.getPet() != null) {
                    player.sendMessage("Already Have a Pet or Mounted.");
                    return false;
                }
                player2.setMount(12526, 0);
                break;
            }
            case admin_unride: 
            case admin_ur: {
                player2.setMount(0, 0, 0);
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
        public static final /* enum */ Commands admin_ride = new Commands();
        public static final /* enum */ Commands admin_ride_wyvern = new Commands();
        public static final /* enum */ Commands admin_ride_strider = new Commands();
        public static final /* enum */ Commands admin_unride = new Commands();
        public static final /* enum */ Commands admin_wr = new Commands();
        public static final /* enum */ Commands admin_sr = new Commands();
        public static final /* enum */ Commands admin_ur = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_ride, admin_ride_wyvern, admin_ride_strider, admin_unride, admin_wr, admin_sr, admin_ur};
        }

        static {
            a = Commands.a();
        }
    }
}
