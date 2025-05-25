/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.threading.RunnableImpl;

class Player.3
extends RunnableImpl {
    Player.3() {
    }

    @Override
    public void runImpl() {
        if (Player.this.getPetControlItem() != null) {
            Player.this.summonPet();
        }
    }
}
