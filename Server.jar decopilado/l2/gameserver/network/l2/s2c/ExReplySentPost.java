/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ExReplySentPost
extends AbstractItemListPacket {
    private final Mail c;

    public ExReplySentPost(Mail mail) {
        this.c = mail;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(174);
        this.writeD(this.c.getType().ordinal());
        this.writeD(this.c.getMessageId());
        this.writeD(this.c.isPayOnDelivery() ? 1 : 0);
        this.writeS(this.c.getReceiverName());
        this.writeS(this.c.getTopic());
        this.writeS(this.c.getBody());
        this.writeD(this.c.getAttachments().size());
        for (ItemInstance itemInstance : this.c.getAttachments()) {
            this.writeItemInfo(new ItemInfo(itemInstance));
            this.writeD(itemInstance.getObjectId());
        }
        this.writeQ(this.c.getPrice());
        this.writeD(0);
        this.writeD(0);
    }
}
