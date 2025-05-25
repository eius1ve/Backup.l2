/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

public static final class RaidBossSpawnManager.Status
extends Enum<RaidBossSpawnManager.Status> {
    public static final /* enum */ RaidBossSpawnManager.Status ALIVE = new RaidBossSpawnManager.Status();
    public static final /* enum */ RaidBossSpawnManager.Status DEAD = new RaidBossSpawnManager.Status();
    public static final /* enum */ RaidBossSpawnManager.Status UNDEFINED = new RaidBossSpawnManager.Status();
    private static final /* synthetic */ RaidBossSpawnManager.Status[] a;

    public static RaidBossSpawnManager.Status[] values() {
        return (RaidBossSpawnManager.Status[])a.clone();
    }

    public static RaidBossSpawnManager.Status valueOf(String string) {
        return Enum.valueOf(RaidBossSpawnManager.Status.class, string);
    }

    private static /* synthetic */ RaidBossSpawnManager.Status[] a() {
        return new RaidBossSpawnManager.Status[]{ALIVE, DEAD, UNDEFINED};
    }

    static {
        a = RaidBossSpawnManager.Status.a();
    }
}
