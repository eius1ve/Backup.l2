/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.versioning;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import l2.commons.versioning.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Version {
    private static final Logger ak = LoggerFactory.getLogger(Version.class);
    private String aP = "exported";
    private String aQ = "-1";
    private String aR = "";
    private String aS = "";

    public Version(Class<?> clazz) {
        File file = null;
        try {
            file = Locator.getClassSource(clazz);
            JarFile jarFile = new JarFile(file);
            Attributes attributes = jarFile.getManifest().getMainAttributes();
            this.c(attributes);
            this.d(attributes);
            this.b(attributes);
            this.a(attributes);
        }
        catch (IOException iOException) {
            ak.error("Unable to get soft information\nFile name '" + (file == null ? "null" : file.getAbsolutePath()) + "' isn't a valid jar", (Throwable)iOException);
        }
    }

    private void a(Attributes attributes) {
        String string = attributes.getValue("Implementation-Version");
        this.aQ = string != null ? string : "-1";
    }

    private void b(Attributes attributes) {
        String string = attributes.getValue("Implementation-Build");
        this.aP = string != null ? string : "-1";
    }

    private void c(Attributes attributes) {
        String string = attributes.getValue("Build-Jdk");
        this.aS = string != null ? string : ((string = attributes.getValue("Created-By")) != null ? string : "-1");
    }

    private void d(Attributes attributes) {
        String string = attributes.getValue("Build-Date");
        this.aR = string != null ? string : "-1";
    }

    public String getRevisionNumber() {
        return this.aP;
    }

    public String getVersionNumber() {
        return this.aQ;
    }

    public String getBuildDate() {
        return this.aR;
    }

    public String getBuildJdk() {
        return this.aS;
    }
}
