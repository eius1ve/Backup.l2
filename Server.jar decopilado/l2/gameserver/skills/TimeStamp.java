/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills;

import l2.gameserver.model.Skill;

public class TimeStamp {
    private final int CR;
    private final int CS;
    private final long dC;
    private final long dD;

    public TimeStamp(int n, long l, long l2) {
        this.CR = n;
        this.CS = 0;
        this.dC = l2;
        this.dD = l;
    }

    public TimeStamp(Skill skill, long l) {
        this(skill, System.currentTimeMillis() + l, l);
    }

    public TimeStamp(Skill skill, long l, long l2) {
        this.CR = skill.getId();
        this.CS = skill.getLevel();
        this.dC = l2;
        this.dD = l;
    }

    public long getReuseBasic() {
        if (this.dC == 0L) {
            return this.getReuseCurrent();
        }
        return this.dC;
    }

    public long getReuseCurrent() {
        return Math.max(this.dD - System.currentTimeMillis(), 0L);
    }

    public long getEndTime() {
        return this.dD;
    }

    public boolean hasNotPassed() {
        return System.currentTimeMillis() < this.dD;
    }

    public int getId() {
        return this.CR;
    }

    public int getLevel() {
        return this.CS;
    }
}
