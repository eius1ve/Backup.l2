/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.model;

import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;
import l2.gameserver.templates.item.ItemTemplate;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Recipe {
    private final int ji;
    private final ItemTemplate a;
    private final ERecipeType a;
    private final int jj;
    private final int jk;
    private final int jl;
    private final List<Pair<ItemTemplate, Long>> bc;
    private final List<Pair<ItemTemplate, Pair<Long, Double>>> bd;
    private final List<Pair<ItemTemplate, Long>> be;

    public Recipe(int n, ItemTemplate itemTemplate, ERecipeType eRecipeType, int n2, int n3, int n4, List<Pair<ItemTemplate, Long>> list, List<Pair<ItemTemplate, Pair<Long, Double>>> list2, List<Pair<ItemTemplate, Long>> list3) {
        this.ji = n;
        this.a = itemTemplate;
        this.a = eRecipeType;
        this.jj = n2;
        this.jk = n3;
        this.jl = n4;
        this.bc = list;
        this.bd = list2;
        this.be = list3;
    }

    public int getId() {
        return this.ji;
    }

    public ItemTemplate getItem() {
        return this.a;
    }

    public ERecipeType getType() {
        return this.a;
    }

    public int getRequiredSkillLvl() {
        return this.jj;
    }

    public int getMpConsume() {
        return this.jk;
    }

    public int getSuccessRate() {
        return this.jl;
    }

    public List<Pair<ItemTemplate, Long>> getMaterials() {
        return this.bc;
    }

    public List<Pair<ItemTemplate, Pair<Long, Double>>> getProducts() {
        return this.bd;
    }

    public List<Pair<ItemTemplate, Long>> getNpcFees() {
        return this.be;
    }

    public int hashCode() {
        return this.ji;
    }

    public String toString() {
        return "Recipe{_id=" + this.ji + ", _item=" + this.a + ", _type=" + this.a + ", _requiredSkillLvl=" + this.jj + ", _mpConsume=" + this.jk + ", _successRate=" + this.jl + ", _materials=" + this.bc + ", _products=" + this.bd + ", _npcFees=" + this.be + "}";
    }

    public static final class ERecipeType
    extends Enum<ERecipeType> {
        public static final /* enum */ ERecipeType ERT_DWARF = new ERecipeType();
        public static final /* enum */ ERecipeType ERT_COMMON = new ERecipeType();
        private static final /* synthetic */ ERecipeType[] a;

        public static ERecipeType[] values() {
            return (ERecipeType[])a.clone();
        }

        public static ERecipeType valueOf(String string) {
            return Enum.valueOf(ERecipeType.class, string);
        }

        public boolean isApplicableBy(Player player) {
            return this != ERT_DWARF || player.getRace() == Race.dwarf;
        }

        private static /* synthetic */ ERecipeType[] a() {
            return new ERecipeType[]{ERT_DWARF, ERT_COMMON};
        }

        static {
            a = ERecipeType.a();
        }
    }
}
