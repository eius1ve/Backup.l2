/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;

public static class TamedBeastInstance.Buff
extends RunnableImpl {
    private NpcInstance w;
    private Player f;
    private Skill _skill;

    public TamedBeastInstance.Buff(NpcInstance npcInstance, Player player, Skill skill) {
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
