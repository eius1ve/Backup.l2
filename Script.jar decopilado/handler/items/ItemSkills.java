/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Skill$SkillType
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.templates.item.ItemTemplate
 */
package handler.items;

import gnu.trove.TIntHashSet;
import handler.items.ScriptItemHandler;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.templates.item.ItemTemplate;

public class ItemSkills
extends ScriptItemHandler {
    private int[] H;

    public ItemSkills() {
        TIntHashSet tIntHashSet = new TIntHashSet();
        for (ItemTemplate itemTemplate : ItemHolder.getInstance().getAllTemplates()) {
            if (itemTemplate == null) continue;
            for (Skill skill : itemTemplate.getAttachedSkills()) {
                if (!skill.isHandler() || skill.getSkillType() == Skill.SkillType.SOULSHOT || skill.getSkillType() == Skill.SkillType.SPIRITSHOT) continue;
                tIntHashSet.add(itemTemplate.getItemId());
            }
        }
        this.H = tIntHashSet.toArray();
    }

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        Player player;
        if (playable.isPlayer()) {
            player = (Player)playable;
        } else if (playable.isPet()) {
            player = playable.getPlayer();
        } else {
            return false;
        }
        Skill[] skillArray = itemInstance.getTemplate().getAttachedSkills();
        for (int i = 0; i < skillArray.length; ++i) {
            Skill skill = skillArray[i];
            Creature creature = skill.getAimingTarget((Creature)player, player.getTarget());
            if (skill.checkCondition((Creature)player, creature, bl, false, true)) {
                player.getAI().Cast(skill, creature, bl, false);
                continue;
            }
            if (i != 0) continue;
            return false;
        }
        return true;
    }

    public int[] getItemIds() {
        return this.H;
    }
}
