/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.npc;

public class MinionData {
    private final int Hb;
    private final int Hc;

    public MinionData(int n, int n2) {
        this.Hb = n;
        this.Hc = n2;
    }

    public int getMinionId() {
        return this.Hb;
    }

    public int getAmount() {
        return this.Hc;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        return ((MinionData)object).getMinionId() == this.getMinionId();
    }
}
