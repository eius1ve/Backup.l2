/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.AcquireSkillInfo;
import l2.gameserver.network.l2.s2c.ExAcquireSkillInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.tables.SkillTable;

public class RequestAquireSkillInfo
extends L2GameClientPacket {
    private int _id;
    private int _level;
    private AcquireType a;

    @Override
    protected void readImpl() {
        this._id = this.readD();
        this._level = this.readD();
        this.a = AcquireType.getById(this.readD());
    }

    @Override
    protected void runImpl() {
        NpcInstance npcInstance;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getTransformation() != 0 || player.isCursedWeaponEquipped() || SkillTable.getInstance().getInfo(this._id, this._level) == null || this.a == null) {
            return;
        }
        if (!(this.a == AcquireType.NORMAL || (npcInstance = player.getLastNpc()) != null && npcInstance.isInActingRange(player))) {
            return;
        }
        int n = Config.ALT_WEAK_SKILL_LEARN ? player.getVarInt("AcquireSkillClassId", player.getClassId().getId()) : player.getClassId().getId();
        ClassId classId = n >= 0 && n < ClassId.VALUES.length ? ClassId.VALUES[n] : null;
        SkillLearn skillLearn = SkillAcquireHolder.getInstance().getSkillLearn(player, classId, this._id, this._level, this.a);
        if (skillLearn == null) {
            return;
        }
        if (this.a == AcquireType.NORMAL) {
            NpcInstance npcInstance2 = player.getLastNpc();
            if (npcInstance2 == null || !npcInstance2.isInActingRange(player)) {
                this.sendPacket((L2GameServerPacket)new ExAcquireSkillInfo(skillLearn));
            } else {
                this.sendPacket((L2GameServerPacket)new AcquireSkillInfo(this.a, skillLearn, skillLearn.getItemId(), (int)skillLearn.getItemCount()));
            }
        } else {
            this.sendPacket((L2GameServerPacket)new AcquireSkillInfo(this.a, skillLearn, skillLearn.getItemId(), (int)skillLearn.getItemCount()));
        }
    }
}
