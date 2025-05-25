/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.htm;

import java.io.File;
import java.io.IOException;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.utils.Language;

class HtmCache.1
extends RunnableImpl {
    final /* synthetic */ Language val$lang;
    final /* synthetic */ File val$file;
    final /* synthetic */ String val$rootPath;

    HtmCache.1(Language language, File file, String string) {
        this.val$lang = language;
        this.val$file = file;
        this.val$rootPath = string;
    }

    @Override
    public void runImpl() {
        try {
            HtmCache.this.putContent(this.val$lang, this.val$file, this.val$rootPath);
        }
        catch (IOException iOException) {
            _log.info("HtmCache: file error: " + iOException, (Throwable)iOException);
        }
    }
}
