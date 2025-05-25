/*
 * Decompiled with CFR 0.152.
 */
package achievements;

import achievements.AchievementCounter;

class AchievementMetricType.1
extends AchievementCounter {
    final /* synthetic */ int val$playerLevel;

    AchievementMetricType.1(int n, int n2, int n3) {
        this.val$playerLevel = n3;
        super(n, n2);
    }

    @Override
    public int getVal() {
        return this.val$playerLevel;
    }

    @Override
    public void setVal(int n) {
    }

    @Override
    public int incrementAndGetValue() {
        return this.val$playerLevel;
    }

    @Override
    public void store() {
    }

    @Override
    public boolean isStorable() {
        return false;
    }
}
