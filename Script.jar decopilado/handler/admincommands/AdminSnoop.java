/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.listener.actor.player.impl.SnoopPlayerSayListener
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 */
package handler.admincommands;

import handler.admincommands.ScriptAdminCommand;
import l2.commons.listener.Listener;
import l2.gameserver.listener.actor.player.impl.SnoopPlayerSayListener;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminSnoop
extends ScriptAdminCommand {
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        if (!player.getPlayerAccess().CanViewChar) {
            return false;
        }
        if (stringArray.length != 3 || !stringArray[2].equalsIgnoreCase("on") && !stringArray[2].equalsIgnoreCase("off")) {
            player.sendMessage("USAGE: //snoop [TARGET_NAME] [on|off]");
            return false;
        }
        boolean bl = stringArray[2].equalsIgnoreCase("on");
        if (bl) {
            String string2 = player.getVar("snoop_target");
            if (string2 == null) {
                Player player2 = GameObjectsStorage.getPlayer((String)stringArray[1]);
                if (player2 == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_PLAYER_IS_NOT_ONLINE);
                    return false;
                }
                player2.addListener((Listener)new SnoopPlayerSayListener(player));
                player.getVars().set((Object)"snoop_target", stringArray[1]);
                player.sendMessage("SNOOP: you snoop target: " + stringArray[1]);
                return true;
            }
            player.sendMessage("SNOOP: you already snooped target: " + string2);
        } else {
            String string3 = (String)player.getVars().remove((Object)"snoop_target");
            if (string3 == null) {
                player.sendMessage("SNOOP: you not snoop any target");
            } else {
                Player player3 = GameObjectsStorage.getPlayer((String)string3);
                if (player3 == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_PLAYER_IS_NOT_ONLINE);
                } else {
                    for (SnoopPlayerSayListener snoopPlayerSayListener : player3.getListeners().getListeners(SnoopPlayerSayListener.class)) {
                        if (snoopPlayerSayListener.getOwner() != player) continue;
                        player3.removeListener((Listener)snoopPlayerSayListener);
                        break;
                    }
                }
                player.sendMessage("SNOOP: you cancel snoop for target: " + string3);
            }
        }
        return false;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_snoop = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_snoop};
        }

        static {
            a = Commands.a();
        }
    }
}
