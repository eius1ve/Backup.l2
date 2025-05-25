/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

private class Summon.UpdateEffectIcons
extends RunnableImpl {
    private Summon.UpdateEffectIcons() {
    }

    @Override
    public void runImpl() throws Exception {
        Summon.this.updateEffectIconsImpl();
        Summon.this.u = null;
    }
}
