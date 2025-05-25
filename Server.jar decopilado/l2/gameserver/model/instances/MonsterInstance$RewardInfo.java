/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.gameserver.model.Creature;

protected static final class MonsterInstance.RewardInfo {
    protected Creature _attacker;
    protected int _dmg = 0;

    public MonsterInstance.RewardInfo(Creature creature, int n) {
        this._attacker = creature;
        this._dmg = n;
    }

    public void addDamage(int n) {
        if (n < 0) {
            n = 0;
        }
        this._dmg += n;
    }

    public int hashCode() {
        return this._attacker.getObjectId();
    }
}
