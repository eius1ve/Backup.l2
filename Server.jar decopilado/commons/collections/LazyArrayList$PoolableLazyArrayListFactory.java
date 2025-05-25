/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.pool.PoolableObjectFactory
 */
package l2.commons.collections;

import l2.commons.collections.LazyArrayList;
import org.apache.commons.pool.PoolableObjectFactory;

private static class LazyArrayList.PoolableLazyArrayListFactory
implements PoolableObjectFactory {
    private LazyArrayList.PoolableLazyArrayListFactory() {
    }

    public Object makeObject() throws Exception {
        return new LazyArrayList();
    }

    public void destroyObject(Object object) throws Exception {
        ((LazyArrayList)object).clear();
    }

    public boolean validateObject(Object object) {
        return true;
    }

    public void activateObject(Object object) throws Exception {
    }

    public void passivateObject(Object object) throws Exception {
        ((LazyArrayList)object).clear();
    }
}
