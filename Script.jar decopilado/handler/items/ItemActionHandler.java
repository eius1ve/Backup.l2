/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.Dice
 *  l2.gameserver.network.l2.s2c.ExChangeNicknameNColor
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SSQStatus
 *  l2.gameserver.network.l2.s2c.ShowCalc
 *  l2.gameserver.network.l2.s2c.ShowMiniMap
 *  l2.gameserver.network.l2.s2c.ShowXMasSeal
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.item.ActionType
 *  l2.gameserver.templates.item.ItemTemplate
 *  org.napile.primitive.sets.impl.CArrayIntSet
 */
package handler.items;

import handler.items.SimpleItemHandler;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.Dice;
import l2.gameserver.network.l2.s2c.ExChangeNicknameNColor;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SSQStatus;
import l2.gameserver.network.l2.s2c.ShowCalc;
import l2.gameserver.network.l2.s2c.ShowMiniMap;
import l2.gameserver.network.l2.s2c.ShowXMasSeal;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.ActionType;
import l2.gameserver.templates.item.ItemTemplate;
import org.napile.primitive.sets.impl.CArrayIntSet;

public class ItemActionHandler
extends SimpleItemHandler {
    public int[] getItemIds() {
        CArrayIntSet cArrayIntSet = new CArrayIntSet();
        for (ItemTemplate itemTemplate : ItemHolder.getInstance().getAllTemplates()) {
            if (itemTemplate == null) continue;
            ActionType actionType = itemTemplate.getDefaultAction();
            switch (actionType) {
                case ACTION_SHOW_HTML: 
                case ACTION_SHOW_SSQ_STATUS: 
                case ACTION_XMAS_OPEN: 
                case ACTION_CALC: 
                case ACTION_DICE: 
                case ACTION_HARVEST: 
                case ACTION_NICK_COLOR: 
                case ACTION_SHOW_MAP: {
                    cArrayIntSet.add(itemTemplate.getItemId());
                }
            }
        }
        return cArrayIntSet.toArray();
    }

    @Override
    protected boolean useItemImpl(Player player, ItemInstance itemInstance, boolean bl) {
        if (player == null || !player.isPlayer()) {
            return false;
        }
        ItemTemplate itemTemplate = itemInstance.getTemplate();
        if (itemTemplate == null) {
            return false;
        }
        ActionType actionType = itemTemplate.getDefaultAction();
        switch (actionType) {
            case ACTION_SHOW_HTML: 
            case ACTION_SHOW_ADVENTURER_GUIDE_BOOK: {
                return this.a(player, itemInstance);
            }
            case ACTION_SHOW_SSQ_STATUS: {
                return this.g(player);
            }
            case ACTION_XMAS_OPEN: {
                return this.b(player, itemInstance);
            }
            case ACTION_CALC: {
                return this.c(player, itemInstance);
            }
            case ACTION_DICE: {
                return this.d(player, itemInstance);
            }
            case ACTION_HARVEST: {
                return this.h(player);
            }
            case ACTION_NICK_COLOR: {
                return this.e(player, itemInstance);
            }
            case ACTION_SHOW_MAP: {
                return this.f(player, itemInstance);
            }
        }
        return false;
    }

    private boolean a(Player player, ItemInstance itemInstance) {
        Functions.show((String)("help/" + itemInstance.getItemId() + ".htm"), (Player)player, null, (Object[])new Object[0]);
        player.sendActionFailed();
        return true;
    }

    private boolean g(Player player) {
        player.sendPacket((IStaticPacket)new SSQStatus(player, 1));
        return true;
    }

    private boolean b(Player player, ItemInstance itemInstance) {
        player.sendPacket((IStaticPacket)new ShowXMasSeal(itemInstance.getItemId()));
        return true;
    }

    private boolean c(Player player, ItemInstance itemInstance) {
        player.sendPacket((IStaticPacket)new ShowCalc(itemInstance.getItemId()));
        return true;
    }

    private boolean d(Player player, ItemInstance itemInstance) {
        if (player.isOlyParticipant()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_THAT_ITEM_IN_A_GRAND_OLYMPIAD_MATCH);
            return false;
        }
        if (player.isSitting()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_MOVE_WHILE_SITTING);
            return false;
        }
        int n = Rnd.get((int)1, (int)6);
        if (n == 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_THROW_THE_DICE_AT_THIS_TIME_TRY_AGAIN_LATER);
            return false;
        }
        player.broadcastPacket(new L2GameServerPacket[]{new Dice(player.getObjectId(), itemInstance.getItemId(), n, player.getX() - 30, player.getY() - 30, player.getZ()), ((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_ROLLED_A_S2).addString(player.getName())).addNumber(n)});
        return true;
    }

    private boolean h(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isMonster()) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        MonsterInstance monsterInstance = (MonsterInstance)player.getTarget();
        if (!monsterInstance.isDead()) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        Skill skill = SkillTable.getInstance().getInfo(2098, 1);
        if (skill != null && skill.checkCondition((Creature)player, (Creature)monsterInstance, false, false, true)) {
            player.getAI().Cast(skill, (Creature)monsterInstance);
            return true;
        }
        return false;
    }

    private boolean e(Player player, ItemInstance itemInstance) {
        player.sendPacket((IStaticPacket)new ExChangeNicknameNColor(itemInstance.getItemId()));
        return true;
    }

    private boolean f(Player player, ItemInstance itemInstance) {
        player.sendPacket((IStaticPacket)new ShowMiniMap(player, itemInstance.getItemId()));
        return true;
    }
}
