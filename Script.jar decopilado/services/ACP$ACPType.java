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

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
static abstract class ACP.ACPType
extends Enum<ACP.ACPType> {
    public static final /* enum */ ACP.ACPType HPACP = new ACP.ACPType("HP"){

        @Override
        public boolean isEnabled() {
            return Config.SERVICES_HPACP_ENABLE;
        }

        @Override
        public void apply(Player player) {
            if (!player.getListeners().getListeners(ACP.HPACPHelper.class).isEmpty()) {
                return;
            }
            player.addListener((Listener)new ACP.HPACPHelper(player));
        }

        @Override
        public void remove(Player player) {
            player.getListeners().getListeners(ACP.HPACPHelper.class).forEach(arg_0 -> ((PlayerListenerList)player.getListeners()).remove(arg_0));
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
    public static final /* enum */ ACP.ACPType CPACP = new ACP.ACPType("CP"){

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
    };
    public static final /* enum */ ACP.ACPType MPACP = new ACP.ACPType("MP"){

        @Override
        public boolean isEnabled() {
            return Config.SERVICES_MPACP_ENABLE;
        }

        @Override
        public void apply(Player player) {
            if (!player.getListeners().getListeners(ACP.MPACPHelper.class).isEmpty()) {
                return;
            }
            player.addListener((Listener)new ACP.MPACPHelper(player));
        }

        @Override
        public void remove(Player player) {
            player.getListeners().getListeners(ACP.MPACPHelper.class).forEach(arg_0 -> ((PlayerListenerList)player.getListeners()).remove(arg_0));
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
    public static final ACP.ACPType[] VALUES;
    private static final /* synthetic */ ACP.ACPType[] a;

    public static ACP.ACPType[] values() {
        return (ACP.ACPType[])a.clone();
    }

    public static ACP.ACPType valueOf(String string) {
        return Enum.valueOf(ACP.ACPType.class, string);
    }

    private ACP.ACPType(String string2) {
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

    private static /* synthetic */ ACP.ACPType[] a() {
        return new ACP.ACPType[]{HPACP, CPACP, MPACP};
    }

    static {
        a = ACP.ACPType.a();
        ho = "Enabled";
        hp = "ActPercent";
        VALUES = ACP.ACPType.values();
    }
}
