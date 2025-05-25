/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Attribute
 *  org.dom4j.Element
 *  org.napile.primitive.sets.IntSet
 *  org.napile.primitive.sets.impl.HashIntSet
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import l2.commons.data.xml.AbstractDirParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.Skill;
import l2.gameserver.model.TeleportLocation;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.entity.residence.ResidenceFunction;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.support.MerchantGuard;
import l2.gameserver.utils.Location;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.HashIntSet;

public final class ResidenceParser
extends AbstractDirParser<ResidenceHolder> {
    private static ResidenceParser _instance = new ResidenceParser();

    public static ResidenceParser getInstance() {
        return _instance;
    }

    private ResidenceParser() {
        super(ResidenceHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/residences/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "residence.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Object object;
        String string = element.attributeValue("impl");
        Class<?> clazz = null;
        StatsSet statsSet = new StatsSet();
        Object object2 = element.attributeIterator();
        while (object2.hasNext()) {
            object = (Attribute)object2.next();
            statsSet.set(object.getName(), object.getValue());
        }
        object2 = null;
        try {
            clazz = Class.forName("l2.gameserver.model.entity.residence." + string);
            object = clazz.getConstructor(StatsSet.class);
            object2 = (Residence)((Constructor)object).newInstance(statsSet);
            ((ResidenceHolder)this.getHolder()).addResidence((Residence)object2);
        }
        catch (Exception exception) {
            this.error("fail to init: " + this.getCurrentFileName(), exception);
            return;
        }
        object = element.elementIterator();
        while (object.hasNext()) {
            Element element2 = (Element)object.next();
            String string2 = element2.getName();
            int n = element2.attributeValue("level") == null ? 0 : Integer.valueOf(element2.attributeValue("level"));
            int n2 = (int)((double)(element2.attributeValue("lease") == null ? 0 : Integer.valueOf(element2.attributeValue("lease"))) * Config.RESIDENCE_LEASE_FUNC_MULTIPLIER);
            int n3 = element2.attributeValue("npcId") == null ? 0 : Integer.valueOf(element2.attributeValue("npcId"));
            int n4 = element2.attributeValue("listId") == null ? 0 : Integer.valueOf(element2.attributeValue("listId"));
            ResidenceFunction residenceFunction = null;
            if (string2.equalsIgnoreCase("teleport")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 1);
                var14_15 = new ArrayList();
                var15_16 = element2.elementIterator();
                while (var15_16.hasNext()) {
                    Element element3 = (Element)var15_16.next();
                    if (!"target".equalsIgnoreCase(element3.getName())) continue;
                    String string3 = element3.attributeValue("name");
                    long l = Long.parseLong(element3.attributeValue("price"));
                    int n5 = element3.attributeValue("item") == null ? 57 : Integer.parseInt(element3.attributeValue("item"));
                    int n6 = Integer.parseInt(element3.attributeValue("fstring", "0"));
                    TeleportLocation teleportLocation = new TeleportLocation(n5, l, string3, n6, 0, 0);
                    teleportLocation.set(Location.parseLoc(element3.attributeValue("loc")));
                    var14_15.add(teleportLocation);
                }
                residenceFunction.addTeleports(n, var14_15.toArray(new TeleportLocation[var14_15.size()]));
            } else if (string2.equalsIgnoreCase("support")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 6);
                residenceFunction.addBuffs(n);
            } else if (string2.equalsIgnoreCase("item_create")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 2);
                residenceFunction.addBuylist(n, new int[]{n3, n4});
            } else if (string2.equalsIgnoreCase("curtain")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 7);
            } else if (string2.equalsIgnoreCase("platform")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 8);
            } else if (string2.equalsIgnoreCase("restore_exp")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 5);
            } else if (string2.equalsIgnoreCase("restore_hp")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 3);
            } else if (string2.equalsIgnoreCase("restore_mp")) {
                residenceFunction = this.checkAndGetFunction((Residence)object2, 4);
            } else if (string2.equalsIgnoreCase("skills")) {
                var14_15 = element2.elementIterator();
                while (var14_15.hasNext()) {
                    var15_16 = (Element)var14_15.next();
                    var16_18 = Integer.parseInt(var15_16.attributeValue("id"));
                    var17_20 = Integer.parseInt(var15_16.attributeValue("level"));
                    Skill skill = SkillTable.getInstance().getInfo(var16_18, var17_20);
                    if (skill == null) continue;
                    ((Residence)object2).addSkill(skill);
                }
            } else if (string2.equalsIgnoreCase("banish_points")) {
                var14_15 = element2.elementIterator();
                while (var14_15.hasNext()) {
                    var15_16 = Location.parse((Element)var14_15.next());
                    ((Residence)object2).addBanishPoint((Location)var15_16);
                }
            } else if (string2.equalsIgnoreCase("owner_restart_points")) {
                var14_15 = element2.elementIterator();
                while (var14_15.hasNext()) {
                    var15_16 = Location.parse((Element)var14_15.next());
                    ((Residence)object2).addOwnerRestartPoint((Location)var15_16);
                }
            } else if (string2.equalsIgnoreCase("other_restart_points")) {
                var14_15 = element2.elementIterator();
                while (var14_15.hasNext()) {
                    var15_16 = Location.parse((Element)var14_15.next());
                    ((Residence)object2).addOtherRestartPoint((Location)var15_16);
                }
            } else if (string2.equalsIgnoreCase("chaos_restart_points")) {
                var14_15 = element2.elementIterator();
                while (var14_15.hasNext()) {
                    var15_16 = Location.parse((Element)var14_15.next());
                    ((Residence)object2).addChaosRestartPoint((Location)var15_16);
                }
            } else if (string2.equalsIgnoreCase("merchant_guards")) {
                var14_15 = element2.elementIterator();
                while (var14_15.hasNext()) {
                    String[] stringArray;
                    var15_16 = (Element)var14_15.next();
                    var16_18 = Integer.parseInt(var15_16.attributeValue("item_id"));
                    var17_20 = Integer.parseInt(var15_16.attributeValue("npc_id"));
                    int n7 = Integer.parseInt(var15_16.attributeValue("max"));
                    HashIntSet hashIntSet = new HashIntSet(3);
                    for (String string4 : stringArray = var15_16.attributeValue("ssq").split(";")) {
                        if (string4.equalsIgnoreCase("cabal_null")) {
                            hashIntSet.add(0);
                            continue;
                        }
                        if (string4.equalsIgnoreCase("cabal_dusk")) {
                            hashIntSet.add(1);
                            continue;
                        }
                        if (string4.equalsIgnoreCase("cabal_dawn")) {
                            hashIntSet.add(2);
                            continue;
                        }
                        this.error("Unknown ssq type: " + string4 + "; file: " + this.getCurrentFileName());
                    }
                    ((Castle)object2).addMerchantGuard(new MerchantGuard(var16_18, var17_20, n7, (IntSet)hashIntSet));
                }
            }
            if (residenceFunction == null) continue;
            residenceFunction.addLease(n, n2);
        }
    }

    private ResidenceFunction checkAndGetFunction(Residence residence, int n) {
        ResidenceFunction residenceFunction = residence.getFunction(n);
        if (residenceFunction == null) {
            residenceFunction = new ResidenceFunction(residence.getId(), n);
            residence.addFunction(residenceFunction);
        }
        return residenceFunction;
    }
}
