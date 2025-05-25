/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 */
package l2.gameserver.data.xml.holder;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.templates.StaticObjectTemplate;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

public final class StaticObjectHolder
extends AbstractHolder {
    private static final StaticObjectHolder a = new StaticObjectHolder();
    private IntObjectMap<StaticObjectTemplate> e = new HashIntObjectMap();
    private IntObjectMap<StaticObjectInstance> g = new HashIntObjectMap();

    public static StaticObjectHolder getInstance() {
        return a;
    }

    public void addTemplate(StaticObjectTemplate staticObjectTemplate) {
        this.e.put(staticObjectTemplate.getUId(), (Object)staticObjectTemplate);
    }

    public StaticObjectTemplate getTemplate(int n) {
        return (StaticObjectTemplate)this.e.get(n);
    }

    public void spawnAll() {
        for (StaticObjectTemplate staticObjectTemplate : this.e.values()) {
            if (!staticObjectTemplate.isSpawn()) continue;
            StaticObjectInstance staticObjectInstance = staticObjectTemplate.newInstance();
            this.g.put(staticObjectTemplate.getUId(), (Object)staticObjectInstance);
        }
        this.info("spawned: " + this.g.size() + " static object(s).");
    }

    public StaticObjectInstance getObject(int n) {
        return (StaticObjectInstance)this.g.get(n);
    }

    @Override
    public int size() {
        return this.e.size();
    }

    @Override
    public void clear() {
        this.e.clear();
    }
}
