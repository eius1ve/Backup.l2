/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Comparator;
import l2.gameserver.model.Creature;

class Skill.1
implements Comparator<Creature> {
    Skill.1() {
    }

    @Override
    public int compare(Creature creature, Creature creature2) {
        if (creature == null && creature2 == null) {
            return 0;
        }
        if (creature == null) {
            return -1;
        }
        if (creature2 == null) {
            return 1;
        }
        return (int)(creature.getCurrentHp() - creature2.getCurrentHp());
    }
}
