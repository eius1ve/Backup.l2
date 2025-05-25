/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Creature;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.templates.npc.NpcTemplate;

public class ConditionTargetRace
extends Condition {
    private final int Ea;

    public ConditionTargetRace(String string) {
        if (string.equalsIgnoreCase("Undead")) {
            this.Ea = 1;
        } else if (string.equalsIgnoreCase("MagicCreatures")) {
            this.Ea = 2;
        } else if (string.equalsIgnoreCase("Beasts")) {
            this.Ea = 3;
        } else if (string.equalsIgnoreCase("Animals")) {
            this.Ea = 4;
        } else if (string.equalsIgnoreCase("Plants")) {
            this.Ea = 5;
        } else if (string.equalsIgnoreCase("Humanoids")) {
            this.Ea = 6;
        } else if (string.equalsIgnoreCase("Spirits")) {
            this.Ea = 7;
        } else if (string.equalsIgnoreCase("Angels")) {
            this.Ea = 8;
        } else if (string.equalsIgnoreCase("Demons")) {
            this.Ea = 9;
        } else if (string.equalsIgnoreCase("Dragons")) {
            this.Ea = 10;
        } else if (string.equalsIgnoreCase("Giants")) {
            this.Ea = 11;
        } else if (string.equalsIgnoreCase("Bugs")) {
            this.Ea = 12;
        } else if (string.equalsIgnoreCase("Fairies")) {
            this.Ea = 13;
        } else if (string.equalsIgnoreCase("Humans")) {
            this.Ea = 14;
        } else if (string.equalsIgnoreCase("Elves")) {
            this.Ea = 15;
        } else if (string.equalsIgnoreCase("DarkElves")) {
            this.Ea = 16;
        } else if (string.equalsIgnoreCase("Orcs")) {
            this.Ea = 17;
        } else if (string.equalsIgnoreCase("Dwarves")) {
            this.Ea = 18;
        } else if (string.equalsIgnoreCase("Others")) {
            this.Ea = 19;
        } else if (string.equalsIgnoreCase("NonLivingBeings")) {
            this.Ea = 20;
        } else if (string.equalsIgnoreCase("SiegeWeapons")) {
            this.Ea = 21;
        } else if (string.equalsIgnoreCase("DefendingArmy")) {
            this.Ea = 22;
        } else if (string.equalsIgnoreCase("Mercenaries")) {
            this.Ea = 23;
        } else if (string.equalsIgnoreCase("UnknownCreature")) {
            this.Ea = 24;
        } else {
            throw new IllegalArgumentException("ConditionTargetRace: Invalid race name: " + string);
        }
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature creature = env.target;
        return creature != null && creature.getTemplate() != null && (creature.isSummon() || creature.isNpc()) && this.Ea == ((NpcTemplate)creature.getTemplate()).getRace();
    }
}
