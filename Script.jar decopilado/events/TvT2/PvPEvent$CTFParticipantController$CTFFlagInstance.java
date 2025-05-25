/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.ItemStateFlags
 *  l2.gameserver.model.items.attachment.FlagItemAttachment
 *  l2.gameserver.model.items.attachment.ItemAttachment
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.skills.AbnormalEffect
 *  l2.gameserver.utils.ItemFunctions
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import events.TvT2.PvPEventProperties;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.ItemStateFlags;
import l2.gameserver.model.items.attachment.FlagItemAttachment;
import l2.gameserver.model.items.attachment.ItemAttachment;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.utils.ItemFunctions;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
private class PvPEvent.CTFParticipantController.CTFFlagInstance
extends MonsterInstance
implements FlagItemAttachment {
    private ItemInstance a;
    private final TeamType _team;
    private PvPEvent.CTFParticipantController a;

    public PvPEvent.CTFParticipantController.CTFFlagInstance(TeamType teamType, PvPEvent.CTFParticipantController cTFParticipantController2) {
        super(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(teamType == TeamType.BLUE ? PvPEventProperties.CTF_EVENT_BLUE_FLAG_NPC : (teamType == TeamType.RED ? PvPEventProperties.CTF_EVENT_RED_FLAG_NPC : -1)));
        this._team = teamType;
        this.a = ItemFunctions.createItem((int)(teamType == TeamType.BLUE ? PvPEventProperties.CTF_EVENT_BLUE_FLAG_ITEM : (teamType == TeamType.RED ? PvPEventProperties.CTF_EVENT_RED_FLAG_ITEM : -1)));
        this.a.setAttachment((ItemAttachment)this);
        this.a.getItemStateFlag().set((Enum)ItemStateFlags.STATE_CHANGED, false);
        this.a = cTFParticipantController2;
    }

    public void destroy() {
        Player player = GameObjectsStorage.getPlayer((int)this.a.getOwnerId());
        if (player != null) {
            player.getInventory().destroyItem(this.a);
            player.sendDisarmMessage(this.a);
            player.stopAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
        }
        this.a.setAttachment(null);
        this.a.deleteMe();
        this.a.delete();
        this.a = null;
        this.deleteMe();
    }

    public boolean isAutoAttackable(Creature creature) {
        return this.isAttackable(creature);
    }

    public boolean isAttackable(Creature creature) {
        return creature != null && creature.getTeam() != null && creature.getTeam() != TeamType.NONE && creature.getTeam() != this._team;
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (this.getDistance((GameObject)creature) > (double)PvPEventProperties.CTF_FLAG_MIN_ATTACK_DISTANCE) {
            d = 0.0;
        }
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    protected void onDeath(Creature creature) {
        Player player;
        boolean bl = PvPEvent.getInstance().j();
        if (this.isAttackable(creature) && (player = creature.getPlayer()) != null && (this._team == TeamType.RED && creature.isInZone(this.a.s) || this._team == TeamType.BLUE && creature.isInZone(this.a.r))) {
            player.getInventory().addItem(this.a);
            player.startAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
            player.getInventory().equipItem(this.a);
            this.a.getItemStateFlag().set((Enum)ItemStateFlags.STATE_CHANGED, false);
            if (bl && creature.getEffectList().getEffectsBySkill(p) == null) {
                p.getEffects(creature, creature, false, false, false);
            }
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("events.PvPEvent.TheCTFCaptureTheFlag", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
            this.decayMe();
            if (this._team == TeamType.RED) {
                for (Integer n : this.a.j.keySet()) {
                    Player player2 = GameObjectsStorage.getPlayer((int)n);
                    ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("events.PvPEvent.RedTeamCaptureTheFlag", player2, new Object[0]).addString(player.getName()).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true);
                    if (player2 == null) continue;
                    player2.sendPacket((IStaticPacket)exShowScreenMessage);
                }
            } else if (this._team == TeamType.BLUE) {
                for (Integer n : this.a.k.keySet()) {
                    Player player3 = GameObjectsStorage.getPlayer((int)n);
                    ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("events.PvPEvent.BlueTeamCaptureTheFlag", player3, new Object[0]).addString(player.getName()).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true);
                    if (player3 == null) continue;
                    player3.sendPacket((IStaticPacket)exShowScreenMessage);
                }
            }
            return;
        }
        this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
    }

    public int getOwnerOid() {
        return this.a.getOwnerId();
    }

    public void removeFlag(Player player) {
        boolean bl = PvPEvent.getInstance().j();
        if (player == null) {
            player = GameObjectsStorage.getPlayer((int)this.a.getOwnerId());
        }
        if (player != null) {
            player.getInventory().removeItem(this.a);
            player.sendDisarmMessage(this.a);
            player.stopAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
            if (bl && player.getEffectList().getEffectsBySkill(p) != null) {
                player.getEffectList().stopEffect(p);
            }
        }
        this.a.setOwnerId(0);
        this.a.delete();
        this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
        this.spawnMe(this.a.b(this._team));
    }

    public boolean canPickUp(Player player) {
        return false;
    }

    public void pickUp(Player player) {
    }

    public void setItem(ItemInstance itemInstance) {
        if (itemInstance != null) {
            itemInstance.setCustomFlags(39);
        }
    }

    public void onLogout(Player player) {
        player.getInventory().removeItem(this.a);
        this.a.setOwnerId(0);
        this.a.delete();
        this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
        this.spawnMe(this.a.b(this._team));
    }

    public void onDeath(Player player, Creature creature) {
        player.getInventory().removeItem(this.a);
        player.sendDisarmMessage(this.a);
        player.stopAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
        this.a.setOwnerId(0);
        this.a.delete();
        this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
        this.spawnMe(this.a.b(this._team));
    }

    public void onEnterPeace(Player player) {
    }

    public boolean canAttack(Player player) {
        player.sendPacket((IStaticPacket)SystemMsg.THAT_WEAPON_CANNOT_PERFORM_ANY_ATTACKS);
        return false;
    }

    public boolean canCast(Player player, Skill skill) {
        player.sendPacket((IStaticPacket)SystemMsg.THAT_WEAPON_CANNOT_USE_ANY_OTHER_SKILL_EXCEPT_THE_WEAPONS_SKILL);
        return false;
    }

    public boolean isEffectImmune() {
        return true;
    }

    public boolean isDebuffImmune() {
        return true;
    }
}
