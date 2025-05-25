/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask
 */
package services;

import l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask;
import services.MMOTopVote;

class MMOTopVote.2
extends SchedulingPatternTask {
    MMOTopVote.2(String string) {
        super(string);
    }

    protected void runTask() {
        try {
            MMOTopVote.cl();
        }
        catch (Exception exception) {
            eb.error(exception.getMessage(), (Throwable)exception);
        }
    }
}
