/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.ItemFunctions
 */
package services;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.ItemFunctions;

public static class PvpBonusSkills.Rank {
    private final int bFP;
    private final int bFQ;
    private final List<Skill> dV = new ArrayList<Skill>();
    private final int bFR;
    private final int bFS;
    private final int bFT;
    private final long ey;
    private final String hH;

    public PvpBonusSkills.Rank(int n, int n2, int n3, int n4, int n5, long l, String string) {
        this.bFP = n;
        this.bFQ = n2;
        this.bFR = n3;
        this.bFS = n4;
        this.bFT = n5;
        this.ey = l;
        this.hH = string;
    }

    public void addSkill(int n, int n2) {
        Skill skill = SkillTable.getInstance().getInfo(n, n2);
        if (skill != null) {
            this.dV.add(skill);
        }
    }

    public int getRankId() {
        return this.bFP;
    }

    public int getMinPvp() {
        return this.bFQ;
    }

    public List<Skill> getSkills() {
        return this.dV;
    }

    public boolean isEligible(Player player) {
        return player.getLevel() >= this.bFR && player.getLevel() <= this.bFS;
    }

    public void rewardPlayer(Player player) {
        if (this.bFT > 0 && this.ey > 0L) {
            ItemFunctions.addItem((Playable)player, (int)this.bFT, (long)this.ey, (boolean)true);
        }
    }

    public String getAnnounceMsg() {
        return this.hH;
    }

    public int getRewardItemId() {
        return this.bFT;
    }

    public long getRewardItemCount() {
        return this.ey;
    }
}
