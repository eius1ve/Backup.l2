/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 */
package handler.admincommands;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.entity.events.impl.SiegeEvent;

class AdminResidence.1
extends RunnableImpl {
    final /* synthetic */ SiegeEvent val$event;

    AdminResidence.1(SiegeEvent siegeEvent) {
        this.val$event = siegeEvent;
    }

    public void runImpl() throws Exception {
        this.val$event.stopEvent();
    }
}
