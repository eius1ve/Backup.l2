/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.templates.item.ItemTemplate;

public final class ArmorSet {
    private final int fV;
    private final Map<Integer, Set<ItemTemplate>> ax;
    private final Set<ItemTemplate> g;
    private final Set<Skill> h;
    private final Set<Skill> i;
    private final Map<Integer, Set<Skill>> ay;
    private final Map<Integer, Set<Skill>> az;
    public static final int[] ARMOR_SET_SLOTS_ALL = new int[]{6, 11, 1, 10, 12, 0};

    public ArmorSet(int n, Map<Integer, Set<ItemTemplate>> map, Set<Skill> set, Set<ItemTemplate> set2, Set<Skill> set3, Map<Integer, Set<Skill>> map2, Map<Integer, Set<Skill>> map3) {
        this.fV = n;
        this.g = set2;
        this.ax = map;
        this.h = set;
        this.ay = map2;
        this.i = set3;
        this.az = map3;
    }

    private Set<ItemTemplate> a(int n) {
        return this.ax.getOrDefault(n, Collections.emptySet());
    }

    public int getEquippedPartsCount(Player player) {
        int n = 0;
        PcInventory pcInventory = player.getInventory();
        for (int n2 : ARMOR_SET_SLOTS_ALL) {
            ItemTemplate itemTemplate;
            Set<ItemTemplate> set = this.a(n2);
            ItemInstance itemInstance = pcInventory.getPaperdollItem(n2);
            if (itemInstance == null || set == null || set.isEmpty() || !set.contains(itemTemplate = itemInstance.getTemplate())) continue;
            ++n;
        }
        return n;
    }

    public boolean containAll(Player player) {
        PcInventory pcInventory = player.getInventory();
        for (int n : ARMOR_SET_SLOTS_ALL) {
            if (this.a(n, pcInventory.getPaperdollItem(n))) continue;
            return false;
        }
        return true;
    }

    private boolean a(int n, ItemTemplate itemTemplate) {
        Set<ItemTemplate> set = this.a(n);
        if (set == null || set.isEmpty()) {
            return true;
        }
        if (itemTemplate == null) {
            return false;
        }
        return set.contains(itemTemplate);
    }

    private boolean a(int n, ItemInstance itemInstance) {
        Set<ItemTemplate> set = this.a(n);
        if (set == null || set.isEmpty()) {
            return true;
        }
        if (itemInstance == null) {
            return false;
        }
        return set.contains(itemInstance.getTemplate());
    }

    public int getSetById() {
        return this.fV;
    }

    public Collection<ItemTemplate> getChestItems() {
        return this.a(6);
    }

    public Set<Skill> getSkills() {
        return this.h;
    }

    public Set<Skill> getShieldSkills() {
        return this.i;
    }

    public Map<Integer, Set<Skill>> getEnchantSkills() {
        return this.ay;
    }

    public Set<Skill> getEnchantSkillsForLevel(Integer n) {
        if (n == null || n < 1) {
            return Collections.emptySet();
        }
        HashSet<Skill> hashSet = new HashSet<Skill>();
        for (int i = n.intValue(); i > 0; --i) {
            Set<Skill> set = this.getEnchantSkills().get(i);
            if (set == null || set.isEmpty()) continue;
            hashSet.addAll(set);
        }
        return hashSet;
    }

    public boolean containShield(Player player) {
        PcInventory pcInventory = player.getInventory();
        ItemInstance itemInstance = pcInventory.getPaperdollItem(7);
        return itemInstance != null && this.g.contains(itemInstance.getTemplate());
    }

    public boolean containShield(ItemTemplate itemTemplate) {
        if (this.g.isEmpty()) {
            return false;
        }
        return this.g.contains(itemTemplate);
    }

    public Integer getEnchantLevel(Player player) {
        return this.a(player, -1, null);
    }

