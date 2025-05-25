/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntObjectHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.MultiSellEntry;
import l2.gameserver.model.base.MultiSellIngredient;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.MultiSellList;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class MultiSellHolder {
    private static final Logger aV = LoggerFactory.getLogger(MultiSellHolder.class);
    private static MultiSellHolder a = new MultiSellHolder();
    private static final String bG = "production";
    private static final String bH = "ingredient";
    private TIntObjectHashMap<MultiSellListContainer> i = new TIntObjectHashMap();

    public static MultiSellHolder getInstance() {
        return a;
    }

    public MultiSellListContainer getList(int n) {
        return (MultiSellListContainer)this.i.get(n);
    }

    public MultiSellHolder() {
        this.aq();
    }

    public void reload() {
        this.aq();
    }

    private void aq() {
        this.i.clear();
        this.parse();
    }

    private void a(String string, List<File> list) {
        File[] fileArray;
        File file = new File(Config.DATAPACK_ROOT, "data/" + string);
        if (!file.exists()) {
            aV.info("Dir " + file.getAbsolutePath() + " not exists");
            return;
        }
        for (File file2 : fileArray = file.listFiles()) {
            if (file2.getName().endsWith(".xml")) {
                list.add(file2);
                continue;
            }
            if (!file2.isDirectory() || file2.getName().equals(".svn")) continue;
            this.a(string + "/" + file2.getName(), list);
        }
    }

    public void addMultiSellListContainer(int n, MultiSellListContainer multiSellListContainer) {
        if (this.i.containsKey(n)) {
            aV.warn("MultiSell redefined: " + n);
        }
        multiSellListContainer.setListId(n);
        this.i.put(n, (Object)multiSellListContainer);
    }

    public MultiSellListContainer remove(String string) {
        return this.remove(new File(string));
    }

    public MultiSellListContainer remove(File file) {
        return this.remove(Integer.parseInt(file.getName().replaceAll(".xml", "")));
    }

    public MultiSellListContainer remove(int n) {
        return (MultiSellListContainer)this.i.remove(n);
    }

    public void parseFile(File file) {
        int n = 0;
        try {
            n = Integer.parseInt(file.getName().replaceAll(".xml", ""));
        }
        catch (Exception exception) {
            aV.error("Error loading file " + file, (Throwable)exception);
            return;
        }
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            document = documentBuilderFactory.newDocumentBuilder().parse(file);
        }
        catch (Exception exception) {
            aV.error("Error loading file " + file, (Throwable)exception);
            return;
        }
        try {
            this.addMultiSellListContainer(n, this.parseDocument(document, n));
        }
        catch (Exception exception) {
            aV.error("Error in file " + file, (Throwable)exception);
        }
    }

    private void parse() {
        ArrayList<File> arrayList = new ArrayList<File>();
        this.a("multisell", arrayList);
        for (File file : arrayList) {
            this.parseFile(file);
        }
    }

    protected MultiSellListContainer parseDocument(Document document, int n) {
        MultiSellListContainer multiSellListContainer = new MultiSellListContainer();
        int n2 = 1;
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!"list".equalsIgnoreCase(node.getNodeName())) continue;
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                if ("item".equalsIgnoreCase(node2.getNodeName())) {
                    MultiSellEntry multiSellEntry = this.parseEntry(node2, n);
                    if (multiSellEntry == null) continue;
                    multiSellEntry.setEntryId(n2++);
                    multiSellListContainer.addEntry(multiSellEntry);
                    continue;
                }
                if (!"config".equalsIgnoreCase(node2.getNodeName())) continue;
                multiSellListContainer.setShowAll(XMLUtil.getAttributeBooleanValue(node2, "showall", true));
                multiSellListContainer.setNoTax(XMLUtil.getAttributeBooleanValue(node2, "notax", false));
                multiSellListContainer.setKeepEnchant(XMLUtil.getAttributeBooleanValue(node2, "keepenchanted", false));
                multiSellListContainer.setNoKey(XMLUtil.getAttributeBooleanValue(node2, "nokey", false));
                multiSellListContainer.setNoMerchant(XMLUtil.getAttributeBooleanValue(node2, "no_merchant", false));
                multiSellListContainer.setChancedList(XMLUtil.getAttributeBooleanValue(node2, "is_chanced", false));
            }
        }
        return multiSellListContainer;
    }

    protected MultiSellEntry parseEntry(Node node, int n) {
        Object object;
        MultiSellEntry multiSellEntry = new MultiSellEntry();
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            ItemTemplate itemTemplate;
            long l;
            int n2;
            if (bH.equalsIgnoreCase(node2.getNodeName())) {
                n2 = Integer.parseInt(node2.getAttributes().getNamedItem("id").getNodeValue());
                l = Long.parseLong(node2.getAttributes().getNamedItem("count").getNodeValue());
                object = new MultiSellIngredient(n2, l);
                if (node2.getAttributes().getNamedItem("enchant") != null) {
                    ((MultiSellIngredient)object).setItemEnchant(Integer.parseInt(node2.getAttributes().getNamedItem("enchant").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("mantainIngredient") != null) {
                    ((MultiSellIngredient)object).setMantainIngredient(Boolean.parseBoolean(node2.getAttributes().getNamedItem("mantainIngredient").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("fireAttr") != null) {
                    ((MultiSellIngredient)object).getItemAttributes().setFire(Integer.parseInt(node2.getAttributes().getNamedItem("fireAttr").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("waterAttr") != null) {
                    ((MultiSellIngredient)object).getItemAttributes().setWater(Integer.parseInt(node2.getAttributes().getNamedItem("waterAttr").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("earthAttr") != null) {
                    ((MultiSellIngredient)object).getItemAttributes().setEarth(Integer.parseInt(node2.getAttributes().getNamedItem("earthAttr").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("windAttr") != null) {
                    ((MultiSellIngredient)object).getItemAttributes().setWind(Integer.parseInt(node2.getAttributes().getNamedItem("windAttr").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("holyAttr") != null) {
                    ((MultiSellIngredient)object).getItemAttributes().setHoly(Integer.parseInt(node2.getAttributes().getNamedItem("holyAttr").getNodeValue()));
                }
                if (node2.getAttributes().getNamedItem("unholyAttr") != null) {
                    ((MultiSellIngredient)object).getItemAttributes().setUnholy(Integer.parseInt(node2.getAttributes().getNamedItem("unholyAttr").getNodeValue()));
                }
                multiSellEntry.addIngredient((MultiSellIngredient)object);
                continue;
            }
            if (!bG.equalsIgnoreCase(node2.getNodeName())) continue;
            n2 = Integer.parseInt(node2.getAttributes().getNamedItem("id").getNodeValue());
            l = Long.parseLong(node2.getAttributes().getNamedItem("count").getNodeValue());
            object = new MultiSellIngredient(n2, l);
            if (node2.getAttributes().getNamedItem("enchant") != null) {
                ((MultiSellIngredient)object).setItemEnchant(Integer.parseInt(node2.getAttributes().getNamedItem("enchant").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("chance") != null) {
                ((MultiSellIngredient)object).setItemChance(Integer.parseInt(node2.getAttributes().getNamedItem("chance").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("fireAttr") != null) {
                ((MultiSellIngredient)object).getItemAttributes().setFire(Integer.parseInt(node2.getAttributes().getNamedItem("fireAttr").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("waterAttr") != null) {
                ((MultiSellIngredient)object).getItemAttributes().setWater(Integer.parseInt(node2.getAttributes().getNamedItem("waterAttr").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("earthAttr") != null) {
                ((MultiSellIngredient)object).getItemAttributes().setEarth(Integer.parseInt(node2.getAttributes().getNamedItem("earthAttr").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("windAttr") != null) {
                ((MultiSellIngredient)object).getItemAttributes().setWind(Integer.parseInt(node2.getAttributes().getNamedItem("windAttr").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("holyAttr") != null) {
                ((MultiSellIngredient)object).getItemAttributes().setHoly(Integer.parseInt(node2.getAttributes().getNamedItem("holyAttr").getNodeValue()));
            }
            if (node2.getAttributes().getNamedItem("unholyAttr") != null) {
                ((MultiSellIngredient)object).getItemAttributes().setUnholy(Integer.parseInt(node2.getAttributes().getNamedItem("unholyAttr").getNodeValue()));
            }
            if (!Config.ALT_ALLOW_SHADOW_WEAPONS && n2 > 0 && (itemTemplate = ItemHolder.getInstance().getTemplate(n2)) != null && itemTemplate.isShadowItem() && itemTemplate.isWeapon() && !Config.ALT_ALLOW_SHADOW_WEAPONS) {
                return null;
            }
            multiSellEntry.addProduct((MultiSellIngredient)object);
        }
        if (multiSellEntry.getIngredients().isEmpty() || multiSellEntry.getProduction().isEmpty()) {
            aV.warn("MultiSell [" + n + "] is empty!");
            return null;
        }
        if (multiSellEntry.getIngredients().size() == 1 && multiSellEntry.getIngredients().get(0).getItemId() == 57) {
            long l = 0L;
            for (MultiSellIngredient multiSellIngredient : multiSellEntry.getProduction()) {
                object = ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId());
                if (object == null) {
                    aV.warn("MultiSell [" + n + "] Production [" + multiSellEntry.getProduction().get(0).getItemId() + "] not found!");
                    return null;
                }
                l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(multiSellEntry.getProduction().get(0).getItemCount(), ((ItemTemplate)object).getReferencePrice()));
            }
            if (l > multiSellEntry.getIngredients().get(0).getItemCount() && Config.ALT_MULTISELL_DEBUG) {
                aV.warn("MultiSell [" + n + "] Production [" + multiSellEntry.getEntryId() + "] [" + multiSellEntry.getProduction().get(0).getItemId() + "] price is lower than referenced | " + l + " > " + multiSellEntry.getIngredients().get(0).getItemCount());
            }
        }
        return multiSellEntry;
    }

    private static long[] a(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        String[] stringArray = string.split(":");
        try {
            long l = Integer.parseInt(stringArray[0]);
            long l2 = stringArray.length > 1 ? Long.parseLong(stringArray[1]) : 1L;
            return new long[]{l, l2};
        }
        catch (Exception exception) {
            aV.error("", (Throwable)exception);
            return null;
        }
    }

    public static MultiSellEntry parseEntryFromStr(String string) {
        long[] lArray;
        if (string == null || string.isEmpty()) {
            return null;
        }
        String[] stringArray = string.split("->");
        if (stringArray.length != 2) {
            return null;
        }
        long[] lArray2 = MultiSellHolder.a(stringArray[0]);
        if (lArray2 == null || (lArray = MultiSellHolder.a(stringArray[1])) == null) {
            return null;
        }
        MultiSellEntry multiSellEntry = new MultiSellEntry();
        multiSellEntry.addIngredient(new MultiSellIngredient((int)lArray2[0], lArray2[1]));
        multiSellEntry.addProduct(new MultiSellIngredient((int)lArray[0], lArray[1]));
        return multiSellEntry;
    }

    public void SeparateAndSend(int n, Player player, double d) {
        for (int n2 : Config.ALT_DISABLED_MULTISELL) {
            if (n2 != n) continue;
            player.sendMessage(new CustomMessage("common.MultisellForbidden", player, new Object[0]));
            return;
        }
        MultiSellListContainer multiSellListContainer = this.getList(n);
        if (multiSellListContainer == null) {
            player.sendMessage(new CustomMessage("common.NoMultisell", player, new Object[0]));
            return;
        }
        this.SeparateAndSend(multiSellListContainer, player, d);
    }

    public void SeparateAndSend(MultiSellListContainer multiSellListContainer, Player player, double d) {
        multiSellListContainer = this.a(multiSellListContainer, player, d);
        MultiSellListContainer multiSellListContainer2 = new MultiSellListContainer();
        int n = 1;
        multiSellListContainer2.setListId(multiSellListContainer.getListId());
        multiSellListContainer2.setChancedList(multiSellListContainer.isChancedList());
        player.setMultisell(multiSellListContainer);
        for (MultiSellEntry multiSellEntry : multiSellListContainer.getEntries()) {
            if (multiSellListContainer2.getEntries().size() == Config.MULTISELL_SIZE) {
                player.sendPacket((IStaticPacket)new MultiSellList(multiSellListContainer2, n, 0));
                ++n;
                multiSellListContainer2 = new MultiSellListContainer();
                multiSellListContainer2.setListId(multiSellListContainer.getListId());
            }
            multiSellListContainer2.addEntry(multiSellEntry);
        }
        if (player.isGM()) {
            Functions.sendDebugMessage(player, "MultiSell: " + multiSellListContainer2.getListId() + ".xml");
        }
        player.sendPacket((IStaticPacket)new MultiSellList(multiSellListContainer2, n, 1));
    }

    private MultiSellListContainer a(MultiSellListContainer multiSellListContainer, Player player, double d) {
        MultiSellListContainer multiSellListContainer2 = new MultiSellListContainer();
        multiSellListContainer2.setListId(multiSellListContainer.getListId());
        boolean bl = multiSellListContainer.isKeepEnchant();
        boolean bl2 = multiSellListContainer.isNoTax();
        boolean bl3 = multiSellListContainer.isShowAll();
        boolean bl4 = multiSellListContainer.isNoKey();
        boolean bl5 = multiSellListContainer.isNoMerchant();
        boolean bl6 = multiSellListContainer.isChancedList();
        multiSellListContainer2.setShowAll(bl3);
        multiSellListContainer2.setKeepEnchant(bl);
        multiSellListContainer2.setChancedList(bl6);
        multiSellListContainer2.setNoTax(bl2);
        multiSellListContainer2.setNoKey(bl4);
        multiSellListContainer2.setNoMerchant(bl5);
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        for (MultiSellEntry multiSellEntry : multiSellListContainer.getEntries()) {
            List<MultiSellIngredient> list;
            MultiSellEntry multiSellEntry2 = multiSellEntry.clone();
            if (!bl2 && d > 0.0) {
                double d2 = 0.0;
                long l = 0L;
                list = new ArrayList<MultiSellIngredient>(multiSellEntry2.getIngredients().size() + 1);
                for (MultiSellIngredient multiSellIngredient : multiSellEntry2.getIngredients()) {
                    ItemTemplate itemTemplate;
                    if (multiSellIngredient.getItemId() == 57) {
                        l += multiSellIngredient.getItemCount();
                        d2 += (double)multiSellIngredient.getItemCount() * d;
                        continue;
                    }
                    list.add(multiSellIngredient);
                    if (multiSellIngredient.getItemId() == -200) {
                        d2 += (double)(multiSellIngredient.getItemCount() / 120L * 1000L) * d * 100.0;
                    }
                    if (multiSellIngredient.getItemId() < 1 || !(itemTemplate = ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId())).isStackable()) continue;
                    d2 += (double)(itemTemplate.getReferencePrice() * multiSellIngredient.getItemCount()) * d;
                }
                if ((l = Math.round((double)l + d2)) > 0L) {
                    list.add(new MultiSellIngredient(57, l));
                }
                multiSellEntry2.setTax(Math.round(d2));
                multiSellEntry2.getIngredients().clear();
                multiSellEntry2.getIngredients().addAll(list);
            } else {
                list = multiSellEntry2.getIngredients();
            }
            if (bl3) {
                multiSellListContainer2.ag.add(multiSellEntry2);
                continue;
            }
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            block2: for (MultiSellIngredient multiSellIngredient : list) {
                ItemTemplate itemTemplate;
                ItemTemplate itemTemplate2 = itemTemplate = multiSellIngredient.getItemId() <= 0 ? null : ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId());
                if (multiSellIngredient.getItemId() > 0 && !bl4 && !itemTemplate.isEquipment() || multiSellIngredient.getItemId() == 12374) continue;
                if (multiSellIngredient.getItemId() == -200) {
                    if (arrayList.contains(multiSellIngredient.getItemId()) || player.getClan() == null || (long)player.getClan().getReputationScore() < multiSellIngredient.getItemCount()) continue;
                    arrayList.add(multiSellIngredient.getItemId());
                    continue;
                }
                if (multiSellIngredient.getItemId() == -100) {
                    if (arrayList.contains(multiSellIngredient.getItemId()) || (long)player.getPcBangPoints() < multiSellIngredient.getItemCount()) continue;
                    arrayList.add(multiSellIngredient.getItemId());
                    continue;
                }
                if (multiSellIngredient.getItemId() == -500) {
                    if (arrayList.contains(multiSellIngredient.getItemId()) || (long)player.getRaidBossPoints() < multiSellIngredient.getItemCount()) continue;
                    arrayList.add(multiSellIngredient.getItemId());
                    continue;
                }
                for (ItemInstance itemInstance : itemInstanceArray) {
                    if (itemInstance.getItemId() != multiSellIngredient.getItemId() || !itemInstance.canBeExchanged(player) || arrayList.contains(bl ? (long)multiSellIngredient.getItemId() + (long)multiSellIngredient.getItemEnchant() * 100000L : (long)multiSellIngredient.getItemId()) || itemInstance.getEnchantLevel() < multiSellIngredient.getItemEnchant()) continue;
                    if (itemInstance.isStackable() && itemInstance.getCount() < multiSellIngredient.getItemCount()) continue block2;
                    arrayList.add(bl ? multiSellIngredient.getItemId() + multiSellIngredient.getItemEnchant() * 100000 : multiSellIngredient.getItemId());
                    MultiSellEntry multiSellEntry3 = new MultiSellEntry(bl ? multiSellEntry2.getEntryId() + itemInstance.getEnchantLevel() * 100000 : multiSellEntry2.getEntryId());
                    for (MultiSellIngredient multiSellIngredient2 : multiSellEntry2.getProduction()) {
                        if (bl && itemTemplate.canBeEnchanted(true)) {
                            multiSellIngredient2.setItemEnchant(itemInstance.getEnchantLevel());
                            multiSellIngredient2.setItemAttributes(itemInstance.getAttributes().clone());
                        }
                        multiSellEntry3.addProduct(multiSellIngredient2);
                    }
                    for (MultiSellIngredient multiSellIngredient2 : list) {
                        if (bl && multiSellIngredient2.getItemId() > 0 && ItemHolder.getInstance().getTemplate(multiSellIngredient2.getItemId()).canBeEnchanted(true)) {
                            multiSellIngredient2.setItemEnchant(itemInstance.getEnchantLevel());
                            multiSellIngredient2.setItemAttributes(itemInstance.getAttributes().clone());
                        }
                        multiSellEntry3.addIngredient(multiSellIngredient2);
                    }
                    multiSellListContainer2.ag.add(multiSellEntry3);
                    continue block2;
                }
            }
        }
        return multiSellListContainer2;
    }

    public static class MultiSellListContainer {
        private int fq;
        private boolean aX = true;
        private boolean aY = false;
        private boolean aZ = false;
        private boolean ba = false;
        private boolean bb = false;
        private boolean bc = false;
        private List<MultiSellEntry> ag = new ArrayList<MultiSellEntry>();

        public void setListId(int n) {
            this.fq = n;
        }

        public int getListId() {
            return this.fq;
        }

        public void setShowAll(boolean bl) {
            this.aX = bl;
        }

        public boolean isShowAll() {
            return this.aX;
        }

        public void setNoTax(boolean bl) {
            this.ba = bl;
        }

        public boolean isNoTax() {
            return this.ba;
        }

        public void setNoKey(boolean bl) {
            this.bb = bl;
        }

        public boolean isNoKey() {
            return this.bb;
        }

        public boolean isNoMerchant() {
            return this.bc;
        }

        public void setNoMerchant(boolean bl) {
            this.bc = bl;
        }

        public void setKeepEnchant(boolean bl) {
            this.aY = bl;
        }

        public boolean isKeepEnchant() {
            return this.aY;
        }

        public void setChancedList(boolean bl) {
            this.aZ = bl;
        }

        public boolean isChancedList() {
            return this.aZ;
        }

        public void addEntry(MultiSellEntry multiSellEntry) {
            this.ag.add(multiSellEntry);
        }

        public List<MultiSellEntry> getEntries() {
            return this.ag;
        }

        public boolean isEmpty() {
            return this.ag.isEmpty();
        }
    }
}
