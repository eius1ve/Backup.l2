/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.time.LocalDateTime;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.CharSelected;
import l2.gameserver.network.l2.s2c.Ex2ndPasswordCheck;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.AutoBan;

public class CharacterSelected
extends L2GameClientPacket {
    private int pZ;

    @Override
    protected void readImpl() {
        this.pZ = this.readD();
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        if (gameClient.getActiveChar() != null) {
            return;
        }
        int n = gameClient.getObjectIdForSlot(this.pZ);
        if (AutoBan.isBanned(n)) {
            this.sendPacket(ActionFail.STATIC);
            return;
        }
        String string = CharacterVariablesDAO.getInstance().getVar(n, "hwidlock@");
        if (!(string == null || string.isEmpty() || gameClient.getHwid() == null || gameClient.getHwid().isEmpty() || string.equalsIgnoreCase(gameClient.getHwid()))) {
            this.sendPacket((L2GameServerPacket)new ExShowScreenMessage("HWID is locked.", 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
            this.sendPacket(ActionFail.STATIC);
            return;
        }
        String string2 = CharacterVariablesDAO.getInstance().getVar(n, "iplock@");
        if (!(string2 == null || string2.isEmpty() || gameClient.getIpAddr() == null || gameClient.getIpAddr().isEmpty() || string2.equalsIgnoreCase(gameClient.getIpAddr()))) {
            this.sendPacket((L2GameServerPacket)new ExShowScreenMessage("IP address is locked.", 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
            this.sendPacket(ActionFail.STATIC);
            return;
        }
        if (Config.USE_SECOND_PASSWORD_AUTH && !gameClient.isSecondPasswordAuthed()) {
            if (gameClient.getSecondPasswordAuth().isSecondPasswordSet()) {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordCheck(Ex2ndPasswordCheck.Ex2ndPasswordCheckResult.CHECK));
            } else {
                gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordCheck(Ex2ndPasswordCheck.Ex2ndPasswordCheckResult.CREATE));
            }
            return;
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        if (Config.OPEN_SERVER_TIME != null && localDateTime.isBefore(Config.OPEN_SERVER_TIME) && !Config.OPEN_SERVER_ALLOWED_CHARS.contains(n)) {
            CustomMessage customMessage = new CustomMessage("service_server_opened", null, new Object[0]).addString(String.valueOf(Config.OPEN_SERVER_TIME));
            this.sendPacket((L2GameServerPacket)new ExShowScreenMessage(customMessage == null ? "Missing data/strings service_server_opened" : String.valueOf(customMessage), 20000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
            this.sendPacket(ActionFail.STATIC);
            return;
        }
        Player player = gameClient.loadCharFromDisk(this.pZ);
        if (player == null) {
            this.sendPacket(ActionFail.STATIC);
            return;
        }
        if (player.getAccessLevel() < 0) {
            player.setAccessLevel(0);
        }
        gameClient.setState(GameClient.GameClientState.IN_GAME);
        gameClient.sendPacket((L2GameServerPacket)new CharSelected(player, gameClient.getSessionKey().playOkID1));
    }
}
