/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  gnu.trove.TIntObjectIterator
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntObjectHashMap;
import gnu.trove.TIntObjectIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.templates.npc.NpcTemplate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class NpcHolder
extends AbstractHolder {
    private static final NpcHolder a = new NpcHolder();
    private TIntObjectHashMap<NpcTemplate> j = new TIntObjectHashMap(20000);
    private TIntObjectHashMap<List<NpcTemplate>> k;
    private NpcTemplate[] a;
    private Map<String, NpcTemplate> V;

    public static NpcHolder getInstance() {
        return a;
    }

    NpcHolder() {
    }

    public void addTemplate(NpcTemplate npcTemplate) {
        this.j.put(npcTemplate.npcId, (Object)npcTemplate);
    }

    public NpcTemplate getTemplate(int n) {
        NpcTemplate npcTemplate = ArrayUtils.valid(this.a, n);
        if (npcTemplate == null) {
            this.warn("Not defined npc id : " + n + ", or out of range!", new Exception());
            return null;
        }
        return this.a[n];
    }

    public NpcTemplate getTemplateByName(String string) {
        return this.V.get(string.toLowerCase());
    }

    public List<NpcTemplate> getAllOfLevel(int n) {
        return (List)this.k.get(n);
    }

    public NpcTemplate[] getAll() {
        return (NpcTemplate[])this.j.getValues((Object[])new NpcTemplate[this.j.size()]);
    }

    private void an() {
        this.k = new TIntObjectHashMap();
        this.V = new HashMap<String, NpcTemplate>();
        int n = 0;
        for (int arrayList : this.j.keys()) {
            if (arrayList <= n) continue;
            n = arrayList;
        }
        this.a = new NpcTemplate[n + 1];
        TIntObjectIterator tIntObjectIterator = this.j.iterator();
        while (tIntObjectIterator.hasNext()) {
            NpcTemplate npcTemplate;
            tIntObjectIterator.advance();
            int n2 = tIntObjectIterator.key();
            this.a[n2] = npcTemplate = (NpcTemplate)tIntObjectIterator.value();
            ArrayList<NpcTemplate> arrayList = (ArrayList<NpcTemplate>)this.k.get(npcTemplate.level);
            if (arrayList == null) {
                arrayList = new ArrayList<NpcTemplate>();
                this.k.put(n2, arrayList);
            }
            arrayList.add(npcTemplate);
            this.V.put(npcTemplate.name.toLowerCase(), npcTemplate);
        }
    }

    @Override
    protected void process() {
        this.an();
    }

    @Override
    public int size() {
        return this.j.size();
    }

    @Override
    public void clear() {
        this.V.clear();
        this.j.clear();
    }
}
