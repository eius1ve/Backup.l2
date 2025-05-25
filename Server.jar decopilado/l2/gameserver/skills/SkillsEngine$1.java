/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills;

import java.io.File;
import java.io.FileFilter;

class SkillsEngine.1
implements FileFilter {
    SkillsEngine.1() {
    }

    @Override
    public boolean accept(File file) {
        return file.getName().endsWith(".xml");
    }
}
