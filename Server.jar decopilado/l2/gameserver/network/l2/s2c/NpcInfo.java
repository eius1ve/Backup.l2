/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.AbstractNpcPacket;
import l2.gameserver.network.l2.s2c.NpcInfoType;

public class NpcInfo
extends AbstractNpcPacket {
    public NpcInfo(NpcInstance npcInstance, Creature creature) {
        this._npcId = npcInstance.getDisplayId() != 0 ? npcInstance.getDisplayId() : npcInstance.getTemplate().npcId;
        this._isAttackable = creature != null && npcInstance.isAutoAttackable(creature);
        this._rhand = npcInstance.getRightHandItem();
        this._lhand = npcInstance.getLeftHandItem();
        this._enchantEffect = 0;
        if (Config.SERVER_SIDE_NPC_NAME || npcInstance.getTemplate().displayId != 0 || npcInstance.getName() != npcInstance.getTemplate().name) {
            this._name = npcInstance.getName();
        }
        if (Config.SERVER_SIDE_NPC_TITLE || npcInstance.getTemplate().displayId != 0 || npcInstance.getTitle() != npcInstance.getTemplate().title) {
            this._title = npcInstance.getTitle();
        }
        if (Config.SERVER_SIDE_MONSTER_LEVEL_TITLE && npcInstance instanceof MonsterInstance) {
            this._title = "Lv " + npcInstance.getLevel() + (npcInstance.getAggroRange() > 0 ? "*" : "") + " " + npcInstance.getTitle();
        }
        this._showSpawnAnimation = npcInstance.getSpawnAnimation();
        this._showName = npcInstance.isShowName();
        this._state = npcInstance.getNpcState();
        this._isPet = false;
        this.setValues(npcInstance, NpcInfoType.VALUES);
    }

    public NpcInfo(Player player) {
        if (player.isInvisible()) {
            return;
        }
        this._npcId = player.getPolyId();
        this._isAttackable = false;
        this._enchantEffect = 0;
        this._showName = true;
        this._name = player.getName();
        this._title = player.getTitle();
        this._showSpawnAnimation = 0;
        Clan clan = player.getClan();
        Alliance alliance = clan == null ? null : clan.getAlliance();
        this.clan_id = clan == null ? 0 : clan.getClanId();
        this.clan_crest_id = clan == null ? 0 : clan.getCrestId();
        this.clan_large_crest_id = clan == null ? 0 : clan.getCrestLargeId();
        this.ally_id = alliance == null ? 0 : alliance.getAllyId();
        this.ally_crest_id = alliance == null ? 0 : alliance.getAllyCrestId();
        this._rhand = player.getInventory().getPaperdollItemId(5);
        this._lhand = player.getInventory().getPaperdollItemId(7);
        this._npcObjId = player.getObjectId();
        this._loc = player.getLoc();
        this._mAtkSpd = player.getMAtkSpd();
        this.karma = player.getKarma();
        this.pvp_flag = player.getPvpFlag();
        this._pAtkSpd = player.getPAtkSpd();
        this.running = player.isRunning() ? 1 : 0;
        this.incombat = player.isInCombat() ? 1 : 0;
        this.dead = player.isAlikeDead() ? 1 : 0;
        this.isFlying = player.isFlying();
        this._team = player.getTeam();
        this._isNameAbove = player.isNameAbove();
        this._isPet = false;
        this.abnormalEffects = player.getAbnormalEffects();
        this.moveAnimMod = (float)((double)player.getRunSpeed() * 1.0 / (double)player.getTemplate().baseRunSpd);
        this.atkSpeed = (float)player.getAttackSpeedMultiplier();
        this.addComponentType(NpcInfoType.VALUES);
        this.can_writeImpl = true;
    }

    public NpcInfo update() {
        return (NpcInfo)this.update(true);
    }

    @Override
    protected final void writeImpl() {
        if (!this.can_writeImpl) {
            return;
        }
        this.writeC(12);
        this.writeData();
    }
}
