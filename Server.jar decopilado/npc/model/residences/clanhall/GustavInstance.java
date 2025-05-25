/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.model.AggroList$HateInfo
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.entity.events.objects.SpawnExObject
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.clanhall;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.events.impl.ClanHallSiegeEvent;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SpawnExObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import npc.model.residences.SiegeGuardInstance;
import npc.model.residences.clanhall._34SiegeGuard;

public class GustavInstance
extends SiegeGuardInstance
implements _34SiegeGuard {
    private AtomicBoolean l = new AtomicBoolean();

    public GustavInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onSpawn() {
        super.onSpawn();
        this.l.set(false);
        Functions.npcShoutCustomMessage((NpcInstance)this, (String)"clanhall.siege.GustavInstance.PREPARE_TO_DIE_FOREIGN_INVADERS_I_AM_GUSTAV_THE_ETERNAL_RULER_OF_THIS_FORTRESS_AND_I_HAVE_TAKEN_UP_MY_SWORD_TO_REPEL_THEE", (Object[])new Object[0]);
    }

    public void onDeath(Creature creature) {
        if (!this.l.get()) {
            Creature creature22;
            this.l.set(true);
            this.setCurrentHp(1.0, true);
            for (Creature creature22 : World.getAroundCharacters((GameObject)this)) {
                creature22.getAI().notifyEvent(CtrlEvent.EVT_FORGET_OBJECT, (Object)this);
            }
            ClanHallSiegeEvent clanHallSiegeEvent = (ClanHallSiegeEvent)this.getEvent(ClanHallSiegeEvent.class);
            if (clanHallSiegeEvent == null) {
                return;
            }
            creature22 = (SpawnExObject)clanHallSiegeEvent.getFirstObject("boss");
            for (int i = 0; i < 3; ++i) {
                final NpcInstance npcInstance = ((Spawner)creature22.getSpawns().get(i)).getFirstSpawned();
                Functions.npcSay((NpcInstance)npcInstance, (String)((_34SiegeGuard)npcInstance).teleChatSay());
                npcInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 4235, 1, 10000, 0L)});
                this._skillTask = ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

                    public void runImpl() throws Exception {
                        Location location = Location.findAroundPosition((int)177134, (int)-18807, (int)-2256, (int)50, (int)100, (int)npcInstance.getGeoIndex());
                        npcInstance.teleToLocation(location);
                        if (npcInstance == GustavInstance.this) {
                            npcInstance.reduceCurrentHp(npcInstance.getCurrentHp(), (Creature)npcInstance, null, false, false, false, false, false, false, false);
                        }
                    }
                }, 10000L);
            }
        } else {
            SiegeEvent siegeEvent;
            if (this._skillTask != null) {
                this._skillTask.cancel(false);
                this._skillTask = null;
            }
            if ((siegeEvent = (SiegeEvent)this.getEvent(SiegeEvent.class)) == null) {
                return;
            }
            siegeEvent.processStep(this.getMostDamagedClan());
            super.onDeath(creature);
        }
    }

    public Clan getMostDamagedClan() {
        int n;
        AggroList.HateInfo hateInfo22;
        ClanHallSiegeEvent clanHallSiegeEvent = (ClanHallSiegeEvent)this.getEvent(ClanHallSiegeEvent.class);
        Player player = null;
        HashMap<Player, Integer> hashMap = new HashMap<Player, Integer>();
        for (AggroList.HateInfo hateInfo22 : this.getAggroList().getPlayableMap().values()) {
            Playable playable = (Playable)hateInfo22.attacker;
            int n2 = hateInfo22.damage;
            if (playable.isPet() || playable.isSummon()) {
                player = playable.getPlayer();
            } else if (playable.isPlayer()) {
                player = (Player)playable;
            }
            if (player == null || clanHallSiegeEvent.getSiegeClan("attackers", player.getClan()) == null) continue;
            if (!hashMap.containsKey(player)) {
                hashMap.put(player, n2);
                continue;
            }
            n = (Integer)hashMap.get(player) + n2;
            hashMap.put(player, n);
        }
        int n3 = 0;
        hateInfo22 = null;
        for (Map.Entry entry : hashMap.entrySet()) {
            n = (Integer)entry.getValue();
            Player player2 = (Player)entry.getKey();
            if (n <= n3) continue;
            n3 = n;
            hateInfo22 = player2;
        }
        return hateInfo22 == null ? null : hateInfo22.getClan();
    }

    @Override
    public String teleChatSay() {
        return "This is unbelievable! Have I really been defeated? I shall return and take your head!";
    }

    public boolean isEffectImmune() {
        return true;
    }
}
