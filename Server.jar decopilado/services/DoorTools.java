/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package services;

import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class DoorTools
extends Functions
implements ScriptFile {
    private static long c(String string) {
        long l = System.currentTimeMillis();
        long l2 = new SchedulingPattern(string).next(l) - l;
        return l2;
    }

    private static Mutable<ScheduledFuture<?>> a(final Runnable runnable, final String string) {
        final MutableObject mutableObject = new MutableObject();
        mutableObject.setValue((Object)ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

            public void runImpl() throws Exception {
                try {
                    runnable.run();
                }
                finally {
                    mutableObject.setValue((Object)ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), DoorTools.c(string)));
                }
            }
        }, DoorTools.c(string)));
        return mutableObject;
    }

    public void onLoad() {
        if (Config.ENABLE_ON_TIME_DOOR_OPEN) {
            DoorTools.a((Runnable)new RunnableImpl(){

                public void runImpl() throws Exception {
                    DoorInstance doorInstance = ReflectionManager.DEFAULT.getDoor(Config.ON_TIME_OPEN_DOOR_ID);
                    if (doorInstance != null) {
                        doorInstance.openMe(null, true);
                    }
                }
            }, Config.ON_TIME_OPEN_PATTERN);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
