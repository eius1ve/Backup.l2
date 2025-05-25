/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;

public class AdminSS
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().Menu) {
            return false;
        }
        switch (commands) {
            case admin_ssq_change: {
                if (stringArray.length > 2) {
                    int n = Integer.parseInt(stringArray[1]);
                    int n2 = Integer.parseInt(stringArray[2]);
                    SevenSigns.getInstance().changePeriod(n, n2 * 60);
                    break;
                }
                if (stringArray.length > 1) {
                    int n = Integer.parseInt(stringArray[1]);
                    SevenSigns.getInstance().changePeriod(n);
                    break;
                }
                SevenSigns.getInstance().changePeriod();
                break;
            }
            case admin_ssq_time: {
                if (stringArray.length <= 1) break;
                int n = Integer.parseInt(stringArray[1]);
                SevenSigns.getInstance().setTimeToNextPeriodChange(n);
                break;
            }
            case admin_ssq_cabal: {
                if (stringArray.length > 3) {
                    Player player2 = GameObjectsStorage.getPlayer(stringArray[1]);
                    int n = Integer.parseInt(stringArray[2]);
                    int n3 = Integer.parseInt(stringArray[3]);
                    SevenSigns.getInstance().setPlayerInfo(player2.getObjectId(), n, n3);
                    String[] stringArray2 = new String[]{"null", "dusk", "dawn"};
                    String[] stringArray3 = new String[]{"null", "avarice", "gnosis", "strife"};
                    String string2 = stringArray2[n];
                    String string3 = stringArray3[n3];
                    player.sendMessage("Player " + player2 + " added to cabal " + string2 + " with seal " + string3);
                    break;
                }
                player.sendMessage("Command: //ssq_cabal PlayerName cabal(1 - dusk,2 - dawn) seal(1 - avarice, 2 - gnosis, 3 - strife)");
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
        public static final /* enum */ Commands admin_ssq_change = new Commands();
        public static final /* enum */ Commands admin_ssq_time = new Commands();
        public static final /* enum */ Commands admin_ssq_cabal = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_ssq_change, admin_ssq_time, admin_ssq_cabal};
        }

        static {
            a = Commands.a();
        }
    }
}
