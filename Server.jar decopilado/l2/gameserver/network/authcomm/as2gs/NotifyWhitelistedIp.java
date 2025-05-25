/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.as2gs;

import java.util.HashSet;
import java.util.Set;
import l2.commons.net.nio.impl.SelectorThread;
import l2.gameserver.GameServer;
import l2.gameserver.network.authcomm.ReceivablePacket;
import l2.gameserver.network.authcomm.gs2as.WhitelistedOk;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.GamePacketHandler;

public class NotifyWhitelistedIp
extends ReceivablePacket {
    private final Set<String> z = new HashSet<String>();
    private final Set<String> A = new HashSet<String>();
    private int dB;

    @Override
    protected void readImpl() {
        int n;
        int n2 = this.readD();
        for (n = 0; n < n2; ++n) {
            this.z.add(this.readS());
        }
        n2 = this.readD();
        for (n = 0; n < n2; ++n) {
            this.A.add(this.readS());
        }
        this.dB = this.readD();
    }

    @Override
    protected void runImpl() {
        for (SelectorThread<GameClient> selectorThread : GameServer.getInstance().getSelectorThreads()) {
            if (!(selectorThread.getAcceptFilter() instanceof GamePacketHandler)) continue;
            GamePacketHandler gamePacketHandler = (GamePacketHandler)selectorThread.getAcceptFilter();
            for (String string : this.z) {
                gamePacketHandler.addWhitelistedIp(string);
            }
        }
        this.sendPacket(new WhitelistedOk(this.dB));
    }
}
