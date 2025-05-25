/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import java.util.ArrayList;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;

static class EffectCubic.2
extends RunnableImpl {
    final /* synthetic */ Creature val$aimTarget;
    final /* synthetic */ Player val$player;
    final /* synthetic */ Skill val$skill;

    EffectCubic.2(Creature creature, Player player, Skill skill) {
        this.val$aimTarget = creature;
        this.val$player = player;
        this.val$skill = skill;
    }

    @Override
    public void runImpl() throws Exception {
        ArrayList<Creature> arrayList = new ArrayList<Creature>(1);
        arrayList.add(this.val$aimTarget);
        this.val$player.broadcastPacket(new MagicSkillLaunched((Creature)this.val$player, this.val$skill.getDisplayId(), this.val$skill.getDisplayLevel(), arrayList));
        this.val$player.callSkill(this.val$skill, arrayList, false);
        if (this.val$aimTarget.isNpc()) {
            if (this.val$aimTarget.paralizeOnAttack(this.val$player)) {
                if (Config.PARALIZE_ON_RAID_DIFF) {
                    this.val$player.paralizeMe(this.val$aimTarget);
                }
            } else {
                int n = this.val$skill.getEffectPoint() != 0 ? this.val$skill.getEffectPoint() : (int)this.val$skill.getPower();
                this.val$aimTarget.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, this.val$player, n);
            }
        }
    }
}
