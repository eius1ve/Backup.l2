/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent
 *  l2.gameserver.model.entity.events.objects.CMGSiegeClanObject
 *  l2.gameserver.model.entity.events.objects.SpawnExObject
 *  l2.gameserver.model.entity.events.objects.ZoneObject
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.NpcUtils
 */
package ai.residences.clanhall;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent;
import l2.gameserver.model.entity.events.objects.CMGSiegeClanObject;
import l2.gameserver.model.entity.events.objects.SpawnExObject;
import l2.gameserver.model.entity.events.objects.ZoneObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.NpcUtils;
import npc.model.residences.clanhall.RainbowGourdInstance;
import npc.model.residences.clanhall.RainbowYetiInstance;

public class RainbowYeti
extends CharacterAI {
    public RainbowYeti(NpcInstance npcInstance) {
        super((Creature)npcInstance);
    }

    public void onEvtSeeSpell(Skill skill, Creature creature) {
        RainbowYetiInstance rainbowYetiInstance = (RainbowYetiInstance)this.getActor();
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)rainbowYetiInstance.getEvent(ClanHallMiniGameEvent.class);
        if (clanHallMiniGameEvent == null) {
            return;
        }
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        CMGSiegeClanObject cMGSiegeClanObject = null;
        List list = clanHallMiniGameEvent.getObjects("attackers");
        for (CMGSiegeClanObject cMGSiegeClanObject2 : list) {
            if (!cMGSiegeClanObject2.isParticle(player)) continue;
            cMGSiegeClanObject = cMGSiegeClanObject2;
        }
        if (cMGSiegeClanObject == null) {
            return;
        }
        int n = list.indexOf(cMGSiegeClanObject);
        int n2 = Integer.MIN_VALUE;
        RainbowGourdInstance rainbowGourdInstance = null;
        RainbowGourdInstance rainbowGourdInstance2 = null;
        switch (skill.getId()) {
            case 2240: {
                if (Rnd.chance((int)90)) {
                    rainbowGourdInstance = this.a(n);
                    if (rainbowGourdInstance == null) {
                        return;
                    }
                    rainbowGourdInstance.doDecrease((Creature)player);
                    break;
                }
                rainbowYetiInstance.addMob((GameObject)NpcUtils.spawnSingle((int)35592, (int)(rainbowYetiInstance.getX() + 10), (int)(rainbowYetiInstance.getY() + 10), (int)rainbowYetiInstance.getZ(), (long)0L));
                break;
            }
            case 2241: {
                n2 = this.a(list.size(), n);
                if (n2 == Integer.MIN_VALUE) {
                    return;
                }
                rainbowGourdInstance2 = this.a(n2);
                if (rainbowGourdInstance2 == null) {
                    return;
                }
                rainbowGourdInstance2.doHeal();
                break;
            }
            case 2242: {
                n2 = this.a(list.size(), n);
                if (n2 == Integer.MIN_VALUE) {
                    return;
                }
                rainbowGourdInstance = this.a(n);
                rainbowGourdInstance2 = this.a(n2);
                if (rainbowGourdInstance2 == null || rainbowGourdInstance == null) {
                    return;
                }
                rainbowGourdInstance.doSwitch(rainbowGourdInstance2);
                break;
            }
            case 2243: {
                n2 = this.a(list.size(), n);
                if (n2 == Integer.MIN_VALUE) {
                    return;
                }
                ZoneObject zoneObject = (ZoneObject)clanHallMiniGameEvent.getFirstObject("zone_" + n2);
                if (zoneObject == null) {
                    return;
                }
                zoneObject.setActive(true);
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new ZoneDeactive(zoneObject)), 60000L);
            }
        }
    }

    private RainbowGourdInstance a(int n) {
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)this.getActor().getEvent(ClanHallMiniGameEvent.class);
        SpawnExObject spawnExObject = (SpawnExObject)clanHallMiniGameEvent.getFirstObject("arena_" + n);
        return (RainbowGourdInstance)((Spawner)spawnExObject.getSpawns().get(1)).getFirstSpawned();
    }

    private int a(int n, int n2) {
        int n3 = Integer.MIN_VALUE;
        for (int i = 0; i < 127 && (n3 = Rnd.get((int)n)) == n2; ++i) {
        }
        return n3;
    }

    private static class ZoneDeactive
    extends RunnableImpl {
        private final ZoneObject a;

        public ZoneDeactive(ZoneObject zoneObject) {
            this.a = zoneObject;
        }

        public void runImpl() throws Exception {
            this.a.setActive(false);
        }
    }
}
