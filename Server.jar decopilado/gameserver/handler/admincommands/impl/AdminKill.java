/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import org.apache.commons.lang3.math.NumberUtils;

public class AdminKill
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanEditNPC) {
            return false;
        }
        switch (commands) {
            case admin_kill: {
                if (stringArray.length == 1) {
                    this.y(player);
                    break;
                }
                this.h(player, stringArray[1]);
                break;
            }
            case admin_damage: {
                this.g(player, NumberUtils.toInt((String)stringArray[1], (int)1));
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void y(Player player) {
        this.h(player, null);
    }

    private void h(Player player, String string) {
        Creature creature;
        GameObject gameObject = player.getTarget();
        if (string != null) {
            creature = World.getPlayer(string);
            if (creature != null) {
                gameObject = creature;
            } else {
                int n = Math.max(Integer.parseInt(string), 100);
                for (Creature creature2 : player.getAroundCharacters(n, 200)) {
                    if (creature2.isDoor()) continue;
                    creature2.doDie(player);
                }
                player.sendMessage("Killed within " + n + " unit radius.");
                return;
            }
        }
        if (gameObject != null && gameObject.isCreature()) {
            creature = (Creature)gameObject;
            creature.doDie(player);
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
        }
    }

    private void g(Player player, int n) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null) {
            player.sendPacket((IStaticPacket)SystemMsg.SELECT_TARGET);
            return;
        }
        if (!gameObject.isCreature()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Creature creature = (Creature)gameObject;
        creature.reduceCurrentHp(n, player, null, true, true, false, false, false, false, true);
        player.sendMessage("You gave " + n + " damage to " + creature.getName() + ".");
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_kill = new Commands();
        public static final /* enum */ Commands admin_damage = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_kill, admin_damage};
        }

        static {
            a = Commands.a();
        }
    }
}
