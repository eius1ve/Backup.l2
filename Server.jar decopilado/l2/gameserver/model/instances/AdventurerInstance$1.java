/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.gameserver.instancemanager.RaidBossSpawnManager;

static class AdventurerInstance.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$instancemanager$RaidBossSpawnManager$Status;

    static {
        $SwitchMap$l2$gameserver$instancemanager$RaidBossSpawnManager$Status = new int[RaidBossSpawnManager.Status.values().length];
        try {
            AdventurerInstance.1.$SwitchMap$l2$gameserver$instancemanager$RaidBossSpawnManager$Status[RaidBossSpawnManager.Status.ALIVE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdventurerInstance.1.$SwitchMap$l2$gameserver$instancemanager$RaidBossSpawnManager$Status[RaidBossSpawnManager.Status.DEAD.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdventurerInstance.1.$SwitchMap$l2$gameserver$instancemanager$RaidBossSpawnManager$Status[RaidBossSpawnManager.Status.UNDEFINED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
