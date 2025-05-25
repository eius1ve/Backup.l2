/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.listener.inventory.OnDisplayListener
 *  l2.gameserver.listener.inventory.OnEquipListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.listener.inventory.OnDisplayListener;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemFakeAppearance
implements OnPlayerEnterListener,
ScriptFile {
    private static final Logger dX = LoggerFactory.getLogger(ItemFakeAppearance.class);
    private static final ItemFakeAppearance a = new ItemFakeAppearance();
    private static final File k = new File(Config.DATAPACK_ROOT, "data/item_fake_appearance.xml");
    private static Map<Integer, Map<Integer, Integer>> cp = new HashMap<Integer, Map<Integer, Integer>>();

    private Pair<Boolean, Map<Integer, Map<Integer, Integer>>> b() {
        try {
            SAXReader sAXReader = new SAXReader(true);
            Document document = sAXReader.read(k);
            Element element = document.getRootElement();
            if (!"list".equalsIgnoreCase(element.getName())) {
                throw new RuntimeException();
            }
            if (!Boolean.parseBoolean(element.attributeValue("enabled", "false"))) {
                return Pair.of((Object)Boolean.FALSE, Collections.emptyMap());
            }
            HashMap hashMap = new HashMap();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!"item".equalsIgnoreCase(element2.getName())) continue;
                int n = Integer.parseInt(element2.attributeValue("itemId"));
                HashMap<Integer, Integer> hashMap2 = new HashMap<Integer, Integer>();
                Iterator iterator2 = element2.elementIterator();
                while (iterator2.hasNext()) {
                    Element element3 = (Element)iterator2.next();
                    if (!"display".equalsIgnoreCase(element3.getName())) continue;
                    int n2 = Integer.parseInt(element3.attributeValue("itemId"));
                    ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n2);
                    int n3 = ItemFunctions.getPaperdollIndex((long)itemTemplate.getBodyPart());
                    hashMap2.put(n3, itemTemplate.getItemId());
                }
                hashMap.put(n, hashMap2);
            }
            return Pair.of((Object)Boolean.TRUE, hashMap);
        }
        catch (Exception exception) {
            dX.error(exception.getMessage(), (Throwable)exception);
            return Pair.of((Object)Boolean.FALSE, Collections.emptyMap());
        }
    }

    public void onLoad() {
        Pair<Boolean, Map<Integer, Map<Integer, Integer>>> pair = a.b();
        if (!((Boolean)pair.getLeft()).booleanValue()) {
            cp.clear();
            dX.info("ItemFakeAppearance: Disabled.");
            return;
        }
        if (((Map)pair.getRight()).isEmpty()) {
            cp.clear();
            dX.info("ItemFakeAppearance: No item(s). Disabled.");
            return;
        }
        cp = (Map)pair.getRight();
        dX.info("ItemFakeAppearance: Loaded " + cp.size() + " item(s) appearance.");
        PlayerListenerList.addGlobal((Listener)a);
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        PlayerListenerList.removeGlobal((Listener)a);
    }

    public void onPlayerEnter(Player player) {
        if (cp.isEmpty()) {
            return;
        }
        player.getInventory().addListener((OnEquipListener)new ItemFakeAppearanceEquipListener());
        for (ItemInstance itemInstance : player.getInventory().getPaperdollItems()) {
            if (itemInstance == null || !cp.containsKey(itemInstance.getItemId()) || !this.c(player, itemInstance.getItemId())) continue;
            return;
        }
    }

    private boolean c(Player player, int n) {
        Map<Integer, Integer> map = cp.get(n);
        if (map == null || map.isEmpty()) {
            return false;
        }
        player.getInventory().setOnDisplayListener((OnDisplayListener)new ItemFakeAppearanceDisplayListener(map));
        return true;
    }

    private boolean A(Player player) {
        if (player.getInventory().getOnDisplayListener() == null) {
            return false;
        }
        player.getInventory().setOnDisplayListener(null);
        return true;
    }

    private static final class ItemFakeAppearanceEquipListener
    implements OnEquipListener {
        private ItemFakeAppearanceEquipListener() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
            Player player = playable.getPlayer();
            if (itemInstance == null || player == null) {
                return;
            }
            int n2 = itemInstance.getItemId();
            PcInventory pcInventory = player.getInventory();
            Map<Integer, Integer> map = cp.get(n2);
            if (map == null || map.isEmpty()) {
                return;
            }
            pcInventory.writeLock();
            LinkedHashMap<Integer, ItemInstance> linkedHashMap = new LinkedHashMap<Integer, ItemInstance>();
            try {
                for (Integer n3 : map.keySet()) {
                    ItemInstance itemInstance2 = pcInventory.getPaperdollItem(n3.intValue());
                    if (itemInstance2 == null || itemInstance2 == itemInstance) continue;
                    linkedHashMap.put(n3, itemInstance2);
                }
                for (Integer n3 : linkedHashMap.values()) {
                    pcInventory.unEquipItem((ItemInstance)n3);
                }
                a.c(player, n2);
                player.sendUserInfo(true);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                for (ItemInstance itemInstance3 : linkedHashMap.values()) {
                    pcInventory.equipItem(itemInstance3);
                }
                pcInventory.writeUnlock();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
            Player player = playable.getPlayer();
            if (itemInstance == null || player == null) {
                return;
            }
            int n2 = itemInstance.getItemId();
            PcInventory pcInventory = player.getInventory();
            Map<Integer, Integer> map = cp.get(n2);
            if (map == null || map.isEmpty()) {
                return;
            }
            pcInventory.writeLock();
            LinkedHashMap<Integer, ItemInstance> linkedHashMap = new LinkedHashMap<Integer, ItemInstance>();
            try {
                for (Integer n3 : map.keySet()) {
                    ItemInstance itemInstance2 = pcInventory.getPaperdollItem(n3.intValue());
                    if (itemInstance2 == null || itemInstance2 == itemInstance) continue;
                    linkedHashMap.put(n3, itemInstance2);
                }
                for (Integer n3 : linkedHashMap.values()) {
                    pcInventory.unEquipItem((ItemInstance)n3);
                }
                a.A(playable.getPlayer());
                player.sendUserInfo(true);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                for (ItemInstance itemInstance3 : linkedHashMap.values()) {
                    pcInventory.equipItem(itemInstance3);
                }
                pcInventory.writeUnlock();
            }
        }
    }

    private static class ItemFakeAppearanceDisplayListener
    implements OnDisplayListener {
        private final Map<Integer, Integer> cq;

        private ItemFakeAppearanceDisplayListener(Map<Integer, Integer> map) {
            this.cq = map;
        }

        public Integer onDisplay(int n, ItemInstance itemInstance, Playable playable) {
            return this.cq.get(n);
        }
    }
}
