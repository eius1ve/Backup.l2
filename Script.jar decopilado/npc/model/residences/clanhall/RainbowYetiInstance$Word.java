/*
 * Decompiled with CFR 0.152.
 */
package npc.model.residences.clanhall;

private static class RainbowYetiInstance.Word {
    private final String hg;
    private final int[][] m;

    public RainbowYetiInstance.Word(String string, int[] ... nArray) {
        this.hg = string;
        this.m = nArray;
    }

    public String getName() {
        return this.hg;
    }

    public int[][] getItems() {
        return this.m;
    }
}
