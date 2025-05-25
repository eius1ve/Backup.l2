/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

private static class RequestExItemEnsoul.ItemEnsoulRequest {
    int slotType;
    int slotIndex;
    int stoneItemId;
    int optionID;

    public RequestExItemEnsoul.ItemEnsoulRequest(int n, int n2, int n3, int n4) {
        this.slotType = n;
        this.slotIndex = n2;
        this.stoneItemId = n3;
        this.optionID = n4;
    }
}
