/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import services.ACP;

final class ACP.ACPType.2
extends ACP.ACPType {
    private ACP.ACPType.2(String string2) {
    }

    @Override
    public boolean isEnabled() {
        return Config.SERVICES_CPACP_ENABLE;
    }

    @Override
    public void apply(Player player) {
        if (!player.getListeners().getListeners(ACP.CPACPHelper.class).isEmpty()) {
            return;
        }
        player.addListener((Listener)new ACP.CPACPHelper(player));
    }

    @Override
    public void remove(Player player) {
        player.getListeners().getListeners(ACP.CPACPHelper.class).forEach(arg_0 -> ((PlayerListenerList)player.getListeners()).remove(arg_0));
    }

    @Override
    public int[] getPotionsItemIds() {
        return Config.SERVICES_CPACP_POTIONS_ITEM_IDS;
    }

    @Override
    protected int getActMinPercent() {
        return Config.SERVICES_CPACP_MIN_PERCENT;
    }

    @Override
    protected int getActMaxPercent() {
        return Config.SERVICES_CPACP_MAX_PERCENT;
    }

    @Override
    protected int getActDefPercent() {
        return Config.SERVICES_CPACP_DEF_PERCENT;
    }
}
