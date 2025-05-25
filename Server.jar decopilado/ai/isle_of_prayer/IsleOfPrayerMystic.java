/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.isle_of_prayer;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Mystic;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;

public class IsleOfPrayerMystic
extends Mystic {
    private boolean L = true;
    private static final int[] B = new int[]{18364, 18365, 18366};
    private static final int bd = 9593;
    private static final int be = 9594;
    private static final int bf = 9596;

    public IsleOfPrayerMystic(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        Party party;
        NpcInstance npcInstance = this.getActor();
        if (this.L && creature.isPlayable() && creature.getPlayer() != null && (party = creature.getPlayer().getParty()) != null && party.getMemberCount() > 2) {
            this.L = false;
            for (int i = 0; i < 2; ++i) {
                try {
                    MonsterInstance monsterInstance = new MonsterInstance(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(B[Rnd.get((int)B.length)]));
                    monsterInstance.setSpawnedLoc(((MonsterInstance)npcInstance).getMinionPosition());
                    monsterInstance.setReflection(npcInstance.getReflection());
                    monsterInstance.setCurrentHpMp((double)monsterInstance.getMaxHp(), (double)monsterInstance.getMaxMp(), true);
                    monsterInstance.spawnMe(monsterInstance.getSpawnedLoc());
                    monsterInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)1, (int)100));
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtDead(Creature creature) {
        Player player;
        this.L = true;
        if (creature != null && (player = creature.getPlayer()) != null) {
            NpcInstance npcInstance = this.getActor();
            switch (npcInstance.getNpcId()) {
                case 22261: {
                    if (!Rnd.chance((int)12)) break;
                    npcInstance.dropItem(player, 9594, 1L);
                    break;
                }
                case 22265: {
                    if (!Rnd.chance((int)6)) break;
                    npcInstance.dropItem(player, 9596, 1L);
                    break;
                }
                case 22260: {
                    if (!Rnd.chance((int)23)) break;
                    npcInstance.dropItem(player, 9593, 1L);
                    break;
                }
                case 22262: {
                    if (!Rnd.chance((int)12)) break;
                    npcInstance.dropItem(player, 9594, 1L);
                    break;
                }
                case 22264: {
                    if (!Rnd.chance((int)12)) break;
                    npcInstance.dropItem(player, 9594, 1L);
                    break;
                }
                case 22266: {
                    if (!Rnd.chance((int)5)) break;
                    npcInstance.dropItem(player, 9596, 1L);
                    break;
                }
                case 22257: {
                    if (!Rnd.chance((int)21)) break;
                    npcInstance.dropItem(player, 9593, 1L);
                    break;
                }
                case 22258: {
                    if (!Rnd.chance((int)22)) break;
                    npcInstance.dropItem(player, 9593, 1L);
                }
            }
        }
        super.onEvtDead(creature);
    }
}
