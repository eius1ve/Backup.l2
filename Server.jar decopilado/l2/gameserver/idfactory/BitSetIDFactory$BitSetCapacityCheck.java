/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.idfactory;

import l2.commons.threading.RunnableImpl;

public class BitSetIDFactory.BitSetCapacityCheck
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        if (BitSetIDFactory.this.reachingBitSetCapacity()) {
            BitSetIDFactory.this.increaseBitSetCapacity();
        }
    }
}
