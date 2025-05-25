/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ProductHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ReceiveVipInfo;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.support.VipInfoTemplate;

public final class VipManager {
    private static VipManager a;

    private VipManager() {
        if (!Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            return;
        }
        a = this;
    }

    public static VipManager getInstance() {
        if (a == null) {
            new VipManager();
        }
        return a;
    }

    public void manageVipLevelSkill(Player player) {
        Skill skill;
        Skill skill2;
        int n;
        player.setVipLevel(this.getVipLevel(player) > 0 ? this.getVipLevel(player) : (byte)0);
        if (!this.checkVipLevelExpiration(player)) {
            player.sendPacket((IStaticPacket)new ReceiveVipInfo(player, this));
        }
        if (player.getVipLevel() > 1 && (n = ProductHolder.getInstance().getSkillId((byte)(player.getVipLevel() - 1))) > 0 && (skill2 = SkillTable.getInstance().getInfo(n, 1)) != null) {
            player.removeSkill(skill2);
            player.sendSkillList();
        }
        n = ProductHolder.getInstance().getSkillId(player.getVipLevel());
        int n2 = ProductHolder.getInstance().getSkillLevel(player.getVipLevel());
        if (n > 0 && (skill = SkillTable.getInstance().getInfo(n, n2)) != null) {
            player.addSkill(skill);
            player.sendSkillList();
        }
    }

    public byte getVipLevel(Player player) {
        return this.a(player).getVipLevel();
    }

    public byte getVipLevel(long l) {
        byte by = this.a(l).getVipLevel();
        if (by > Config.PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL) {
            by = (byte)Config.PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL;
        }
        return by;
    }

    private VipInfoTemplate a(Player player) {
        return this.a(player.getVipPoints());
    }

    private VipInfoTemplate a(long l) {
        for (byte by = 0; by < ProductHolder.getInstance().getVipLevels().size(); by = (byte)(by + 1)) {
            if (l >= ProductHolder.getInstance().getVipLevels().get(by).getPointsRequired()) continue;
            byte by2 = (byte)(by - 1);
            if (by2 > Config.PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL) {
                by2 = (byte)Config.PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL;
            }
            return ProductHolder.getInstance().getVipLevels().get(by2);
        }
        return ProductHolder.getInstance().getVipLevels().get((byte)Config.PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL);
    }

    public long getPointsDepreciatedOnLevel(byte by) {
        VipInfoTemplate vipInfoTemplate = ProductHolder.getInstance().getVipLevels().get(by);
        if (vipInfoTemplate == null) {
            return 0L;
        }
        return vipInfoTemplate.getPointsDepreciated();
    }

    public long getPointsToLevel(byte by) {
        VipInfoTemplate vipInfoTemplate = ProductHolder.getInstance().getVipLevels().get(by);
        if (vipInfoTemplate == null) {
            return 0L;
        }
        return vipInfoTemplate.getPointsRequired();
    }

    public boolean checkVipLevelExpiration(Player player) {
        Instant instant = Instant.now();
        if (instant.isAfter(Instant.ofEpochMilli(player.getVipTierExpiration()))) {
            player.updateVipPoints(-this.getPointsDepreciatedOnLevel(player.getVipLevel()));
            player.setVipTierExpiration(Instant.now().plus(30L, ChronoUnit.DAYS).toEpochMilli());
            return true;
        }
        return false;
    }
}
