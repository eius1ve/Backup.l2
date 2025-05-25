/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.scripts.Functions
 */
package events.TvT;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.scripts.Functions;

public class TvT.StartTask
extends RunnableImpl {
    public void runImpl() {
        if (!T) {
            return;
        }
        if (Functions.isPvPEventStarted()) {
            _log.info("TvT not started: another event is already running");
            return;
        }
        if (!Rnd.chance((int)Config.EVENT_TvTChanceToStart)) {
            _log.debug("TvT not started: chance");
            return;
        }
        for (Residence residence : ResidenceHolder.getInstance().getResidenceList(Castle.class)) {
            if (residence.getSiegeEvent() == null || !residence.getSiegeEvent().isInProgress()) continue;
            _log.debug("TvT not started: CastleSiege in progress");
            return;
        }
        TvT.this.start(new String[]{"1", "1"});
    }
}
