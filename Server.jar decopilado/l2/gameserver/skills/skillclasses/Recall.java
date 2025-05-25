/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.skills.skillclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.StringUtils;

public class Recall
extends Skill {
    private final int Dn;
    private final boolean gg;
    private final boolean gh;
    private final Location X;
    private final List<Location> dj;

    public Recall(StatsSet statsSet) {
        super(statsSet);
        this.Dn = statsSet.getInteger("townId", 0);
        this.gg = statsSet.getBool("clanhall", false);
        this.gh = statsSet.getBool("castle", false);
        String string = statsSet.getString("random_loc", null);
        String[] stringArray = statsSet.getString("loc", "").split(";");
        this.X = stringArray.length == 3 ? new Location(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2])) : null;
        if (string == null) {
            this.dj = Collections.emptyList();
        } else {
            ArrayList<Location> arrayList = new ArrayList<Location>();
            for (String string2 : StringUtils.split((String)string, (String)" ")) {
                arrayList.add(Location.parseLoc(string2));
            }
            this.dj = arrayList;
        }
    }

    @Override
    public boolean checkCondition(Creature creature, Creature creature2, boolean bl, boolean bl2, boolean bl3) {
        Player player;
        if (this.getHitTime() == 200) {
            player = creature.getPlayer();
            if (this.gg) {
                if (player.getClan() == null || player.getClan().getHasHideout() == 0 || player.getReflection() == ReflectionManager.JAIL) {
                    creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(this._itemConsumeId[0]));
                    return false;
                }
            } else if (this.gh && (player.getClan() == null || player.getClan().getCastle() == 0 || player.getReflection() == ReflectionManager.JAIL)) {
                creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(this._itemConsumeId[0]));
                return false;
            }
        }
        if (creature.isPlayer()) {
            player = (Player)creature;
            if (player.getActiveWeaponFlagAttachment() != null) {
                creature.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD);
                return false;
            }
            if (player.isInDuel() || player.getTeam() != TeamType.NONE || this.q(player)) {
                creature.sendMessage(new CustomMessage("common.RecallInDuel", player, new Object[0]));
                return false;
            }
            if (player.isOlyParticipant()) {
                creature.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_THAT_SKILL_IN_A_GRAND_OLYMPIAD_MATCH);
                return false;
            }
        }
        if (creature.isInZone(Zone.ZoneType.no_escape) || this.Dn > 0 && creature.getReflection() != null && creature.getReflection().getCoreLoc() != null || creature.getReflection() == ReflectionManager.JAIL) {
            if (creature.isPlayer()) {
                creature.sendMessage(new CustomMessage("l2p.gameserver.skills.skillclasses.Recall.Here", (Player)creature, new Object[0]));
            }
            return false;
        }
        return super.checkCondition(creature, creature2, bl, bl2, bl3);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        for (Creature creature2 : list) {
            Player player;
            if (creature2 == null || (player = creature2.getPlayer()) == null || !player.getPlayerAccess().UseTeleport) continue;
            if (player.getActiveWeaponFlagAttachment() != null) {
                creature.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD);
                continue;
            }
            if (player.isFestivalParticipant()) {
                creature.sendMessage(new CustomMessage("l2p.gameserver.skills.skillclasses.Recall.Festival", (Player)creature, new Object[0]));
                continue;
            }
            if (player.isOlyParticipant()) {
                creature.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_SUMMON_PLAYERS_WHO_ARE_CURRENTLY_PARTICIPATING_IN_THE_GRAND_OLYMPIAD);
                return;
            }
            if (player.isInObserverMode()) {
                creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(this.getId(), this.getLevel()));
                return;
            }
            if (player.isInDuel() || player.getTeam() != TeamType.NONE) {
                creature.sendMessage(new CustomMessage("common.RecallInDuel", (Player)creature, new Object[0]));
                return;
            }
            if (this.X != null) {
                player.teleToLocation(this.X);
                return;
            }
            if (!this.dj.isEmpty()) {
                Location location = this.dj.get(Rnd.get(this.dj.size()));
                player.teleToLocation(location.x, location.y, location.z, 0);
                return;
            }
            if (this.gh) {
                player.teleToCastle();
                return;
            }
            if (this.gg) {
                player.teleToClanhall();
                return;
            }
            player.teleToClosestTown();
        }
        if (this.isSSPossible()) {
            creature.unChargeShots(this.isMagic());
        }
    }

    private boolean q(Player player) {
        return (Boolean)Scripts.getInstance().callScripts(player, "events.TvT2.PvPEvent", "isEventParticipant") != false;
    }
}
