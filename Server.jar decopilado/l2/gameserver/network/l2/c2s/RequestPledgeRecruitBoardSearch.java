/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.entry.PledgeRecruitInfo;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExPledgeRecruitBoardSearch;
import l2.gameserver.utils.HtmlUtils;

public class RequestPledgeRecruitBoardSearch
extends L2GameClientPacket {
    private int rN;
    private int gh;
    private int _type;
    private String et;
    private int rO;
    private boolean dZ;
    private int kl;
    private int pl;

    @Override
    protected void readImpl() {
        this.rN = this.readD();
        this.gh = this.readD();
        this._type = this.readD();
        this.et = this.readS();
        this.rO = this.readD();
        this.dZ = this.readD() == 2;
        this.kl = this.readD();
        this.pl = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || !Config.ENABLE_CLAN_ENTRY) {
            return;
        }
        this.et = HtmlUtils.sanitizeHtml(this.et);
        List<PledgeRecruitInfo> list = this.et.isEmpty() ? (this.gh < 0 && this.rN < 0 && this.rO < 0 ? ClanEntryManager.getInstance().getUnSortedClanList() : ClanEntryManager.getInstance().getSortedClanList(this.rN, this.gh, this.rO, this.dZ)) : ClanEntryManager.getInstance().getSortedClanListByName(this.et.toLowerCase(), this._type);
        player.sendPacket((IStaticPacket)ExPledgeRecruitBoardSearch.paged(list, this.kl));
    }
}
