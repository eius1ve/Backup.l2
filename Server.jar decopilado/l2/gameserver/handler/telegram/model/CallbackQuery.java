/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.model;

import l2.gameserver.handler.telegram.model.Message;
import l2.gameserver.handler.telegram.model.User;

public class CallbackQuery {
    public String id;
    public User from;
    public Message message;
    public String inline_message_id;
    public String data;
}
