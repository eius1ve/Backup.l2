/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.model.mail.Mail;

class MailDAO.1
extends LinkedHashMap<Integer, Mail> {
    MailDAO.1(int n, float f, boolean bl) {
        super(n, f, bl);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Mail> entry) {
        return this.size() > Config.MAIL_CACHE_SIZE;
    }
}
