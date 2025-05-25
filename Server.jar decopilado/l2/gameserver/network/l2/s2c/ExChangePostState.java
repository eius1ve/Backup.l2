/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExChangePostState
extends L2GameServerPacket {
    private boolean es;
    private Mail[] a;
    private int uM;

    public ExChangePostState(boolean bl, int n, Mail ... mailArray) {
        this.es = bl;
        this.a = mailArray;
        this.uM = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(180);
        this.writeD(this.es ? 1 : 0);
        this.writeD(this.a.length);
        for (Mail mail : this.a) {
            this.writeD(mail.getMessageId());
            this.writeD(this.uM);
        }
    }
}
