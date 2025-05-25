/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2;

import l2.gameserver.network.l2.PacketFloodProtector;

public class PacketFloodProtector.PacketData {
    private int pH;
    private int pI;
    private PacketFloodProtector.ActionType a;

    public PacketFloodProtector.PacketData(int n, int n2, PacketFloodProtector.ActionType actionType) {
        this.pH = n;
        this.pI = n2;
        this.a = actionType;
    }

    public int getDelay() {
        return this.pI;
    }

    public PacketFloodProtector.ActionType getAction() {
        return this.a;
    }

    public int getPacketId() {
        return this.pH;
    }
}
