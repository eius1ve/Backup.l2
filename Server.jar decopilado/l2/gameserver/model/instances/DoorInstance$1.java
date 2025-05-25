/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.gameserver.templates.DoorTemplate;

static class DoorInstance.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$DoorTemplate$DoorType;

    static {
        $SwitchMap$l2$gameserver$templates$DoorTemplate$DoorType = new int[DoorTemplate.DoorType.values().length];
        try {
            DoorInstance.1.$SwitchMap$l2$gameserver$templates$DoorTemplate$DoorType[DoorTemplate.DoorType.WALL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            DoorInstance.1.$SwitchMap$l2$gameserver$templates$DoorTemplate$DoorType[DoorTemplate.DoorType.DOOR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
