/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class ExReplyReceivedPost
extends AbstractItemListPacket {
    private final Mail b;

    public ExReplyReceivedPost(Mail mail) {
        this.b = mail;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(172);
        this.writeD(this.b.getType().ordinal());
        this.writeD(this.b.getMessageId());
        this.writeD(this.b.isPayOnDelivery() ? 1 : 0);
        this.writeD(this.b.getType() == Mail.SenderType.NORMAL ? 0 : 1);
        this.writeS(this.b.getSenderName());
        this.writeS(this.b.getTopic());
        this.writeS(this.b.getBody());
        this.writeD(this.b.getAttachments().size());
        for (ItemInstance itemInstance : this.b.getAttachments()) {
            this.writeItemInfo(new ItemInfo(itemInstance));
            this.writeD(itemInstance.getObjectId());
        }
        this.writeQ(this.b.getPrice());
        this.writeD(this.b.getAttachments().size() > 0 ? 1 : 0);
    }
}
