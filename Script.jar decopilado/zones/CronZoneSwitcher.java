/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.collections.MultiValueSet
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.gameserver.Announcements
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.Zone
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.function.LongSupplier;
import l2.commons.collections.MultiValueSet;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Announcements;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Zone;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronZoneSwitcher
extends Functions
implements ScriptFile {
    private static final Logger eA = LoggerFactory.getLogger(CronZoneSwitcher.class);
    private static final String iH = "cronZoneEnablePattern";
    private static final String iI = "cronZoneEnableBroadcastSpawnEvent";
    private static final String iJ = "cronZoneEnableBroadcastDespawnEvent";
    private static final String iK = "cronZoneEnableAnnounceCustomMessage";
    private static final String iL = "cronZoneDisablePattern";
    private static final String iM = "cronZoneDisableBroadcastSpawnEvent";
    private static final String iN = "cronZoneDisableBroadcastDespawnEvent";
    private static final String iO = "cronZoneDisableAnnounceCustomMessage";
    private static final List<Mutable<ScheduledFuture<?>>> ed = new CopyOnWriteArrayList();

    private static Mutable<ScheduledFuture<?>> a(final Runnable runnable, String string) {
        final MutableObject mutableObject = new MutableObject();
        final LongSupplier longSupplier = () -> {
            long l = System.currentTimeMillis();
            long l2 = new SchedulingPattern(string).next(l) - l;
            return l2;
        };
        mutableObject.setValue((Object)ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

            public void runImpl() throws Exception {
                try {
                    runnable.run();
                }
                catch (Throwable throwable) {
                    eA.error(throwable.getMessage(), throwable);
                }
                finally {
                    mutableObject.setValue((Object)ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), longSupplier.getAsLong()));
                }
            }
        }, longSupplier.getAsLong()));
        return mutableObject;
    }

    public void onLoad() {
        Collection collection = ReflectionManager.DEFAULT.getZones();
        int n = 0;
        int n2 = 0;
        for (Zone zone : collection) {
            MultiValueSet multiValueSet = zone.getParams();
            if (multiValueSet.isSet((Object)iH)) {
                ed.add(CronZoneSwitcher.a(((CronZoneEnableTask)((Object)((CronZoneEnableTask)((Object)new CronZoneEnableTask(zone).setBroadcastSpawnEvent(multiValueSet.getString((Object)iI, "")))).setBroadcastDespawnEvent(multiValueSet.getString((Object)iJ, "")))).setBroadcastAnnounceCustomMessage(multiValueSet.getString((Object)iK, "")), multiValueSet.getString((Object)iH)));
                ++n;
            }
            if (!multiValueSet.isSet((Object)iL)) continue;
            ed.add(CronZoneSwitcher.a(((CronZoneDisableTask)((Object)((CronZoneDisableTask)((Object)new CronZoneDisableTask(zone).setBroadcastSpawnEvent(multiValueSet.getString((Object)iM, "")))).setBroadcastDespawnEvent(multiValueSet.getString((Object)iN, "")))).setBroadcastAnnounceCustomMessage(multiValueSet.getString((Object)iO, "")), multiValueSet.getString((Object)iL)));
            ++n2;
        }
        eA.info("CronZoneSwitcher: Loaded " + n + " cron zone enable pattern(s) and " + n2 + " cron zone disable pattern(s).");
    }

    public void onReload() {
        this.onShutdown();
    }

    public void onShutdown() {
        for (Mutable<ScheduledFuture<?>> mutable : ed) {
            ScheduledFuture scheduledFuture;
            if (mutable == null || (scheduledFuture = (ScheduledFuture)mutable.getValue()) == null) continue;
            scheduledFuture.cancel(true);
        }
    }

    private static final class CronZoneEnableTask
    extends BaseCronZoneTask<CronZoneEnableTask> {
        private CronZoneEnableTask(Zone zone) {
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

    private static abstract class BaseCronZoneTask<T extends BaseCronZoneTask>
    extends RunnableImpl {
        private final Zone D;
        private String iP;
        private String iQ;
        private String iR;

        private BaseCronZoneTask(Zone zone) {
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

    private static final class CronZoneDisableTask
    extends BaseCronZoneTask<CronZoneDisableTask> {
        private CronZoneDisableTask(Zone zone) {
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
}
