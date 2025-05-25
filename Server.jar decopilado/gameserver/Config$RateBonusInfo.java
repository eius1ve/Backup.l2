/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.actor.instances.player.Bonus;
import org.apache.commons.lang3.tuple.Pair;

public static class Config.RateBonusInfo {
    public final int id;
    public final int consumeItemId;
    public final long consumeItemAmount;
    public float rateXp = 1.0f;
    public float rateSp = 1.0f;
    public float rateRaidXp = 1.0f;
    public float rateRaidSp = 1.0f;
    public float questRewardRate = 1.0f;
    public float questAdenaRewardRate = 1.0f;
    public float questDropRate = 1.0f;
    public float dropAdena = 1.0f;
    public float dropItems = 1.0f;
    public float dropRaidItems = 1.0f;
    public float dropSpoil = 1.0f;
    public float dropSealStones = 1.0f;
    public float enchantItemMul = 1.0f;
    public float enchantSkillMul = 1.0f;
    public int hwidLimits = 1;
    public List<Pair<Integer, Long>> rewardItem = new ArrayList<Pair<Integer, Long>>();
    public Integer nameColor = null;
    public long bonusTimeSeconds = 0L;

    public Config.RateBonusInfo(int n, int n2, long l) {
        this.id = n;
        this.consumeItemId = n2;
        this.consumeItemAmount = l;
    }

    public Bonus makeBonus() {
        Bonus bonus = new Bonus();
        bonus.setBonusExpire(this.bonusTimeSeconds + System.currentTimeMillis() / 1000L);
        bonus.setRateXp(this.rateXp);
        bonus.setRateSp(this.rateSp);
        bonus.setRateRaidXp(this.rateRaidXp);
        bonus.setRateRaidSp(this.rateRaidSp);
        bonus.setQuestRewardRate(this.questRewardRate);
        bonus.setQuestRewardAdenaRate(this.questAdenaRewardRate);
        bonus.setQuestDropRate(this.questDropRate);
        bonus.setDropAdena(this.dropAdena);
        bonus.setDropItems(this.dropItems);
        bonus.setDropSealStones(this.dropSealStones);
        bonus.setDropRaidItems(this.dropRaidItems);
        bonus.setDropSpoil(this.dropSpoil);
        bonus.setEnchantItem(this.enchantItemMul);
        bonus.setEnchantSkill(this.enchantSkillMul);
        bonus.setHwidsLimit(this.hwidLimits);
        return bonus;
    }
}
