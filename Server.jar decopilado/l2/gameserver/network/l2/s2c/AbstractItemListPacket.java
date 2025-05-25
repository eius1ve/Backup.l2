/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public abstract class AbstractItemListPacket
extends L2GameServerPacket {
    public static final int AUGMENTATION = 1;
    public static final int ATTRIBUTES = 2;
    public static final int ENCHANT_OPTIONS = 4;
    public static final int VISUAL = 8;
    public static final int SOUL_CRYSTAL = 16;

    protected void writeItemInfo(ItemInfo itemInfo) {
        this.writeItemInfo(itemInfo, itemInfo.getCount());
    }

    protected void writeItemInfo(ItemInfo itemInfo, long l) {
        int n = this.a(itemInfo);
        this.writeC(n);
        this.writeD(itemInfo.getObjectId());
        this.writeD(itemInfo.getItemId());
        this.writeC(itemInfo.getEquipSlot());
        this.writeQ(l);
        this.writeC(itemInfo.getItem().getType2ForPackets());
        this.writeC(itemInfo.getCustomType1());
        this.writeH(itemInfo.isEquipped() ? 1 : 0);
        this.writeQ(itemInfo.getItem().getBodyPart());
        this.writeC(itemInfo.getEnchantLevel());
        this.writeC(itemInfo.getCustomType2());
        this.writeD(itemInfo.getShadowLifeTime());
        this.writeD(itemInfo.getTemporalLifeTime());
        this.writeC(1);
        this.writeC(0);
        this.writeC(0);
        if (this.d(n, 1)) {
            this.writeD(itemInfo.getVariationStat1());
            this.writeD(itemInfo.getVariationStat2());
        }
        if (this.d(n, 2)) {
            this.writeH(itemInfo.getAttackElement());
            this.writeH(itemInfo.getAttackElementValue());
            this.writeH(itemInfo.getDefenceFire());
            this.writeH(itemInfo.getDefenceWater());
            this.writeH(itemInfo.getDefenceWind());
            this.writeH(itemInfo.getDefenceEarth());
            this.writeH(itemInfo.getDefenceHoly());
            this.writeH(itemInfo.getDefenceUnholy());
        }
        if (this.d(n, 4)) {
            int[] nArray = itemInfo.getEnchantOptions();
            this.writeD(nArray[0]);
            this.writeD(nArray[1]);
            this.writeD(nArray[2]);
        }
        if (this.d(n, 8)) {
            this.writeD(0);
        }
        if (this.d(n, 16)) {
            int n2 = itemInfo.getEnsoulSlotN1();
            int n3 = itemInfo.getEnsoulSlotN2();
            if (n2 > 0 && n3 > 0) {
                this.writeC(2);
                this.writeD(n2);
                this.writeD(n3);
            } else if (n2 > 0) {
                this.writeC(1);
                this.writeD(n2);
            } else if (n3 > 0) {
                this.writeC(1);
                this.writeD(n3);
            } else {
                this.writeC(0);
            }
            if (itemInfo.getEnsoulSlotBm() > 0) {
                this.writeC(1);
                this.writeD(itemInfo.getEnsoulSlotBm());
            } else {
                this.writeC(0);
            }
        }
    }

    private boolean d(int n, int n2) {
        return (n & n2) == n2;
    }

    private int a(ItemInfo itemInfo) {
        int n = 0;
        if (itemInfo.isAugmented()) {
            n |= 1;
        }
        if (itemInfo.haveAttributes()) {
            n |= 2;
        }
        if (itemInfo.haveEnchantOptions()) {
            n |= 4;
        }
        if (itemInfo.haveEnsoul()) {
            n |= 0x10;
        }
        return n;
    }
}
