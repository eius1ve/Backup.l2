/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.util.TreeMap;
import services.ItemBroker;

public class ItemBroker.NpcInfo {
    public long lastUpdate;
    public TreeMap<String, TreeMap<Long, ItemBroker.Item>> bestSellItems;
    public TreeMap<String, TreeMap<Long, ItemBroker.Item>> bestBuyItems;
    public TreeMap<String, TreeMap<Long, ItemBroker.Item>> bestCraftItems;
}
