/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import java.util.ArrayList;
import java.util.Iterator;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Util;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SpawnTaskManager {
    private SpawnTask[] a = new SpawnTask[500];
    private int Ec = 0;
    private final Object k = new Object();
    private static SpawnTaskManager a;

    public SpawnTaskManager() {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new SpawnScheduler(), 2000L, 2000L);
    }

    public static SpawnTaskManager getInstance() {
        if (a == null) {
            a = new SpawnTaskManager();
        }
        return a;
    }

    public void addSpawnTask(NpcInstance npcInstance, long l) {
        this.removeObject(npcInstance);
        this.a(new SpawnTask(npcInstance, System.currentTimeMillis() + l));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("============= SpawnTask Manager Report ============\n\r");
        stringBuilder.append("Tasks count: ").append(this.Ec).append("\n\r");
        stringBuilder.append("Tasks dump:\n\r");
        long l = System.currentTimeMillis();
        for (SpawnTask spawnTask : this.a) {
            if (spawnTask == null) continue;
            stringBuilder.append("Class/Name: ").append(spawnTask.getClass().getSimpleName()).append('/').append(spawnTask.getActor());
            stringBuilder.append(" spawn timer: ").append(Util.formatTime((int)((spawnTask.endtime - l) / 1000L))).append("\n\r");
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(SpawnTask spawnTask) {
        Object object = this.k;
        synchronized (object) {
            if (this.Ec >= this.a.length) {
                SpawnTask[] spawnTaskArray = new SpawnTask[this.a.length * 2];
                for (int i = 0; i < this.Ec; ++i) {
                    spawnTaskArray[i] = this.a[i];
                }
                this.a = spawnTaskArray;
            }
            this.a[this.Ec] = spawnTask;
            ++this.Ec;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeObject(NpcInstance npcInstance) {
        Object object = this.k;
        synchronized (object) {
            if (this.Ec > 1) {
                int n = -1;
                for (int i = 0; i < this.Ec; ++i) {
                    if (this.a[i].getActor() != npcInstance) continue;
                    n = i;
                }
                if (n > -1) {
                    this.a[n] = this.a[this.Ec - 1];
                    this.a[this.Ec - 1] = null;
                    --this.Ec;
                }
            } else if (this.Ec == 1 && this.a[0].getActor() == npcInstance) {
                this.a[0] = null;
                this.Ec = 0;
            }
        }
    }

    private class SpawnTask {
        private final HardReference<NpcInstance> ab;
        public long endtime;

        SpawnTask(NpcInstance npcInstance, long l) {
            this.ab = npcInstance.getRef();
            this.endtime = l;
        }

        public NpcInstance getActor() {
            return this.ab.get();
        }
    }

    public class SpawnScheduler
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
                                SpawnTask spawnTask = SpawnTaskManager.this.a[i];
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
}
