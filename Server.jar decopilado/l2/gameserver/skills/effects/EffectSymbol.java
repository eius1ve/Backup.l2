/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.skills.effects;

import java.util.ArrayList;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.SymbolInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExMagicSkillUseGround;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EffectSymbol
extends Effect {
    private static final Logger dm = LoggerFactory.getLogger(EffectSymbol.class);
    private NpcInstance y = null;

    public EffectSymbol(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    public boolean checkCondition() {
        if (this.getSkill().getTargetType() != Skill.SkillTargetType.TARGET_SELF) {
            dm.error("Symbol skill with target != self, id = " + this.getSkill().getId());
            return false;
        }
        Skill skill = this.getSkill().getFirstAddedSkill();
        if (skill == null) {
            dm.error("Not implemented symbol skill, id = " + this.getSkill().getId());
            return false;
        }
        return super.checkCondition();
    }

    @Override
    public void onStart() {
        super.onStart();
        Skill skill = this.getSkill().getFirstAddedSkill();
        skill.setMagicType(this.getSkill().getMagicType());
        Location location = this._effected.getLoc();
        if (this._effected.isPlayer() && ((Player)this._effected).getGroundSkillLoc() != null) {
            location = ((Player)this._effected).getGroundSkillLoc();
            ((Player)this._effected).setGroundSkillLoc(null);
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(this._skill.getSymbolId());
        this.y = this.getTemplate()._count <= 1 ? new SymbolInstance(IdFactory.getInstance().getNextId(), npcTemplate, this._effected, skill) : new NpcInstance(IdFactory.getInstance().getNextId(), npcTemplate);
        Location location2 = new Location(location.getX(), location.getY(), location.getZ(), location.getHeading());
        ThreadPoolManager.getInstance().schedule(() -> this._effected.broadcastPacket(new ExMagicSkillUseGround(this._effected.getObjectId(), skill.getDisplayId(), location2)), 100L);
        this.y.setLevel(this._effected.getLevel());
        this.y.setReflection(this._effected.getReflection());
        this.y.spawnMe(location);
    }

    @Override
    public void onExit() {
        super.onExit();
        if (this.y != null && this.y.isVisible()) {
            this.y.deleteMe();
        }
        this.y = null;
    }

    @Override
    public boolean onActionTime() {
        if (this.getTemplate()._count <= 1) {
            return false;
        }
        Creature creature = this.getEffector();
        Skill skill = this.getSkill().getFirstAddedSkill();
        NpcInstance npcInstance = this.y;
        double d = this.getSkill().getMpConsume();
        if (creature == null || skill == null || npcInstance == null) {
            return false;
        }
        if (d > creature.getCurrentMp()) {
            creature.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
            return false;
        }
        creature.reduceCurrentMp(d, creature);
        for (Creature creature2 : World.getAroundCharacters(npcInstance, this.getSkill().getSkillRadius(), 200)) {
            if (creature2.isDoor() || creature2.getEffectList().getEffectsBySkill(skill) != null || skill.checkTarget(creature, creature2, creature2, false, false) != null || skill.isOffensive() && !GeoEngine.canSeeTarget(npcInstance, creature2, false)) continue;
            ArrayList<Creature> arrayList = new ArrayList<Creature>(1);
            arrayList.add(creature2);
            creature.callSkill(skill, arrayList, true);
            creature.broadcastPacket(new MagicSkillLaunched((Creature)npcInstance, this.getSkill().getDisplayId(), this.getSkill().getDisplayLevel(), creature2));
        }
        return true;
    }
}
