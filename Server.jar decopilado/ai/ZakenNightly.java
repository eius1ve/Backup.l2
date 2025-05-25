/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.GameTimeController
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.listener.GameListener
 *  l2.gameserver.listener.game.OnDayNightChangeListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.network.l2.s2c.PlaySound$Type
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 */
package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.GameTimeController;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.listener.GameListener;
import l2.gameserver.listener.game.OnDayNightChangeListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;

public class ZakenNightly
extends Fighter {
    private static final int[] w = new int[]{29023, 29024, 29026, 29027};
    private static final Location[] c = new Location[]{new Location(55272, 219112, -3496), new Location(56296, 218072, -3496), new Location(54232, 218072, -3496), new Location(54248, 220136, -3496), new Location(56296, 220136, -3496), new Location(55272, 219112, -3224), new Location(56296, 218072, -3224), new Location(54232, 218072, -3224), new Location(54248, 220136, -3224), new Location(56296, 220136, -3224), new Location(55272, 219112, -2952), new Location(56296, 218072, -2952), new Location(54232, 218072, -2952), new Location(54248, 220136, -2952), new Location(56296, 220136, -2952)};
    private static final long E = 15000L;
    private static final int aO = 2;
    private static final long F = 90000L;
    private static final long G = 240000L;
    private static final long H = 30000L;
    private long I = 0L;
    private List<NpcInstance> o = new ArrayList<NpcInstance>();
    private long J = 0L;
    private HardReference<Player> f = HardReferences.emptyRef();
    private long K = 0L;

    public ZakenNightly(NpcInstance npcInstance) {
        super(npcInstance);
        this.MAX_PURSUE_RANGE = 0x3FFFFFFF;
        GameTimeController.getInstance().addListener((GameListener)new s_zaken_regen());
    }

    private void h() {
        this.f = HardReferences.emptyRef();
        List list = this.getActor().getAggroList().getHateList(900);
        Collections.shuffle(list);
        for (Creature creature : list) {
            if (!creature.isPlayer() || creature.isDead() || !GeoEngine.canSeeTarget((GameObject)this.getActor(), (GameObject)creature, (boolean)false)) continue;
            this.f = creature.getPlayer().getRef();
        }
    }

    protected void onEvtFinishCasting(Skill skill, Creature creature) {
        Player player = (Player)this.f.get();
        if (skill != null) {
            if (player != null && skill.getId() == 4216) {
                player.teleToLocation(c[Rnd.get((int)c.length)]);
                this.getActor().getAggroList().remove((Creature)player, true);
                this.f = HardReferences.emptyRef();
            } else if (skill.getId() == 4222) {
                this.getActor().teleToLocation(c[Rnd.get((int)c.length)]);
                this.getActor().setSpawnedLoc(this.getActor().getLoc());
                this.getActor().getAggroList().clear(true);
                for (int i = 0; i < 10; ++i) {
                    this.getActor().getReflection().addSpawnWithoutRespawn(w[Rnd.get((int)w.length)], this.getActor().getLoc(), 500);
                }
            }
        }
        super.onEvtFinishCasting(skill, creature);
    }

    protected void thinkAttack() {
        if (this.I == 0L) {
            this.I = System.currentTimeMillis() + 30000L;
        }
        if (this.J == 0L) {
            this.J = System.currentTimeMillis() + 90000L;
        }
        if (this.K == 0L) {
            this.K = System.currentTimeMillis() + 150000L;
        }
        if (this.I + 15000L < System.currentTimeMillis()) {
            int n = 0;
            for (NpcInstance npcInstance : this.o) {
                if (npcInstance.isDead()) continue;
                ++n;
            }
            this.I = System.currentTimeMillis();
            if (n < 30) {
                for (int i = 0; i < 2; ++i) {
                    NpcInstance npcInstance;
                    npcInstance = this.getActor().getReflection().addSpawnWithoutRespawn(w[Rnd.get((int)w.length)], this.getActor().getLoc(), 350);
                    npcInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)this.getActor().getAI().getAttackTarget(), (Object)5000);
                    this.o.add(npcInstance);
                }
            }
        }
        if (Config.ZAKEN_USE_TELEPORT) {
            if (this.J + 90000L < System.currentTimeMillis()) {
                this.h();
                Player player = (Player)this.f.get();
                if (player != null) {
                    this.getActor().doCast(SkillTable.getInstance().getInfo(4216, 1), (Creature)player, false);
                }
                this.J = System.currentTimeMillis();
            }
            if (this.K + 240000L < System.currentTimeMillis()) {
                this.getActor().doCast(SkillTable.getInstance().getInfo(4222, 1), (Creature)this.getActor(), false);
                this.K = System.currentTimeMillis();
            }
        }
        super.thinkAttack();
    }

    protected void onEvtDead(Creature creature) {
        this.getActor().broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_D", 1, this.getActor().getObjectId(), this.getActor().getLoc())});
        super.onEvtDead(creature);
    }

    private class s_zaken_regen
    implements OnDayNightChangeListener {
        private final Skill h = SkillTable.getInstance().getInfo(4227, 1);

        private s_zaken_regen() {
            if (GameTimeController.getInstance().isNowNight()) {
                this.onNight();
            } else {
                this.onDay();
            }
        }

        public void onDay() {
            ZakenNightly.this.getActor().getEffectList().stopEffect(this.h);
        }

        public void onNight() {
            this.h.getEffects((Creature)ZakenNightly.this.getActor(), (Creature)ZakenNightly.this.getActor(), false, false);
        }
    }
}
