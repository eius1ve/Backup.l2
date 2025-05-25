/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExPledgeDraftListSearch;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.Util;

public class RequestPledgeDraftListSearch
extends L2GameClientPacket {
    private int rH;
    private int rI;
    private int ga;
    private String et;
    private int rJ;
    private boolean dZ;

    @Override
    protected void readImpl() {
        this.rH = Util.constrain(this.readD(), 0, 107);
        this.rI = Util.constrain(this.readD(), 0, 107);
        this.ga = this.readD();
        this.et = this.readS().trim().toLowerCase();
        this.rJ = this.readD();
        this.dZ = this.readD() == 2;
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        this.et = HtmlUtils.sanitizeHtml(this.et);
        if (this.et.isEmpty()) {
            player.sendPacket((IStaticPacket)new ExPledgeDraftListSearch(ClanEntryManager.getInstance().getSortedWaitingList(this.rH, this.rI, this.ga, this.rJ, this.dZ)));
        } else {
            player.sendPacket((IStaticPacket)new ExPledgeDraftListSearch(ClanEntryManager.getInstance().queryWaitingListByName(this.et)));
        }
    }
}
