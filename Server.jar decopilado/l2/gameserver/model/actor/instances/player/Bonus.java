/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player;

import java.util.concurrent.TimeUnit;

public class Bonus {
    public static final float DEFAULT_RATE_XP = 1.0f;
    public static final float DEFAULT_RATE_SP = 1.0f;
    public static final float DEFAULT_RATE_RAID_XP = 1.0f;
    public static final float DEFAULT_RATE_RAID_SP = 1.0f;
    public static final float DEFAULT_QUEST_REWARD_RATE = 1.0f;
    public static final float DEFAULT_QUEST_ADENA_REWARD_RATE = 1.0f;
    public static final float DEFAULT_QUEST_DROP_RATE = 1.0f;
    public static final float DEFAULT_DROP_ADENA = 1.0f;
    public static final float DEFAULT_DROP_ITEMS = 1.0f;
    public static final float DEFAULT_DROP_SEAL_STONES = 1.0f;
    public static final float DEFAULT_DROP_RAID_ITEMS = 1.0f;
    public static final float DEFAULT_DROP_SPOIL = 1.0f;
    public static final float DEFAULT_ENCHANT_ITEM = 1.0f;
    public static final float DEFAULT_ENCHANT_SKILL = 1.0f;
    public static final int DEFAULT_HWIDS_LIMIT = 1;
    private float rateXp = 1.0f;
    private float rateSp = 1.0f;
    private float rateRaidXp = 1.0f;
    private float rateRaidSp = 1.0f;
    private float questRewardRate = 1.0f;
    private float a = 1.0f;
    private float questDropRate = 1.0f;
    private float dropAdena = 1.0f;
    private float dropItems = 1.0f;
    private float dropSealStones = 1.0f;
    private float dropRaidItems = 1.0f;
    private float dropSpoil = 1.0f;
    private float b = 1.0f;
    private float c = 1.0f;
    private int kf = 1;
    private long bP = 0L;

    public void reset() {
        this.setRateXp(1.0f);
        this.setRateSp(1.0f);
        this.setRateRaidXp(1.0f);
        this.setRateRaidSp(1.0f);
        this.setQuestRewardRate(1.0f);
        this.setQuestRewardAdenaRate(1.0f);
        this.setQuestDropRate(1.0f);
        this.setDropAdena(1.0f);
        this.setDropItems(1.0f);
        this.setDropSealStones(1.0f);
        this.setDropRaidItems(1.0f);
        this.setDropSpoil(1.0f);
        this.setEnchantItem(1.0f);
        this.setEnchantSkill(1.0f);
        this.setBonusExpire(0L);
        this.setHwidsLimit(1);
    }

    public float getRateXp() {
        return this.rateXp;
    }

    public void setRateXp(float f) {
        this.rateXp = f;
    }

    public float getRateSp() {
        return this.rateSp;
    }

    public void setRateSp(float f) {
        this.rateSp = f;
    }

    public float getRateRaidXp() {
        return this.rateRaidXp;
    }

    public void setRateRaidXp(float f) {
        this.rateRaidXp = f;
    }

    public float getRateRaidSp() {
        return this.rateRaidSp;
    }

    public void setRateRaidSp(float f) {
        this.rateRaidSp = f;
    }

    public float getQuestRewardRate() {
        return this.questRewardRate;
    }

    public void setQuestRewardRate(float f) {
        this.questRewardRate = f;
    }

    public float getQuestRewardAdenaRate() {
        return this.a;
    }

    public void setQuestRewardAdenaRate(float f) {
        this.a = f;
    }

    public float getQuestDropRate() {
        return this.questDropRate;
    }

    public void setQuestDropRate(float f) {
        this.questDropRate = f;
    }

    public float getDropAdena() {
        return this.dropAdena;
    }

    public void setDropAdena(float f) {
        this.dropAdena = f;
    }

    public float getDropItems() {
        return this.dropItems;
    }

    public void setDropItems(float f) {
        this.dropItems = f;
    }

    public float getDropSealStones() {
        return this.dropSealStones;
    }

    public void setDropSealStones(float f) {
        this.dropSealStones = f;
    }

    public float getDropRaidItems() {
        return this.dropRaidItems;
    }

    public void setDropRaidItems(float f) {
        this.dropRaidItems = f;
    }

    public float getDropSpoil() {
        return this.dropSpoil;
    }

    public void setDropSpoil(float f) {
        this.dropSpoil = f;
    }

    public float getEnchantItemMul() {
        return this.b;
    }

    public void setEnchantItem(float f) {
        this.b = f;
    }

    public float getEnchantSkillMul() {
        return this.c;
    }

    public void setEnchantSkill(float f) {
        this.c = f;
    }

    public long getBonusExpire() {
        return this.bP;
    }

    public void setBonusExpire(long l) {
        this.bP = l;
    }

    public boolean isExpired() {
        return this.getBonusExpire() < TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public int getHwidsLimit() {
        return this.kf;
    }

    public Bonus setHwidsLimit(int n) {
        this.kf = n;
        return this;
    }
}
