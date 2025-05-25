/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class AndreasVanHalterInstance
extends RaidBossInstance {
    private static final String gQ = "[guard_of_andreas]";
    private static final int[] bl = new int[]{19160016, 19160017};

    public AndreasVanHalterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onSpawn() {
        super.onSpawn();
        SpawnManager.getInstance().spawn(gQ);
    }

    protected void onDespawn() {
        super.onDespawn();
        SpawnManager.getInstance().despawn(gQ);
        int[] nArray = bl;
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            Integer n2 = nArray[i];
            DoorInstance doorInstance = this.getReflection().getDoor(n2.intValue());
            if (doorInstance == null) continue;
            doorInstance.openMe();
        }
    }
}
