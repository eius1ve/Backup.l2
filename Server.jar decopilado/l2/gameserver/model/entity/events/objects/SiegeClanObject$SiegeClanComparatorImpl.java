/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import java.util.Comparator;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;

public static class SiegeClanObject.SiegeClanComparatorImpl
implements Comparator<SiegeClanObject> {
    private static final SiegeClanObject.SiegeClanComparatorImpl a = new SiegeClanObject.SiegeClanComparatorImpl();

    public static SiegeClanObject.SiegeClanComparatorImpl getInstance() {
        return a;
    }

    @Override
    public int compare(SiegeClanObject siegeClanObject, SiegeClanObject siegeClanObject2) {
        return siegeClanObject2.getParam() < siegeClanObject.getParam() ? -1 : (siegeClanObject2.getParam() == siegeClanObject.getParam() ? 0 : 1);
    }
}
