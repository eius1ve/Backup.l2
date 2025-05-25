/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;

private class ParticipantPool.EntryRec {
    long[] sids;
    int average;
    long reg_time;
    int cls_id;

    public ParticipantPool.EntryRec(Player[] playerArray) {
        this.sids = new long[playerArray.length];
        this.cls_id = playerArray[0].getClassId().getId();
        int n = 0;
        for (int i = 0; i < playerArray.length; ++i) {
            this.sids[i] = playerArray[i].getStoredId();
            n += Math.max(0, NoblesController.getInstance().getPointsOf(playerArray[i].getObjectId()));
            OlyController.getInstance().incPartCount();
        }
        this.average = n / playerArray.length;
        this.reg_time = System.currentTimeMillis();
    }
}
