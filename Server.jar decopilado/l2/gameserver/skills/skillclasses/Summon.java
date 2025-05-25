/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.dao.EffectsDAO;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.SummonInstance;
import l2.gameserver.model.instances.TrapInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncAdd;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;

public class Summon
extends Skill {
    private final SummonType a;
    private final double an;
    private final int Dr;
    private final int Ds;
    private final int Dt;
    private final int Du;
    private final int Dv;

    public Summon(StatsSet statsSet) {
        super(statsSet);
        this.a = Enum.valueOf(SummonType.class, statsSet.getString("summonType", "PET").toUpperCase());
        this.an = statsSet.getDouble("expPenalty", 0.0);
        this.Dr = statsSet.getInteger("itemConsumeIdInTime", 0);
        this.Ds = statsSet.getInteger("itemConsumeCountInTime", 0);
        this.Dt = statsSet.getInteger("itemConsumeDelay", 240) * 1000;
        this.Du = statsSet.getInteger("lifeTime", 1200) * 1000;
        this.Dv = statsSet.getInteger("minRadius", 0);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        Player player = creature.getPlayer();
        if (player == null) {
            return false;
        }
        switch (this.a) {
            case TRAP: {
                if (!player.isInZonePeace()) break;
                creature.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_ATTACK_IN_A_PEACEFUL_ZONE);
                return false;
            }
            case PET: 
            case SIEGE_SUMMON: {
                if (player.isProcessingRequest()) {
                    player.sendPacket((IStaticPacket)SystemMsg.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
                    return false;
                }
                if (player.getPet() == null && !player.isMounted()) break;
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ALREADY_HAVE_A_PET);
                return false;
            }
            case MERCHANT: {
                if (!player.isProcessingRequest()) break;
                player.sendPacket((IStaticPacket)SystemMsg.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
                return false;
            }
            case AGATHION: {
                if (player.getAgathionId() > 0 && this._npcId != 0) {
                    player.sendPacket((IStaticPacket)SystemMsg.AN_AGATHION_HAS_ALREADY_BEEN_SUMMONED);
                    return false;
                }
            }
            case NPC: {
                if (this.Dv <= 0) break;
                for (NpcInstance npcInstance : World.getAroundNpc(player, this.Dv, 200)) {
                    if (npcInstance == null || npcInstance.getNpcId() != this.getNpcId()) continue;
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SINCE_S1_ALREADY_EXISTS_NEARBY_YOU_CANNOT_SUMMON_IT_AGAIN).addName(npcInstance));
                    return false;
                }
                break;
            }
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        Player player = creature.getPlayer();
        switch (this.a) {
            case AGATHION: {
                player.setAgathion(this.getNpcId());
                break;
            }
            case TRAP: {
                Skill skill = this.getFirstAddedSkill();
                if (player.getTrapsCount() >= 5) {
                    player.destroyFirstTrap();
                }
                TrapInstance trapInstance = new TrapInstance(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(this.getNpcId()), player, skill);
                player.addTrap(trapInstance);
                trapInstance.spawnMe();
                break;
            }
            case PET: 
            case SIEGE_SUMMON: {
                Creature creature22;
                Location location = null;
                if (this._targetType == Skill.SkillTargetType.TARGET_CORPSE) {
                    for (Creature creature22 : list) {
                        if (creature22 == null || !creature22.isDead()) continue;
                        player.getAI().setAttackTarget(null);
                        location = creature22.getLoc();
                        if (creature22.isNpc()) {
                            ((NpcInstance)creature22).endDecayTask();
                            continue;
                        }
                        if (creature22.isSummon()) {
                            ((SummonInstance)creature22).endDecayTask();
                            continue;
                        }
                        return;
                    }
                }
                if (player.getPet() != null || player.isMounted()) {
                    return;
                }
                NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(this.getNpcId());
                creature22 = new SummonInstance(IdFactory.getInstance().getNextId(), npcTemplate, player, this.Du, this.Dr, this.Ds, this.Dt, this);
                player.setPet((l2.gameserver.model.Summon)creature22);
                ((SummonInstance)creature22).setExpPenalty(this.an);
                ((l2.gameserver.model.Summon)creature22).setExp(Experience.LEVEL[Math.min(((SummonInstance)creature22).getLevel(), Experience.LEVEL.length - 1)]);
                creature22.setHeading(player.getHeading());
                creature22.setReflection(player.getReflection());
                creature22.spawnMe(location == null ? Location.findAroundPosition(player, 50, 70) : location);
                creature22.setRunning();
                ((l2.gameserver.model.Summon)creature22).setFollowMode(true);
                if (creature22.getSkillLevel(4140) > 0) {
                    creature22.altUseSkill(SkillTable.getInstance().getInfo(4140, creature22.getSkillLevel(4140)), player);
                }
                if (creature22.getName().equalsIgnoreCase("Shadow")) {
                    creature22.addStatFunc(new FuncAdd(Stats.ABSORB_DAMAGE_PERCENT, 64, this, 15.0));
                }
                EffectsDAO.getInstance().restoreEffects((Playable)creature22);
                if (player.isOlyParticipant()) {
                    creature22.getEffectList().stopAllEffects();
                }
                creature22.setCurrentHpMp(creature22.getMaxHp(), creature22.getMaxMp(), false);
                if (this.a != SummonType.SIEGE_SUMMON) break;
                SiegeEvent siegeEvent = player.getEvent(SiegeEvent.class);
                siegeEvent.addSiegeSummon((SummonInstance)creature22);
                break;
            }
            case MERCHANT: {
                if (player.getPet() != null || player.isMounted()) {
                    return;
                }
                NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(this.getNpcId());
                MerchantInstance merchantInstance = new MerchantInstance(IdFactory.getInstance().getNextId(), npcTemplate);
                merchantInstance.setCurrentHp(merchantInstance.getMaxHp(), false);
                merchantInstance.setCurrentMp(merchantInstance.getMaxMp());
                merchantInstance.setHeading(player.getHeading());
                merchantInstance.setReflection(player.getReflection());
                merchantInstance.spawnMe(player.getLoc());
                ThreadPoolManager.getInstance().schedule(new GameObjectTasks.DeleteTask(merchantInstance), this.Du);
                break;
            }
            case NPC: {
                NpcUtils.spawnSingle(this.getNpcId(), player.getLoc(), player.getReflection(), (long)this.Du, player.getName());
            }
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    @Override
    public boolean isOffensive() {
        return this._targetType == Skill.SkillTargetType.TARGET_CORPSE;
    }

    private static final class SummonType
    extends Enum<SummonType> {
        public static final /* enum */ SummonType PET = new SummonType();
        public static final /* enum */ SummonType SIEGE_SUMMON = new SummonType();
        public static final /* enum */ SummonType AGATHION = new SummonType();
        public static final /* enum */ SummonType TRAP = new SummonType();
        public static final /* enum */ SummonType MERCHANT = new SummonType();
        public static final /* enum */ SummonType NPC = new SummonType();
        private static final /* synthetic */ SummonType[] a;

        public static SummonType[] values() {
            return (SummonType[])a.clone();
        }

        public static SummonType valueOf(String string) {
            return Enum.valueOf(SummonType.class, string);
        }

        private static /* synthetic */ SummonType[] a() {
            return new SummonType[]{PET, SIEGE_SUMMON, AGATHION, TRAP, MERCHANT, NPC};
        }

        static {
            a = SummonType.a();
        }
    }
}
