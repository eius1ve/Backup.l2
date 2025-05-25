/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.utils.HtmlUtils;

public class RequestWriteHeroWords
extends L2GameClientPacket {
    private String eD;

    @Override
    protected void readImpl() {
        this.eD = this.readS();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || !player.isHero()) {
            return;
        }
        this.eD = HtmlUtils.sanitizeHtml(this.eD);
        HeroController.getInstance().setHeroMessage(player.getObjectId(), this.eD);
    }
}
