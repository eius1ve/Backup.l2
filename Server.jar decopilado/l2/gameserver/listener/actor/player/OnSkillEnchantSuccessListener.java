/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.player;

import l2.gameserver.listener.PlayerListener;
import l2.gameserver.model.Player;

public interface OnSkillEnchantSuccessListener
extends PlayerListener {
    public void onSkillEnchantSuccess(Player var1, int var2, int var3);
}
