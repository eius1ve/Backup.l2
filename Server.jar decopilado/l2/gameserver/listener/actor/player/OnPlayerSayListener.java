/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.player;

import l2.gameserver.listener.PlayerListener;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;

public interface OnPlayerSayListener
extends PlayerListener {
    public void onSay(Player var1, ChatType var2, String var3, String var4);
}
