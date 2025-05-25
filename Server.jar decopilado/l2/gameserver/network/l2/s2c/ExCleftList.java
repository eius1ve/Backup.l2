/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExCleftList
extends L2GameServerPacket {
    public static final int CleftType_Close = -1;
    public static final int CleftType_Total = 0;
    public static final int CleftType_Add = 1;
    public static final int CleftType_Remove = 2;
    public static final int CleftType_TeamChange = 3;
    private int uO = 0;

    @Override
    protected void writeImpl() {
        this.writeEx(148);
        this.writeD(this.uO);
        switch (this.uO) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
        }
    }
}
