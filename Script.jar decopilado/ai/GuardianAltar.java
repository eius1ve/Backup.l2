/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package ai;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

public class GuardianAltar
extends DefaultAI {
    private static final int S = 18808;

    public GuardianAltar(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.setIsInvul(true);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null) {
            return;
        }
        Player player = creature.getPlayer();
        if (Rnd.chance((int)40) && player.getInventory().destroyItemByItemId(14848, 1L)) {
            List list = npcInstance.getAroundNpc(1500, 300);
            if (list != null && !list.isEmpty()) {
                for (NpcInstance npcInstance2 : list) {
                    if (npcInstance2.getNpcId() != 18808) continue;
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1900160", (Object[])new Object[]{player.getName()});
                    return;
                }
            }
            try {
                NpcInstance npcInstance2;
                SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(18808));
                simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)400, (int)420));
                npcInstance2 = simpleSpawner.doSpawn(true);
                if (creature.isPet() || creature.isSummon()) {
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)2, (int)100));
                }
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature.getPlayer(), (Object)Rnd.get((int)1, (int)100));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (Rnd.chance((int)5)) {
            List list = npcInstance.getAroundNpc(1000, 300);
            if (list != null && !list.isEmpty()) {
                for (NpcInstance npcInstance3 : list) {
                    if (npcInstance3.getNpcId() != 22702) continue;
                    return;
                }
            }
            for (int i = 0; i < 2; ++i) {
                try {
                    NpcInstance npcInstance3;
                    npcInstance3 = new SimpleSpawner(NpcHolder.getInstance().getTemplate(22702));
                    npcInstance3.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)150, (int)160));
                    NpcInstance npcInstance4 = npcInstance3.doSpawn(true);
                    if (creature.isPet() || creature.isSummon()) {
                        npcInstance4.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)2, (int)100));
                    }
                    npcInstance4.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature.getPlayer(), (Object)Rnd.get((int)1, (int)100));
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    protected boolean randomWalk() {
        return false;
    }
}
