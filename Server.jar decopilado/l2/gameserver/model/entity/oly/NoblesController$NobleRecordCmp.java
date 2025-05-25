/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import java.util.Comparator;
import l2.gameserver.model.entity.oly.NoblesController;

private static class NoblesController.NobleRecordCmp
implements Comparator<NoblesController.NobleRecord> {
    private NoblesController.NobleRecordCmp() {
    }

    @Override
    public int compare(NoblesController.NobleRecord nobleRecord, NoblesController.NobleRecord nobleRecord2) {
        if (nobleRecord == null && nobleRecord2 == null) {
            return 0;
        }
        if (nobleRecord == null && nobleRecord2 != null) {
            return 1;
        }
        if (nobleRecord2 == null && nobleRecord != null) {
            return -1;
        }
        int n = nobleRecord2.points_pre_past - nobleRecord.points_pre_past;
        if (n != 0) {
            return n;
        }
        return nobleRecord2.comp_done_past - nobleRecord.comp_done_past;
    }
}
