/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import java.io.Serializable;
import l2.gameserver.model.base.Element;

public class ItemAttributes
implements Serializable {
    private static final long cv = 401594188363005415L;
    private int nL;
    private int nM;
    private int nN;
    private int nO;
    private int nP;
    private int nQ;

    public ItemAttributes() {
        this(0, 0, 0, 0, 0, 0);
    }

    public ItemAttributes(int n, int n2, int n3, int n4, int n5, int n6) {
        this.nL = n;
        this.nM = n2;
        this.nN = n3;
        this.nO = n4;
        this.nP = n5;
        this.nQ = n6;
    }

    public int getFire() {
        return this.nL;
    }

    public void setFire(int n) {
        this.nL = n;
    }

    public int getWater() {
        return this.nM;
    }

    public void setWater(int n) {
        this.nM = n;
    }

    public int getWind() {
        return this.nN;
    }

    public void setWind(int n) {
        this.nN = n;
    }

    public int getEarth() {
        return this.nO;
    }

    public void setEarth(int n) {
        this.nO = n;
    }

    public int getHoly() {
        return this.nP;
    }

    public void setHoly(int n) {
        this.nP = n;
    }

    public int getUnholy() {
        return this.nQ;
    }

    public void setUnholy(int n) {
        this.nQ = n;
    }

    public Element getElement() {
        if (this.nL > 0) {
            return Element.FIRE;
        }
        if (this.nM > 0) {
            return Element.WATER;
        }
        if (this.nN > 0) {
            return Element.WIND;
        }
        if (this.nO > 0) {
            return Element.EARTH;
        }
        if (this.nP > 0) {
            return Element.HOLY;
        }
        if (this.nQ > 0) {
            return Element.UNHOLY;
        }
        return Element.NONE;
    }

    public int getValue() {
        if (this.nL > 0) {
            return this.nL;
        }
        if (this.nM > 0) {
            return this.nM;
        }
        if (this.nN > 0) {
            return this.nN;
        }
        if (this.nO > 0) {
            return this.nO;
        }
        if (this.nP > 0) {
            return this.nP;
        }
        if (this.nQ > 0) {
            return this.nQ;
        }
        return 0;
    }

    public void setValue(Element element, int n) {
        switch (element) {
            case FIRE: {
                this.nL = n;
                break;
            }
            case WATER: {
                this.nM = n;
                break;
            }
            case WIND: {
                this.nN = n;
                break;
            }
            case EARTH: {
                this.nO = n;
                break;
            }
            case HOLY: {
                this.nP = n;
                break;
            }
            case UNHOLY: {
                this.nQ = n;
            }
        }
    }

    public int getValue(Element element) {
        switch (element) {
            case FIRE: {
                return this.nL;
            }
            case WATER: {
                return this.nM;
            }
            case WIND: {
                return this.nN;
            }
            case EARTH: {
                return this.nO;
            }
            case HOLY: {
                return this.nP;
            }
            case UNHOLY: {
                return this.nQ;
            }
        }
        return 0;
    }

    public ItemAttributes clone() {
        return new ItemAttributes(this.nL, this.nM, this.nN, this.nO, this.nP, this.nQ);
    }
}
