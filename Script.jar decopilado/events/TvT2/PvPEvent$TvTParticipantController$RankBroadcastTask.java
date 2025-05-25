/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord$PVPMatchAction
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.s2c.ExPVPMatchRecord;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

private class PvPEvent.TvTParticipantController.RankBroadcastTask
extends RunnableImpl {
    private PvPEvent.TvTParticipantController a;

    public PvPEvent.TvTParticipantController.RankBroadcastTask(PvPEvent.TvTParticipantController tvTParticipantController2) {
        this.a = tvTParticipantController2;
    }

    public void runImpl() throws Exception {
        if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION) {
            return;
        }
        ExPVPMatchRecord exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.UPDATE, TeamType.NONE, this.a.d.get(), this.a.c.get());
        this.a.a(exPVPMatchRecord);
        PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{exPVPMatchRecord});
        this.a.K = ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 20000L);
    }
}
