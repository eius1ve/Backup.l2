/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.instances.NpcInstance;

public class Mystic
extends DefaultAI {
    public Mystic(NpcInstance npcInstance) {
        super(npcInstance);
    }

    @Override
    protected boolean thinkActive() {
        return super.thinkActive() || this.defaultThinkBuff(2);
    }

    @Override
    protected boolean createNewTask() {
        return this.defaultFightTask();
    }

    @Override
    public int getRatePHYS() {
        return this._damSkills.length == 0 ? 25 : 0;
    }

    @Override
    public int getRateDOT() {
        return 10;
    }

    @Override
    public int getRateDEBUFF() {
        return 5;
    }

    @Override
    public int getRateDAM() {
        return 30;
    }

    @Override
    public int getRateSTUN() {
        return 3;
    }

    @Override
    public int getRateBUFF() {
        return 3;
    }

    @Override
    public int getRateHEAL() {
        return 5;
    }
}
