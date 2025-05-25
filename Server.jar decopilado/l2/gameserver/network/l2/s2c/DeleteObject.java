/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class DeleteObject
extends L2GameServerPacket {
    private int fW;

    public DeleteObject(GameObject gameObject) {
        this.fW = gameObject.getObjectId();
    }

    @Override
    protected final void writeImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getObjectId() == this.fW) {
            return;
        }
        this.writeC(8);
        this.writeD(this.fW);
        this.writeD(0);
    }

    @Override
    public String getType() {
        return super.getType() + " " + GameObjectsStorage.findObject(this.fW) + " (" + this.fW + ")";
    }
}
