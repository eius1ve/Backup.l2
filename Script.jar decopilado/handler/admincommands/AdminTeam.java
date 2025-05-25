/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 */
package handler.admincommands;

import handler.admincommands.ScriptAdminCommand;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminTeam
extends ScriptAdminCommand {
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        GameObject gameObject;
        TeamType teamType = TeamType.NONE;
        if (stringArray.length >= 2) {
            gameObject = TeamType.values();
            int n = ((GameObject)gameObject).length;
            for (int i = 0; i < n; ++i) {
                GameObject gameObject2 = gameObject[i];
                if (!stringArray[1].equalsIgnoreCase(gameObject2.name())) continue;
                teamType = gameObject2;
            }
        }
        if ((gameObject = player.getTarget()) == null || !gameObject.isCreature()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return false;
        }
        ((Creature)gameObject).setTeam(teamType);
        return true;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_setteam = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_setteam};
        }

        static {
            a = Commands.a();
        }
    }
}
