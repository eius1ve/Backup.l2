/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.ExIsCharNameCreatable;
import l2.gameserver.utils.Util;

public class RequestCharacterNameCreatable
extends L2GameClientPacket {
    private String ec;

    @Override
    protected void readImpl() {
        this.ec = this.readS();
    }

    @Override
    protected void runImpl() {
        if (CharacterDAO.getInstance().accountCharNumber(((GameClient)this.getClient()).getLogin()) >= 8) {
            this.sendPacket(ExIsCharNameCreatable.TOO_MANY_CHARACTERS);
            return;
        }
        if (!Util.isMatchingRegexp(this.ec, Config.CNAME_TEMPLATE)) {
            this.sendPacket(ExIsCharNameCreatable.ENTER_CHAR_NAME__MAX_16_CHARS);
            return;
        }
        if (Util.isMatchingRegexp(this.ec.toLowerCase(), Config.CNAME_FORBIDDEN_PATTERN) || CharacterDAO.getInstance().getObjectIdByName(this.ec) > 0) {
            this.sendPacket(ExIsCharNameCreatable.NAME_ALREADY_EXISTS);
            return;
        }
        for (String string : Config.CNAME_FORBIDDEN_NAMES) {
            if (!string.equalsIgnoreCase(this.ec)) continue;
            this.sendPacket(ExIsCharNameCreatable.WRONG_NAME);
            return;
        }
        this.sendPacket(ExIsCharNameCreatable.SUCCESS);
    }
}
