/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.holder.RecipeHolder
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.base.Element
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ManufactureItem
 *  l2.gameserver.model.items.TradeItem
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.CharInfo
 *  l2.gameserver.network.l2.s2c.ExPrivateStoreSetWholeMsg
 *  l2.gameserver.network.l2.s2c.PrivateStoreMsgBuy
 *  l2.gameserver.network.l2.s2c.PrivateStoreMsgSell
 *  l2.gameserver.network.l2.s2c.RadarControl
 *  l2.gameserver.network.l2.s2c.RecipeShopMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.templates.item.ItemTemplate$ItemClass
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.Util
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.RecipeHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.CharInfo;
import l2.gameserver.network.l2.s2c.ExPrivateStoreSetWholeMsg;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgBuy;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgSell;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.RecipeShopMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

public class ItemBroker
extends Functions {
    private static final int bFF = 10;
    private static final int bFG = 9;
    private static Map<Integer, NpcInfo> co = new ConcurrentHashMap<Integer, NpcInfo>();
    public int[] RARE_ITEMS = new int[]{16205, 16206, 16207, 16208, 16209, 16210, 16211, 16212, 16213, 16214, 16215, 16216, 16217, 16218, 16219, 16220, 16304, 16321, 16338, 16355};

    private TreeMap<String, TreeMap<Long, Item>> a(int n) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return null;
        }
        this.updateInfo(player, npcInstance);
        NpcInfo npcInfo = co.get(this.getNpc().getObjectId());
        if (npcInfo == null) {
            return null;
        }
        switch (n) {
            case 1: {
                return npcInfo.bestSellItems;
            }
            case 3: {
                return npcInfo.bestBuyItems;
            }
            case 5: {
                return npcInfo.bestCraftItems;
            }
        }
        return null;
    }

    public String DialogAppend_31732(Integer n) {
        return this.getHtmlAppends(n);
    }

    public String DialogAppend_31833(Integer n) {
        return this.getHtmlAppends(n);
    }

    public String DialogAppend_31838(Integer n) {
        return this.getHtmlAppends(n);
    }

    public String DialogAppend_31829(Integer n) {
        return this.getHtmlAppends(n);
    }

    public String DialogAppend_31991(Integer n) {
        return this.getHtmlAppends(n);
    }

    public String DialogAppend_31805(Integer n) {
        return this.getHtmlAppends(n);
    }

    public String getHtmlAppends(Integer n) {
        if (!Config.ITEM_BROKER_ITEM_SEARCH) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        String string = "";
        String string2 = "";
        switch (n) {
            case 0: {
                if (this.getSelf().isLangRus()) {
                    stringBuilder.append("<br><font color=\"LEVEL\">\u041f\u043e\u0438\u0441\u043a \u0442\u043e\u0440\u0433\u043e\u0432\u0446\u0435\u0432:</font><br1>");
                    stringBuilder.append("[npc_%objectId%_Chat 11|<font color=\"FF9900\">\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u0440\u043e\u0434\u0430\u0432\u0430\u0435\u043c\u044b\u0445 \u0442\u043e\u0432\u0430\u0440\u043e\u0432</font>]<br1>");
                    stringBuilder.append("[npc_%objectId%_Chat 13|<font color=\"FF9900\">\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u043e\u043a\u0443\u043f\u0430\u0435\u043c\u044b\u0445 \u0442\u043e\u0432\u0430\u0440\u043e\u0432</font>]<br1>");
                    stringBuilder.append("[npc_%objectId%_Chat 15|<font color=\"FF9900\">\u0421\u043f\u0438\u0441\u043e\u043a \u0441\u043e\u0437\u0434\u0430\u0432\u0430\u0435\u043c\u044b\u0445 \u0442\u043e\u0432\u0430\u0440\u043e\u0432</font>]<br1>");
                    break;
                }
                stringBuilder.append("<br><font color=\"LEVEL\">Search for dealers:</font><br1>");
                stringBuilder.append("[npc_%objectId%_Chat 11|<font color=\"FF9900\">The list of goods for sale</font>]<br1>");
                stringBuilder.append("[npc_%objectId%_Chat 13|<font color=\"FF9900\">The list of goods to buy</font>]<br1>");
                stringBuilder.append("[npc_%objectId%_Chat 15|<font color=\"FF9900\">The list of goods to craft</font>]<br1>");
                break;
            }
            case 11: {
                n2 = 1;
                string = "\u043f\u0440\u043e\u0434\u0430\u0432\u0430\u0435\u043c\u044b\u0445";
                string2 = "sell";
                break;
            }
            case 13: {
                n2 = 3;
                string = "\u043f\u043e\u043a\u0443\u043f\u0430\u0435\u043c\u044b\u0445";
                string2 = "buy";
                break;
            }
            case 15: {
                n2 = 5;
                string = "\u0441\u043e\u0437\u0434\u0430\u0432\u0430\u0435\u043c\u044b\u0445";
                string2 = "craft";
                break;
            }
            case 21: 
            case 23: 
            case 25: {
                n2 = n - 20;
                if (this.getSelf().isLangRus()) {
                    stringBuilder.append("!\u0421\u043f\u0438\u0441\u043e\u043a \u0441\u043d\u0430\u0440\u044f\u0436\u0435\u043d\u0438\u044f:<br>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 1 1 0 0|<font color=\"FF9900\">\u041e\u0440\u0443\u0436\u0438\u0435</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 2 1 0 0|<font color=\"FF9900\">\u0411\u0440\u043e\u043d\u044f</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 3 1 0 0|<font color=\"FF9900\">\u0411\u0438\u0436\u0443\u0442\u0435\u0440\u0438\u044f</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 4 1 0 0|<font color=\"FF9900\">\u0423\u043a\u0440\u0430\u0448\u0435\u043d\u0438\u044f</font>]<br1>");
                    stringBuilder.append("<br>[npc_%objectId%_Chat ").append(10 + n2).append("|<font color=\"FF9900\">\u041d\u0430\u0437\u0430\u0434</font>]");
                } else {
                    stringBuilder.append("!The list of equipment:<br>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 1 1 0 0|<font color=\"FF9900\">Weapons</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 2 1 0 0|<font color=\"FF9900\">Armors</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 3 1 0 0|<font color=\"FF9900\">Jewels</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 4 1 0 0|<font color=\"FF9900\">Accessories</font>]<br1>");
                    stringBuilder.append("<br>[npc_%objectId%_Chat ").append(10 + n2).append("|<font color=\"FF9900\">Back</font>]");
                }
                return stringBuilder.toString();
            }
            case 31: 
            case 33: 
            case 35: {
                n2 = n - 30;
                if (this.getSelf().isLangRus()) {
                    stringBuilder.append("!\u0421\u043f\u0438\u0441\u043e\u043a \u0441\u043d\u0430\u0440\u044f\u0436\u0435\u043d\u0438\u044f, \u0437\u0430\u0442\u043e\u0447\u0435\u043d\u043d\u043e\u0433\u043e \u043d\u0430 +4 \u0438 \u0432\u044b\u0448\u0435:<br>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 1 1 4 0|<font color=\"FF9900\">\u041e\u0440\u0443\u0436\u0438\u0435</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 2 1 4 0|<font color=\"FF9900\">\u0411\u0440\u043e\u043d\u044f</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 3 1 4 0|<font color=\"FF9900\">\u0411\u0438\u0436\u0443\u0442\u0435\u0440\u0438\u044f</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 4 1 4 0|<font color=\"FF9900\">\u0423\u043a\u0440\u0430\u0448\u0435\u043d\u0438\u044f</font>]<br1>");
                    stringBuilder.append("<br>[npc_%objectId%_Chat ").append(10 + n2).append("|<font color=\"FF9900\">\u041d\u0430\u0437\u0430\u0434</font>]");
                } else {
                    stringBuilder.append("!The list of equipment, enchanted to +4 and more:<br>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 1 1 4 0|<font color=\"FF9900\">Weapons+</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 2 1 4 0|<font color=\"FF9900\">Armors+</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 3 1 4 0|<font color=\"FF9900\">Jewels+</font>]<br1>");
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 4 1 4 0|<font color=\"FF9900\">Accessories+</font>]<br1>");
                    stringBuilder.append("<br>[npc_%objectId%_Chat ").append(10 + n2).append("|<font color=\"FF9900\">Back</font>]");
                }
                return stringBuilder.toString();
            }
        }
        if (n2 > 0) {
            if (this.getSelf().isLangRus()) {
                stringBuilder.append("!\u0421\u043f\u0438\u0441\u043e\u043a ").append(string).append(" \u0442\u043e\u0432\u0430\u0440\u043e\u0432:<br>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 0 1 0 0|<font color=\"FF9900\">\u0412\u0435\u0441\u044c \u0441\u043f\u0438\u0441\u043e\u043a</font>]<br1>");
                stringBuilder.append("[npc_%objectId%_Chat ").append(n2 + 20).append("|<font color=\"FF9900\">\u0421\u043d\u0430\u0440\u044f\u0436\u0435\u043d\u0438\u0435</font>]<br1>");
                if (n2 == 1) {
                    stringBuilder.append("[npc_%objectId%_Chat ").append(n2 + 30).append("|<font color=\"FF9900\">\u0421\u043d\u0430\u0440\u044f\u0436\u0435\u043d\u0438\u0435 +4 \u0438 \u0432\u044b\u0448\u0435</font>]<br1>");
                }
                if (n2 != 5) {
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 0 1 0 1|<font color=\"FF9900\">\u0420\u0435\u0434\u043a\u043e\u0435 \u0441\u043d\u0430\u0440\u044f\u0436\u0435\u043d\u0438\u0435</font>]<br1>");
                }
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 5 1 0 0|<font color=\"FF9900\">\u0420\u0430\u0441\u0445\u043e\u0434\u043d\u044b\u0435 \u043c\u0430\u0442\u0435\u0440\u0438\u0430\u043b\u044b</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 6 1 0 0|<font color=\"FF9900\">\u0418\u043d\u0433\u0440\u0435\u0434\u0438\u0435\u043d\u0442\u044b</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 7 1 0 0|<font color=\"FF9900\">\u041a\u043b\u044e\u0447\u0435\u0432\u044b\u0435 \u0438\u043d\u0433\u0440\u0435\u0434\u0438\u0435\u043d\u0442\u044b</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 8 1 0 0|<font color=\"FF9900\">\u0420\u0435\u0446\u0435\u043f\u0442\u044b</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 9 1 0 0|<font color=\"FF9900\">\u041a\u043d\u0438\u0433\u0438 \u0438 \u0430\u043c\u0443\u043b\u0435\u0442\u044b</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 10 1 0 0|<font color=\"FF9900\">\u041f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0434\u043b\u044f \u0443\u043b\u0443\u0447\u0448\u0435\u043d\u0438\u044f</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 11 1 0 0|<font color=\"FF9900\">\u0420\u0430\u0437\u043d\u043e\u0435</font>]<br1>");
                if (n2 != 5) {
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 90 1 0 0|<font color=\"FF9900\">\u0421\u0442\u0430\u043d\u0434\u0430\u0440\u0442\u043d\u044b\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b</font>]<br1>");
                }
                stringBuilder.append("<edit var=\"tofind\" width=100><br1>");
                stringBuilder.append("[scripts_services.ItemBroker:find ").append(n2).append(" 1 $tofind|<font color=\"FF9900\">\u041d\u0430\u0439\u0442\u0438</font>]<br1>");
                stringBuilder.append("<br>[npc_%objectId%_Chat 0|<font color=\"FF9900\">\u041d\u0430\u0437\u0430\u0434</font>]");
            } else {
                stringBuilder.append("!The list of goods to ").append(string2).append(":<br>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 0 1 0 0|<font color=\"FF9900\">List all</font>]<br1>");
                stringBuilder.append("[npc_%objectId%_Chat ").append(n2 + 20).append("|<font color=\"FF9900\">Equipment</font>]<br1>");
                if (n2 == 1) {
                    stringBuilder.append("[npc_%objectId%_Chat ").append(n2 + 30).append("|<font color=\"FF9900\">Equipment +4 and more</font>]<br1>");
                }
                if (n2 != 5) {
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 0 1 0 1|<font color=\"FF9900\">Rare equipment</font>]<br1>");
                }
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 5 1 0 0|<font color=\"FF9900\">Consumable</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 6 1 0 0|<font color=\"FF9900\">Matherials</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 7 1 0 0|<font color=\"FF9900\">Key matherials</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 8 1 0 0|<font color=\"FF9900\">Recipies</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 9 1 0 0|<font color=\"FF9900\">Books and amulets</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 10 1 0 0|<font color=\"FF9900\">Enchant items</font>]<br1>");
                stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 11 1 0 0|<font color=\"FF9900\">Other</font>]<br1>");
                if (n2 != 5) {
                    stringBuilder.append("[scripts_services.ItemBroker:list ").append(n2).append(" 90 1 0 0|<font color=\"FF9900\">Commons</font>]<br1>");
                }
                stringBuilder.append("<edit var=\"tofind\" width=100><br1>");
                stringBuilder.append("[scripts_services.ItemBroker:find ").append(n2).append(" 1 $tofind|<font color=\"FF9900\">Find</font>]<br1>");
                stringBuilder.append("<br>[npc_%objectId%_Chat 0|<font color=\"FF9900\">Back</font>]");
            }
        }
        return stringBuilder.toString();
    }

    public void list(String[] stringArray) {
        Item item;
        Map.Entry<Long, Item> entry;
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (stringArray.length != 5) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0430\u044f \u0434\u043b\u0438\u043d\u0430 \u0434\u0430\u043d\u043d\u044b\u0445", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        try {
            n5 = Integer.valueOf(stringArray[0]);
            n4 = Integer.valueOf(stringArray[1]);
            n3 = Integer.valueOf(stringArray[2]);
            n2 = Integer.valueOf(stringArray[3]);
            n = Integer.valueOf(stringArray[4]);
        }
        catch (Exception exception) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ItemTemplate.ItemClass itemClass = n4 >= ItemTemplate.ItemClass.values().length ? null : ItemTemplate.ItemClass.values()[n4];
        TreeMap<String, TreeMap<Long, Item>> treeMap = this.a(n5);
        if (treeMap == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u0442\u0430\u043a\u043e\u0433\u043e \u0442\u0438\u043f\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(treeMap.size() * 10);
        for (TreeMap<Long, Item> treeMap2 : treeMap.values()) {
            TreeMap<Long, Item> treeMap3 = new TreeMap<Long, Item>();
            Object object = treeMap2.entrySet().iterator();
            while (object.hasNext()) {
                ItemTemplate itemTemplate;
                entry = object.next();
                item = entry.getValue();
                if (item == null || item.enchant < n2 || (itemTemplate = item.item != null ? item.item.getItem() : ItemHolder.getInstance().getTemplate(item.itemId)) == null || n > 0 && !item.rare || itemClass != null && itemClass != ItemTemplate.ItemClass.ALL && itemTemplate.getItemClass() != itemClass) continue;
                treeMap3.put(entry.getKey(), item);
            }
            if (treeMap3.isEmpty() || (object = n5 == 3 ? (Item)treeMap3.lastEntry().getValue() : (Item)treeMap3.firstEntry().getValue()) == null) continue;
            arrayList.add(object);
        }
        StringBuilder stringBuilder = new StringBuilder(200);
        stringBuilder.append("<a action=\"bypass -h npc_%objectId%_Chat 1");
        stringBuilder.append(n5);
        stringBuilder.append("\">\u00ab\u00ab</a>&nbsp;&nbsp;");
        int n6 = arrayList.size();
        n6 = n6 / 10 + (n6 % 10 > 0 ? 1 : 0);
        n6 = Math.max(1, n6);
        n3 = Math.min(n6, Math.max(1, n3));
        if (n6 > 1) {
            int n7 = Math.max(1, Math.min(n6 - 9 + 1, n3 - 4));
            if (n7 > 1) {
                this.a(stringBuilder, n5, n4, 1, n2, n, "1");
            }
            if (n3 > 11) {
                this.a(stringBuilder, n5, n4, n3 - 10, n2, n, String.valueOf(n3 - 10));
            }
            if (n3 > 1) {
                this.a(stringBuilder, n5, n4, n3 - 1, n2, n, "<");
            }
            for (int i = 0; i < 9 && n7 <= n6; ++i, ++n7) {
                if (n7 == n3) {
                    stringBuilder.append(n7).append("&nbsp;");
                    continue;
                }
                this.a(stringBuilder, n5, n4, n7, n2, n, String.valueOf(n7));
            }
            if (n3 < n6) {
                this.a(stringBuilder, n5, n4, n3 + 1, n2, n, ">");
            }
            if (n3 < n6 - 10) {
                this.a(stringBuilder, n5, n4, n3 + 10, n2, n, String.valueOf(n3 + 10));
            }
            if (n7 <= n6) {
                this.a(stringBuilder, n5, n4, n6, n2, n, String.valueOf(n6));
            }
        }
        stringBuilder.append("<table width=100%>");
        if (arrayList.size() > 0) {
            int n8 = 0;
            ListIterator listIterator = arrayList.listIterator((n3 - 1) * 10);
            while (listIterator.hasNext() && n8 < 10) {
                entry = (Item)listIterator.next();
                Object object = item = ((Item)((Object)entry)).item != null ? ((Item)((Object)entry)).item.getItem() : ItemHolder.getInstance().getTemplate(((Item)((Object)entry)).itemId);
                if (item == null) continue;
                stringBuilder.append("<tr><td>");
                stringBuilder.append(item.getIcon32());
                stringBuilder.append("</td><td><table width=100%><tr><td>[scripts_services.ItemBroker:listForItem ");
                stringBuilder.append(n5);
                stringBuilder.append(" ");
                stringBuilder.append(((Item)((Object)entry)).itemId);
                stringBuilder.append(" ");
                stringBuilder.append(n2);
                stringBuilder.append(" ");
                stringBuilder.append(n);
                stringBuilder.append(" ");
                stringBuilder.append(n4);
                stringBuilder.append(" 1 ");
                stringBuilder.append(n3);
                stringBuilder.append("|");
                stringBuilder.append(((Item)((Object)entry)).name);
                stringBuilder.append("</td></tr><tr><td>price: ");
                stringBuilder.append(Util.formatAdena((long)((Item)((Object)entry)).price));
                if (item.isStackable()) {
                    stringBuilder.append(", count: ").append(Util.formatAdena((long)((Item)((Object)entry)).count));
                }
                stringBuilder.append("</td></tr></table></td></tr>");
                ++n8;
            }
        } else if (this.getSelf().isLangRus()) {
            stringBuilder.append("<tr><td colspan=2>\u041d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.</td></tr>");
        } else {
            stringBuilder.append("<tr><td colspan=2>Nothing found.</td></tr>");
        }
        stringBuilder.append("</table><br>&nbsp;");
        ItemBroker.show((String)stringBuilder.toString(), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    private void a(StringBuilder stringBuilder, int n, int n2, int n3, int n4, int n5, String string) {
        stringBuilder.append("[scripts_services.ItemBroker:list ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(n2);
        stringBuilder.append(" ");
        stringBuilder.append(n3);
        stringBuilder.append(" ");
        stringBuilder.append(n4);
        stringBuilder.append(" ");
        stringBuilder.append(n5);
        stringBuilder.append("|");
        stringBuilder.append(string);
        stringBuilder.append("]&nbsp;");
    }

    public void listForItem(String[] stringArray) {
        NavigableMap<Long, Item> navigableMap;
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (stringArray.length < 7 || stringArray.length > 12) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0430\u044f \u0434\u043b\u0438\u043d\u0430 \u0434\u0430\u043d\u043d\u044b\u0445", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        String[] stringArray2 = null;
        try {
            n7 = Integer.valueOf(stringArray[0]);
            n6 = Integer.valueOf(stringArray[1]);
            n5 = Integer.valueOf(stringArray[2]);
            n4 = Integer.valueOf(stringArray[3]);
            n3 = Integer.valueOf(stringArray[4]);
            n2 = Integer.valueOf(stringArray[5]);
            n = Integer.valueOf(stringArray[6]);
            if (stringArray.length > 7) {
                stringArray2 = new String[stringArray.length - 7];
                System.arraycopy(stringArray, 7, stringArray2, 0, stringArray2.length);
            }
        }
        catch (Exception exception) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n6);
        if (itemTemplate == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - itemId \u043d\u0435 \u043e\u043f\u0440\u0435\u0434\u0435\u043b\u0435\u043d.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        TreeMap<String, TreeMap<Long, Item>> treeMap = this.a(n7);
        if (treeMap == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u0442\u0430\u043a\u043e\u0439 \u0442\u0438\u043f \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430 \u043e\u0442\u0441\u0443\u0442\u0441\u0442\u0432\u0443\u0435\u0442.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        TreeMap<Long, Item> treeMap2 = treeMap.get(itemTemplate.getName());
        if (treeMap2 == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u0441 \u0442\u0430\u043a\u0438\u043c \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435\u043c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(200);
        if (stringArray2 == null) {
            this.a(stringBuilder, n7, n3, n, n5, n4, "\u00ab\u00ab");
        } else {
            this.a(stringBuilder, n7, n, stringArray2, "\u00ab\u00ab");
        }
        stringBuilder.append("&nbsp;&nbsp;");
        NavigableMap<Long, Item> navigableMap2 = navigableMap = n7 == 3 ? treeMap2.descendingMap() : treeMap2;
        if (navigableMap == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ArrayList<Item> arrayList = new ArrayList<Item>(navigableMap.size());
        for (Item item : navigableMap.values()) {
            if (item == null || item.enchant < n5 || n4 > 0 && !item.rare) continue;
            arrayList.add(item);
        }
        int n8 = arrayList.size();
        n8 = n8 / 10 + (n8 % 10 > 0 ? 1 : 0);
        n8 = Math.max(1, n8);
        n2 = Math.min(n8, Math.max(1, n2));
        if (n8 > 1) {
            int n9 = Math.max(1, Math.min(n8 - 9 + 1, n2 - 4));
            if (n9 > 1) {
                this.a(stringBuilder, n7, n6, n5, n4, n3, 1, n, stringArray2, "1");
            }
            if (n2 > 11) {
                this.a(stringBuilder, n7, n6, n5, n4, n3, n2 - 10, n, stringArray2, String.valueOf(n2 - 10));
            }
            if (n2 > 1) {
                this.a(stringBuilder, n7, n6, n5, n4, n3, n2 - 1, n, stringArray2, "<");
            }
            for (int i = 0; i < 9 && n9 <= n8; ++i, ++n9) {
                if (n9 == n2) {
                    stringBuilder.append(n9).append("&nbsp;");
                    continue;
                }
                this.a(stringBuilder, n7, n6, n5, n4, n3, n9, n, stringArray2, String.valueOf(n9));
            }
            if (n2 < n8) {
                this.a(stringBuilder, n7, n6, n5, n4, n3, n2 + 1, n, stringArray2, ">");
            }
            if (n2 < n8 - 10) {
                this.a(stringBuilder, n7, n6, n5, n4, n3, n2 + 10, n, stringArray2, String.valueOf(n2 + 10));
            }
            if (n9 <= n8) {
                this.a(stringBuilder, n7, n6, n5, n4, n3, n8, n, stringArray2, String.valueOf(n8));
            }
        }
        stringBuilder.append("<table width=100%>");
        if (arrayList.size() > 0) {
            int n10 = 0;
            ListIterator listIterator = arrayList.listIterator((n2 - 1) * 10);
            while (listIterator.hasNext() && n10 < 10) {
                ItemTemplate itemTemplate2;
                Item item = (Item)listIterator.next();
                ItemTemplate itemTemplate3 = itemTemplate2 = item.item != null ? item.item.getItem() : ItemHolder.getInstance().getTemplate(item.itemId);
                if (itemTemplate2 == null) continue;
                stringBuilder.append("<tr><td>");
                stringBuilder.append(itemTemplate2.getIcon32());
                stringBuilder.append("</td><td><table width=100%><tr><td>[scripts_services.ItemBroker:path ");
                stringBuilder.append(n7);
                stringBuilder.append(" ");
                stringBuilder.append(item.itemId);
                stringBuilder.append(" ");
                stringBuilder.append(item.itemObjId);
                stringBuilder.append("|");
                stringBuilder.append(item.name);
                stringBuilder.append("</td></tr><tr><td>price: ");
                stringBuilder.append(Util.formatAdena((long)item.price));
                if (itemTemplate2.isStackable()) {
                    stringBuilder.append(", count: ").append(Util.formatAdena((long)item.count));
                }
                stringBuilder.append(", owner: ").append(item.merchantName);
                stringBuilder.append("</td></tr></table></td></tr>");
                ++n10;
            }
        } else if (this.getSelf().isLangRus()) {
            stringBuilder.append("<tr><td colspan=2>\u041d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.</td></tr>");
        } else {
            stringBuilder.append("<tr><td colspan=2>Nothing found.</td></tr>");
        }
        stringBuilder.append("</table><br>&nbsp;");
        ItemBroker.show((String)stringBuilder.toString(), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    private void a(StringBuilder stringBuilder, int n, int n2, int n3, int n4, int n5, int n6, int n7, String[] stringArray, String string) {
        stringBuilder.append("[scripts_services.ItemBroker:listForItem ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(n2);
        stringBuilder.append(" ");
        stringBuilder.append(n3);
        stringBuilder.append(" ");
        stringBuilder.append(n4);
        stringBuilder.append(" ");
        stringBuilder.append(n5);
        stringBuilder.append(" ");
        stringBuilder.append(n6);
        stringBuilder.append(" ");
        stringBuilder.append(n7);
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                stringBuilder.append(" ");
                stringBuilder.append(stringArray[i]);
            }
        }
        stringBuilder.append("|");
        stringBuilder.append(string);
        stringBuilder.append("]&nbsp;");
    }

    public void path(String[] stringArray) {
        Item item3;
        int n;
        int n2;
        int n3;
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (stringArray.length != 3) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u0430\u044f \u0434\u043b\u0438\u043d\u0430 \u0434\u0430\u043d\u043d\u044b\u0445", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        try {
            n3 = Integer.valueOf(stringArray[0]);
            n2 = Integer.valueOf(stringArray[1]);
            n = Integer.valueOf(stringArray[2]);
        }
        catch (Exception exception) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n2);
        if (itemTemplate == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - itemId \u043d\u0435 \u043e\u043f\u0440\u0435\u0434\u0435\u043b\u0435\u043d.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        TreeMap<String, TreeMap<Long, Item>> treeMap = this.a(n3);
        if (treeMap == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u0442\u0430\u043a\u043e\u0433\u043e \u0442\u0438\u043f\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        TreeMap<Long, Item> treeMap2 = treeMap.get(itemTemplate.getName());
        if (treeMap2 == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u0441 \u0442\u0430\u043a\u0438\u043c \u0438\u043c\u0435\u043d\u0435\u043c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        Item item2 = null;
        for (Item item3 : treeMap2.values()) {
            if (item3.itemObjId != n) continue;
            item2 = item3;
            break;
        }
        if (item2 == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043f\u0440\u0435\u0434\u043c\u0435\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        boolean bl = false;
        item3 = GameObjectsStorage.getAsPlayer((long)item2.merchantStoredId);
        if (item3 == null) {
            ItemBroker.show((String)"\u0422\u043e\u0440\u0433\u043e\u0432\u0435\u0446 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d, \u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u043e\u043d \u0432\u044b\u0448\u0435\u043b \u0438\u0437 \u0438\u0433\u0440\u044b.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        block1 : switch (n3) {
            case 1: {
                if (item3.getSellList() == null) break;
                for (TradeItem tradeItem : item3.getSellList()) {
                    if (tradeItem.getItemId() != item2.itemId || tradeItem.getOwnersPrice() != item2.price) continue;
                    bl = true;
                    break block1;
                }
                break;
            }
            case 3: {
                if (item3.getBuyList() == null) break;
                for (TradeItem tradeItem : item3.getBuyList()) {
                    if (tradeItem.getItemId() != item2.itemId || tradeItem.getOwnersPrice() != item2.price) continue;
                    bl = true;
                    break block1;
                }
                break;
            }
            case 5: {
                bl = true;
            }
        }
        if (!bl) {
            if (player.isLangRus()) {
                ItemBroker.show((String)"\u0412\u043d\u0438\u043c\u0430\u043d\u0438\u0435, \u0446\u0435\u043d\u0430 \u0438\u043b\u0438 \u043f\u0440\u0435\u0434\u043c\u0435\u0442 \u0438\u0437\u043c\u0435\u043d\u0438\u043b\u0438\u0441\u044c, \u0431\u0443\u0434\u044c\u0442\u0435 \u043e\u0441\u0442\u043e\u0440\u043e\u0436\u043d\u044b !", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            } else {
                ItemBroker.show((String)"Caution, price or item was changed, please be careful !", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            }
        }
        RadarControl radarControl = new RadarControl(0, 1, item2.player);
        player.sendPacket((IStaticPacket)radarControl);
        if (player.getVarB("notraders")) {
            player.sendPacket((IStaticPacket)new CharInfo((Player)item3));
            if (item3.getPrivateStoreType() == 3) {
                player.sendPacket((IStaticPacket)new PrivateStoreMsgBuy((Player)item3));
            } else if (item3.getPrivateStoreType() == 1) {
                player.sendPacket((IStaticPacket)new PrivateStoreMsgSell((Player)item3));
            } else if (item3.getPrivateStoreType() == 8) {
                player.sendPacket((IStaticPacket)new ExPrivateStoreSetWholeMsg((Player)item3));
            } else if (item3.getPrivateStoreType() == 5) {
                player.sendPacket((IStaticPacket)new RecipeShopMsg((Player)item3));
            }
        }
        player.setTarget((GameObject)item3);
    }

    public void updateInfo(Player player, NpcInstance npcInstance) {
        NpcInfo npcInfo = co.get(npcInstance.getObjectId());
        if (npcInfo == null || npcInfo.lastUpdate < System.currentTimeMillis() - Config.ITEM_BROKER_UPDATE_TIME) {
            npcInfo = new NpcInfo();
            npcInfo.lastUpdate = System.currentTimeMillis();
            npcInfo.bestBuyItems = new TreeMap();
            npcInfo.bestSellItems = new TreeMap();
            npcInfo.bestCraftItems = new TreeMap();
            int n = 0;
            block5: for (Player player2 : World.getAroundPlayers((GameObject)npcInstance, (int)4000, (int)400)) {
                int n2 = player2.getPrivateStoreType();
                if (n2 != 1 && n2 != 3 && n2 != 5) continue;
                TreeMap<String, TreeMap<Long, Item>> treeMap = null;
                List list = null;
                switch (n2) {
                    case 1: {
                        Object object;
                        ManufactureItem manufactureItem;
                        treeMap = npcInfo.bestSellItems;
                        list = player2.getSellList();
                        for (TradeItem tradeItem : list) {
                            long l;
                            manufactureItem = tradeItem.getItem();
                            if (manufactureItem == null) continue;
                            TreeMap<Long, Item> treeMap2 = treeMap.get(manufactureItem.getName());
                            if (treeMap2 == null) {
                                treeMap2 = new TreeMap();
                                treeMap.put(manufactureItem.getName(), treeMap2);
                            }
                            object = new Item(tradeItem.getItemId(), n2, tradeItem.getOwnersPrice(), tradeItem.getCount(), tradeItem.getEnchantLevel(), manufactureItem.getName(), player2.getStoredId(), player2.getName(), player2.getLoc(), tradeItem.getObjectId(), tradeItem);
                            for (l = object.price * 100L; l < object.price * 100L + 100L && treeMap2.containsKey(l); ++l) {
                            }
                            treeMap2.put(l, (Item)object);
                        }
                        continue block5;
                    }
                    case 3: {
                        Object object;
                        ManufactureItem manufactureItem;
                        treeMap = npcInfo.bestBuyItems;
                        list = player2.getBuyList();
                        for (TradeItem tradeItem : list) {
                            long l;
                            manufactureItem = tradeItem.getItem();
                            if (manufactureItem == null) continue;
                            TreeMap<Long, Item> treeMap3 = treeMap.get(manufactureItem.getName());
                            if (treeMap3 == null) {
                                treeMap3 = new TreeMap();
                                treeMap.put(manufactureItem.getName(), treeMap3);
                            }
                            object = new Item(tradeItem.getItemId(), n2, tradeItem.getOwnersPrice(), tradeItem.getCount(), tradeItem.getEnchantLevel(), manufactureItem.getName(), player2.getStoredId(), player2.getName(), player2.getLoc(), n++, tradeItem);
                            for (l = object.price * 100L; l < object.price * 100L + 100L && treeMap3.containsKey(l); ++l) {
                            }
                            treeMap3.put(l, (Item)object);
                        }
                        continue block5;
                    }
                    case 5: {
                        Object object;
                        ManufactureItem manufactureItem;
                        TradeItem tradeItem;
                        treeMap = npcInfo.bestCraftItems;
                        Iterator iterator = player2.getCreateList();
                        if (iterator == null) continue block5;
                        tradeItem = iterator.iterator();
                        while (tradeItem.hasNext()) {
                            manufactureItem = (ManufactureItem)tradeItem.next();
                            int n3 = manufactureItem.getRecipeId();
                            object = RecipeHolder.getInstance().getRecipeById(n3);
                            if (object == null) continue;
                            for (Pair pair : object.getProducts()) {
                                long l;
                                ItemTemplate itemTemplate = (ItemTemplate)pair.getKey();
                                if (itemTemplate == null) continue;
                                TreeMap<Long, Item> treeMap4 = treeMap.get(itemTemplate.getName());
                                if (treeMap4 == null) {
                                    treeMap4 = new TreeMap();
                                    treeMap.put(itemTemplate.getName(), treeMap4);
                                }
                                Item item = new Item(((ItemTemplate)pair.getKey()).getItemId(), n2, manufactureItem.getCost(), (Long)((Pair)pair.getValue()).getLeft(), 0, itemTemplate.getName(), player2.getStoredId(), player2.getName(), player2.getLoc(), n++, null);
                                for (l = item.price * 100L; l < item.price * 100L + 100L && treeMap4.containsKey(l); ++l) {
                                }
                                treeMap4.put(l, item);
                            }
                        }
                        continue block5;
                    }
                    default: {
                        continue block5;
                    }
                }
            }
            co.put(npcInstance.getObjectId(), npcInfo);
        }
    }

    public void find(String[] stringArray) {
        Item item;
        int n;
        TreeMap<String, TreeMap<Long, Item>> treeMap;
        int n2;
        int n3;
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (stringArray.length < 3 || stringArray.length > 7) {
            if (player.isLangRus()) {
                ItemBroker.show((String)"\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430 \u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u043e\u0442 1 \u0434\u043e 16 \u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432.<br>[npc_%objectId%_Chat 0|<font color=\"FF9900\">\u041d\u0430\u0437\u0430\u0434</font>]", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            } else {
                ItemBroker.show((String)"Please enter from 1 up to 16 symbols.<br>[npc_%objectId%_Chat 0|<font color=\"FF9900\">Back</font>]", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            }
            return;
        }
        int n4 = 0;
        String[] stringArray2 = null;
        try {
            n3 = Integer.valueOf(stringArray[0]);
            n2 = Integer.valueOf(stringArray[1]);
            stringArray2 = new String[stringArray.length - 2];
            for (int i = 0; i < stringArray2.length; ++i) {
                stringArray2[i] = treeMap = stringArray[i + 2].trim().toLowerCase();
                if (((String)((Object)treeMap)).length() <= 1 || !((String)((Object)treeMap)).startsWith("+")) continue;
                n4 = Integer.valueOf(((String)((Object)treeMap)).substring(1));
            }
        }
        catch (Exception exception) {
            ItemBroker.show((String)"\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        treeMap = this.a(n3);
        if (treeMap == null) {
            ItemBroker.show((String)"\u041e\u0448\u0438\u0431\u043a\u0430 - \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432 \u0441 \u0442\u0430\u043a\u0438\u043c \u0442\u0438\u043f\u043e\u043c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ArrayList<Item> arrayList = new ArrayList<Item>();
        block3: for (Map.Entry entry : treeMap.entrySet()) {
            for (n = 0; n < stringArray2.length; ++n) {
                String string = stringArray2[n];
                if (!string.startsWith("+") && ((String)entry.getKey()).toLowerCase().indexOf(string) == -1) continue block3;
            }
            TreeMap treeMap2 = (TreeMap)entry.getValue();
            item = null;
            for (Item item2 : treeMap2.values()) {
                if (item2 == null || item2.enchant < n4) continue;
                item = item2;
                break;
            }
            if (item == null) continue;
            arrayList.add(item);
        }
        StringBuilder stringBuilder = new StringBuilder(200);
        stringBuilder.append("<a action=\"bypass -h npc_%objectId%_Chat 1");
        stringBuilder.append(n3);
        stringBuilder.append("\">\u00ab\u00ab</a>&nbsp;&nbsp;");
        int n5 = arrayList.size();
        n5 = n5 / 10 + (n5 % 10 > 0 ? 1 : 0);
        n5 = Math.max(1, n5);
        n2 = Math.min(n5, Math.max(1, n2));
        if (n5 > 1) {
            n = Math.max(1, Math.min(n5 - 9 + 1, n2 - 4));
            if (n > 1) {
                this.a(stringBuilder, n3, 1, stringArray2, "1");
            }
            if (n2 > 11) {
                this.a(stringBuilder, n3, n2 - 10, stringArray2, String.valueOf(n2 - 10));
            }
            if (n2 > 1) {
                this.a(stringBuilder, n3, n2 - 1, stringArray2, "<");
            }
            for (int i = 0; i < 9 && n <= n5; ++i, ++n) {
                if (n == n2) {
                    stringBuilder.append(n).append("&nbsp;");
                    continue;
                }
                this.a(stringBuilder, n3, n, stringArray2, String.valueOf(n));
            }
            if (n2 < n5) {
                this.a(stringBuilder, n3, n2 + 1, stringArray2, ">");
            }
            if (n2 < n5 - 10) {
                this.a(stringBuilder, n3, n2 + 10, stringArray2, String.valueOf(n2 + 10));
            }
            if (n <= n5) {
                this.a(stringBuilder, n3, n5, stringArray2, String.valueOf(n5));
            }
        }
        stringBuilder.append("<table width=100%>");
        if (arrayList.size() > 0) {
            n = 0;
            ListIterator listIterator = arrayList.listIterator((n2 - 1) * 10);
            while (listIterator.hasNext() && n < 10) {
                ItemTemplate itemTemplate;
                item = (Item)listIterator.next();
                ItemTemplate itemTemplate2 = itemTemplate = item.item != null ? item.item.getItem() : ItemHolder.getInstance().getTemplate(item.itemId);
                if (itemTemplate == null) continue;
                stringBuilder.append("<tr><td>");
                stringBuilder.append(itemTemplate.getIcon32());
                stringBuilder.append("</td><td><table width=100%><tr><td>[scripts_services.ItemBroker:listForItem ");
                stringBuilder.append(n3);
                stringBuilder.append(" ");
                stringBuilder.append(item.itemId);
                stringBuilder.append(" ");
                stringBuilder.append(n4);
                stringBuilder.append(" 0 0 1 ");
                stringBuilder.append(n2);
                if (stringArray2 != null) {
                    for (int i = 0; i < stringArray2.length; ++i) {
                        stringBuilder.append(" ");
                        stringBuilder.append(stringArray2[i]);
                    }
                }
                stringBuilder.append("|");
                stringBuilder.append("<font color=\"LEVEL\">");
                stringBuilder.append(itemTemplate.getName());
                stringBuilder.append("</font>]");
                stringBuilder.append("</td></tr>");
                stringBuilder.append("</table></td></tr>");
                ++n;
            }
        } else if (this.getSelf().isLangRus()) {
            stringBuilder.append("<tr><td colspan=2>\u041d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e.</td></tr>");
        } else {
            stringBuilder.append("<tr><td colspan=2>Nothing found.</td></tr>");
        }
        stringBuilder.append("</table><br>&nbsp;");
        ItemBroker.show((String)stringBuilder.toString(), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    private void a(StringBuilder stringBuilder, int n, int n2, String[] stringArray, String string) {
        stringBuilder.append("[scripts_services.ItemBroker:find ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(n2);
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                stringBuilder.append(" ");
                stringBuilder.append(stringArray[i]);
            }
        }
        stringBuilder.append("|");
        stringBuilder.append(string);
        stringBuilder.append("]&nbsp;");
    }

    public class NpcInfo {
        public long lastUpdate;
        public TreeMap<String, TreeMap<Long, Item>> bestSellItems;
        public TreeMap<String, TreeMap<Long, Item>> bestBuyItems;
        public TreeMap<String, TreeMap<Long, Item>> bestCraftItems;
    }

    public class Item {
        public final int itemId;
        public final int itemObjId;
        public final int type;
        public final long price;
        public final long count;
        public final int enchant;
        public final boolean rare;
        public final long merchantStoredId;
        public final String name;
        public final String merchantName;
        public final Location player;
        public final TradeItem item;

        public Item(int n, int n2, long l, long l2, int n3, String string, long l3, String string2, Location location, int n4, TradeItem tradeItem) {
            this.itemId = n;
            this.type = n2;
            this.price = l;
            this.count = l2;
            this.enchant = n3;
            this.rare = ArrayUtils.contains((int[])ItemBroker.this.RARE_ITEMS, (int)n);
            StringBuilder stringBuilder = new StringBuilder(70);
            if (n3 > 0) {
                if (this.rare) {
                    stringBuilder.append("<font color=\"FF0000\">+");
                } else {
                    stringBuilder.append("<font color=\"7CFC00\">+");
                }
                stringBuilder.append(n3);
                stringBuilder.append(" ");
            } else if (this.rare) {
                stringBuilder.append("<font color=\"0000FF\">Rare ");
            } else {
                stringBuilder.append("<font color=\"LEVEL\">");
            }
            stringBuilder.append(string);
            stringBuilder.append("</font>]");
            if (tradeItem != null) {
                if (tradeItem.getAttackElement() != Element.NONE.getId()) {
                    stringBuilder.append(" &nbsp;<font color=\"7CFC00\">+");
                    stringBuilder.append(tradeItem.getAttackElementValue());
                    switch (tradeItem.getAttackElement()) {
                        case 0: {
                            stringBuilder.append(" Fire");
                            break;
                        }
                        case 1: {
                            stringBuilder.append(" Water");
                            break;
                        }
                        case 2: {
                            stringBuilder.append(" Wind");
                            break;
                        }
                        case 3: {
                            stringBuilder.append(" Earth");
                            break;
                        }
                        case 4: {
                            stringBuilder.append(" Holy");
                            break;
                        }
                        case 5: {
                            stringBuilder.append(" Unholy");
                        }
                    }
                    stringBuilder.append("</font>");
                } else {
                    int n5;
                    int n6;
                    int n7;
                    int n8;
                    int n9;
                    int n10 = tradeItem.getDefenceFire();
                    if (n10 + (n9 = tradeItem.getDefenceWater()) + (n8 = tradeItem.getDefenceWind()) + (n7 = tradeItem.getDefenceEarth()) + (n6 = tradeItem.getDefenceHoly()) + (n5 = tradeItem.getDefenceUnholy()) > 0) {
                        stringBuilder.append("&nbsp;<font color=\"7CFC00\">");
                        if (n10 > 0) {
                            stringBuilder.append("+");
                            stringBuilder.append(n10);
                            stringBuilder.append(" Fire ");
                        }
                        if (n9 > 0) {
                            stringBuilder.append("+");
                            stringBuilder.append(n9);
                            stringBuilder.append(" Water ");
                        }
                        if (n8 > 0) {
                            stringBuilder.append("+");
                            stringBuilder.append(n8);
                            stringBuilder.append(" Wind ");
                        }
                        if (n7 > 0) {
                            stringBuilder.append("+");
                            stringBuilder.append(n7);
                            stringBuilder.append(" Earth ");
                        }
                        if (n6 > 0) {
                            stringBuilder.append("+");
                            stringBuilder.append(n6);
                            stringBuilder.append(" Holy ");
                        }
                        if (n5 > 0) {
                            stringBuilder.append("+");
                            stringBuilder.append(n5);
                            stringBuilder.append(" Unholy ");
                        }
                        stringBuilder.append("</font>");
                    }
                }
            }
            this.name = stringBuilder.toString();
            this.merchantStoredId = l3;
            this.merchantName = string2;
            this.player = location;
            this.itemObjId = n4;
            this.item = tradeItem;
        }
    }
}
