/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import java.util.Calendar;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.CastleManorManager;
import l2.gameserver.instancemanager.ServerVariables;

private class CastleManorManager.ManorTask
extends RunnableImpl {
    private CastleManorManager.ManorTask() {
    }

    @Override
    public void runImpl() throws Exception {
        int n = Calendar.getInstance().get(11);
        int n2 = Calendar.getInstance().get(12);
        if (ServerVariables.getBool(CastleManorManager.var_name)) {
            if (n < fJ || n > fL || n == fL && n2 >= fM) {
                ServerVariables.set(CastleManorManager.var_name, false);
                CastleManorManager.this.setUnderMaintenance(true);
                _log.info("Manor System: Under maintenance mode started");
            }
        } else if (CastleManorManager.this.isUnderMaintenance()) {
            if (n != fL || (long)n2 >= (long)fM + MAINTENANCE_PERIOD) {
                CastleManorManager.this.setUnderMaintenance(false);
                _log.info("Manor System: Next period started");
                if (CastleManorManager.this.isDisabled()) {
                    return;
                }
                CastleManorManager.this.setNextPeriod();
                try {
                    CastleManorManager.this.save();
                }
                catch (Exception exception) {
                    _log.info("Manor System: Failed to save manor data: " + exception);
                }
            }
        } else if (n > fJ && n < fL || n == fJ && n2 >= fK) {
            ServerVariables.set(CastleManorManager.var_name, true);
            _log.info("Manor System: Next period approved");
            if (CastleManorManager.this.isDisabled()) {
                return;
            }
            CastleManorManager.this.approveNextPeriod();
        }
    }
}
