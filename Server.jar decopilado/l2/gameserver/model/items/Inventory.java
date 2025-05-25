/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.items;

import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import l2.commons.listener.ListenerList;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.listener.inventory.OnDisplayListener;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemContainer;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.listeners.StatsListener;
import l2.gameserver.templates.item.EtcItemTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Inventory
extends ItemContainer {
    private static final Logger co = LoggerFactory.getLogger(Inventory.class);
    public static final int PAPERDOLL_UNDER = 0;
    public static final int PAPERDOLL_HEAD = 1;
    public static final int PAPERDOLL_HAIR = 2;
    public static final int PAPERDOLL_DHAIR = 3;
    public static final int PAPERDOLL_NECK = 4;
    public static final int PAPERDOLL_RHAND = 5;
    public static final int PAPERDOLL_CHEST = 6;
    public static final int PAPERDOLL_LHAND = 7;
    public static final int PAPERDOLL_REAR = 8;
    public static final int PAPERDOLL_LEAR = 9;
    public static final int PAPERDOLL_GLOVES = 10;
    public static final int PAPERDOLL_LEGS = 11;
    public static final int PAPERDOLL_FEET = 12;
    public static final int PAPERDOLL_RFINGER = 13;
    public static final int PAPERDOLL_LFINGER = 14;
    public static final int PAPERDOLL_LBRACELET = 15;
    public static final int PAPERDOLL_RBRACELET = 16;
    public static final int PAPERDOLL_AGATHION1 = 17;
    public static final int PAPERDOLL_AGATHION2 = 18;
    public static final int PAPERDOLL_AGATHION3 = 19;
    public static final int PAPERDOLL_AGATHION4 = 20;
    public static final int PAPERDOLL_AGATHION5 = 21;
    public static final int PAPERDOLL_DECO1 = 22;
    public static final int PAPERDOLL_DECO2 = 23;
    public static final int PAPERDOLL_DECO3 = 24;
    public static final int PAPERDOLL_DECO4 = 25;
    public static final int PAPERDOLL_DECO5 = 26;
    public static final int PAPERDOLL_DECO6 = 27;
    public static final int PAPERDOLL_BACK = 28;
    public static final int PAPERDOLL_BELT = 29;
    public static final int PAPERDOLL_BROOCH = 30;
    public static final int PAPERDOLL_BROOCH_JEWEL1 = 31;
    public static final int PAPERDOLL_BROOCH_JEWEL2 = 32;
    public static final int PAPERDOLL_BROOCH_JEWEL3 = 33;
    public static final int PAPERDOLL_BROOCH_JEWEL4 = 34;
    public static final int PAPERDOLL_BROOCH_JEWEL5 = 35;
    public static final int PAPERDOLL_BROOCH_JEWEL6 = 36;
    public static final int PAPERDOLL_ARTIFACT_BOOK = 37;
    public static final int PAPERDOLL_ARTIFACT1 = 38;
    public static final int PAPERDOLL_ARTIFACT2 = 39;
    public static final int PAPERDOLL_ARTIFACT3 = 40;
    public static final int PAPERDOLL_ARTIFACT4 = 41;
    public static final int PAPERDOLL_ARTIFACT5 = 42;
    public static final int PAPERDOLL_ARTIFACT6 = 43;
    public static final int PAPERDOLL_ARTIFACT7 = 44;
    public static final int PAPERDOLL_ARTIFACT8 = 45;
    public static final int PAPERDOLL_ARTIFACT9 = 46;
    public static final int PAPERDOLL_ARTIFACT10 = 47;
    public static final int PAPERDOLL_ARTIFACT11 = 48;
    public static final int PAPERDOLL_ARTIFACT12 = 49;
    public static final int PAPERDOLL_ARTIFACT13 = 50;
    public static final int PAPERDOLL_ARTIFACT14 = 51;
    public static final int PAPERDOLL_ARTIFACT15 = 52;
    public static final int PAPERDOLL_ARTIFACT16 = 53;
    public static final int PAPERDOLL_ARTIFACT17 = 54;
    public static final int PAPERDOLL_ARTIFACT18 = 55;
    public static final int PAPERDOLL_ARTIFACT19 = 56;
    public static final int PAPERDOLL_ARTIFACT20 = 57;
    public static final int PAPERDOLL_ARTIFACT21 = 58;
    public static final int PAPERDOLL_MAX = 59;
    public static final int[] PAPERDOLL_ORDER = new int[]{0, 8, 9, 4, 13, 14, 1, 5, 7, 10, 6, 11, 12, 28, 5, 3, 2, 16, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58};
    public static final int[] PAPERDOLL_VISUAL_ORDER = new int[]{5, 7, 10, 6, 11, 12, 3, 2, 28};
    private static final Map<Long, BiConsumer<Inventory, ItemInstance>> bi = MapUtils.mapBuilder().add(16384L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(7, null);
        inventory.setPaperdollItem(5, (ItemInstance)itemInstance);
    }).add(256L, (inventory, itemInstance) -> {
        ItemInstance itemInstance2 = inventory.getPaperdollItem(5);
        ItemTemplate itemTemplate = itemInstance2 == null ? null : itemInstance2.getTemplate();
        ItemTemplate itemTemplate2 = itemInstance.getTemplate();
        if (itemTemplate2.getItemType() == EtcItemTemplate.EtcItemType.ARROW || itemTemplate2.getItemType() == EtcItemTemplate.EtcItemType.ARROW_QUIVER) {
            if (itemTemplate == null) {
                return;
            }
            if (itemTemplate.getItemType() != WeaponTemplate.WeaponType.BOW) {
                return;
            }
            if (itemTemplate.getCrystalType() != itemTemplate2.getCrystalType()) {
                return;
            }
        } else if (itemTemplate2.getItemType() == EtcItemTemplate.EtcItemType.BAIT) {
            if (itemTemplate == null) {
                return;
            }
            if (itemTemplate.getItemType() != WeaponTemplate.WeaponType.ROD) {
                return;
            }
            if (!inventory.getActor().isPlayer()) {
                return;
            }
            Player player = (Player)inventory.getActor();
            player.setVar("LastLure", String.valueOf(itemInstance.getObjectId()), -1L);
        } else if (itemTemplate != null && itemTemplate.getBodyPart() == 16384L) {
            inventory.setPaperdollItem(5, null);
        }
        inventory.setPaperdollItem(7, (ItemInstance)itemInstance);
    }).add(128L, (inventory, itemInstance) -> inventory.setPaperdollItem(5, (ItemInstance)itemInstance)).addKeys((inventory, itemInstance) -> {
        if (inventory.getPaperdollItem(8) == null) {
            inventory.setPaperdollItem(8, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(9) == null) {
            inventory.setPaperdollItem(9, (ItemInstance)itemInstance);
        } else {
            inventory.setPaperdollItem(9, (ItemInstance)itemInstance);
        }
    }, (Long[])new Long[]{4L, 2L, 6L}).addKeys((inventory, itemInstance) -> {
        if (inventory.getPaperdollItem(13) == null) {
            inventory.setPaperdollItem(13, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(14) == null) {
            inventory.setPaperdollItem(14, (ItemInstance)itemInstance);
        } else {
            inventory.setPaperdollItem(14, (ItemInstance)itemInstance);
        }
    }, (Long[])new Long[]{32L, 16L, 48L}).add(8L, (inventory, itemInstance) -> inventory.setPaperdollItem(4, (ItemInstance)itemInstance)).add(32768L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(11, null);
        inventory.setPaperdollItem(6, (ItemInstance)itemInstance);
    }).add(1024L, (inventory, itemInstance) -> inventory.setPaperdollItem(6, (ItemInstance)itemInstance)).add(2048L, (inventory, itemInstance) -> {
        ItemInstance itemInstance2 = inventory.getPaperdollItem(6);
        if (itemInstance2 != null && itemInstance2.getBodyPart() == 32768L || inventory.getPaperdollBodyPart(6) == 131072L) {
            inventory.setPaperdollItem(6, null);
        }
        inventory.setPaperdollItem(11, (ItemInstance)itemInstance);
    }).add(4096L, (inventory, itemInstance) -> {
        if (inventory.getPaperdollBodyPart(6) == 131072L) {
            inventory.setPaperdollItem(6, null);
        }
        inventory.setPaperdollItem(12, (ItemInstance)itemInstance);
    }).add(512L, (inventory, itemInstance) -> {
        if (inventory.getPaperdollBodyPart(6) == 131072L) {
            inventory.setPaperdollItem(6, null);
        }
        inventory.setPaperdollItem(10, (ItemInstance)itemInstance);
    }).add(64L, (inventory, itemInstance) -> {
        if (inventory.getPaperdollBodyPart(6) == 131072L) {
            inventory.setPaperdollItem(6, null);
        }
        inventory.setPaperdollItem(1, (ItemInstance)itemInstance);
    }).add(65536L, (inventory, itemInstance) -> {
        ItemInstance itemInstance2 = inventory.getPaperdollItem(3);
        if (itemInstance2 != null && itemInstance2.getBodyPart() == 524288L) {
            inventory.setPaperdollItem(3, null);
        }
        inventory.setPaperdollItem(2, (ItemInstance)itemInstance);
    }).add(262144L, (inventory, itemInstance) -> {
        ItemInstance itemInstance2 = inventory.getPaperdollItem(3);
        if (itemInstance2 != null && itemInstance2.getBodyPart() == 524288L) {
            inventory.setPaperdollItem(2, null);
        }
        inventory.setPaperdollItem(3, (ItemInstance)itemInstance);
    }).add(524288L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(2, null);
        inventory.setPaperdollItem(3, (ItemInstance)itemInstance);
    }).add(1L, (inventory, itemInstance) -> inventory.setPaperdollItem(0, (ItemInstance)itemInstance)).add(0x10000000L, (inventory, itemInstance) -> inventory.setPaperdollItem(29, (ItemInstance)itemInstance)).add(8192L, (inventory, itemInstance) -> inventory.setPaperdollItem(28, (ItemInstance)itemInstance)).add(0x200000L, (inventory, itemInstance) -> inventory.setPaperdollItem(15, (ItemInstance)itemInstance)).add(131072L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(11, null);
        inventory.setPaperdollItem(1, null);
        inventory.setPaperdollItem(12, null);
        inventory.setPaperdollItem(10, null);
        inventory.setPaperdollItem(6, (ItemInstance)itemInstance);
    }).add(0x100000L, (inventory, itemInstance) -> inventory.setPaperdollItem(16, (ItemInstance)itemInstance)).add(0x400000L, (inventory, itemInstance) -> {
        if (inventory.getPaperdollItem(22) == null) {
            inventory.setPaperdollItem(22, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(23) == null) {
            inventory.setPaperdollItem(23, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(24) == null) {
            inventory.setPaperdollItem(24, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(25) == null) {
            inventory.setPaperdollItem(25, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(26) == null) {
            inventory.setPaperdollItem(26, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(27) == null) {
            inventory.setPaperdollItem(27, (ItemInstance)itemInstance);
        } else {
            inventory.setPaperdollItem(22, (ItemInstance)itemInstance);
        }
    }).add(0x20000000L, (inventory, itemInstance) -> inventory.setPaperdollItem(30, (ItemInstance)itemInstance)).add(0x40000000L, (inventory, itemInstance) -> {
        if (inventory.getPaperdollItem(31) == null) {
            inventory.setPaperdollItem(31, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(32) == null) {
            inventory.setPaperdollItem(32, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(33) == null) {
            inventory.setPaperdollItem(33, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(34) == null) {
            inventory.setPaperdollItem(34, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(35) == null) {
            inventory.setPaperdollItem(35, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(36) == null) {
            inventory.setPaperdollItem(36, (ItemInstance)itemInstance);
        } else {
            inventory.setPaperdollItem(31, (ItemInstance)itemInstance);
        }
    }).add(0x3000000000L, (inventory, itemInstance) -> {
        if (inventory.getPaperdollItem(17) == null) {
            inventory.setPaperdollItem(17, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(18) == null) {
            inventory.setPaperdollItem(18, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(19) == null) {
            inventory.setPaperdollItem(19, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(20) == null) {
            inventory.setPaperdollItem(20, (ItemInstance)itemInstance);
        } else if (inventory.getPaperdollItem(21) == null) {
            inventory.setPaperdollItem(21, (ItemInstance)itemInstance);
        } else {
            inventory.setPaperdollItem(17, (ItemInstance)itemInstance);
        }
    }).build();
    private static final Map<Long, BiFunction<Inventory, ItemInstance, Integer>> bj = MapUtils.mapBuilder().add(8L, (inventory, itemInstance) -> 4).add(4L, (inventory, itemInstance) -> 9).add(2L, (inventory, itemInstance) -> 8).add(6L, (inventory, itemInstance) -> {
        if (itemInstance == null) {
            return -1;
        }
        if (inventory.getPaperdollItem(9) == itemInstance) {
            return 9;
        }
        if (inventory.getPaperdollItem(8) == itemInstance) {
            return 8;
        }
        return -1;
    }).add(32L, (inventory, itemInstance) -> 14).add(16L, (inventory, itemInstance) -> 13).add(48L, (inventory, itemInstance) -> {
        if (itemInstance == null) {
            return -1;
        }
        if (inventory.getPaperdollItem(14) == itemInstance) {
            return 14;
        }
        if (inventory.getPaperdollItem(13) == itemInstance) {
            return 13;
        }
        return -1;
    }).add(65536L, (inventory, itemInstance) -> 2).add(262144L, (inventory, itemInstance) -> 3).add(524288L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(3, null);
        return 2;
    }).add(64L, (inventory, itemInstance) -> 1).add(128L, (inventory, itemInstance) -> 5).add(256L, (inventory, itemInstance) -> 7).add(512L, (inventory, itemInstance) -> 10).add(2048L, (inventory, itemInstance) -> 11).addKeys((inventory, itemInstance) -> 6, (Long[])new Long[]{1024L, 32768L, 131072L}).add(8192L, (inventory, itemInstance) -> 28).add(4096L, (inventory, itemInstance) -> 12).add(1L, (inventory, itemInstance) -> 0).add(0x10000000L, (inventory, itemInstance) -> 29).add(16384L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(7, null);
        return 5;
    }).add(0x200000L, (inventory, itemInstance) -> 15).add(0x100000L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(22, null);
        inventory.setPaperdollItem(23, null);
        inventory.setPaperdollItem(24, null);
        inventory.setPaperdollItem(25, null);
        inventory.setPaperdollItem(26, null);
        inventory.setPaperdollItem(27, null);
        return 16;
    }).add(0x400000L, (inventory, itemInstance) -> {
        if (itemInstance == null) {
            return -1;
        }
        if (inventory.getPaperdollItem(22) == itemInstance) {
            return 22;
        }
        if (inventory.getPaperdollItem(23) == itemInstance) {
            return 23;
        }
        if (inventory.getPaperdollItem(24) == itemInstance) {
            return 24;
        }
        if (inventory.getPaperdollItem(25) == itemInstance) {
            return 25;
        }
        if (inventory.getPaperdollItem(26) == itemInstance) {
            return 26;
        }
        if (inventory.getPaperdollItem(27) == itemInstance) {
            return 27;
        }
        return -1;
    }).add(0x20000000L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(31, null);
        inventory.setPaperdollItem(32, null);
        inventory.setPaperdollItem(33, null);
        inventory.setPaperdollItem(34, null);
        inventory.setPaperdollItem(35, null);
        inventory.setPaperdollItem(36, null);
        return 30;
    }).add(0x40000000L, (inventory, itemInstance) -> {
        if (itemInstance == null) {
            return -1;
        }
        if (inventory.getPaperdollItem(31) == itemInstance) {
            return 31;
        }
        if (inventory.getPaperdollItem(32) == itemInstance) {
            return 32;
        }
        if (inventory.getPaperdollItem(33) == itemInstance) {
            return 33;
        }
        if (inventory.getPaperdollItem(34) == itemInstance) {
            return 34;
        }
        if (inventory.getPaperdollItem(35) == itemInstance) {
            return 35;
        }
        if (inventory.getPaperdollItem(36) == itemInstance) {
            return 36;
        }
        return -1;
    }).add(0x200000L, (inventory, itemInstance) -> {
        inventory.setPaperdollItem(17, null);
        inventory.setPaperdollItem(18, null);
        inventory.setPaperdollItem(19, null);
        inventory.setPaperdollItem(20, null);
        inventory.setPaperdollItem(21, null);
        return 15;
    }).add(0x3000000000L, (inventory, itemInstance) -> {
        if (itemInstance == null) {
            return -1;
        }
        if (inventory.getPaperdollItem(17) == itemInstance) {
            return 17;
        }
        if (inventory.getPaperdollItem(18) == itemInstance) {
            return 18;
        }
        if (inventory.getPaperdollItem(19) == itemInstance) {
            return 19;
        }
        if (inventory.getPaperdollItem(20) == itemInstance) {
            return 20;
        }
        if (inventory.getPaperdollItem(21) == itemInstance) {
            return 21;
        }
        return -1;
    }).build();
    protected final int _ownerId;
    protected final ItemInstance[] _paperdoll = new ItemInstance[59];
    protected final InventoryListenerList _listeners = new InventoryListenerList();
    protected OnDisplayListener _onDisplayListener;
    protected int _totalWeight;
    protected long _wearedMask;

    protected Inventory(int n) {
        this._ownerId = n;
        this.addListener(StatsListener.getInstance());
    }

    public abstract Playable getActor();

    protected abstract ItemInstance.ItemLocation getBaseLocation();

    protected abstract ItemInstance.ItemLocation getEquipLocation();

    public int getOwnerId() {
        return this._ownerId;
    }

    protected void onRestoreItem(ItemInstance itemInstance) {
        this._totalWeight = (int)((long)this._totalWeight + (long)itemInstance.getTemplate().getWeight() * itemInstance.getCount());
    }

    @Override
    protected void onAddItem(ItemInstance itemInstance) {
        itemInstance.setOwnerId(this.getOwnerId());
        itemInstance.setLocation(this.getBaseLocation());
        itemInstance.setLocData(this.x());
        this.sendAddItem(itemInstance);
        this.refreshWeight();
        itemInstance.save();
    }

    @Override
    protected void onModifyItem(ItemInstance itemInstance) {
        this.sendModifyItem(itemInstance);
        this.refreshWeight();
    }

    @Override
    protected void onRemoveItem(ItemInstance itemInstance) {
        if (itemInstance.isEquipped()) {
            this.unEquipItem(itemInstance);
        }
        this.sendRemoveItem(itemInstance);
        itemInstance.setLocData(-1);
        itemInstance.save();
        this.refreshWeight();
    }

    @Override
    protected void onDestroyItem(ItemInstance itemInstance) {
        itemInstance.setCount(0L);
        itemInstance.delete();
    }

    protected void onEquip(int n, ItemInstance itemInstance) {
        this._listeners.onEquip(n, itemInstance);
        itemInstance.setLocation(this.getEquipLocation());
        itemInstance.setLocData(n);
        itemInstance.setEquipped(true);
        this.sendModifyItem(itemInstance);
        this._wearedMask |= itemInstance.getTemplate().getItemMask();
        itemInstance.save();
    }

    protected void onUnequip(int n, ItemInstance itemInstance) {
        itemInstance.setLocation(this.getBaseLocation());
        itemInstance.setLocData(this.x());
        itemInstance.setEquipped(false);
        itemInstance.setChargedSpiritshot(0);
        itemInstance.setChargedSoulshot(0);
        this.sendModifyItem(itemInstance);
        this._wearedMask &= itemInstance.getTemplate().getItemMask() ^ 0xFFFFFFFFFFFFFFFFL;
        this._listeners.onUnequip(n, itemInstance);
        itemInstance.save();
    }

    private int x() {
        int n = 0;
        block0: for (n = 0; n < this._items.size(); ++n) {
            for (int i = 0; i < this._items.size(); ++i) {
                ItemInstance itemInstance = (ItemInstance)this._items.get(i);
                if (!itemInstance.isEquipped() && !itemInstance.getTemplate().isQuest() && itemInstance.getEquipSlot() == n) continue block0;
            }
        }
        return n;
    }

    public ItemInstance getPaperdollItem(int n) {
        return this._paperdoll[n];
    }

    public ItemInstance[] getPaperdollItems() {
        return this._paperdoll;
    }

    public long getPaperdollBodyPart(int n) {
        ItemInstance itemInstance = this.getPaperdollItem(n);
        if (itemInstance != null) {
            return itemInstance.getBodyPart();
        }
        if (n == 2 && (itemInstance = this._paperdoll[3]) != null) {
            return itemInstance.getBodyPart();
        }
        return 0L;
    }

    public int getPaperdollItemId(int n) {
        Integer n2;
        ItemInstance itemInstance = this.getPaperdollItem(n);
        if (itemInstance != null) {
            Integer n3;
            if (this.getOnDisplayListener() != null && (n3 = this.getOnDisplayListener().onDisplay(n, itemInstance, this.getActor())) != null) {
                return n3;
            }
            return itemInstance.getVisibleItemId();
        }
        if (n == 2 && (itemInstance = this._paperdoll[3]) != null) {
            return itemInstance.getVisibleItemId();
        }
        if (this.getOnDisplayListener() != null && (n2 = this.getOnDisplayListener().onDisplay(n, null, this.getActor())) != null) {
            return n2;
        }
        return 0;
    }

    public int getPaperdollObjectId(int n) {
        ItemInstance itemInstance = this._paperdoll[n];
        if (itemInstance != null) {
            return itemInstance.getObjectId();
        }
        if (n == 2 && (itemInstance = this._paperdoll[3]) != null) {
            return itemInstance.getObjectId();
        }
        return 0;
    }

    public void addListener(OnEquipListener onEquipListener) {
        this._listeners.add(onEquipListener);
    }

    public void removeListener(OnEquipListener onEquipListener) {
        this._listeners.remove(onEquipListener);
    }

    public InventoryListenerList getListeners() {
        return this._listeners;
    }

    public OnDisplayListener getOnDisplayListener() {
        return this._onDisplayListener;
    }

    public void setOnDisplayListener(OnDisplayListener onDisplayListener) {
        this._onDisplayListener = onDisplayListener;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ItemInstance setPaperdollItem(int n, ItemInstance itemInstance) {
        ItemInstance itemInstance2;
        this.writeLock();
        try {
            itemInstance2 = this._paperdoll[n];
            if (itemInstance2 != itemInstance) {
                if (itemInstance2 != null) {
                    this._paperdoll[n] = null;
                    this.onUnequip(n, itemInstance2);
                }
                if (itemInstance != null) {
                    this._paperdoll[n] = itemInstance;
                    this.onEquip(n, itemInstance);
                }
            }
        }
        finally {
            this.writeUnlock();
        }
        return itemInstance2;
    }

    public long getWearedMask() {
        return this._wearedMask;
    }

    public void unEquipItem(ItemInstance itemInstance) {
        if (itemInstance.isEquipped()) {
            this.a(itemInstance.getBodyPart(), itemInstance);
        }
    }

    public void unEquipItemInBodySlot(long l) {
        this.a(l, null);
    }

    private void a(long l, ItemInstance itemInstance) {
        BiFunction<Inventory, ItemInstance, Integer> biFunction = bj.get(l);
        if (biFunction == null) {
            co.warn("Requested invalid body slot: " + l + ", Item: " + itemInstance + ", ownerId: '" + this.getOwnerId() + "'");
            return;
        }
        int n = biFunction.apply(this, itemInstance);
        if (n >= 0) {
            this.setPaperdollItem(n, null);
        }
    }

    public void equipItem(ItemInstance itemInstance) {
        double d = this.getActor().getCurrentHp();
        double d2 = this.getActor().getCurrentMp();
        double d3 = this.getActor().getCurrentCp();
        long l = itemInstance.getBodyPart();
        BiConsumer<Inventory, ItemInstance> biConsumer = bi.get(l);
        if (biConsumer == null) {
            co.warn("unknown body slot:" + l + " for item id: " + itemInstance.getItemId());
            return;
        }
        biConsumer.accept(this, itemInstance);
        this.getActor().setCurrentHp(d, false);
        this.getActor().setCurrentMp(d2);
        this.getActor().setCurrentCp(d3);
        if (this.getActor().isPlayer()) {
            ((Player)this.getActor()).autoShot();
        }
    }

    protected abstract void sendAddItem(ItemInstance var1);

    protected abstract void sendModifyItem(ItemInstance var1);

    protected abstract void sendRemoveItem(ItemInstance var1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void refreshWeight() {
        int n = 0;
        this.readLock();
        try {
            for (int i = 0; i < this._items.size(); ++i) {
                ItemInstance itemInstance = (ItemInstance)this._items.get(i);
                n = (int)((long)n + (long)itemInstance.getTemplate().getWeight() * itemInstance.getCount());
            }
        }
        finally {
            this.readUnlock();
        }
        if (this._totalWeight == n) {
            return;
        }
        this._totalWeight = n;
        this.onRefreshWeight();
    }

    protected abstract void onRefreshWeight();

    public int getTotalWeight() {
        return this._totalWeight;
    }

    public boolean validateCapacity(ItemInstance itemInstance) {
        long l = 0L;
        if (!itemInstance.isStackable() || this.getItemByItemId(itemInstance.getItemId()) == null) {
            ++l;
        }
        return this.validateCapacity(l);
    }

    public boolean validateCapacity(int n, long l) {
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        return this.validateCapacity(itemTemplate, l);
    }

    public boolean validateCapacity(ItemTemplate itemTemplate, long l) {
        return this.validateCapacity(itemTemplate.isStackable() ? (long)(this.getItemByItemId(itemTemplate.getItemId()) == null ? 1 : 0) : l);
    }

    public boolean validateCapacity(long l) {
        if (l == 0L) {
            return true;
        }
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            return false;
        }
        if (this.getSize() + (int)l < 0) {
            return false;
        }
        return (long)this.getSize() + l <= (long)this.getActor().getInventoryLimit();
    }

    public boolean validateWeight(ItemInstance itemInstance) {
        long l = (long)itemInstance.getTemplate().getWeight() * itemInstance.getCount();
        return this.validateWeight(l);
    }

    public boolean validateWeight(int n, long l) {
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        return this.validateWeight(itemTemplate, l);
    }

    public boolean validateWeight(ItemTemplate itemTemplate, long l) {
        long l2 = (long)itemTemplate.getWeight() * l;
        return this.validateWeight(l2);
    }

    public boolean validateWeight(long l) {
        if (l == 0L) {
            return true;
        }
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            return false;
        }
        if (this.getTotalWeight() + (int)l < 0) {
            return false;
        }
        return (long)this.getTotalWeight() + l <= (long)this.getActor().getMaxLoad();
    }

    public abstract void restore();

    public abstract void store();

    @Override
    public int getSize() {
        return super.getSize() - this.getQuestSize();
    }

    public int getQuestSize() {
        int n = 0;
        for (ItemInstance itemInstance : this.getItems()) {
            if (!itemInstance.getTemplate().isQuest()) continue;
            ++n;
        }
        return n;
    }

    public class InventoryListenerList
    extends ListenerList<Playable> {
        public void onEquip(int n, ItemInstance itemInstance) {
            this.forEachListener(OnEquipListener.class, onEquipListener -> onEquipListener.onEquip(n, itemInstance, Inventory.this.getActor()));
        }

        public void onUnequip(int n, ItemInstance itemInstance) {
            this.forEachListener(OnEquipListener.class, onEquipListener -> onEquipListener.onUnequip(n, itemInstance, Inventory.this.getActor()));
        }
    }

    public static class ItemOrderComparator
    implements Comparator<ItemInstance> {
        private static final Comparator<ItemInstance> d = new ItemOrderComparator();

        public static final Comparator<ItemInstance> getInstance() {
            return d;
        }

        @Override
        public int compare(ItemInstance itemInstance, ItemInstance itemInstance2) {
            if (itemInstance == null || itemInstance2 == null) {
                return 0;
            }
            return itemInstance.getLocData() - itemInstance2.getLocData();
        }
    }
}
