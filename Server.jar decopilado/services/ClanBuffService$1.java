/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Skill
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.Comparator;
import l2.gameserver.model.Skill;
import org.apache.commons.lang3.tuple.Pair;

static class ClanBuffService.1
implements Comparator<Pair<Integer, Skill>> {
    ClanBuffService.1() {
    }

    @Override
    public int compare(Pair<Integer, Skill> pair, Pair<Integer, Skill> pair2) {
        return Integer.compare((Integer)pair.getKey(), (Integer)pair2.getKey());
    }
}
