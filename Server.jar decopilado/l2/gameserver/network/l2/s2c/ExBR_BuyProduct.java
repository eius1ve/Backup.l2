/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_BuyProduct
extends L2GameServerPacket {
    public static final int RESULT_OK = 1;
    public static final int RESULT_NOT_ENOUGH_POINTS = -1;
    public static final int RESULT_WRONG_PRODUCT = -2;
    public static final int RESULT_USER_CANCEL = -3;
    public static final int RESULT_INVENTORY_FULL = -4;
    public static final int RESULT_CLOSED_PRODUCT = -5;
    public static final int RESULT_SERVER_ERROR = -6;
    public static final int RESULT_SALE_PERIOD_ENDED = -7;
    public static final int RESULT_SALE_AFTER_SALE_DATE = -8;
    public static final int RESULT_INVALID_USER = -9;
    public static final int RESULT_INVALID_ITEM = -10;
    public static final int RESULT_INVALID_USER_STATE = -11;
    public static final int RESULT_NOT_DAY_OF_WEEK = -12;
    public static final int RESULT_NOT_TIME_OF_DAY = -13;
    public static final int RESULT_SOLD_OUT = -14;
    private final int uC;

    public ExBR_BuyProduct(int n) {
        this.uC = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(217);
        this.writeD(this.uC);
    }
}
