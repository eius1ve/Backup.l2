/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.collections.LazyArrayList
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.MinionList
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.MinionInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import java.util.List;
import l2.commons.collections.LazyArrayList;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.MinionList;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class Kama56Boss
extends Fighter {
    private long r = 0L;
    private HardReference<Player> d = HardReferences.emptyRef();

    public Kama56Boss(NpcInstance npcInstance) {
        super(npcInstance);
    }

    private void b(NpcInstance npcInstance) {
        List list;
        if (!npcInstance.isInCombat()) {
            this.d = HardReferences.emptyRef();
            return;
        }
        MinionList minionList = npcInstance.getMinionList();
        if (minionList == null || !minionList.hasMinions()) {
            this.d = HardReferences.emptyRef();
            return;
        }
        long l = System.currentTimeMillis();
        if (this.r > l && this.d.get() != null && (list = (Player)this.d.get()) != null && !list.isAlikeDead()) {
            for (MinionInstance minionInstance : minionList.getAliveMinions()) {
                if (minionInstance.getAI().getAttackTarget() == list) continue;
                minionInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)list, (Object)10000000);
            }
            return;
        }
        this.r = l + 30000L;
        list = World.getAroundPlayers((GameObject)npcInstance);
        if (list.isEmpty()) {
            this.d = HardReferences.emptyRef();
            return;
        }
        LazyArrayList lazyArrayList = new LazyArrayList();
        for (Object object : list) {
            if (object.isAlikeDead()) continue;
            lazyArrayList.add(object);
        }
        if (lazyArrayList.isEmpty()) {
            this.d = HardReferences.emptyRef();
            return;
        }
        Player player = (Player)lazyArrayList.get(Rnd.get((int)lazyArrayList.size()));
        this.d = player.getRef();
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"Kama56Boss.attack", (Object[])new Object[]{player.getName()});
        for (MinionInstance minionInstance : minionList.getAliveMinions()) {
            minionInstance.getAggroList().clear();
            minionInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)player, (Object)10000000);
        }
    }

    protected void thinkAttack() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return;
        }
        this.b(npcInstance);
        super.thinkAttack();
    }
}
