/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import java.util.List;
import java.util.Map;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.skills.effects.EffectDispelEffects;

private static final class EffectDispelEffects.ReApplyEffects
extends RunnableImpl {
    private final HardReference<Player> aa;
    private final Map<Skill, List<EffectDispelEffects.StopEffectRecord>> bG;

    private EffectDispelEffects.ReApplyEffects(HardReference<Player> hardReference, Map<Skill, List<EffectDispelEffects.StopEffectRecord>> map) {
        this.aa = hardReference;
        this.bG = map;
    }

    public EffectDispelEffects.ReApplyEffects(Player player, Map<Skill, List<EffectDispelEffects.StopEffectRecord>> map) {
        this(player.getRef(), map);
    }

    @Override
    public void runImpl() throws Exception {
        Player player = this.aa.get();
        if (player == null || player.isLogoutStarted() || player.isOutOfControl() || !fD && (player.isDead() || player.isAlikeDead()) || player.isInDuel() || player.isOlyParticipant() || player.isFlying() || player.isSitting() || player.getTeam() != TeamType.NONE || player.isInStoreMode()) {
            return;
        }
        for (Map.Entry<Skill, List<EffectDispelEffects.StopEffectRecord>> entry : this.bG.entrySet()) {
            Skill skill = entry.getKey();
            List<EffectDispelEffects.StopEffectRecord> list = entry.getValue();
            for (EffectDispelEffects.StopEffectRecord stopEffectRecord : list) {
                stopEffectRecord.apply(skill, player);
            }
        }
    }
}
