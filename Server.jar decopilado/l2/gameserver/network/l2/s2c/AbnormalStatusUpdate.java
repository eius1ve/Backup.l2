/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class AbnormalStatusUpdate
extends L2GameServerPacket {
    public static final int INFINITIVE_EFFECT = -1;
    private List<Effect> aP = new ArrayList<Effect>();

    public void addEffect(int n, int n2, int n3) {
        this.aP.add(new Effect(n, n2, n3, 0, 0));
    }

    @Override
    protected final void writeImpl() {
        this.writeC(133);
        this.writeH(this.aP.size());
        for (Effect effect : this.aP) {
            this.writeD(effect.skillId);
            this.writeH(effect.skillLevel);
            this.writeD(effect.clientAbnormalId);
            this.writeOptionalD(effect.duration);
        }
    }

    public static class Effect {
        int skillId;
        int skillLevel;
        int duration;
        int clientAbnormalId;
        int effectorObjectId;

        public Effect(int n, int n2, int n3, int n4, int n5) {
            this.skillId = n;
            this.skillLevel = n2;
            this.duration = n3;
            this.clientAbnormalId = n4;
            this.effectorObjectId = n5;
        }
    }
}
