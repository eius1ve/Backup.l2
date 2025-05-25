/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public abstract class NpcStringContainer
extends L2GameServerPacket {
    private final NpcString c;
    private final String[] aN = new String[5];

    protected NpcStringContainer(NpcString npcString, String ... stringArray) {
        this.c = npcString;
        System.arraycopy(stringArray, 0, this.aN, 0, stringArray.length);
    }

    protected NpcString getNpcString() {
        return this.c;
    }

    protected String[] getParameters() {
        return this.aN;
    }

    protected void writeElements() {
        this.writeD(this.c.getId());
        for (String string : this.aN) {
            this.writeS(string);
        }
    }
}
