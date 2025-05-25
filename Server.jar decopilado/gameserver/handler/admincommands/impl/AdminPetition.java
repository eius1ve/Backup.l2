/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.PetitionManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.apache.commons.lang3.math.NumberUtils;

public class AdminPetition
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        int n = NumberUtils.toInt((String)(stringArray.length > 1 ? stringArray[1] : "-1"), (int)-1);
        Commands commands = (Commands)enum_;
        switch (commands) {
            case admin_view_petitions: {
                PetitionManager.getInstance().sendPendingPetitionList(player);
                break;
            }
            case admin_view_petition: {
                PetitionManager.getInstance().viewPetition(player, n);
                break;
            }
            case admin_accept_petition: {
                if (n < 0) {
                    player.sendMessage("Usage: //accept_petition id");
                    return false;
                }
                if (PetitionManager.getInstance().isPlayerInConsultation(player)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_MAY_ONLY_SUBMIT_ONE_PETITION_ACTIVE_AT_A_TIME));
                    return true;
                }
                if (PetitionManager.getInstance().isPetitionInProcess(n)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PETITION_IS_BEING_PROCESSED));
                    return true;
                }
                if (PetitionManager.getInstance().acceptPetition(player, n)) break;
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.NOT_UNDER_PETITION_CONSULTATION));
                break;
            }
            case admin_reject_petition: {
                if (n < 0) {
                    player.sendMessage("Usage: //accept_petition id");
                    return false;
                }
                if (!PetitionManager.getInstance().rejectPetition(player, n)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.FAILED_TO_CANCEL_PETITION));
                }
                PetitionManager.getInstance().sendPendingPetitionList(player);
                break;
            }
            case admin_reset_petitions: {
                if (PetitionManager.getInstance().isPetitionInProcess()) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_PETITION_IS_BEING_PROCESSED));
                    return false;
                }
                PetitionManager.getInstance().clearPendingPetitions();
                PetitionManager.getInstance().sendPendingPetitionList(player);
                break;
            }
            case admin_force_peti: {
                if (string.length() < 11) {
                    player.sendMessage("Usage: //force_peti text");
                    return false;
                }
                try {
                    GameObject gameObject = player.getTarget();
                    if (gameObject == null || !(gameObject instanceof Player)) {
                        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.INVALID_TARGET));
                        return false;
                    }
                    Player player2 = (Player)gameObject;
                    n = PetitionManager.getInstance().submitPetition(player2, string.substring(10), 9);
                    PetitionManager.getInstance().acceptPetition(player, n);
                    break;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    player.sendMessage("Usage: //force_peti text");
                    return false;
                }
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
        public static final /* enum */ Commands admin_view_petitions = new Commands();
        public static final /* enum */ Commands admin_view_petition = new Commands();
        public static final /* enum */ Commands admin_accept_petition = new Commands();
        public static final /* enum */ Commands admin_reject_petition = new Commands();
        public static final /* enum */ Commands admin_reset_petitions = new Commands();
        public static final /* enum */ Commands admin_force_peti = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_view_petitions, admin_view_petition, admin_accept_petition, admin_reject_petition, admin_reset_petitions, admin_force_peti};
        }

        static {
            a = Commands.a();
        }
    }
}
