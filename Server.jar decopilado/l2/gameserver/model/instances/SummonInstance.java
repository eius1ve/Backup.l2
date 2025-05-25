/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.concurrent.Future;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SetSummonRemainTime;
import l2.gameserver.network.l2.s2c.SummonInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.npc.NpcTemplate;

public class SummonInstance
extends Summon {
    public final int CYCLE = 5000;
    private int nu;
    private double u = 0.0;
    private int nv;
    private int nw;
    private int nx;
    private Future<?> N;
    private int ny;
    private int nz;
    private int nA;

    public SummonInstance(int n, NpcTemplate npcTemplate, Player player, int n2, int n3, int n4, int n5, Skill skill) {
        super(n, npcTemplate, player);
        this.setName(npcTemplate.name);
        if (Config.ALT_SET_TITLE_OWNER_TO_SUMMON) {
            this.setTitle(player.getName());
        }
        this.nz = this.nA = n2;
        this.nv = n3;
        this.nw = n4;
        this.ny = this.nx = n5;
        this.nu = skill.getDisplayId();
        this.N = ThreadPoolManager.getInstance().schedule(new Lifetime(), 5000L);
    }

    public HardReference<SummonInstance> getRef() {
        return super.getRef();
    }

    @Override
    public int getSummonType() {
        return 1;
    }

    @Override
    public int getCurrentFed() {
        return this.nz;
    }

    @Override
    public int getMaxFed() {
        return this.nA;
    }

    public void setExpPenalty(double d) {
        this.u = d;
    }

    @Override
    public double getExpPenalty() {
        return this.u;
    }

    @Override
    protected L2GameServerPacket createInfoPacketForOthers(Player player, boolean bl) {
        return new SummonInfo(this, player).update(bl);
    }

    @Override
    protected void onDeath(Creature creature) {
        super.onDeath(creature);
        this.saveEffects();
        if (this.N != null) {
            this.N.cancel(false);
            this.N = null;
        }
    }

    public int getItemConsumeIdInTime() {
        return this.nv;
    }

    public int getItemConsumeCountInTime() {
        return this.nw;
    }

    public int getItemConsumeDelay() {
        return this.nx;
    }

    protected synchronized void stopDisappear() {
        if (this.N != null) {
            this.N.cancel(false);
            this.N = null;
        }
    }

    @Override
    public void unSummon() {
        this.stopDisappear();
        super.unSummon();
    }

    @Override
    public final int getLevel() {
        return this.getTemplate() != null ? this.getTemplate().level : 0;
    }

    @Override
    public void displayGiveDamageMessage(Creature creature, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        Player player = this.getPlayer();
        if (player == null) {
            return;
        }
        if (bl) {
            player.sendPacket((IStaticPacket)SystemMsg.SUMMONED_MONSTERS_CRITICAL_HIT);
        }
        if (bl2) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1S_ATTACK_WENT_ASTRAY).addName(this));
        } else if (!creature.isInvul()) {
            player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_DONE_S3_POINTS_OF_DAMAGE_TO_C2_S4).addName(this)).addName(creature)).addNumber(n)).addVisibleDamage(this, creature, -n));
        }
    }

    @Override
    public void displayReceiveDamageMessage(Creature creature, int n) {
        Player player = this.getPlayer();
        player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_RECEIVED_S3_DAMAGE_FROM_C2).addName(this)).addName(creature)).addNumber((long)n));
    }

    @Override
    public int getEffectIdentifier() {
        return this.nu;
    }

    @Override
    public boolean isSummon() {
        return true;
    }

    @Override
    public long getWearedMask() {
        return WeaponTemplate.WeaponType.SWORD.mask();
    }

    class Lifetime
    extends RunnableImpl {
        Lifetime() {
        }

        @Override
        public void runImpl() throws Exception {
            Player player = SummonInstance.this.getPlayer();
            if (player == null) {
                SummonInstance.this.N = null;
                SummonInstance.this.unSummon();
                return;
            }
            int n = SummonInstance.this.isInCombat() ? 5000 : 1250;
            SummonInstance.this.nz -= n;
            if (SummonInstance.this.nz <= 0) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_SERVITOR_HAS_VANISHED_YOULL_NEED_TO_SUMMON_A_NEW_ONE);
                SummonInstance.this.N = null;
                SummonInstance.this.unSummon();
                return;
            }
            SummonInstance.this.ny -= n;
            if (SummonInstance.this.nv > 0 && SummonInstance.this.nw > 0 && SummonInstance.this.ny <= 0) {
                if (player.getInventory().destroyItemByItemId(SummonInstance.this.getItemConsumeIdInTime(), SummonInstance.this.getItemConsumeCountInTime())) {
                    SummonInstance.this.ny = SummonInstance.this.nx;
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.A_SUMMONED_MONSTER_USES_S1).addItemName(SummonInstance.this.getItemConsumeIdInTime()));
                } else {
                    player.sendPacket((IStaticPacket)SystemMsg.SINCE_YOU_DO_NOT_HAVE_ENOUGH_ITEMS_TO_MAINTAIN_THE_SERVITORS_STAY_THE_SERVITOR_HAS_DISAPPEARED);
                    SummonInstance.this.unSummon();
                    return;
                }
            }
            player.sendPacket((IStaticPacket)new SetSummonRemainTime(SummonInstance.this));
            SummonInstance.this.N = ThreadPoolManager.getInstance().schedule(this, 5000L);
        }
    }
}
