/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

private static class SailrenManager.CubeSpawn
extends RunnableImpl {
    private SailrenManager.CubeSpawn() {
    }

    public void runImpl() throws Exception {
        f = Functions.spawn((Location)new Location(27734, -6838, -1982, 0), (int)32107);
    }
}
