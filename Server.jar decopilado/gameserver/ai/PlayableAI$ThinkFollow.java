/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.PlayableAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.GameClient;

protected static class PlayableAI.ThinkFollow
extends RunnableImpl {
    private final HardReference<? extends Playable> l;

    public PlayableAI.ThinkFollow(Playable playable) {
        this.l = playable.getRef();
    }

    @Override
    public void runImpl() throws Exception {
        Playable playable = this.l.get();
        if (playable == null) {
            return;
        }
        PlayableAI playableAI = (PlayableAI)playable.getAI();
        if (playableAI.getIntention() != CtrlIntention.AI_INTENTION_FOLLOW) {
            return;
        }
        Creature creature = (Creature)playableAI._intention_arg0;
        if (creature == null || creature.isAlikeDead()) {
            playableAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            return;
        }
        int n = playable.getPlayer() != null && playable.getPlayer().getNetConnection() != null ? playable.getPlayer().getNetConnection().getPawnClippingRange() : GameClient.DEFAULT_PAWN_CLIPPING_RANGE;
        int n2 = (int)(playable.getColRadius() + creature.getColRadius());
        boolean bl = PlayableAI.isThinkImplyZ(playable, creature);
        int n3 = (int)(!bl ? playable.getDistance(creature) : playable.getDistance3D(creature)) - n2;
        int n4 = Math.min(n, creature.getActingRange());
        int n5 = Config.FOLLOW_ARRIVE_DISTANCE;
        if (n3 > n || n3 > 2 << World.SHIFT_BY) {
            playableAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playableAI.clientStopMoving();
            return;
        }
        Player player = playable.getPlayer();
        if (player == null || player.isLogoutStarted() || (playable.isPet() || playable.isSummon()) && player.getPet() != playable) {
            playableAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            playableAI.clientStopMoving();
            return;
        }
        if (!(n3 <= n5 + 16 || playable.isFollowing() && playable.getFollowTarget() == creature)) {
            playable.moveToRelative(creature, n4 + n2, n5 + n2);
        }
        playableAI._followTask = playableAI.scheduleThinkFollowTask();
    }
}
