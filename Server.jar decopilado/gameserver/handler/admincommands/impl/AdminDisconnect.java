/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminDisconnect
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanKick) {
            return false;
        }
        switch (commands) {
            case admin_disconnect: 
            case admin_kick: {
                Player player2;
                if (stringArray.length == 1) {
                    GameObject gameObject = player.getTarget();
                    if (gameObject == null) {
                        player.sendMessage("Select character or specify player name.");
                        break;
                    }
                    if (!gameObject.isPlayer()) {
                        player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                        break;
                    }
                    player2 = (Player)gameObject;
                } else {
                    player2 = World.getPlayer(stringArray[1]);
                    if (player2 == null) {
                        player.sendMessage("Character " + stringArray[1] + " not found in game.");
                        break;
                    }
                }
                if (player2.getObjectId() == player.getObjectId()) {
                    player.sendMessage("You can't logout your character.");
                    break;
                }
                player.sendMessage("Character " + player2.getName() + " disconnected from server.");
                if (player2.isInOfflineMode()) {
                    player2.setOfflineMode(false);
                    player2.kick();
                    return true;
                }
                if (Config.ALLOW_CURSED_WEAPONS && Config.DROP_CURSED_WEAPONS_ON_KICK && player2.isCursedWeaponEquipped()) {
                    player2.setPvpFlag(0);
                    CursedWeaponsManager.getInstance().dropPlayer(player2);
                }
                player2.sendMessage(new CustomMessage("admincommandhandlers.AdminDisconnect.YoureKickedByGM", player2, new Object[0]));
                player2.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_BEEN_DISCONNECTED_FROM_THE_SERVER_PLEASE_LOGIN_AGAIN);
                ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

                    @Override
                    public void runImpl() throws Exception {
                        player2.kick();
                    }
                }, 500L);
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
        public static final /* enum */ Commands admin_disconnect = new Commands();
        public static final /* enum */ Commands admin_kick = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_disconnect, admin_kick};
        }

        static {
            a = Commands.a();
        }
    }
}
