/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 */
package dressmeEngine.C;

import dressmeEngine.B0;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;

class C.1
extends RunnableImpl {
    private final /* synthetic */ Player val$Player;
    private final /* synthetic */ int val$oldvisible;
    private final /* synthetic */ ItemInstance val$Equiped_item;

    C.1(Player player, int n, ItemInstance itemInstance) {
        this.val$Player = player;
        this.val$oldvisible = n;
        this.val$Equiped_item = itemInstance;
    }

    public void runImpl() throws Exception {
        this.val$Player.unsetVar("IsTrying");
        if (this.val$oldvisible != 0) {
            this.val$Equiped_item.setVisibleItemId(this.val$oldvisible);
        }
        this.val$Player.getInventory().refreshEquip();
        B0.A(this.val$Player);
    }
}
