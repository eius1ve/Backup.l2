/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.freya;

import bosses.ValakasManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

public class ValakasMinion
extends Mystic {
    public ValakasMinion(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        for (Player player : ValakasManager.getZone().getInsidePlayers()) {
            this.notifyEvent(CtrlEvent.EVT_AGGRESSION, player, 5000);
        }
    }
}
