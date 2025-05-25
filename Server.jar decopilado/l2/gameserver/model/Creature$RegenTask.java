/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;

private class Creature.RegenTask
implements Runnable {
    private Creature.RegenTask() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        if (Creature.this.isAlikeDead() || Creature.this.getRegenTick() == 0L) {
            return;
        }
        double d = Creature.this._currentHp;
        int n = Creature.this.getMaxHp();
        int n2 = Creature.this.getMaxMp();
        int n3 = Creature.this.isPlayer() ? Creature.this.getMaxCp() : 0;
        double d2 = 0.0;
        double d3 = 0.0;
        Creature.this.q.lock();
        try {
            if (Creature.this._currentHp < (double)n) {
                d2 += Formulas.calcHpRegen(Creature.this);
            }
            if (Creature.this._currentMp < (double)n2) {
                d3 += Formulas.calcMpRegen(Creature.this);
            }
            if (Creature.this.isPlayer() && Config.REGEN_SIT_WAIT) {
                Player player = (Player)Creature.this;
                if (player.isSitting()) {
                    player.updateWaitSitTime();
                    if (player.getWaitSitTime() > 5) {
                        d2 += (double)player.getWaitSitTime();
                        d3 += (double)player.getWaitSitTime();
                    }
                }
            } else if (Creature.this.isRaid() && Creature.this.getLevel() >= Config.RATE_MOD_MIN_LEVEL_LIMIT && Creature.this.getLevel() <= Config.RATE_MOD_MAX_LEVEL_LIMIT) {
                d2 *= Config.RATE_RAID_REGEN;
                d3 *= Config.RATE_RAID_REGEN;
            }
            Creature.this._currentHp += Math.max(0.0, Math.min(d2, Creature.this.calcStat(Stats.HP_LIMIT, null, null) * (double)n / 100.0 - Creature.this._currentHp));
            Creature.this._currentMp += Math.max(0.0, Math.min(d3, Creature.this.calcStat(Stats.MP_LIMIT, null, null) * (double)n2 / 100.0 - Creature.this._currentMp));
            Creature.this._currentHp = Math.min((double)n, Creature.this._currentHp);
            Creature.this._currentMp = Math.min((double)n2, Creature.this._currentMp);
            if (Creature.this.isPlayer()) {
                Creature.this._currentCp += Math.max(0.0, Math.min(Formulas.calcCpRegen(Creature.this), Creature.this.calcStat(Stats.CP_LIMIT, null, null) * (double)n3 / 100.0 - Creature.this._currentCp));
                Creature.this._currentCp = Math.min((double)n3, Creature.this._currentCp);
            }
            if (Creature.this._currentHp == (double)n && Creature.this._currentMp == (double)n2 && Creature.this._currentCp == (double)n3) {
                Creature.this.stopRegeneration();
            }
        }
        finally {
            Creature.this.q.unlock();
        }
        Creature.this.broadcastStatusUpdate();
        Creature.this.sendChanges();
        Creature.this.checkHpMessages(d, Creature.this._currentHp);
    }
}
