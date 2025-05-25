/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import l2.commons.threading.RunnableImpl;

private class CastleSiegeEvent.NextSiegeDateSet
extends RunnableImpl {
    private CastleSiegeEvent.NextSiegeDateSet() {
    }

    @Override
    public void runImpl() throws Exception {
        CastleSiegeEvent.this.bA();
    }
}
