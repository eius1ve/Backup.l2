/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.as2gs;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.ReceivablePacket;
import l2.gameserver.network.authcomm.SessionKey;
import l2.gameserver.network.authcomm.gs2as.PlayerInGame;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.CharacterSelectionInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.LoginFail;
import l2.gameserver.network.l2.s2c.ServerClose;

public class PlayerAuthResponse
extends ReceivablePacket {
    private String account;
    private boolean al;
    private int pu;
    private int pv;
    private int pw;
    private int px;
    private int dH = Config.REQUEST_ID;
    private byte[] hwid;

    @Override
    public void readImpl() {
        this.account = this.readS();
        boolean bl = this.al = this.readC() == 1;
        if (this.al) {
            this.pu = this.readD();
            this.pv = this.readD();
            this.pw = this.readD();
            this.px = this.readD();
            if (this.getAvaliableBytes() > 0) {
                this.dH = this.readD();
                if (this.getAvaliableBytes() > 0) {
                    int n = this.readC();
                    this.hwid = new byte[Math.max(n, 0)];
                    this.readB(this.hwid);
                }
            }
        }
    }

    @Override
    protected void runImpl() {
        SessionKey sessionKey = new SessionKey(this.pw, this.px, this.pu, this.pv);
        GameClient gameClient = AuthServerCommunication.getInstance().removeWaitingClient(this.account);
        if (gameClient == null) {
            return;
        }
        if (this.al && gameClient.getSessionKey().equals(sessionKey)) {
            Object object;
            gameClient.setAuthed(true);
            gameClient.setState(GameClient.GameClientState.AUTHED);
            gameClient.setServerId(this.dH);
            GameClient gameClient2 = AuthServerCommunication.getInstance().addAuthedClient(gameClient);
            if (!Config.ALLOW_MULILOGIN && gameClient2 != null) {
                gameClient2.setAuthed(false);
                object = gameClient2.getActiveChar();
                if (object != null) {
                    ((Player)object).sendPacket((IStaticPacket)SystemMsg.ANOTHER_PERSON_HAS_LOGGED_IN_WITH_THE_SAME_ACCOUNT);
                    ((Player)object).logout();
                } else {
                    gameClient2.close(ServerClose.STATIC);
                }
            }
            if (this.hwid != null) {
                gameClient.getSessionKey().hwid = this.hwid;
            }
            this.sendPacket(new PlayerInGame(gameClient.getLogin(), gameClient.getHwid()));
            object = new CharacterSelectionInfo(gameClient.getLogin(), gameClient.getSessionKey().playOkID1);
            gameClient.sendPacket(new L2GameServerPacket[]{new LoginFail(-1), object});
            gameClient.setCharSelection(((CharacterSelectionInfo)object).getCharInfo());
        } else {
            gameClient.close(new LoginFail(LoginFail.ACCESS_FAILED_TRY_LATER));
        }
    }
}
