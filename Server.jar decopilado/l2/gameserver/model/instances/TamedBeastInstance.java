/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.concurrent.Future;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.base.SpecialEffectState;
import l2.gameserver.model.instances.FeedableBeastInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcInfo;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;

public final class TamedBeastInstance
extends FeedableBeastInstance {
    private static final int nB = 2000;
    private static final int nC = 300;
    private static final int nD = 1200000;
    private static final int nE = 60000;
    private static final int nF = 20000;
    private static final int nG = 30000;
    private HardReference<Player> a = HardReferences.emptyRef();
    private int nH;
    private int nI = 1200000;
    private Future<?> O = null;
    private Future<?> P = null;
    private Skill[] _skills = new Skill[0];
    private static final int nJ = 5200;
    private static final int nK = 5195;
    private static final Skill[][] d = new Skill[6][];

    public TamedBeastInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this._hasRandomWalk = false;
        this._hasChatWindow = false;
        this._hasRandomAnimation = false;
        this.setUndying(SpecialEffectState.FALSE);
    }

    @Override
    public boolean isAutoAttackable(Creature creature) {
        return false;
    }

    private void bL() {
        this.nI += 20000;
        if (this.nI > 1200000) {
            this.nI = 1200000;
        }
    }

    public int getRemainingTime() {
        return this.nI;
    }

    public void setRemainingTime(int n) {
        this.nI = n;
    }

    public int getFoodType() {
        return this.nH;
    }

    public void setTameType(Player player) {
        switch (this.getNpcId()) {
            case 16013: {
                break;
            }
            case 16014: {
                break;
            }
            case 16015: {
                break;
            }
            case 16016: {
                break;
            }
            case 16017: {
                break;
            }
        }
        Skill[] skillArray = d[Rnd.get(d.length)];
        this._skills = (Skill[])skillArray.clone();
    }

    public void buffOwner() {
        if (!this.isInRange(this.getPlayer(), 300L)) {
            this.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, this.getPlayer());
            return;
        }
        int n = 0;
        for (Skill skill : this._skills) {
            if (this.getPlayer().getEffectList().getEffectsCountForSkill(skill.getId()) > 0) continue;
            ThreadPoolManager.getInstance().schedule(new Buff(this, this.getPlayer(), skill), n);
            n = n + skill.getHitTime() + 500;
        }
    }

    public void setFoodType(int n) {
        if (n > 0) {
            this.nH = n;
            if (this.O != null) {
                this.O.cancel(false);
            }
            this.O = ThreadPoolManager.getInstance().scheduleAtFixedRate(new CheckDuration(this), 60000L, 60000L);
        }
    }

    @Override
    protected void onDeath(Creature creature) {
        Player player;
        super.onDeath(creature);
        if (this.O != null) {
            this.O.cancel(false);
            this.O = null;
        }
        if (this.P != null) {
            this.P.cancel(false);
            this.P = null;
        }
        if ((player = this.getPlayer()) != null && player.getTrainedBeast() == this) {
            player.setTrainedBeast(null);
        }
        this.nH = 0;
        this.nI = 0;
    }

    @Override
    public Player getPlayer() {
        return this.a.get();
    }

    public void setOwner(Player player) {
        HardReference<Object> hardReference = this.a = player == null ? HardReferences.emptyRef() : player.getRef();
        if (player != null) {
            if (player.getTrainedBeast() != null) {
                player.getTrainedBeast().doDespawn();
            }
            player.setTrainedBeast(this);
            for (Player player2 : World.getAroundPlayers(this)) {
                player2.sendPacket((IStaticPacket)new NpcInfo(this, player2));
            }
            this.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
            this.P = ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    TamedBeastInstance.this.buffOwner();
                }
            }, 30000L, 30000L);
        } else {
            this.doDespawn();
        }
    }

    public void despawnWithDelay(int n) {
        ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                TamedBeastInstance.this.doDespawn();
            }
        }, n);
    }

    public void doDespawn() {
        Player player;
        this.stopMove();
        if (this.O != null) {
            this.O.cancel(false);
            this.O = null;
        }
        if (this.P != null) {
            this.P.cancel(false);
            this.P = null;
        }
        if ((player = this.getPlayer()) != null && player.getTrainedBeast() == this) {
            player.setTrainedBeast(null);
        }
        this.setTarget(null);
        this.nH = 0;
        this.nI = 0;
        this.onDecay();
    }

    static {
        TamedBeastInstance.d[0] = new Skill[]{SkillTable.getInstance().getInfo(5186, 1), SkillTable.getInstance().getInfo(5188, 1), SkillTable.getInstance().getInfo(5189, 1), SkillTable.getInstance().getInfo(5187, 1), SkillTable.getInstance().getInfo(5191, 1), SkillTable.getInstance().getInfo(5195, 1)};
        TamedBeastInstance.d[1] = new Skill[]{SkillTable.getInstance().getInfo(5192, 1), SkillTable.getInstance().getInfo(5193, 1), SkillTable.getInstance().getInfo(5201, 1), SkillTable.getInstance().getInfo(5194, 1), SkillTable.getInstance().getInfo(5190, 1), SkillTable.getInstance().getInfo(5200, 1)};
        TamedBeastInstance.d[2] = new Skill[]{SkillTable.getInstance().getInfo(5186, 1), SkillTable.getInstance().getInfo(5187, 1), SkillTable.getInstance().getInfo(5188, 1), SkillTable.getInstance().getInfo(5189, 1), SkillTable.getInstance().getInfo(5191, 1), SkillTable.getInstance().getInfo(5195, 1)};
        TamedBeastInstance.d[3] = new Skill[]{SkillTable.getInstance().getInfo(5192, 1), SkillTable.getInstance().getInfo(5193, 1), SkillTable.getInstance().getInfo(5201, 1), SkillTable.getInstance().getInfo(5194, 1), SkillTable.getInstance().getInfo(5190, 1), SkillTable.getInstance().getInfo(5200, 1)};
        TamedBeastInstance.d[4] = new Skill[]{SkillTable.getInstance().getInfo(5186, 1), SkillTable.getInstance().getInfo(5187, 1), SkillTable.getInstance().getInfo(5188, 1), SkillTable.getInstance().getInfo(5189, 1), SkillTable.getInstance().getInfo(5191, 1), SkillTable.getInstance().getInfo(5195, 1)};
        TamedBeastInstance.d[5] = new Skill[]{SkillTable.getInstance().getInfo(5192, 1), SkillTable.getInstance().getInfo(5193, 1), SkillTable.getInstance().getInfo(5201, 1), SkillTable.getInstance().getInfo(5194, 1), SkillTable.getInstance().getInfo(5190, 1), SkillTable.getInstance().getInfo(5200, 1)};
    }

    public static class Buff
    extends RunnableImpl {
        private NpcInstance w;
        private Player f;
        private Skill _skill;

        public Buff(NpcInstance npcInstance, Player player, Skill skill) {
            this.w = npcInstance;
            this.f = player;
            this._skill = skill;
        }

        @Override
        public void runImpl() throws Exception {
            if (this.w != null) {
                this.w.doCast(this._skill, this.f, true);
            }
        }
    }

    private static class CheckDuration
    extends RunnableImpl {
        private TamedBeastInstance a;

        CheckDuration(TamedBeastInstance tamedBeastInstance) {
            this.a = tamedBeastInstance;
        }

        @Override
        public void runImpl() throws Exception {
            Player player = this.a.getPlayer();
            if (player == null || !player.isOnline()) {
                this.a.doDespawn();
                return;
            }
            if (this.a.getDistance(player) > 2000.0) {
                this.a.doDespawn();
                return;
            }
            int n = this.a.getFoodType();
            this.a.setRemainingTime(this.a.getRemainingTime() - 60000);
            ItemInstance itemInstance = null;
            int n2 = this.a.getItemIdBySkillId(n);
            if (n2 > 0) {
                itemInstance = player.getInventory().getItemByItemId(n2);
            }
            if (itemInstance != null && itemInstance.getCount() >= 1L) {
                this.a.bL();
                player.getInventory().destroyItem(itemInstance, 1L);
            } else if (this.a.getRemainingTime() < 900000) {
                this.a.setRemainingTime(-1);
            }
            if (this.a.getRemainingTime() <= 0) {
                this.a.doDespawn();
            }
        }
    }
}
