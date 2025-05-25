/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.TamedBeastInstance;
import l2.gameserver.model.items.ItemInstance;

private static class TamedBeastInstance.CheckDuration
extends RunnableImpl {
    private TamedBeastInstance a;

    TamedBeastInstance.CheckDuration(TamedBeastInstance tamedBeastInstance) {
        this.a = tamedBeastInstance;
    }

    @Override
    public void runImpl() throws Exception {
        Player player = this.a.getPlayer();
        if (player == null || !player.isOnline()) {
            this.a.doDespawn();
            return;
        }
        if (this.a.getDistance(player) > 2000.0) {
            this.a.doDespawn();
            return;
        }
        int n = this.a.getFoodType();
        this.a.setRemainingTime(this.a.getRemainingTime() - 60000);
        ItemInstance itemInstance = null;
        int n2 = this.a.getItemIdBySkillId(n);
        if (n2 > 0) {
            itemInstance = player.getInventory().getItemByItemId(n2);
        }
        if (itemInstance != null && itemInstance.getCount() >= 1L) {
            this.a.bL();
            player.getInventory().destroyItem(itemInstance, 1L);
        } else if (this.a.getRemainingTime() < 900000) {
            this.a.setRemainingTime(-1);
        }
        if (this.a.getRemainingTime() <= 0) {
            this.a.doDespawn();
        }
    }
}
