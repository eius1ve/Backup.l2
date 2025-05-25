/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.actor.OnReviveListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  org.apache.commons.lang3.tuple.Pair
 */
package zones;

import java.util.Map;
import java.util.function.Predicate;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.actor.OnReviveListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import org.apache.commons.lang3.tuple.Pair;
import zones.AutoBuffZone;

private static class AutoBuffZone.AutoBuffClassedZoneListener
implements OnZoneEnterLeaveListener {
    private final Map<Predicate<Player>, Pair<Skill, Integer>> cx;
    final OnReviveListener onReviveClassListener = new OnReviveListener(){

        public void onRevive(Creature creature) {
            this.l(creature);
        }
    };

    private AutoBuffZone.AutoBuffClassedZoneListener(Map<Predicate<Player>, Pair<Skill, Integer>> map) {
        this.cx = map;
    }

    private void l(Creature creature) {
        if (creature == null || !creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        if (!AutoBuffZone.D(player)) {
            return;
        }
        final HardReference hardReference = player.getRef();
        ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

            public void runImpl() {
                Player player = (Player)hardReference.get();
                if (player == null) {
                    return;
                }
                for (Map.Entry<Predicate<Player>, Pair<Skill, Integer>> entry : cx.entrySet()) {
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
        });
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        this.l(creature);
        creature.addListener((Listener)this.onReviveClassListener);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        creature.removeListener((Listener)this.onReviveClassListener);
    }
}
