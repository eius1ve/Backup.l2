/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.quest;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.NpcInstance;

public class Quest.DeSpawnScheduleTimerTask
extends RunnableImpl {
    NpcInstance _npc = null;

    public Quest.DeSpawnScheduleTimerTask(NpcInstance npcInstance) {
        this._npc = npcInstance;
    }

    @Override
    public void runImpl() throws Exception {
        if (this._npc != null) {
            if (this._npc.getSpawn() != null) {
                this._npc.getSpawn().deleteAll();
            } else {
                this._npc.deleteMe();
            }
        }
    }
}
