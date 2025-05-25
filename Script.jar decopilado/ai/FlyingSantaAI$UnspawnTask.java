/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import java.lang.ref.WeakReference;
import l2.gameserver.model.instances.NpcInstance;

private class FlyingSantaAI.UnspawnTask
implements Runnable {
    private WeakReference<NpcInstance> a;

    public FlyingSantaAI.UnspawnTask(NpcInstance npcInstance) {
        this.a = new WeakReference<NpcInstance>(npcInstance);
    }

    @Override
    public void run() {
        if (this.a != null && this.a.get() != null) {
            ((NpcInstance)this.a.get()).decayMe();
            ((NpcInstance)this.a.get()).deleteMe();
        }
    }
}
