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

import java.util.List;
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

private static class AutoBuffZone.AutoBuffZoneListener
implements OnZoneEnterLeaveListener {
    private final List<Pair<Skill, Integer>> ea;
    final OnReviveListener onReviveListener = new OnReviveListener(){

        public void onRevive(Creature creature) {
            this.m(creature);
        }
    };

    private AutoBuffZone.AutoBuffZoneListener(List<Pair<Skill, Integer>> list) {
        this.ea = list;
    }

    private void m(Creature creature) {
        if (creature == null || !creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        if (!AutoBuffZone.D(player)) {
            return;
        }
        final HardReference hardReference = player.getRef();
        ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

            public void runImpl() throws Exception {
                Player player = (Player)hardReference.get();
                if (player == null) {
                    return;
                }
                for (Pair<Skill, Integer> pair : ea) {
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
        creature.addListener((Listener)this.onReviveListener);
        this.m(creature);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        creature.removeListener((Listener)this.onReviveListener);
    }
}
