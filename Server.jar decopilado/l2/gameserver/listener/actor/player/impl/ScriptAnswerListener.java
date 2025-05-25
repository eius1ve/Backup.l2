/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.player.impl;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Scripts;

public class ScriptAnswerListener
implements OnAnswerListener {
    private HardReference<Player> a;
    private String cJ;
    private Object[] b;
    private long aR;

    public ScriptAnswerListener(Player player, String string, Object[] objectArray, long l) {
        this.cJ = string;
        this.b = objectArray;
        this.a = player.getRef();
        this.aR = System.currentTimeMillis() + l;
    }

    @Override
    public void sayYes() {
        Player player = this.a.get();
        if (player == null || System.currentTimeMillis() > this.aR) {
            return;
        }
        Scripts.getInstance().callScripts(player, this.cJ.split(":")[0], this.cJ.split(":")[1], this.b);
    }

    @Override
    public void sayNo() {
    }
}
