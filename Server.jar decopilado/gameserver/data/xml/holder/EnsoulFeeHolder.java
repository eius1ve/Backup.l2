/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;
import l2.gameserver.templates.item.support.Grade;
import org.apache.commons.lang3.tuple.Pair;

public class EnsoulFeeHolder
extends AbstractHolder {
    private static final EnsoulFeeHolder a = new EnsoulFeeHolder();
    private final Map<Grade, Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>>> O = new HashMap<Grade, Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>>>();
    private final Map<Grade, Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>>> P = new HashMap<Grade, Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>>>();
    private final Map<Grade, Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>>> Q = new HashMap<Grade, Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>>>();

    public static EnsoulFeeHolder getInstance() {
        return a;
    }

    private EnsoulFeeHolder() {
    }

    public void addInsertFee(Grade grade, EnsoulFeeSlotType ensoulFeeSlotType, List<Pair<Integer, Long>> list) {
        Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>> map = this.O.get(grade);
        if (map == null) {
            map = new HashMap<EnsoulFeeSlotType, List<Pair<Integer, Long>>>();
            this.O.put(grade, map);
        }
        map.put(ensoulFeeSlotType, list);
    }

    public List<Pair<Integer, Long>> getInsertFee(Grade grade, EnsoulFeeSlotType ensoulFeeSlotType) {
        Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>> map = this.O.get(grade);
        if (map == null) {
            return null;
        }
        return map.get((Object)ensoulFeeSlotType);
    }

    public void addReplaceFee(Grade grade, EnsoulFeeSlotType ensoulFeeSlotType, List<Pair<Integer, Long>> list) {
        Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>> map = this.P.get(grade);
        if (map == null) {
            map = new HashMap<EnsoulFeeSlotType, List<Pair<Integer, Long>>>();
            this.P.put(grade, map);
        }
        map.put(ensoulFeeSlotType, list);
    }

    public List<Pair<Integer, Long>> getReplaceFee(Grade grade, EnsoulFeeSlotType ensoulFeeSlotType) {
        Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>> map = this.P.get(grade);
        if (map == null) {
            return null;
        }
        return map.get((Object)ensoulFeeSlotType);
    }

    public void addRemoveFee(Grade grade, EnsoulFeeSlotType ensoulFeeSlotType, List<Pair<Integer, Long>> list) {
        Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>> map = this.Q.get(grade);
        if (map == null) {
            map = new HashMap<EnsoulFeeSlotType, List<Pair<Integer, Long>>>();
            this.Q.put(grade, map);
        }
        map.put(ensoulFeeSlotType, list);
    }

    public List<Pair<Integer, Long>> getRemoveFee(Grade grade, EnsoulFeeSlotType ensoulFeeSlotType) {
        Map<EnsoulFeeSlotType, List<Pair<Integer, Long>>> map = this.Q.get(grade);
        if (map == null) {
            return null;
        }
        return map.get((Object)ensoulFeeSlotType);
    }

    @Override
    public int size() {
        return this.O.size() + this.P.size() + this.Q.size();
    }

    @Override
    public void clear() {
        this.O.clear();
        this.P.clear();
        this.Q.clear();
    }
}
