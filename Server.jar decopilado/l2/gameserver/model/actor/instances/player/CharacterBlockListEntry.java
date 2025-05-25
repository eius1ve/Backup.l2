/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player;

import l2.gameserver.dao.CharacterBlockDAO;

public class CharacterBlockListEntry {
    private final int kg;
    private final int kh;
    private final String cV;
    private String cW;

    public CharacterBlockListEntry(int n, int n2, String string) {
        this(n, n2, string, "");
    }

    public CharacterBlockListEntry(int n, int n2, String string, String string2) {
        this.kg = n;
        this.kh = n2;
        this.cV = string;
        this.cW = string2;
    }

    public int getTargetObjId() {
        return this.kh;
    }

    public String getName() {
        return this.cV;
    }

    public String getMemo() {
        return this.cW;
    }

    public void setMemo(String string) {
        this.cW = string;
        CharacterBlockDAO.getInstance().updateMemo(this.kg, this);
    }

    public String toString() {
        return "CharacterBlockListEntry{_targetObjId=" + this.kh + ", _name='" + this.cV + "', _memo='" + this.cW + "'}";
    }
}
