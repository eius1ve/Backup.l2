/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;

public class RequestExMagicSkillUseGround
extends L2GameClientPacket {
    private Location _loc = new Location();
    private int _skillId;
    private boolean dP;
    private boolean dQ;

    @Override
    protected void readImpl() {
        this._loc.x = this.readD();
        this._loc.y = this.readD();
        this._loc.z = this.readD();
        this._skillId = this.readD();
        this.dP = this.readD() != 0;
        this.dQ = this.readC() != 0;
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        Skill skill = SkillTable.getInstance().getInfo(this._skillId, player.getSkillLevel(this._skillId));
        if (skill != null) {
            if (skill.getAddedSkills().length == 0) {
                return;
            }
            if ((player.getTransformation() != 0 || player.isCursedWeaponEquipped()) && !player.getAllSkills().contains(skill)) {
                return;
            }
            if (!player.isInRange(this._loc, (long)skill.getCastRange())) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_TARGET_IS_OUT_OF_RANGE);
                player.sendActionFailed();
                return;
            }
            Creature creature = skill.getAimingTarget(player, player.getTarget());
            if (skill.checkCondition(player, creature, this.dP, this.dQ, true)) {
                player.setGroundSkillLoc(this._loc);
                player.getAI().Cast(skill, creature, this.dP, this.dQ);
            } else {
                player.sendActionFailed();
            }
        } else {
            player.sendActionFailed();
        }
    }
}
