/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.SevenSignsFestival;

import l2.commons.threading.RunnableImpl;

class DarknessFestival.2
extends RunnableImpl {
    DarknessFestival.2() {
    }

    @Override
    public void runImpl() throws Exception {
        DarknessFestival.this.spawnFestivalMonsters(60, 2);
        DarknessFestival.this.c("Next wave arrived!");
        DarknessFestival.this.ai();
    }
}
