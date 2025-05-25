/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.holder.RecipeHolder
 *  l2.gameserver.handler.bbs.CommunityBoardManager
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Recipe
 *  l2.gameserver.model.World
 *  l2.gameserver.model.items.ManufactureItem
 *  l2.gameserver.model.items.TradeItem
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.RadarControl
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.templates.item.support.Grade
 *  l2.gameserver.utils.MapRegionUtils
 *  l2.gameserver.utils.Util
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.RecipeHolder;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Recipe;
import l2.gameserver.model.World;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.support.Grade;
import l2.gameserver.utils.MapRegionUtils;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionCommunity
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger eo = LoggerFactory.getLogger(RegionCommunity.class);
    private static final TownEntry[] a = new TownEntry[]{new TownEntry("Gatekeeper.TheTownofGludio", 19, 21), new TownEntry("Gatekeeper.TheTownofDion", 20, 22), new TownEntry("Gatekeeper.TheTownofGiran", 22, 22), new TownEntry("Gatekeeper.TheTownofOren", 22, 19), new TownEntry("Gatekeeper.TheTownofAden", 24, 18), new TownEntry("Gatekeeper.Heine", 23, 24), new TownEntry("Gatekeeper.TheTownofGoddard", 24, 16), new TownEntry("Gatekeeper.RuneTownship", 21, 16), new TownEntry("Gatekeeper.TheTownofSchuttgart", 22, 13)};
    private static final String[] bd = new String[]{"&$596;", "&$597;", "&$665;"};
    private static final String[] be = new String[]{"&$1622;", "&$1623;", "&$1624;", "&$1625;", "&$1626;", "&$1627;"};
    private static final String[] bf = new String[]{"&$1291;", "&$1292;", "&$1293;", "&$1294;", "&$1295;", "S80 Grade", "S84 Grade"};
    private static final int bGq = 12;

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            eo.info("CommunityBoard: Region service loaded.");
            CommunityBoardManager.getInstance().registerHandler((ICommunityBoardHandler)this);
        }
    }

    public void onReload() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            CommunityBoardManager.getInstance().removeHandler((ICommunityBoardHandler)this);
        }
    }

    public void onShutdown() {
    }

    public String[] getBypassCommands() {
        return new String[]{"_bbsloc", "_bbsregion_", "_bbsreglist_", "_bbsregsearch", "_bbsregview_", "_bbsregtarget_"};
    }

    public void onBypassCommand(Player player, String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
        String string2 = stringTokenizer.nextToken();
        player.setSessionVar("add_fav", null);
        if ("bbsloc".equals(string2)) {
            Object object;
            String string3 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_regiontpl.htm", player);
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < a.length; ++i) {
                object = a[i];
                String string4 = string3.replace("%region_bypass%", "_bbsregion_" + String.valueOf(i));
                string4 = string4.replace("%region_name%", StringHolder.getInstance().getNotNull(player, ((TownEntry)object).getTownNameAddr()));
                string4 = string4.replace("%region_desc%", "&$498;: &$1157;, &$1434;, &$645;.");
                string4 = string4.replace("%region_type%", "l2ui.bbs_folder");
                int n = 0;
                int n2 = ((TownEntry)object).getX();
                int n3 = ((TownEntry)object).getY();
                int n4 = 0;
                for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                    int n5 = MapRegionUtils.regionX((GameObject)player2);
                    int n6 = MapRegionUtils.regionY((GameObject)player2);
                    if (n5 < n2 - n4 || n5 > n2 + n4 || n6 < n3 - n4 || n6 > n3 + n4 || player2.getPrivateStoreType() <= 0 || player2.getPrivateStoreType() == 7) continue;
                    ++n;
                }
                string4 = string4.replace("%sellers_count%", String.valueOf(n));
                stringBuilder.append(string4);
            }
            HashMap hashMap = Util.parseTemplate((String)HtmCache.getInstance().getNotNull("scripts/services/community/bbs_region_list.htm", player));
            object = (String)hashMap.get(0);
            object = ((String)object).replace("%REGION_LIST%", stringBuilder.toString());
            object = ((String)object).replace("<?tree_menu?>", (CharSequence)hashMap.get(1));
            ShowBoard.separateAndSend((String)object, (Player)player);
        } else if ("bbsregion".equals(string2)) {
            String string5;
            String string6 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_regiontpl.htm", player);
            int n = Integer.parseInt(stringTokenizer.nextToken());
            StringBuilder stringBuilder = new StringBuilder("");
            TownEntry townEntry = a[n];
            player.setSessionVar("add_fav", string + "&Region " + n);
            for (int i = 0; i < bd.length; ++i) {
                string5 = string6.replace("%region_bypass%", "_bbsreglist_" + n + "_" + i + "_1_0_");
                string5 = string5.replace("%region_name%", bd[i]);
                string5 = string5.replace("%region_desc%", bd[i] + ".");
                string5 = string5.replace("%region_type%", "l2ui.bbs_board");
                int n7 = 0;
                int n8 = townEntry.getX();
                int n9 = townEntry.getY();
                int n10 = 0;
                for (Player player3 : GameObjectsStorage.getAllPlayersForIterate()) {
                    int n11 = MapRegionUtils.regionX((GameObject)player3);
                    int n12 = MapRegionUtils.regionY((GameObject)player3);
                    if (n11 < n8 - n10 || n11 > n8 + n10 || n12 < n9 - n10 || n12 > n9 + n10) continue;
                    if (i == 0 && (player3.getPrivateStoreType() == 1 || player3.getPrivateStoreType() == 8)) {
                        ++n7;
                        continue;
                    }
                    if (i == 1 && player3.getPrivateStoreType() == 3) {
                        ++n7;
                        continue;
                    }
                    if (i != 2 || player3.getPrivateStoreType() != 5) continue;
                    ++n7;
                }
                string5 = string5.replace("%sellers_count%", String.valueOf(n7));
                stringBuilder.append(string5);
            }
            HashMap hashMap = Util.parseTemplate((String)HtmCache.getInstance().getNotNull("scripts/services/community/bbs_region_list.htm", player));
            string5 = (String)hashMap.get(0);
            string5 = string5.replace("%REGION_LIST%", stringBuilder.toString());
            string5 = string5.replace("<?tree_menu?>", ((String)hashMap.get(2)).replace("%TREE%", "&nbsp;>&nbsp;" + StringHolder.getInstance().getNotNull(player, townEntry.getTownNameAddr())));
            ShowBoard.separateAndSend((String)string5, (Player)player);
        } else if ("bbsreglist".equals(string2)) {
            int n;
            CharSequence charSequence;
            int n13;
            int n14 = Integer.parseInt(stringTokenizer.nextToken());
            int n15 = Integer.parseInt(stringTokenizer.nextToken());
            int n16 = Integer.parseInt(stringTokenizer.nextToken());
            int n17 = Integer.parseInt(stringTokenizer.nextToken());
            String string7 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken().toLowerCase() : "";
            TownEntry townEntry = a[n14];
            player.setSessionVar("add_fav", string + "&Region " + n14 + " " + bd[n15]);
            List<Player> list = RegionCommunity.a(n14, n15, string7, n17 == 1);
            int n18 = (n16 - 1) * 12;
            int n19 = Math.min(n16 * 12, list.size());
            String string8 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_region_sellers.htm", player);
            if (n16 == 1) {
                string8 = string8.replace("%ACTION_GO_LEFT%", "");
                string8 = string8.replace("%GO_LIST%", "");
                string8 = string8.replace("%NPAGE%", "1");
            } else {
                string8 = string8.replace("%ACTION_GO_LEFT%", "bypass _bbsreglist_" + n14 + "_" + n15 + "_" + (n16 - 1) + "_" + n17 + "_" + string7);
                string8 = string8.replace("%NPAGE%", String.valueOf(n16));
                StringBuilder stringBuilder = new StringBuilder("");
                int n20 = n13 = n16 > 10 ? n16 - 10 : 1;
                while (n13 < n16) {
                    stringBuilder.append("<td><a action=\"bypass _bbsreglist_").append(n14).append("_").append(n15).append("_").append(n13).append("_").append(n17).append("_").append(string7).append("\"> ").append(n13).append(" </a> </td>\n\n");
                    ++n13;
                }
                string8 = string8.replace("%GO_LIST%", stringBuilder.toString());
            }
            int n21 = Math.max(list.size() / 12, 1);
            if (list.size() > n21 * 12) {
                ++n21;
            }
            if (n21 > n16) {
                string8 = string8.replace("%ACTION_GO_RIGHT%", "bypass _bbsreglist_" + n14 + "_" + n15 + "_" + (n16 + 1) + "_" + n17 + "_" + string7);
                n13 = Math.min(n16 + 10, n21);
                charSequence = new StringBuilder("");
                for (n = n16 + 1; n <= n13; ++n) {
                    ((StringBuilder)charSequence).append("<td><a action=\"bypass _bbsreglist_").append(n14).append("_").append(n15).append("_").append(n).append("_").append(n17).append("_").append(string7).append("\"> ").append(n).append(" </a> </td>\n\n");
                }
                string8 = string8.replace("%GO_LIST2%", ((StringBuilder)charSequence).toString());
            } else {
                string8 = string8.replace("%ACTION_GO_RIGHT%", "");
                string8 = string8.replace("%GO_LIST2%", "");
            }
            StringBuilder stringBuilder = new StringBuilder("");
            charSequence = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_region_stpl.htm", player);
            for (n = n18; n < n19; ++n) {
                Player player4 = list.get(n);
                List list2 = player4.getTradeList();
                List list3 = player4.getCreateList();
                if (list2 == null && list3 == null) continue;
                CharSequence charSequence2 = charSequence;
                charSequence2 = ((String)charSequence2).replace("%view_bypass%", "bypass _bbsregview_" + n14 + "_" + n15 + "_" + n16 + "_" + player4.getObjectId() + "_" + n17 + "_" + string7);
                charSequence2 = ((String)charSequence2).replace("%seller_name%", player4.getName());
                String string9 = "-";
                if (n15 == 0) {
                    string9 = list2 != null && player4.getSellStoreName() != null && !player4.getSellStoreName().isEmpty() ? player4.getSellStoreName() : "-";
                } else if (n15 == 1) {
                    string9 = list2 != null && player4.getBuyStoreName() != null && !player4.getBuyStoreName().isEmpty() ? player4.getBuyStoreName() : "-";
                } else if (n15 == 2 && player4.getPrivateStoreType() == 5) {
                    string9 = list3 != null && player4.getManufactureName() != null && !player4.getManufactureName().isEmpty() ? player4.getManufactureName() : "-";
                }
                string9 = string9.replace("<", "");
                string9 = string9.replace(">", "");
                string9 = string9.replace("&", "");
                string9 = string9.replace("$", "");
                if (string9.isEmpty()) {
                    string9 = "-";
                }
                charSequence2 = ((String)charSequence2).replace("%seller_title%", string9);
                stringBuilder.append((String)charSequence2);
            }
            string8 = string8.replace("%SELLER_LIST%", stringBuilder.toString());
            string8 = string8.replace("%search_bypass%", "_bbsregsearch_" + n14 + "_" + n15);
            string8 = string8.replace("%TREE%", "&nbsp;>&nbsp;<a action=\"bypass _bbsregion_" + n14 + "\">" + StringHolder.getInstance().getNotNull(player, townEntry.getTownNameAddr()) + "</a>&nbsp;>&nbsp;" + bd[n15]);
            ShowBoard.separateAndSend((String)string8, (Player)player);
        } else if ("bbsregview".equals(string2)) {
            Object object;
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int n22 = Integer.parseInt(stringTokenizer.nextToken());
            int n23 = Integer.parseInt(stringTokenizer.nextToken());
            int n24 = Integer.parseInt(stringTokenizer.nextToken());
            int n25 = Integer.parseInt(stringTokenizer.nextToken());
            String string10 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken().toLowerCase() : "";
            TownEntry townEntry = a[n];
            Player player5 = World.getPlayer((int)n24);
            if (player5 == null || player5.getPrivateStoreType() == 0) {
                this.onBypassCommand(player, "_bbsreglist_" + n + "_" + n22 + "_" + n23 + "_" + n25 + "_" + string10);
                return;
            }
            String string11 = "-";
            String string12 = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_region_storetpl.htm", player);
            StringBuilder stringBuilder = new StringBuilder("");
            if (n22 < 2) {
                object = n22 == 0 ? player5.getSellList() : player5.getBuyList();
                List list = player5.getTradeList();
                if (object == null || object.isEmpty() || list == null) {
                    this.onBypassCommand(player, "_bbsreglist_" + n + "_" + n22 + "_" + n23 + "_" + n25 + "_" + string10);
                    return;
                }
                if (n22 == 0 && player5.getSellStoreName() != null && !player5.getSellStoreName().isEmpty()) {
                    string11 = player5.getSellStoreName();
                } else if (n22 == 1 && player5.getBuyStoreName() != null && !player5.getBuyStoreName().isEmpty()) {
                    string11 = player5.getBuyStoreName();
                }
                Iterator iterator = object.iterator();
                while (iterator.hasNext()) {
                    TradeItem tradeItem = (TradeItem)iterator.next();
                    ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(tradeItem.getItemId());
                    if (itemTemplate == null) continue;
                    String string13 = string12.replace("%item_name%", itemTemplate.getName() + (String)(itemTemplate.isEquipment() && tradeItem.getEnchantLevel() > 0 ? " +" + tradeItem.getEnchantLevel() : ""));
                    string13 = string13.replace("%item_img%", itemTemplate.getIcon());
                    string13 = string13.replace("%item_count%", String.valueOf(tradeItem.getCount()));
                    string13 = string13.replace("%item_price%", String.format("%,3d", tradeItem.getOwnersPrice()).replace("\u00a0", ","));
                    Object object2 = "";
                    if (itemTemplate.getCrystalType() != Grade.NONE) {
                        object2 = bf[itemTemplate.getCrystalType().ordinal() - 1];
                        object2 = (String)object2 + (String)(itemTemplate.getCrystalCount() > 0 ? (player.isLangRus() ? " \u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u043e\u0432: " : " Crystals: ") + itemTemplate.getCrystalCount() + ";&nbsp;" : ";&nbsp;");
                    }
                    if (itemTemplate.isEquipment()) {
                        if (tradeItem.getAttackElement() >= 0 && tradeItem.getAttackElementValue() > 0) {
                            object2 = (String)object2 + "&$1620;: " + be[tradeItem.getAttackElement()] + " +" + tradeItem.getAttackElementValue();
                        } else if (tradeItem.getDefenceFire() > 0 || tradeItem.getDefenceWater() > 0 || tradeItem.getDefenceWind() > 0 || tradeItem.getDefenceEarth() > 0 || tradeItem.getDefenceHoly() > 0 || tradeItem.getDefenceUnholy() > 0) {
                            object2 = (String)object2 + "&$1651;:";
                            if (tradeItem.getDefenceFire() > 0) {
                                object2 = (String)object2 + " &$1622; +" + tradeItem.getDefenceFire() + ";&nbsp;";
                            }
                            if (tradeItem.getDefenceWater() > 0) {
                                object2 = (String)object2 + " &$1623; +" + tradeItem.getDefenceWater() + ";&nbsp;";
                            }
                            if (tradeItem.getDefenceWind() > 0) {
                                object2 = (String)object2 + " &$1624; +" + tradeItem.getDefenceWind() + ";&nbsp;";
                            }
                            if (tradeItem.getDefenceEarth() > 0) {
                                object2 = (String)object2 + " &$1625; +" + tradeItem.getDefenceEarth() + ";&nbsp;";
                            }
                            if (tradeItem.getDefenceHoly() > 0) {
                                object2 = (String)object2 + " &$1626; +" + tradeItem.getDefenceHoly() + ";&nbsp;";
                            }
                            if (tradeItem.getDefenceUnholy() > 0) {
                                object2 = (String)object2 + " &$1627; +" + tradeItem.getDefenceUnholy() + ";&nbsp;";
                            }
                        }
                    }
                    if (itemTemplate.isStackable()) {
                        object2 = (String)object2 + (player.isLangRus() ? "\u0421\u0442\u044b\u043a\u0443\u0435\u043c\u044b\u0439;&nbsp;" : "Stackable;&nbsp;");
                    }
                    if (itemTemplate.isSealedItem()) {
                        object2 = (String)object2 + (player.isLangRus() ? "\u0417\u0430\u043f\u0435\u0447\u0430\u0442\u0430\u043d\u043d\u044b\u0439;&nbsp;" : "Sealed;&nbsp;");
                    }
                    if (itemTemplate.isShadowItem()) {
                        object2 = (String)object2 + (player.isLangRus() ? "\u0422\u0435\u043d\u0435\u0432\u043e\u0439 \u043f\u0440\u0435\u0434\u043c\u0435\u0442;&nbsp;" : "Shadow item;&nbsp;");
                    }
                    if (itemTemplate.isTemporal()) {
                        object2 = (String)object2 + (player.isLangRus() ? "\u0412\u0440\u0435\u043c\u0435\u043d\u043d\u044b\u0439;&nbsp;" : "Temporal;&nbsp;");
                    }
                    string13 = string13.replace("%item_desc%", (CharSequence)object2);
                    stringBuilder.append(string13);
                }
            } else {
                object = player5.getCreateList();
                if (object == null) {
                    this.onBypassCommand(player, "_bbsreglist_" + n + "_" + n22 + "_" + n23 + "_" + n25 + "_" + string10);
                    return;
                }
                string11 = player5.getManufactureName();
                if (string11 == null) {
                    string11 = "-";
                }
                Iterator iterator = object.iterator();
                while (iterator.hasNext()) {
                    ItemTemplate itemTemplate;
                    ManufactureItem manufactureItem = (ManufactureItem)iterator.next();
                    Recipe recipe = RecipeHolder.getInstance().getRecipeById(manufactureItem.getRecipeId() - 1);
                    if (recipe == null || recipe.getProducts().isEmpty() || (itemTemplate = (ItemTemplate)((Pair)recipe.getProducts().get(0)).getKey()) == null) continue;
                    String string14 = string12.replace("%item_name%", itemTemplate.getName());
                    string14 = string14.replace("%item_img%", itemTemplate.getIcon());
                    string14 = string14.replace("%item_count%", "N/A");
                    string14 = string14.replace("%item_price%", String.format("%,3d", manufactureItem.getCost()).replace("\u00a0", ","));
                    Object object3 = "";
                    if (itemTemplate.getCrystalType() != Grade.NONE) {
                        object3 = bf[itemTemplate.getCrystalType().ordinal() - 1] + (String)(itemTemplate.getCrystalCount() > 0 ? (player.isLangRus() ? " \u041a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u043e\u0432: " : " Crystals: ") + itemTemplate.getCrystalCount() + ";&nbsp;" : ";&nbsp;");
                    }
                    if (itemTemplate.isStackable()) {
                        Object object4 = object3 = player.isLangRus() ? "\u0421\u0442\u044b\u043a\u0443\u0435\u043c\u044b\u0439;&nbsp;" : "Stackable;&nbsp;";
                    }
                    if (itemTemplate.isSealedItem()) {
                        object3 = (String)object3 + (player.isLangRus() ? "\u0417\u0430\u043f\u0435\u0447\u0430\u0442\u0430\u043d\u043d\u044b\u0439;&nbsp;" : "Sealed;&nbsp;");
                    }
                    string14 = string14.replace("%item_desc%", (CharSequence)object3);
                    stringBuilder.append(string14);
                }
            }
            object = HtmCache.getInstance().getNotNull("scripts/services/community/bbs_region_view.htm", player);
            object = ((String)object).replace("%sell_type%", bd[n22]);
            string11 = string11.replace("<", "");
            string11 = string11.replace(">", "");
            string11 = string11.replace("&", "");
            if ((string11 = string11.replace("$", "")).isEmpty()) {
                string11 = "-";
            }
            object = ((String)object).replace("%title%", string11);
            object = ((String)object).replace("%char_name%", player5.getName());
            object = ((String)object).replace("%object_id%", String.valueOf(player5.getObjectId()));
            object = ((String)object).replace("%STORE_LIST%", stringBuilder.toString());
            object = ((String)object).replace("%list_bypass%", "_bbsreglist_" + n + "_" + n22 + "_" + n23 + "_" + n25 + "_" + string10);
            object = ((String)object).replace("%TREE%", "&nbsp;>&nbsp;<a action=\"bypass _bbsregion_" + n + "\">" + StringHolder.getInstance().getNotNull(player, townEntry.getTownNameAddr()) + "</a>&nbsp;>&nbsp;<a action=\"bypass _bbsreglist_" + n + "_" + n22 + "_" + n23 + "_" + n25 + "_\">" + bd[n22] + "</a>&nbsp;>&nbsp;" + player5.getName());
            ShowBoard.separateAndSend((String)object, (Player)player);
        } else if ("bbsregtarget".equals(string2)) {
            int n = Integer.parseInt(stringTokenizer.nextToken());
            Player player6 = World.getPlayer((int)n);
            if (player6 != null) {
                player.sendPacket((IStaticPacket)new RadarControl(0, 2, player6.getLoc()));
                if (player.knowsObject((GameObject)player6)) {
                    player.setObjectTarget((GameObject)player6);
                    player6.broadcastRelation();
                }
            } else {
                player.sendActionFailed();
            }
        }
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, "_");
        String string7 = stringTokenizer.nextToken();
        if ("bbsregsearch".equals(string7)) {
            String string8;
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int n2 = Integer.parseInt(stringTokenizer.nextToken());
            String string9 = string8 = "Item".equals(string5) ? "1" : "0";
            if (string4 == null) {
                string4 = "";
            }
            string4 = string4.replace("<", "");
            string4 = string4.replace(">", "");
            string4 = string4.replace("&", "");
            if ((string4 = string4.replace("$", "")).length() > 30) {
                string4 = string4.substring(0, 30);
            }
            this.onBypassCommand(player, "_bbsreglist_" + n + "_" + n2 + "_1_" + string8 + "_" + string4);
        }
    }

    private static List<Player> a(int n, int n2, String string, boolean bl) {
        Player[] playerArray;
        Object object;
        List list;
        Player[] playerArray2 = new ArrayList();
        TownEntry townEntry = a[n];
        int n3 = townEntry.getX();
        int n4 = townEntry.getY();
        int n5 = 0;
        for (Object object2 : GameObjectsStorage.getAllPlayersForIterate()) {
            int n6 = MapRegionUtils.regionX((GameObject)object2);
            int n7 = MapRegionUtils.regionY((GameObject)object2);
            if (n6 < n3 - n5 || n6 > n3 + n5 || n7 < n4 - n5 || n7 > n4 + n5) continue;
            list = object2.getTradeList();
            object = object2.getCreateList();
            if (object2.getPrivateStoreType() <= 0) continue;
            if (n2 == 0 && list != null && (object2.getPrivateStoreType() == 1 || object2.getPrivateStoreType() == 8)) {
                playerArray2.add(object2);
                continue;
            }
            if (n2 == 1 && list != null && object2.getPrivateStoreType() == 3) {
                playerArray2.add(object2);
                continue;
            }
            if (n2 != 2 || object == null || object2.getPrivateStoreType() != 5) continue;
            playerArray2.add(object2);
        }
        if (!string.isEmpty() && !playerArray2.isEmpty()) {
            playerArray = new ArrayList();
            block1: for (Player player : playerArray2) {
                List list2 = player.getTradeList();
                list = player.getCreateList();
                if (bl) {
                    ItemTemplate itemTemplate;
                    TradeItem tradeItem;
                    if ((n2 == 0 || n2 == 1) && list2 != null) {
                        object = n2 == 0 ? player.getSellList() : player.getBuyList();
                        if (object == null) continue;
                        ManufactureItem manufactureItem = object.iterator();
                        while (manufactureItem.hasNext()) {
                            tradeItem = (TradeItem)manufactureItem.next();
                            itemTemplate = ItemHolder.getInstance().getTemplate(tradeItem.getItemId());
                            if (itemTemplate == null || itemTemplate.getName() == null || !itemTemplate.getName().toLowerCase().contains(string)) continue;
                            playerArray.add(player);
                            continue block1;
                        }
                        continue;
                    }
                    if (n2 != 2 || list == null) continue;
                    for (ManufactureItem manufactureItem : list) {
                        tradeItem = RecipeHolder.getInstance().getRecipeById(manufactureItem.getRecipeId() - 1);
                        if (tradeItem == null || tradeItem.getProducts().isEmpty() || (itemTemplate = (ItemTemplate)((Pair)tradeItem.getProducts().get(0)).getKey()) == null || itemTemplate.getName() == null || !itemTemplate.getName().toLowerCase().contains(string)) continue;
                        playerArray.add(player);
                        continue block1;
                    }
                    continue;
                }
                if (n2 == 0 && list2 != null && player.getSellStoreName() != null && player.getSellStoreName().toLowerCase().contains(string)) {
                    playerArray.add(player);
                    continue;
                }
                if (n2 == 1 && list2 != null && player.getBuyStoreName() != null && player.getBuyStoreName().toLowerCase().contains(string)) {
                    playerArray.add(player);
                    continue;
                }
                if (n2 != 2 || list == null || player.getCreateList() == null || player.getManufactureName() == null || !player.getManufactureName().toLowerCase().contains(string)) continue;
                playerArray.add(player);
            }
            playerArray2 = playerArray;
        }
        if (!playerArray2.isEmpty()) {
            playerArray = new Player[playerArray2.size()];
            playerArray2.toArray(playerArray);
            Arrays.sort(playerArray, new PlayersComparator());
            playerArray2.clear();
            playerArray2.addAll(Arrays.asList(playerArray));
        }
        return playerArray2;
    }

    private static class TownEntry {
        private final String io;
        private final int bGr;
        private final int bGs;

        private TownEntry(String string, int n, int n2) {
            this.io = string;
            this.bGr = n;
            this.bGs = n2;
        }

        public String getTownNameAddr() {
            return this.io;
        }

        public int getX() {
            return this.bGr;
        }

        public int getY() {
            return this.bGs;
        }
    }

    private static class PlayersComparator<T>
    implements Comparator<T> {
        private PlayersComparator() {
        }

        @Override
        public int compare(Object object, Object object2) {
            if (object instanceof Player && object2 instanceof Player) {
                Player player = (Player)object;
                Player player2 = (Player)object2;
                return player.getName().compareTo(player2.getName());
            }
            return 0;
        }
    }
}
