/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.util.HashMap;
import java.util.Map;

static final class MMOTopVote.MMOTopVoteType
extends Enum<MMOTopVote.MMOTopVoteType> {
    public static final /* enum */ MMOTopVote.MMOTopVoteType Unknown = new MMOTopVote.MMOTopVoteType(-1);
    public static final /* enum */ MMOTopVote.MMOTopVoteType NORMAL = new MMOTopVote.MMOTopVoteType(1);
    public static final /* enum */ MMOTopVote.MMOTopVoteType SMS10 = new MMOTopVote.MMOTopVoteType(2);
    public static final /* enum */ MMOTopVote.MMOTopVoteType SMS50 = new MMOTopVote.MMOTopVoteType(3);
    public static final /* enum */ MMOTopVote.MMOTopVoteType SMS100 = new MMOTopVote.MMOTopVoteType(4);
    public static final /* enum */ MMOTopVote.MMOTopVoteType SMS500 = new MMOTopVote.MMOTopVoteType(5);
    private static final Map<Integer, MMOTopVote.MMOTopVoteType> cs;
    private final int bFH;
    private static final /* synthetic */ MMOTopVote.MMOTopVoteType[] a;

    public static MMOTopVote.MMOTopVoteType[] values() {
        return (MMOTopVote.MMOTopVoteType[])a.clone();
    }

    public static MMOTopVote.MMOTopVoteType valueOf(String string) {
        return Enum.valueOf(MMOTopVote.MMOTopVoteType.class, string);
    }

    private MMOTopVote.MMOTopVoteType(int n2) {
        this.bFH = n2;
    }

    public static MMOTopVote.MMOTopVoteType getTypeByTypeId(int n) {
        return cs.getOrDefault(n, Unknown);
    }

    public int getTypeId() {
        return this.bFH;
    }

    private static /* synthetic */ MMOTopVote.MMOTopVoteType[] a() {
        return new MMOTopVote.MMOTopVoteType[]{Unknown, NORMAL, SMS10, SMS50, SMS100, SMS500};
    }

    static {
        a = MMOTopVote.MMOTopVoteType.a();
        cs = new HashMap<Integer, MMOTopVote.MMOTopVoteType>();
        for (MMOTopVote.MMOTopVoteType mMOTopVoteType : MMOTopVote.MMOTopVoteType.values()) {
            cs.put(mMOTopVoteType.getTypeId(), mMOTopVoteType);
        }
    }
}
