/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.util.concurrent.ThreadFactory;

class L2HopZoneService.1
implements ThreadFactory {
    L2HopZoneService.1() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "L2HopZoneService-exec");
    }
}
