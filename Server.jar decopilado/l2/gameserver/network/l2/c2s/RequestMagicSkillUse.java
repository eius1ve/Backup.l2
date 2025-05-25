/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.attachment.FlagItemAttachment;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;

public class RequestMagicSkillUse
extends L2GameClientPacket {
    private Integer e;
    private boolean dP;
    private boolean dQ;

    @Override
    protected void readImpl() {
        this.e = this.readD();
        this.dP = this.readD() != 0;
        this.dQ = this.readC() != 0;
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        player.setActive();
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        Skill skill = SkillTable.getInstance().getInfo(this.e, player.getSkillLevel(this.e));
        if (skill != null) {
            if (!skill.isActive() && !skill.isToggle()) {
                return;
            }
            FlagItemAttachment flagItemAttachment = player.getActiveWeaponFlagAttachment();
            if (flagItemAttachment != null && !flagItemAttachment.canCast(player, skill)) {
                player.sendActionFailed();
                return;
            }
            if ((player.getTransformation() != 0 || player.isCursedWeaponEquipped()) && !player.getAllSkills().contains(skill)) {
                return;
            }
            if (skill.isToggle() && player.getEffectList().getEffectsBySkill(skill) != null) {
                player.getEffectList().stopEffect(skill.getId());
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_ABORTED).addSkillName(skill.getId(), skill.getLevel()));
                player.sendActionFailed();
                return;
            }
            Creature creature = skill.getAimingTarget(player, player.getTarget());
            player.setGroundSkillLoc(null);
            player.getAI().Cast(skill, creature, this.dP, this.dQ);
        } else {
            player.sendActionFailed();
        }
    }
}
