/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class EventReflectionMobInstance
extends MonsterInstance {
    public EventReflectionMobInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onDeath(Creature creature) {
        super.onDeath(creature);
        if (this.getReflection() == creature.getReflection() && this.getReflection() != ReflectionManager.DEFAULT) {
            switch (this.getNpcId()) {
                case 25657: {
                    DoorInstance doorInstance = this.getReflection().getDoor(25150002);
                    if (doorInstance == null) break;
                    doorInstance.openMe();
                    break;
                }
                case 25658: {
                    DoorInstance doorInstance = this.getReflection().getDoor(25150003);
                    if (doorInstance == null) break;
                    doorInstance.openMe();
                }
            }
        }
    }
}
