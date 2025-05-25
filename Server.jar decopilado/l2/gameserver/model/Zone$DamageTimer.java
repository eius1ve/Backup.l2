/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

private class Zone.DamageTimer
extends Zone.ZoneTimer {
    public Zone.DamageTimer(Creature creature) {
        super(Zone.this, creature);
    }

    @Override
    public void runImpl() throws Exception {
        if (!Zone.this.isActive()) {
            return;
        }
        if (!Zone.this.checkTarget(this.cha)) {
            return;
        }
        int n = Zone.this.getDamageOnHP();
        int n2 = Zone.this.getDamageOnMP();
        SystemMsg systemMsg = Zone.this.getDamageMessage();
        if (n == 0 && n2 == 0) {
            return;
        }
        if (n > 0) {
            this.cha.reduceCurrentHp(n, this.cha, null, false, false, true, false, false, false, true);
            if (systemMsg != null) {
                this.cha.sendPacket((IStaticPacket)new SystemMessage(systemMsg).addNumber(n));
            }
        }
        if (n2 > 0) {
            this.cha.reduceCurrentMp(n2, null);
            if (systemMsg != null) {
                this.cha.sendPacket((IStaticPacket)new SystemMessage(systemMsg).addNumber(n2));
            }
        }
        this.next();
    }
}
