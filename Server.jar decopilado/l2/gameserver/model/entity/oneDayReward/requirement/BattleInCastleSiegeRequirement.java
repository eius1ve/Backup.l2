/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.pledge.Clan;

public class BattleInCastleSiegeRequirement
implements OneDayRewardRequirement {
    @Override
    public int getRequiredProgress() {
        return 1;
    }

    @Override
    public OneDayRewardStatus getInitialState(OneDayReward oneDayReward, Player player) {
        Clan clan = player.getClan();
        if (clan != null) {
            for (Castle castle : ResidenceHolder.getInstance().getResidenceList(Castle.class)) {
                Object e = castle.getSiegeEvent();
                if (e == null || !((SiegeEvent)e).isInProgress()) continue;
                Object s = ((SiegeEvent)e).getSiegeClan("attackers", clan);
                if (s != null) {
                    return new OneDayRewardStatus(oneDayReward, player.getObjectId(), 1);
                }
                s = ((SiegeEvent)e).getSiegeClan("defenders", clan);
                if (s == null) continue;
                return new OneDayRewardStatus(oneDayReward, player.getObjectId(), 1);
            }
        }
        return null;
    }
}
