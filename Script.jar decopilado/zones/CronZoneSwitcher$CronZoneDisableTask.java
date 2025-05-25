/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Zone
 */
package zones;

import l2.gameserver.model.Zone;
import zones.CronZoneSwitcher;

private static final class CronZoneSwitcher.CronZoneDisableTask
extends CronZoneSwitcher.BaseCronZoneTask<CronZoneSwitcher.CronZoneDisableTask> {
    private CronZoneSwitcher.CronZoneDisableTask(Zone zone) {
        super(zone);
    }

    @Override
    protected boolean doIt() {
        if (!this.getZone().isActive()) {
            return false;
        }
        this.getZone().setActive(false);
        return true;
    }
}
