/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.EnchantSkillHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.ExEnchantSkillInfo;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.SkillEnchant;
import org.apache.commons.lang3.StringUtils;

public class ExEnchantSkillList
extends NpcHtmlMessage {
    public static String EX_ENCHANT_SKILLLIST_BYPASS = "ExEnchantSkillList";
    private static final int vb = 9;
    private final List<SkillEnchantEntry> cf = new ArrayList<SkillEnchantEntry>();
    private int vc;

    public ExEnchantSkillList(Player player, NpcInstance npcInstance, int n) {
        super(player, npcInstance);
        this.vc = n;
    }

    public static ExEnchantSkillList packetFor(Player player, NpcInstance npcInstance) {
        return ExEnchantSkillList.packetFor(player, npcInstance, 0);
    }

    public static ExEnchantSkillList packetFor(Player player, NpcInstance npcInstance, int n) {
        Collection<Skill> collection = player.getAllSkills();
        ExEnchantSkillList exEnchantSkillList = new ExEnchantSkillList(player, npcInstance, n);
        for (Skill skill : collection) {
            int n2;
            Object object;
            Object object2;
            Map<Integer, Map<Integer, SkillEnchant>> map;
            int n3;
            int n4 = skill.getId();
            int n5 = skill.getLevel();
            if (n5 < (n3 = skill.getBaseLevel()) || (map = EnchantSkillHolder.getInstance().getRoutesOf(n4)) == null || map.isEmpty()) continue;
            SkillEnchant skillEnchant = EnchantSkillHolder.getInstance().getSkillEnchant(n4, n5);
            if (n5 == n3) {
                object2 = map.values().iterator();
                while (object2.hasNext()) {
                    Map<Integer, SkillEnchant> map2 = object2.next();
                    object = map2.values().iterator();
                    while (object.hasNext()) {
                        SkillEnchant skillEnchant2 = object.next();
                        if (skillEnchant2.getEnchantLevel() != 1) continue;
                        exEnchantSkillList.addSkill(skillEnchant2);
                    }
                }
                continue;
            }
            if (skillEnchant == null || (object = (SkillEnchant)(object2 = map.get(skillEnchant.getRouteId())).get(n2 = n5 + 1)) == null) continue;
            exEnchantSkillList.addSkill((SkillEnchant)object);
        }
        return exEnchantSkillList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processHtml(GameClient gameClient) {
        try {
            int n;
            Player player = gameClient.getActiveChar();
            if (player == null) {
                return;
            }
            this.setFile("trainer/ExEnchantSkillList.htm");
            StringBuilder stringBuilder = new StringBuilder();
            int n2 = this.cf.size() / 9 + Math.min(1, this.cf.size() % 9);
            this.vc = Math.min(this.vc, n2);
            if (!this.cf.isEmpty()) {
                int n3 = this.vc * 9;
                n = Math.max(0, Math.min((this.vc + 1) * 9 - 1, this.cf.size() - 1));
                for (int i = n3; i <= n; ++i) {
                    SkillEnchantEntry skillEnchantEntry = this.cf.get(i);
                    stringBuilder.append(skillEnchantEntry.toHtml(player));
                }
            }
            this.replace("%skill_enchant_list%", stringBuilder.toString());
            if (n2 > 1) {
                stringBuilder.setLength(0);
                String string = StringHolder.getInstance().getNotNull(player, "l2.gameserver.ExEnchantSkillList.paging");
                for (n = 0; n < n2; ++n) {
                    stringBuilder.append("<td>");
                    if (n == this.vc) {
                        stringBuilder.append("&nbsp;").append(n + 1).append("&nbsp;");
                    } else {
                        stringBuilder.append(" <a action=\"bypass -h ").append(EX_ENCHANT_SKILLLIST_BYPASS).append(" ").append(n).append("\">&nbsp;").append(n + 1).append("&nbsp;</a>");
                    }
                    stringBuilder.append("</td>");
                }
                this.replace("%paging%", string.replace("%pages%", stringBuilder.toString()));
                stringBuilder.setLength(0);
            } else {
                this.replace("%paging%", "");
            }
        }
        finally {
            super.processHtml(gameClient);
        }
    }

    public void addSkill(SkillEnchant skillEnchant) {
        this.cf.add(new SkillEnchantEntry(skillEnchant));
    }

    class SkillEnchantEntry {
        private final SkillEnchant b;

        public SkillEnchantEntry(SkillEnchant skillEnchant) {
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
}
