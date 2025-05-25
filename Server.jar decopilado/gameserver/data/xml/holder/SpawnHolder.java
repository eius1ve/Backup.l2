/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.spawn.SpawnTemplate;

public final class SpawnHolder
extends AbstractHolder {
    private static final SpawnHolder a = new SpawnHolder();
    private Map<String, List<SpawnTemplate>> ac = new HashMap<String, List<SpawnTemplate>>();

    public static SpawnHolder getInstance() {
        return a;
    }

    public void addSpawn(String string, SpawnTemplate spawnTemplate) {
        List<SpawnTemplate> list = this.ac.get(string);
        if (list == null) {
            list = new ArrayList<SpawnTemplate>();
            this.ac.put(string, list);
        }
        list.add(spawnTemplate);
    }

    public List<SpawnTemplate> getSpawn(String string) {
        List<SpawnTemplate> list = this.ac.get(string);
        return list == null ? Collections.emptyList() : list;
    }

    @Override
    public int size() {
        int n = 0;
        for (List<SpawnTemplate> list : this.ac.values()) {
            n += list.size();
        }
        return n;
    }

    @Override
    public void clear() {
        this.ac.clear();
    }

    public Map<String, List<SpawnTemplate>> getSpawns() {
        return this.ac;
    }
}
