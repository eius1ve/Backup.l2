/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.stats.Env;
import l2.gameserver.utils.Location;

public class DuelSnapshotObject
implements Serializable {
    private final TeamType a;
    private final HardReference<Player> V;
    private final int ly;
    private final List<Effect> bB;
    private final Location K;
    private final double A;
    private final double B;
    private final double C;
    private boolean dm;

    public DuelSnapshotObject(Player player, TeamType teamType) {
        this.V = player.getRef();
        this.a = teamType;
        this.K = player.getReflection().getReturnLoc() == null ? player.getLoc() : player.getReflection().getReturnLoc();
        this.C = player.getCurrentCp();
        this.A = player.getCurrentHp();
        this.B = player.getCurrentMp();
        this.ly = player.getActiveClassId();
        List<Effect> list = player.getEffectList().getAllEffects();
        this.bB = new ArrayList<Effect>(list.size());
        for (Effect effect : list) {
            Effect effect2 = effect.getTemplate().getEffect(new Env(effect.getEffector(), effect.getEffected(), effect.getSkill()));
            if (!effect2.isSaveable()) continue;
            effect2.setCount(effect.getCount());
            effect2.setPeriod(effect.getCount() == 1 ? effect.getPeriod() - effect.getTime() : effect.getPeriod());
            this.bB.add(effect2);
        }
    }

    public void restore(boolean bl) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (!bl) {
            player.getEffectList().stopAllEffects();
            if (this.ly == player.getActiveClassId()) {
                for (Effect effect : this.bB) {
                    if (player.getEffectList().getEffectsBySkill(effect.getSkill()) != null) continue;
                    player.getEffectList().addEffect(effect);
                }
            }
            player.setCurrentCp(this.C);
            player.setCurrentHpMp(this.A, this.B);
        }
    }

    public void teleport() {
        Player player = this.getPlayer();
        player._stablePoint = null;
        if (player.isFrozen()) {
            player.stopFrozen();
        }
        ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                Player player = DuelSnapshotObject.this.getPlayer();
                if (player == null) {
                    return;
                }
                player.teleToLocation(DuelSnapshotObject.this.K, ReflectionManager.DEFAULT);
            }
        }, 5000L);
    }

    public Player getPlayer() {
        return this.V.get();
    }

    public boolean isDead() {
        return this.dm;
    }

    public void setDead() {
        this.dm = true;
    }

    public Location getLoc() {
        return this.K;
    }

    public TeamType getTeam() {
        return this.a;
    }
}
