/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.entity.events.objects.ZoneObject
 */
package ai.residences.clanhall;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.entity.events.objects.ZoneObject;

private static class RainbowYeti.ZoneDeactive
extends RunnableImpl {
    private final ZoneObject a;

    public RainbowYeti.ZoneDeactive(ZoneObject zoneObject) {
        this.a = zoneObject;
    }

    public void runImpl() throws Exception {
        this.a.setActive(false);
    }
}
