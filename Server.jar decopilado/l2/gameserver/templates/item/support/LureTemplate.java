/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import java.util.Map;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.templates.item.support.FishGroup;
import l2.gameserver.templates.item.support.LureType;

public class LureTemplate {
    private final int GP;
    private final int GQ;
    private final double aZ;
    private final double ba;
    private final LureType a;
    private final Map<FishGroup, Integer> bV;

    public LureTemplate(MultiValueSet<String> multiValueSet) {
        this.GP = multiValueSet.getInteger("item_id");
        this.GQ = multiValueSet.getInteger("length_bonus");
        this.aZ = multiValueSet.getDouble("revision_number");
        this.ba = multiValueSet.getDouble("rate_bonus");
        this.a = multiValueSet.getEnum("type", LureType.class);
        this.bV = (Map)multiValueSet.get("chances");
    }

    public int getItemId() {
        return this.GP;
    }

    public int getLengthBonus() {
        return this.GQ;
    }

    public double getRevisionNumber() {
        return this.aZ;
    }

    public double getRateBonus() {
        return this.ba;
    }

    public LureType getLureType() {
        return this.a;
    }

    public Map<FishGroup, Integer> getChances() {
        return this.bV;
    }
}
