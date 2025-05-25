/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.handler.admincommands.AdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class SendBypassBuildCmd
extends L2GameClientPacket {
    private String eF;

    @Override
    protected void readImpl() {
        this.eF = this.readS();
        if (this.eF != null) {
            this.eF = this.eF.trim();
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Object object = this.eF;
        if (!((String)object).contains("admin_")) {
            object = "admin_" + (String)object;
        }
        AdminCommandHandler.getInstance().useAdminCommandHandler(player, (String)object);
    }
}
