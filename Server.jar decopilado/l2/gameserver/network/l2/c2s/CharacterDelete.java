/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.database.mysql;
import l2.gameserver.model.base.CharacterDeleteFailType;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.CharacterDeleteFail;
import l2.gameserver.network.l2.s2c.CharacterDeleteSuccess;
import l2.gameserver.network.l2.s2c.CharacterSelectionInfo;
import l2.gameserver.network.l2.s2c.Ex2ndPasswordCheck;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterDelete
extends L2GameClientPacket {
    private static final Logger cI = LoggerFactory.getLogger(CharacterDelete.class);
    private int pZ;

    @Override
    protected void readImpl() {
        this.pZ = this.readD();
    }

    @Override
    protected void runImpl() {
        int n = this.y();
        int n2 = this.z();
        GameClient gameClient = (GameClient)this.getClient();
        if (n > 0 || n2 > 0) {
            if (n == 2) {
                this.sendPacket((L2GameServerPacket)new CharacterDeleteFail(CharacterDeleteFailType.PLEDGE_MASTER));
            } else if (n == 1) {
                this.sendPacket((L2GameServerPacket)new CharacterDeleteFail(CharacterDeleteFailType.PLEDGE_MEMBER));
            } else if (n2 > 0) {
                this.sendPacket((L2GameServerPacket)new CharacterDeleteFail(CharacterDeleteFailType.UNKNOWN));
            }
        } else {
            if (Config.USE_SECOND_PASSWORD_AUTH && !gameClient.isSecondPasswordAuthed()) {
                if (gameClient.getSecondPasswordAuth().isSecondPasswordSet()) {
                    gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordCheck(Ex2ndPasswordCheck.Ex2ndPasswordCheckResult.CHECK));
                } else {
                    gameClient.sendPacket((L2GameServerPacket)new Ex2ndPasswordCheck(Ex2ndPasswordCheck.Ex2ndPasswordCheckResult.CREATE));
                }
                return;
            }
            try {
                if (Config.DELETE_DAYS == 0.0) {
                    gameClient.deleteCharacterInSlot(this.pZ);
                } else {
                    gameClient.markToDeleteChar(this.pZ);
                }
                gameClient.sendPacket((L2GameServerPacket)new CharacterDeleteSuccess());
            }
            catch (Exception exception) {
                cI.error(exception.getMessage(), (Throwable)exception);
            }
        }
        CharacterSelectionInfo characterSelectionInfo = new CharacterSelectionInfo(gameClient.getLogin(), gameClient.getSessionKey().playOkID1);
        gameClient.sendPacket((L2GameServerPacket)characterSelectionInfo);
        gameClient.setCharSelection(characterSelectionInfo.getCharInfo());
    }

    private int y() {
        int n = ((GameClient)this.getClient()).getObjectIdForSlot(this.pZ);
        if (n == -1) {
            return 0;
        }
        if (mysql.simple_get_int("clanid", "characters", "obj_Id=" + n) > 0) {
            if (mysql.simple_get_int("leader_id", "clan_subpledges", "leader_id=" + n + " AND type = 0") > 0) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private int z() {
        int n = ((GameClient)this.getClient()).getObjectIdForSlot(this.pZ);
        if (n == -1) {
            return 0;
        }
        if (mysql.simple_get_int("online", "characters", "obj_Id=" + n) > 0) {
            return 1;
        }
        return 0;
    }
}
