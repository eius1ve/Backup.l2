/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Language;

public final class StringHolder
extends AbstractHolder {
    private static final StringHolder a = new StringHolder();
    private Map<Language, Map<String, String>> B = new HashMap<Language, Map<String, String>>();

    public static StringHolder getInstance() {
        return a;
    }

    private StringHolder() {
    }

    public String getNullable(Player player, String string) {
        Language language = player == null ? Language.ENGLISH : player.getLanguage();
        return this.a(language, string);
    }

    public String getNotNull(Player player, String string) {
        Language language = player == null ? Language.ENGLISH : player.getLanguage();
        Object object = this.a(language, string);
        if (object == null && player != null) {
            object = "Not find string: " + string + "; for lang: " + language;
            this.B.get((Object)language).put(string, (String)object);
        }
        return object;
    }

    public String getNotNull(Language language, String string) {
        Object object = this.a(language, string);
        if (object == null && language != null) {
            object = "Not find string: " + string + "; for lang: " + language;
            this.B.get((Object)language).put(string, (String)object);
        }
        return object;
    }

    private String a(Language language, String string) {
        Map<String, String> map = this.B.get((Object)language);
        return map.get(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load() {
        for (Language language : Language.VALUES) {
            this.B.put(language, new HashMap());
            File file = new File(Config.DATAPACK_ROOT, "data/string/strings_" + language.getShortName() + ".properties");
            if (!file.exists()) {
                this.warn("Not find file: " + file.getAbsolutePath());
                continue;
            }
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new LineNumberReader(new FileReader(file));
                String string = null;
                while ((string = ((LineNumberReader)bufferedReader).readLine()) != null) {
                    if (string.startsWith("#")) continue;
                    StringTokenizer stringTokenizer = new StringTokenizer(string, "=");
                    if (stringTokenizer.countTokens() < 2) {
                        this.error("Error on line " + ((LineNumberReader)bufferedReader).getLineNumber() + ": " + string + "; file: " + file.getName());
                        continue;
                    }
                    String string2 = stringTokenizer.nextToken();
                    Object object = stringTokenizer.nextToken();
                    while (stringTokenizer.hasMoreTokens()) {
                        object = (String)object + "=" + stringTokenizer.nextToken();
                    }
                    Map<String, String> map = this.B.get((Object)language);
                    map.put(string2, (String)object);
                }
            }
            catch (Exception exception) {
                this.error("Exception: " + exception, exception);
            }
            finally {
                try {
                    bufferedReader.close();
                }
                catch (Exception exception) {}
            }
        }
        this.log();
    }

    public void reload() {
        this.clear();
        this.load();
    }

    @Override
    public void log() {
        for (Map.Entry<Language, Map<String, String>> entry : this.B.entrySet()) {
            this.info("load strings: " + entry.getValue().size() + " for lang: " + (Object)((Object)entry.getKey()));
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
        this.B.clear();
    }
}
