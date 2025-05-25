/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExConnectedTimeAndGettableReward;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class OneDayRewardHolder
extends AbstractHolder {
    private static final OneDayRewardHolder a = new OneDayRewardHolder();
    private List<OneDayReward> ah = new ArrayList<OneDayReward>();

    public static OneDayRewardHolder getInstance() {
        return a;
    }

    public void addOneDayReward(OneDayReward oneDayReward) {
        this.ah.add(oneDayReward);
    }

    public Collection<OneDayReward> getOneDayRewards(Player player) {
        ArrayList<OneDayReward> arrayList = new ArrayList<OneDayReward>(this.ah.size());
        for (OneDayReward oneDayReward : this.ah) {
            if (!oneDayReward.accept(player)) continue;
            arrayList.add(oneDayReward);
        }
        return arrayList;
    }

    public List<OneDayReward> getOneDayReward(int n, Player player) {
        ArrayList<OneDayReward> arrayList = new ArrayList<OneDayReward>(1);
        for (OneDayReward oneDayReward : this.ah) {
            if (oneDayReward.getId() != n || !oneDayReward.accept(player)) continue;
            arrayList.add(oneDayReward);
        }
        return arrayList;
    }

    public int getAvaliableOneDayReward(Player player) {
        int n = 0;
        for (OneDayReward oneDayReward : this.ah) {
            OneDayRewardStatus oneDayRewardStatus;
            OneDayRewardRequirement oneDayRewardRequirement;
            if (!oneDayReward.accept(player) || (oneDayRewardRequirement = oneDayReward.getRequirement()) == null || (oneDayRewardStatus = player.getOneDayRewardStore().getStatus(oneDayReward)).isReceived() || !oneDayRewardRequirement.isDone(oneDayRewardStatus)) continue;
            ++n;
        }
        return n;
    }

    public <T extends OneDayRewardRequirement> void fireRequirements(Player player, Creature creature, Class<T> clazz) {
        if (!Config.EX_ONE_DAY_REWARD) {
            return;
        }
        int n = this.getAvaliableOneDayReward(player);
        for (OneDayReward oneDayReward : this.ah) {
            Object object;
            Condition condition;
            OneDayRewardRequirement oneDayRewardRequirement = oneDayReward.getRequirement();
            if (oneDayRewardRequirement == null || !clazz.isInstance(oneDayRewardRequirement) || !oneDayReward.accept(player) || (condition = oneDayReward.getCondition()) != null && !condition.test((Env)(object = new Env(player, creature, null))) || ((OneDayRewardStatus)(object = player.getOneDayRewardStore().getStatus(oneDayReward))).isReceived() || oneDayRewardRequirement.isDone((OneDayRewardStatus)object)) continue;
            int n2 = oneDayRewardRequirement.increaseProgress(player, ((OneDayRewardStatus)object).getCurrentProgress());
            if (n2 == -1) {
                return;
            }
            player.getOneDayRewardStore().updateStatus(oneDayReward, n2, false);
        }
        int n3 = this.getAvaliableOneDayReward(player);
        if (n != n3) {
            player.sendPacket((IStaticPacket)new ExConnectedTimeAndGettableReward(n3));
        }
    }

    public <T extends OneDayRewardRequirement> void fireRequirementsWithDistribution(Player player3, Creature creature, Class<T> clazz) {
        if (!Config.EX_ONE_DAY_REWARD) {
            return;
        }
        int n = this.getAvaliableOneDayReward(player3);
        block5: for (OneDayReward oneDayReward : this.ah) {
            Object object;
            Condition condition;
            OneDayRewardRequirement oneDayRewardRequirement = oneDayReward.getRequirement();
            if (oneDayRewardRequirement == null || !clazz.isInstance(oneDayRewardRequirement) || !oneDayReward.accept(player3) || (condition = oneDayReward.getCondition()) != null && !condition.test((Env)(object = new Env(player3, creature, null)))) continue;
            object = oneDayReward.getDistributionType();
            switch (object) {
                case SOLO: {
                    this.a(player3, creature, oneDayReward, oneDayRewardRequirement);
                    break;
                }
                case PARTY_RANDOM: {
                    if (player3.getParty() != null) {
                        List list = player3.getParty().getPartyMembers().stream().filter(player2 -> !player2.isDead() && player3.isInRangeZ((GameObject)player2, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) && Math.abs(player3.getLevel() - player2.getLevel()) <= 9).collect(Collectors.toList());
                        if (list.isEmpty()) continue block5;
                        Player player4 = (Player)Rnd.get(list);
                        this.a(player4, creature, oneDayReward, oneDayRewardRequirement);
                        break;
                    }
                    this.a(player3, creature, oneDayReward, oneDayRewardRequirement);
                    break;
                }
                case PARTY_ALL: {
                    if (player3.getParty() != null) {
                        player3.getParty().getPartyMembers().stream().filter(player2 -> !player2.isDead() && player3.isInRangeZ((GameObject)player2, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) && Math.abs(player3.getLevel() - player2.getLevel()) <= 9).forEach(player -> this.a((Player)player, creature, oneDayReward, oneDayRewardRequirement));
                        break;
                    }
                    this.a(player3, creature, oneDayReward, oneDayRewardRequirement);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown distribution type: " + object);
                }
            }
        }
        int n2 = this.getAvaliableOneDayReward(player3);
        if (n != n2) {
            player3.sendPacket((IStaticPacket)new ExConnectedTimeAndGettableReward(n2));
        }
    }

    private void a(Player player, Creature creature, OneDayReward oneDayReward, OneDayRewardRequirement oneDayRewardRequirement) {
        OneDayRewardStatus oneDayRewardStatus = player.getOneDayRewardStore().getStatus(oneDayReward);
        if (oneDayRewardStatus.isReceived() || oneDayRewardRequirement.isDone(oneDayRewardStatus)) {
            return;
        }
        int n = oneDayRewardRequirement.increaseProgress(player, oneDayRewardStatus.getCurrentProgress());
        if (n != -1) {
            player.getOneDayRewardStore().updateStatus(oneDayReward, n, false);
        }
    }

    @Override
    public int size() {
        return this.ah.size();
    }

    @Override
    public void clear() {
        this.ah.clear();
    }
}
