/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.KeyPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SendStatus;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolVersion
extends L2GameClientPacket {
    private static final Logger cK = LoggerFactory.getLogger(ProtocolVersion.class);
    private int qk;

    @Override
    protected void readImpl() {
        this.qk = this.readD();
    }

    @Override
    protected void runImpl() {
        if (this.qk == -2) {
            ((GameClient)this._client).closeNow(false);
            return;
        }
        if (this.qk == -3) {
            cK.info("Status request from IP : " + ((GameClient)this.getClient()).getIpAddr());
            ((GameClient)this.getClient()).close(new SendStatus());
            return;
        }
        if (this.qk < Config.MIN_PROTOCOL_REVISION || this.qk > Config.MAX_PROTOCOL_REVISION) {
            Log.network("Unknown protocol revision : " + this.qk + ", client : " + this._client);
            ((GameClient)this.getClient()).close(new KeyPacket(null));
            return;
        }
        ((GameClient)this.getClient()).setRevision(this.qk);
        this.sendPacket((L2GameServerPacket)new KeyPacket(((GameClient)this._client).enableCrypt()));
    }
}
