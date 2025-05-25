/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.scripts;

import java.io.File;
import java.io.FileFilter;

class Scripts.1
implements FileFilter {
    Scripts.1() {
    }

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".ext.jar");
    }
}
