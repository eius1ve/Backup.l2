/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.holder;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.SkillEnchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantSkillHolder
extends AbstractHolder {
    private static final Logger aU = LoggerFactory.getLogger(EnchantSkillHolder.class);
    private static final EnchantSkillHolder a = new EnchantSkillHolder();
    private final Map<Integer, Map<Integer, SkillEnchant>> M = new TreeMap<Integer, Map<Integer, SkillEnchant>>();
    private final Map<Integer, Map<Integer, Map<Integer, SkillEnchant>>> N = new TreeMap<Integer, Map<Integer, Map<Integer, SkillEnchant>>>();

    private EnchantSkillHolder() {
    }

    public static EnchantSkillHolder getInstance() {
        return a;
    }

    public void addEnchantSkill(SkillEnchant skillEnchant) {
        int n;
        Map<Integer, SkillEnchant> map;
        int n2 = skillEnchant.getSkillId();
        Map<Integer, SkillEnchant> map2 = this.M.get(n2);
        if (map2 == null) {
            map2 = new TreeMap<Integer, SkillEnchant>();
            this.M.put(n2, map2);
        }
        map2.put(skillEnchant.getSkillLevel(), skillEnchant);
        Map<Integer, Map<Integer, SkillEnchant>> map3 = this.N.get(n2);
        if (map3 == null) {
            map3 = new TreeMap<Integer, Map<Integer, SkillEnchant>>();
            this.N.put(n2, map3);
        }
        if ((map = map3.get(n = skillEnchant.getRouteId())) == null) {
            map = new TreeMap<Integer, SkillEnchant>();
            map3.put(n, map);
        }
        map.put(skillEnchant.getSkillLevel(), skillEnchant);
    }

    public SkillEnchant getSkillEnchant(int n, int n2) {
        Map<Integer, SkillEnchant> map = this.M.get(n);
        if (map == null) {
            return null;
        }
        return map.get(n2);
    }

    public SkillEnchant getSkillEnchant(int n, int n2, int n3) {
        Map<Integer, SkillEnchant> map = this.M.get(n);
        if (map == null) {
            return null;
        }
        for (SkillEnchant skillEnchant : map.values()) {
            if (skillEnchant.getRouteId() != n2 || skillEnchant.getEnchantLevel() != n3) continue;
            return skillEnchant;
        }
        return null;
    }

    public Map<Integer, Map<Integer, SkillEnchant>> getRoutesOf(int n) {
        Map<Integer, Map<Integer, SkillEnchant>> map = this.N.get(n);
        if (map == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(map);
    }

    public int getFirstSkillLevelOf(int n, int n2) {
        Map<Integer, SkillEnchant> map = this.M.get(n);
        if (map == null) {
            return 0;
        }
        for (SkillEnchant skillEnchant : map.values()) {
            if (skillEnchant.getRouteId() != n2 || skillEnchant.getEnchantLevel() != 1) continue;
            return skillEnchant.getSkillLevel();
        }
        return 0;
    }

    public int getMaxEnchantLevelOf(int n) {
        int n2 = 0;
        Map<Integer, SkillEnchant> map = this.M.get(n);
        if (map == null) {
            return 0;
        }
        for (SkillEnchant skillEnchant : map.values()) {
            if (skillEnchant.getEnchantLevel() <= n2) continue;
            n2 = skillEnchant.getEnchantLevel();
        }
        return n2;
    }

    public Map<Integer, SkillEnchant> getLevelsOf(int n) {
        Map<Integer, SkillEnchant> map = this.M.get(n);
        if (map == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(map);
    }

    public void addEnchantSkill(int n, int n2, int n3, int n4, long l, int n5, int[] nArray, int n6, long l2, boolean bl, int n7) {
        this.addEnchantSkill(new SkillEnchant(n, n2, n3, n4, l, n5, nArray, n6, l2, bl, n7));
    }

    @Override
    public int size() {
        return this.M.size();
    }

    @Override
    public void clear() {
        this.M.clear();
    }
}
