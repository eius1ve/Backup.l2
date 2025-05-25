/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.MinionData;

public class MinionList {
    private final Set<MinionData> j;
    private final Set<MinionInstance> k;
    private final Lock s;
    private final MonsterInstance a;

    public MinionList(MonsterInstance monsterInstance) {
        this.a = monsterInstance;
        this.k = new HashSet<MinionInstance>();
        this.j = new HashSet<MinionData>();
        this.j.addAll(this.a.getTemplate().getMinionData());
        this.s = new ReentrantLock();
    }

    public boolean addMinion(MinionData minionData) {
        this.s.lock();
        try {
            boolean bl = this.j.add(minionData);
            return bl;
        }
        finally {
            this.s.unlock();
        }
    }

    public boolean addMinion(MinionInstance minionInstance) {
        this.s.lock();
        try {
            boolean bl = this.k.add(minionInstance);
            return bl;
        }
        finally {
            this.s.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean hasAliveMinions() {
        this.s.lock();
        try {
            for (MinionInstance minionInstance : this.k) {
                if (!minionInstance.isVisible() || minionInstance.isDead()) continue;
                boolean bl = true;
                return bl;
            }
        }
        finally {
            this.s.unlock();
        }
        return false;
    }

    public boolean hasMinions() {
        return this.j.size() > 0 || this.k.size() > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<MinionInstance> getAliveMinions() {
        ArrayList<MinionInstance> arrayList = new ArrayList<MinionInstance>(this.k.size());
        this.s.lock();
        try {
            for (MinionInstance minionInstance : this.k) {
                if (!minionInstance.isVisible() || minionInstance.isDead()) continue;
                arrayList.add(minionInstance);
            }
        }
        finally {
            this.s.unlock();
        }
        return arrayList;
    }

    public void spawnMinions() {
        for (MinionData minionData : this.j) {
            this.spawnMinion(minionData.getMinionId(), minionData.getAmount(), true);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void spawnMinion(int n, int n2, boolean bl) {
        if (this.a.isMinion() && this.a.getNpcId() == n) {
            return;
        }
        this.s.lock();
        try {
            int n3 = n2;
            for (MinionInstance minionInstance : this.k) {
                if (minionInstance.getNpcId() != n || !bl && !minionInstance.isDead()) continue;
                --n3;
                minionInstance.stopDecay();
                minionInstance.decayMe();
                minionInstance.refreshID();
                this.a.spawnMinion(minionInstance);
            }
            if (n3 > 0) {
                for (int i = 0; i < n3; ++i) {
                    MinionInstance minionInstance;
                    minionInstance = new MinionInstance(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(n));
                    minionInstance.setLeader(this.a);
                    this.a.spawnMinion(minionInstance);
                    this.k.add(minionInstance);
                }
            }
        }
        finally {
            this.s.unlock();
        }
    }

    public void unspawnMinions() {
        this.s.lock();
        try {
            for (MinionInstance minionInstance : this.k) {
                minionInstance.decayMe();
            }
        }
        finally {
            this.s.unlock();
        }
    }

    public void deleteMinions() {
        this.s.lock();
        try {
            for (MinionInstance minionInstance : this.k) {
                minionInstance.deleteMe();
            }
            this.k.clear();
        }
        finally {
            this.s.unlock();
        }
    }
}
