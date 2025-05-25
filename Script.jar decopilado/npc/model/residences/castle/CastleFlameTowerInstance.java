/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.entity.events.impl.CastleSiegeEvent
 *  l2.gameserver.model.entity.events.objects.CastleDamageZoneObject
 *  l2.gameserver.model.instances.residences.SiegeToggleNpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.castle;

import java.util.List;
import java.util.Set;
import l2.gameserver.model.Creature;
import l2.gameserver.model.entity.events.impl.CastleSiegeEvent;
import l2.gameserver.model.entity.events.objects.CastleDamageZoneObject;
import l2.gameserver.model.instances.residences.SiegeToggleNpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class CastleFlameTowerInstance
extends SiegeToggleNpcInstance {
    private Set<String> F;

    public CastleFlameTowerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onDeathImpl(Creature creature) {
        CastleSiegeEvent castleSiegeEvent = (CastleSiegeEvent)this.getEvent(CastleSiegeEvent.class);
        if (castleSiegeEvent == null || !castleSiegeEvent.isInProgress()) {
            return;
        }
        for (String string : this.F) {
            List list = castleSiegeEvent.getObjects(string);
            for (CastleDamageZoneObject castleDamageZoneObject : list) {
                castleDamageZoneObject.getZone().setActive(false);
            }
        }
    }

    public void setZoneList(Set<String> set) {
        this.F = set;
    }
}
