/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.AbnormalStatusUpdate;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExOlympiadSpelledInfo
extends L2GameServerPacket {
    private int char_obj_id = 0;
    private List<AbnormalStatusUpdate.Effect> aP = new ArrayList<AbnormalStatusUpdate.Effect>();

    public void addEffect(int n, int n2, int n3) {
        this.aP.add(new AbnormalStatusUpdate.Effect(n, n2, n3, 0, 0));
    }

    public void addSpellRecivedPlayer(Player player) {
        if (player != null) {
            this.char_obj_id = player.getObjectId();
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(124);
        this.writeD(this.char_obj_id);
        this.writeD(this.aP.size());
        for (AbnormalStatusUpdate.Effect effect : this.aP) {
            this.writeD(effect.skillId);
            this.writeH(effect.skillLevel);
            this.writeD(effect.clientAbnormalId);
            this.writeOptionalD(effect.duration);
        }
    }
}
