/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlEvent;

protected class CharacterAI.Timer
extends RunnableImpl {
    private int fj;
    private Object c;
    private Object d;

    public CharacterAI.Timer(int n, Object object, Object object2) {
        this.fj = n;
        this.c = object;
        this.d = object2;
    }

    @Override
    public void runImpl() {
        CharacterAI.this.notifyEvent(CtrlEvent.EVT_TIMER, this.fj, this.c, this.d);
    }
}
