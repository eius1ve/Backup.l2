/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static class AbnormalStatusUpdate.Effect {
    int skillId;
    int skillLevel;
    int duration;
    int clientAbnormalId;
    int effectorObjectId;

    public AbnormalStatusUpdate.Effect(int n, int n2, int n3, int n4, int n5) {
        this.skillId = n;
        this.skillLevel = n2;
        this.duration = n3;
        this.clientAbnormalId = n4;
        this.effectorObjectId = n5;
    }
}
