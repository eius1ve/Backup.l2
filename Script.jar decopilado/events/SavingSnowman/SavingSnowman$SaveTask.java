/*
 * Decompiled with CFR 0.152.
 */
package events.SavingSnowman;

import events.SavingSnowman.SavingSnowman;

public class SavingSnowman.SaveTask
implements Runnable {
    @Override
    public void run() {
        if (!T || _snowmanState == SavingSnowman.SnowmanState.CAPTURED) {
            return;
        }
        SavingSnowman.this.captureSnowman();
    }
}
