/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.templates.npc.NpcTemplate;

public class DoormanInstance
extends npc.model.residences.DoormanInstance {
    public DoormanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    public int getOpenPriv() {
        return 2048;
    }

    @Override
    public Residence getResidence() {
        return this.getClanHall();
    }
}
