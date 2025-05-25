/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.SysMsgContainer;

private static class SysMsgContainer.PlayerNameArgument
extends SysMsgContainer.StringArgument {
    public SysMsgContainer.PlayerNameArgument(Player player) {
        super(player.isCursedWeaponEquipped() ? player.getTransformationName() : player.getName());
    }

    @Override
    SysMsgContainer.Types getType() {
        return SysMsgContainer.Types.TEXT;
    }
}
