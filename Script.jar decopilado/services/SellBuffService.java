/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.holder.SellBuffHolder
 *  l2.gameserver.data.xml.holder.SellBuffHolder$BuffSellInfo
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.instancemanager.SellBuffsManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Util
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import java.util.StringTokenizer;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.SellBuffHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.instancemanager.SellBuffsManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.StringUtils;

public class SellBuffService
implements IVoicedCommandHandler,
ScriptFile {
    private static final String[] bc = new String[]{"sellbuff", "sellbuffs", "sellbuffadd", "sellbuffaddskill", "sellbuffedit", "sellbuffchangeprice", "sellbuffremove", "sellbuffbuymenu", "sellbuffbuyskill", "sellbuffstart", "sellbuffstop"};

    public SellBuffService() {
        if (Config.SELLBUFF_ENABLED) {
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
        }
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        switch (string) {
            case "sellbuff": 
            case "sellbuffs": {
                SellBuffsManager.getInstance().sendSellMenu(player);
                break;
            }
            case "sellbuffstart": {
                if (player.isSellingBuffs() || string2 == null || string2.isEmpty() || !SellBuffsManager.getInstance().canStartSellBuffs(player)) {
                    player.sendActionFailed();
                    return false;
                }
                if (player.getBuffs4Sale().isEmpty()) {
                    player.sendMessage(new CustomMessage("sellbuff.emptylist", player, new Object[0]));
                    return false;
                }
                String string3 = String.format("%s %s", Config.SELLBUFF_PREFIX, string2);
                if (string3.length() > 40) {
                    player.sendMessage(new CustomMessage("sellbuff.titleIncorrectSize", player, new Object[0]));
                    return false;
                }
                SellBuffsManager.getInstance().startSellBuffs(player, string3);
                break;
            }
            case "sellbuffstop": {
                if (!player.isSellingBuffs()) break;
                SellBuffsManager.getInstance().stopSellBuffs(player);
                break;
            }
            case "sellbuffadd": {
                if (player.isSellingBuffs()) break;
                int n = 0;
                if (!(string2 = StringUtils.trimToEmpty((String)string2)).isEmpty() && Util.isDigit((String)string2)) {
                    n = Integer.parseInt(string2);
                }
                SellBuffsManager.getInstance().buildSkillMenu(player, n);
                break;
            }
            case "sellbuffedit": {
                if (player.isSellingBuffs()) break;
                SellBuffsManager.getInstance().buildEditMenu(player);
                break;
            }
            case "sellbuffchangeprice": {
                Long l;
                if (player.isSellingBuffs() || string2 == null || string2.isEmpty()) break;
                String[] stringArray = StringUtils.split((String)string2, (String)" ");
                if (stringArray.length != 2) {
                    return false;
                }
                Skill skill = player.getKnownSkill(Integer.parseInt(stringArray[0]));
                SellBuffHolder.BuffSellInfo buffSellInfo = SellBuffHolder.getInstance().getBuffInfo(skill.getId());
                Long l2 = (Long)player.getBuffs4Sale().get(skill);
                if (buffSellInfo == null || skill == null || l2 == null) {
                    return false;
                }
                try {
                    l = Long.parseLong(stringArray[1]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Incorrect price value");
                    SellBuffsManager.getInstance().buildEditMenu(player);
                    return false;
                }
                player.getBuffs4Sale().put(skill, l);
                player.sendMessage(new CustomMessage("sellbuff.PriceOfSkillsChanged", player, new Object[0]).addSkillName(skill).addNumber(l.longValue()));
                SellBuffsManager.getInstance().buildEditMenu(player);
                break;
            }
            case "sellbuffremove": {
                if (player.isSellingBuffs() || string2 == null || string2.isEmpty()) break;
                int n = Integer.parseInt(StringUtils.trimToEmpty((String)string2));
                if (player.getBuffs4Sale().remove(player.getKnownSkill(n)) == null) break;
                player.sendMessage(new CustomMessage("sellbuff.SkillRemoved", player, new Object[0]).addSkillName(player.getKnownSkill(n)));
                SellBuffsManager.getInstance().buildEditMenu(player);
                break;
            }
            case "sellbuffaddskill": {
                long l;
                if (player.isSellingBuffs() || string2 == null || string2.isEmpty()) break;
                String[] stringArray = StringUtils.split((String)string2, (String)" ");
                if (stringArray.length != 2) {
                    SellBuffsManager.getInstance().buildSkillMenu(player, 0);
                    return false;
                }
                Skill skill = player.getKnownSkill(Integer.parseInt(stringArray[0]));
                if (skill == null) {
                    return false;
                }
                SellBuffHolder.BuffSellInfo buffSellInfo = SellBuffHolder.getInstance().getBuffInfo(skill.getId());
                try {
                    l = Long.parseLong(stringArray[1]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Incorrect price value");
                    SellBuffsManager.getInstance().buildEditMenu(player);
                    return false;
                }
                if (l < buffSellInfo.getSkillMinPrice()) {
                    player.sendMessage(new CustomMessage("sellbuff.toSmallPrice", player, new Object[0]).addNumber(buffSellInfo.getSkillMinPrice()));
                    return false;
                }
                if (l > buffSellInfo.getSkillMaxPrice()) {
                    player.sendMessage(new CustomMessage("sellbuff.toBigPrice", player, new Object[0]).addNumber(buffSellInfo.getSkillMaxPrice()));
                    return false;
                }
                if (player.getBuffs4Sale().size() >= Config.SELLBUFF_MAX_BUFFS) {
                    player.sendMessage(new CustomMessage("sellbuff.buffMaxCountLimit", player, new Object[0]).addNumber((long)Config.SELLBUFF_MAX_BUFFS));
                    return false;
                }
                if (player.getBuffs4Sale().containsKey(skill)) break;
                player.getBuffs4Sale().put(skill, l);
                player.sendMessage(new CustomMessage("sellbuff.buffAdded", player, new Object[0]).addString(skill.getName()));
                SellBuffsManager.getInstance().buildSkillMenu(player, 0);
                break;
            }
            case "sellbuffbuymenu": {
                Player player2;
                if (string2 == null || string2.isEmpty()) break;
                StringTokenizer stringTokenizer = new StringTokenizer(string2, " ");
                int n = -1;
                int n2 = 0;
                if (stringTokenizer.hasMoreTokens()) {
                    n = Integer.parseInt(stringTokenizer.nextToken());
                }
                if (stringTokenizer.hasMoreTokens()) {
                    n2 = Integer.parseInt(stringTokenizer.nextToken());
                }
                if ((player2 = World.getPlayer((int)n)) == null) break;
                if (!player2.isSellingBuffs() || player.getDistance((GameObject)player2) > 128.0) {
                    return false;
                }
                SellBuffsManager.getInstance().sendBuffMenu(player, player2, n2);
                break;
            }
            case "sellbuffbuyskill": {
                if (string2 == null || string2.isEmpty()) break;
                String[] stringArray = StringUtils.split((String)string2, (String)" ");
                if (stringArray.length < 2) {
                    return false;
                }
                Player player3 = World.getPlayer((int)Integer.parseInt(stringArray[0]));
                if (player3 == null) {
                    return false;
                }
                SellBuffHolder.BuffSellInfo buffSellInfo = SellBuffHolder.getInstance().getBuffInfo(Integer.parseInt(stringArray[1]));
                if (buffSellInfo == null) {
                    return false;
                }
                int n = 0;
                if (stringArray.length > 2) {
                    n = Integer.parseInt(stringArray[2]);
                }
                if (!player3.isSellingBuffs() || player.getDistance((GameObject)player3) > 128.0) {
                    return false;
                }
                SellBuffService.a(player3, player, buffSellInfo);
                SellBuffsManager.getInstance().sendBuffMenu(player, player3, n);
            }
        }
        return true;
    }

    private static void a(Player player, Player player2, SellBuffHolder.BuffSellInfo buffSellInfo) {
        Skill skill = player.getKnownSkill(buffSellInfo.getSkillId());
        SellBuffHolder.BuffSellInfo buffSellInfo2 = SellBuffHolder.getInstance().getBuffInfo(skill.getId());
        double d = player2.hasBonus() ? buffSellInfo2.getEffectTimePremiumMultiplier() : buffSellInfo2.getEffectTimeMultiplier();
        long l = (long)buffSellInfo2.getEffectTime() * 1000L;
        long l2 = (long)((double)l * d);
        if (skill == null || !player.getBuffs4Sale().containsKey(skill)) {
            return;
        }
        long l3 = (Long)player.getBuffs4Sale().get(skill);
        if (player.getCurrentMp() < skill.getMpConsume() * buffSellInfo2.getSkillMpConsumeMultiplayer()) {
            player2.sendMessage(new CustomMessage("sellbuff.noEnoughMp", player2, new Object[0]).addString(player.getName()).addString(skill.getName()));
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player2, (int)buffSellInfo2.getItemId()) < l3) {
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(buffSellInfo2.getItemId());
            player2.sendMessage(itemTemplate != null ? new CustomMessage("sellbuff.noEnoughItems", player2, new Object[0]).addString(itemTemplate.getName()) : new CustomMessage("sellbuff.noItems", player2, new Object[0]));
            return;
        }
        ItemFunctions.removeItem((Playable)player2, (int)buffSellInfo2.getItemId(), (long)l3, (boolean)true);
        ItemFunctions.addItem((Playable)player, (int)buffSellInfo2.getItemId(), (long)l3, (!player.isInOfflineMode() ? 1 : 0) != 0);
        player.reduceCurrentMp(skill.getMpConsume() * buffSellInfo2.getSkillMpConsumeMultiplayer(), (Creature)player2);
        if (buffSellInfo2.isApplyOnPets() && player2.getPet() != null) {
            skill.getEffects((Creature)player, (Creature)player2.getPet(), false, false, l2, d, false);
        }
        skill.getEffects((Creature)player, (Creature)player2, false, false, l2, d, false);
    }

    public String[] getVoicedCommandList() {
        return bc;
    }

    public void onLoad() {
        new SellBuffService();
    }

    public void onReload() {
        new SellBuffService();
    }

    public void onShutdown() {
    }
}
