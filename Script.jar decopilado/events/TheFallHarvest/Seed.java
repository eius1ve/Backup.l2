/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package events.TheFallHarvest;

import events.TheFallHarvest.SquashAI;
import handler.items.ScriptItemHandler;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import npc.model.SquashInstance;

public class Seed
extends ScriptItemHandler {
    private static int[] H = new int[]{6389, 6390};
    private static int[] E = new int[]{12774, 12777};

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        Player player = (Player)playable;
        if (player.isInZone(Zone.ZoneType.RESIDENCE)) {
            return false;
        }
        if (player.isOlyParticipant()) {
            player.sendMessage(new CustomMessage("scripts.events.TheFallHarvest.Seed.CantSeedInStadium", player, new Object[0]));
            return false;
        }
        if (!player.getReflection().isDefault()) {
            player.sendMessage(new CustomMessage("scripts.events.TheFallHarvest.Seed.CantSeedInInstance", player, new Object[0]));
            return false;
        }
        NpcTemplate npcTemplate = null;
        int n = itemInstance.getItemId();
        for (int i = 0; i < H.length; ++i) {
            if (H[i] != n) continue;
            npcTemplate = NpcHolder.getInstance().getTemplate(E[i]);
            break;
        }
        if (npcTemplate == null) {
            return false;
        }
        if (!player.getInventory().destroyItem(itemInstance, 1L)) {
            return false;
        }
        SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
        simpleSpawner.setLoc(Location.findPointToStay((GameObject)player, (int)30, (int)70));
        NpcInstance npcInstance = simpleSpawner.doSpawn(true);
        npcInstance.setAI((CharacterAI)new SquashAI(npcInstance));
        ((SquashInstance)npcInstance).setSpawner(player);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new DeSpawnScheduleTimerTask(simpleSpawner)), 180000L);
        return true;
    }

    public int[] getItemIds() {
        return H;
    }

    public class DeSpawnScheduleTimerTask
    extends RunnableImpl {
        SimpleSpawner spawnedPlant = null;

        public DeSpawnScheduleTimerTask(SimpleSpawner simpleSpawner) {
            this.spawnedPlant = simpleSpawner;
        }

        public void runImpl() throws Exception {
            this.spawnedPlant.deleteAll();
        }
    }
}
