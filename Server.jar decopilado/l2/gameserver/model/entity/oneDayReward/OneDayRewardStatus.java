/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward;

import l2.gameserver.model.entity.oneDayReward.OneDayReward;

public class OneDayRewardStatus {
    private int id;
    private int lR;
    private int currentProgress;
    private int lS;
    private boolean dq;

    public OneDayRewardStatus(OneDayReward oneDayReward, int n, int n2) {
        this(oneDayReward.getId(), n, n2, oneDayReward.getResetTime().ordinal(), false);
    }

    public OneDayRewardStatus(int n, int n2, int n3, int n4, boolean bl) {
        this.id = n;
        this.lR = n2;
        this.currentProgress = n3;
        this.lS = n4;
        this.dq = bl;
    }

    public boolean isReceived() {
        return this.dq;
    }

    public int getId() {
        return this.id;
    }

    public int getPlayerObjectId() {
        return this.lR;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    public int getResetTime() {
        return this.lS;
    }
}
