/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class FriendStatus
extends L2GameServerPacket {
    public static final int MODE_OFFLINE = 0;
    public static final int MODE_ONLINE = 1;
    public static final int MODE_LEVEL = 2;
    public static final int MODE_CLASS = 3;
    private String eM;
    private int type;
    private int value;

    public FriendStatus(Player player, int n, int n2) {
        this.eM = player.getName();
        this.type = n;
        this.value = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(89);
        this.writeD(this.type);
        this.writeS(this.eM);
        switch (this.type) {
            case 0: 
            case 2: 
            case 3: {
                this.writeD(this.value);
            }
        }
    }
}
