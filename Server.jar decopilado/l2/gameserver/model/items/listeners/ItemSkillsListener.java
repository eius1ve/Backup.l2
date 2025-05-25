/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.stats.Formulas;
import l2.gameserver.templates.item.EtcItemTemplate;
import l2.gameserver.templates.item.ItemTemplate;

public final class ItemSkillsListener
implements OnEquipListener {
    private static final ItemSkillsListener a = new ItemSkillsListener();
    private static final int oG = 0;
    private static final int oH = 1;
    private static final int oI = 2;

    public static ItemSkillsListener getInstance() {
        return a;
    }

    @Override
    public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
        Player player = (Player)playable;
        ItemTemplate itemTemplate = itemInstance.getTemplate();
        HashSet<Skill> hashSet = new HashSet<Skill>();
        List<Skill> list = Arrays.asList(Objects.requireNonNullElse(itemTemplate.getAttachedSkills(), Skill.EMPTY_ARRAY));
        int n2 = 0;
        player.removeTriggers(itemTemplate);
        for (Skill itemInstanceArray : list) {
            Skill skill = player.getKnownSkill(itemInstanceArray.getId());
            if (skill == null || skill.getLevel() >= 100) continue;
            player.removeSkill(skill, false);
            hashSet.add(skill);
            n2 = 1;
        }
        if (itemInstance.getEnchantLevel() >= 4 && itemTemplate.getEnchant4Skill().isPresent()) {
            player.removeSkill(itemTemplate.getEnchant4Skill().get(), false);
            n2 = 1;
        }
        ArrayList arrayList = new ArrayList();
        if (n == -1 && (itemInstance.getItemType() == EtcItemTemplate.EtcItemType.RUNE || itemInstance.getItemType() == EtcItemTemplate.EtcItemType.RUNE_QUEST)) {
            for (ItemInstance itemInstance2 : player.getInventory().getItems()) {
                if (itemInstance2 == null || itemInstance2 == itemInstance || itemInstance2.getTemplate().getAttachedSkills() == null || itemInstance2.getItemType() != EtcItemTemplate.EtcItemType.RUNE && itemInstance2.getItemType() != EtcItemTemplate.EtcItemType.RUNE_QUEST) continue;
                arrayList.add(itemInstance2);
            }
        } else {
            for (ItemInstance itemInstance3 : player.getInventory().getPaperdollItems()) {
                if (itemInstance3 == null || itemInstance3 == itemInstance || itemInstance3.getTemplate().getAttachedSkills() == null) continue;
                arrayList.add(itemInstance3);
            }
        }
        if (!arrayList.isEmpty()) {
            n2 |= this.a(player, hashSet, arrayList);
        }
        this.m(player, n2);
    }

    @Override
    public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
        Player player = (Player)playable;
        ItemTemplate itemTemplate = itemInstance.getTemplate();
        switch (itemTemplate.getType2()) {
            case 0: {
                if (player.getWeaponsExpertisePenalty() <= 0) break;
                return;
            }
            case 2: {
                if (player.getArmorsExpertisePenalty() <= 0 || itemInstance.getCrystalType().ordinal() <= player.getExpertiseGrade().ordinal()) break;
                return;
            }
        }
        List<Skill> list = Arrays.asList(Objects.requireNonNullElse(itemTemplate.getAttachedSkills(), Skill.EMPTY_ARRAY));
        player.addTriggers(itemTemplate);
        int n2 = 0;
        for (Skill skill : list) {
            int n3 = this.a(player, itemInstance, skill);
            if (n3 == 0) continue;
            n2 = n3;
        }
        if (itemInstance.getEnchantLevel() >= 4 && itemTemplate.getEnchant4Skill().isPresent()) {
            player.addSkill(itemTemplate.getEnchant4Skill().get(), false);
            n2 |= 1;
        }
        this.m(player, n2);
    }

    private void m(Player player, int n) {
        if (n != 0) {
            player.sendSkillList();
            player.updateStats();
            if ((n & 2) != 0) {
                player.sendPacket((IStaticPacket)new SkillCoolTime(player));
            }
        }
    }

    private int a(Player player, ItemInstance itemInstance, Skill skill) {
        Skill skill2 = player.getKnownSkill(skill.getId());
        if (skill2 == null || skill2.getLevel() < skill.getLevel()) {
            player.addSkill(skill, false);
            if (skill.isActive()) {
                long l = itemInstance.getTemplate().getEquipReuseDelay();
                if (l < 0L) {
                    l = Math.min(Formulas.calcSkillReuseDelay(player, skill), 30000L);
                }
                if (l > 0L && !player.isSkillDisabled(skill)) {
                    player.disableSkill(skill, l);
                }
                return 3;
            }
            return 1;
        }
        return 0;
    }

    private int a(Player player, Collection<Skill> collection, Collection<ItemInstance> collection2) {
        int n = 0;
        for (ItemInstance itemInstance : collection2) {
            if (itemInstance.getTemplate().getAttachedSkills() == null) continue;
            for (Skill skill : collection) {
                for (Skill skill2 : itemInstance.getTemplate().getAttachedSkills()) {
                    int n2;
                    if (skill2.getId() != skill.getId() || 0 == (n2 = this.a(player, itemInstance, skill2))) continue;
                    n |= n2;
                }
            }
        }
        return n;
    }
}
