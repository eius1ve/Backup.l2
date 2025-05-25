/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminHeal
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().Heal) {
            return false;
        }
        switch (commands) {
            case admin_heal: {
                if (stringArray.length == 1) {
                    this.w(player);
                    break;
                }
                this.f(player, stringArray[1]);
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void w(Player player) {
        this.f(player, null);
    }

    private void f(Player player, String string) {
        Creature creature;
        GameObject gameObject = player.getTarget();
        if (string != null) {
            creature = World.getPlayer(string);
            if (creature != null) {
                gameObject = creature;
            } else {
                int n = Math.max(Integer.parseInt(string), 100);
                for (Creature creature2 : player.getAroundCharacters(n, 200)) {
                    creature2.setCurrentHpMp(creature2.getMaxHp(), creature2.getMaxMp());
                    if (!creature2.isPlayer()) continue;
                    creature2.setCurrentCp(creature2.getMaxCp());
                }
                player.sendMessage("Healed within " + n + " unit radius.");
                return;
            }
        }
        if (gameObject == null) {
            gameObject = player;
        }
        if (gameObject instanceof Creature) {
            creature = (Creature)gameObject;
            creature.setCurrentHpMp(creature.getMaxHp(), creature.getMaxMp());
            if (creature.isPlayer()) {
                creature.setCurrentCp(creature.getMaxCp());
            }
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
        }
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_heal = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_heal};
        }

        static {
            a = Commands.a();
        }
    }
}
