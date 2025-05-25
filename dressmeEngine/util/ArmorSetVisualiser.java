/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.ArmorSetsHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.ArmorSet
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.templates.item.ItemTemplate
 */
package dressmeEngine.util;

import dressmeEngine.data.DressMeArmorData;
import dressmeEngine.xml.dataHolder.DressMeArmorHolder;
import l2.gameserver.data.xml.holder.ArmorSetsHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.ArmorSet;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.templates.item.ItemTemplate;

public class ArmorSetVisualiser {
    protected static ArmorSetVisualiser Y;

    public static ArmorSetVisualiser getInstance() {
        if (Y == null) {
            Y = new ArmorSetVisualiser();
        }
        return Y;
    }

    public static boolean A(Player Player2, int i, int i2) {
        ArmorSet setByItemId = ArmorSetsHolder.getInstance().getArmorSetByChestItemId(i);
        boolean z = false;
        if (setByItemId == null) {
            return false;
        }
        return z;
    }

    public static boolean hasArmorEquipped(Player Player2) {
        PcInventory inventory = Player2.getInventory();
        ItemInstance paperdollItem = inventory.getPaperdollItem(11);
        ItemInstance paperdollItem2 = inventory.getPaperdollItem(6);
        ItemInstance paperdollItem3 = inventory.getPaperdollItem(10);
        ItemInstance paperdollItem4 = inventory.getPaperdollItem(12);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        if (paperdollItem != null) {
            i = paperdollItem.getItemId();
        }
        if (paperdollItem3 != null) {
            i2 = paperdollItem3.getItemId();
        }
        if (paperdollItem4 != null) {
            i3 = paperdollItem4.getItemId();
        }
        if (paperdollItem2 != null) {
            i4 = paperdollItem2.getItemId();
        }
        return i4 != 0 && i != 0 && i2 != 0 && i3 != 0;
    }

    public static boolean A(int i) {
        switch (i) {
            case 1: 
            case 6: 
            case 10: 
            case 11: 
            case 12: {
                return true;
            }
        }
        return false;
    }

    public static boolean isSuitEquipped(int i, Player Player2) {
        ItemInstance paperdollItem;
        return i == 6 && (paperdollItem = Player2.getInventory().getPaperdollItem(6)) != null && paperdollItem.getVisibleItemId() != 0 && DressMeArmorHolder.getInstance().getArmorByPartId(paperdollItem.getVisibleItemId()) != null;
    }

    public static int[] getRightDressForChest(Player Player2) {
        ItemInstance paperdollItem = Player2.getInventory().getPaperdollItem(10);
        ItemInstance paperdollItem2 = Player2.getInventory().getPaperdollItem(6);
        ItemInstance paperdollItem3 = Player2.getInventory().getPaperdollItem(11);
        ItemInstance paperdollItem4 = Player2.getInventory().getPaperdollItem(12);
        DressMeArmorData armorByPartId = DressMeArmorHolder.getInstance().getArmorByPartId(paperdollItem2.getVisibleItemId());
        if (armorByPartId == null) {
            int[] iArr = new int[]{paperdollItem == null ? 0 : paperdollItem.getVisibleItemId(), paperdollItem2 == null ? 0 : paperdollItem2.getVisibleItemId(), paperdollItem3 == null ? 0 : paperdollItem3.getVisibleItemId(), paperdollItem4 == null ? 0 : paperdollItem4.getVisibleItemId()};
            return iArr;
        }
        if (paperdollItem2.getVisibleItemId() == 59999) {
            return new int[4];
        }
        if (armorByPartId.M()) {
            int[] nArray = new int[4];
            nArray[1] = armorByPartId.H();
            return nArray;
        }
        int[] iArr2 = new int[]{armorByPartId.K(), armorByPartId.H(), armorByPartId.L() == -1 ? 0 : armorByPartId.L(), armorByPartId.J()};
        return iArr2;
    }

    public static boolean isChest(int i) {
        ItemTemplate template = ItemHolder.getInstance().getTemplate(i);
        if (template == null) {
            return false;
        }
        return template.getBodyPart() == 1024L || template.getBodyPart() == 32768L || template.getBodyPart() == 131072L;
    }

    public static boolean isOtherPieces(int i) {
        ItemTemplate template = ItemHolder.getInstance().getTemplate(i);
        if (template == null) {
            return false;
        }
        switch ((int)template.getBodyPart()) {
            case 512: 
            case 1024: 
            case 2048: 
            case 4096: 
            case 32768: 
            case 131072: {
                return true;
            }
        }
        return false;
    }

    public static int getIntForPcInventory(int i, int i2) {
        ItemTemplate template;
        DressMeArmorData armorByPartId = DressMeArmorHolder.getInstance().getArmorByPartId(i2);
        if (armorByPartId != null && (template = ItemHolder.getInstance().getTemplate(i)) != null) {
            switch ((int)template.getBodyPart()) {
                case 512: {
                    return armorByPartId.K();
                }
                case 1024: 
                case 32768: 
                case 131072: {
                    return armorByPartId.H();
                }
                case 2048: {
                    return armorByPartId.L();
                }
                case 4096: {
                    return armorByPartId.J();
                }
            }
            return i;
        }
        return i;
    }

    public static int getIntForPcInventorySLOT(int i, int i2) {
        DressMeArmorData armorByPartId = DressMeArmorHolder.getInstance().getArmorByPartId(i2);
        if (armorByPartId == null) {
            return 0;
        }
        switch (i) {
            case 10: {
                return armorByPartId.K();
            }
            case 11: {
                return armorByPartId.L();
            }
            case 12: {
                return armorByPartId.J();
            }
            case 1024: 
            case 32768: 
            case 131072: {
                return armorByPartId.H();
            }
        }
        return 0;
    }
}
