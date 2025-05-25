/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;

public class ImperialGravekeeper
extends Fighter {
    private int V;
    private int W;
    private int X;

    public ImperialGravekeeper(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.V = 1;
        this.W = 50;
        this.X = 80;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (!npcInstance.isInZonePeace()) {
            if (npcInstance.getCurrentHpPercents() <= (double)this.W) {
                if (this.V == 1 || this.V == 3) {
                    this.V = 2;
                    for (Player player : World.getAroundPlayers((GameObject)npcInstance, (int)200, (int)200)) {
                        if (player == null) continue;
                        player.teleToLocation(179520, 6464, -2706);
                    }
                } else {
                    this.V = 3;
                    for (Player player : World.getAroundPlayers((GameObject)npcInstance, (int)200, (int)200)) {
                        if (player == null) continue;
                        player.teleToLocation(171104, 6496, -2706);
                    }
                }
            }
            if (this.W == 50) {
                this.W = 30;
            } else if (this.W == 30) {
                this.W = -1;
            }
        }
        if (npcInstance.getCurrentHpPercents() <= (double)this.X) {
            this.X = this.X == 80 ? 40 : (this.X == 40 ? 20 : -1);
            NpcUtils.spawnSingle((int)27180, (Location)Location.findAroundPosition((GameObject)npcInstance, (int)150), (Reflection)npcInstance.getReflection(), (long)120000L);
            NpcUtils.spawnSingle((int)27180, (Location)Location.findAroundPosition((GameObject)npcInstance, (int)150), (Reflection)npcInstance.getReflection(), (long)120000L);
            NpcUtils.spawnSingle((int)27180, (Location)Location.findAroundPosition((GameObject)npcInstance, (int)150), (Reflection)npcInstance.getReflection(), (long)120000L);
            NpcUtils.spawnSingle((int)27180, (Location)Location.findAroundPosition((GameObject)npcInstance, (int)150), (Reflection)npcInstance.getReflection(), (long)120000L);
        }
        if (npcInstance.getCurrentHpPercents() > 50.0) {
            this.W = 50;
        } else if (npcInstance.getCurrentHpPercents() > 30.0) {
            this.W = 30;
        }
        if (npcInstance.getCurrentHpPercents() > 80.0) {
            this.X = 80;
        } else if (npcInstance.getCurrentHpPercents() > 40.0) {
            this.X = 40;
        } else if (npcInstance.getCurrentHpPercents() > 20.0) {
            this.X = 20;
        }
        super.onEvtAttacked(creature, n);
    }
}
