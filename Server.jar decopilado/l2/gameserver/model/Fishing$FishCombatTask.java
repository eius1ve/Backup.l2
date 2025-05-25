/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExFishingHpRegen;

private class Fishing.FishCombatTask
extends RunnableImpl {
    private Fishing.FishCombatTask() {
    }

    @Override
    public void runImpl() throws Exception {
        if (Fishing.this.gY >= Fishing.this.a.getHP() * 2) {
            Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.YOUR_BAIT_WAS_STOLEN_BY_THAT_FISH);
            Fishing.this.b(false);
        } else if (Fishing.this._time <= 0) {
            Fishing.this.e.sendPacket((IStaticPacket)SystemMsg.THAT_FISH_IS_MORE_DETERMINED_THAN_YOU_ARE__IT_SPIT_THE_HOOK);
            Fishing.this.b(false);
        } else {
            --Fishing.this._time;
            if (Fishing.this.gX == 1 && Fishing.this._deceptiveMode == 0 || Fishing.this.gX == 0 && Fishing.this._deceptiveMode == 1) {
                Fishing.this.gY += Fishing.this.a.getHpRegen();
            }
            if (Fishing.this.gU == 0) {
                Fishing.this.gU = 1;
                if (Rnd.chance(30)) {
                    int n = Fishing.this.gX = Fishing.this.gX == 0 ? 1 : 0;
                }
                if (Fishing.this.a.getGroup() == 2 && Rnd.chance(10)) {
                    Fishing.this._deceptiveMode = Fishing.this._deceptiveMode == 0 ? 1 : 0;
                }
            } else {
                --Fishing.this.gU;
            }
            ExFishingHpRegen exFishingHpRegen = new ExFishingHpRegen(Fishing.this.e, Fishing.this._time, Fishing.this.gY, Fishing.this.gX, 0, Fishing.this.gW, 0, Fishing.this._deceptiveMode);
            if (Fishing.this.gW != 0) {
                Fishing.this.e.broadcastPacket(exFishingHpRegen);
            } else {
                Fishing.this.e.sendPacket((IStaticPacket)exFishingHpRegen);
            }
        }
    }
}
