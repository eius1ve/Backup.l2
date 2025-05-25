/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player;

import l2.gameserver.utils.Location;

public class TpBookMark
extends Location {
    private int kp;
    private String _name;
    private String cX;

    public TpBookMark(int n, int n2, int n3, int n4, String string, String string2) {
        super(n, n2, n3);
        this.setIcon(n4);
        this.setName(string);
        this.setAcronym(string2);
    }

    public void setIcon(int n) {
        this.kp = n;
    }

    public int getIcon() {
        return this.kp;
    }

    public void setName(String string) {
        this._name = string;
    }

    public String getName() {
        return this._name;
    }

    public void setAcronym(String string) {
        this.cX = string;
    }

    public String getAcronym() {
        return this.cX;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        TpBookMark tpBookMark = (TpBookMark)object;
        return tpBookMark.getName().equals(this.getName()) && tpBookMark.getX() == this.getX() && tpBookMark.getY() == this.getY() && tpBookMark.getZ() == this.getZ();
    }
}
