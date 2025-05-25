/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Zone
 */
package zones;

import l2.gameserver.model.Zone;
import zones.CronZoneSwitcher;

private static final class CronZoneSwitcher.CronZoneEnableTask
extends CronZoneSwitcher.BaseCronZoneTask<CronZoneSwitcher.CronZoneEnableTask> {
    private CronZoneSwitcher.CronZoneEnableTask(Zone zone) {
        super(zone);
    }

    @Override
    protected boolean doIt() {
        if (this.getZone().isActive()) {
            return false;
        }
        this.getZone().setActive(true);
        return true;
    }
}
