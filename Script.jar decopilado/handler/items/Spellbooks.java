/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  l2.gameserver.data.xml.holder.SkillAcquireHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.SkillLearn
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.tables.SkillTable
 */
package handler.items;

import gnu.trove.TIntHashSet;
import handler.items.ScriptItemHandler;
import java.util.List;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;

public class Spellbooks
extends ScriptItemHandler {
    private int[] H = null;

    public Spellbooks() {
        TIntHashSet tIntHashSet = new TIntHashSet();
        List list = SkillAcquireHolder.getInstance().getAllNormalSkillTreeWithForgottenScrolls();
        for (SkillLearn skillLearn : list) {
            tIntHashSet.add(skillLearn.getItemId());
        }
        this.H = tIntHashSet.toArray();
    }

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (!playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        if (itemInstance.getCount() < 1L) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return false;
        }
        List list = SkillAcquireHolder.getInstance().getSkillLearnListByItemId(player, itemInstance.getItemId());
        if (list.isEmpty()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(itemInstance.getItemId()));
            return false;
        }
        boolean bl2 = true;
        for (Object object : list) {
            if (player.getSkillLevel(Integer.valueOf(object.getId())) == object.getLevel()) continue;
            bl2 = false;
            break;
        }
        if (bl2) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(itemInstance.getItemId()));
            return false;
        }
        boolean bl3 = false;
        for (SkillLearn skillLearn : list) {
            if (player.getLevel() >= skillLearn.getMinLevel()) continue;
            bl3 = true;
        }
        if (bl3) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(itemInstance.getItemId()));
            return false;
        }
        if (!player.consumeItem(itemInstance.getItemId(), 1L)) {
            return false;
        }
        for (SkillLearn skillLearn : list) {
            Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
            if (skill == null) continue;
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_SKILL).addSkillName(skill.getId(), skill.getLevel()));
            player.addSkill(skill, true);
        }
        player.updateStats();
        player.sendSkillList();
        player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 2790, 1, 1, 0L)});
        return true;
    }

    public int[] getItemIds() {
        return this.H;
    }
}
