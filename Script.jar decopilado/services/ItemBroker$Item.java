/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.base.Element
 *  l2.gameserver.model.items.TradeItem
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.gameserver.model.base.Element;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

public class ItemBroker.Item {
    public final int itemId;
    public final int itemObjId;
    public final int type;
    public final long price;
    public final long count;
    public final int enchant;
    public final boolean rare;
    public final long merchantStoredId;
    public final String name;
    public final String merchantName;
    public final Location player;
    public final TradeItem item;

    public ItemBroker.Item(int n, int n2, long l, long l2, int n3, String string, long l3, String string2, Location location, int n4, TradeItem tradeItem) {
        this.itemId = n;
        this.type = n2;
        this.price = l;
        this.count = l2;
        this.enchant = n3;
        this.rare = ArrayUtils.contains((int[])ItemBroker.this.RARE_ITEMS, (int)n);
        StringBuilder stringBuilder = new StringBuilder(70);
        if (n3 > 0) {
            if (this.rare) {
                stringBuilder.append("<font color=\"FF0000\">+");
            } else {
                stringBuilder.append("<font color=\"7CFC00\">+");
            }
            stringBuilder.append(n3);
            stringBuilder.append(" ");
        } else if (this.rare) {
            stringBuilder.append("<font color=\"0000FF\">Rare ");
        } else {
            stringBuilder.append("<font color=\"LEVEL\">");
        }
        stringBuilder.append(string);
        stringBuilder.append("</font>]");
        if (tradeItem != null) {
            if (tradeItem.getAttackElement() != Element.NONE.getId()) {
                stringBuilder.append(" &nbsp;<font color=\"7CFC00\">+");
                stringBuilder.append(tradeItem.getAttackElementValue());
                switch (tradeItem.getAttackElement()) {
                    case 0: {
                        stringBuilder.append(" Fire");
                        break;
                    }
                    case 1: {
                        stringBuilder.append(" Water");
                        break;
                    }
                    case 2: {
                        stringBuilder.append(" Wind");
                        break;
                    }
                    case 3: {
                        stringBuilder.append(" Earth");
                        break;
                    }
                    case 4: {
                        stringBuilder.append(" Holy");
                        break;
                    }
                    case 5: {
                        stringBuilder.append(" Unholy");
                    }
                }
                stringBuilder.append("</font>");
            } else {
                int n5;
                int n6;
                int n7;
                int n8;
                int n9;
                int n10 = tradeItem.getDefenceFire();
                if (n10 + (n9 = tradeItem.getDefenceWater()) + (n8 = tradeItem.getDefenceWind()) + (n7 = tradeItem.getDefenceEarth()) + (n6 = tradeItem.getDefenceHoly()) + (n5 = tradeItem.getDefenceUnholy()) > 0) {
                    stringBuilder.append("&nbsp;<font color=\"7CFC00\">");
                    if (n10 > 0) {
                        stringBuilder.append("+");
                        stringBuilder.append(n10);
                        stringBuilder.append(" Fire ");
                    }
                    if (n9 > 0) {
                        stringBuilder.append("+");
                        stringBuilder.append(n9);
                        stringBuilder.append(" Water ");
                    }
                    if (n8 > 0) {
                        stringBuilder.append("+");
                        stringBuilder.append(n8);
                        stringBuilder.append(" Wind ");
                    }
                    if (n7 > 0) {
                        stringBuilder.append("+");
                        stringBuilder.append(n7);
                        stringBuilder.append(" Earth ");
                    }
                    if (n6 > 0) {
                        stringBuilder.append("+");
                        stringBuilder.append(n6);
                        stringBuilder.append(" Holy ");
                    }
                    if (n5 > 0) {
                        stringBuilder.append("+");
                        stringBuilder.append(n5);
                        stringBuilder.append(" Unholy ");
                    }
                    stringBuilder.append("</font>");
                }
            }
        }
        this.name = stringBuilder.toString();
        this.merchantStoredId = l3;
        this.merchantName = string2;
        this.player = location;
        this.itemObjId = n4;
        this.item = tradeItem;
    }
}
