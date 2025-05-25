/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  gnu.trove.TIntObjectIterator
 */
package l2.gameserver.model;

import gnu.trove.TIntObjectHashMap;
import gnu.trove.TIntObjectIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import l2.commons.collections.LazyArrayList;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;

public class AggroList {
    private final NpcInstance t;
    private final TIntObjectHashMap<AggroInfo> v = new TIntObjectHashMap();
    private final ReadWriteLock e = new ReentrantReadWriteLock();
    private final Lock n = this.e.readLock();
    private final Lock o = this.e.writeLock();

    public AggroList(NpcInstance npcInstance) {
        this.t = npcInstance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addDamageHate(Creature creature, int n, int n2) {
        if ((n = Math.max(n, 0)) == 0 && n2 == 0) {
            return;
        }
        this.o.lock();
        try {
            AggroInfo aggroInfo = (AggroInfo)this.v.get(creature.getObjectId());
            if (aggroInfo == null) {
                aggroInfo = new AggroInfo(creature);
                this.v.put(creature.getObjectId(), (Object)aggroInfo);
            }
            aggroInfo.damage += n;
            aggroInfo.hate += n2;
            aggroInfo.damage = Math.max(aggroInfo.damage, 0);
            aggroInfo.hate = Math.max(aggroInfo.hate, 0);
        }
        finally {
            this.o.unlock();
        }
    }

    public AggroInfo get(Creature creature) {
        this.n.lock();
        try {
            AggroInfo aggroInfo = (AggroInfo)this.v.get(creature.getObjectId());
            return aggroInfo;
        }
        finally {
            this.n.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void remove(Creature creature, boolean bl) {
        this.o.lock();
        try {
            if (!bl) {
                this.v.remove(creature.getObjectId());
                return;
            }
            AggroInfo aggroInfo = (AggroInfo)this.v.get(creature.getObjectId());
            if (aggroInfo != null) {
                aggroInfo.hate = 0;
            }
        }
        finally {
            this.o.unlock();
        }
    }

    public void clear() {
        this.clear(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear(boolean bl) {
        this.o.lock();
        try {
            if (this.v.isEmpty()) {
                return;
            }
            if (!bl) {
                this.v.clear();
                return;
            }
            TIntObjectIterator tIntObjectIterator = this.v.iterator();
            while (tIntObjectIterator.hasNext()) {
                tIntObjectIterator.advance();
                AggroInfo aggroInfo = (AggroInfo)tIntObjectIterator.value();
                aggroInfo.hate = 0;
                if (aggroInfo.damage != 0) continue;
                tIntObjectIterator.remove();
            }
        }
        finally {
            this.o.unlock();
        }
    }

    public boolean isEmpty() {
        this.n.lock();
        try {
            boolean bl = this.v.isEmpty();
            return bl;
        }
        finally {
            this.n.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Creature> getHateList(int n) {
        AggroInfo[] aggroInfoArray;
        this.n.lock();
        try {
            if (this.v.isEmpty()) {
                List<Creature> list = Collections.emptyList();
                return list;
            }
            aggroInfoArray = (AggroInfo[])this.v.getValues((Object[])new AggroInfo[this.v.size()]);
        }
        finally {
            this.n.unlock();
        }
        Arrays.sort(aggroInfoArray, HateComparator.getInstance());
        if (aggroInfoArray[0].hate == 0) {
            return Collections.emptyList();
        }
        LazyArrayList<Creature> lazyArrayList = new LazyArrayList<Creature>();
        List<Creature> list = World.getAroundCharacters(this.t, n, n);
        block4: for (int i = 0; i < aggroInfoArray.length; ++i) {
            AggroInfo aggroInfo = aggroInfoArray[i];
            if (aggroInfo.hate == 0) continue;
            for (Creature creature : list) {
                if (creature.getObjectId() != aggroInfo.attackerId) continue;
                lazyArrayList.add(creature);
                continue block4;
            }
        }
        return lazyArrayList;
    }

    public Creature getMostHated() {
        AggroInfo[] aggroInfoArray;
        this.n.lock();
        try {
            if (this.v.isEmpty()) {
                Creature creature = null;
                return creature;
            }
            aggroInfoArray = (AggroInfo[])this.v.getValues((Object[])new AggroInfo[this.v.size()]);
        }
        finally {
            this.n.unlock();
        }
        Arrays.sort(aggroInfoArray, HateComparator.getInstance());
        if (aggroInfoArray[0].hate == 0) {
            return null;
        }
        List<Creature> list = World.getAroundCharacters(this.t);
        block4: for (int i = 0; i < aggroInfoArray.length; ++i) {
            AggroInfo aggroInfo = aggroInfoArray[i];
            if (aggroInfo.hate == 0) continue;
            for (Creature creature : list) {
                if (creature.getObjectId() != aggroInfo.attackerId) continue;
                if (creature.isDead()) continue block4;
                return creature;
            }
        }
        return null;
    }

    public Creature getRandomHated() {
        AggroInfo[] aggroInfoArray;
        this.n.lock();
        try {
            if (this.v.isEmpty()) {
                Creature creature = null;
                return creature;
            }
            aggroInfoArray = (AggroInfo[])this.v.getValues((Object[])new AggroInfo[this.v.size()]);
        }
        finally {
            this.n.unlock();
        }
        Arrays.sort(aggroInfoArray, HateComparator.getInstance());
        if (aggroInfoArray[0].hate == 0) {
            return null;
        }
        List<Creature> list = World.getAroundCharacters(this.t);
        LazyArrayList<Creature> lazyArrayList = LazyArrayList.newInstance();
        block4: for (int i = 0; i < aggroInfoArray.length; ++i) {
            AggroInfo aggroInfo = aggroInfoArray[i];
            if (aggroInfo.hate == 0) continue;
            for (Creature creature : list) {
                if (creature.getObjectId() != aggroInfo.attackerId) continue;
                if (creature.isDead()) continue block4;
                lazyArrayList.add(creature);
                continue block4;
            }
        }
        Creature creature = lazyArrayList.isEmpty() ? null : (Creature)lazyArrayList.get(Rnd.get(lazyArrayList.size()));
        LazyArrayList.recycle(lazyArrayList);
        return creature;
    }

    public Creature getTopDamager() {
        AggroInfo[] aggroInfoArray;
        this.n.lock();
        try {
            if (this.v.isEmpty()) {
                Creature creature = null;
                return creature;
            }
            aggroInfoArray = (AggroInfo[])this.v.getValues((Object[])new AggroInfo[this.v.size()]);
        }
        finally {
            this.n.unlock();
        }
        Creature creature = null;
        Arrays.sort(aggroInfoArray, DamageComparator.getInstance());
        if (aggroInfoArray[0].damage == 0) {
            return null;
        }
        List<Creature> list = World.getAroundCharacters(this.t);
        for (int i = 0; i < aggroInfoArray.length; ++i) {
            AggroInfo aggroInfo = aggroInfoArray[i];
            if (aggroInfo.damage == 0) continue;
            for (Creature creature2 : list) {
                if (creature2.getObjectId() != aggroInfo.attackerId) continue;
                creature = creature2;
                return creature;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<Creature, HateInfo> getCharMap() {
        if (this.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<Creature, HateInfo> hashMap = new HashMap<Creature, HateInfo>();
        List<Creature> list = World.getAroundCharacters(this.t);
        this.n.lock();
        try {
            TIntObjectIterator tIntObjectIterator = this.v.iterator();
            block3: while (tIntObjectIterator.hasNext()) {
                tIntObjectIterator.advance();
                AggroInfo aggroInfo = (AggroInfo)tIntObjectIterator.value();
                if (aggroInfo.damage == 0 && aggroInfo.hate == 0) continue;
                for (Creature creature : list) {
                    if (creature.getObjectId() != aggroInfo.attackerId) continue;
                    hashMap.put(creature, new HateInfo(creature, aggroInfo));
                    continue block3;
                }
            }
        }
        finally {
            this.n.unlock();
        }
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<Playable, HateInfo> getPlayableMap() {
        if (this.isEmpty()) {
            return Collections.emptyMap();
        }
        HashMap<Playable, HateInfo> hashMap = new HashMap<Playable, HateInfo>();
        List<Playable> list = World.getAroundPlayables(this.t);
        this.n.lock();
        try {
            TIntObjectIterator tIntObjectIterator = this.v.iterator();
            block3: while (tIntObjectIterator.hasNext()) {
                tIntObjectIterator.advance();
                AggroInfo aggroInfo = (AggroInfo)tIntObjectIterator.value();
                if (aggroInfo.damage == 0 && aggroInfo.hate == 0) continue;
                for (Playable playable : list) {
                    if (playable.getObjectId() != aggroInfo.attackerId) continue;
                    hashMap.put(playable, new HateInfo(playable, aggroInfo));
                    continue block3;
                }
            }
        }
        finally {
            this.n.unlock();
        }
        return hashMap;
    }

    public class AggroInfo
    extends DamageHate {
        public final int attackerId;

        AggroInfo(Creature creature) {
            this.attackerId = creature.getObjectId();
        }
    }

    public static class HateComparator
    implements Comparator<DamageHate> {
        private static Comparator<DamageHate> c = new HateComparator();

        public static Comparator<DamageHate> getInstance() {
            return c;
        }

        HateComparator() {
        }

        @Override
        public int compare(DamageHate damageHate, DamageHate damageHate2) {
            int n = damageHate2.hate - damageHate.hate;
            return n == 0 ? damageHate2.damage - damageHate.damage : n;
        }
    }

    public static class DamageComparator
    implements Comparator<DamageHate> {
        private static Comparator<DamageHate> c = new DamageComparator();

        public static Comparator<DamageHate> getInstance() {
            return c;
        }

        DamageComparator() {
        }

        @Override
        public int compare(DamageHate damageHate, DamageHate damageHate2) {
            return damageHate2.damage - damageHate.damage;
        }
    }

    public class HateInfo
    extends DamageHate {
        public final Creature attacker;

        HateInfo(Creature creature, AggroInfo aggroInfo) {
            this.attacker = creature;
            this.hate = aggroInfo.hate;
            this.damage = aggroInfo.damage;
        }
    }

    private abstract class DamageHate {
        public int hate;
        public int damage;

        private DamageHate() {
        }
    }
}
