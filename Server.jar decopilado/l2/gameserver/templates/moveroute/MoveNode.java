/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.moveroute;

import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.utils.Location;

public class MoveNode
extends Location {
    private static final long eb = 8291528118019681063L;
    private final String gx;
    private final ChatType c;
    private final long ec;
    private final int GY;

    public MoveNode(int n, int n2, int n3, String string, int n4, long l, ChatType chatType) {
        super(n, n2, n3);
        this.gx = string;
        this.GY = n4;
        this.ec = l;
        this.c = chatType;
    }

    public String getNpcMsgAddress() {
        return this.gx;
    }

    public long getDelay() {
        return this.ec;
    }

    public int getSocialId() {
        return this.GY;
    }

    public ChatType getChatType() {
        return this.c;
    }
}
