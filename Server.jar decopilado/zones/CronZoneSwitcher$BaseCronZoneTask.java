/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.Zone
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package zones;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Zone;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

private static abstract class CronZoneSwitcher.BaseCronZoneTask<T extends CronZoneSwitcher.BaseCronZoneTask>
extends RunnableImpl {
    private final Zone D;
    private String iP;
    private String iQ;
    private String iR;

    private CronZoneSwitcher.BaseCronZoneTask(Zone zone) {
        this.D = zone;
    }

    protected Zone getZone() {
        return this.D;
    }

    public String getBroadcastSpawnEvent() {
        return this.iP;
    }

    public T setBroadcastSpawnEvent(String string) {
        this.iP = string;
        return (T)((Object)this);
    }

    public String getBroadcastDespawnEvent() {
        return this.iQ;
    }

    public T setBroadcastDespawnEvent(String string) {
        this.iQ = string;
        return (T)((Object)this);
    }

    public String getBroadcastAnnounceCustomMessage() {
        return this.iR;
    }

    public T setBroadcastAnnounceCustomMessage(String string) {
        this.iR = string;
        return (T)((Object)this);
    }

    protected abstract boolean doIt();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runImpl() throws Exception {
        Zone zone = this.D;
        synchronized (zone) {
            if (!this.doIt()) {
                return;
            }
            if (StringUtils.isNotBlank((CharSequence)this.getBroadcastSpawnEvent())) {
                SpawnManager.getInstance().spawn(this.getBroadcastSpawnEvent());
            }
            if (StringUtils.isNotBlank((CharSequence)this.getBroadcastDespawnEvent())) {
                SpawnManager.getInstance().despawn(this.getBroadcastDespawnEvent());
            }
            if (StringUtils.isNotBlank((CharSequence)this.getBroadcastAnnounceCustomMessage())) {
                Announcements.getInstance().announceByCustomMessage(this.getBroadcastAnnounceCustomMessage(), ArrayUtils.EMPTY_STRING_ARRAY);
            }
        }
    }
}
