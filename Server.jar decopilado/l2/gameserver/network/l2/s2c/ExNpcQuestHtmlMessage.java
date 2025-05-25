/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.NpcHtmlMessage;

public class ExNpcQuestHtmlMessage
extends NpcHtmlMessage {
    private int _questId;

    public ExNpcQuestHtmlMessage(int n, int n2) {
        super(n);
        this._questId = n2;
    }

    @Override
    protected void writeImpl() {
        if (this._html != null) {
            this.writeEx(142);
            this.writeD(this._npcObjId);
            this.writeS(this._html);
            this.writeD(this._questId);
        }
    }
}
