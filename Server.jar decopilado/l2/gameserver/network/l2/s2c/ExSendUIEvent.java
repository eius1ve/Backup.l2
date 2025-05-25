/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.s2c.NpcStringContainer;

public class ExSendUIEvent
extends NpcStringContainer {
    private int fW;
    private boolean eO;
    private boolean eP;
    private int xc;
    private int xd;

    public ExSendUIEvent(Player player, boolean bl, boolean bl2, int n, int n2, String ... stringArray) {
        this(player, bl, bl2, n, n2, NpcString.NONE, stringArray);
    }

    public ExSendUIEvent(Player player, boolean bl, boolean bl2, int n, int n2, NpcString npcString, String ... stringArray) {
        super(npcString, stringArray);
        this.fW = player.getObjectId();
        this.eO = bl;
        this.eP = bl2;
        this.xc = n;
        this.xd = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(143);
        this.writeD(this.fW);
        this.writeD(this.eO ? 1 : 0);
        this.writeD(0);
        this.writeD(0);
        this.writeS(this.eP ? "1" : "0");
        this.writeS(String.valueOf(this.xc / 60));
        this.writeS(String.valueOf(this.xc % 60));
        this.writeS(String.valueOf(this.xd / 60));
        this.writeS(String.valueOf(this.xd % 60));
        this.writeElements();
    }
}
