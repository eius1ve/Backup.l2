/*
 * Decompiled with CFR 0.152.
 */
package achievements;

import achievements.AchievementInfo;
import achievements.AchievementUI;

class AchievementUI.1
extends AchievementUI.Paginator<AchievementInfo> {
    final /* synthetic */ int val$activeCategoryIdx;

    AchievementUI.1(int n, int n2, int n3) {
        this.val$activeCategoryIdx = n3;
        super(n, n2);
    }

    @Override
    protected String getBypassForPageOrdinal(int n, int n2) {
        return String.format("%s:achievements %d %d", SCRIPT_BYPASS_CLASS, this.val$activeCategoryIdx, n);
    }
}
