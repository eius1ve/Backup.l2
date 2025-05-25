/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.utils.Util;

public class RequestChangeNicknameColor
extends L2GameClientPacket {
    public static final int TITLE_COLOR_ID = 13021;
    public static final int TITLE_COLOR_EVENT_ID = 13307;
    public static final int STYLISH_COLOR_TITL = 49662;
    private static final int[] aQ = new int[]{0x9393FF, 8145404, 9959676, 16423662, 16735635, 64672, 10528257, 7903407, 4743829, 0x999999};
    private int qu;
    private int qv;
    private String _title;

    @Override
    protected void readImpl() {
        this.qu = this.readD();
        this._title = this.readS(16);
        this.qv = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.qu < 0 || this.qu >= aQ.length) {
            return;
        }
        if (!this._title.isEmpty() && !Util.isMatchingRegexp(this._title, Config.CLAN_TITLE_TEMPLATE)) {
            player.sendMessage("Incorrect title.");
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(this.qv);
        if (itemInstance == null) {
            return;
        }
        int n = itemInstance.getItemId();
        if (player.consumeItem(n, 1L)) {
            switch (n) {
                case 49662: {
                    player.setTitleColor(aQ[this.qu]);
                    break;
                }
                case 13307: {
                    player.setTitleColor(aQ[this.qu]);
                    break;
                }
                case 13021: {
                    player.setTitleColor(aQ[this.qu]);
                }
            }
            player.setTitle(this._title);
            player.broadcastUserInfo(true, new UserInfoType[0]);
        }
    }
}
