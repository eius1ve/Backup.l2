/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.Config;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class AdminCancel
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        switch (commands) {
            case admin_cancel: {
                this.b(player, stringArray.length > 1 ? stringArray[1] : null);
                break;
            }
            case admin_check_cancel: {
                this.c(player, stringArray.length > 1 ? stringArray[1] : null);
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void b(Player player, String string) {
        GameObject gameObject = player.getTarget();
        if (string != null) {
            Player player2 = World.getPlayer(string);
            if (player2 != null) {
                gameObject = player2;
            } else {
                try {
                    int n = Math.max(Integer.parseInt(string), 100);
                    for (Creature creature : player.getAroundCharacters(n, 200)) {
                        creature.getEffectList().stopAllEffects();
                    }
                    player.sendMessage("Apply Cancel within " + n + " unit radius.");
                    return;
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Enter valid player name or radius");
                    return;
                }
            }
        }
        if (gameObject == null) {
            gameObject = player;
        }
        if (gameObject.isCreature()) {
            ((Creature)gameObject).getEffectList().stopAllEffects();
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
        }
    }

    private void c(Player player, String string) {
        Creature creature;
        GameObject gameObject = player.getTarget();
        if (string != null) {
            creature = World.getPlayer(string);
            if (creature != null) {
                gameObject = creature;
            } else {
                player.sendMessage("Target not found.");
                return;
            }
        }
        if (gameObject == null || !gameObject.isCreature()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        creature = (Creature)gameObject;
        for (Effect effect : creature.getEffectList().getAllEffects()) {
            if (effect.isOffensive() || effect.getSkill().isMusic() && !Config.SONGDANCE_CAN_BE_DISPELL || !effect.getSkill().isSelfDispellable() || effect.getSkill().getSkillType() == Skill.SkillType.TRANSFORMATION) continue;
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(effect.getSkill()));
            effect.exit();
        }
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_cancel = new Commands();
        public static final /* enum */ Commands admin_check_cancel = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_cancel, admin_check_cancel};
        }

        static {
            a = Commands.a();
        }
    }
}
