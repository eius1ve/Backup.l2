/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 */
package dressmeEngine.C;

import dressmeEngine.B0;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

class C.2
extends RunnableImpl {
    private final /* synthetic */ Player val$Player;

    C.2(Player player) {
        this.val$Player = player;
    }

    public void runImpl() throws Exception {
        this.val$Player.unsetVar("IsTrying");
        this.val$Player.unsetVar("tryEnchantColor");
        B0.A(this.val$Player);
        this.val$Player.sendMessage("You are no longer trying enchant item");
    }
}
