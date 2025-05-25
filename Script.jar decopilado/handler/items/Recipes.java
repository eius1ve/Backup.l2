/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.RecipeHolder
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Recipe
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.RecipeBookItemList
 *  l2.gameserver.network.l2.s2c.SystemMessage
 */
package handler.items;

import handler.items.ScriptItemHandler;
import java.util.Collection;
import l2.gameserver.data.xml.holder.RecipeHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Recipe;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.RecipeBookItemList;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class Recipes
extends ScriptItemHandler {
    private static int[] H = null;

    public Recipes() {
        Collection collection = RecipeHolder.getInstance().getRecipes();
        H = new int[collection.size()];
        int n = 0;
        for (Recipe recipe : collection) {
            Recipes.H[n++] = recipe.getItem().getItemId();
        }
    }

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        Recipe recipe = RecipeHolder.getInstance().getRecipeByItem(itemInstance);
        switch (recipe.getType()) {
            case ERT_DWARF: {
                if (player.getDwarvenRecipeLimit() > 0) {
                    if (player.getDwarvenRecipeBook().size() >= player.getDwarvenRecipeLimit()) {
                        player.sendPacket((IStaticPacket)SystemMsg.NO_FURTHER_RECIPES_MAY_BE_REGISTERED);
                        return false;
                    }
                    if (recipe.getRequiredSkillLvl() > player.getSkillLevel(Integer.valueOf(172))) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOUR_CREATE_ITEM_LEVEL_IS_TOO_LOW_TO_REGISTER_THIS_RECIPE);
                        return false;
                    }
                    if (player.hasRecipe(recipe)) {
                        player.sendPacket((IStaticPacket)SystemMsg.THAT_RECIPE_IS_ALREADY_REGISTERED);
                        return false;
                    }
                    if (!player.getInventory().destroyItem(itemInstance, 1L)) {
                        player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                        return false;
                    }
                    player.registerRecipe(recipe, true);
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_BEEN_ADDED).addItemName(itemInstance.getItemId()));
                    player.sendPacket((IStaticPacket)new RecipeBookItemList(player, true));
                    return true;
                }
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_REGISTER_A_RECIPE);
                break;
            }
            case ERT_COMMON: {
                if (player.getCommonRecipeLimit() > 0) {
                    if (player.getCommonRecipeBook().size() >= player.getCommonRecipeLimit()) {
                        player.sendPacket((IStaticPacket)SystemMsg.NO_FURTHER_RECIPES_MAY_BE_REGISTERED);
                        return false;
                    }
                    if (player.hasRecipe(recipe)) {
                        player.sendPacket((IStaticPacket)SystemMsg.THAT_RECIPE_IS_ALREADY_REGISTERED);
                        return false;
                    }
                    if (!player.getInventory().destroyItem(itemInstance, 1L)) {
                        player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                        return false;
                    }
                    player.registerRecipe(recipe, true);
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_BEEN_ADDED).addItemName(itemInstance.getItemId()));
                    player.sendPacket((IStaticPacket)new RecipeBookItemList(player, false));
                    return true;
                }
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_REGISTER_A_RECIPE);
            }
        }
        return false;
    }

    public int[] getItemIds() {
        return H;
    }
}
