/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 */
package dressmeEngine.C;

import dressmeEngine.B0;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;

public class TryDressMeCustom
implements Runnable {
    private ItemInstance A;
    private Player B;

    public TryDressMeCustom(Player Player2, ItemInstance ItemInstance2) {
        this.A = ItemInstance2;
        this.B = Player2;
    }

    @Override
    public void run() {
        this.B.unsetVar("IsTrying");
        B0.A(this.B);
    }
}
