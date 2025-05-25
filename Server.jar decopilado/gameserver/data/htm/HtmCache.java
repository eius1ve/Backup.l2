/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.htm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Language;
import l2.gameserver.utils.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HtmCache {
    public static final int DISABLED = 0;
    public static final int LAZY = 1;
    public static final int ENABLED = 2;
    private static final Logger aR = LoggerFactory.getLogger(HtmCache.class);
    private static final HtmCache a = new HtmCache();
    private static final Language a = Language.ENGLISH;
    private final Map<String, String> C = new ConcurrentHashMap<String, String>();
    private final Map<String, String> D = new ConcurrentHashMap<String, String>();

    public static HtmCache getInstance() {
        return a;
    }

    private HtmCache() {
    }

    public void reload() {
        this.clear();
        switch (Config.HTM_CACHE_MODE) {
            case 2: {
                aR.info("HtmCache: Starting cache build. Please wait until it finishes.");
                ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                for (Language language : Language.VALUES) {
                    File file = new File(Config.DATAPACK_ROOT, "data/html-" + language.getShortName());
                    if (!file.exists()) {
                        aR.info("HtmCache: Not find html dir for lang: " + language);
                        continue;
                    }
                    this.a(language, file, file.getAbsolutePath() + "/", executorService);
                }
                executorService.shutdown();
                try {
                    if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                        aR.warn("Executor did not terminate as expected.");
                    }
                }
                catch (InterruptedException interruptedException) {
                    aR.error("Await termination interrupted", (Throwable)interruptedException);
                    executorService.shutdownNow().forEach(Runnable::run);
                    Thread.currentThread().interrupt();
                }
                aR.info("HtmCache: Loaded documents for all languages.");
                for (Language language : Language.VALUES) {
                    aR.info(String.format("HtmCache: Loaded %d document(s) for lang '%s'.", new Object[]{this.a(language).size(), language}));
                }
                break;
            }
            case 1: {
                aR.info("HtmCache: lazy cache mode.");
                break;
            }
            case 0: {
                aR.info("HtmCache: disabled.");
            }
        }
    }

    private void a(final Language language, File file, final String string, ExecutorService executorService) {
        if (!file.exists()) {
            aR.info("HtmCache: dir not exists: " + file);
            return;
        }
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return;
        }
        for (final File file2 : fileArray) {
            if (file2.isDirectory()) {
                this.a(language, file2, string, executorService);
                continue;
            }
            if (!file2.getName().endsWith(".htm")) continue;
            RunnableImpl runnableImpl = new RunnableImpl(){

                @Override
                public void runImpl() {
                    try {
                        HtmCache.this.putContent(language, file2, string);
                    }
                    catch (IOException iOException) {
                        _log.info("HtmCache: file error: " + iOException, (Throwable)iOException);
                    }
                }
            };
            if (executorService != null) {
                executorService.submit(runnableImpl);
                continue;
            }
            runnableImpl.run();
        }
    }

    public void putContent(Language language, File file, String string) throws IOException {
        String string2 = null;
        try (Object object = new FileInputStream(file);){
            byte[] byArray = new byte[(int)file.length()];
            int n = ((FileInputStream)object).read(byArray);
            string2 = new String(byArray, 0, n, StandardCharsets.UTF_8);
        }
        object = file.getAbsolutePath().substring(string.length()).replace("\\", "/");
        this.a(language).put(((String)object).toLowerCase(), Strings.bbParse(string2));
    }

    public String getNotNull(String string, Player player) {
        Language language = player == null ? a : player.getLanguage();
        Object object = this.a(string, language);
        if (StringUtils.isEmpty((CharSequence)object)) {
            object = "Dialog not found: " + string + "; Lang: " + language;
        }
        return object;
    }

    private Map<String, String> a(Language language) {
        if (language == Language.RUSSIAN) {
            return this.D;
        }
        return this.C;
    }

    public String getNullable(String string, Player player) {
        Language language = player == null ? a : player.getLanguage();
        String string2 = this.a(string, language);
        if (StringUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        return string2;
    }

    private String a(String string, Language language) {
        if (string == null) {
            return null;
        }
        String string2 = string.toLowerCase();
        String string3 = this.a(language, string2);
        if (string3 == null) {
            switch (Config.HTM_CACHE_MODE) {
                case 2: {
                    break;
                }
                case 1: {
                    string3 = this.c(language, string);
                    if (string3 != null || language == a) break;
                    string3 = this.c(a, string);
                    break;
                }
                case 0: {
                    string3 = this.b(language, string);
                    if (string3 != null || language == a) break;
                    string3 = this.b(a, string);
                }
            }
        }
        return string3;
    }

    private String b(Language language, String string) {
        String string2 = null;
        File file = new File(Config.DATAPACK_ROOT, "data/html-" + language.getShortName() + "/" + string);
        if (file.exists()) {
            try {
                string2 = FileUtils.readFileToString((File)file, (Charset)StandardCharsets.UTF_8);
                string2 = Strings.bbParse(string2);
            }
            catch (IOException iOException) {
                aR.info("HtmCache: File error: " + string + " lang: " + language);
            }
        }
        return string2;
    }

    private String c(Language language, String string) {
        String string2 = null;
        File file = new File(Config.DATAPACK_ROOT, "data/html-" + language.getShortName() + "/" + string);
        if (file.exists()) {
            try {
                string2 = FileUtils.readFileToString((File)file, (Charset)StandardCharsets.UTF_8);
                string2 = Strings.bbParse(string2);
                this.a(language).put(string.toLowerCase(), string2);
            }
            catch (IOException iOException) {
                aR.info("HtmCache: File error: " + string + " lang: " + language);
            }
        }
        return string2;
    }

    private String a(Language language, String string) {
        String string2 = this.a(language).get(string);
        if (string2 == null) {
            string2 = this.a(Language.ENGLISH).get(string);
        }
        return string2;
    }

    public void clear() {
        for (Language language : Language.VALUES) {
            this.a(language).clear();
        }
    }
}
