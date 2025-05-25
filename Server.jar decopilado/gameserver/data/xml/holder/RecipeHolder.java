/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.holder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.Recipe;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RecipeHolder
extends AbstractHolder {
    private static final Logger aW = LoggerFactory.getLogger(RecipeHolder.class);
    private static final RecipeHolder a = new RecipeHolder();
    private final Map<Integer, Recipe> Y = new HashMap<Integer, Recipe>();
    private final Map<Integer, Recipe> Z = new HashMap<Integer, Recipe>();

    public static final RecipeHolder getInstance() {
        return a;
    }

    public void addRecipe(Recipe recipe) {
        if (this.Y.containsKey(recipe.getId())) {
            aW.warn("Recipe \"" + recipe.getId() + "\" already exists.");
        }
        this.Y.put(recipe.getId(), recipe);
        this.Z.put(recipe.getItem().getItemId(), recipe);
    }

    public Recipe getRecipeById(int n) {
        return this.Y.get(n);
    }

    public Recipe getRecipeByItem(ItemTemplate itemTemplate) {
        return this.getRecipeByItem(itemTemplate.getItemId());
    }

    public Recipe getRecipeByItem(ItemInstance itemInstance) {
        return this.getRecipeByItem(itemInstance.getItemId());
    }

    public Recipe getRecipeByItem(int n) {
        return this.Z.get(n);
    }

    public Collection<Recipe> getRecipes() {
        return Collections.unmodifiableMap(this.Y).values();
    }

    @Override
    public int size() {
        return this.Y.size();
    }

    @Override
    public void clear() {
        this.Y.clear();
        this.Z.clear();
    }
}
