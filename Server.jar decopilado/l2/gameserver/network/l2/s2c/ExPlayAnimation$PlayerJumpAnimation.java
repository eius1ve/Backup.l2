/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;

public static final class ExPlayAnimation.PlayerJumpAnimation
extends Enum<ExPlayAnimation.PlayerJumpAnimation> {
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation HUMAN_MALE_FIGHTER = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MFighter", 0, new int[]{ClassId.fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation HUMAN_FEMALE_FIGHTER = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FFighter", 1, new int[]{ClassId.fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation HUMAN_MALE_MAGIC = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MMagic", 0, new int[]{ClassId.mage.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation HUMAN_FEMALE_MAGIC = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FMagic", 1, new int[]{ClassId.mage.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation ELF_MALE = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MElf", 0, new int[]{ClassId.elven_mage.getId(), ClassId.elven_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation ELF_FEMALE = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FElf", 1, new int[]{ClassId.elven_mage.getId(), ClassId.elven_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation DARK_ELF_MALE = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MDarkElf", 0, new int[]{ClassId.dark_mage.getId(), ClassId.dark_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation DARK_ELF_FEMALE = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FDarkElf", 1, new int[]{ClassId.dark_mage.getId(), ClassId.dark_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation ORC_MALE_FIGHTER = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MOrc", 0, new int[]{ClassId.orc_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation ORC_FEMALE_FIGHTER = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FOrc", 1, new int[]{ClassId.orc_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation ORC_MALE_SHAMAN = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MShaman", 0, new int[]{ClassId.orc_mage.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation ORC_FEMALE_SHAMAN = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FShaman", 1, new int[]{ClassId.orc_mage.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation DWARF_MALE = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_MDwarf", 0, new int[]{ClassId.dwarven_fighter.getId()});
    public static final /* enum */ ExPlayAnimation.PlayerJumpAnimation DWARF_FEMALE = new ExPlayAnimation.PlayerJumpAnimation("JumpRun_Dual_FDwarf", 1, new int[]{ClassId.dwarven_fighter.getId()});
    private final String fa;
    private final int wt;
    private final int[] bb;
    public static final ExPlayAnimation.PlayerJumpAnimation[] VALUES;
    private static final /* synthetic */ ExPlayAnimation.PlayerJumpAnimation[] a;

    public static ExPlayAnimation.PlayerJumpAnimation[] values() {
        return (ExPlayAnimation.PlayerJumpAnimation[])a.clone();
    }

    public static ExPlayAnimation.PlayerJumpAnimation valueOf(String string) {
        return Enum.valueOf(ExPlayAnimation.PlayerJumpAnimation.class, string);
    }

    private ExPlayAnimation.PlayerJumpAnimation(String string2, int n2, int[] nArray) {
        this.fa = string2;
        this.wt = n2;
        this.bb = nArray;
    }

    public String getAnimationName() {
        return this.fa;
    }

    public boolean match(Player player) {
        if (player.getSex() != this.wt) {
            return false;
        }
        for (int n : this.bb) {
            if (player.getBaseClassId() != n) continue;
            return true;
        }
        return false;
    }

    public static ExPlayAnimation.PlayerJumpAnimation getAnimation(Player player) {
        for (ExPlayAnimation.PlayerJumpAnimation playerJumpAnimation : VALUES) {
            if (!playerJumpAnimation.match(player)) continue;
            return playerJumpAnimation;
        }
        return null;
    }

    private static /* synthetic */ ExPlayAnimation.PlayerJumpAnimation[] a() {
        return new ExPlayAnimation.PlayerJumpAnimation[]{HUMAN_MALE_FIGHTER, HUMAN_FEMALE_FIGHTER, HUMAN_MALE_MAGIC, HUMAN_FEMALE_MAGIC, ELF_MALE, ELF_FEMALE, DARK_ELF_MALE, DARK_ELF_FEMALE, ORC_MALE_FIGHTER, ORC_FEMALE_FIGHTER, ORC_MALE_SHAMAN, ORC_FEMALE_SHAMAN, DWARF_MALE, DWARF_FEMALE};
    }

    static {
        a = ExPlayAnimation.PlayerJumpAnimation.a();
        VALUES = ExPlayAnimation.PlayerJumpAnimation.values();
    }
}
