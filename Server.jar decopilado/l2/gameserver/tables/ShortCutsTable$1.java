/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.tables;

import java.io.File;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

static class ShortCutsTable.1
implements ErrorHandler {
    final /* synthetic */ File val$file;

    ShortCutsTable.1(File file) {
        this.val$file = file;
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        ds.warn("File: " + this.val$file.getName() + ":" + sAXParseException.getLineNumber() + " warning: " + sAXParseException.getMessage());
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        ds.error("File: " + this.val$file.getName() + ":" + sAXParseException.getLineNumber() + " error: " + sAXParseException.getMessage());
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        ds.error("File: " + this.val$file.getName() + ":" + sAXParseException.getLineNumber() + " fatal: " + sAXParseException.getMessage());
    }
}
