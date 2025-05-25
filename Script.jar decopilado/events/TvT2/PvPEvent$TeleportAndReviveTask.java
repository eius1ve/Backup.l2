/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.Revive
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.tuple.Pair
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import events.TvT2.PvPEventProperties;
import java.util.HashSet;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.Revive;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.tuple.Pair;

private class PvPEvent.TeleportAndReviveTask
extends RunnableImpl {
    private final HardReference<Player> i;
    private Location _loc;
    private Reflection a;

    public PvPEvent.TeleportAndReviveTask(Player player, Location location, Reflection reflection) {
        this.i = player.getRef();
        this._loc = location;
        this.a = reflection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runImpl() throws Exception {
        Player player = (Player)this.i.get();
        if (player == null) {
            return;
        }
        Player player2 = player;
        synchronized (player2) {
            player.teleToLocation(this._loc, this.a);
            if (!player.isConnected()) {
                player.onTeleported();
            }
            if (player.isDead()) {
                player.setCurrentHp((double)player.getMaxHp(), true, true);
                player.setCurrentCp((double)player.getMaxCp());
                player.setCurrentMp((double)player.getMaxMp());
                player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
                HashSet<Skill> hashSet = new HashSet<Skill>();
                for (Effect effect : player.getEffectList().getAllEffects()) {
                    if (!effect.getSkill().isOffensive()) continue;
                    hashSet.add(effect.getSkill());
                }
                for (Skill skill : hashSet) {
                    for (Effect effect : player.getEffectList().getEffectsBySkill(skill)) {
                        effect.exit();
                    }
                }
                if (PvPEvent.getInstance().i()) {
                    o.getEffects((Creature)player, (Creature)player, false, false, false);
                }
                if (!PvPEventProperties.PVP_EVENTS_MAGE_BUFF_ON_REVIVE.isEmpty() || !PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE.isEmpty()) {
                    for (Pair pair : player.isMageClass() ? PvPEventProperties.PVP_EVENTS_MAGE_BUFF_ON_REVIVE : PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE) {
                        Skill skill = SkillTable.getInstance().getInfo(((Integer)pair.getLeft()).intValue(), ((Integer)pair.getRight()).intValue());
                        skill.getEffects((Creature)player, (Creature)player, false, false, PvPEventProperties.PVP_EVENTS_BUFF_ON_REVIVE_TIME, 1.0, false);
                    }
                }
            }
        }
    }
}
