/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SocialAction;

private static class SailrenManager.Social
extends RunnableImpl {
    private int bC;
    private NpcInstance _npc;

    public SailrenManager.Social(NpcInstance npcInstance, int n) {
        this._npc = npcInstance;
        this.bC = n;
    }

    public void runImpl() throws Exception {
        this._npc.broadcastPacket(new L2GameServerPacket[]{new SocialAction(this._npc.getObjectId(), this.bC)});
    }
}
