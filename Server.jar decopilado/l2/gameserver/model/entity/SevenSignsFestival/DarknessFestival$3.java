/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.SevenSignsFestival;

import l2.commons.threading.RunnableImpl;

class DarknessFestival.3
extends RunnableImpl {
    DarknessFestival.3() {
    }

    @Override
    public void runImpl() throws Exception {
        DarknessFestival.this.spawnFestivalMonsters(60, 3);
        DarknessFestival.this.c("The chests have spawned! Be quick, the festival will end soon.");
    }
}
