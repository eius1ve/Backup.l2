/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  org.apache.commons.lang3.ArrayUtils
 */
package instances;

import instances.Frintezza;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import org.apache.commons.lang3.ArrayUtils;

private class Frintezza.DeathListener
implements OnDeathListener {
    private Frintezza.DeathListener() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isNpc()) {
            if (creature.getNpcId() == 18328) {
                for (int i = 0; i < Q.length; ++i) {
                    Frintezza.this.openDoor(Q[i]);
                }
                Frintezza.this.a(false, U);
                for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                    if (!ArrayUtils.contains((int[])U, (int)npcInstance.getNpcId())) continue;
                    npcInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, Frintezza.this.getPlayers().get(Rnd.get((int)Frintezza.this.getPlayers().size())), (Object)200);
                }
            } else if (ArrayUtils.contains((int[])U, (int)creature.getNpcId())) {
                for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                    if (!ArrayUtils.contains((int[])U, (int)npcInstance.getNpcId()) || npcInstance.isDead()) continue;
                    return;
                }
                for (int i = 0; i < R.length; ++i) {
                    Frintezza.this.openDoor(R[i]);
                }
                Frintezza.this.a(true, V);
            } else if (creature.getNpcId() == 18339) {
                for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                    if (npcInstance.getNpcId() != 18339 || npcInstance.isDead()) continue;
                    return;
                }
                for (int i = 0; i < S.length; ++i) {
                    Frintezza.this.openDoor(S[i]);
                }
                Frintezza.this.a(false, V);
            } else if (ArrayUtils.contains((int[])V, (int)creature.getNpcId())) {
                if (Rnd.chance((int)10)) {
                    ((NpcInstance)creature).dropItem(creature2.getPlayer(), 8556, 1L);
                }
                for (NpcInstance npcInstance : Frintezza.this.getNpcs()) {
                    if (!ArrayUtils.contains((int[])V, (int)npcInstance.getNpcId()) && !ArrayUtils.contains((int[])U, (int)npcInstance.getNpcId()) || npcInstance.isDead()) continue;
                    return;
                }
                for (int i = 0; i < T.length; ++i) {
                    Frintezza.this.openDoor(T[i]);
                }
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.FrintezzaStart(Frintezza.this)), 300000L);
            } else {
                if (creature.getNpcId() == 29046) {
                    creature.decayMe();
                    return;
                }
                if (creature.getNpcId() == 29047) {
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Die(Frintezza.this, 1)), 10L);
                    Frintezza.this.setReenterTime(System.currentTimeMillis());
                }
            }
        }
    }
}
