/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import java.util.ArrayList;
import java.util.Iterator;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.taskmanager.SpawnTaskManager;

public class SpawnTaskManager.SpawnScheduler
extends RunnableImpl {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runImpl() throws Exception {
        if (SpawnTaskManager.this.Ec > 0) {
            try {
                ArrayList<NpcInstance> arrayList = new ArrayList<NpcInstance>();
                Iterator iterator = SpawnTaskManager.this.k;
                synchronized (iterator) {
                    long l = System.currentTimeMillis();
                    int n = SpawnTaskManager.this.Ec;
                    for (int i = n - 1; i >= 0; --i) {
                        try {
                            SpawnTaskManager.SpawnTask spawnTask = SpawnTaskManager.this.a[i];
                            if (spawnTask != null && spawnTask.endtime > 0L && l > spawnTask.endtime) {
                                NpcInstance npcInstance = spawnTask.getActor();
                                if (npcInstance != null && npcInstance.getSpawn() != null) {
                                    arrayList.add(npcInstance);
                                }
                                spawnTask.endtime = -1L;
                            }
                            if (spawnTask != null && spawnTask.getActor() != null && spawnTask.endtime >= 0L) continue;
                            if (i == SpawnTaskManager.this.Ec - 1) {
                                SpawnTaskManager.this.a[i] = null;
                            } else {
                                SpawnTaskManager.this.a[i] = SpawnTaskManager.this.a[SpawnTaskManager.this.Ec - 1];
                                SpawnTaskManager.this.a[SpawnTaskManager.this.Ec - 1] = null;
                            }
                            if (SpawnTaskManager.this.Ec <= 0) continue;
                            --SpawnTaskManager.this.Ec;
                            continue;
                        }
                        catch (Exception exception) {
                            _log.error("", (Throwable)exception);
                        }
                    }
                }
                for (NpcInstance npcInstance : arrayList) {
                    Spawner spawner = npcInstance.getSpawn();
                    if (spawner == null) continue;
                    spawner.decreaseScheduledCount();
                    if (!spawner.isDoRespawn()) continue;
                    spawner.respawnNpc(npcInstance);
                }
            }
            catch (Exception exception) {
                _log.error("", (Throwable)exception);
            }
        }
    }
}
