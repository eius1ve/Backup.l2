/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.PlayableAI;
import l2.gameserver.ai.SummonAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.GameClient;

protected static class SummonAI.ThinkFollowForSummon
extends RunnableImpl {
    private final HardReference<? extends Summon> n;

    public SummonAI.ThinkFollowForSummon(Summon summon) {
        this.n = summon.getRef();
    }

    @Override
    public void runImpl() throws Exception {
        Summon summon = this.n.get();
        if (summon == null) {
            return;
        }
        SummonAI summonAI = summon.getAI();
        if (summonAI.getIntention() != CtrlIntention.AI_INTENTION_FOLLOW) {
            if (summonAI.getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                summon.setFollowMode(false);
            }
            return;
        }
        Creature creature = (Creature)summonAI._intention_arg0;
        if (creature == null || creature.isAlikeDead()) {
            summonAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            return;
        }
        int n = summon.getPlayer() != null && summon.getPlayer().getNetConnection() != null ? summon.getPlayer().getNetConnection().getPawnClippingRange() : GameClient.DEFAULT_PAWN_CLIPPING_RANGE;
        int n2 = (int)(summon.getColRadius() + creature.getColRadius());
        boolean bl = PlayableAI.isThinkImplyZ(summon, creature);
        int n3 = (int)(!bl ? summon.getDistance(creature) : summon.getDistance3D(creature)) - n2;
        int n4 = Math.min(n, creature.getActingRange());
        int n5 = summon.getActingRange();
        if (n3 > n || n3 > 2 << World.SHIFT_BY) {
            summonAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            summonAI.clientStopMoving();
            return;
        }
        Player player = summon.getPlayer();
        if (player == null || player.isLogoutStarted() || (summon.isPet() || summon.isSummon()) && player.getPet() != summon) {
            summonAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            summonAI.clientStopMoving();
            return;
        }
        if (n3 > n5) {
            if (!summon.isFollowing() || summon.getFollowTarget() != creature) {
                summon.moveToRelative(creature, CharacterAI.getIndentRange(n4), n5);
            }
        } else {
            summonAI.am();
        }
        summonAI._followTask = summonAI.scheduleThinkFollowTask();
    }
}
