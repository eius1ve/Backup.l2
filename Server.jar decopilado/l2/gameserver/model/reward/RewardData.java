/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.model.reward;

import java.util.ArrayList;
import java.util.List;
import l2.commons.math.SafeMath;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.reward.RewardItem;
import l2.gameserver.templates.item.ItemTemplate;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RewardData
implements Cloneable {
    private ItemTemplate a;
    private boolean dI;
    private boolean dJ;
    private long cM;
    private long cN;
    private double a;
    private double R;
    private int pr = 0;
    private int ps = 0;

    public RewardData(int n) {
        this.a = ItemHolder.getInstance().getTemplate(n);
        this.setNotRate(this.a.isArrow() || Config.NO_RATE_EQUIPMENT && this.a.isEquipment() || Config.NO_RATE_KEY_MATERIAL && this.a.isKeyMatherial() || Config.NO_RATE_RECIPES && this.a.isRecipe(), ArrayUtils.contains((int[])Config.NO_RATE_ITEMS, (int)n));
    }

    public RewardData(int n, long l, long l2, double d) {
        this(n);
        this.cM = l;
        this.cN = l2;
        this.a = d;
    }

    public boolean notRate() {
        return this.dI;
    }

    public boolean onePassOnly() {
        return this.dJ;
    }

    public void setNotRate(boolean bl, boolean bl2) {
        this.dI = bl || bl2;
        this.dJ = bl2;
    }

    public int getItemId() {
        return this.a.getItemId();
    }

    public ItemTemplate getItem() {
        return this.a;
    }

    public long getMinDrop() {
        return this.cM;
    }

    public void setMinDrop(long l) {
        this.cM = l;
    }

    public long getMaxDrop() {
        return this.cN;
    }

    public void setMaxDrop(long l) {
        this.cN = l;
    }

    public double getChance() {
        return this.a;
    }

    public void setChance(double d) {
        this.a = d;
    }

    public double getChanceInGroup() {
        return this.R;
    }

    public void setChanceInGroup(double d) {
        this.R = d;
    }

    public int getEnchantMin() {
        return this.pr;
    }

    public void setEnchantMin(int n) {
        this.pr = n;
    }

    public int getEnchantMax() {
        return this.ps;
    }

    public void setEnchantMax(int n) {
        this.ps = n;
    }

    public String toString() {
        return "ItemID: " + this.getItem() + " Min: " + this.getMinDrop() + " Max: " + this.getMaxDrop() + " Chance: " + this.getChance() / 10000.0 + "%";
    }

    public RewardData clone() {
        return new RewardData(this.getItemId(), this.getMinDrop(), this.getMaxDrop(), this.getChance());
    }

    public boolean equals(Object object) {
        if (object instanceof RewardData) {
            RewardData rewardData = (RewardData)object;
            return rewardData.getItemId() == this.getItemId();
        }
        return false;
    }

    public List<RewardItem> roll(Player player, double d) {
        double d2 = 1.0;
        d2 = this.a.isAdena() ? Config.RATE_DROP_ADENA * player.getRateAdena() : Config.RATE_DROP_ITEMS * (player != null ? player.getRateItems() : 1.0);
        return this.roll(d2 * d);
    }

    public List<RewardItem> roll(double d) {
        double d2 = Math.ceil(d);
        ArrayList<RewardItem> arrayList = new ArrayList<RewardItem>(1);
        RewardItem rewardItem = null;
        int n = 0;
        while ((double)n < d2) {
            if ((double)Rnd.get(1000000) <= this.a * Math.min(d - (double)n, 1.0)) {
                long l = this.getMinDrop() >= this.getMaxDrop() ? this.getMinDrop() : Rnd.get(this.getMinDrop(), this.getMaxDrop());
                if (rewardItem == null) {
                    rewardItem = new RewardItem(this.a.getItemId());
                    arrayList.add(rewardItem);
                    rewardItem.count = l;
                    rewardItem.enchantMin = this.pr;
                    rewardItem.enchantMax = this.ps;
                } else {
                    rewardItem.count = SafeMath.addAndLimit(rewardItem.count, l);
                }
            }
            ++n;
        }
        return arrayList;
    }
}
