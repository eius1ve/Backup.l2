/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.instances.NpcInstance
 *  org.apache.commons.lang3.ArrayUtils
 */
package ai.custom;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.instances.NpcInstance;
import org.apache.commons.lang3.ArrayUtils;

public class SSQAnakimMinion
extends Fighter {
    private final int[] x = new int[]{32717, 32716};

    public SSQAnakimMinion(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.setHasChatWindow(false);
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Attack()), 3000L);
    }

    private NpcInstance c() {
        List list = this.getActor().getAroundNpc(1000, 300);
        if (list != null && !list.isEmpty()) {
            for (NpcInstance npcInstance : list) {
                if (!ArrayUtils.contains((int[])this.x, (int)npcInstance.getNpcId())) continue;
                return npcInstance;
            }
        }
        return null;
    }

    protected boolean randomWalk() {
        return false;
    }

    public class Attack
    extends RunnableImpl {
        public void runImpl() {
            if (SSQAnakimMinion.this.c() != null) {
                SSQAnakimMinion.this.getActor().getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, (Object)SSQAnakimMinion.this.c(), (Object)10000000);
            }
        }
    }
}
