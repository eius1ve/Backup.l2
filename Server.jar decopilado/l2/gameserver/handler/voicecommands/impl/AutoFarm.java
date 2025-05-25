/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.handler.voicecommands.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.actor.instances.player.AutoFarmContext;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.taskmanager.AutoFarmManager;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.TimeUtils;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AutoFarm
implements IVoicedCommandHandler {
    private static final String[] az = new String[]{"autofarm", "autosummonfarm", "farmstart", "farmstop", "buyfarm", "buyfarmTime", "tryFreeTime", "expendLimit", "changeSkillType", "refreshSkills", "removeSkill", "addSkill", "addNewSkill", "editFarmOption", "editSummonSkills", "removeSummonSkill", "addSummonSkill", "addNewSummonSkill", "editSummonFarmOption"};

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.ALLOW_AUTO_FARM) {
            return false;
        }
        if (player == null) {
            return false;
        }
        AutoFarmContext autoFarmContext = player.getFarmSystem();
        int n = player.getVarInt("farmType", Config.FARM_TYPE);
        long l = System.currentTimeMillis();
        if (string.startsWith("farmstart")) {
            String[] stringArray = string2.split(" ");
            String string3 = "attack";
            try {
                string3 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string3 == null || string3.isEmpty()) {
                string3 = "attack";
            }
            if (autoFarmContext.isActiveAutofarmAllowed()) {
                autoFarmContext.startFarmTask();
            } else {
                player.sendMessage(new CustomMessage("CANT_ACTIVATE_AUTO_FARM_YOU_HAVE_TO_PURCHASE_IT", player, new Object[0]));
            }
            this.a(player, autoFarmContext, null, n, string3);
            return true;
        }
        if (string.startsWith("editSummonSkills")) {
            String[] stringArray = string2.split(" ");
            String string4 = "attack";
            String string5 = "attack";
            try {
                string5 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string4 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string4 == null || string4.isEmpty()) {
                string4 = "attack";
            }
            if (string5 == null || string5.isEmpty()) {
                string5 = "attack";
            }
            if (!autoFarmContext.isUseSummonSkills()) {
                player.sendMessage(new CustomMessage("YOU_CANT_EDIT_SETTINGS_THE_OPTION_IS_DISABLED", player, new Object[0]));
                this.a(player, autoFarmContext, null, n, string4);
                return false;
            }
            if (player.getPet() == null) {
                player.sendMessage(new CustomMessage("YOU_CANT_USE_THIS_OPTION", player, new Object[0]));
                this.a(player, autoFarmContext, null, n, string4);
                return false;
            }
            this.a(player, autoFarmContext, null, n, string5, string4);
            return true;
        }
        if (string.startsWith("changeSkillType")) {
            String[] stringArray = string2.split(" ");
            String string6 = "attack";
            try {
                string6 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string6 == null || string6.isEmpty()) {
                string6 = "attack";
            }
            this.a(player, autoFarmContext, null, n, string6);
            return true;
        }
        if (string.startsWith("refreshSkills")) {
            String[] stringArray = string2.split(" ");
            String string7 = "attack";
            String string8 = "1";
            try {
                string7 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string8 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string7 == null || string7.isEmpty()) {
                string7 = "attack";
            }
            if (string8 == null || string8.isEmpty()) {
                string8 = "1";
            }
            autoFarmContext.setShortcutPageValue(Integer.parseInt(string8));
            autoFarmContext.checkAllSlots();
            this.a(player, autoFarmContext, null, n, string7);
            return true;
        }
        if (string.startsWith("removeSkill")) {
            String[] stringArray = string2.split(" ");
            String string9 = null;
            String string10 = null;
            try {
                string9 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string10 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string9 == null || string9.isEmpty()) {
                string9 = "attack";
            }
            if (string9 != null && string10 != null) {
                List<Integer> list = null;
                switch (string9) {
                    case "attack": {
                        list = autoFarmContext.getAttackSpells();
                        break;
                    }
                    case "chance": {
                        list = autoFarmContext.getChanceSpells();
                        break;
                    }
                    case "self": {
                        list = autoFarmContext.getSelfSpells();
                        break;
                    }
                    case "heal": {
                        list = autoFarmContext.getLowLifeSpells();
                    }
                }
                if (list != null && list.contains(Integer.parseInt(string10))) {
                    list.remove((Object)Integer.parseInt(string10));
                    switch (string9) {
                        case "attack": {
                            autoFarmContext.saveSkills("farmAttackSkills");
                            break;
                        }
                        case "chance": {
                            autoFarmContext.saveSkills("farmChanceSkills");
                            break;
                        }
                        case "self": {
                            autoFarmContext.saveSkills("farmSelfSkills");
                            break;
                        }
                        case "heal": {
                            autoFarmContext.saveSkills("farmHealSkills");
                        }
                    }
                }
            }
            this.a(player, autoFarmContext, null, n, string9);
            return true;
        }
        if (string.startsWith("removeSummonSkill")) {
            String[] stringArray = string2.split(" ");
            String string11 = null;
            String string12 = null;
            String string13 = null;
            try {
                string11 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string12 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string13 = stringArray[2];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string11 != null && string12 != null && string13 != null) {
                List<Integer> list = null;
                switch (string11) {
                    case "attack": {
                        list = autoFarmContext.getSummonAttackSpells();
                        break;
                    }
                    case "self": {
                        list = autoFarmContext.getSummonSelfSpells();
                        break;
                    }
                    case "heal": {
                        list = autoFarmContext.getSummonHealSpells();
                    }
                }
                if (list != null && list.contains(Integer.parseInt(string12))) {
                    list.remove((Object)Integer.parseInt(string12));
                    switch (string13) {
                        case "attack": {
                            autoFarmContext.saveSkills("farmAttackSummonSkills");
                            break;
                        }
                        case "self": {
                            autoFarmContext.saveSkills("farmSelfSummonSkills");
                            break;
                        }
                        case "heal": {
                            autoFarmContext.saveSkills("farmHealSummonSkills");
                        }
                    }
                }
            }
            this.a(player, autoFarmContext, null, n, string11, string13);
            return true;
        }
        if (string.startsWith("addSkill")) {
            String[] stringArray = string2.split(" ");
            String string14 = null;
            String string15 = "1";
            try {
                string14 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string15 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string14 != null) {
                this.a(player, autoFarmContext, string14, Integer.parseInt(string15));
            }
            return true;
        }
        if (string.startsWith("addSummonSkill")) {
            String[] stringArray = string2.split(" ");
            String string16 = null;
            String string17 = null;
            String string18 = "1";
            try {
                string16 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string17 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string18 = stringArray[2];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string16 != null && string17 != null) {
                this.a(player, autoFarmContext, n, string16, string17, Integer.parseInt(string18));
            }
            return true;
        }
        if (string.startsWith("addNewSkill")) {
            Skill skill;
            String[] stringArray = string2.split(" ");
            String string19 = null;
            String string20 = null;
            try {
                string19 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string20 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string20 == null || string20.isEmpty()) {
                string20 = "attack";
            }
            if (string19 != null && string20 != null && (skill = player.getKnownSkill(Integer.parseInt(string19))) != null) {
                switch (string20) {
                    case "attack": {
                        if (autoFarmContext.getAttackSpells().size() >= 8) {
                            this.a(player, autoFarmContext, null, n, string20);
                            return false;
                        }
                        if (skill.isSpoilSkill() || skill.isSweepSkill() || skill.getId() == 1263 || skill.getSkillType() != Skill.SkillType.AGGRESSION && skill.getSkillType() != Skill.SkillType.PDAM && skill.getSkillType() != Skill.SkillType.MANADAM && skill.getSkillType() != Skill.SkillType.MDAM && skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.CPDAM && skill.getSkillType() != Skill.SkillType.STUN) break;
                        autoFarmContext.getAttackSpells().add(skill.getId());
                        autoFarmContext.saveSkills("farmAttackSkills");
                        break;
                    }
                    case "chance": {
                        if (autoFarmContext.getChanceSpells().size() >= 8) {
                            this.a(player, autoFarmContext, null, n, string20);
                            return false;
                        }
                        if (skill.getSkillType() != Skill.SkillType.DOT && skill.getSkillType() != Skill.SkillType.MDOT && skill.getSkillType() != Skill.SkillType.POISON && skill.getSkillType() != Skill.SkillType.BLEED && skill.getSkillType() != Skill.SkillType.DEBUFF && skill.getSkillType() != Skill.SkillType.SLEEP && skill.getSkillType() != Skill.SkillType.ROOT && skill.getSkillType() != Skill.SkillType.PARALYZE && skill.getSkillType() != Skill.SkillType.MUTE && !skill.isSpoilSkill() && !skill.isSweepSkill() && skill.getId() != 1263) break;
                        autoFarmContext.getChanceSpells().add(skill.getId());
                        autoFarmContext.saveSkills("farmChanceSkills");
                        break;
                    }
                    case "self": {
                        if (autoFarmContext.getSelfSpells().size() >= 8) {
                            this.a(player, autoFarmContext, null, n, string20);
                            return false;
                        }
                        if (!(skill.isToggle() || skill.isMusic() || skill.getSkillType() == Skill.SkillType.BUFF || skill.isCubicSkill())) {
                            return false;
                        }
                        autoFarmContext.getSelfSpells().add(skill.getId());
                        autoFarmContext.saveSkills("farmSelfSkills");
                        break;
                    }
                    case "heal": {
                        if (autoFarmContext.getLowLifeSpells().size() >= 8) {
                            this.a(player, autoFarmContext, null, n, string20);
                            return false;
                        }
                        if (skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.HEAL && skill.getSkillType() != Skill.SkillType.HEAL_PERCENT && skill.getSkillType() != Skill.SkillType.MANAHEAL && skill.getSkillType() != Skill.SkillType.MANAHEAL_PERCENT) {
                            return false;
                        }
                        autoFarmContext.getLowLifeSpells().add(skill.getId());
                        autoFarmContext.saveSkills("farmHealSkills");
                    }
                }
            }
            this.a(player, autoFarmContext, null, n, string20);
            return true;
        }
        if (string.startsWith("addNewSummonSkill")) {
            String[] stringArray = string2.split(" ");
            String string21 = null;
            String string22 = null;
            String string23 = null;
            try {
                string21 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string22 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string23 = stringArray[2];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string21 != null && string22 != null && string23 != null) {
                Skill skill;
                if (player.getPet() == null) {
                    player.sendMessage(new CustomMessage("YOU_CANT_USE_THIS_OPTION", player, new Object[0]));
                    this.a(player, autoFarmContext, null, n, string22, string23);
                    return false;
                }
                Summon summon = player.getPet();
                if (summon.isPet() && summon.getLevel() - player.getLevel() > 20) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_IS_TOO_HIGH_LEVEL_TO_CONTROL);
                    this.a(player, autoFarmContext, null, n, string22, string23);
                    return false;
                }
                int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, Integer.parseInt(string21));
                if (n2 > 0 && (skill = SkillTable.getInstance().getInfo(Integer.parseInt(string21), n2)) != null) {
                    switch (string22) {
                        case "attack": {
                            if (autoFarmContext.getSummonAttackSpells().size() >= 8) {
                                this.a(player, autoFarmContext, null, n, string22, string23);
                                return false;
                            }
                            if (skill.getSkillType() != Skill.SkillType.AGGRESSION && skill.getSkillType() != Skill.SkillType.PDAM && skill.getSkillType() != Skill.SkillType.MANADAM && skill.getSkillType() != Skill.SkillType.MDAM && skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.CPDAM && skill.getSkillType() != Skill.SkillType.STUN) break;
                            autoFarmContext.getSummonAttackSpells().add(skill.getId());
                            autoFarmContext.saveSkills("farmAttackSummonSkills");
                            break;
                        }
                        case "self": {
                            if (autoFarmContext.getSummonSelfSpells().size() >= 8) {
                                this.a(player, autoFarmContext, null, n, string22, string23);
                                return false;
                            }
                            if (!(skill.isToggle() || skill.isMusic() || skill.getSkillType() == Skill.SkillType.BUFF || skill.isCubicSkill())) {
                                return false;
                            }
                            autoFarmContext.getSummonSelfSpells().add(skill.getId());
                            autoFarmContext.saveSkills("farmSelfSummonSkills");
                            break;
                        }
                        case "heal": {
                            if (autoFarmContext.getSummonHealSpells().size() >= 8) {
                                this.a(player, autoFarmContext, null, n, string22, string23);
                                return false;
                            }
                            if (skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.HEAL && skill.getSkillType() != Skill.SkillType.HEAL_PERCENT && skill.getSkillType() != Skill.SkillType.MANAHEAL && skill.getSkillType() != Skill.SkillType.MANAHEAL_PERCENT) {
                                return false;
                            }
                            autoFarmContext.getSummonHealSpells().add(skill.getId());
                            autoFarmContext.saveSkills("farmHealSummonSkills");
                        }
                    }
                }
            }
            this.a(player, autoFarmContext, null, n, string22, string23);
            return true;
        }
        if (string.startsWith("editFarmOption")) {
            String[] stringArray = string2.split(" ");
            String string24 = "attack";
            String string25 = null;
            try {
                string24 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string25 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string24 != null && string25 != null) {
                boolean bl = false;
                switch (n) {
                    case 0: {
                        boolean bl2;
                        if (string25.equalsIgnoreCase("farmLeaderAssist")) {
                            bl2 = !autoFarmContext.isLeaderAssist();
                            autoFarmContext.setLeaderAssist(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmAssistMonsterAttack")) {
                            bl2 = !autoFarmContext.isAssistMonsterAttack();
                            autoFarmContext.setAssistMonsterAttack(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmKeepLocation")) {
                            bl2 = !autoFarmContext.isKeepLocation();
                            autoFarmContext.setKeepLocation(player.getLoc(), bl2, false);
                            bl = true;
                            break;
                        }
                        if (!string25.equalsIgnoreCase("farmDelaySkills")) break;
                        bl2 = !autoFarmContext.isExtraDelaySkill();
                        autoFarmContext.setExDelaySkill(bl2, false);
                        bl = true;
                        break;
                    }
                    case 1: {
                        boolean bl2;
                        if (string25.equalsIgnoreCase("farmLeaderAssist")) {
                            bl2 = !autoFarmContext.isLeaderAssist();
                            autoFarmContext.setLeaderAssist(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmAssistMonsterAttack")) {
                            bl2 = !autoFarmContext.isAssistMonsterAttack();
                            autoFarmContext.setAssistMonsterAttack(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmKeepLocation")) {
                            bl2 = !autoFarmContext.isKeepLocation();
                            autoFarmContext.setKeepLocation(player.getLoc(), bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmDelaySkills")) {
                            bl2 = !autoFarmContext.isExtraDelaySkill();
                            autoFarmContext.setExDelaySkill(bl2, false);
                            bl = true;
                            break;
                        }
                        if (!string25.equalsIgnoreCase("farmRunTargetCloseUp")) break;
                        bl2 = !autoFarmContext.isRunTargetCloseUp();
                        autoFarmContext.setRunTargetCloseUp(bl2, false);
                        bl = true;
                        break;
                    }
                    case 2: {
                        boolean bl2;
                        if (string25.equalsIgnoreCase("farmLeaderAssist")) {
                            bl2 = !autoFarmContext.isLeaderAssist();
                            autoFarmContext.setLeaderAssist(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmAssistMonsterAttack")) {
                            bl2 = !autoFarmContext.isAssistMonsterAttack();
                            autoFarmContext.setAssistMonsterAttack(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmKeepLocation")) {
                            bl2 = !autoFarmContext.isKeepLocation();
                            autoFarmContext.setKeepLocation(player.getLoc(), bl2, false);
                            bl = true;
                            break;
                        }
                        if (!string25.equalsIgnoreCase("farmRunTargetCloseUp")) break;
                        bl2 = !autoFarmContext.isRunTargetCloseUp();
                        autoFarmContext.setRunTargetCloseUp(bl2, false);
                        bl = true;
                        break;
                    }
                    case 3: {
                        boolean bl2;
                        if (string25.equalsIgnoreCase("farmLeaderAssist")) {
                            bl2 = !autoFarmContext.isLeaderAssist();
                            autoFarmContext.setLeaderAssist(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmAssistMonsterAttack")) {
                            bl2 = !autoFarmContext.isAssistMonsterAttack();
                            autoFarmContext.setAssistMonsterAttack(bl2, false);
                            bl = true;
                            break;
                        }
                        if (!string25.equalsIgnoreCase("farmTargetRestoreMp")) break;
                        bl2 = !autoFarmContext.isTargetRestoreMp();
                        autoFarmContext.setTargetRestoreMp(bl2, false);
                        bl = true;
                        break;
                    }
                    case 4: {
                        boolean bl2;
                        if (string25.equalsIgnoreCase("farmLeaderAssist")) {
                            bl2 = !autoFarmContext.isLeaderAssist();
                            autoFarmContext.setLeaderAssist(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmAssistMonsterAttack")) {
                            bl2 = !autoFarmContext.isAssistMonsterAttack();
                            autoFarmContext.setAssistMonsterAttack(bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmKeepLocation")) {
                            bl2 = !autoFarmContext.isKeepLocation();
                            autoFarmContext.setKeepLocation(player.getLoc(), bl2, false);
                            bl = true;
                            break;
                        }
                        if (string25.equalsIgnoreCase("farmUseSummonSkills")) {
                            if (player.getPet() == null) {
                                player.sendMessage(new CustomMessage("YOU_CANT_USE_THIS_OPTION", player, new Object[0]));
                                this.a(player, autoFarmContext, null, n, string24);
                                return true;
                            }
                            bl2 = !autoFarmContext.isUseSummonSkills();
                            autoFarmContext.setUseSummonSkills(bl2, false);
                            bl = true;
                            break;
                        }
                        if (!string25.equalsIgnoreCase("farmDelaySkills")) break;
                        bl2 = !autoFarmContext.isExtraDelaySkill();
                        autoFarmContext.setExDelaySkill(bl2, false);
                        bl = true;
                    }
                }
                if (bl) {
                    this.a(player, autoFarmContext, null, n, string24);
                    return true;
                }
                switch (string24) {
                    case "attack": {
                        if (string25.equalsIgnoreCase("farmRndAttackSkills")) {
                            boolean bl3 = !autoFarmContext.isRndAttackSkills();
                            autoFarmContext.setRndAttackSkills(bl3, false);
                        }
                        this.a(player, autoFarmContext, null, n, string24);
                        return true;
                    }
                    case "chance": {
                        if (string25.equalsIgnoreCase("farmRndChanceSkills")) {
                            boolean bl4 = !autoFarmContext.isRndChanceSkills();
                            autoFarmContext.setRndChanceSkills(bl4, false);
                        }
                        this.a(player, autoFarmContext, null, n, string24);
                        return true;
                    }
                    case "self": {
                        if (string25.equalsIgnoreCase("farmRndSelfSkills")) {
                            boolean bl5 = !autoFarmContext.isRndSelfSkills();
                            autoFarmContext.setRndSelfSkills(bl5, false);
                        }
                        this.a(player, autoFarmContext, null, n, string24);
                        return true;
                    }
                    case "heal": {
                        if (string25.equalsIgnoreCase("farmRndLifeSkills")) {
                            boolean bl6 = !autoFarmContext.isRndLifeSkills();
                            autoFarmContext.setRndLifeSkills(bl6, false);
                        }
                        this.a(player, autoFarmContext, null, n, string24);
                        return true;
                    }
                }
            }
            this.a(player, autoFarmContext, null, n, string24);
            return true;
        }
        if (string.startsWith("editSummonFarmOption")) {
            String[] stringArray = string2.split(" ");
            String string26 = "attack";
            String string27 = "attack";
            String string28 = null;
            try {
                string26 = stringArray[0];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string28 = stringArray[1];
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                string27 = stringArray[2];
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (string26 != null && string28 != null) {
                if (string28.equalsIgnoreCase("farmSummonDelaySkills")) {
                    boolean bl = !autoFarmContext.isExtraSummonDelaySkill();
                    autoFarmContext.setExSummonDelaySkill(bl, false);
                    this.a(player, autoFarmContext, null, n, string26, string27);
                    return true;
                }
                switch (string26) {
                    case "attack": {
                        if (string28.equalsIgnoreCase("farmRndSummonAttackSkills")) {
                            boolean bl = !autoFarmContext.isRndSummonAttackSkills();
                            autoFarmContext.setRndSummonAttackSkills(bl, false);
                        }
                        this.a(player, autoFarmContext, null, n, string26, string27);
                        return true;
                    }
                    case "self": {
                        if (string28.equalsIgnoreCase("farmRndSummonSelfSkills")) {
                            boolean bl = !autoFarmContext.isRndSummonSelfSkills();
                            autoFarmContext.setRndSummonSelfSkills(bl, false);
                        }
                        this.a(player, autoFarmContext, null, n, string26, string27);
                        return true;
                    }
                    case "heal": {
                        if (string28.equalsIgnoreCase("farmRndSummonLifeSkills")) {
                            boolean bl = !autoFarmContext.isRndSummonLifeSkills();
                            autoFarmContext.setRndSummonLifeSkills(bl, false);
                        }
                        this.a(player, autoFarmContext, null, n, string26, string27);
                        return true;
                    }
                }
            }
            this.a(player, autoFarmContext, null, n, string26, string27);
            return true;
        }
        if (string.equalsIgnoreCase("farmstop")) {
            autoFarmContext.stopFarmTask();
            this.a(player, autoFarmContext, null, n, "attack");
        } else if (string.equalsIgnoreCase("expendLimit")) {
            if (autoFarmContext.isAutofarming()) {
                this.a(player, autoFarmContext, null, n, "attack");
                return false;
            }
            if (AutoFarmManager.getInstance().isNonCheckPlayer(player.getObjectId())) {
                player.sendMessage(new CustomMessage("YOU_HAVE_ALREADY_USED_THIS_SERVICE", player, new Object[0]));
                this.a(player, autoFarmContext, null, n, "attack");
                return false;
            }
            if (Config.FARM_EXPEND_LIMIT_PRICE[0] != 0) {
                if (player.getInventory().getItemByItemId(Config.FARM_EXPEND_LIMIT_PRICE[0]) == null || player.getInventory().getItemByItemId(Config.FARM_EXPEND_LIMIT_PRICE[0]).getCount() < (long)Config.FARM_EXPEND_LIMIT_PRICE[1]) {
                    ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Config.FARM_EXPEND_LIMIT_PRICE[0]);
                    if (itemTemplate != null) {
                        player.sendMessage(new CustomMessage("TO_USE_THE_SERVICE_YOU_MUST_HAVE", player, new Object[0]).addNumber(Config.FARM_EXPEND_LIMIT_PRICE[1]).addString(itemTemplate.getName()));
                    }
                    this.a(player, autoFarmContext, null, n, "attack");
                    return false;
                }
                ItemFunctions.removeItem(player, Config.FARM_EXPEND_LIMIT_PRICE[0], Config.FARM_EXPEND_LIMIT_PRICE[1], true);
                Log.service("AutoFarm", player, " bought expend period " + Config.FARM_EXPEND_LIMIT_PRICE[0] + " amount " + Config.FARM_EXPEND_LIMIT_PRICE[1]);
            }
            AutoFarmManager.getInstance().addNonCheckPlayer(player.getObjectId());
            player.sendMessage(new CustomMessage("YOUR_CHARACTER_IS_EXCLUDED_FROM_LIMITS_LIST", player, new Object[0]));
            this.a(player, autoFarmContext, null, n, "attack");
        } else {
            if (string.equalsIgnoreCase("buyfarm")) {
                if (Config.AUTO_FARM_FOR_PREMIUM && !player.hasBonus()) {
                    player.sendMessage(new CustomMessage("AUTO_FARM_PREMIUM_ONLY", player, new Object[0]));
                    this.a(player, autoFarmContext, null, n, "attack");
                } else {
                    this.a(player, autoFarmContext);
                }
                return true;
            }
            if (string.startsWith("tryFreeTime")) {
                String[] stringArray = string2.split(" ");
                String string29 = "attack";
                try {
                    string29 = stringArray[0];
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (string29 == null || string29.isEmpty()) {
                    string29 = "attack";
                }
                long l2 = player.getVarLong("farmFreeTime", 0L);
                if (!Config.ALLOW_FARM_FREE_TIME || l2 > 0L) {
                    player.sendMessage(new CustomMessage("FUNCTION_IS_NOT_AVAILABLE", player, new Object[0]));
                    this.a(player, autoFarmContext);
                    return false;
                }
                if (Config.AUTOFARM_TIME_TRACK_USAGE_ONLY) {
                    player.setVar("activeFarmOnlineTask", TimeUnit.HOURS.toMillis(Config.FARM_FREE_TIME), -1L);
                    autoFarmContext.resetFarmOnlineTimestamp();
                } else {
                    player.setVar("activeFarmTask", TimeUnit.HOURS.toMillis(Config.FARM_FREE_TIME), -1L);
                    autoFarmContext.setAutoFarmEndTask(TimeUnit.HOURS.toMillis(Config.FARM_FREE_TIME));
                }
                player.setVar("farmFreeTime", TimeUnit.HOURS.toMillis(Config.FARM_FREE_TIME) + l, -1L);
                autoFarmContext.checkFarmTask();
                this.a(player, autoFarmContext, null, n, string29);
                player.sendMessage(new CustomMessage("YOU_HAVE_SUCCESSFULLY_ACTIVATE_AUTO_FARM_FREE_TIME_SERVICE", player, new Object[0]));
            } else {
                if (string.startsWith("buyfarmTime")) {
                    if (Config.AUTO_FARM_UNLIMITED || Config.AUTO_FARM_PA_UNLIMITED && player.hasBonus()) {
                        return true;
                    }
                    String[] stringArray = string2.split(" ");
                    String string30 = null;
                    String string31 = "attack";
                    try {
                        string30 = stringArray[0];
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        string31 = stringArray[1];
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (string30 != null) {
                        int n3 = Integer.parseInt(string30);
                        boolean bl = false;
                        int n4 = 0;
                        long l3 = 0L;
                        for (Map.Entry<Integer, Pair<Integer, Long>> entry : Config.AUTO_FARM_PRICES.entrySet()) {
                            if (!entry.getKey().equals(n3)) continue;
                            bl = true;
                            n4 = (Integer)entry.getValue().getLeft();
                            l3 = (Long)entry.getValue().getRight();
                            break;
                        }
                        if (bl) {
                            if (n4 != 0) {
                                if (player.getInventory().getItemByItemId(n4) == null || player.getInventory().getItemByItemId(n4).getCount() < l3) {
                                    ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n4);
                                    if (itemTemplate != null) {
                                        player.sendMessage(new CustomMessage("TO_USE_THE_SERVICE_YOU_MUST_HAVE", player, new Object[0]).addNumber(l3).addString(itemTemplate.getName()));
                                    }
                                    this.a(player, autoFarmContext);
                                    return false;
                                }
                                ItemFunctions.removeItem(player, n4, l3, true);
                                Log.service("AutoFarm", player, "bought farm period " + n4 + " amount " + l3);
                            }
                            if (Config.AUTOFARM_TIME_TRACK_USAGE_ONLY) {
                                long l4 = autoFarmContext.getActiveTimeRemaining();
                                player.setVar("activeFarmOnlineTask", l4 += TimeUnit.HOURS.toMillis(n3), -1L);
                                if (autoFarmContext.getFarmOnlineTimestamp() > 0L) {
                                    autoFarmContext.scheduleEnd();
                                }
                                autoFarmContext.resetFarmOnlineTimestamp();
                            } else {
                                long l5 = player.getVarLong("activeFarmTask", 0L);
                                long l6 = TimeUnit.HOURS.toMillis(n3) + (l5 > l ? l5 : l);
                                player.setVar("activeFarmTask", l6, -1L);
                                autoFarmContext.setAutoFarmEndTask(l6);
                            }
                            if (autoFarmContext.isAutofarming()) {
                                autoFarmContext.stopFarmTask();
                            }
                            autoFarmContext.checkFarmTask();
                            this.a(player, autoFarmContext, null, n, string31);
                            player.sendMessage(new CustomMessage("YOU_HAVE_SUCCESSFULLY_PURCHASED_THE_AUTO_FARM_SERVICE", player, new Object[0]));
                        }
                    }
                    return true;
                }
                if (string.startsWith("autofarm")) {
                    String[] stringArray = string2.split(" ");
                    String string32 = "attack";
                    if (stringArray.length >= 2 && stringArray[0].equalsIgnoreCase("edit_farm")) {
                        try {
                            string32 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.a(player, autoFarmContext, stringArray[1], n, string32);
                    } else if (stringArray.length >= 2 && stringArray[0].equals("edit_farmType")) {
                        String string33 = null;
                        try {
                            string33 = stringArray[1];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        try {
                            string32 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (string33 != null) {
                            int n5 = Integer.parseInt(string33);
                            if (n5 > 4) {
                                n5 = 4;
                            } else if (n5 < 0) {
                                n5 = 0;
                            }
                            player.setVar("farmType", n5, -1L);
                            autoFarmContext.setFarmTypeValue(n5);
                            this.a(player, autoFarmContext, null, n5, string32);
                            return true;
                        }
                        this.a(player, autoFarmContext, null, n, string32);
                    } else if (stringArray.length >= 2) {
                        try {
                            string32 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (stringArray[0].equals("set_attackSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_chanceSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_selfSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_healSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_attackSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_chanceSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_selfSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_healSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_distance")) {
                            this.a(player, autoFarmContext, stringArray, Config.SEARCH_DISTANCE, 1);
                        } else if (stringArray[0].equals("set_shortcutPage")) {
                            this.a(player, autoFarmContext, stringArray, Config.SHORTCUT_PAGE, 1);
                        }
                        this.a(player, autoFarmContext, null, n, string32);
                    } else {
                        try {
                            string32 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.a(player, autoFarmContext, null, n, string32);
                    }
                    return true;
                }
                if (string.startsWith("autosummonfarm")) {
                    String[] stringArray = string2.split(" ");
                    String string34 = "attack";
                    if (stringArray.length >= 2 && stringArray[0].equalsIgnoreCase("edit_farm")) {
                        try {
                            string34 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.a(player, autoFarmContext, stringArray[1], n, string34, "attack");
                    } else if (stringArray.length >= 2) {
                        try {
                            string34 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (stringArray[0].equals("set_attackSummonSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_selfSummonSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_healSummonSkills")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_attackSummonSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_selfSummonSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        } else if (stringArray[0].equals("set_healSummonSkillsPercent")) {
                            this.a(player, autoFarmContext, stringArray, 100, 1);
                        }
                        this.a(player, autoFarmContext, null, n, string34, "attack");
                    } else {
                        try {
                            string34 = stringArray[2];
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.a(player, autoFarmContext, null, n, string34, "attack");
                    }
                    return true;
                }
            }
        }
        return true;
    }

    private void a(Player player, AutoFarmContext autoFarmContext, String[] stringArray, int n, int n2) {
        String string = null;
        try {
            string = stringArray[1];
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (string != null) {
            int n3;
            block53: {
                n3 = 0;
                try {
                    n3 = Integer.parseInt(string);
                    if (n3 > n) {
                        n3 = n;
                    }
                    if (n3 < n2) {
                        n3 = n2;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    if (stringArray[0].equals("set_attackSkills")) {
                        n3 = player.getVarInt("attackChanceSkills", Config.ATTACK_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_chanceSkills")) {
                        n3 = player.getVarInt("chanceChanceSkills", Config.CHANCE_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_selfSkills")) {
                        n3 = player.getVarInt("selfChanceSkills", Config.SELF_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_healSkills")) {
                        n3 = player.getVarInt("healChanceSkills", Config.HEAL_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_attackSkillsPercent")) {
                        n3 = player.getVarInt("attackSkillsPercent", Config.ATTACK_SKILL_PERCENT);
                    }
                    if (stringArray[0].equals("set_chanceSkillsPercent")) {
                        n3 = player.getVarInt("chanceSkillsPercent", Config.CHANCE_SKILL_PERCENT);
                    }
                    if (stringArray[0].equals("set_selfSkillsPercent")) {
                        n3 = player.getVarInt("selfSkillsPercent", Config.SELF_SKILL_PERCENT);
                    }
                    if (stringArray[0].equals("set_healSkillsPercent")) {
                        n3 = player.getVarInt("healSkillsPercent", Config.HEAL_SKILL_PERCENT);
                    }
                    if (stringArray[0].equals("set_distance")) {
                        n3 = player.getVarInt("farmDistance", Config.SEARCH_DISTANCE);
                    }
                    if (stringArray[0].equals("set_shortcutPage")) {
                        n3 = player.getVarInt("shortcutPage", Config.SHORTCUT_PAGE);
                    }
                    if (stringArray[0].equals("set_attackSummonSkills")) {
                        n3 = player.getVarInt("attackSummonChanceSkills", Config.SUMMON_ATTACK_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_selfSummonSkills")) {
                        n3 = player.getVarInt("selfSummonChanceSkills", Config.SUMMON_SELF_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_healSummonSkills")) {
                        n3 = player.getVarInt("healSummonChanceSkills", Config.SUMMON_HEAL_SKILL_CHANCE);
                    }
                    if (stringArray[0].equals("set_attackSummonSkillsPercent")) {
                        n3 = player.getVarInt("attackSummonSkillsPercent", Config.SUMMON_ATTACK_SKILL_PERCENT);
                    }
                    if (stringArray[0].equals("set_selfSummonSkillsPercent")) {
                        n3 = player.getVarInt("selfSummonSkillsPercent", Config.SUMMON_SELF_SKILL_PERCENT);
                    }
                    if (!stringArray[0].equals("set_healSummonSkillsPercent")) break block53;
                    n3 = player.getVarInt("healSummonSkillsPercent", Config.SUMMON_HEAL_SKILL_PERCENT);
                }
            }
            if (stringArray[0].equals("set_attackSkills")) {
                player.setVar("attackChanceSkills", n3, -1L);
                autoFarmContext.setAttackSkillValue(false, n3);
            } else if (stringArray[0].equals("set_chanceSkills")) {
                player.setVar("chanceChanceSkills", n3, -1L);
                autoFarmContext.setChanceSkillValue(false, n3);
            } else if (stringArray[0].equals("set_selfSkills")) {
                player.setVar("selfChanceSkills", n3, -1L);
                autoFarmContext.setSelfSkillValue(false, n3);
            } else if (stringArray[0].equals("set_healSkills")) {
                player.setVar("healChanceSkills", n3, -1L);
                autoFarmContext.setLifeSkillValue(false, n3);
            } else if (stringArray[0].equals("set_attackSkillsPercent")) {
                player.setVar("attackSkillsPercent", n3, -1L);
                autoFarmContext.setAttackSkillValue(true, n3);
            } else if (stringArray[0].equals("set_chanceSkillsPercent")) {
                player.setVar("chanceSkillsPercent", n3, -1L);
                autoFarmContext.setChanceSkillValue(true, n3);
            } else if (stringArray[0].equals("set_selfSkillsPercent")) {
                player.setVar("selfSkillsPercent", n3, -1L);
                autoFarmContext.setSelfSkillValue(true, n3);
            } else if (stringArray[0].equals("set_healSkillsPercent")) {
                player.setVar("healSkillsPercent", n3, -1L);
                autoFarmContext.setLifeSkillValue(true, n3);
            } else if (stringArray[0].equals("set_distance")) {
                player.setVar("farmDistance", n3, -1L);
                autoFarmContext.setRadiusValue(n3);
            } else if (stringArray[0].equals("set_shortcutPage")) {
                player.setVar("shortcutPage", n3, -1L);
                autoFarmContext.setShortcutPageValue(n3);
            } else if (stringArray[0].equals("set_attackSummonSkills")) {
                player.setVar("attackSummonChanceSkills", n3, -1L);
                autoFarmContext.setSummonAttackSkillValue(false, n3);
            } else if (stringArray[0].equals("set_selfSummonSkills")) {
                player.setVar("selfSummonChanceSkills", n3, -1L);
                autoFarmContext.setSummonSelfSkillValue(false, n3);
            } else if (stringArray[0].equals("set_healSummonSkills")) {
                player.setVar("healSummonChanceSkills", n3, -1L);
                autoFarmContext.setSummonLifeSkillValue(false, n3);
            } else if (stringArray[0].equals("set_attackSummonSkillsPercent")) {
                player.setVar("attackSummonSkillsPercent", n3, -1L);
                autoFarmContext.setSummonAttackSkillValue(true, n3);
            } else if (stringArray[0].equals("set_selfSummonSkillsPercent")) {
                player.setVar("selfSummonSkillsPercent", n3, -1L);
                autoFarmContext.setSummonSelfSkillValue(true, n3);
            } else if (stringArray[0].equals("set_healSummonSkillsPercent")) {
                player.setVar("healSummonSkillsPercent", n3, -1L);
                autoFarmContext.setSummonLifeSkillValue(true, n3);
            }
        }
    }

    private void a(Player player, AutoFarmContext autoFarmContext, String string, int n, String string2) {
        int n2;
        int n3;
        boolean bl;
        boolean bl2;
        String string3 = null;
        switch (n) {
            case 0: {
                string3 = HtmCache.getInstance().getNotNull("command/autofarm/index-fighter.htm", player);
                boolean bl3 = autoFarmContext.isLeaderAssist();
                string3 = bl3 ? string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assist_bypass%", "bypass -h user_editFarmOption " + string2 + " farmLeaderAssist");
                boolean bl4 = autoFarmContext.isAssistMonsterAttack();
                string3 = bl4 ? string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assistMAttack_bypass%", "bypass -h user_editFarmOption " + string2 + " farmAssistMonsterAttack");
                boolean bl5 = autoFarmContext.isKeepLocation();
                string3 = bl5 ? string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%keepLoc_bypass%", "bypass -h user_editFarmOption " + string2 + " farmKeepLocation");
                boolean bl6 = autoFarmContext.isExtraDelaySkill();
                string3 = bl6 ? string3.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%delaySk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmDelaySkills");
                break;
            }
            case 1: {
                string3 = HtmCache.getInstance().getNotNull("command/autofarm/index-archer.htm", player);
                boolean bl7 = autoFarmContext.isLeaderAssist();
                string3 = bl7 ? string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assist_bypass%", "bypass -h user_editFarmOption " + string2 + " farmLeaderAssist");
                boolean bl8 = autoFarmContext.isAssistMonsterAttack();
                string3 = bl8 ? string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assistMAttack_bypass%", "bypass -h user_editFarmOption " + string2 + " farmAssistMonsterAttack");
                boolean bl9 = autoFarmContext.isKeepLocation();
                string3 = bl9 ? string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%keepLoc_bypass%", "bypass -h user_editFarmOption " + string2 + " farmKeepLocation");
                boolean bl10 = autoFarmContext.isExtraDelaySkill();
                string3 = bl10 ? string3.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%delaySk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmDelaySkills");
                boolean bl11 = autoFarmContext.isRunTargetCloseUp();
                string3 = bl11 ? string3.replace("%runCloseUp_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%runCloseUp_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%runCloseUp_bypass%", "bypass -h user_editFarmOption " + string2 + " farmRunTargetCloseUp");
                break;
            }
            case 2: {
                string3 = HtmCache.getInstance().getNotNull("command/autofarm/index-mage.htm", player);
                boolean bl12 = autoFarmContext.isLeaderAssist();
                string3 = bl12 ? string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assist_bypass%", "bypass -h user_editFarmOption " + string2 + " farmLeaderAssist");
                boolean bl13 = autoFarmContext.isAssistMonsterAttack();
                string3 = bl13 ? string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assistMAttack_bypass%", "bypass -h user_editFarmOption " + string2 + " farmAssistMonsterAttack");
                int n4 = autoFarmContext.isKeepLocation() ? 1 : 0;
                string3 = n4 != 0 ? string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%keepLoc_bypass%", "bypass -h user_editFarmOption " + string2 + " farmKeepLocation");
                bl2 = autoFarmContext.isRunTargetCloseUp();
                string3 = bl2 ? string3.replace("%runCloseUp_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%runCloseUp_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%runCloseUp_bypass%", "bypass -h user_editFarmOption " + string2 + " farmRunTargetCloseUp");
                break;
            }
            case 3: {
                string3 = HtmCache.getInstance().getNotNull("command/autofarm/index-heal.htm", player);
                bl = autoFarmContext.isLeaderAssist();
                string3 = bl ? string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assist_bypass%", "bypass -h user_editFarmOption " + string2 + " farmLeaderAssist");
                n3 = autoFarmContext.isAssistMonsterAttack();
                string3 = n3 != 0 ? string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assistMAttack_bypass%", "bypass -h user_editFarmOption " + string2 + " farmAssistMonsterAttack");
                n2 = autoFarmContext.isTargetRestoreMp();
                string3 = n2 != 0 ? string3.replace("%tgRestoreMp_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%tgRestoreMp_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%tgRestoreMp_bypass%", "bypass -h user_editFarmOption " + string2 + " farmTargetRestoreMp");
                break;
            }
            case 4: {
                string3 = HtmCache.getInstance().getNotNull("command/autofarm/index-summon.htm", player);
                boolean bl14 = autoFarmContext.isLeaderAssist();
                string3 = bl14 ? string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assist_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assist_bypass%", "bypass -h user_editFarmOption " + string2 + " farmLeaderAssist");
                boolean bl15 = autoFarmContext.isAssistMonsterAttack();
                string3 = bl15 ? string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%assistMAttack_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%assistMAttack_bypass%", "bypass -h user_editFarmOption " + string2 + " farmAssistMonsterAttack");
                boolean bl16 = autoFarmContext.isKeepLocation();
                string3 = bl16 ? string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%keepLoc_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%keepLoc_bypass%", "bypass -h user_editFarmOption " + string2 + " farmKeepLocation");
                boolean bl17 = autoFarmContext.isUseSummonSkills();
                string3 = bl17 ? string3.replace("%useSummonSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%useSummonSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%useSummonSk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmUseSummonSkills");
                boolean bl18 = autoFarmContext.isExtraDelaySkill();
                string3 = bl18 ? string3.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string3.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string3 = string3.replace("%delaySk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmDelaySkills");
            }
        }
        String string4 = "";
        String string5 = "";
        String string6 = "";
        String string7 = "";
        String string8 = "";
        String string9 = "";
        String string10 = "";
        String string11 = "";
        List<Integer> list = null;
        String string12 = null;
        switch (string2) {
            case "attack": {
                list = autoFarmContext.getAttackSpells();
                string12 = HtmCache.getInstance().getNotNull("command/autofarm/index-skill_attack.htm", player);
                string4 = AutoFarm.b(player, string != null && string.equals("editAttackSkills") ? string : null, "editAttackSkills", "attackChanceSkills", Config.ATTACK_SKILL_CHANCE, string2);
                string5 = AutoFarm.b(player, string != null && string.equals("editAttackPercent") ? string : null, "editAttackPercent", "attackSkillsPercent", Config.ATTACK_SKILL_PERCENT, string2);
                bl2 = autoFarmContext.isRndAttackSkills();
                string12 = bl2 ? string12.replace("%rndAttackSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string12.replace("%rndAttackSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string12 = string12.replace("%rndAttackSk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmRndAttackSkills");
                break;
            }
            case "chance": {
                list = autoFarmContext.getChanceSpells();
                string12 = HtmCache.getInstance().getNotNull("command/autofarm/index-skill_chance.htm", player);
                string6 = AutoFarm.b(player, string != null && string.equals("editChanceSkills") ? string : null, "editChanceSkills", "chanceChanceSkills", Config.CHANCE_SKILL_CHANCE, string2);
                string7 = AutoFarm.b(player, string != null && string.equals("editChancePercent") ? string : null, "editChancePercent", "chanceSkillsPercent", Config.CHANCE_SKILL_PERCENT, string2);
                bl = autoFarmContext.isRndChanceSkills();
                string12 = bl ? string12.replace("%rndChanceSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string12.replace("%rndChanceSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string12 = string12.replace("%rndChanceSk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmRndChanceSkills");
                break;
            }
            case "self": {
                list = autoFarmContext.getSelfSpells();
                string12 = HtmCache.getInstance().getNotNull("command/autofarm/index-skill_self.htm", player);
                string8 = AutoFarm.b(player, string != null && string.equals("editSelfSkills") ? string : null, "editSelfSkills", "selfChanceSkills", Config.SELF_SKILL_CHANCE, string2);
                string9 = AutoFarm.b(player, string != null && string.equals("editSelfPercent") ? string : null, "editSelfPercent", "selfSkillsPercent", Config.SELF_SKILL_PERCENT, string2);
                n3 = autoFarmContext.isRndSelfSkills() ? 1 : 0;
                string12 = n3 != 0 ? string12.replace("%rndSelfSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string12.replace("%rndSelfSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string12 = string12.replace("%rndSelfSk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmRndSelfSkills");
                break;
            }
            case "heal": {
                list = autoFarmContext.getLowLifeSpells();
                string12 = HtmCache.getInstance().getNotNull("command/autofarm/index-skill_heal.htm", player);
                string10 = AutoFarm.b(player, string != null && string.equals("editHealSkills") ? string : null, "editHealSkills", "healChanceSkills", Config.HEAL_SKILL_CHANCE, string2);
                string11 = AutoFarm.b(player, string != null && string.equals("editLowHealPercent") ? string : null, "editLowHealPercent", "healSkillsPercent", Config.HEAL_SKILL_PERCENT, string2);
                n2 = autoFarmContext.isRndLifeSkills();
                string12 = n2 != 0 ? string12.replace("%rndLifeSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string12.replace("%rndLifeSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string12 = string12.replace("%rndLifeSk_bypass%", "bypass -h user_editFarmOption " + string2 + " farmRndLifeSkills");
            }
        }
        Object object = "";
        Object object2 = "";
        if (list != null) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            Object object3 = list.iterator();
            while (object3.hasNext()) {
                Skill skill;
                n3 = object3.next();
                if (n3 <= 0 || (skill = player.getKnownSkill(n3)) != null) continue;
                arrayList.add(n3);
            }
            if (!arrayList.isEmpty()) {
                object3 = arrayList.iterator();
                while (object3.hasNext()) {
                    n3 = (Integer)object3.next();
                    list.remove((Object)n3);
                }
                arrayList.clear();
            }
            object3 = HtmCache.getInstance().getNotNull("command/autofarm/skill-template.htm", player);
            for (n3 = 0; n3 < 8; ++n3) {
                object = object3;
                if (list.size() > 0 && n3 < list.size()) {
                    n2 = list.get(n3);
                    if (n2 > 0) {
                        Skill skill = player.getKnownSkill(n2);
                        if (skill != null) {
                            object = ((String)object).replace("%icon%", skill.getIcon());
                            object = ((String)object).replace("%background%", "");
                            object = ((String)object).replace("%bypass%", "bypass -h user_removeSkill " + string2 + " " + skill.getId());
                        } else {
                            object = ((String)object).replace("%icon%", StringHolder.getInstance().getNotNull(player, "services.autofarm.empty.icon"));
                            object = ((String)object).replace("%bypass%", "bypass -h user_addSkill " + string2);
                        }
                    } else {
                        object = ((String)object).replace("%icon%", StringHolder.getInstance().getNotNull(player, "services.autofarm.empty.icon"));
                        object = ((String)object).replace("%bypass%", "bypass -h user_addSkill " + string2);
                    }
                } else {
                    object = ((String)object).replace("%icon%", StringHolder.getInstance().getNotNull(player, "services.autofarm.empty.icon"));
                    object = ((String)object).replace("%bypass%", "bypass -h user_addSkill " + string2);
                }
                object2 = (String)object2 + (String)object;
            }
        }
        String string13 = AutoFarm.a(player, string != null && string.equals("editDistance") ? string : null, "editDistance", "farmDistance", Config.SEARCH_DISTANCE, string2);
        String string14 = AutoFarm.b(player, string != null && string.equals("editShortcutPage") ? string : null, "editShortcutPage", "shortcutPage", Config.SHORTCUT_PAGE, string2);
        string3 = string3.replace("%activate%", this.a(player, autoFarmContext));
        string3 = string3.replace("%skillType%", string2);
        string3 = string3.replace("%skillList%", (CharSequence)object2);
        string3 = string3.replace("%skillsParam%", string12);
        string3 = string3.replace("%activeHwids%", this.getActivateHwids(player));
        string3 = string3.replace("%refreshSkills%", "bypass -h user_refreshSkills " + string2);
        string3 = string3.replace("%status%", autoFarmContext.isAutofarming() ? new CustomMessage("services.autofarm.on", player, new Object[0]).toString() : new CustomMessage("services.autofarm.off", player, new Object[0]).toString());
        string3 = string3.replace("%button%", autoFarmContext.isAutofarming() ? new CustomMessage("services.autofarm.on_button", player, new Object[0]).toString() : new CustomMessage("services.autofarm.off_button", player, new Object[0]).toString());
        string3 = string3.replace("%chanceAttack%", string4.equals("") ? "" : string4);
        string3 = string3.replace("%chanceChance%", string6.equals("") ? "" : string6);
        string3 = string3.replace("%chanceSelf%", string8.equals("") ? "" : string8);
        string3 = string3.replace("%chanceLowHeal%", string10.equals("") ? "" : string10);
        string3 = string3.replace("%percentAttack%", string5.equals("") ? "" : string5);
        string3 = string3.replace("%percentChance%", string7.equals("") ? "" : string7);
        string3 = string3.replace("%percentSelf%", string9.equals("") ? "" : string9);
        string3 = string3.replace("%percentLowHeal%", string11.equals("") ? "" : string11);
        string3 = string3.replace("%distance%", string13.equals("") ? "" : string13);
        string3 = string3.replace("%shortcutPage%", string14.equals("") ? "" : string14);
        string3 = string3.replace("%farmType%", this.a(player, n, string2));
        Functions.show(string3, player, null, new Object[0]);
    }

    public String getActivateHwids(Player player) {
        if (Config.FARM_ACTIVE_LIMITS < 0) {
            return "<font color=\"LEVEL\">-<font>";
        }
        int n = AutoFarmManager.getInstance().getActiveFarms(Config.ALLOW_CHECK_HWID_LIMIT ? player.getNetConnection().getHwid() : player.getIP());
        if (n > 0) {
            return "<font color=\"LEVEL\">" + n + "<font>";
        }
        if (n <= 0 && AutoFarmManager.getInstance().isNonCheckPlayer(player.getObjectId())) {
            return "<font color=\"00FF00\">" + n + "<font>";
        }
        return "<a action=\"bypass -h user_expendLimit\"><font color=\"FF0000\">" + n + "<font></a>";
    }

    private String a(Player player, AutoFarmContext autoFarmContext) {
        String string = StringHolder.getInstance().getNotNull(player, "AUTO_FARM_NO_LIMIT_TIME");
        String string2 = StringHolder.getInstance().getNotNull(player, "AUTO_FARM_BUY_TIME");
        String string3 = StringHolder.getInstance().getNotNull(player, "AUTO_FARM_ADD_TIME");
        if (Config.AUTO_FARM_UNLIMITED || Config.AUTO_FARM_PA_UNLIMITED && player.hasBonus()) {
            return string;
        }
        if (autoFarmContext.isActiveAutofarmAllowed()) {
            if (Config.AUTOFARM_TIME_TRACK_USAGE_ONLY) {
                return "<font color=\"E6D0AE\">" + TimeUtils.formatTime((int)TimeUnit.MILLISECONDS.toSeconds(autoFarmContext.getActiveTimeRemaining()), false, player) + " </font>" + string3;
            }
            long l = (autoFarmContext.getAutoFarmEnd() - System.currentTimeMillis()) / 1000L;
            return "<font color=\"E6D0AE\">" + TimeUtils.formatTime((int)l, false, player) + " </font>" + string3;
        }
        return string2;
    }

    private void a(Player player, AutoFarmContext autoFarmContext) {
        int n;
        String string = HtmCache.getInstance().getNotNull("command/autofarm/buy.htm", player);
        Object object = "";
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Object object2 = Config.AUTO_FARM_PRICES.keySet().iterator();
        while (object2.hasNext()) {
            n = object2.next();
            arrayList.add(n);
        }
        object2 = new SortTimeInfo();
        Collections.sort(arrayList, object2);
        n = 0;
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            int n2 = (Integer)iterator.next();
            if (n > 0) {
                object = (String)object + ";";
            }
            object = (String)object + n2;
            ++n;
        }
        string = string.replace("%time%", (CharSequence)object);
        string = string.replace("%freeUse%", this.b(player, autoFarmContext));
        arrayList.clear();
        Functions.show(string, player, null, new Object[0]);
    }

    private String b(Player player, AutoFarmContext autoFarmContext) {
        if (autoFarmContext.isActiveAutofarmAllowed() || !Config.ALLOW_FARM_FREE_TIME) {
            return "";
        }
        long l = player.getVarLong("farmFreeTime", 0L);
        if (l <= 0L) {
            return new CustomMessage("services.autofarm.try_free", player, Config.FARM_FREE_TIME).toString();
        }
        return "";
    }

    private String a(Player player, int n, String string) {
        Object object = "<td aling=center width=20>";
        int n2 = 0;
        String string2 = "";
        switch (n) {
            case 0: {
                object = (String)object + StringHolder.getInstance().getNotNull(player, "services.autofarm.fightericon");
                string2 = "Fighter";
                ++n2;
                break;
            }
            case 1: {
                object = (String)object + StringHolder.getInstance().getNotNull(player, "services.autofarm.archericon");
                string2 = "Archer";
                n2 = 2;
                break;
            }
            case 2: {
                object = (String)object + StringHolder.getInstance().getNotNull(player, "services.autofarm.magicicon");
                string2 = "Magic";
                n2 = 3;
                break;
            }
            case 3: {
                object = (String)object + StringHolder.getInstance().getNotNull(player, "services.autofarm.healicon");
                string2 = "Healer";
                n2 = 4;
                break;
            }
            case 4: {
                object = (String)object + StringHolder.getInstance().getNotNull(player, "services.autofarm.summonicon");
                string2 = "Summon";
            }
        }
        object = (String)object + "<td width=90>" + string2 + "</td><td width=60><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm edit_farmType " + n2 + " " + string + "\" value=\"Switch\"></td>";
        return object;
    }

    private static String a(Player player, String string, String string2, String string3, int n, String string4) {
        Object object = "";
        if (string != null && !string.isEmpty()) {
            if (string.equals("editDistance")) {
                object = (String)object + "<td width=50><edit var=\"" + string + "\" width=40 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_distance $editDistance " + string4 + "\" value=\"Save\"></td>";
            }
        } else {
            object = (String)object + "<td aling=center width=50><font color=c1b33a>" + player.getVarInt(string3, n) + "</font></td>";
            object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm edit_farm " + string2 + " " + string4 + "\" value=\"Edit\"></td>";
        }
        return object;
    }

    private static String b(Player player, String string, String string2, String string3, int n, String string4) {
        Object object = "";
        if (string != null && !string.isEmpty()) {
            if (string.equals("editAttackSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_attackSkills $editAttackSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editChanceSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_chanceSkills $editChanceSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSelfSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_selfSkills $editSelfSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editHealSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_healSkills $editHealSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editAttackPercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_attackSkillsPercent $editAttackPercent " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editChancePercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_chanceSkillsPercent $editChancePercent " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSelfPercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_selfSkillsPercent $editSelfPercent " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editLowHealPercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_healSkillsPercent $editLowHealPercent " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editShortcutPage")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm set_shortcutPage $editShortcutPage " + string4 + "\" value=\"Save\"></td>";
            }
        } else {
            object = string3.equals("shortcutPage") ? (String)object + "<td aling=center width=45><font color=c1b33a>" + player.getVarInt(string3, n) + "</font></td>" : (String)object + "<td aling=center width=45><font color=c1b33a>" + player.getVarInt(string3, n) + "%</font></td>";
            object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autofarm edit_farm " + string2 + " " + string4 + "\" value=\"Edit\"></td>";
        }
        return object;
    }

    private static String c(Player player, String string, String string2, String string3, int n, String string4) {
        Object object = "";
        if (string != null && !string.isEmpty()) {
            if (string.equals("editSummonAttackSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm set_attackSummonSkills $editSummonAttackSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSummonSelfSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm set_selfSummonSkills $editSummonSelfSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSummonHealSkills")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm set_healSummonSkills $editSummonHealSkills " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSummonAttackPercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm set_attackSummonSkillsPercent $editSummonAttackPercent " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSummonSelfPercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm set_selfSummonSkillsPercent $editSummonSelfPercent " + string4 + "\" value=\"Save\"></td>";
            } else if (string.equals("editSummonLowHealPercent")) {
                object = (String)object + "<td width=43><edit var=\"" + string + "\" width=34 height=12></td>";
                object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm set_healSummonSkillsPercent $editSummonLowHealPercent " + string4 + "\" value=\"Save\"></td>";
            }
        } else {
            object = (String)object + "<td aling=center width=45><font color=c1b33a>" + player.getVarInt(string3, n) + "%</font></td>";
            object = (String)object + "<td width=40><button width=40 height=20 back=\"L2UI_CT1.ListCTRL_DF_Title_Down\" fore=\"L2UI_CT1.ListCTRL_DF_Title\" action=\"bypass -h user_autosummonfarm edit_farm " + string2 + " " + string4 + "\" value=\"Edit\"></td>";
        }
        return object;
    }

    /*
     * WARNING - void declaration
     */
    private void a(Player player, AutoFarmContext autoFarmContext, String string, int n) {
        void var10_18;
        Object object42;
        Object object2;
        List<Integer> list = null;
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        switch (string) {
            case "attack": {
                list = autoFarmContext.getAttackSpells();
                for (Skill object32 : player.getAllSkills()) {
                    if (object32 == null || object32.isPassive() || object32.isSpoilSkill() || object32.isSweepSkill() || object32.getId() == 1263 || object32.getSkillType() != Skill.SkillType.AGGRESSION && object32.getSkillType() != Skill.SkillType.PDAM && object32.getSkillType() != Skill.SkillType.MANADAM && object32.getSkillType() != Skill.SkillType.MDAM && object32.getSkillType() != Skill.SkillType.DRAIN && object32.getSkillType() != Skill.SkillType.CPDAM && object32.getSkillType() != Skill.SkillType.STUN) continue;
                    arrayList.add(object32);
                }
                break;
            }
            case "chance": {
                list = autoFarmContext.getChanceSpells();
                for (Skill skill : player.getAllSkills()) {
                    if (skill == null || skill.isPassive() || skill.getSkillType() != Skill.SkillType.DOT && skill.getSkillType() != Skill.SkillType.MDOT && skill.getSkillType() != Skill.SkillType.POISON && skill.getSkillType() != Skill.SkillType.BLEED && skill.getSkillType() != Skill.SkillType.DEBUFF && skill.getSkillType() != Skill.SkillType.SLEEP && skill.getSkillType() != Skill.SkillType.ROOT && skill.getSkillType() != Skill.SkillType.PARALYZE && skill.getSkillType() != Skill.SkillType.MUTE && !skill.isSpoilSkill() && !skill.isSweepSkill() && skill.getId() != 1263) continue;
                    arrayList.add(skill);
                }
                break;
            }
            case "self": {
                list = autoFarmContext.getSelfSpells();
                for (Skill skill : player.getAllSkills()) {
                    if ((skill == null || skill.isPassive() || !skill.isToggle() && !skill.isMusic() && skill.getSkillType() != Skill.SkillType.BUFF) && !skill.isCubicSkill()) continue;
                    arrayList.add(skill);
                }
                break;
            }
            case "heal": {
                list = autoFarmContext.getLowLifeSpells();
                for (Skill skill : player.getAllSkills()) {
                    if (skill == null || skill.isPassive() || skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.HEAL && skill.getSkillType() != Skill.SkillType.HEAL_PERCENT && skill.getSkillType() != Skill.SkillType.MANAHEAL && skill.getSkillType() != Skill.SkillType.MANAHEAL_PERCENT) continue;
                    arrayList.add(skill);
                }
                break;
            }
        }
        if (arrayList.isEmpty()) {
            player.sendMessage("You have no valid skills!");
            this.a(player, autoFarmContext, null, player.getVarInt("farmType", Config.FARM_TYPE), string);
            return;
        }
        if (list.size() > 0) {
            object2 = new ArrayList();
            for (Object object42 : arrayList) {
                if (!list.contains(((Skill)object42).getId())) continue;
                object2.add(object42);
            }
            if (!object2.isEmpty()) {
                Iterator iterator = object2.iterator();
                while (iterator.hasNext()) {
                    object42 = (Skill)iterator.next();
                    arrayList.remove(object42);
                }
                object2.clear();
            }
        }
        object2 = HtmCache.getInstance().getNotNull("command/autofarm/player_skills.htm", player);
        String string2 = HtmCache.getInstance().getNotNull("command/autofarm/player_skills_template.htm", player);
        object42 = "";
        String string3 = "";
        int n2 = 0;
        int n3 = arrayList.size();
        boolean bl = n3 > 5;
        for (int i = (n - 1) * 5; i < n3; ++i) {
            Skill skill = (Skill)arrayList.get(i);
            if (skill != null) {
                object42 = string2;
                object42 = ((String)object42).replace("%name%", skill.getName());
                object42 = ((String)object42).replace("%icon%", skill.getIcon());
                object42 = ((String)object42).replace("%bypass%", "bypass -h user_addNewSkill " + skill.getId() + " " + string);
                String string4 = (String)var10_18 + (String)object42;
            }
            if (++n2 >= 5) break;
        }
        double d = (double)n3 / 5.0;
        int n4 = (int)Math.ceil(d);
        object2 = ((String)object2).replace("%list%", (CharSequence)var10_18);
        object2 = ((String)object2).replace("%page%", String.valueOf(n));
        object2 = ((String)object2).replace("%skillType%", string);
        object2 = ((String)object2).replace("%navigation%", Util.getNavigationBlock(n4, n, n3, 5, bl, "user_addSkill " + string + " %s"));
        Functions.show((String)object2, player, null, new Object[0]);
        arrayList.clear();
    }

    private void a(Player player, AutoFarmContext autoFarmContext, String string, int n, String string2, String string3) {
        int n2;
        int n32;
        int n4;
        if (player.getPet() == null) {
            player.sendMessage("You can't use this option!");
            this.a(player, autoFarmContext, null, n, string2, string3);
            return;
        }
        Summon summon = player.getPet();
        if (summon.isPet() && summon.getLevel() - player.getLevel() > 20) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_IS_TOO_HIGH_LEVEL_TO_CONTROL);
            this.a(player, autoFarmContext, null, n, string2, string3);
            return;
        }
        String string4 = HtmCache.getInstance().getNotNull("command/autofarm/index-summonSkills.htm", player);
        List<Integer> list = null;
        String string5 = null;
        String string6 = "";
        String string7 = "";
        String string8 = "";
        String string9 = "";
        String string10 = "";
        String string11 = "";
        switch (string2) {
            case "attack": {
                list = autoFarmContext.getSummonAttackSpells();
                string5 = HtmCache.getInstance().getNotNull("command/autofarm/index-summon_skill_attack.htm", player);
                string6 = AutoFarm.c(player, string != null && string.equals("editSummonAttackSkills") ? string : null, "editSummonAttackSkills", "attackSummonChanceSkills", Config.SUMMON_ATTACK_SKILL_CHANCE, string2);
                string7 = AutoFarm.c(player, string != null && string.equals("editSummonAttackPercent") ? string : null, "editSummonAttackPercent", "attackSummonSkillsPercent", Config.SUMMON_ATTACK_SKILL_PERCENT, string2);
                boolean bl = autoFarmContext.isRndSummonAttackSkills();
                string5 = bl ? string5.replace("%rndAttackSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string5.replace("%rndAttackSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string5 = string5.replace("%rndAttackSk_bypass%", "bypass -h user_editSummonFarmOption " + string2 + " farmRndSummonAttackSkills " + string3);
                break;
            }
            case "self": {
                list = autoFarmContext.getSummonSelfSpells();
                string5 = HtmCache.getInstance().getNotNull("command/autofarm/index-summon_skill_self.htm", player);
                string8 = AutoFarm.c(player, string != null && string.equals("editSummonSelfSkills") ? string : null, "editSummonSelfSkills", "selfSummonChanceSkills", Config.SUMMON_SELF_SKILL_CHANCE, string2);
                string9 = AutoFarm.c(player, string != null && string.equals("editSummonSelfPercent") ? string : null, "editSummonSelfPercent", "selfSummonSkillsPercent", Config.SUMMON_SELF_SKILL_PERCENT, string2);
                n4 = autoFarmContext.isRndSummonSelfSkills() ? 1 : 0;
                string5 = n4 != 0 ? string5.replace("%rndSelfSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string5.replace("%rndSelfSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string5 = string5.replace("%rndSelfSk_bypass%", "bypass -h user_editSummonFarmOption " + string2 + " farmRndSummonSelfSkills " + string3);
                break;
            }
            case "heal": {
                list = autoFarmContext.getSummonHealSpells();
                string5 = HtmCache.getInstance().getNotNull("command/autofarm/index-summon_skill_heal.htm", player);
                string10 = AutoFarm.c(player, string != null && string.equals("editSummonHealSkills") ? string : null, "editSummonHealSkills", "healSummonChanceSkills", Config.SUMMON_HEAL_SKILL_CHANCE, string2);
                string11 = AutoFarm.c(player, string != null && string.equals("editSummonLowHealPercent") ? string : null, "editSummonLowHealPercent", "healSummonSkillsPercent", Config.SUMMON_HEAL_SKILL_PERCENT, string2);
                n32 = autoFarmContext.isRndSummonLifeSkills() ? 1 : 0;
                string5 = n32 != 0 ? string5.replace("%rndLifeSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string5.replace("%rndLifeSk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
                string5 = string5.replace("%rndLifeSk_bypass%", "bypass -h user_editSummonFarmOption " + string2 + " farmRndSummonLifeSkills " + string3);
            }
        }
        String string12 = "";
        Object object = "";
        if (list != null && !list.isEmpty()) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int n32 : list) {
                if (n32 <= 0 || (n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n32)) != 0) continue;
                arrayList.add(n32);
            }
            if (!arrayList.isEmpty()) {
                for (int n32 : arrayList) {
                    list.remove((Object)n32);
                }
                arrayList.clear();
            }
        }
        String string13 = HtmCache.getInstance().getNotNull("command/autofarm/summon_skill-template.htm", player);
        for (n4 = 0; n4 < 8; ++n4) {
            string12 = string13;
            if (list.size() > 0 && n4 < list.size()) {
                n32 = list.get(n4);
                if (n32 > 0) {
                    n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n32);
                    if (n2 > 0) {
                        string12 = string12.replace("%icon%", SkillTable.getInstance().getInfo(n32, n2).getIcon());
                        string12 = string12.replace("%background%", "");
                        string12 = string12.replace("%bypass%", "bypass -h user_removeSummonSkill " + string2 + " " + n32 + " " + string3);
                    } else {
                        string12 = string12.replace("%icon%", "icon.high_tab");
                        string12 = string12.replace("%background%", "background=\"l2ui_ch3.multisell_plusicon\"");
                        string12 = string12.replace("%bypass%", "bypass -h user_addSummonSkill " + string2 + " " + string3);
                    }
                } else {
                    string12 = string12.replace("%icon%", "icon.high_tab");
                    string12 = string12.replace("%background%", "background=\"l2ui_ch3.multisell_plusicon\"");
                    string12 = string12.replace("%bypass%", "bypass -h user_addSummonSkill " + string2 + " " + string3);
                }
            } else {
                string12 = string12.replace("%icon%", "icon.high_tab");
                string12 = string12.replace("%background%", "background=\"l2ui_ch3.multisell_plusicon\"");
                string12 = string12.replace("%bypass%", "bypass -h user_addSummonSkill " + string2 + " " + string3);
            }
            object = (String)object + string12;
        }
        n4 = autoFarmContext.isExtraSummonDelaySkill() ? 1 : 0;
        string4 = n4 != 0 ? string4.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox.checked")) : string4.replace("%delaySk_img%", StringHolder.getInstance().getNotNull(player, "services.autofarm.checkbox"));
        string4 = string4.replace("%delaySk_bypass%", "bypass -h user_editSummonFarmOption " + string2 + " farmSummonDelaySkills " + string3);
        string4 = string4.replace("%skillType%", string3);
        string4 = string4.replace("%skillsParam%", string5);
        string4 = string4.replace("%summonSkillType%", string2);
        string4 = string4.replace("%skillList%", (CharSequence)object);
        string4 = string4.replace("%chanceAttack%", string6.equals("") ? "" : string6);
        string4 = string4.replace("%chanceSelf%", string8.equals("") ? "" : string8);
        string4 = string4.replace("%chanceLowHeal%", string10.equals("") ? "" : string10);
        string4 = string4.replace("%percentAttack%", string7.equals("") ? "" : string7);
        string4 = string4.replace("%percentSelf%", string9.equals("") ? "" : string9);
        string4 = string4.replace("%percentLowHeal%", string11.equals("") ? "" : string11);
        Functions.show(string4, player, null, new Object[0]);
    }

    private void a(Player player, AutoFarmContext autoFarmContext, int n, String string, String string2, int n2) {
        Object object;
        int n3;
        if (player.getPet() == null) {
            player.sendMessage("You can't use this option!");
            this.a(player, autoFarmContext, null, n, string, string2);
            return;
        }
        Summon summon = player.getPet();
        if (summon.isPet() && summon.getLevel() - player.getLevel() > 20) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_IS_TOO_HIGH_LEVEL_TO_CONTROL);
            this.a(player, autoFarmContext, null, n, string, string2);
            return;
        }
        List<Integer> list = null;
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        switch (string) {
            case "attack": {
                Skill skill;
                list = autoFarmContext.getSummonAttackSpells();
                for (int n4 : PetDataHolder.getInstance().getInfo(summon.getNpcId(), summon.getLevel()).getAvailableSkills(summon)) {
                    n3 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n4);
                    if (n3 <= 0 || (skill = SkillTable.getInstance().getInfo(n4, n3)) == null || skill.getSkillType() != Skill.SkillType.AGGRESSION && skill.getSkillType() != Skill.SkillType.PDAM && skill.getSkillType() != Skill.SkillType.MANADAM && skill.getSkillType() != Skill.SkillType.MDAM && skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.CPDAM && skill.getSkillType() != Skill.SkillType.STUN) continue;
                    arrayList.add(skill);
                }
                break;
            }
            case "self": {
                Skill skill;
                list = autoFarmContext.getSummonSelfSpells();
                for (int n4 : PetDataHolder.getInstance().getInfo(summon.getNpcId(), summon.getLevel()).getAvailableSkills(summon)) {
                    n3 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n4);
                    if (n3 <= 0 || ((skill = SkillTable.getInstance().getInfo(n4, n3)) == null || !skill.isToggle() && !skill.isMusic() && skill.getSkillType() != Skill.SkillType.BUFF) && !skill.isCubicSkill()) continue;
                    arrayList.add(skill);
                }
                break;
            }
            case "heal": {
                Skill skill;
                list = autoFarmContext.getSummonHealSpells();
                for (int n4 : PetDataHolder.getInstance().getInfo(summon.getNpcId(), summon.getLevel()).getAvailableSkills(summon)) {
                    n3 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n4);
                    if (n3 <= 0 || (skill = SkillTable.getInstance().getInfo(n4, n3)) == null || skill.getSkillType() != Skill.SkillType.DRAIN && skill.getSkillType() != Skill.SkillType.HEAL && skill.getSkillType() != Skill.SkillType.HEAL_PERCENT && skill.getSkillType() != Skill.SkillType.MANAHEAL && skill.getSkillType() != Skill.SkillType.MANAHEAL_PERCENT) continue;
                    arrayList.add(skill);
                }
                break;
            }
        }
        if (list.size() > 0) {
            object = new ArrayList();
            for (Skill skill : arrayList) {
                if (!list.contains(skill.getId())) continue;
                object.add(skill);
            }
            if (!object.isEmpty()) {
                Iterator iterator = object.iterator();
                while (iterator.hasNext()) {
                    Skill skill = (Skill)iterator.next();
                    arrayList.remove(skill);
                }
                object.clear();
            }
        }
        if (arrayList.isEmpty()) {
            player.sendMessage("Your summon has no valid skills!");
            this.a(player, autoFarmContext, null, n, string, string2);
            return;
        }
        object = HtmCache.getInstance().getNotNull("command/autofarm/summon_skills.htm", player);
        String string3 = HtmCache.getInstance().getNotNull("command/autofarm/summon_skills_template.htm", player);
        String string4 = "";
        Object object2 = "";
        n3 = 0;
        int n5 = arrayList.size();
        boolean bl = n5 > 5;
        for (int i = (n2 - 1) * 5; i < n5; ++i) {
            Skill skill = (Skill)arrayList.get(i);
            if (skill != null) {
                String string8 = string3;
                string8 = string8.replace("%name%", skill.getName());
                string8 = string8.replace("%icon%", skill.getIcon());
                string8 = string8.replace("%bypass%", "bypass -h user_addNewSummonSkill " + skill.getId() + " " + string + " " + string2);
                object2 = (String)object2 + string8;
            }
            if (++n3 >= 5) break;
        }
        double d = (double)n5 / 5.0;
        int n6 = (int)Math.ceil(d);
        object = ((String)object).replace("%list%", (CharSequence)object2);
        object = ((String)object).replace("%page%", String.valueOf(n2));
        object = ((String)object).replace("%summonSkillType%", string);
        object = ((String)object).replace("%skillType%", string2);
        object = ((String)object).replace("%navigation%", Util.getNavigationBlock(n6, n2, n5, 5, bl, "user_addSummonSkill " + string + " " + string2 + " %s"));
        Functions.show((String)object, player, null, new Object[0]);
        arrayList.clear();
    }

    @Override
    public String[] getVoicedCommandList() {
        return az;
    }

    private static class SortTimeInfo
    implements Serializable,
    Comparator<Integer> {
        private static final long aO = 7691414259610932752L;

        private SortTimeInfo() {
        }

        @Override
        public int compare(Integer n, Integer n2) {
            return Double.compare(n.intValue(), n2.intValue());
        }
    }
}
