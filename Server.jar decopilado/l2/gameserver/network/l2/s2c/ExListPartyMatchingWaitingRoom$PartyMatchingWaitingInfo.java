/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.commons.lang.ArrayUtils;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;

static class ExListPartyMatchingWaitingRoom.PartyMatchingWaitingInfo {
    public final int classId;
    public final int level;
    public final int currentInstance;
    public final String name;
    public final int[] instanceReuses;

    public ExListPartyMatchingWaitingRoom.PartyMatchingWaitingInfo(Player player) {
        this.name = player.getName();
        this.classId = player.getClassId().getId();
        this.level = player.getLevel();
        Reflection reflection = player.getReflection();
        this.currentInstance = reflection == null ? 0 : reflection.getInstancedZoneId();
        this.instanceReuses = ArrayUtils.toArray(player.getInstanceReuses().keySet());
    }
}
