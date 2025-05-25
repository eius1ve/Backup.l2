/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Playable;
import l2.gameserver.network.l2.s2c.AbnormalStatusUpdate;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PartySpelled
extends L2GameServerPacket {
    private final int zS;
    private final int zT;
    private final List<AbnormalStatusUpdate.Effect> cP;

    public PartySpelled(Playable playable, boolean bl) {
        this.zT = playable.getObjectId();
        this.zS = playable.isPet() ? 1 : (playable.isSummon() ? 2 : 0);
        this.cP = new ArrayList<AbnormalStatusUpdate.Effect>();
        if (bl) {
            for (Effect effect : playable.getEffectList().getAllFirstEffects()) {
                if (effect == null || !effect.isInUse()) continue;
                effect.addPartySpelledIcon(this);
            }
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(244);
        this.writeD(this.zS);
        this.writeD(this.zT);
        this.writeD(this.cP.size());
        for (AbnormalStatusUpdate.Effect effect : this.cP) {
            this.writeD(effect.skillId);
            this.writeH(effect.skillLevel);
            this.writeD(effect.clientAbnormalId);
            this.writeOptionalD(effect.duration);
        }
    }

    public void addPartySpelledEffect(int n, int n2, int n3) {
        this.cP.add(new AbnormalStatusUpdate.Effect(n, n2, n3, 0, 0));
    }
}
