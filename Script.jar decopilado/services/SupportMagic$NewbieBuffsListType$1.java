/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 */
package services;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import services.SupportMagic;

final class SupportMagic.NewbieBuffsListType.1
extends SupportMagic.NewbieBuffsListType {
    @Override
    public boolean canUse(Player player) {
        return !player.isMageClass() || player.getTemplate().race == Race.orc;
    }
}
