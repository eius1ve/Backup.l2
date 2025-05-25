/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.model.entity.events.objects;

import l2.gameserver.utils.Location;
import org.dom4j.Element;

public class BoatPoint
extends Location {
    private int lv;
    private int lw;
    private final int lx;
    private boolean dk;

    public BoatPoint(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl) {
        super(n, n2, n3, n4);
        this.lv = n5;
        this.lw = n6;
        this.lx = n7;
        this.dk = bl;
    }

    public int getSpeed1() {
        return this.lv;
    }

    public int getSpeed2() {
        return this.lw;
    }

    public int getFuel() {
        return this.lx;
    }

    public boolean isTeleport() {
        return this.dk;
    }

    public static BoatPoint parse(Element element) {
        int n = element.attributeValue("speed1") == null ? 0 : Integer.parseInt(element.attributeValue("speed1"));
        int n2 = element.attributeValue("speed2") == null ? 0 : Integer.parseInt(element.attributeValue("speed2"));
        int n3 = Integer.parseInt(element.attributeValue("x"));
        int n4 = Integer.parseInt(element.attributeValue("y"));
        int n5 = Integer.parseInt(element.attributeValue("z"));
        int n6 = element.attributeValue("h") == null ? 0 : Integer.parseInt(element.attributeValue("h"));
        int n7 = element.attributeValue("fuel") == null ? 0 : Integer.parseInt(element.attributeValue("fuel"));
        boolean bl = Boolean.parseBoolean(element.attributeValue("teleport"));
        return new BoatPoint(n3, n4, n5, n6, n, n2, n7, bl);
    }

    public void setSpeed1(int n) {
        this.lv = n;
    }

    public void setSpeed2(int n) {
        this.lw = n;
    }

    public void setTeleport(boolean bl) {
        this.dk = bl;
    }
}
