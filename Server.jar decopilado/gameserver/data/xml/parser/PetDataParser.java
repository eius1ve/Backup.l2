/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  org.apache.commons.lang3.StringUtils
 *  org.dom4j.Element
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.parser;

import gnu.trove.TIntIntHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import l2.commons.collections.MultiValueSet;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.Experience;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetDataParser
extends AbstractFileParser<PetDataHolder> {
    private static final Logger aY = LoggerFactory.getLogger(PetDataParser.class);
    private static final PetDataParser a = new PetDataParser();
    private static final String bK = "data/stats/pets/pet_data.xml";
    private static final String bL = "pet_data.dtd";

    private PetDataParser() {
        super(PetDataHolder.getInstance());
    }

    public static final PetDataParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, bK);
    }

    @Override
    public String getDTDFileName() {
        return bL;
    }

    private void a(Element element, MultiValueSet<String>[] multiValueSetArray, Optional<Integer> optional) {
        String string = Objects.requireNonNull(element.attributeValue("name"), "'key' is null for " + element);
        String string2 = Objects.requireNonNull(element.attributeValue("value"), "'value' is null for key '" + string + "'");
        if (optional.isPresent()) {
            int n = optional.get();
            MultiValueSet<String> multiValueSet = multiValueSetArray[n - 1];
            if (multiValueSet == null) {
                MultiValueSet multiValueSet2 = new MultiValueSet();
                multiValueSetArray[n - 1] = multiValueSet2;
                multiValueSet = multiValueSet2;
            }
            multiValueSet.set(string, StringUtils.trimToNull((String)string2));
        } else {
            String[] stringArray = StringUtils.split((String)string2, null);
            if (stringArray.length == multiValueSetArray.length) {
                for (int i = 0; i < stringArray.length; ++i) {
                    MultiValueSet<String> multiValueSet = multiValueSetArray[i];
                    if (multiValueSet == null) {
                        multiValueSet = multiValueSetArray[i] = new MultiValueSet();
                    }
                    multiValueSet.set(string, stringArray[i]);
                }
            } else if (stringArray.length == 1) {
                for (int i = 0; i < multiValueSetArray.length; ++i) {
                    MultiValueSet<String> multiValueSet = multiValueSetArray[i];
                    if (multiValueSet == null) {
                        multiValueSet = multiValueSetArray[i] = new MultiValueSet();
                    }
                    multiValueSet.set(string, stringArray[0]);
                }
            } else {
                throw new IllegalArgumentException("'" + string2 + "' does not represent table or placeholder");
            }
        }
    }

    protected void readPetData(Element element) throws Exception {
        Object object;
        Object object2;
        int n = Integer.parseInt(element.attributeValue("id"));
        int n2 = Integer.parseInt(element.attributeValue("max_lvl", String.valueOf(Experience.LEVEL.length - 1)));
        MultiValueSet[] multiValueSetArray = new MultiValueSet[n2];
        ArrayList<SkillLearn> arrayList = new ArrayList<SkillLearn>();
        TIntIntHashMap tIntIntHashMap = new TIntIntHashMap();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            int n3;
            Element element2;
            Iterator iterator2;
            object2 = (Element)iterator.next();
            if ("set".equalsIgnoreCase(object2.getName())) {
                this.a((Element)object2, multiValueSetArray, Optional.empty());
                continue;
            }
            if ("levels".equalsIgnoreCase(object2.getName())) {
                object = object2;
                iterator2 = object.elementIterator();
                while (iterator2.hasNext()) {
                    element2 = (Element)iterator2.next();
                    if (!"level".equalsIgnoreCase(element2.getName())) continue;
                    n3 = Integer.parseInt(element2.attributeValue("val"));
                    Iterator iterator3 = element2.elementIterator();
                    while (iterator3.hasNext()) {
                        Element element3 = (Element)iterator3.next();
                        if (!"set".equalsIgnoreCase(element3.getName())) continue;
                        this.a((Element)object2, multiValueSetArray, Optional.of(n3));
                    }
                }
                continue;
            }
            if (!"skills".equalsIgnoreCase(object2.getName())) continue;
            object = object2;
            iterator2 = object.elementIterator();
            while (iterator2.hasNext()) {
                element2 = (Element)iterator2.next();
                if (!"skill".equalsIgnoreCase(element2.getName())) continue;
                n3 = Integer.parseInt(element2.attributeValue("id"));
                int n4 = Integer.parseInt(element2.attributeValue("level"));
                int n5 = Integer.parseInt(element2.attributeValue("min_lvl"));
                String string = element2.attributeValue("action_id");
                Skill skill = SkillTable.getInstance().getInfo(n3, n4 > 0 ? n4 : 1);
                if (skill == null) {
                    this.error("Skill [" + n3 + ":" + n4 + "] not defined!");
                    continue;
                }
                SkillLearn skillLearn = new SkillLearn(skill.getId(), n4 > 0 ? skill.getLevel() : n4, n5, 0, 0, 0L, false, false);
                arrayList.add(skillLearn);
                if (string == null || string.isEmpty()) continue;
                tIntIntHashMap.put(Integer.parseInt(string), skill.getId());
            }
        }
        for (int i = 0; i < n2; ++i) {
            object2 = multiValueSetArray[i] != null ? multiValueSetArray[i] : new MultiValueSet();
            object = new PetData(n, i + 1, (MultiValueSet<String>)object2);
            ((PetData)object).setSkillLearns(arrayList);
            ((PetData)object).setActionId2SkillId(tIntIntHashMap);
            ((PetDataHolder)this.getHolder()).addPetData((PetData)object);
        }
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"pet".equalsIgnoreCase(element2.getName())) continue;
            this.readPetData(element2);
        }
    }
}
