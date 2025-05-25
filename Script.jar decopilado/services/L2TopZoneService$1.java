/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.util.concurrent.ThreadFactory;

class L2TopZoneService.1
implements ThreadFactory {
    L2TopZoneService.1() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "L2TopZoneService-exec");
    }
}
