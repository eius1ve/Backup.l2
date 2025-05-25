/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.SpecialMonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.SpecialMonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class MeleonInstance
extends SpecialMonsterInstance {
    public static final int Young_Watermelon = 13271;
    public static final int Rain_Watermelon = 13273;
    public static final int Defective_Watermelon = 13272;
    public static final int Young_Honey_Watermelon = 13275;
    public static final int Rain_Honey_Watermelon = 13277;
    public static final int Defective_Honey_Watermelon = 13276;
    public static final int Large_Rain_Watermelon = 13274;
    public static final int Large_Rain_Honey_Watermelon = 13278;
    private HardReference<Player> ac;

    public MeleonInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void setSpawner(Player player) {
        this.ac = player.getRef();
    }

    public Player getSpawner() {
        return (Player)this.ac.get();
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (creature.getActiveWeaponInstance() == null) {
            return;
        }
        int n = creature.getActiveWeaponInstance().getItemId();
        if (this.getNpcId() == 13276 || this.getNpcId() == 13277 || this.getNpcId() == 13278) {
            if (n != 4202 && n != 5133 && n != 5817 && n != 7058 && n != 8350) {
                return;
            }
            d = 1.0;
        } else if (this.getNpcId() == 13273 || this.getNpcId() == 13272 || this.getNpcId() == 13274) {
            d = 5.0;
        } else {
            return;
        }
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    public long getRegenTick() {
        return 0L;
    }

    public boolean canChampion() {
        return false;
    }
}
