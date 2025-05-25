/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 */
package l2.commons.data.xml;

import java.io.File;
import java.io.InputStream;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.data.xml.helpers.ErrorHandlerImpl;
import l2.commons.data.xml.helpers.SimpleDTDEntityResolver;
import l2.commons.logging.LoggerObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

public abstract class AbstractParser<H extends AbstractHolder>
extends LoggerObject {
    protected final H _holder;
    protected String _currentFile;
    protected SAXReader _reader;

    protected AbstractParser(H h) {
        this._holder = h;
        this._reader = new SAXReader();
        this._reader.setValidation(false);
        this._reader.setErrorHandler((ErrorHandler)new ErrorHandlerImpl(this));
    }

    protected void initDTD(File file) {
        this._reader.setEntityResolver((EntityResolver)new SimpleDTDEntityResolver(file));
    }

    protected void parseDocument(InputStream inputStream, String string) throws Exception {
        this._currentFile = string;
        Document document = this._reader.read(inputStream);
        this.readData(document.getRootElement());
    }

    protected abstract void readData(Element var1) throws Exception;

    protected abstract void parse();

    protected H getHolder() {
        return this._holder;
    }

    public String getCurrentFileName() {
        return this._currentFile;
    }

    public void load() {
        this.parse();
        ((AbstractHolder)this._holder).process();
        ((AbstractHolder)this._holder).log();
    }

    public void reload() {
        this.info("reload start...");
        ((AbstractHolder)this._holder).clear();
        this.load();
    }
}
