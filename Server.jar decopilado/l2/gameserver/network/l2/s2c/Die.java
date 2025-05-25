/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.RestartType;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Die
extends L2GameServerPacket {
    private int fW;
    private boolean ei;
    private boolean ej;
    private Map<RestartType, Boolean> bs = new HashMap<RestartType, Boolean>(RestartType.VALUES.length);

    public Die(Creature creature) {
        Player player;
        this.fW = creature.getObjectId();
        boolean bl = this.ei = !creature.isDead();
        if (creature.isMonster()) {
            this.ej = ((MonsterInstance)creature).isSweepActive();
        } else if (creature.isPlayer() && !(player = (Player)creature).isOlyCompetitionStarted() && !player.isResurectProhibited()) {
            this.a(RestartType.FIXED, Config.ALT_REVIVE_WINDOW_TO_TOWN || player.getPlayerAccess().ResurectFixed || Config.SERVICE_FEATHER_REVIVE_ENABLE && (!Config.SERVICE_DISABLE_FEATHER_ON_SIEGES_AND_EPIC || !player.isOnSiegeField() && !player.isInZone(Zone.ZoneType.epic)) && player.getInventory().getCountOf(Config.SERVICE_FEATHER_ITEM_ID) > 0L && !player.getInventory().isLockedItem(Config.SERVICE_FEATHER_ITEM_ID));
            this.a(RestartType.TO_VILLAGE, true);
            Clan clan = null;
            if (this.a(RestartType.TO_VILLAGE)) {
                clan = player.getClan();
            }
            if (clan != null) {
                this.a(RestartType.TO_CLANHALL, clan.getHasHideout() > 0);
                this.a(RestartType.TO_CASTLE, clan.getCastle() > 0);
            }
            for (GlobalEvent globalEvent : creature.getEvents()) {
                globalEvent.checkRestartLocs(player, this.bs);
            }
        }
    }

    @Override
    protected final void writeImpl() {
        if (this.ei) {
            return;
        }
        this.writeC(0);
        this.writeD(this.fW);
        this.writeD(this.a(RestartType.TO_VILLAGE));
        this.writeD(this.a(RestartType.TO_CLANHALL));
        this.writeD(this.a(RestartType.TO_CASTLE));
        this.writeD(this.a(RestartType.TO_FLAG));
        this.writeD(this.ej ? 1 : 0);
        this.writeD(this.a(RestartType.FIXED));
        this.writeD(this.a(RestartType.TO_FORTRESS));
        this.writeD(0);
        this.writeD(0);
        this.writeC(0);
        this.writeD(this.a(RestartType.AGATHION));
        this.writeD(0);
    }

    private void a(RestartType restartType, boolean bl) {
        this.bs.put(restartType, bl);
    }

    private boolean a(RestartType restartType) {
        Boolean bl = this.bs.get((Object)restartType);
        return bl != null && bl != false;
    }
}
