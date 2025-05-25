/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.util.Date;
import java.util.Objects;
import services.MMOTopVote;

private static class MMOTopVote.MMOTopVoteInstance {
    private final long et;
    private final long eu;
    private final String hE;
    private final String hF;
    private final MMOTopVote.MMOTopVoteType a;

    private MMOTopVote.MMOTopVoteInstance(long l, Date date, String string, String string2, MMOTopVote.MMOTopVoteType mMOTopVoteType) {
        this.et = l;
        this.eu = date.getTime() / 1000L;
        this.hE = string;
        this.hF = string2;
        this.a = mMOTopVoteType;
    }

    private MMOTopVote.MMOTopVoteInstance(long l, long l2, String string, String string2, MMOTopVote.MMOTopVoteType mMOTopVoteType) {
        this.et = l;
        this.eu = l2;
        this.hE = string;
        this.hF = string2;
        this.a = mMOTopVoteType;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        MMOTopVote.MMOTopVoteInstance mMOTopVoteInstance = (MMOTopVote.MMOTopVoteInstance)object;
        return this.et == mMOTopVoteInstance.et;
    }

    public int hashCode() {
        return Objects.hash(this.et);
    }

    public String toString() {
        return "MMOTopVoteInstance{voteId=" + this.et + ", timestamp='" + this.eu + "', voterIp='" + this.hE + "', userName='" + this.hF + "', voteType=" + this.a + "}";
    }
}
