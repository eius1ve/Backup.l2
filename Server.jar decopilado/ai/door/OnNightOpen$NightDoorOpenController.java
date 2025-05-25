/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.gameserver.listener.game.OnDayNightChangeListener
 *  l2.gameserver.model.instances.DoorInstance
 */
package ai.door;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.game.OnDayNightChangeListener;
import l2.gameserver.model.instances.DoorInstance;

private static class OnNightOpen.NightDoorOpenController
implements OnDayNightChangeListener {
    private final HardReference<DoorInstance> g;

    private OnNightOpen.NightDoorOpenController(DoorInstance doorInstance) {
        this.g = doorInstance.getRef();
    }

    public void onDay() {
    }

    public void onNight() {
        DoorInstance doorInstance = (DoorInstance)this.g.get();
        if (doorInstance != null) {
            doorInstance.openMe();
        } else {
            _log.warn("Zaken door is null");
        }
    }
}
