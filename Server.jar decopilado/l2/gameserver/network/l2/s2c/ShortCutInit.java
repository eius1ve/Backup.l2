/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.network.l2.s2c.ShortCutPacket;

public class ShortCutInit
extends ShortCutPacket {
    private List<ShortCutPacket.ShortcutInfo> da = Collections.emptyList();

    public ShortCutInit(Player player) {
        Collection<ShortCut> collection = player.getAllShortCuts();
        this.da = new ArrayList<ShortCutPacket.ShortcutInfo>(collection.size());
        for (ShortCut shortCut : collection) {
            this.da.add(ShortCutInit.convert(player, shortCut));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(69);
        this.writeD(this.da.size());
        for (ShortCutPacket.ShortcutInfo shortcutInfo : this.da) {
            shortcutInfo.write(this);
        }
    }
}
