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

public class SSQLilithMinion.Attack
extends RunnableImpl {
    public void runImpl() {
        if (SSQLilithMinion.this.c() != null) {
            SSQLilithMinion.this.getActor().getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, (Object)SSQLilithMinion.this.c(), (Object)10000000);
        }
    }
}
