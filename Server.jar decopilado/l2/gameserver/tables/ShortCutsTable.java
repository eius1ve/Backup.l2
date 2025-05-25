/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FilenameUtils
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.helpers.SimpleDTDEntityResolver;
import l2.gameserver.Config;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.base.ClassId;
import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ShortCutsTable {
    private static final Logger ds = LoggerFactory.getLogger(ShortCutsTable.class);
    private static ShortCutsTable a;
    private Map<ClassId, List<ShortCut>> aR = ShortCutsTable.a(new File(Config.DATAPACK_ROOT, "data/character_shortcuts.xml"));

    public static ShortCutsTable getInstance() {
        if (a == null) {
            a = new ShortCutsTable();
        }
        return a;
    }

    private ShortCutsTable() {
    }

    public List<ShortCut> getShortCuts(ClassId classId) {
        List<ShortCut> list = this.aR.get((Object)classId);
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Map<ClassId, List<ShortCut>> a(final File file) {
        HashMap<ClassId, List<ShortCut>> hashMap = new HashMap<ClassId, List<ShortCut>>();
        if (!file.exists()) {
            ds.warn("File " + file.getAbsolutePath() + " not exists");
            return Collections.emptyMap();
        }
        SAXReader sAXReader = new SAXReader();
        sAXReader.setValidation(true);
        sAXReader.setErrorHandler(new ErrorHandler(){

            @Override
            public void warning(SAXParseException sAXParseException) throws SAXException {
                ds.warn("File: " + file.getName() + ":" + sAXParseException.getLineNumber() + " warning: " + sAXParseException.getMessage());
            }

            @Override
            public void error(SAXParseException sAXParseException) throws SAXException {
                ds.error("File: " + file.getName() + ":" + sAXParseException.getLineNumber() + " error: " + sAXParseException.getMessage());
            }

            @Override
            public void fatalError(SAXParseException sAXParseException) throws SAXException {
                ds.error("File: " + file.getName() + ":" + sAXParseException.getLineNumber() + " fatal: " + sAXParseException.getMessage());
            }
        });
        sAXReader.setEntityResolver((EntityResolver)new SimpleDTDEntityResolver(new File(file.getParentFile(), FilenameUtils.removeExtension((String)file.getName()) + ".dtd")));
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            Document document = sAXReader.read((InputStream)fileInputStream);
            Element element = document.getRootElement();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!"shortcut".equals(element2.getName())) continue;
                ClassId classId = null;
                String string = element2.attributeValue("classId");
                if (string != null) {
                    classId = ClassId.valueOf(string);
                }
                int n = Integer.parseInt(element2.attributeValue("slot", "0"));
                int n2 = Integer.parseInt(element2.attributeValue("page", "0"));
                String string2 = element2.attributeValue("type");
                int n3 = 0;
                if ("ITEM".equalsIgnoreCase(string2) || "TYPE_ITEM".equalsIgnoreCase(string2)) {
                    n3 = 1;
                } else if ("SKILL".equalsIgnoreCase(string2) || "TYPE_SKILL".equalsIgnoreCase(string2)) {
                    n3 = 2;
                } else if ("ACTION".equalsIgnoreCase(string2) || "TYPE_ACTION".equalsIgnoreCase(string2)) {
                    n3 = 3;
                } else {
                    throw new RuntimeException("Unknown short cut type");
                }
                int n4 = Integer.parseInt(element2.attributeValue("id", "0"));
                int n5 = Integer.parseInt(element2.attributeValue("level", "-1"));
                int n6 = Integer.parseInt(element2.attributeValue("characterType", "1"));
                ShortCut shortCut = new ShortCut(n, n2, n3, n4, n5, n6);
                if (classId == null) {
                    for (ClassId classId2 : ClassId.VALUES) {
                        ArrayList<ShortCut> arrayList = (ArrayList<ShortCut>)hashMap.get((Object)classId2);
                        if (arrayList == null) {
                            arrayList = new ArrayList<ShortCut>();
                            hashMap.put(classId2, arrayList);
                        }
                        arrayList.add(shortCut);
                    }
                    continue;
                }
                Object object = (List)hashMap.get((Object)classId);
                if (object == null) {
                    object = new ArrayList();
                    hashMap.put(classId, (List<ShortCut>)object);
                }
                object.add(shortCut);
            }
        }
        catch (Exception exception) {
            ds.warn("Exception: " + exception, (Throwable)exception);
        }
        finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (Exception exception) {}
            }
        }
        return hashMap;
    }
}
