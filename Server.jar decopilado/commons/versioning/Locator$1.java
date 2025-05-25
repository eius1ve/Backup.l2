/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.versioning;

import java.io.File;
import java.io.FilenameFilter;

static class Locator.1
implements FilenameFilter {
    final /* synthetic */ String[] val$extensions;

    Locator.1(String[] stringArray) {
        this.val$extensions = stringArray;
    }

    @Override
    public boolean accept(File file, String string) {
        for (int i = 0; i < this.val$extensions.length; ++i) {
            if (!string.toLowerCase().endsWith(this.val$extensions[i])) continue;
            return true;
        }
        return false;
    }
}
