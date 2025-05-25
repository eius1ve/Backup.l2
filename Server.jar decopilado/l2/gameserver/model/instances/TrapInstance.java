/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.NpcInfo;
import l2.gameserver.taskmanager.EffectTaskManager;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public final class TrapInstance
extends NpcInstance {
    private final HardReference<? extends Creature> Y;
    private final Skill s;
    private ScheduledFuture<?> ab;
    private ScheduledFuture<?> ac;
    private boolean dE;

    public TrapInstance(int n, NpcTemplate npcTemplate, Creature creature, Skill skill) {
        this(n, npcTemplate, creature, skill, creature.getLoc());
    }

    public TrapInstance(int n, NpcTemplate npcTemplate, Creature creature, Skill skill, Location location) {
        super(n, npcTemplate);
        this.Y = creature.getRef();
        this.s = skill;
        this.setReflection(creature.getReflection());
        this.setLevel(creature.getLevel());
        this.setTitle(creature.getName());
        this.setLoc(location);
    }

    @Override
    public boolean isTrap() {
        return true;
    }

    public Creature getOwner() {
        return this.Y.get();
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        this.ac = ThreadPoolManager.getInstance().schedule(new GameObjectTasks.DeleteTask(this), 120000L);
        this.ab = EffectTaskManager.getInstance().scheduleAtFixedRate(new CastTask(this), 250L, 250L);
    }

    @Override
    public void broadcastCharInfo() {
        if (!this.isDetected()) {
            return;
        }
        super.broadcastCharInfo();
    }

    @Override
    protected void onDelete() {
        Creature creature = this.getOwner();
        if (creature != null && creature.isPlayer()) {
            ((Player)creature).removeTrap(this);
        }
        if (this.ac != null) {
            this.ac.cancel(false);
        }
        this.ac = null;
        if (this.ab != null) {
            this.ab.cancel(false);
        }
        this.ab = null;
        super.onDelete();
    }

    public boolean isDetected() {
        return this.dE;
    }

    public void setDetected(boolean bl) {
        this.dE = bl;
    }

    @Override
    public int getPAtk(Creature creature) {
        Creature creature2 = this.getOwner();
        return creature2 == null ? 0 : creature2.getPAtk(creature);
    }

    @Override
    public int getMAtk(Creature creature, Skill skill) {
        Creature creature2 = this.getOwner();
        return creature2 == null ? 0 : creature2.getMAtk(creature, skill);
    }

    @Override
    public boolean hasRandomAnimation() {
        return false;
    }

    @Override
    public boolean isAutoAttackable(Creature creature) {
        return false;
    }

    @Override
    public boolean isAttackable(Creature creature) {
        return false;
    }

    @Override
    public boolean isInvul() {
        return true;
    }

    @Override
    public boolean isFearImmune() {
        return true;
    }

    @Override
    public boolean isParalyzeImmune() {
        return true;
    }

    @Override
    public boolean isLethalImmune() {
        return true;
    }

    @Override
    public void showChatWindow(Player player, int n, Object ... objectArray) {
    }

    @Override
    public void showChatWindow(Player player, String string, Object ... objectArray) {
    }

    @Override
    public void onBypassFeedback(Player player, String string) {
    }

    @Override
    public void onAction(Player player, boolean bl) {
        if (player.getTarget() != this) {
            player.setTarget(this);
            if (player.getTarget() == this) {
                player.sendPacket((IStaticPacket)new MyTargetSelected(this.getObjectId(), player.getLevel()));
            }
        }
        player.sendActionFailed();
    }

    @Override
    public List<L2GameServerPacket> addPacketList(Player player, Creature creature) {
        if (!this.isDetected() && this.getOwner() != player) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new NpcInfo(this, player));
    }

    private static class CastTask
    extends RunnableImpl {
        private HardReference<NpcInstance> Z;

        public CastTask(TrapInstance trapInstance) {
            this.Z = trapInstance.getRef();
        }

        @Override
        public void runImpl() throws Exception {
            TrapInstance trapInstance = (TrapInstance)this.Z.get();
            if (trapInstance == null) {
                return;
            }
            Creature creature = trapInstance.getOwner();
            if (creature == null) {
                return;
            }
            for (Creature creature2 : trapInstance.getAroundCharacters(200, 200)) {
                if (creature2 == creature || trapInstance.s.checkTarget(creature, creature2, null, false, false) != null) continue;
                ArrayList<Creature> arrayList = new ArrayList<Creature>();
                if (trapInstance.s.getTargetType() != Skill.SkillTargetType.TARGET_AREA) {
                    arrayList.add(creature2);
                } else {
                    for (Creature creature3 : trapInstance.getAroundCharacters(trapInstance.s.getSkillRadius(), 128)) {
                        if (trapInstance.s.checkTarget(creature, creature3, null, false, false) != null) continue;
                        arrayList.add(creature2);
                    }
                }
                trapInstance.s.useSkill(trapInstance, arrayList);
                if (creature2.isPlayer()) {
                    creature2.sendMessage(new CustomMessage("common.Trap", creature2.getPlayer(), new Object[0]));
                }
                trapInstance.deleteMe();
                break;
            }
        }
    }
}
