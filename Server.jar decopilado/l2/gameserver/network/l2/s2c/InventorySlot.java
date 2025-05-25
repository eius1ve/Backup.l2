/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.mask.IUpdateTypeComponent;

public final class InventorySlot
extends Enum<InventorySlot>
implements IUpdateTypeComponent {
    public static final /* enum */ InventorySlot UNDER = new InventorySlot(0);
    public static final /* enum */ InventorySlot REAR = new InventorySlot(8);
    public static final /* enum */ InventorySlot LEAR = new InventorySlot(9);
    public static final /* enum */ InventorySlot NECK = new InventorySlot(4);
    public static final /* enum */ InventorySlot RFINGER = new InventorySlot(13);
    public static final /* enum */ InventorySlot LFINGER = new InventorySlot(14);
    public static final /* enum */ InventorySlot HEAD = new InventorySlot(1);
    public static final /* enum */ InventorySlot RHAND = new InventorySlot(5);
    public static final /* enum */ InventorySlot LHAND = new InventorySlot(7);
    public static final /* enum */ InventorySlot GLOVES = new InventorySlot(10);
    public static final /* enum */ InventorySlot CHEST = new InventorySlot(6);
    public static final /* enum */ InventorySlot LEGS = new InventorySlot(11);
    public static final /* enum */ InventorySlot FEET = new InventorySlot(12);
    public static final /* enum */ InventorySlot CLOAK = new InventorySlot(28);
    public static final /* enum */ InventorySlot LRHAND = new InventorySlot(5);
    public static final /* enum */ InventorySlot HAIR2 = new InventorySlot(3);
    public static final /* enum */ InventorySlot HAIR = new InventorySlot(2);
    public static final /* enum */ InventorySlot RBRACELET = new InventorySlot(16);
    public static final /* enum */ InventorySlot LBRACELET = new InventorySlot(15);
    public static final /* enum */ InventorySlot AGATHION1 = new InventorySlot(17);
    public static final /* enum */ InventorySlot AGATHION2 = new InventorySlot(18);
    public static final /* enum */ InventorySlot AGATHION3 = new InventorySlot(19);
    public static final /* enum */ InventorySlot AGATHION4 = new InventorySlot(20);
    public static final /* enum */ InventorySlot AGATHION5 = new InventorySlot(21);
    public static final /* enum */ InventorySlot DECO1 = new InventorySlot(22);
    public static final /* enum */ InventorySlot DECO2 = new InventorySlot(23);
    public static final /* enum */ InventorySlot DECO3 = new InventorySlot(24);
    public static final /* enum */ InventorySlot DECO4 = new InventorySlot(25);
    public static final /* enum */ InventorySlot DECO5 = new InventorySlot(26);
    public static final /* enum */ InventorySlot DECO6 = new InventorySlot(27);
    public static final /* enum */ InventorySlot BELT = new InventorySlot(29);
    public static final /* enum */ InventorySlot BROOCH = new InventorySlot(30);
    public static final /* enum */ InventorySlot BROOCH_JEWEL = new InventorySlot(31);
    public static final /* enum */ InventorySlot BROOCH_JEWEL2 = new InventorySlot(32);
    public static final /* enum */ InventorySlot BROOCH_JEWEL3 = new InventorySlot(33);
    public static final /* enum */ InventorySlot BROOCH_JEWEL4 = new InventorySlot(34);
    public static final /* enum */ InventorySlot BROOCH_JEWEL5 = new InventorySlot(35);
    public static final /* enum */ InventorySlot BROOCH_JEWEL6 = new InventorySlot(36);
    public static final /* enum */ InventorySlot ARTIFACT_BOOK = new InventorySlot(37);
    public static final /* enum */ InventorySlot ARTIFACT1 = new InventorySlot(38);
    public static final /* enum */ InventorySlot ARTIFACT2 = new InventorySlot(39);
    public static final /* enum */ InventorySlot ARTIFACT3 = new InventorySlot(40);
    public static final /* enum */ InventorySlot ARTIFACT4 = new InventorySlot(41);
    public static final /* enum */ InventorySlot ARTIFACT5 = new InventorySlot(42);
    public static final /* enum */ InventorySlot ARTIFACT6 = new InventorySlot(43);
    public static final /* enum */ InventorySlot ARTIFACT7 = new InventorySlot(44);
    public static final /* enum */ InventorySlot ARTIFACT8 = new InventorySlot(45);
    public static final /* enum */ InventorySlot ARTIFACT9 = new InventorySlot(46);
    public static final /* enum */ InventorySlot ARTIFACT10 = new InventorySlot(47);
    public static final /* enum */ InventorySlot ARTIFACT11 = new InventorySlot(48);
    public static final /* enum */ InventorySlot ARTIFACT12 = new InventorySlot(49);
    public static final /* enum */ InventorySlot ARTIFACT13 = new InventorySlot(50);
    public static final /* enum */ InventorySlot ARTIFACT14 = new InventorySlot(51);
    public static final /* enum */ InventorySlot ARTIFACT15 = new InventorySlot(52);
    public static final /* enum */ InventorySlot ARTIFACT16 = new InventorySlot(53);
    public static final /* enum */ InventorySlot ARTIFACT17 = new InventorySlot(54);
    public static final /* enum */ InventorySlot ARTIFACT18 = new InventorySlot(55);
    public static final /* enum */ InventorySlot ARTIFACT19 = new InventorySlot(56);
    public static final /* enum */ InventorySlot ARTIFACT20 = new InventorySlot(57);
    public static final /* enum */ InventorySlot ARTIFACT21 = new InventorySlot(58);
    public static final InventorySlot[] VALUES;
    public static final Map<Integer, InventorySlot> SLOT_2_PAPERDOLL_SLOT;
    private final int zl;
    private static final /* synthetic */ InventorySlot[] a;

    public static InventorySlot[] values() {
        return (InventorySlot[])a.clone();
    }

    public static InventorySlot valueOf(String string) {
        return Enum.valueOf(InventorySlot.class, string);
    }

    private InventorySlot(int n2) {
        this.zl = n2;
    }

    public int getSlot() {
        return this.zl;
    }

    @Override
    public int getMask() {
        return this.ordinal();
    }

    public static InventorySlot[] getInventorySlotByPaperdollSlot(int n, ItemInstance itemInstance) {
        return new InventorySlot[]{SLOT_2_PAPERDOLL_SLOT.get(n)};
    }

    private static /* synthetic */ InventorySlot[] a() {
        return new InventorySlot[]{UNDER, REAR, LEAR, NECK, RFINGER, LFINGER, HEAD, RHAND, LHAND, GLOVES, CHEST, LEGS, FEET, CLOAK, LRHAND, HAIR2, HAIR, RBRACELET, LBRACELET, AGATHION1, AGATHION2, AGATHION3, AGATHION4, AGATHION5, DECO1, DECO2, DECO3, DECO4, DECO5, DECO6, BELT, BROOCH, BROOCH_JEWEL, BROOCH_JEWEL2, BROOCH_JEWEL3, BROOCH_JEWEL4, BROOCH_JEWEL5, BROOCH_JEWEL6, ARTIFACT_BOOK, ARTIFACT1, ARTIFACT2, ARTIFACT3, ARTIFACT4, ARTIFACT5, ARTIFACT6, ARTIFACT7, ARTIFACT8, ARTIFACT9, ARTIFACT10, ARTIFACT11, ARTIFACT12, ARTIFACT13, ARTIFACT14, ARTIFACT15, ARTIFACT16, ARTIFACT17, ARTIFACT18, ARTIFACT19, ARTIFACT20, ARTIFACT21};
    }

    static {
        a = InventorySlot.a();
        VALUES = InventorySlot.values();
        SLOT_2_PAPERDOLL_SLOT = new HashMap<Integer, InventorySlot>();
        for (InventorySlot inventorySlot : VALUES) {
            SLOT_2_PAPERDOLL_SLOT.put(inventorySlot.getSlot(), inventorySlot);
        }
    }
}
