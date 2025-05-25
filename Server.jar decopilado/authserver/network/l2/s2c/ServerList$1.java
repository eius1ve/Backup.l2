/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import java.util.Comparator;
import l2.authserver.network.l2.s2c.ServerList;

class ServerList.1
implements Comparator<ServerList.ServerData> {
    ServerList.1() {
    }

    @Override
    public int compare(ServerList.ServerData serverData, ServerList.ServerData serverData2) {
        return serverData.serverId - serverData2.serverId;
    }
}
