/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.model;

public class ForceReply {
    boolean force_reply;
    String input_field_placeholder;
    boolean selective;

    public ForceReply(boolean bl, String string, boolean bl2) {
        this.force_reply = bl;
        this.input_field_placeholder = string;
        this.selective = bl2;
    }
}
