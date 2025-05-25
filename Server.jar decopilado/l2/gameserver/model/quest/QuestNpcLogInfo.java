/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.quest;

public class QuestNpcLogInfo {
    private final int[] aO;
    private final String dO;
    private final int pp;

    public QuestNpcLogInfo(int[] nArray, String string, int n) {
        this.aO = nArray;
        this.dO = string;
        this.pp = n;
    }

    public int[] getNpcIds() {
        return this.aO;
    }

    public String getVarName() {
        return this.dO;
    }

    public int getMaxCount() {
        return this.pp;
    }
}
