/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.network.l2.c2s.ScriptPacket;

public class ScriptExPacket
extends ScriptPacket {
    private Integer f;

    public Integer getOpEx() {
        return this.f;
    }

    public ScriptExPacket setOpEx(Integer n) {
        this.f = n;
        return this;
    }

    public void OnReceiveExPacket_0x0106() {
        System.currentTimeMillis();
    }
}
