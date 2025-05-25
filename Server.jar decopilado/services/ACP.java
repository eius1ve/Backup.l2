/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.listener.PlayerListener
 *  l2.gameserver.listener.actor.OnCurrentHpDamageListener
 *  l2.gameserver.listener.actor.OnCurrentMpReduceListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.listener.actor.player.OnPlayerExitListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExUseSharedGroupItem
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.skills.TimeStamp
 *  l2.gameserver.stats.Stats
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.util.concurrent.atomic.AtomicReference;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.listener.PlayerListener;
import l2.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2.gameserver.listener.actor.OnCurrentMpReduceListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExUseSharedGroupItem;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.stats.Stats;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ACP
implements IVoicedCommandHandler,
OnPlayerEnterListener,
OnPlayerExitListener,
ScriptFile {
    private static final Logger dT = LoggerFactory.getLogger(ACP.class);
    private static final long er = 100L;
    private String[] o = new String[]{"acp"};

    public String[] getVoicedCommandList() {
        return this.o;
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (ACP.y() && string.equalsIgnoreCase("acp")) {
            String[] stringArray = string2.split("\\s+");
            if (stringArray.length > 1) {
                String string3 = stringArray[0];
                String string4 = stringArray[1];
                for (ACPType aCPType : ACPType.VALUES) {
                    if (!aCPType.isEnabled()) {
                        aCPType.remove(player);
                        continue;
                    }
                    if (!aCPType.getCfgName().equalsIgnoreCase(string3)) continue;
                    if (string4.equalsIgnoreCase("true") || string4.equalsIgnoreCase("on") || string4.equalsIgnoreCase("enable") || string4.equalsIgnoreCase("1") || string4.equalsIgnoreCase("yes")) {
                        aCPType.apply(player);
                        aCPType.setEnabled(player, true);
                        player.sendMessage(new CustomMessage("services.ACP.Enabled", player, new Object[]{aCPType.getCfgName()}));
                        continue;
                    }
                    if (string4.equalsIgnoreCase("false") || string4.equalsIgnoreCase("of") || string4.equalsIgnoreCase("off") || string4.equalsIgnoreCase("disable") || string4.equalsIgnoreCase("0") || string4.equalsIgnoreCase("no")) {
                        aCPType.remove(player);
                        aCPType.setEnabled(player, false);
                        player.sendMessage(new CustomMessage("services.ACP.Disabled", player, new Object[]{aCPType.getCfgName()}));
                        continue;
                    }
                    if (!aCPType.isEnabled(player)) continue;
                    int n = aCPType.setActPercent(player, NumberUtils.toInt((String)string4, (int)aCPType.getActDefPercent()));
                    aCPType.apply(player);
                    player.sendMessage(new CustomMessage("services.ACP.ActPercentSet", player, new Object[]{aCPType.getCfgName(), n}));
                }
            }
            ACP.am(player);
            return true;
        }
        return false;
    }

    private static void am(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        npcHtmlMessage.setFile("command/acp.htm");
        if (ACPType.HPACP.isEnabled(player)) {
            npcHtmlMessage.replace("%hpap%", String.valueOf(ACPType.HPACP.getActPercent(player)) + "%");
        } else {
            npcHtmlMessage.replace("%hpap%", "Off");
        }
        if (ACPType.CPACP.isEnabled(player)) {
            npcHtmlMessage.replace("%cpap%", String.valueOf(ACPType.CPACP.getActPercent(player)) + "%");
        } else {
            npcHtmlMessage.replace("%cpap%", "Off");
        }
        if (ACPType.MPACP.isEnabled(player)) {
            npcHtmlMessage.replace("%mpap%", String.valueOf(ACPType.MPACP.getActPercent(player)) + "%");
        } else {
            npcHtmlMessage.replace("%mpap%", "Off");
        }
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static boolean y() {
        return Config.SERVICES_HPACP_ENABLE || Config.SERVICES_CPACP_ENABLE || Config.SERVICES_MPACP_ENABLE;
    }

    public void onPlayerEnter(Player player) {
        if (ACP.y()) {
            for (ACPType aCPType : ACPType.VALUES) {
                if (!aCPType.isEnabled() || !aCPType.isEnabled(player)) continue;
                aCPType.apply(player);
            }
        }
    }

    public void onPlayerExit(Player player) {
        if (ACP.y()) {
            for (ACPType aCPType : ACPType.VALUES) {
                aCPType.remove(player);
            }
        }
    }

    public void onLoad() {
        if (ACP.y()) {
            PlayerListenerList.addGlobal((Listener)this);
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
            dT.info("ACPService: Loaded");
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        PlayerListenerList.removeGlobal((Listener)this);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static abstract class ACPType
    extends Enum<ACPType> {
        public static final /* enum */ ACPType HPACP = new ACPType("HP"){

            @Override
            public boolean isEnabled() {
                return Config.SERVICES_HPACP_ENABLE;
            }

            @Override
            public void apply(Player player) {
                if (!player.getListeners().getListeners(HPACPHelper.class).isEmpty()) {
                    return;
                }
                player.addListener((Listener)new HPACPHelper(player));
            }

            @Override
            public void remove(Player player) {
                player.getListeners().getListeners(HPACPHelper.class).forEach(arg_0 -> ((PlayerListenerList)player.getListeners()).remove(arg_0));
            }

            @Override
            public int[] getPotionsItemIds() {
                return Config.SERVICES_HPACP_POTIONS_ITEM_IDS;
            }

            @Override
            protected int getActMinPercent() {
                return Config.SERVICES_HPACP_MIN_PERCENT;
            }

            @Override
            protected int getActMaxPercent() {
                return Config.SERVICES_HPACP_MAX_PERCENT;
            }

            @Override
            protected int getActDefPercent() {
                return Config.SERVICES_HPACP_DEF_PERCENT;
            }
        };
        public static final /* enum */ ACPType CPACP = new ACPType("CP"){

            @Override
            public boolean isEnabled() {
                return Config.SERVICES_CPACP_ENABLE;
            }

            @Override
            public void apply(Player player) {
                if (!player.getListeners().getListeners(CPACPHelper.class).isEmpty()) {
                    return;
                }
                player.addListener((Listener)new CPACPHelper(player));
            }

            @Override
            public void remove(Player player) {
                player.getListeners().getListeners(CPACPHelper.class).forEach(arg_0 -> ((PlayerListenerList)player.getListeners()).remove(arg_0));
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
        };
        public static final /* enum */ ACPType MPACP = new ACPType("MP"){

            @Override
            public boolean isEnabled() {
                return Config.SERVICES_MPACP_ENABLE;
            }

            @Override
            public void apply(Player player) {
                if (!player.getListeners().getListeners(MPACPHelper.class).isEmpty()) {
                    return;
                }
                player.addListener((Listener)new MPACPHelper(player));
            }

            @Override
            public void remove(Player player) {
                player.getListeners().getListeners(MPACPHelper.class).forEach(arg_0 -> ((PlayerListenerList)player.getListeners()).remove(arg_0));
            }

            @Override
            public int[] getPotionsItemIds() {
                return Config.SERVICES_MPACP_POTIONS_ITEM_IDS;
            }

            @Override
            protected int getActMinPercent() {
                return Config.SERVICES_MPACP_MIN_PERCENT;
            }

            @Override
            protected int getActMaxPercent() {
                return Config.SERVICES_MPACP_MAX_PERCENT;
            }

            @Override
            protected int getActDefPercent() {
                return Config.SERVICES_MPACP_DEF_PERCENT;
            }
        };
        private final String hn;
        private static String ho;
        private static String hp;
        public static final ACPType[] VALUES;
        private static final /* synthetic */ ACPType[] a;

        public static ACPType[] values() {
            return (ACPType[])a.clone();
        }

        public static ACPType valueOf(String string) {
            return Enum.valueOf(ACPType.class, string);
        }

        private ACPType(String string2) {
            this.hn = string2;
        }

        public String getCfgName() {
            return this.hn;
        }

        public abstract boolean isEnabled();

        public boolean isEnabled(Player player) {
            if (Config.SERVICES_HPACP_AVAILABLE_ONLY_PREMIUM && !player.hasBonus()) {
                return false;
            }
            return this.isEnabled() && player.getVarB(this.name() + ho, Config.ENABLE_ACP_ON_CHARACTER_CREATE);
        }

        public int getActPercent(Player player) {
            int n = player.getVarInt(this.name() + hp, this.getActDefPercent());
            return Math.min(Math.max(this.getActMinPercent(), n), this.getActMaxPercent());
        }

        public int setActPercent(Player player, int n) {
            n = Math.min(Math.max(this.getActMinPercent(), n), this.getActMaxPercent());
            player.setVar(this.name() + hp, n, -1L);
            return n;
        }

        public void setEnabled(Player player, boolean bl) {
            if (bl) {
                player.setVar(this.name() + ho, "true", -1L);
                return;
            }
            player.unsetVar(this.name() + ho);
            player.unsetVar(this.name() + hp);
        }

        public abstract void apply(Player var1);

        public abstract void remove(Player var1);

        public abstract int[] getPotionsItemIds();

        protected abstract int getActMinPercent();

        protected abstract int getActMaxPercent();

        protected abstract int getActDefPercent();

        private static /* synthetic */ ACPType[] a() {
            return new ACPType[]{HPACP, CPACP, MPACP};
        }

        static {
            a = ACPType.a();
            ho = "Enabled";
            hp = "ActPercent";
            VALUES = ACPType.values();
        }
    }

    private static final class MPACPHelper
    extends ACPHelper
    implements OnCurrentMpReduceListener {
        protected MPACPHelper(Player player) {
            super(player, ACPType.MPACP);
        }

        @Override
        protected boolean canUse(Player player) {
            if (super.canUse(player)) {
                double d = player.calcStat(Stats.MP_LIMIT, null, null) * (double)player.getMaxMp() / 100.0 * ((double)this.getType().getActPercent(player) / 100.0);
                return player.getCurrentMp() < d;
            }
            return false;
        }

        public void onCurrentMpReduce(Creature creature, double d, Creature creature2) {
            Player player = creature.getPlayer();
            if (player == null) {
                creature.removeListener((Listener)this);
                return;
            }
            if (!this.getType().isEnabled(player)) {
                creature.removeListener((Listener)this);
            }
            this.act(player);
        }
    }

    private static final class CPACPHelper
    extends ACPHelper
    implements OnCurrentHpDamageListener {
        protected CPACPHelper(Player player) {
            super(player, ACPType.CPACP);
        }

        @Override
        protected boolean canUse(Player player) {
            if (super.canUse(player)) {
                double d = player.calcStat(Stats.CP_LIMIT, null, null) * (double)player.getMaxCp() / 100.0 * ((double)this.getType().getActPercent(player) / 100.0);
                return player.getCurrentCp() < d;
            }
            return false;
        }

        public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
            Player player = creature.getPlayer();
            if (player == null) {
                creature.removeListener((Listener)this);
                return;
            }
            if (!this.getType().isEnabled(player)) {
                creature.removeListener((Listener)this);
            }
            this.act(player);
        }
    }

    private static final class HPACPHelper
    extends ACPHelper
    implements OnCurrentHpDamageListener {
        protected HPACPHelper(Player player) {
            super(player, ACPType.HPACP);
        }

        @Override
        protected boolean canUse(Player player) {
            if (super.canUse(player)) {
                double d = player.calcStat(Stats.HP_LIMIT, null, null) * (double)player.getMaxHp() / 100.0 * ((double)this.getType().getActPercent(player) / 100.0);
                return player.getCurrentHp() < d;
            }
            return false;
        }

        public void onCurrentHpDamage(Creature creature, double d, Creature creature2, Skill skill) {
            Player player = creature.getPlayer();
            if (player == null) {
                creature.removeListener((Listener)this);
                return;
            }
            if (!this.getType().isEnabled(player)) {
                creature.removeListener((Listener)this);
            }
            this.act(player);
        }
    }

    private static abstract class ACPHelper
    implements Runnable,
    PlayerListener {
        private final HardReference<Player> ad;
        private final AtomicReference<ACPHelperState> d;
        private final ACPType a;

        protected ACPHelper(Player player, ACPType aCPType) {
            this.a = aCPType;
            this.ad = player.getRef();
            this.d = new AtomicReference<ACPHelperState>(ACPHelperState.IDLE);
        }

        protected Player getPlayer() {
            return (Player)this.ad.get();
        }

        public ACPType getType() {
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
}
