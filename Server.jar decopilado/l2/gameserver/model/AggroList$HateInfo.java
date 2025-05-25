/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;

public class AggroList.HateInfo
extends AggroList.DamageHate {
    public final Creature attacker;

    AggroList.HateInfo(Creature creature, AggroList.AggroInfo aggroInfo) {
        super(AggroList.this);
        this.attacker = creature;
        this.hate = aggroInfo.hate;
        this.damage = aggroInfo.damage;
    }
}
