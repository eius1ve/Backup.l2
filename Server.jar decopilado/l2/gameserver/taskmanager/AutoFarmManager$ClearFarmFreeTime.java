/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;

private class AutoFarmManager.ClearFarmFreeTime
extends RunnableImpl {
    private AutoFarmManager.ClearFarmFreeTime() {
    }

    @Override
    public void runImpl() {
        AutoFarmManager.this.bY();
    }
}
