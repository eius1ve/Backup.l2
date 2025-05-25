/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Collection;
import l2.gameserver.Config;
import l2.gameserver.dao.ItemsDAO;
import l2.gameserver.model.items.ItemInstance;

public class CharSelectInfoPackage {
    private String _name;
    private int fW = 0;
    private int fX = 199546;
    private long _exp = 0L;
    private int _sp = 0;
    private int fY = 0;
    private int fZ = 0;
    private int ga = 0;
    private int gb = 0;
    private int gc = 0;
    private long aV = 0L;
    private int gd = 0;
    private int ge = 0;
    private int gf = 0;
    private int gg = 0;
    private int _level = 1;
    private int gh = 0;
    private int gi = 0;
    private int gj = 0;
    private int _maxHp = 0;
    private double _currentHp = 0.0;
    private int _maxMp = 0;
    private double _currentMp = 0.0;
    private ItemInstance[] a;
    private int gk = 0;
    private int _x = 0;
    private int _y = 0;
    private int gl = 0;
    private int gm = 140000;
    private int gn;
    private int go;

    public CharSelectInfoPackage(int n, String string) {
        this.setObjectId(n);
        this._name = string;
        Collection<ItemInstance> collection = ItemsDAO.getInstance().loadItemsByOwnerIdAndLoc(n, ItemInstance.ItemLocation.PAPERDOLL);
        this.a = new ItemInstance[59];
        for (ItemInstance itemInstance : collection) {
            if (itemInstance.getEquipSlot() >= 59) continue;
            this.a[itemInstance.getEquipSlot()] = itemInstance;
        }
    }

    public int getObjectId() {
        return this.fW;
    }

    public void setObjectId(int n) {
        this.fW = n;
    }

    public int getCharId() {
        return this.fX;
    }

    public void setCharId(int n) {
        this.fX = n;
    }

    public int getClanId() {
        return this.fY;
    }

    public void setClanId(int n) {
        this.fY = n;
    }

    public int getClassId() {
        return this.ga;
    }

    public int getBaseClassId() {
        return this.gb;
    }

    public void setBaseClassId(int n) {
        this.gb = n;
    }

    public void setClassId(int n) {
        this.ga = n;
    }

    public double getCurrentHp() {
        return this._currentHp;
    }

    public void setCurrentHp(double d) {
        this._currentHp = d;
    }

    public double getCurrentMp() {
        return this._currentMp;
    }

    public void setCurrentMp(double d) {
        this._currentMp = d;
    }

    public int getDeleteTimer() {
        return this.gc;
    }

    public void setDeleteTimer(int n) {
        this.gc = n;
    }

    public long getLastAccess() {
        return this.aV;
    }

    public void setLastAccess(long l) {
        this.aV = l;
    }

    public long getExp() {
        return this._exp;
    }

    public void setExp(long l) {
        this._exp = l;
    }

    public int getFace() {
        return this.gd;
    }

    public void setFace(int n) {
        this.gd = n;
    }

    public int getHairColor() {
        return this.gf;
    }

    public void setHairColor(int n) {
        this.gf = n;
    }

    public int getHairStyle() {
        return this.ge;
    }

    public void setHairStyle(int n) {
        this.ge = n;
    }

    public int getPaperdollObjectId(int n) {
        ItemInstance itemInstance = this.a[n];
        if (itemInstance != null) {
            return itemInstance.getObjectId();
        }
        return 0;
    }

    public int getPaperdollAugmentationId(int n) {
        ItemInstance itemInstance = this.a[n];
        if (itemInstance != null && itemInstance.isAugmented()) {
            return itemInstance.getVariationStat1() & 0xFFFF | itemInstance.getVariationStat2() << 16;
        }
        return 0;
    }

    public int getPaperdollItemId(int n) {
        ItemInstance itemInstance = this.a[n];
        if (itemInstance != null) {
            return itemInstance.getItemId();
        }
        return 0;
    }

    public int getPaperdollVisualItemId(int n) {
        return 0;
    }

    public int getPaperdollEnchantEffect(int n) {
        if (!Config.ALT_ALLOW_GLOW_ARMOR_SET) {
            return 0;
        }
        ItemInstance itemInstance = this.a[n];
        if (itemInstance == null && n == 11 && this.a[6] != null && this.a[6].getBodyPart() == 32768L) {
            itemInstance = this.a[6];
        }
        return itemInstance != null ? itemInstance.getEnchantLevel() : 0;
    }

    public int getLevel() {
        return this._level;
    }

    public void setLevel(int n) {
        this._level = n;
    }

    public int getMaxHp() {
        return this._maxHp;
    }

    public void setMaxHp(int n) {
        this._maxHp = n;
    }

    public int getMaxMp() {
        return this._maxMp;
    }

    public void setMaxMp(int n) {
        this._maxMp = n;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String string) {
        this._name = string;
    }

    public int getRace() {
        return this.fZ;
    }

    public void setRace(int n) {
        this.fZ = n;
    }

    public int getSex() {
        return this.gg;
    }

    public void setSex(int n) {
        this.gg = n;
    }

    public int getSp() {
        return this._sp;
    }

    public void setSp(int n) {
        this._sp = n;
    }

    public int getKarma() {
        return this.gh;
    }

    public void setKarma(int n) {
        this.gh = n;
    }

    public int getAccessLevel() {
        return this.gk;
    }

    public void setAccessLevel(int n) {
        this.gk = n;
    }

    public int getX() {
        return this._x;
    }

    public void setX(int n) {
        this._x = n;
    }

    public int getY() {
        return this._y;
    }

    public void setY(int n) {
        this._y = n;
    }

    public int getZ() {
        return this.gl;
    }

    public void setZ(int n) {
        this.gl = n;
    }

    public int getPk() {
        return this.gi;
    }

    public void setPk(int n) {
        this.gi = n;
    }

    public int getPvP() {
        return this.gj;
    }

    public void setPvP(int n) {
        this.gj = n;
    }

    public int getVitalityPoints() {
        return this.gm;
    }

    public void setVitalityPoints(int n) {
        this.gm = n;
    }

    public int getCurrentHero() {
        return this.gn;
    }

    public void setCurrentHero(int n) {
        this.gn = n;
    }

    public int getHairAccessoryPriority() {
        return this.go;
    }

    public void setHairAccessoryPriority(int n) {
        this.go = n;
    }
}
