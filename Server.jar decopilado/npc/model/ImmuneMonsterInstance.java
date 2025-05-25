/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class ImmuneMonsterInstance
extends MonsterInstance {
    public ImmuneMonsterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public boolean isFearImmune() {
        return true;
    }

    public boolean isParalyzeImmune() {
        return true;
    }

    public boolean isLethalImmune() {
        return true;
    }
}
