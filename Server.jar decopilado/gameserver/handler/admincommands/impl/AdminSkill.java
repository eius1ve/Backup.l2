/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.instances.TrainerInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEnchantSkillList;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.stats.Calculator;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Log;

public class AdminSkill
implements IAdminCommandHandler {
    private static Skill[] d;
    private static final List<Skill> ap;

    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        switch (commands) {
            case admin_show_skills: {
                this.E(player);
                break;
            }
            case admin_show_effects: {
                this.F(player);
                break;
            }
            case admin_stop_effect: {
                this.a(player, stringArray);
                break;
            }
            case admin_remove_skills: {
                try {
                    int n = stringArray.length > 1 ? Integer.parseInt(stringArray[1]) : 1;
                    this.h(player, n);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Invalid page number. Showing page 1.");
                    this.h(player, 1);
                }
                break;
            }
            case admin_delete_skills: {
                this.I(player);
                break;
            }
            case admin_skill_list: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/skills.htm"));
                break;
            }
            case admin_skill_index: {
                if (stringArray.length <= 1) break;
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/skills/" + stringArray[1] + ".htm"));
                break;
            }
            case admin_add_skill: {
                this.b(player, stringArray);
                break;
            }
            case admin_remove_skill: {
                this.c(player, stringArray);
                break;
            }
            case admin_get_skills: {
                this.G(player);
                break;
            }
            case admin_reset_skills: {
                this.H(player);
                break;
            }
            case admin_give_all_skills: {
                this.D(player);
                break;
            }
            case admin_debug_stats: {
                this.C(player);
                break;
            }
            case admin_remove_cooldown: {
                Player player2;
                Player player3 = player.getTarget() != null ? player.getTarget().getPlayer() : (player2 = stringArray.length > 1 ? GameObjectsStorage.getPlayer(stringArray[1]) : null);
                if (player2 != null) {
                    player2.resetReuse();
                    player2.sendPacket((IStaticPacket)new SkillCoolTime(player2));
                    this.E(player);
                    player.sendMessage("Skills reuse was reset to player " + player2.getName());
                    break;
                }
                player.sendMessage("Usage: //remove_cooldown [<target>|player_name]");
                break;
            }
            case admin_buff: {
                for (int i = 7041; i <= 7064; ++i) {
                    player.addSkill(SkillTable.getInstance().getInfo(i, 1));
                }
                player.sendSkillList();
                break;
            }
            case admin_skill_ench: 
            case admin_skill_enchant: {
                player.sendPacket((IStaticPacket)ExEnchantSkillList.packetFor(player, (TrainerInstance)player.getLastNpc()));
                break;
            }
            case admin_add_clan_skill: {
                this.d(player, stringArray);
                break;
            }
            case admin_give_all_clan_skills: {
                this.J(player);
            }
        }
        return true;
    }

    private void C(Player player) {
        GameObject gameObject = player.getTarget();
        if (!gameObject.isCreature()) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Creature creature = (Creature)gameObject;
        Calculator[] calculatorArray = creature.getCalculators();
        String string = "--- Debug for " + creature.getName() + " ---\r\n";
        for (Calculator calculator : calculatorArray) {
            if (calculator == null) continue;
            Env env = new Env(creature, player, null);
            env.value = calculator.getBase();
            string = string + "Stat: " + calculator._stat.getValue() + ", prevValue: " + calculator.getLast() + "\r\n";
            Func[] funcArray = calculator.getFunctions();
            for (int i = 0; i < funcArray.length; ++i) {
                Object object = Integer.toHexString(funcArray[i].order).toUpperCase();
                if (((String)object).length() == 1) {
                    object = "0" + (String)object;
                }
                string = string + "\tFunc #" + i + "@ [0x" + (String)object + "]" + funcArray[i].getClass().getSimpleName() + "\t" + env.value;
                if (funcArray[i].getCondition() == null || funcArray[i].getCondition().test(env)) {
                    funcArray[i].calc(env);
                }
                string = string + " -> " + env.value + (String)(funcArray[i].owner != null ? "; owner: " + funcArray[i].owner.toString() : "; no owner") + "\r\n";
            }
        }
        Log.add(string, "debug_stats");
    }

    private void D(Player player) {
        GameObject gameObject = player.getTarget();
        Player player2 = null;
        if (gameObject == null || !gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        player2 = (Player)gameObject;
        int n = 0;
        int n2 = 0;
        Collection<SkillLearn> collection = SkillAcquireHolder.getInstance().getAvailableSkills(player2, AcquireType.NORMAL);
        while (collection.size() > n) {
            n = 0;
            for (SkillLearn skillLearn : collection) {
                Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                if (skill == null || !skill.getCanLearn(player2.getClassId())) {
                    ++n;
                    continue;
                }
                if (player2.getSkillLevel(skill.getId()) == -1) {
                    ++n2;
                }
                player2.addSkill(skill, true);
            }
            collection = SkillAcquireHolder.getInstance().getAvailableSkills(player2, AcquireType.NORMAL);
        }
        player2.sendMessage("Admin gave you " + n2 + " skills.");
        player2.sendSkillList();
        this.E(player);
        player.sendMessage("You gave " + n2 + " skills to " + player2.getName());
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void h(Player player, int n) {
        GameObject gameObject = player.getTarget();
        if (!gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        ArrayList<Skill> arrayList = new ArrayList<Skill>(player2.getAllSkills());
        int n2 = 15;
        int n3 = (int)Math.ceil((double)arrayList.size() / (double)n2);
        if (n < 1 || n > n3) {
            n = 1;
        }
        int n4 = (n - 1) * n2;
        int n5 = Math.min(n4 + n2, arrayList.size());
        List list = arrayList.subList(n4, n5);
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15  back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_show_skills\" width=40 height=15  back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<center>Editing character: ").append(player2.getName()).append("</center>");
        stringBuilder.append("<br><table width=270><tr><td>Lv: ").append(player2.getLevel()).append(" ").append(player2.getTemplate().className).append("</td></tr></table>");
        stringBuilder.append("<br><center>Click on the skill you wish to remove:</center>");
        stringBuilder.append("<br><table width=270>");
        stringBuilder.append("<tr><td width=80>Name:</td><td width=60>Level:</td><td width=40>Id:</td></tr>");
        for (Skill skill : list) {
            stringBuilder.append("<tr><td width=80><a action=\"bypass -h admin_remove_skill " + skill.getId() + "\">" + skill.getName() + "</a></td><td width=60>" + skill.getLevel() + "</td><td width=40>" + skill.getId() + "</td></tr>");
        }
        stringBuilder.append("</table>");
        stringBuilder.append("<br><center><table>");
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>Remove skill Id: <edit var=\"id_to_remove\" width=100></td></tr>");
        stringBuilder.append("</table></center>");
        stringBuilder.append("<br>");
        stringBuilder.append("<center><button value=\"Remove skill\" action=\"bypass -h admin_remove_skill $id_to_remove\" width=100 height=15  back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></center>");
        stringBuilder.append("<br><center><table width=260><tr>");
        if (n > 1) {
            stringBuilder.append("<td align=\"left\"><button value=\"Previous\" action=\"bypass -h admin_remove_skills ").append(n - 1).append("\" width=80 height=15  back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        } else {
            stringBuilder.append("<td align=\"left\"></td>");
        }
        stringBuilder.append("<td align=\"center\">Page ").append(n).append(" of ").append(n3).append("</td>");
        if (n < n3) {
            stringBuilder.append("<td align=\"right\"><button value=\"Next\" action=\"bypass -h admin_remove_skills ").append(n + 1).append("\" width=80 height=15  back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        } else {
            stringBuilder.append("<td align=\"right\"></td>");
        }
        stringBuilder.append("</tr></table></center>");
        stringBuilder.append("<br><center><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15  back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void E(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<center>Editing character: " + player2.getName() + "</center>");
        stringBuilder.append("<br><table width=270><tr><td>Lv: " + player2.getLevel() + " " + player2.getTemplate().className + "</td></tr></table>");
        stringBuilder.append("<br><center><table>");
        stringBuilder.append("<tr><td><button value=\"Add skills\" action=\"bypass -h admin_skill_list\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Get skills\" action=\"bypass -h admin_get_skills\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("<tr><td><button value=\"Delete skills\" action=\"bypass -h admin_remove_skills\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Reset skills\" action=\"bypass -h admin_reset_skills\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("<tr><td><button value=\"Give All Skills\" action=\"bypass -h admin_give_all_skills\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Delete All Skills\" action=\"bypass -h admin_delete_skills\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("<tr><td><button value=\"Remove Reuse\" action=\"bypass -h admin_remove_cooldown\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"Skill Enchant\" action=\"bypass -h admin_skill_enchant\" width=90 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
        stringBuilder.append("</table></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void F(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayable() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Playable playable = (Playable)gameObject;
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><body>");
        stringBuilder.append("<table width=260><tr>");
        stringBuilder.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td width=180><center>Character Selection Menu</center></td>");
        stringBuilder.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<br><br>");
        stringBuilder.append("<center>Editing character: " + playable.getName() + "</center>");
        stringBuilder.append("<br><center><button value=\"Refresh\" action=\"bypass -h admin_show_effects\" width=100 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" /></center>");
        stringBuilder.append("<br>");
        List<Effect> list = playable.getEffectList().getAllEffects();
        if (list != null && !list.isEmpty()) {
            for (Effect effect : list) {
                stringBuilder.append("&nbsp;<a action=\"bypass -h admin_stop_effect ").append(effect.getSkill().getId()).append("\">");
                stringBuilder.append(effect.getSkill().getName()).append(" ").append(effect.getSkill().getLevel());
                stringBuilder.append("</a> - ").append((String)(effect.getSkill().isToggle() ? "Infinity" : effect.getTimeLeft() + " seconds")).append("<br1>");
            }
        }
        stringBuilder.append("<br></body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void a(Player player, String[] stringArray) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayable() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Playable playable = (Playable)gameObject;
        if (stringArray.length == 2) {
            int n = Integer.parseInt(stringArray[1]);
            List<Effect> list = playable.getEffectList().getEffectsBySkillId(n);
            if (list != null && !list.isEmpty()) {
                for (Effect effect : list) {
                    effect.exit();
                    playable.getPlayer().sendMessage("Admin removed effect of " + effect.getSkill().getName() + ".");
                    playable.sendChanges();
                    playable.updateStats();
                    playable.updateEffectIcons();
                    playable.broadcastStatusUpdate();
                    player.sendMessage("You removed effect of " + effect.getSkill().getName() + " from " + playable.getName() + ".");
                }
            } else {
                player.sendMessage("Error: there is no such skill.");
            }
        }
        this.F(player);
    }

    private void G(Player player) {
        GameObject gameObject = player.getTarget();
        if (!gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        if (player2.getName().equals(player.getName())) {
            player2.sendMessage("There is no point in doing it on your character.");
        } else {
            Collection<Skill> collection = player2.getAllSkills();
            d = player.getAllSkillsArray();
            for (Skill skill : d) {
                player.removeSkill(skill, true);
            }
            for (Skill skill : collection) {
                player.addSkill(skill, true);
            }
            player.sendMessage("You now have all the skills of  " + player2.getName() + ".");
        }
        this.E(player);
    }

    private void H(Player player) {
        GameObject gameObject = player.getTarget();
        Player player2 = null;
        if (!gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        player2 = (Player)gameObject;
        Skill[] skillArray = player2.getAllSkillsArray();
        int n = 0;
        for (Skill skill : skillArray) {
            if (skill.isClanSkill() || skill.isCommon() || SkillAcquireHolder.getInstance().isSkillPossible(player2, skill)) continue;
            player2.removeSkill(skill, true);
            player2.removeSkillFromShortCut(skill.getId());
            ++n;
        }
        player2.checkSkills();
        player2.sendSkillList();
        player2.sendMessage("[GM]" + player.getName() + " has updated your skills.");
        player.sendMessage(n + " skills removed.");
        this.E(player);
    }

    private void b(Player player, String[] stringArray) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        if (stringArray.length == 3) {
            int n = Integer.parseInt(stringArray[1]);
            int n2 = Integer.parseInt(stringArray[2]);
            Skill skill = SkillTable.getInstance().getInfo(n, n2);
            if (skill != null) {
                player2.sendMessage("Admin gave you the skill " + skill.getName() + ".");
                player2.addSkill(skill, true);
                player2.sendSkillList();
                player.sendMessage("You gave the skill " + skill.getName() + " to " + player2.getName() + ".");
            } else {
                player.sendMessage("Error: there is no such skill.");
            }
        }
        this.E(player);
    }

    private void c(Player player, String[] stringArray) {
        GameObject gameObject = player.getTarget();
        Player player2 = null;
        if (!gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        player2 = (Player)gameObject;
        if (stringArray.length == 2) {
            int n = Integer.parseInt(stringArray[1]);
            int n2 = player2.getSkillLevel(n);
            Skill skill = SkillTable.getInstance().getInfo(n, n2);
            if (skill != null) {
                player2.sendMessage("Admin removed the skill " + skill.getName() + ".");
                player2.removeSkill(skill, true);
                player2.sendSkillList();
                player.sendMessage("You removed the skill " + skill.getName() + " from " + player2.getName() + ".");
            } else {
                player.sendMessage("Error: there is no such skill.");
            }
        }
        this.h(player, 1);
    }

    private void I(Player player) {
        GameObject gameObject = player.getTarget();
        if (!gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        for (Skill skill : ((Player)gameObject).getAllSkills()) {
            player2.removeSkill(skill, true);
            player2.sendMessage("Admin removed the skill " + skill.getName() + ".");
        }
        player2.sendSkillList();
        player2.sendMessage("Admin removed all skills ");
        this.E(player);
    }

    private void d(Player player, String[] stringArray) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        Clan clan = player2.getClan();
        if (clan == null) {
            player.sendMessage("Player have no clan");
            return;
        }
        if (clan.getSkillLevel(370) == 3 && clan.getSkillLevel(373) == 3 && clan.getSkillLevel(379) == 3 && clan.getSkillLevel(391) == 1 && clan.getSkillLevel(371) == 3 && clan.getSkillLevel(374) == 3 && clan.getSkillLevel(376) == 3 && clan.getSkillLevel(377) == 3 && clan.getSkillLevel(383) == 3 && clan.getSkillLevel(380) == 3 && clan.getSkillLevel(382) == 3 && clan.getSkillLevel(384) == 3 && clan.getSkillLevel(385) == 3 && clan.getSkillLevel(386) == 3 && clan.getSkillLevel(387) == 3 && clan.getSkillLevel(388) == 3 && clan.getSkillLevel(390) == 3 && clan.getSkillLevel(372) == 3 && clan.getSkillLevel(375) == 3 && clan.getSkillLevel(378) == 3 && clan.getSkillLevel(381) == 3 && clan.getSkillLevel(389) == 3) {
            player.sendMessage("All Clan skill already exists.");
            return;
        }
        if (stringArray.length == 3) {
            int n = Integer.parseInt(stringArray[1]);
            int n2 = Integer.parseInt(stringArray[2]);
            Skill skill = SkillTable.getInstance().getInfo(n, n2);
            if (skill != null && skill.isClanSkill()) {
                clan.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_CLAN_SKILL_S1_HAS_BEEN_ADDED).addSkillName(skill)});
                player2.sendMessage("Admin gave you the clan skill " + skill.getName() + ".");
                clan.addSkill(skill, true);
                player.sendMessage("You gave the clan skill " + skill.getName() + " to " + player2.getName() + ".");
            } else {
                player.sendMessage("Error: Skill ID " + n + " is not a clan skill or skill is null");
            }
        }
    }

    private void J(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isPlayer() || player != gameObject && !player.getPlayerAccess().CanEditCharAll) {
            player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
            return;
        }
        Player player2 = (Player)gameObject;
        Clan clan = player2.getClan();
        if (clan == null) {
            player.sendMessage("Player have no clan");
            return;
        }
        if (clan.getSkillLevel(370) == 3 && clan.getSkillLevel(373) == 3 && clan.getSkillLevel(379) == 3 && clan.getSkillLevel(391) == 1 && clan.getSkillLevel(371) == 3 && clan.getSkillLevel(374) == 3 && clan.getSkillLevel(376) == 3 && clan.getSkillLevel(377) == 3 && clan.getSkillLevel(383) == 3 && clan.getSkillLevel(380) == 3 && clan.getSkillLevel(382) == 3 && clan.getSkillLevel(384) == 3 && clan.getSkillLevel(385) == 3 && clan.getSkillLevel(386) == 3 && clan.getSkillLevel(387) == 3 && clan.getSkillLevel(388) == 3 && clan.getSkillLevel(390) == 3 && clan.getSkillLevel(372) == 3 && clan.getSkillLevel(375) == 3 && clan.getSkillLevel(378) == 3 && clan.getSkillLevel(381) == 3 && clan.getSkillLevel(389) == 3) {
            player.sendMessage("All Clan skill already exists.");
            return;
        }
        for (Skill skill : ap) {
            clan.addSkill(skill, true);
            clan.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_CLAN_SKILL_S1_HAS_BEEN_ADDED).addSkillName(skill)});
        }
        player.sendMessage("All clan skills added to clan " + clan.getName());
    }

    static {
        ap = Arrays.asList(SkillTable.getInstance().getInfo(370, 3), SkillTable.getInstance().getInfo(373, 3), SkillTable.getInstance().getInfo(379, 3), SkillTable.getInstance().getInfo(391, 1), SkillTable.getInstance().getInfo(371, 3), SkillTable.getInstance().getInfo(374, 3), SkillTable.getInstance().getInfo(376, 3), SkillTable.getInstance().getInfo(377, 3), SkillTable.getInstance().getInfo(383, 3), SkillTable.getInstance().getInfo(380, 3), SkillTable.getInstance().getInfo(382, 3), SkillTable.getInstance().getInfo(384, 3), SkillTable.getInstance().getInfo(385, 3), SkillTable.getInstance().getInfo(386, 3), SkillTable.getInstance().getInfo(387, 3), SkillTable.getInstance().getInfo(388, 3), SkillTable.getInstance().getInfo(390, 3), SkillTable.getInstance().getInfo(372, 3), SkillTable.getInstance().getInfo(375, 3), SkillTable.getInstance().getInfo(378, 3), SkillTable.getInstance().getInfo(381, 3), SkillTable.getInstance().getInfo(389, 3));
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_show_skills = new Commands();
        public static final /* enum */ Commands admin_remove_skills = new Commands();
        public static final /* enum */ Commands admin_skill_list = new Commands();
        public static final /* enum */ Commands admin_skill_index = new Commands();
        public static final /* enum */ Commands admin_add_skill = new Commands();
        public static final /* enum */ Commands admin_remove_skill = new Commands();
        public static final /* enum */ Commands admin_get_skills = new Commands();
        public static final /* enum */ Commands admin_delete_skills = new Commands();
        public static final /* enum */ Commands admin_reset_skills = new Commands();
        public static final /* enum */ Commands admin_give_all_skills = new Commands();
        public static final /* enum */ Commands admin_show_effects = new Commands();
        public static final /* enum */ Commands admin_stop_effect = new Commands();
        public static final /* enum */ Commands admin_debug_stats = new Commands();
        public static final /* enum */ Commands admin_remove_cooldown = new Commands();
        public static final /* enum */ Commands admin_buff = new Commands();
        public static final /* enum */ Commands admin_skill_ench = new Commands();
        public static final /* enum */ Commands admin_skill_enchant = new Commands();
        public static final /* enum */ Commands admin_add_clan_skill = new Commands();
        public static final /* enum */ Commands admin_give_all_clan_skills = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_show_skills, admin_remove_skills, admin_skill_list, admin_skill_index, admin_add_skill, admin_remove_skill, admin_get_skills, admin_delete_skills, admin_reset_skills, admin_give_all_skills, admin_show_effects, admin_stop_effect, admin_debug_stats, admin_remove_cooldown, admin_buff, admin_skill_ench, admin_skill_enchant, admin_add_clan_skill, admin_give_all_clan_skills};
        }

        static {
            a = Commands.a();
        }
    }
}
