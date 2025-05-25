/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SetSummonRemainTime;
import l2.gameserver.network.l2.s2c.SystemMessage;

class SummonInstance.Lifetime
extends RunnableImpl {
    SummonInstance.Lifetime() {
    }

    @Override
    public void runImpl() throws Exception {
        Player player = SummonInstance.this.getPlayer();
        if (player == null) {
            SummonInstance.this.N = null;
            SummonInstance.this.unSummon();
            return;
        }
        int n = SummonInstance.this.isInCombat() ? 5000 : 1250;
        SummonInstance.this.nz -= n;
        if (SummonInstance.this.nz <= 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_SERVITOR_HAS_VANISHED_YOULL_NEED_TO_SUMMON_A_NEW_ONE);
            SummonInstance.this.N = null;
            SummonInstance.this.unSummon();
            return;
        }
        SummonInstance.this.ny -= n;
        if (SummonInstance.this.nv > 0 && SummonInstance.this.nw > 0 && SummonInstance.this.ny <= 0) {
            if (player.getInventory().destroyItemByItemId(SummonInstance.this.getItemConsumeIdInTime(), SummonInstance.this.getItemConsumeCountInTime())) {
                SummonInstance.this.ny = SummonInstance.this.nx;
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.A_SUMMONED_MONSTER_USES_S1).addItemName(SummonInstance.this.getItemConsumeIdInTime()));
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.SINCE_YOU_DO_NOT_HAVE_ENOUGH_ITEMS_TO_MAINTAIN_THE_SERVITORS_STAY_THE_SERVITOR_HAS_DISAPPEARED);
                SummonInstance.this.unSummon();
                return;
            }
        }
        player.sendPacket((IStaticPacket)new SetSummonRemainTime(SummonInstance.this));
        SummonInstance.this.N = ThreadPoolManager.getInstance().schedule(this, 5000L);
    }
}
