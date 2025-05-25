/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.SendablePacket;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.utils.Log;

public abstract class L2GameServerPacket
extends SendablePacket<GameClient>
implements IStaticPacket {
    @Override
    public final boolean write() {
        if (Config.DEBUG) {
            System.out.println("SEND: " + this.getType());
        }
        try {
            this.writeImpl();
            return true;
        }
        catch (Exception exception) {
            Log.network("Client: " + (MMOClient)this.getClient() + " - Failed writing: " + this.getType(), exception);
            return false;
        }
    }

    protected abstract void writeImpl();

    protected void writeEx(int n) {
        this.writeC(254);
        this.writeH(n);
    }

    protected void writeD(boolean bl) {
        this.writeD(bl ? 1 : 0);
    }

    protected void writeOptionalD(int n) {
        if (n >= Short.MAX_VALUE) {
            this.writeH(Short.MAX_VALUE);
            this.writeD(n);
        } else {
            this.writeH(n);
        }
    }

    protected void writeDD(int[] nArray, boolean bl) {
        if (bl) {
            this.getByteBuffer().putInt(nArray.length);
        }
        for (int n : nArray) {
            this.getByteBuffer().putInt(n);
        }
    }

    protected void writeDD(int[] nArray) {
        this.writeDD(nArray, false);
    }

    public String getType() {
        return "[S] " + this.getClass().getSimpleName();
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        return this;
    }
}
