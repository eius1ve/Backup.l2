/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord$PVPMatchCCAction
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

private class PvPEvent.DMParticipantController.RankBroadcastTask
extends RunnableImpl {
    private PvPEvent.DMParticipantController a;

    public PvPEvent.DMParticipantController.RankBroadcastTask(PvPEvent.DMParticipantController dMParticipantController2) {
        this.a = dMParticipantController2;
    }

    public void runImpl() throws Exception {
        if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION) {
            return;
        }
        ExPVPMatchCCRecord exPVPMatchCCRecord = new ExPVPMatchCCRecord(ExPVPMatchCCRecord.PVPMatchCCAction.UPDATE);
        this.a.a(exPVPMatchCCRecord);
        PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{exPVPMatchCCRecord});
        this.a.K = ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 20000L);
    }
}
