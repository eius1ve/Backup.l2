/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Location;

class DelusionChamber.1
extends RunnableImpl {
    DelusionChamber.1() {
    }

    @Override
    public void runImpl() throws Exception {
        if (DelusionChamber.this.getParty() != null && !DelusionChamber.this.getParty().getPartyMembers().isEmpty()) {
            for (Player player : DelusionChamber.this.getParty().getPartyMembers()) {
                String string;
                if (player.getReflection() != DelusionChamber.this || (string = player.getVar("backCoords")) == null || string.equals("")) continue;
                player.teleToLocation(Location.parseLoc(string), ReflectionManager.DEFAULT);
                player.unsetVar("backCoords");
            }
        }
        DelusionChamber.this.collapse();
    }
}
