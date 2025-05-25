/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

private static class ExOneDayReceiveRewardList.RewardInfo {
    int id;
    int status;
    boolean hasProgress;
    int requiredProgress;
    int currentProgress;

    private ExOneDayReceiveRewardList.RewardInfo(int n, int n2, boolean bl, int n3, int n4) {
        this.id = n;
        this.status = n2;
        this.hasProgress = bl;
        this.requiredProgress = n3;
        this.currentProgress = n4;
    }
}
