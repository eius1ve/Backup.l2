/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import java.util.Arrays;
import l2.gameserver.stats.Stats;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Element
extends Enum<Element> {
    public static final /* enum */ Element FIRE = new Element(0, Stats.ATTACK_FIRE, Stats.DEFENCE_FIRE);
    public static final /* enum */ Element WATER = new Element(1, Stats.ATTACK_WATER, Stats.DEFENCE_WATER);
    public static final /* enum */ Element WIND = new Element(2, Stats.ATTACK_WIND, Stats.DEFENCE_WIND);
    public static final /* enum */ Element EARTH = new Element(3, Stats.ATTACK_EARTH, Stats.DEFENCE_EARTH);
    public static final /* enum */ Element HOLY = new Element(4, Stats.ATTACK_HOLY, Stats.DEFENCE_HOLY);
    public static final /* enum */ Element UNHOLY = new Element(5, Stats.ATTACK_UNHOLY, Stats.DEFENCE_UNHOLY);
    public static final /* enum */ Element NONE = new Element(-2, null, null);
    public static final Element[] VALUES;
    private final byte c;
    private final Stats a;
    private final Stats b;
    private static final /* synthetic */ Element[] a;

    public static Element[] values() {
        return (Element[])a.clone();
    }

    public static Element valueOf(String string) {
        return Enum.valueOf(Element.class, string);
    }

    private Element(int n2, Stats stats, Stats stats2) {
        this.c = (byte)n2;
        this.a = stats;
        this.b = stats2;
    }

    public byte getId() {
        return this.c;
    }

    public Stats getAttack() {
        return this.a;
    }

    public Stats getDefence() {
        return this.b;
    }

    public static Element getElementById(int n) {
        for (Element element : VALUES) {
            if (element.getId() != n) continue;
            return element;
        }
        return NONE;
    }

    public static Element getReverseElement(Element element) {
        switch (element) {
            case WATER: {
                return FIRE;
            }
            case FIRE: {
                return WATER;
            }
            case WIND: {
                return EARTH;
            }
            case EARTH: {
                return WIND;
            }
            case HOLY: {
                return UNHOLY;
            }
            case UNHOLY: {
                return HOLY;
            }
        }
        return NONE;
    }

    public static Element getElementByName(String string) {
        for (Element element : VALUES) {
            if (!element.name().equalsIgnoreCase(string)) continue;
            return element;
        }
        return NONE;
    }

    private static /* synthetic */ Element[] a() {
        return new Element[]{FIRE, WATER, WIND, EARTH, HOLY, UNHOLY, NONE};
    }

    static {
        a = Element.a();
        VALUES = Arrays.copyOf(Element.values(), 6);
    }
}
