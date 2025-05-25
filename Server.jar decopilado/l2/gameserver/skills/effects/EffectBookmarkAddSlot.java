/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.Config;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExGetBookMarkInfo;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectBookmarkAddSlot
extends Effect {
    private final int CT;

    public EffectBookmarkAddSlot(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.CT = effectTemplate.getParam().getInteger("i_bookmark_add_slot", 0);
    }

    @Override
    public void onStart() {
        if (!this._effected.isPlayer()) {
            return;
        }
        Player player = (Player)this._effected;
        if (player.getTpBookmarkSize() >= Config.EX_MAX_TELEPORT_BOOKMARK_SIZE) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_NUMBER_OF_MY_TELEPORTS_SLOTS_HAS_REACHED_ITS_MAXIMUM_LIMIT);
            return;
        }
        player.setTpBookmarkSize(player.getTpBookmarkSize() + this.CT);
        player.sendPacket((IStaticPacket)SystemMsg.THE_NUMBER_OF_MY_TELEPORTS_SLOTS_HAS_BEEN_INCREASED);
        player.sendPacket((IStaticPacket)new ExGetBookMarkInfo(player));
        super.onStart();
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
