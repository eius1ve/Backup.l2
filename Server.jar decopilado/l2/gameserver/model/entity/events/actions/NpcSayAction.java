/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.actions;

import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.s2c.NpcSay;
import l2.gameserver.utils.MapRegionUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NpcSayAction
implements EventAction {
    private int _npcId;
    private int lt;
    private ChatType a;
    private NpcString a;

    public NpcSayAction(int n, int n2, ChatType chatType, NpcString npcString) {
        this._npcId = n;
        this.lt = n2;
        this.a = chatType;
        this.a = npcString;
    }

    @Override
    public void call(GlobalEvent globalEvent) {
        NpcInstance npcInstance = GameObjectsStorage.getByNpcId(this._npcId);
        if (npcInstance == null) {
            return;
        }
        if (this.lt <= 0) {
            int n = MapRegionUtils.regionX(npcInstance);
            int n2 = MapRegionUtils.regionY(npcInstance);
            int n3 = Config.SHOUT_OFFSET;
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                if (npcInstance.getReflection() != player.getReflection()) continue;
                int n4 = MapRegionUtils.regionX(player);
                int n5 = MapRegionUtils.regionY(player);
                if (n4 < n - n3 || n4 > n + n3 || n5 < n2 - n3 || n5 > n2 + n3) continue;
                this.b(npcInstance, player);
            }
        } else {
            for (Player player : World.getAroundPlayers(npcInstance, this.lt, Math.max(this.lt / 2, 200))) {
                if (npcInstance.getReflection() != player.getReflection()) continue;
                this.b(npcInstance, player);
            }
        }
    }

    private void b(NpcInstance npcInstance, Player player) {
        player.sendPacket((IStaticPacket)new NpcSay(npcInstance, this.a, this.a, new String[0]));
    }
}
