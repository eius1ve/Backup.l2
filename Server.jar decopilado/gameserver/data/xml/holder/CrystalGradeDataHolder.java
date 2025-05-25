/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.Grade;

public final class CrystalGradeDataHolder
extends AbstractHolder {
    private static final CrystalGradeDataHolder a = new CrystalGradeDataHolder();
    private final Map<String, Grade> K = new LinkedHashMap<String, Grade>();
    private List<Grade> ac = null;

    public static CrystalGradeDataHolder getInstance() {
        return a;
    }

    private List<Grade> j() {
        if (this.ac == null) {
            ArrayList<Grade> arrayList = new ArrayList<Grade>(this.getAllGrades());
            arrayList.sort(Comparator.comparingInt(Grade::getExperienceLevel));
            this.ac = arrayList;
            return this.ac;
        }
        return this.ac;
    }

    public void addGrade(String string, int n, int n2, int n3, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, int n4) {
        Grade grade = new Grade(string, n, n2, this.K.size(), n3, d, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, n4);
        this.K.put(string, grade);
    }

    public Grade getGrade(String string) {
        return this.K.get(string);
    }

    public Grade getGradeByLevel(int n) {
        Grade grade = null;
        for (Grade grade2 : this.j()) {
            if (grade2.getExperienceLevel() <= n) {
                grade = grade2;
                continue;
            }
            return grade;
        }
        return grade;
    }

    public Collection<Grade> getAllGrades() {
        return this.K.values();
    }

    @Override
    public int size() {
        return this.K.size();
    }

    @Override
    public void clear() {
        this.K.clear();
    }
}
