/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.SimpleSpawner
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.SimpleSpawner;

public static class BaiumManager.CallArchAngel
extends RunnableImpl {
    public void runImpl() throws Exception {
        for (SimpleSpawner simpleSpawner : t) {
            s.add(simpleSpawner.doSpawn(true));
        }
    }
}
