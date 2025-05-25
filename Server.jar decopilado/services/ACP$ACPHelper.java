/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.PlayerListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExUseSharedGroupItem
 *  l2.gameserver.skills.TimeStamp
 */
package services;

import java.util.concurrent.atomic.AtomicReference;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.PlayerListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExUseSharedGroupItem;
import l2.gameserver.skills.TimeStamp;
import services.ACP;

private static abstract class ACP.ACPHelper
implements Runnable,
PlayerListener {
    private final HardReference<Player> ad;
    private final AtomicReference<ACPHelperState> d;
    private final ACP.ACPType a;

    protected ACP.ACPHelper(Player player, ACP.ACPType aCPType) {
        this.a = aCPType;
        this.ad = player.getRef();
        this.d = new AtomicReference<ACPHelperState>(ACPHelperState.IDLE);
    }

    protected Player getPlayer() {
        return (Player)this.ad.get();
    }

    public ACP.ACPType getType() {
        return this.a;
    }

    protected void act(Player player) {
        if (this.getPlayer() != player) {
            if (player != null) {
                player.removeListener((Listener)this);
            }
            if (this.getPlayer() != null) {
                this.getPlayer().removeListener((Listener)this);
            }
            return;
        }
        if (this.d.compareAndSet(ACPHelperState.IDLE, ACPHelperState.APPLY)) {
            this.schedule(100L);
        }
    }

    @Override
    public void run() {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        try {
            if (this.d.compareAndSet(ACPHelperState.APPLY, ACPHelperState.USE)) {
                this.an(player);
            }
        }
        catch (Exception exception) {
            dT.error("Exception in ACP helper task", (Throwable)exception);
            this.d.set(ACPHelperState.IDLE);
        }
    }

    private void schedule(long l) {
        ThreadPoolManager.getInstance().schedule((Runnable)this, l);
    }

    protected boolean canUse(Player player) {
        if (player == null) {
            return false;
        }
        if (player.isDead() || player.isOutOfControl() || player.isInStoreMode()) {
            return false;
        }
        if (player.isFishing() || player.isHealBlocked() || player.isOlyParticipant()) {
            return false;
        }
        return Config.SERVICES_HPACP_WORK_IN_PEACE_ZONE || !player.isInZonePeace();
    }

    private void an(Player player) {
        if (!this.canUse(player)) {
            this.d.compareAndSet(ACPHelperState.USE, ACPHelperState.IDLE);
            return;
        }
        ItemInstance itemInstance = this.a(player);
        if (itemInstance == null) {
            this.d.compareAndSet(ACPHelperState.USE, ACPHelperState.IDLE);
            return;
        }
        long l = this.a(itemInstance, player);
        if (l <= 0L) {
            this.g(player, itemInstance);
            if (this.canUse(player) && this.d.compareAndSet(ACPHelperState.USE, ACPHelperState.APPLY)) {
                this.schedule(100L);
            } else {
                this.d.compareAndSet(ACPHelperState.USE, ACPHelperState.IDLE);
            }
        } else if (this.d.compareAndSet(ACPHelperState.USE, ACPHelperState.APPLY)) {
            this.schedule(100L + l);
        }
    }

    private long a(ItemInstance itemInstance, Player player) {
        if ((long)itemInstance.getTemplate().getReuseDelay() == 0L) {
            return 0L;
        }
        TimeStamp timeStamp = player.getSharedGroupReuse(itemInstance.getTemplate().getReuseGroup());
        if (timeStamp == null || !timeStamp.hasNotPassed()) {
            return 0L;
        }
        return Math.max(0L, timeStamp.getEndTime() - System.currentTimeMillis());
    }

    private ItemInstance a(Player player) {
        if (player.isInStoreMode() || player.isOutOfControl()) {
            return null;
        }
        int[] nArray = this.a.getPotionsItemIds();
        ItemInstance itemInstance = null;
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            ItemInstance itemInstance2 = player.getInventory().getItemByItemId(n);
            if (itemInstance2 == null || !itemInstance2.getTemplate().testCondition((Playable)player, itemInstance2, false) || player.getInventory().isLockedItem(itemInstance2)) continue;
            if (itemInstance == null) {
                itemInstance = itemInstance2;
                continue;
            }
            TimeStamp timeStamp = player.getSharedGroupReuse(itemInstance2.getTemplate().getReuseGroup());
            if (timeStamp == null) {
                itemInstance = itemInstance2;
                continue;
            }
            TimeStamp timeStamp2 = player.getSharedGroupReuse(itemInstance.getTemplate().getReuseGroup());
            if (timeStamp2 == null || timeStamp2.getEndTime() <= timeStamp.getEndTime()) continue;
            itemInstance = itemInstance2;
        }
        return itemInstance;
    }

    private boolean g(Player player, ItemInstance itemInstance) {
        if (itemInstance.getTemplate().getHandler().useItem((Playable)player, itemInstance, false)) {
            long l = itemInstance.getTemplate().getReuseType().next(itemInstance);
            if (l > System.currentTimeMillis()) {
                TimeStamp timeStamp = new TimeStamp(itemInstance.getItemId(), l, (long)itemInstance.getTemplate().getReuseDelay());
                player.addSharedGroupReuse(itemInstance.getTemplate().getReuseGroup(), timeStamp);
                if (itemInstance.getTemplate().getReuseDelay() > 0) {
                    player.sendPacket((IStaticPacket)new ExUseSharedGroupItem(itemInstance.getTemplate().getDisplayReuseGroup(), timeStamp));
                }
            }
            return true;
        }
        return false;
    }

    static final class ACPHelperState
    extends Enum<ACPHelperState> {
        public static final /* enum */ ACPHelperState IDLE = new ACPHelperState();
        public static final /* enum */ ACPHelperState APPLY = new ACPHelperState();
        public static final /* enum */ ACPHelperState USE = new ACPHelperState();
        private static final /* synthetic */ ACPHelperState[] a;

        public static ACPHelperState[] values() {
            return (ACPHelperState[])a.clone();
        }

        public static ACPHelperState valueOf(String string) {
            return Enum.valueOf(ACPHelperState.class, string);
        }

        private static /* synthetic */ ACPHelperState[] a() {
            return new ACPHelperState[]{IDLE, APPLY, USE};
        }

        static {
            a = ACPHelperState.a();
        }
    }
}
