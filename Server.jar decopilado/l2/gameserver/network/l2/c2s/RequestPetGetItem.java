/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.ItemFunctions;

public class RequestPetGetItem
extends L2GameClientPacket {
    private int fW;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        Summon summon = player.getPet();
        if (summon == null || !summon.isPet() || summon.isDead() || summon.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        ItemInstance itemInstance = (ItemInstance)player.getVisibleObject(this.fW);
        if (itemInstance == null) {
            player.sendActionFailed();
            return;
        }
        if (!ItemFunctions.checkIfCanPickup(summon, itemInstance)) {
            SystemMessage systemMessage;
            if (itemInstance.getItemId() == 57) {
                systemMessage = new SystemMessage(SystemMsg.YOU_HAVE_FAILED_TO_PICK_UP_S1_ADENA);
                systemMessage.addNumber(itemInstance.getCount());
            } else {
                systemMessage = new SystemMessage(SystemMsg.YOU_HAVE_FAILED_TO_PICK_UP_S1);
                systemMessage.addItemName(itemInstance.getItemId());
            }
            this.sendPacket((L2GameServerPacket)systemMessage);
            player.sendActionFailed();
            return;
        }
        summon.getAI().setIntention(CtrlIntention.AI_INTENTION_PICK_UP, itemInstance, null);
    }
}
