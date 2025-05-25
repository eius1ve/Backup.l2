/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Iterator;
import l2.commons.collections.EmptyIterator;
import l2.gameserver.model.Player;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.network.l2.components.IStaticPacket;

class PlayerGroup.1
implements PlayerGroup {
    PlayerGroup.1() {
    }

    @Override
    public void broadCast(IStaticPacket ... iStaticPacketArray) {
    }

    @Override
    public Iterator<Player> iterator() {
        return EmptyIterator.getInstance();
    }
}
