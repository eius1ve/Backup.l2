/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.model;

import l2.gameserver.handler.telegram.model.Chat;
import l2.gameserver.handler.telegram.model.MessageEntity;
import l2.gameserver.handler.telegram.model.User;

public class Message {
    public Long message_id;
    public User from;
    public Chat chat;
    public Long date;
    public String text;
    MessageEntity[] entities;
}
