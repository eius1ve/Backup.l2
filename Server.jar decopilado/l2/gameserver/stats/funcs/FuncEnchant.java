/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.funcs;

import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.item.ItemType;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.item.support.Grade;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FuncEnchant
extends Func {
    public FuncEnchant(Stats stats, int n, Object object, double d) {
        super(stats, n, object);
    }

    private int a(ItemInstance itemInstance) {
        if (itemInstance == null) {
            return 0;
        }
        int n = itemInstance.getOwnerId();
        Player player = GameObjectsStorage.getPlayer(n);
        int n2 = itemInstance.getEnchantLevel();
        Integer n3 = null;
        if (player != null) {
            if (player.isOlyParticipant()) {
                n3 = this.a(itemInstance);
            } else if (Config.PVP_EVENTS_RESTRICT_ENCHANT && player.getTeam() != TeamType.NONE) {
                n3 = this.b(itemInstance);
            } else if (!Config.ENCHANT_LIMIT_ZONE_NAMES.isEmpty()) {
                n3 = this.a(itemInstance, player);
            }
        }
        return n3 == null ? n2 : Math.min(n2, n3);
    }

    private Integer a(ItemInstance itemInstance) {
        if (itemInstance.isArmor()) {
            return Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_ARMOR;
        }
        if (itemInstance.isWeapon()) {
            return itemInstance.getTemplate().isMageItem() ? Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_MAGE : Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_PHYS;
        }
        if (itemInstance.isAccessory()) {
            return Config.OLY_LIMIT_ENCHANT_STAT_LEVEL_ACCESSORY;
        }
        return null;
    }

    private Integer b(ItemInstance itemInstance) {
        if (itemInstance.isArmor()) {
            return Config.PVP_EVENTS_RESTRICT_ENCHANT_ARMOR_LEVEL;
        }
        if (itemInstance.isWeapon()) {
            return itemInstance.getTemplate().isMageItem() ? Config.PVP_EVENTS_RESTRICT_ENCHANT_WEAPON_MAGE : Config.PVP_EVENTS_RESTRICT_ENCHANT_WEAPON_PHYS;
        }
        if (itemInstance.isAccessory()) {
            return Config.PVP_EVENTS_RESTRICT_ENCHANT_ACCESSORY;
        }
        return null;
    }

    private Integer a(ItemInstance itemInstance, Player player) {
        for (Zone zone : player.getZones()) {
            if (!Config.ENCHANT_LIMIT_ZONE_NAMES.contains(zone.getName())) continue;
            if (itemInstance.isArmor()) {
                return Config.ENCHANT_LIMIT_ZONE_ARMOR_LEVEL;
            }
            if (itemInstance.isWeapon()) {
                return itemInstance.getTemplate().isMageItem() ? Config.ENCHANT_LIMIT_ZONE_WEAPON_MAGE : Config.ENCHANT_LIMIT_ZONE_WEAPON_PHYS;
            }
            if (!itemInstance.isAccessory()) continue;
            return Config.ENCHANT_LIMIT_ZONE_ACCESSORY;
        }
        return null;
    }

    @Override
    public void calc(Env env) {
        ItemInstance itemInstance = (ItemInstance)this.owner;
        int n = this.a(itemInstance);
        int n2 = Math.max(0, n - 3);
        Grade grade = itemInstance.getTemplate().getCrystalType();
        switch (this.stat) {
            case SHIELD_DEFENCE: {
                env.value += (double)n + (double)n2 * grade.getShieldDefence();
                break;
            }
            case MAGIC_DEFENCE: {
                env.value += (double)n + (double)n2 * grade.getMagicDefence();
                break;
            }
            case POWER_DEFENCE: {
                env.value += (double)n + (double)n2 * grade.getPhysicDefence();
                break;
            }
            case MAGIC_ATTACK: {
                env.value += grade.getMagicAttack() * (double)(n + n2);
                break;
            }
            case POWER_ATTACK: {
                boolean bl;
                ItemType itemType = itemInstance.getItemType();
                boolean bl2 = itemType == WeaponTemplate.WeaponType.BOW;
                boolean bl3 = bl = (itemType == WeaponTemplate.WeaponType.DUALFIST || itemType == WeaponTemplate.WeaponType.DUAL || itemType == WeaponTemplate.WeaponType.BIGSWORD || itemType == WeaponTemplate.WeaponType.SWORD) && itemInstance.getTemplate().getBodyPart() == 16384L;
                if (bl2) {
                    env.value += grade.getPhysicAttackBow() * (double)(n + n2);
                    break;
                }
                if (bl) {
                    env.value += grade.getPowerAttackDoubleSword() * (double)(n + n2);
                    break;
                }
                env.value += grade.getPhysicAttack() * (double)(n + n2);
                break;
            }
            case POWER_ATTACK_SPEED: {
                env.value += grade.getPhysicAttackSpeed() * (double)(n + n2);
                break;
            }
            case MAGIC_ATTACK_SPEED: {
                env.value += grade.getMagicAttackSpeed() * (double)(n + n2);
                break;
            }
            case MAX_HP: {
                env.value += grade.getMaxHp() * (double)(n + n2);
                break;
            }
            case MAX_MP: {
                env.value += grade.getMaxMp() * (double)(n + n2);
                break;
            }
            case MAX_CP: {
                env.value += grade.getMaxCp() * (double)(n + n2);
            }
        }
    }
}
