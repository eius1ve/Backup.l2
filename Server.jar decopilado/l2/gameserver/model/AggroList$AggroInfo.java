/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;

public class AggroList.AggroInfo
extends AggroList.DamageHate {
    public final int attackerId;

    AggroList.AggroInfo(Creature creature) {
        super(AggroList.this);
        this.attackerId = creature.getObjectId();
    }
}
