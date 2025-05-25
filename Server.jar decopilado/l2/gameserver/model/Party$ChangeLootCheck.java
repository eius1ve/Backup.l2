/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Party;

private static class Party.ChangeLootCheck
extends RunnableImpl {
    private final Party _party;

    private Party.ChangeLootCheck(Party party) {
        this._party = party;
    }

    @Override
    public void runImpl() throws Exception {
        if (System.currentTimeMillis() > this._party.bi) {
            this._party.c(false);
        }
    }
}
