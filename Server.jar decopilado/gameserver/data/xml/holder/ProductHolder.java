/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.holder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.gameserver.Config;
import l2.gameserver.model.ProductItem;
import l2.gameserver.model.ProductItemComponent;
import l2.gameserver.templates.item.support.VipInfoTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ProductHolder {
    private static Logger _log = LoggerFactory.getLogger((String)ProductHolder.class.getName());
    TreeMap<Integer, ProductItem> _itemsList;
    private final Map<Byte, VipInfoTemplate> W = new HashMap<Byte, VipInfoTemplate>();
    private static ProductHolder a = new ProductHolder();

    public static ProductHolder getInstance() {
        if (a == null) {
            a = new ProductHolder();
        }
        return a;
    }

    public void reload() {
        a = new ProductHolder();
    }

    private ProductHolder() {
        this._itemsList = new TreeMap();
        try {
            File file = new File(Config.DATAPACK_ROOT, "data/prime_shop.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            Document document = documentBuilderFactory.newDocumentBuilder().parse(file);
            for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (!"list".equalsIgnoreCase(node.getNodeName())) continue;
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    int n;
                    Object object;
                    int n2;
                    byte by;
                    Object object2;
                    Object object3;
                    if ("product".equalsIgnoreCase(node2.getNodeName())) {
                        object3 = node2.getAttributes().getNamedItem("on_sale");
                        object2 = object3 != null && Boolean.parseBoolean(object3.getNodeValue());
                        if (!((Boolean)object2).booleanValue()) continue;
                        by = Integer.parseInt(node2.getAttributes().getNamedItem("id").getNodeValue());
                        Node node3 = node2.getAttributes().getNamedItem("category");
                        n2 = node3 != null ? Integer.parseInt(node3.getNodeValue()) : 11;
                        object = node2.getAttributes().getNamedItem("price");
                        int n3 = object != null ? Integer.parseInt(object.getNodeValue()) : 0;
                        Node node4 = node2.getAttributes().getNamedItem("price_silver_coins");
                        n = node4 != null ? Integer.parseInt(node4.getNodeValue()) : 0;
                        Node node5 = node2.getAttributes().getNamedItem("price_gold_coins");
                        int n4 = node5 != null ? Integer.parseInt(node5.getNodeValue()) : 0;
                        Node node6 = node2.getAttributes().getNamedItem("is_event");
                        boolean bl = node6 != null && Boolean.parseBoolean(node6.getNodeValue());
                        Node node7 = node2.getAttributes().getNamedItem("is_best");
                        boolean bl2 = node7 != null && Boolean.parseBoolean(node7.getNodeValue());
                        Node node8 = node2.getAttributes().getNamedItem("is_new");
                        boolean bl3 = node8 != null && Boolean.parseBoolean(node8.getNodeValue());
                        Node node9 = node2.getAttributes().getNamedItem("is_vip");
                        boolean bl4 = node9 != null && Boolean.parseBoolean(node9.getNodeValue());
                        Node node10 = node2.getAttributes().getNamedItem("vip_level_require");
                        int n5 = node10 != null ? Integer.parseInt(node10.getNodeValue()) : 0;
                        int n6 = ProductHolder.a(bl, bl2, bl3);
                        Node node11 = node2.getAttributes().getNamedItem("sale_start_date");
                        long l = node11 != null ? ProductHolder.b(node11.getNodeValue()) : 0L;
                        Node node12 = node2.getAttributes().getNamedItem("sale_end_date");
                        long l2 = node12 != null ? ProductHolder.b(node12.getNodeValue()) : 0L;
                        ArrayList<ProductItemComponent> arrayList = new ArrayList<ProductItemComponent>();
                        ProductItem productItem = new ProductItem(by, n2, n3, n, n4, bl4, n5, n6, l, l2);
                        for (Node node13 = node2.getFirstChild(); node13 != null; node13 = node13.getNextSibling()) {
                            if (!"component".equalsIgnoreCase(node13.getNodeName())) continue;
                            int n7 = Integer.parseInt(node13.getAttributes().getNamedItem("item_id").getNodeValue());
                            int n8 = Integer.parseInt(node13.getAttributes().getNamedItem("count").getNodeValue());
                            ProductItemComponent productItemComponent = new ProductItemComponent(n7, n8);
                            arrayList.add(productItemComponent);
                        }
                        productItem.setComponents(arrayList);
                        this._itemsList.put(Integer.valueOf(by), productItem);
                    }
                    if (!"vip".equalsIgnoreCase(node2.getNodeName())) continue;
                    object3 = node2.getAttributes();
                    by = -1;
                    int n9 = -1;
                    n2 = -1;
                    object2 = object3.getNamedItem("vipLevel");
                    if (object2 == null) {
                        _log.warn(this.getClass().getSimpleName() + ": Missing vipLevel for vip, skipping");
                        continue;
                    }
                    by = Byte.parseByte(object2.getNodeValue());
                    object2 = object3.getNamedItem("points_to_level");
                    if (object2 == null) {
                        _log.warn(this.getClass().getSimpleName() + ": Missing points_to_level for vip: " + by + ", skipping");
                        continue;
                    }
                    n9 = Integer.parseInt(object2.getNodeValue());
                    object2 = object3.getNamedItem("points_lose");
                    if (object2 == null) {
                        _log.warn(this.getClass().getSimpleName() + ": Missing points_lose for vip: " + by + ", skipping");
                        continue;
                    }
                    n2 = Integer.parseInt(object2.getNodeValue());
                    object = new VipInfoTemplate(by, n9, n2);
                    for (Node node14 = node2.getFirstChild(); node14 != null; node14 = node14.getNextSibling()) {
                        if (!"bonus".equalsIgnoreCase(node14.getNodeName())) continue;
                        int n10 = Integer.parseInt(node14.getAttributes().getNamedItem("skill").getNodeValue());
                        n = Integer.parseInt(node14.getAttributes().getNamedItem("skillLevel").getNodeValue());
                        try {
                            ((VipInfoTemplate)object).setSkill(n10, n);
                            continue;
                        }
                        catch (Exception exception) {
                            _log.warn(this.getClass().getSimpleName() + ": Error in bonus parameter for vip: " + by + ", skipping");
                        }
                    }
                    this.W.put(by, (VipInfoTemplate)object);
                }
            }
            _log.info(String.format("Prime Shop: Loaded %d product item on sale.", this._itemsList.size()));
            _log.info(String.format("Prime Shop Vip Data: Loaded %d Vip levels.", this.W.size()));
        }
        catch (Exception exception) {
            _log.warn("ProductItemTable: Lists could not be initialized.");
            exception.printStackTrace();
        }
    }

    private static int a(boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            return 1;
        }
        if (bl3) {
            return 6;
        }
        if (bl2) {
            return 5;
        }
        return 0;
    }

    private static long b(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date date = simpleDateFormat.parse(string);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return 0L;
        }
    }

    public Collection<ProductItem> getAllItems() {
        return this._itemsList.values();
    }

    public ProductItem getProduct(int n) {
        return this._itemsList.get(n);
    }

    public int getSkillId(byte by) {
        return this.W.get(by).getSkill();
    }

    public int getSkillLevel(byte by) {
        return this.W.get(by).getSkillLevel();
    }

    public Map<Byte, VipInfoTemplate> getVipLevels() {
        return this.W;
    }
}
