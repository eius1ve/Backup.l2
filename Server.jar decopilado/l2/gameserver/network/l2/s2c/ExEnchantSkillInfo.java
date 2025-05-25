/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.network.l2.s2c;

import java.util.Map;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.EnchantSkillHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.SkillEnchant;
import org.apache.commons.lang3.StringUtils;

public class ExEnchantSkillInfo
extends NpcHtmlMessage {
    public static String EX_ENCHANT_SKILLINFO_BYPASS = "ExEnchantSkillInfo";
    private SkillEnchant a;
    private int chance = 0;
    private int va = 0;

    public ExEnchantSkillInfo(Player player, NpcInstance npcInstance) {
        super(player, npcInstance);
    }

    public static L2GameServerPacket packetFor(Player player, NpcInstance npcInstance, String ... stringArray) {
        ExEnchantSkillInfo exEnchantSkillInfo = new ExEnchantSkillInfo(player, npcInstance);
        if (player.getClassId().getLevel() < 4 || player.getLevel() < 76 || stringArray.length < 2) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        try {
            n = Integer.parseInt(stringArray[0]);
            n2 = Integer.parseInt(stringArray[1]);
            if (stringArray.length > 2) {
                n3 = Integer.parseInt(stringArray[2]);
            }
        }
        catch (Exception exception) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        Skill skill = player.getKnownSkill(n);
        if (skill == null) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        int n4 = skill.getLevel();
        Map<Integer, SkillEnchant> map = EnchantSkillHolder.getInstance().getLevelsOf(n);
        if (map == null || map.isEmpty()) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        SkillEnchant skillEnchant = map.get(n4);
        SkillEnchant skillEnchant2 = map.get(n2);
        if (skillEnchant2 == null) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        if (skillEnchant != null ? skillEnchant.getRouteId() != skillEnchant2.getRouteId() || skillEnchant2.getEnchantLevel() != skillEnchant.getEnchantLevel() + 1 : skillEnchant2.getEnchantLevel() != 1) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        int[] nArray = skillEnchant2.getChances();
        int n5 = Experience.LEVEL.length - nArray.length - 1;
        if (player.getLevel() < n5) {
            return new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ANY_FURTHER_SKILLS_TO_LEARN__COME_BACK_WHEN_YOU_HAVE_REACHED_LEVEL_S1).addNumber(n5);
        }
        int n6 = Math.max(0, Math.min(player.getLevel() - n5, nArray.length - 1));
        exEnchantSkillInfo.a = skillEnchant2;
        exEnchantSkillInfo.chance = (int)Math.min(100L, Math.round((double)nArray[n6] * player.getEnchantSkillBonusMul()));
        exEnchantSkillInfo.va = n3;
        return exEnchantSkillInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void processHtml(GameClient gameClient) {
        try {
            Player player = gameClient.getActiveChar();
            if (player == null) {
                return;
            }
            this.setFile("trainer/ExEnchantSkillInfo.htm");
            if (this.a == null) {
                return;
            }
            Skill skill = SkillTable.getInstance().getInfo(this.a.getSkillId(), this.a.getSkillLevel());
            this.replace("%skill_id%", String.valueOf(this.a.getSkillId()));
            this.replace("%skill_level%", String.valueOf(this.a.getSkillLevel()));
            this.replace("%skill_icon%", StringUtils.defaultString((String)skill.getIcon(), (String)""));
            this.replace("%skill_name%", StringUtils.defaultString((String)skill.getName()));
            this.replace("%skill_enchant_route_name%", StringUtils.defaultString((String)skill.getEnchantRouteName()));
            this.replace("%skill_enchant_level%", String.valueOf(this.a.getEnchantLevel()));
            if (this.a.getItemId() != 0 && this.a.getItemCount() > 0L) {
                String string = StringHolder.getInstance().getNotNull(player, "l2.gameserver.ExEnchantSkillInfo.RequiredItem");
                string = StringUtils.replace((String)string, (String)"%item_id%", (String)String.valueOf(this.a.getItemId()));
                string = StringUtils.replace((String)string, (String)"%item_count%", (String)String.valueOf(this.a.getItemCount()));
                this.replace("%required_item%", string);
            } else {
                this.replace("%required_item%", "&nbsp;");
            }
            this.replace("%backPageNum%", String.valueOf(this.va));
            this.replace("%required_exp%", String.valueOf(this.a.getExp()));
            this.replace("%required_sp%", String.valueOf(this.a.getSp()));
            this.replace("%current_exp%", String.valueOf(player.getExp()));
            this.replace("%current_sp%", String.valueOf(player.getSp()));
            this.replace("%chance%", String.valueOf(this.chance));
        }
        finally {
            super.processHtml(gameClient);
        }
    }
}
