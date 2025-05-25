/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  org.apache.commons.lang3.tuple.Pair
 */
package zones;

import java.util.Map;
import java.util.function.Predicate;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import org.apache.commons.lang3.tuple.Pair;

class AutoBuffZone.AutoBuffClassedZoneListener.1
extends RunnableImpl {
    final /* synthetic */ HardReference val$playerRef;

    AutoBuffZone.AutoBuffClassedZoneListener.1(HardReference hardReference) {
        this.val$playerRef = hardReference;
    }

    public void runImpl() {
        Player player = (Player)this.val$playerRef.get();
        if (player == null) {
            return;
        }
        for (Map.Entry<Predicate<Player>, Pair<Skill, Integer>> entry : AutoBuffClassedZoneListener.this.cx.entrySet()) {
            Predicate<Player> predicate = entry.getKey();
            if (!predicate.test(player)) continue;
            Pair<Skill, Integer> pair = entry.getValue();
            Skill skill = (Skill)pair.getLeft();
            Integer n = (Integer)pair.getRight();
            if (player.getEffectList().containEffectFromSkills(new int[]{skill.getId()})) continue;
            if (n != null) {
                skill.getEffects((Creature)player, (Creature)player, false, false, (long)n.intValue() * 1000L, 1.0, false);
                continue;
            }
            skill.getEffects((Creature)player, (Creature)player, false, false);
        }
    }
}
