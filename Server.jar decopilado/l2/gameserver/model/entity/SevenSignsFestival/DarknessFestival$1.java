/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.SevenSignsFestival;

import l2.commons.threading.RunnableImpl;

class DarknessFestival.1
extends RunnableImpl {
    DarknessFestival.1() {
    }

    @Override
    public void runImpl() throws Exception {
        DarknessFestival.this.spawnFestivalMonsters(60, 0);
        DarknessFestival.this.c("Go!");
        DarknessFestival.this.ai();
    }
}
