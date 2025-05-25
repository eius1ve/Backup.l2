/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.c2s;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.ExConnectedTimeAndGettableReward;
import l2.gameserver.network.l2.s2c.ExOneDayReceiveRewardList;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;

public class RequestOneDayRewardReceive
extends L2GameClientPacket {
    private int rA;

    @Override
    protected void readImpl() throws Exception {
        this.rA = this.readH();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() throws Exception {
        if (!Config.EX_ONE_DAY_REWARD) {
            return;
        }
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        List<OneDayReward> list = OneDayRewardHolder.getInstance().getOneDayReward(this.rA, player);
        if (list == null) {
            return;
        }
        OneDayReward oneDayReward = null;
        if (list.size() == 1) {
            oneDayReward = list.get(0);
        } else if (list.size() > 1) {
            for (OneDayReward object2 : list) {
                Condition condition = object2.getCondition();
            }
        }
        if (oneDayReward == null) {
            return;
        }
        OneDayRewardRequirement oneDayRewardRequirement = oneDayReward.getRequirement();
        if (oneDayRewardRequirement == null) {
            return;
        }
        OneDayRewardStatus oneDayRewardStatus = player.getOneDayRewardStore().getStatus(oneDayReward);
        if (oneDayRewardStatus.isReceived() || !oneDayRewardRequirement.isDone(oneDayRewardStatus)) {
            return;
        }
        try {
            player.getOneDayRewardStore().updateStatus(oneDayReward, oneDayRewardStatus.getCurrentProgress(), true);
            for (Pair pair : oneDayReward.getRewards()) {
                ItemFunctions.addItem((Playable)player, (Integer)pair.getKey(), (long)((Long)pair.getValue()), true);
                Log.LogItem(player, Log.ItemLog.OneDayReward, (Integer)pair.getKey(), (long)((Long)pair.getValue()));
            }
        }
        catch (Throwable throwable) {
            player.sendPacket(new ExOneDayReceiveRewardList(player), new ExConnectedTimeAndGettableReward(player));
            throw throwable;
        }
        player.sendPacket(new ExOneDayReceiveRewardList(player), new ExConnectedTimeAndGettableReward(player));
    }
}
