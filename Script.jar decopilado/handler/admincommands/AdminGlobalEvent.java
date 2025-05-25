/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.events.GlobalEvent
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 */
package handler.admincommands;

import handler.admincommands.ScriptAdminCommand;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminGlobalEvent
extends ScriptAdminCommand {
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        switch (commands) {
            case admin_list_events: {
                GameObject gameObject = player.getTarget();
                if (gameObject == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    break;
                }
                for (GlobalEvent globalEvent : gameObject.getEvents()) {
                    player.sendMessage("- " + globalEvent.toString());
                }
                break;
            }
        }
        return false;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_list_events = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_list_events};
        }

        static {
            a = Commands.a();
        }
    }
}
