/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import java.net.InetAddress;

private static class ServerList.ServerData {
    int serverId;
    InetAddress ip;
    int port;
    int online;
    int maxPlayers;
    boolean status;
    boolean pvp;
    boolean brackets;
    int type;
    int ageLimit;

    ServerList.ServerData(int n, InetAddress inetAddress, int n2, boolean bl, boolean bl2, int n3, int n4, int n5, boolean bl3, int n6) {
        this.serverId = n;
        this.ip = inetAddress;
        this.port = n2;
        this.pvp = bl;
        this.brackets = bl2;
        this.type = n3;
        this.online = n4;
        this.maxPlayers = n5;
        this.status = bl3;
        this.ageLimit = n6;
    }
}
