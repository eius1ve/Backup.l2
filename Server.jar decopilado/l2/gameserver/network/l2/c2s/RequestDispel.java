/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class RequestDispel
extends L2GameClientPacket {
    private int fW;
    private int _id;
    private int _level;

    @Override
    protected void readImpl() throws Exception {
        this.fW = this.readD();
        this._id = this.readD();
        this._level = this.readD();
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getObjectId() != this.fW && player.getPet() == null) {
            return;
        }
        if (!Config.ALT_ALLOW_DISPEL_AT_OLY && player.isOlyParticipant()) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        Playable playable = player;
        if (player.getObjectId() != this.fW) {
            playable = player.getPet();
        }
        for (Effect effect : playable.getEffectList().getAllEffects()) {
            if (effect.getDisplayId() != this._id || effect.getDisplayLevel() != this._level && (effect.getDisplayLevel() < 100 || effect.getSkill() == null || effect.getSkill().getBaseLevel() != this._level)) continue;
            if (!effect.isOffensive() && (!effect.getSkill().isMusic() || Config.SONGDANCE_CAN_BE_DISPELL) && effect.getSkill().isSelfDispellable() && effect.getSkill().getSkillType() != Skill.SkillType.TRANSFORMATION) {
                effect.exit();
                continue;
            }
            return;
        }
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(this._id, this._level));
    }
}
