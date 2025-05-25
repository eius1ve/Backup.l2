/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ai.CtrlEvent
 */
package ai.custom;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlEvent;

public class SSQAnakimMinion.Attack
extends RunnableImpl {
    public void runImpl() {
        if (SSQAnakimMinion.this.c() != null) {
            SSQAnakimMinion.this.getActor().getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, (Object)SSQAnakimMinion.this.c(), (Object)10000000);
        }
    }
}
