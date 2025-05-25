/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.ClassId
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.List;
import l2.gameserver.model.base.ClassId;
import org.apache.commons.lang3.tuple.Pair;
import services.CommandClassMaster;

public static class CommandClassMaster.ClassMasterPath {
    private final List<ClassId> dP;
    private final ClassId b;
    private final List<Pair<Integer, Long>> dQ;
    private final List<Pair<Integer, Long>> dR;

    public CommandClassMaster.ClassMasterPath(List<ClassId> list, ClassId classId, List<Pair<Integer, Long>> list2, List<Pair<Integer, Long>> list3) {
        this.dP = list;
        this.b = classId;
        this.dQ = list2;
        this.dR = list3;
    }

    public int getMinPlayerLevel() {
        return CommandClassMaster.o(this.b.getLevel() + 1);
    }

    public List<ClassId> getAvailableClassIds() {
        return this.dP;
    }

    public ClassId getFromClassId() {
        return this.b;
    }

    public List<Pair<Integer, Long>> getPrice() {
        return this.dQ;
    }

    public List<Pair<Integer, Long>> getReward() {
        return this.dR;
    }
}
