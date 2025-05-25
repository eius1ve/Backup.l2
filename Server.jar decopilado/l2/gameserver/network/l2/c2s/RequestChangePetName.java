/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class RequestChangePetName
extends L2GameClientPacket {
    private String _name;

    @Override
    protected void readImpl() {
        this._name = this.readS();
    }

    @Override
    protected void runImpl() {
        PetInstance petInstance;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        PetInstance petInstance2 = petInstance = player.getPet() != null && player.getPet().isPet() ? (PetInstance)player.getPet() : null;
        if (petInstance == null) {
            return;
        }
        if (petInstance.isDefaultName()) {
            if (this._name.isEmpty() || this._name.length() > 8) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_PETS_NAME_CAN_BE_UP_TO_8_CHARACTERS_IN_LENGTH);
                return;
            }
            petInstance.setName(this._name);
            petInstance.broadcastCharInfo();
            petInstance.updateControlItem();
        }
    }
}
