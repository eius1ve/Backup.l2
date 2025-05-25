/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.gameserver.listener.game.OnStartListener;

private class SevenSigns.OnStartListenerImpl
implements OnStartListener {
    private SevenSigns.OnStartListenerImpl() {
    }

    @Override
    public void onStart() {
        SevenSigns.this.getListenerEngine().onPeriodChange();
    }
}
