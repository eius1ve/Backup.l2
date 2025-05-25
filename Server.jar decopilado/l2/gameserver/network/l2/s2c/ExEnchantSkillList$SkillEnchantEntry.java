/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.data.StringHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.ExEnchantSkillInfo;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.SkillEnchant;
import org.apache.commons.lang3.StringUtils;

class ExEnchantSkillList.SkillEnchantEntry {
    private final SkillEnchant b;

    public ExEnchantSkillList.SkillEnchantEntry(SkillEnchant skillEnchant) {
        this.b = skillEnchant;
    }

    public String toHtml(Player player) {
        Skill skill = SkillTable.getInstance().getInfo(this.b.getSkillId(), this.b.getSkillLevel());
        if (skill == null) {
            return "";
        }
        String string = StringHolder.getInstance().getNotNull(player, "l2.gameserver.ExEnchantSkillList.SkillEntry");
        string = StringUtils.replace((String)string, (String)"%skill_icon%", (String)StringUtils.defaultString((String)skill.getIcon(), (String)""));
        string = StringUtils.replace((String)string, (String)"%skill_name%", (String)StringUtils.defaultString((String)skill.getName()));
        string = StringUtils.replace((String)string, (String)"%skill_enchant_route_name%", (String)StringUtils.defaultString((String)skill.getEnchantRouteName()));
        string = StringUtils.replace((String)string, (String)"%skill_enchant_level%", (String)String.valueOf(this.b.getEnchantLevel()));
        string = StringUtils.replace((String)string, (String)"%skill_enchant_bypass%", (String)(ExEnchantSkillInfo.EX_ENCHANT_SKILLINFO_BYPASS + " " + this.b.getSkillId() + " " + this.b.getSkillLevel() + " " + ExEnchantSkillList.this.vc));
        return string;
    }
}
