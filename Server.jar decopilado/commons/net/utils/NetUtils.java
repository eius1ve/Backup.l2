/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.utils;

import l2.commons.net.utils.Net;
import l2.commons.net.utils.NetList;

public class NetUtils {
    private static final NetList b = new NetList();

    public static final boolean isInternalIP(String string) {
        return b.isInRange(string);
    }

    static {
        b.add(Net.valueOf("127.0.0.0/8"));
        b.add(Net.valueOf("10.0.0.0/8"));
        b.add(Net.valueOf("172.16.0.0/12"));
        b.add(Net.valueOf("192.168.0.0/16"));
        b.add(Net.valueOf("169.254.0.0/16"));
    }
}
