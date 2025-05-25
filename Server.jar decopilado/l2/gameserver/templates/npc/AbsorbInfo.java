/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 */
package l2.gameserver.templates.npc;

import gnu.trove.TIntHashSet;

public class AbsorbInfo {
    private final boolean hs;
    private final AbsorbType a;
    private final int GZ;
    private final int Ha;
    private final TIntHashSet h;

    public AbsorbInfo(boolean bl, AbsorbType absorbType, int n, int n2, int n3, int n4) {
        this.hs = bl;
        this.a = absorbType;
        this.GZ = n;
        this.Ha = n2;
        this.h = new TIntHashSet(n4 - n3);
        for (int i = n3; i <= n4; ++i) {
            this.h.add(i);
        }
    }

    public boolean isSkill() {
        return this.hs;
    }

    public AbsorbType getAbsorbType() {
        return this.a;
    }

    public int getChance() {
        return this.GZ;
    }

    public int getCursedChance() {
        return this.Ha;
    }

    public boolean canAbsorb(int n) {
        return this.h.contains(n);
    }

    public static final class AbsorbType
    extends Enum<AbsorbType> {
        public static final /* enum */ AbsorbType LAST_HIT = new AbsorbType();
        public static final /* enum */ AbsorbType PARTY_ONE = new AbsorbType();
        public static final /* enum */ AbsorbType PARTY_ALL = new AbsorbType();
        public static final /* enum */ AbsorbType PARTY_RANDOM = new AbsorbType();
        private static final /* synthetic */ AbsorbType[] a;

        public static AbsorbType[] values() {
            return (AbsorbType[])a.clone();
        }

        public static AbsorbType valueOf(String string) {
            return Enum.valueOf(AbsorbType.class, string);
        }

        private static /* synthetic */ AbsorbType[] a() {
            return new AbsorbType[]{LAST_HIT, PARTY_ONE, PARTY_ALL, PARTY_RANDOM};
        }

        static {
            a = AbsorbType.a();
        }
    }
}
