/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.data.xml.helpers;

import l2.commons.data.xml.AbstractParser;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ErrorHandlerImpl
implements ErrorHandler {
    private AbstractParser<?> a;

    public ErrorHandlerImpl(AbstractParser<?> abstractParser) {
        this.a = abstractParser;
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        this.a.warn("File: " + this.a.getCurrentFileName() + ":" + sAXParseException.getLineNumber() + " warning: " + sAXParseException.getMessage());
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        this.a.error("File: " + this.a.getCurrentFileName() + ":" + sAXParseException.getLineNumber() + " error: " + sAXParseException.getMessage());
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        this.a.error("File: " + this.a.getCurrentFileName() + ":" + sAXParseException.getLineNumber() + " fatal: " + sAXParseException.getMessage());
    }
}
