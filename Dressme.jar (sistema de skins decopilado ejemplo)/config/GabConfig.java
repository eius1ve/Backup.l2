/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.configuration.ExProperties
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package config;

import java.io.File;
import java.io.IOException;
import l2.commons.configuration.ExProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GabConfig {
    private static final Logger _log = LoggerFactory.getLogger(GabConfig.class);
    public static final String file = "./config/wardrobe/wardrobe.ini";
    public static boolean ALLOW_ALL_SETS;
    public static boolean PREMIUM_DRESS;
    public static boolean DRESSME_TRADEABLE;
    protected static GabConfig A;

    public void load() {
        try {
            ExProperties l2Properties = GabConfig.initProperties(file);
            ALLOW_ALL_SETS = Boolean.parseBoolean(l2Properties.getProperty("AllowAllSets", "True"));
            PREMIUM_DRESS = Boolean.parseBoolean(l2Properties.getProperty("AllowFreeDressForPremium", "True"));
            DRESSME_TRADEABLE = Boolean.parseBoolean(l2Properties.getProperty("DressMeTradeable", "False"));
            _log.info("WardrobeConfig Has been Initialized!");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Error("Failed to Load ./config/wardrobe/wardrobe.ini File.");
        }
    }

    public static GabConfig getInstance() {
        if (A == null) {
            A = new GabConfig();
        }
        return A;
    }

    public static ExProperties initProperties(String filename) {
        ExProperties result = new ExProperties();
        try {
            result.load(new File(filename));
        }
        catch (IOException e) {
            _log.warn("Wardrobe: Error loading \"" + filename + "\" config.");
        }
        return result;
    }
}
