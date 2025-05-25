/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.templates.item.ItemTemplate;

public class ItemInfo {
    private int nR;
    private int nS;
    private int nT;
    private int objectId;
    private int itemId;
    private long count;
    private int nU;
    private int nV;
    private boolean dF;
    private int nW;
    private int nX;
    private int nY;
    private int nZ;
    private int oa;
    private int ob;
    private int oc;
    private int od;
    private int oe = Element.NONE.getId();
    private int of;
    private int og;
    private int oh;
    private int oi;
    private int oj;
    private int ok;
    private int ol;
    private int om;
    private int on;
    private int[] aL = ItemInstance.EMPTY_ENCHANT_OPTIONS;
    private ItemTemplate c;

    public ItemInfo() {
    }

    public ItemInfo(ItemInstance itemInstance) {
        this.setOwnerId(itemInstance.getOwnerId());
        this.setObjectId(itemInstance.getObjectId());
        this.setItemId(itemInstance.getItemId());
        this.setCount(itemInstance.getCount());
        this.setCustomType1(itemInstance.getBlessed());
        this.setEquipped(itemInstance.isEquipped());
        this.setEnchantLevel(itemInstance.getEnchantLevel());
        this.setCustomType2(itemInstance.getDamaged());
        this.setVariationStat1(itemInstance.getVariationStat1());
        this.setVariationStat2(itemInstance.getVariationStat2());
        this.setEnsoulSlotN1(itemInstance.getEnsoulSlotN1());
        this.setEnsoulSlotN2(itemInstance.getEnsoulSlotN2());
        this.setEnsoulSlotBm(itemInstance.getEnsoulSlotBm());
        this.setShadowLifeTime(itemInstance.getDuration());
        this.setAttackElement(itemInstance.getAttackElement().getId());
        this.setAttackElementValue(itemInstance.getAttackElementValue());
        this.setDefenceFire(itemInstance.getDefenceFire());
        this.setDefenceWater(itemInstance.getDefenceWater());
        this.setDefenceWind(itemInstance.getDefenceWind());
        this.setDefenceEarth(itemInstance.getDefenceEarth());
        this.setDefenceHoly(itemInstance.getDefenceHoly());
        this.setDefenceUnholy(itemInstance.getDefenceUnholy());
        this.setEquipSlot(itemInstance.getEquipSlot());
        this.setTemporalLifeTime(itemInstance.getPeriod());
        this.setEnchantOptions(itemInstance.getEnchantOptions());
    }

    public ItemTemplate getItem() {
        return this.c;
    }

    public void setOwnerId(int n) {
        this.nR = n;
    }

    public void setLastChange(int n) {
        this.nS = n;
    }

    public void setType1(int n) {
        this.nT = n;
    }

    public void setObjectId(int n) {
        this.objectId = n;
    }

    public void setItemId(int n) {
        this.itemId = n;
        this.c = n > 0 ? ItemHolder.getInstance().getTemplate(this.getItemId()) : null;
        if (this.c != null) {
            this.setType1(this.c.getType1());
            this.setType2(this.c.getType2ForPackets());
        }
    }

    public void setCount(long l) {
        this.count = l;
    }

    public void setType2(int n) {
        this.nU = n;
    }

    public void setCustomType1(int n) {
        this.nV = n;
    }

    public void setEquipped(boolean bl) {
        this.dF = bl;
    }

    public void setEnchantLevel(int n) {
        this.nW = n;
    }

    public void setCustomType2(int n) {
        this.nX = n;
    }

    public void setVariationStat1(int n) {
        this.nY = n;
    }

    public void setVariationStat2(int n) {
        this.nZ = n;
    }

    public int getEnsoulSlotN1() {
        return this.oa;
    }

    public ItemInfo setEnsoulSlotN1(int n) {
        this.oa = n;
        return this;
    }

    public int getEnsoulSlotN2() {
        return this.ob;
    }

    public ItemInfo setEnsoulSlotN2(int n) {
        this.ob = n;
        return this;
    }

    public int getEnsoulSlotBm() {
        return this.oc;
    }

    public ItemInfo setEnsoulSlotBm(int n) {
        this.oc = n;
        return this;
    }

    public boolean haveEnsoul() {
        return this.getEnsoulSlotN1() > 0 || this.getEnsoulSlotN2() > 0 || this.getEnsoulSlotBm() > 0;
    }

    public void setShadowLifeTime(int n) {
        this.od = n;
    }

    public void setAttackElement(int n) {
        this.oe = n;
    }

    public void setAttackElementValue(int n) {
        this.of = n;
    }

    public void setDefenceFire(int n) {
        this.og = n;
    }

    public void setDefenceWater(int n) {
        this.oh = n;
    }

    public void setDefenceWind(int n) {
        this.oi = n;
    }

    public void setDefenceEarth(int n) {
        this.oj = n;
    }

    public void setDefenceHoly(int n) {
        this.ok = n;
    }

    public void setDefenceUnholy(int n) {
        this.ol = n;
    }

    public void setEquipSlot(int n) {
        this.om = n;
    }

    public void setTemporalLifeTime(int n) {
        this.on = n;
    }

    public int getOwnerId() {
        return this.nR;
    }

    public int getLastChange() {
        return this.nS;
    }

    public int getType1() {
        return this.nT;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public long getCount() {
        return this.count;
    }

    public int getType2() {
        return this.nU;
    }

    public int getCustomType1() {
        return this.nV;
    }

    public boolean isEquipped() {
        return this.dF;
    }

    public int getEnchantLevel() {
        return this.nW;
    }

    public int getVariationStat1() {
        return this.nY;
    }

    public int getVariationStat2() {
        return this.nZ;
    }

    public boolean isAugmented() {
        return this.getVariationStat1() != 0 || this.getVariationStat2() != 0;
    }

    public int getShadowLifeTime() {
        return this.od;
    }

    public int getCustomType2() {
        return this.nX;
    }

    public int getAttackElement() {
        return this.oe;
    }

    public int getAttackElementValue() {
        return this.of;
    }

    public int getDefenceFire() {
        return this.og;
    }

    public int getDefenceWater() {
        return this.oh;
    }

    public int getDefenceWind() {
        return this.oi;
    }

    public int getDefenceEarth() {
        return this.oj;
    }

    public int getDefenceHoly() {
        return this.ok;
    }

    public int getDefenceUnholy() {
        return this.ol;
    }

    public boolean haveAttributes() {
        return this.getAttackElement() != Element.NONE.getId() && this.getAttackElementValue() > 0 || this.getDefenceFire() + this.getDefenceWater() + this.getDefenceWind() + this.getDefenceEarth() + this.getDefenceHoly() + this.getDefenceUnholy() > 0;
    }

    public int getEquipSlot() {
        return this.om;
    }

    public int getTemporalLifeTime() {
        return this.on;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        if (this.getObjectId() == 0) {
            return this.getItemId() == ((ItemInfo)object).getItemId();
        }
        return this.getObjectId() == ((ItemInfo)object).getObjectId();
    }

    public int[] getEnchantOptions() {
        return this.aL;
    }

    public boolean haveEnchantOptions() {
        return this.aL != ItemInstance.EMPTY_ENCHANT_OPTIONS && (this.aL[0] != 0 || this.aL[1] != 0 || this.aL[2] != 0);
    }

    public void setEnchantOptions(int[] nArray) {
        this.aL = nArray;
    }
}
