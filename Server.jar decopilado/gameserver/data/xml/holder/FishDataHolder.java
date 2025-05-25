/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.FishGroup;
import l2.gameserver.templates.item.support.FishTemplate;
import l2.gameserver.templates.item.support.LureTemplate;
import l2.gameserver.templates.item.support.LureType;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

public class FishDataHolder
extends AbstractHolder {
    private static final FishDataHolder a = new FishDataHolder();
    private List<FishTemplate> ad = new ArrayList<FishTemplate>();
    private IntObjectMap<LureTemplate> b = new HashIntObjectMap();
    private IntObjectMap<Map<LureType, Map<FishGroup, Integer>>> c = new HashIntObjectMap();

    public static FishDataHolder getInstance() {
        return a;
    }

    public void addFish(FishTemplate fishTemplate) {
        this.ad.add(fishTemplate);
    }

    public void addLure(LureTemplate lureTemplate) {
        this.b.put(lureTemplate.getItemId(), (Object)lureTemplate);
    }

    public void addDistribution(int n, LureType lureType, Map<FishGroup, Integer> map) {
        HashMap<LureType, Map<FishGroup, Integer>> hashMap = (HashMap<LureType, Map<FishGroup, Integer>>)this.c.get(n);
        if (hashMap == null) {
            hashMap = new HashMap<LureType, Map<FishGroup, Integer>>();
            this.c.put(n, hashMap);
        }
        hashMap.put(lureType, map);
    }

    @Override
    public void log() {
        this.info("load " + this.ad.size() + " fish(es).");
        this.info("load " + this.b.size() + " lure(s).");
        this.info("load " + this.c.size() + " distribution(s).");
    }

    @Override
    @Deprecated
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
        this.ad.clear();
        this.b.clear();
        this.c.clear();
    }
}
