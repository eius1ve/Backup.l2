/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.DoorAI
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.listener.actor.player.OnAnswerListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ConfirmDlg
 */
package ai.door;

import l2.gameserver.ai.DoorAI;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ConfirmDlg;

public class ResidenceDoor
extends DoorAI {
    public ResidenceDoor(DoorInstance doorInstance) {
        super(doorInstance);
    }

    public void onEvtTwiceClick(final Player player) {
        final DoorInstance doorInstance = this.getActor();
        Residence residence = ResidenceHolder.getInstance().getResidence(doorInstance.getTemplate().getAIParams().getInteger((Object)"residence_id"));
        if (residence.getOwner() != null && player.getClan() != null && player.getClan() == residence.getOwner() && (player.getClanPrivileges() & 0x10000) == 65536) {
            SystemMsg systemMsg = doorInstance.isOpen() ? SystemMsg.WOULD_YOU_LIKE_TO_CLOSE_THE_GATE : SystemMsg.WOULD_YOU_LIKE_TO_OPEN_THE_GATE;
            player.ask(new ConfirmDlg(systemMsg, 0), new OnAnswerListener(){

                public void sayYes() {
                    if (doorInstance.isOpen()) {
                        doorInstance.closeMe(player, true);
                    } else {
                        doorInstance.openMe(player, true);
                    }
                }

                public void sayNo() {
                }
            });
        }
    }
}
