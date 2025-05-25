/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.instancemanager;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.StringUtils;

public class BypassManager {
    public static final String SIMPLE_DIARY = "_diary";
    public static final String SIMPLE_MATCH = "_match";
    public static final String SIMPLE_MANOR_MENU_SELECT = "manor_menu_select";
    public static final String SIMPLE_LANG_CHANGE_RU = "bypass -h npc_%objectId%_lang ru";
    public static final String SIMPLE_LANG_CHANGE_EN = "bypass -h npc_%objectId%_lang en";
    public static final String SIMPLE_OLYMPIAD = "_olympiad";
    public static final List<String> SIMPLE_BEGINNINGS = Config.ALT_SIMPLE_BEGINNINGS;
    public static final List<String> SIMPLE_BBS_BEGINNINGS = Config.ALT_SIMPLE_BBS_BEGINNINGS;

    private static boolean a(String string, boolean bl) {
        List<String> list = bl ? SIMPLE_BBS_BEGINNINGS : SIMPLE_BEGINNINGS;
        for (String string2 : list) {
            if (!string.startsWith(string2)) continue;
            return true;
        }
        return false;
    }

    public static BypassType getBypassType(String string, Player player) {
        switch (string.charAt(0)) {
            case '0': {
                return BypassType.ENCODED;
            }
            case '1': {
                return BypassType.ENCODED_BBS;
            }
        }
        if (BypassManager.a(string, false)) {
            return BypassType.SIMPLE;
        }
        if (BypassManager.a(string, true) && CommunityBoardManager.getInstance().getCommunityHandler(string, player) != null) {
            return BypassType.SIMPLE_BBS;
        }
        return BypassType.SIMPLE_DIRECT;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String encode(String string, List<String> list, boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] cArray = string.toCharArray();
        int n = 0;
        int n2 = 0;
        while (n2 + 7 < cArray.length) {
            int n3;
            int n4 = 0;
            int n5 = 0;
            if (cArray[n2] == '\"' && Character.toLowerCase(cArray[n2 + 1]) == 'b' && Character.toLowerCase(cArray[n2 + 2]) == 'y' && Character.toLowerCase(cArray[n2 + 3]) == 'p' && Character.toLowerCase(cArray[n2 + 4]) == 'a' && Character.toLowerCase(cArray[n2 + 5]) == 's' && Character.toLowerCase(cArray[n2 + 6]) == 's' && Character.isWhitespace(cArray[n2 + 7])) {
                n3 = 8;
                while (n3 + n2 < cArray.length && cArray[n3 + n2] != '\"') {
                    ++n3;
                }
                if (n3 + n2 == cArray.length) {
                    n4 = 0;
                    n5 = 0;
                } else {
                    n4 = n2 + 1;
                    n5 = n3 - 1;
                }
            }
            if (n5 > 0) {
                boolean bl2;
                String string2;
                boolean bl3;
                for (n3 = 7; n3 < n5 && Character.isWhitespace(cArray[n4 + n3]); ++n3) {
                }
                boolean bl4 = bl3 = cArray[n4 + n3] == '-' && (cArray[n4 + n3 + 1] == 'h' || cArray[n4 + n3 + 1] == 'H');
                if (bl3) {
                    n3 += 2;
                    while (n3 < n5 && (cArray[n4 + n3] == ' ' || cArray[n4 + n3] == '\t')) {
                        ++n3;
                    }
                }
                String string3 = string2 = new String(cArray, n4 + n3, n5 - n3);
                String string4 = "";
                int n6 = string2.indexOf(" $");
                boolean bl5 = bl2 = n6 >= 0;
                if (bl2) {
                    string3 = string2.substring(0, n6);
                    string4 = string2.substring(n6);
                }
                stringBuffer.append(cArray, n, n4 - n);
                n = n4 + n5;
                stringBuffer.append("bypass ");
                if (bl3) {
                    stringBuffer.append("-h ");
                }
                stringBuffer.append(bl ? (char)'1' : '0');
                List<String> list2 = list;
                synchronized (list2) {
                    stringBuffer.append(Integer.toHexString(list.size()));
                    stringBuffer.append(string4);
                    list.add(string3);
                }
            }
            ++n2;
        }
        stringBuffer.append(cArray, n, cArray.length - n);
        return stringBuffer.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DecodedBypass decode(String string, List<String> list, boolean bl, GameClient gameClient) {
        List<String> list2 = list;
        synchronized (list2) {
            String string2;
            String[] stringArray = StringUtils.split((String)string, (char)' ');
            int n = Integer.parseInt(stringArray[0].substring(1), 16);
            try {
                string2 = list.get(n);
            }
            catch (Exception exception) {
                string2 = null;
            }
            if (string2 == null) {
                Log.add("Can't decode bypass (bypass not exists): " + (bl ? "[bbs] " : "") + string + " / Client: " + gameClient.toString() + " / Npc: " + (gameClient.getActiveChar() == null || gameClient.getActiveChar().getLastNpc() == null ? "null" : gameClient.getActiveChar().getLastNpc().getName()), "debug_bypass");
                return null;
            }
            DecodedBypass decodedBypass = new DecodedBypass(string2, bl, !bl ? BypassType.ENCODED : BypassType.ENCODED_BBS);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < stringArray.length; ++i) {
                stringBuilder.append(' ').append(stringArray[i]);
            }
            decodedBypass.bypass = decodedBypass.bypass + stringBuilder.toString();
            decodedBypass.trim();
            return decodedBypass;
        }
    }

    public static final class BypassType
    extends Enum<BypassType> {
        public static final /* enum */ BypassType ENCODED = new BypassType();
        public static final /* enum */ BypassType ENCODED_BBS = new BypassType();
        public static final /* enum */ BypassType SIMPLE = new BypassType();
        public static final /* enum */ BypassType SIMPLE_BBS = new BypassType();
        public static final /* enum */ BypassType SIMPLE_DIRECT = new BypassType();
        private static final /* synthetic */ BypassType[] a;

        public static BypassType[] values() {
            return (BypassType[])a.clone();
        }

        public static BypassType valueOf(String string) {
            return Enum.valueOf(BypassType.class, string);
        }

        private static /* synthetic */ BypassType[] a() {
            return new BypassType[]{ENCODED, ENCODED_BBS, SIMPLE, SIMPLE_BBS, SIMPLE_DIRECT};
        }

        static {
            a = BypassType.a();
        }
    }

    public static class DecodedBypass {
        public String bypass;
        public boolean bbs;
        public BypassType bypassType;

        public DecodedBypass(String string, boolean bl, BypassType bypassType) {
            this.bypass = string;
            this.bbs = bl;
            this.bypassType = bypassType;
        }

        public DecodedBypass trim() {
            this.bypass = this.bypass.trim();
            return this;
        }
    }
}
