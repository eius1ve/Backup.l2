/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPlayAnimation
extends L2GameServerPacket {
    private final int wr;
    private final int ws;
    private final boolean eH;
    private final String eZ;

    public ExPlayAnimation(Player player, int n, boolean bl, String string) {
        this.wr = player.getObjectId();
        this.ws = n;
        this.eZ = string;
        this.eH = bl;
    }

    public ExPlayAnimation(Player player, PlayerJumpAnimation playerJumpAnimation) {
        this(player, 1, true, playerJumpAnimation.getAnimationName());
    }

    @Override
    protected void writeImpl() {
        this.writeEx(91);
        this.writeD(this.wr);
        this.writeC(this.eH ? 1 : 0);
        this.writeD(this.ws);
        this.writeS(this.eZ);
    }

    public static ExPlayAnimation jump(Player player) {
        return new ExPlayAnimation(player, PlayerJumpAnimation.getAnimation(player));
    }

    public static final class PlayerJumpAnimation
    extends Enum<PlayerJumpAnimation> {
        public static final /* enum */ PlayerJumpAnimation HUMAN_MALE_FIGHTER = new PlayerJumpAnimation("JumpRun_Dual_MFighter", 0, new int[]{ClassId.fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation HUMAN_FEMALE_FIGHTER = new PlayerJumpAnimation("JumpRun_Dual_FFighter", 1, new int[]{ClassId.fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation HUMAN_MALE_MAGIC = new PlayerJumpAnimation("JumpRun_Dual_MMagic", 0, new int[]{ClassId.mage.getId()});
        public static final /* enum */ PlayerJumpAnimation HUMAN_FEMALE_MAGIC = new PlayerJumpAnimation("JumpRun_Dual_FMagic", 1, new int[]{ClassId.mage.getId()});
        public static final /* enum */ PlayerJumpAnimation ELF_MALE = new PlayerJumpAnimation("JumpRun_Dual_MElf", 0, new int[]{ClassId.elven_mage.getId(), ClassId.elven_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation ELF_FEMALE = new PlayerJumpAnimation("JumpRun_Dual_FElf", 1, new int[]{ClassId.elven_mage.getId(), ClassId.elven_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation DARK_ELF_MALE = new PlayerJumpAnimation("JumpRun_Dual_MDarkElf", 0, new int[]{ClassId.dark_mage.getId(), ClassId.dark_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation DARK_ELF_FEMALE = new PlayerJumpAnimation("JumpRun_Dual_FDarkElf", 1, new int[]{ClassId.dark_mage.getId(), ClassId.dark_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation ORC_MALE_FIGHTER = new PlayerJumpAnimation("JumpRun_Dual_MOrc", 0, new int[]{ClassId.orc_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation ORC_FEMALE_FIGHTER = new PlayerJumpAnimation("JumpRun_Dual_FOrc", 1, new int[]{ClassId.orc_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation ORC_MALE_SHAMAN = new PlayerJumpAnimation("JumpRun_Dual_MShaman", 0, new int[]{ClassId.orc_mage.getId()});
        public static final /* enum */ PlayerJumpAnimation ORC_FEMALE_SHAMAN = new PlayerJumpAnimation("JumpRun_Dual_FShaman", 1, new int[]{ClassId.orc_mage.getId()});
        public static final /* enum */ PlayerJumpAnimation DWARF_MALE = new PlayerJumpAnimation("JumpRun_Dual_MDwarf", 0, new int[]{ClassId.dwarven_fighter.getId()});
        public static final /* enum */ PlayerJumpAnimation DWARF_FEMALE = new PlayerJumpAnimation("JumpRun_Dual_FDwarf", 1, new int[]{ClassId.dwarven_fighter.getId()});
        private final String fa;
        private final int wt;
        private final int[] bb;
        public static final PlayerJumpAnimation[] VALUES;
        private static final /* synthetic */ PlayerJumpAnimation[] a;

        public static PlayerJumpAnimation[] values() {
            return (PlayerJumpAnimation[])a.clone();
        }

        public static PlayerJumpAnimation valueOf(String string) {
            return Enum.valueOf(PlayerJumpAnimation.class, string);
        }

        private PlayerJumpAnimation(String string2, int n2, int[] nArray) {
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

        public static PlayerJumpAnimation getAnimation(Player player) {
            for (PlayerJumpAnimation playerJumpAnimation : VALUES) {
                if (!playerJumpAnimation.match(player)) continue;
                return playerJumpAnimation;
            }
            return null;
        }

        private static /* synthetic */ PlayerJumpAnimation[] a() {
            return new PlayerJumpAnimation[]{HUMAN_MALE_FIGHTER, HUMAN_FEMALE_FIGHTER, HUMAN_MALE_MAGIC, HUMAN_FEMALE_MAGIC, ELF_MALE, ELF_FEMALE, DARK_ELF_MALE, DARK_ELF_FEMALE, ORC_MALE_FIGHTER, ORC_FEMALE_FIGHTER, ORC_MALE_SHAMAN, ORC_FEMALE_SHAMAN, DWARF_MALE, DWARF_FEMALE};
        }

        static {
            a = PlayerJumpAnimation.a();
            VALUES = PlayerJumpAnimation.values();
        }
    }
}
