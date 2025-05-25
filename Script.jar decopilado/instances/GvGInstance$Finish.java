/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package instances;

import l2.commons.threading.RunnableImpl;

public class GvGInstance.Finish
extends RunnableImpl {
    public void runImpl() throws Exception {
        GvGInstance.this.unParalyzePlayers();
        GvGInstance.this.cleanUp();
    }
}
