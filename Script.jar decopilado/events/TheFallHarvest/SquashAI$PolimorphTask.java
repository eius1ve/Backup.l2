/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 */
package events.TheFallHarvest;

import events.TheFallHarvest.SquashAI;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import npc.model.SquashInstance;

public class SquashAI.PolimorphTask
extends RunnableImpl {
    public void runImpl() throws Exception {
        SquashInstance squashInstance = SquashAI.this.getActor();
        if (squashInstance == null) {
            return;
        }
        SimpleSpawner simpleSpawner = null;
        try {
            simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(SquashAI.this._npcId));
            simpleSpawner.setLoc(squashInstance.getLoc());
            NpcInstance npcInstance = simpleSpawner.doSpawn(true);
            npcInstance.setAI((CharacterAI)new SquashAI(npcInstance));
            ((SquashInstance)npcInstance).setSpawner(squashInstance.getSpawner());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        SquashAI.this.ac = Long.MAX_VALUE;
        squashInstance.deleteMe();
    }
}
