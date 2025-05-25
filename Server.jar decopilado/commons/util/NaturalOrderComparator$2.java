/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util;

import java.io.File;
import java.util.Comparator;
import l2.commons.util.NaturalOrderComparator;

class NaturalOrderComparator.2
implements Comparator<File> {
    NaturalOrderComparator.2() {
    }

    @Override
    public int compare(File file, File file2) {
        return NaturalOrderComparator.b(file.getName(), file2.getName());
    }
}
