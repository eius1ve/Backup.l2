/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.Iterator;
import java.util.List;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.events.objects.ZoneObject;
import l2.gameserver.model.instances.residences.SiegeFlagInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncMul;
import l2.gameserver.templates.StatsSet;

public class SummonSiegeFlag
extends Skill {
    private final FlagType a;
    private final double ao;

    public SummonSiegeFlag(StatsSet statsSet) {
        super(statsSet);
        this.a = statsSet.getEnum("flagType", FlagType.class);
        this.ao = statsSet.getDouble("advancedMultiplier", 1.0);
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        if (!creature.isPlayer()) {
            return false;
        }
        if (!super.checkCondition(creature, creature2, bl, bl2, bl3)) {
            return false;
        }
        Player player = (Player)creature;
        if (player.getClan() == null || !player.isClanLeader()) {
            return false;
        }
        switch (this.a) {
            case DESTROY: {
                break;
            }
            case OUTPOST: 
            case NORMAL: 
            case ADVANCED: {
                if (player.isInZone(Zone.ZoneType.RESIDENCE)) {
                    player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_CANNOT_SET_UP_A_BASE_HERE, new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this)});
                    return false;
                }
                SiegeEvent siegeEvent = creature.getEvent(SiegeEvent.class);
                if (siegeEvent == null) {
                    player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_CANNOT_SET_UP_A_BASE_HERE, new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this)});
                    return false;
                }
                boolean bl4 = false;
                List list = siegeEvent.getObjects("flag_zones");
                for (ZoneObject zoneObject : list) {
                    if (!player.isInZone(zoneObject.getZone())) continue;
                    bl4 = true;
                }
                if (!bl4) {
                    player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_CANNOT_SET_UP_A_BASE_HERE, new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this)});
                    return false;
                }
                Iterator<Object> iterator = siegeEvent.getSiegeClan("attackers", player.getClan());
                if (iterator == null) {
                    player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_CANNOT_SUMMON_THE_ENCAMPMENT_BECAUSE_YOU_ARE_NOT_A_MEMBER_OF_THE_SIEGE_CLAN_INVOLVED_IN_THE_CASTLE__FORTRESS__HIDEOUT_SIEGE, new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this)});
                    return false;
                }
                if (((SiegeClanObject)((Object)iterator)).getFlag() == null) break;
                player.sendPacket(new IStaticPacket[]{SystemMsg.AN_OUTPOST_OR_HEADQUARTERS_CANNOT_BE_BUILT_BECAUSE_ONE_ALREADY_EXISTS, new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this)});
                return false;
            }
        }
        return true;
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        Player player = (Player)creature;
        Clan clan = player.getClan();
        if (clan == null || !player.isClanLeader()) {
            return;
        }
        SiegeEvent siegeEvent = creature.getEvent(SiegeEvent.class);
        if (siegeEvent == null) {
            return;
        }
        Object s = siegeEvent.getSiegeClan("attackers", clan);
        if (s == null) {
            return;
        }
        switch (this.a) {
            case DESTROY: {
                ((SiegeClanObject)s).deleteFlag();
                break;
            }
            default: {
                if (((SiegeClanObject)s).getFlag() != null) {
                    return;
                }
                SiegeFlagInstance siegeFlagInstance = (SiegeFlagInstance)NpcHolder.getInstance().getTemplate(this.a == FlagType.OUTPOST ? 36590 : 35062).getNewInstance();
                siegeFlagInstance.setClan((SiegeClanObject)s);
                siegeFlagInstance.addEvent(siegeEvent);
                if (this.a == FlagType.ADVANCED) {
                    siegeFlagInstance.addStatFunc(new FuncMul(Stats.MAX_HP, 80, siegeFlagInstance, this.ao));
                }
                siegeFlagInstance.setCurrentHpMp(siegeFlagInstance.getMaxHp(), siegeFlagInstance.getMaxMp(), true);
                siegeFlagInstance.setHeading(player.getHeading());
                int n = (int)((double)player.getX() + 100.0 * Math.cos(player.headingToRadians(player.getHeading() - 32768)));
                int n2 = (int)((double)player.getY() + 100.0 * Math.sin(player.headingToRadians(player.getHeading() - 32768)));
                siegeFlagInstance.spawnMe(GeoEngine.moveCheck(player.getX(), player.getY(), player.getZ(), n, n2, player.getGeoIndex()));
                ((SiegeClanObject)s).setFlag(siegeFlagInstance);
            }
        }
    }

    public static final class FlagType
    extends Enum<FlagType> {
        public static final /* enum */ FlagType DESTROY = new FlagType();
        public static final /* enum */ FlagType NORMAL = new FlagType();
        public static final /* enum */ FlagType ADVANCED = new FlagType();
        public static final /* enum */ FlagType OUTPOST = new FlagType();
        private static final /* synthetic */ FlagType[] a;

        public static FlagType[] values() {
            return (FlagType[])a.clone();
        }

        public static FlagType valueOf(String string) {
            return Enum.valueOf(FlagType.class, string);
        }

        private static /* synthetic */ FlagType[] a() {
            return new FlagType[]{DESTROY, NORMAL, ADVANCED, OUTPOST};
        }

        static {
            a = FlagType.a();
        }
    }
}
