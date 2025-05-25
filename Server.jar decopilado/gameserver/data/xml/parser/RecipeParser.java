/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Element
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.RecipeHolder;
import l2.gameserver.model.Recipe;
import l2.gameserver.templates.item.ItemTemplate;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeParser
extends AbstractFileParser<RecipeHolder> {
    private static final Logger aZ = LoggerFactory.getLogger(RecipeParser.class);
    private static final RecipeParser a = new RecipeParser(RecipeHolder.getInstance());

    protected RecipeParser(RecipeHolder recipeHolder) {
        super(recipeHolder);
    }

    public static RecipeParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/recipe.xml");
    }

    @Override
    public String getDTDFileName() {
        return "recipes.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object;
            Element element2 = (Element)iterator.next();
            if (!"recipe".equalsIgnoreCase(element2.getName())) continue;
            int n = Integer.parseInt(element2.attributeValue("id"));
            int n2 = Integer.parseInt(element2.attributeValue("level"));
            int n3 = Integer.parseInt(element2.attributeValue("mp_consume"));
            int n4 = Integer.parseInt(element2.attributeValue("success_rate"));
            int n5 = Integer.parseInt(element2.attributeValue("item_id"));
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n5);
            Recipe.ERecipeType eRecipeType = Boolean.parseBoolean(element2.attributeValue("is_common")) ? Recipe.ERecipeType.ERT_COMMON : Recipe.ERecipeType.ERT_DWARF;
            ArrayList<Pair<ItemTemplate, Long>> arrayList = new ArrayList<Pair<ItemTemplate, Long>>();
            ArrayList<Pair<ItemTemplate, Long>> arrayList2 = new ArrayList<Pair<ItemTemplate, Long>>();
            ArrayList<Pair<ItemTemplate, Long>> arrayList3 = new ArrayList<Pair<ItemTemplate, Long>>();
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Pair<ItemTemplate, Long> pair;
                Element element3;
                Iterator iterator3;
                object = (Element)iterator2.next();
                if ("materials".equalsIgnoreCase(object.getName())) {
                    iterator3 = object.elementIterator();
                    while (iterator3.hasNext()) {
                        element3 = (Element)iterator3.next();
                        pair = this.b(element3);
                        if (pair == null) continue;
                        arrayList.add(pair);
                    }
                    continue;
                }
                if ("products".equalsIgnoreCase(object.getName())) {
                    iterator3 = object.elementIterator();
                    while (iterator3.hasNext()) {
                        element3 = (Element)iterator3.next();
                        pair = this.c(element3);
                        if (pair == null) continue;
                        arrayList2.add(pair);
                    }
                    continue;
                }
                if (!"npc_fee".equalsIgnoreCase(object.getName())) continue;
                iterator3 = object.elementIterator();
                while (iterator3.hasNext()) {
                    element3 = (Element)iterator3.next();
                    pair = this.b(element3);
                    if (pair == null) continue;
                    arrayList3.add(pair);
                }
            }
            if (itemTemplate == null) {
                aZ.warn("Skip recipe " + n);
                continue;
            }
            if (arrayList2.isEmpty()) {
                aZ.warn("Recipe " + n + " have empty product list. Skip");
                continue;
            }
            if (arrayList.isEmpty()) {
                aZ.warn("Recipe " + n + " have empty material list. Skip");
                continue;
            }
            object = new Recipe(n, itemTemplate, eRecipeType, n2, n3, n4, Collections.unmodifiableList(arrayList), Collections.unmodifiableList(arrayList2), Collections.unmodifiableList(arrayList3));
            ((RecipeHolder)this.getHolder()).addRecipe((Recipe)object);
        }
    }

    private Pair<ItemTemplate, Long> b(Element element) {
        if (!"item".equalsIgnoreCase(element.getName())) {
            return null;
        }
        int n = Integer.parseInt(element.attributeValue("id"));
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        if (element == null) {
            return null;
        }
        long l = Long.parseLong(element.attributeValue("count"));
        return Pair.of((Object)itemTemplate, (Object)l);
    }

    private Pair<ItemTemplate, Pair<Long, Double>> c(Element element) {
        if (!"item".equalsIgnoreCase(element.getName())) {
            return null;
        }
        int n = Integer.parseInt(element.attributeValue("id"));
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        if (element == null) {
            return null;
        }
        long l = Long.parseLong(element.attributeValue("count"));
        double d = Double.parseDouble(element.attributeValue("chance", "-1"));
        return Pair.of((Object)itemTemplate, (Object)Pair.of((Object)l, (Object)d));
    }
}
