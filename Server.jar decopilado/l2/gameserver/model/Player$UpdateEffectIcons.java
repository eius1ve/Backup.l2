/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

private class Player.UpdateEffectIcons
extends RunnableImpl {
    private Player.UpdateEffectIcons() {
    }

    @Override
    public void runImpl() throws Exception {
        Player.this.updateEffectIconsImpl();
        Player.this.u = null;
    }
}
