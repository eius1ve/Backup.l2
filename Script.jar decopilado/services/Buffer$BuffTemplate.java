/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 */
package services;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import services.Buffer;

private static class Buffer.BuffTemplate {
    private final List<Skill> dJ;
    private final Buffer.BuffTarget a;
    private final List<Buffer.BuffTemplateConsume> dK;
    private final boolean hx;
    private final int bFz;
    private final int bFA;

    protected Buffer.BuffTemplate(List<Skill> list, Buffer.BuffTarget buffTarget, List<Buffer.BuffTemplateConsume> list2, boolean bl, int n, int n2) {
        this.dJ = list;
        this.a = buffTarget;
        this.dK = list2;
        this.hx = bl;
        this.bFz = n;
        this.bFA = n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void k(Creature creature) {
        if (creature == null) {
            return;
        }
        boolean bl = false;
        creature.block();
        try {
            for (Skill skill : this.dJ) {
                for (Effect effect : creature.getEffectList().getAllEffects()) {
                    if (effect.getSkill().getId() != skill.getId()) continue;
                    effect.exit();
                    bl = true;
                }
                if (bl) {
                    creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(skill.getDisplayId(), skill.getDisplayLevel()));
                }
                if (Config.ALT_NPC_BUFFER_EFFECT_TIME > 0L) {
                    skill.getEffects(creature, creature, false, false, (long)((double)Config.ALT_NPC_BUFFER_EFFECT_TIME * (creature.getPlayer().hasBonus() ? Config.ALT_NPC_BUFFER_PREMIUM_EFFECT_MUL : 1.0)), 1.0, false);
                    continue;
                }
                skill.getEffects(creature, creature, false, false);
            }
        }
        finally {
            creature.unblock();
        }
    }

    private Creature a(Creature creature) {
        switch (this.a) {
            case BUFF_PLAYER: {
                return creature.getPlayer();
            }
            case BUFF_PET: {
                return creature.getPet();
            }
        }
        return null;
    }

    private boolean z(Player player) {
        if (!this.hx) {
            for (Buffer.BuffTemplateConsume buffTemplateConsume : this.dK) {
                if (buffTemplateConsume.getFromLevel() > 0 && player.getLevel() < buffTemplateConsume.getFromLevel()) continue;
                if (buffTemplateConsume.isBonusRequired() && !player.hasBonus()) {
                    return false;
                }
                if (buffTemplateConsume.mayConsume(player)) continue;
                return false;
            }
        }
        for (Buffer.BuffTemplateConsume buffTemplateConsume : this.dK) {
            if (buffTemplateConsume.getFromLevel() > 0 && player.getLevel() < buffTemplateConsume.getFromLevel()) continue;
            if (buffTemplateConsume.isBonusRequired() && !player.hasBonus()) {
                return false;
            }
            if (!this.hx) {
                if (buffTemplateConsume.consume(player)) continue;
                return false;
            }
            if (!buffTemplateConsume.consume(player)) continue;
            return true;
        }
        return !this.hx;
    }

    public boolean canApply(Player player) {
        if (player.isInZone(Zone.ZoneType.SIEGE) || player.isInZone(Zone.ZoneType.water) || player.isInDuel()) {
            return false;
        }
        if (player.isOlyParticipant()) {
            return false;
        }
        if (player.isSitting()) {
            return false;
        }
        if (player.getLevel() < this.bFz) {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.minLevel", player, new Object[0]).addNumber((long)this.bFz));
            return false;
        }
        if (player.getLevel() > this.bFA) {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.maxLevel", player, new Object[0]).addNumber((long)this.bFA));
            return false;
        }
        return true;
    }

    public boolean apply(final Player player) {
        if (!this.canApply(player)) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(Buffer.hs));
            return false;
        }
        ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

            public void runImpl() throws Exception {
                Creature creature = this.a((Creature)player);
                if (creature == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                    return;
                }
                if (!this.z(player)) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                    return;
                }
                this.k(creature);
            }
        });
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean applySync(Player player) {
        Player player2 = player;
        synchronized (player2) {
            if (!this.canApply(player)) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(Buffer.hs));
                return false;
            }
            Creature creature = this.a((Creature)player);
            if (creature == null) {
                player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                return false;
            }
            if (!this.z(player)) {
                return false;
            }
            this.k(creature);
        }
        return true;
    }
}
