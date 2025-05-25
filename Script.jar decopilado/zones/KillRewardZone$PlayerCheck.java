/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  org.apache.commons.lang3.StringUtils
 */
package zones;

import l2.gameserver.model.Player;
import org.apache.commons.lang3.StringUtils;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
static abstract class KillRewardZone.PlayerCheck
extends Enum<KillRewardZone.PlayerCheck> {
    public static final /* enum */ KillRewardZone.PlayerCheck hwid = new KillRewardZone.PlayerCheck(){

        @Override
        boolean check(Player player, Player player2) {
            if (!player.isConnected() || !player2.isConnected()) {
                return false;
            }
            if (player.getNetConnection().getHwid() != null) {
                return !StringUtils.equals((CharSequence)player.getNetConnection().getHwid(), (CharSequence)player2.getNetConnection().getHwid());
            }
            return true;
        }
    };
    public static final /* enum */ KillRewardZone.PlayerCheck ip = new KillRewardZone.PlayerCheck(){

        @Override
        boolean check(Player player, Player player2) {
            if (!player.isConnected() || !player2.isConnected()) {
                return false;
            }
            return !StringUtils.equals((CharSequence)player.getIP(), (CharSequence)player2.getIP());
        }
    };
    private static final /* synthetic */ KillRewardZone.PlayerCheck[] a;

    public static KillRewardZone.PlayerCheck[] values() {
        return (KillRewardZone.PlayerCheck[])a.clone();
    }

    public static KillRewardZone.PlayerCheck valueOf(String string) {
        return Enum.valueOf(KillRewardZone.PlayerCheck.class, string);
    }

    abstract boolean check(Player var1, Player var2);

    private static /* synthetic */ KillRewardZone.PlayerCheck[] a() {
        return new KillRewardZone.PlayerCheck[]{hwid, ip};
    }

    static {
        a = KillRewardZone.PlayerCheck.a();
    }
}