    private Integer a(Player player, int n, ItemInstance itemInstance) {
        PcInventory pcInventory = player.getInventory();
        Integer n2 = null;
        for (int n3 : ARMOR_SET_SLOTS_ALL) {
            ItemInstance itemInstance2;
            Set<ItemTemplate> set = this.a(n3);
            if (set == null || set.isEmpty()) continue;
            ItemInstance itemInstance3 = itemInstance2 = n3 == n && itemInstance != null ? itemInstance : pcInventory.getPaperdollItem(n3);
            if (itemInstance2 == null) {
                return 0;
            }
            ItemTemplate itemTemplate = itemInstance2.getTemplate();
            if (!set.contains(itemTemplate)) {
                return 0;
            }
            if (n2 != null && itemInstance2.getEnchantLevel() >= n2) continue;
            n2 = itemInstance2.getEnchantLevel();
        }
        return n2;
    }

    public Map<Integer, Set<Skill>> getSetSkillsByParts() {
        return this.az;
    }

    private Set<Skill> b(Player player) {
        HashSet<Skill> hashSet = new HashSet<Skill>();
        if (this.containAll(player)) {
            hashSet.addAll(this.getSkills());
            if (this.containShield(player)) {
                hashSet.addAll(this.getShieldSkills());
            }
            hashSet.addAll(this.getEnchantSkillsForLevel(player.getArmorSetEnchantLevel()));
        }
        if (!this.getSetSkillsByParts().isEmpty()) {
            int n = this.getEquippedPartsCount(player);
            for (Map.Entry<Integer, Set<Skill>> entry : this.getSetSkillsByParts().entrySet()) {
                int n2 = entry.getKey();
                if (n < n2) continue;
                hashSet.addAll((Collection<Skill>)entry.getValue());
            }
        }
        return hashSet;
    }

    public boolean onEquip(int n, ItemInstance itemInstance, Player player) {
        Set<Skill> set;
        boolean bl = false;
        if (this.a(n, itemInstance.getTemplate()) && !(set = this.b(player)).isEmpty()) {
            for (Skill skill : set) {
                if (player.getSkillLevel(skill.getId()) > skill.getLevel()) continue;
                player.addSkill(skill, false);
                bl = true;
            }
        }
        return bl;
    }

    public boolean onUnequip(int n, ItemInstance itemInstance, Player player) {
        HashSet<Skill> hashSet = new HashSet<Skill>();
        boolean bl = false;
        if (!this.containAll(player)) {
            for (Skill object : this.getSkills()) {
                if (player.getKnownSkill(object.getId()) != object) continue;
                hashSet.add(object);
            }
            bl = true;
            for (Skill skill : this.getEnchantSkillsForLevel(this.a(player, n, itemInstance))) {
                if (player.getKnownSkill(skill.getId()) != skill) continue;
                hashSet.add(skill);
            }
        } else if (this.containShield(itemInstance.getTemplate())) {
            bl = true;
        }
        if (bl) {
            for (Skill skill : this.getShieldSkills()) {
                if (player.getKnownSkill(skill.getId()) != skill) continue;
                hashSet.add(skill);
            }
        }
        if (!this.getSetSkillsByParts().isEmpty() && this.a(n, itemInstance.getTemplate())) {
            int n2 = this.getEquippedPartsCount(player);
            for (Map.Entry<Integer, Set<Skill>> entry : this.getSetSkillsByParts().entrySet()) {
                int n3 = entry.getKey();
                if (n2 >= n3 && n != 6) continue;
                for (Skill skill : entry.getValue()) {
                    if (player.getKnownSkill(skill.getId()) != skill) continue;
                    hashSet.add(skill);
                }
            }
        }
        boolean bl2 = false;
        if (!hashSet.isEmpty()) {
            for (Skill skill : hashSet) {
                if (player.getSkillLevel(skill.getId()) > skill.getLevel()) continue;
                player.removeSkill(skill, false);
                bl2 = true;
            }
        }
        return bl2;
    }
}
