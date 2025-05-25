/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public static class BaiumManager.MoveAtRandom
extends RunnableImpl {
    private NpcInstance _npc;
    private Location _pos;

    public BaiumManager.MoveAtRandom(NpcInstance npcInstance, Location location) {
        this._npc = npcInstance;
        this._pos = location;
    }

    public void runImpl() throws Exception {
        if (this._npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
            this._npc.moveToLocation(this._pos, 0, false);
        }
    }
}
