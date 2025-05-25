/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.games;

private class FishingChampionShipManager.Fisher {
    private double j = 0.0;
    private String _name;
    private int fR = 0;

    public FishingChampionShipManager.Fisher(String string, double d, int n) {
        this.setName(string);
        this.setLength(d);
        this.setRewardType(n);
    }

    public void setLength(double d) {
        this.j = d;
    }

    public void setName(String string) {
        this._name = string;
    }

    public void setRewardType(int n) {
        this.fR = n;
    }

    public String getName() {
        return this._name;
    }

    public int getRewardType() {
        return this.fR;
    }

    public double getLength() {
        return this.j;
    }
}
