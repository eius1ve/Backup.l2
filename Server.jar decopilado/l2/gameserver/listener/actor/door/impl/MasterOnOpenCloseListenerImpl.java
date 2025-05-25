/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.door.impl;

import l2.gameserver.listener.actor.door.OnOpenCloseListener;
import l2.gameserver.model.instances.DoorInstance;

public class MasterOnOpenCloseListenerImpl
implements OnOpenCloseListener {
    private DoorInstance b;

    public MasterOnOpenCloseListenerImpl(DoorInstance doorInstance) {
        this.b = doorInstance;
    }

    @Override
    public void onOpen(DoorInstance doorInstance) {
        this.b.openMe();
    }

    @Override
    public void onClose(DoorInstance doorInstance) {
        this.b.closeMe();
    }
}
