/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.Config;
import l2.gameserver.model.base.ClassType2;

public final class SkillLearn
implements Comparable<SkillLearn> {
    private final int jt;
    private final int ju;
    private final int jv;
    private final int jw;
    private final int jx;
    private final long bJ;
    private final boolean cw;
    private final ClassType2 a;
    private final boolean cx;

    public SkillLearn(int n, int n2, int n3, int n4, int n5, long l, boolean bl, boolean bl2) {
        this.jt = n;
        this.ju = n2;
        this.jv = n3;
        this.jw = n4;
        this.jx = n5;
        this.bJ = l;
        this.cw = bl;
        this.a = ClassType2.None;
        this.cx = bl2;
    }

    public SkillLearn(int n, int n2, int n3, int n4, int n5, long l, boolean bl, ClassType2 classType2, boolean bl2) {
        this.jt = n;
        this.ju = n2;
        this.jv = n3;
        this.jw = n4;
        this.jx = n5;
        this.bJ = l;
        this.cw = bl;
        this.a = classType2;
        this.cx = bl2;
    }

    public int getId() {
        return this.jt;
    }

    public int getLevel() {
        return this.ju;
    }

    public int getMinLevel() {
        return this.jv;
    }

    public int getCost() {
        return this.jw;
    }

    public int getItemId() {
        return this.jx;
    }

    public long getItemCount() {
        return this.bJ;
    }

    public boolean isClicked() {
        return this.cw;
    }

    public boolean canAutoLearn() {
        if (!Config.AUTO_LEARN_FORGOTTEN_SKILLS && this.isClicked()) {
            return false;
        }
        if (this.jt == 1405) {
            return Config.AUTO_LEARN_DIVINE_INSPIRATION;
        }
        return this.cx;
    }

    @Override
    public int compareTo(SkillLearn skillLearn) {
        if (this.getId() == skillLearn.getId()) {
            return this.getLevel() - skillLearn.getLevel();
        }
        return this.getId() - skillLearn.getId();
    }

    public ClassType2 getClassType2() {
        return this.a;
    }
}
