/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import java.util.Comparator;
import l2.gameserver.model.Creature;

protected class DefaultAI.NearestTargetComparator
implements Comparator<Creature> {
    private final Creature a;

    public DefaultAI.NearestTargetComparator(Creature creature) {
        this.a = creature;
    }

    @Override
    public int compare(Creature creature, Creature creature2) {
        double d = this.a.getDistance3D(creature) - this.a.getDistance3D(creature2);
        if (d < 0.0) {
            return -1;
        }
        return d > 0.0 ? 1 : 0;
    }
}
