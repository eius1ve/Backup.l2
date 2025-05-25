/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.CursedWeapon;

private class CursedWeaponsManager.RemoveTask
extends RunnableImpl {
    private CursedWeaponsManager.RemoveTask() {
    }

    @Override
    public void runImpl() throws Exception {
        for (CursedWeapon cursedWeapon : CursedWeaponsManager.this.a) {
            if (!cursedWeapon.isActive() || cursedWeapon.getTimeLeft() > 0L) continue;
            CursedWeaponsManager.this.endOfLife(cursedWeapon);
        }
    }
}
