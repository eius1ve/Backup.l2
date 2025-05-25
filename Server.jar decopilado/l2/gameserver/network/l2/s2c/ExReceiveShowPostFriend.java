/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.napile.primitive.maps.IntObjectMap;

public class ExReceiveShowPostFriend
extends L2GameServerPacket {
    private IntObjectMap<String> o;

    public ExReceiveShowPostFriend(Player player) {
        this.o = player.getPostFriends();
    }

    @Override
    public void writeImpl() {
        this.writeEx(212);
        this.writeD(this.o.size());
        for (String string : this.o.values()) {
            this.writeS(string);
        }
    }
}
