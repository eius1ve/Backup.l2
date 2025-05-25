/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;

public class SellBuffHolder
extends AbstractHolder {
    private final Map<Integer, BuffSellInfo> ab = new HashMap<Integer, BuffSellInfo>();
    private static final SellBuffHolder a = new SellBuffHolder();

    public static SellBuffHolder getInstance() {
        return a;
    }

    public void addBuffInfo(int n, int n2, long l, long l2, int n3, double d, double d2, double d3, boolean bl) {
        this.ab.put(n, new BuffSellInfo(n, n2, l, l2, n3, d, d2, d3, bl));
    }

    public BuffSellInfo getBuffInfo(int n) {
        return this.ab.get(n);
    }

    public boolean isBuffAllowed(int n) {
        return this.ab.containsKey(n);
    }

    @Override
    public int size() {
        return this.ab.size();
    }

    @Override
    public void clear() {
        this.ab.clear();
    }

    public static class BuffSellInfo {
        private final int skillId;
        private final int itemId;
        private final long aL;
        private final long aM;
        private final int fr;
        private final double f;
        private final double g;
        private final double h;
        private final boolean bd;

        public BuffSellInfo(int n, int n2, long l, long l2, int n3, double d, double d2, double d3, boolean bl) {
            this.skillId = n;
            this.itemId = n2;
            this.aL = l;
            this.aM = l2;
            this.fr = n3;
            this.f = d;
            this.g = d2;
            this.h = d3;
            this.bd = bl;
        }

        public int getSkillId() {
            return this.skillId;
        }

        public int getEffectTime() {
            return this.fr;
        }

        public double getEffectTimeMultiplier() {
            return this.f;
        }

        public double getEffectTimePremiumMultiplier() {
            return this.g;
        }

        public boolean isApplyOnPets() {
            return this.bd;
        }

        public int getItemId() {
            return this.itemId;
        }

        public double getSkillMpConsumeMultiplayer() {
            return this.h;
        }

        public long getSkillMinPrice() {
            return this.aL;
        }

        public long getSkillMaxPrice() {
            return this.aM;
        }
    }
}
