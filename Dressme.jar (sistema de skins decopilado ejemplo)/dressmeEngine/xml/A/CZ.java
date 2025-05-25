/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractFileParser
 *  l2.commons.data.xml.AbstractHolder
 *  l2.commons.lang.ArrayUtils
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.templates.item.ItemTemplate
 *  org.dom4j.Element
 */
package dressmeEngine.xml.A;

import dressmeEngine.data.DressMeWeaponData;
import dressmeEngine.util.A;
import dressmeEngine.xml.dataHolder.DressMeWeaponHolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.data.xml.AbstractFileParser;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.templates.item.ItemTemplate;
import org.dom4j.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public final class CZ
extends AbstractFileParser<DressMeWeaponHolder> {
    private final String B;
    private static final CZ A = new CZ();
    ItemTemplate[] allT = ItemHolder.getInstance().getAllTemplates();

    public static CZ getInstance() {
        return A;
    }

    private CZ() {
        super((AbstractHolder)DressMeWeaponHolder.getInstance());
        this.B = Config.DATAPACK_ROOT + "/data/xml/wardrobe/dressme/weapon.xml";
    }

    public File getXMLFile() {
        return new File(this.B);
    }

    protected void readData(Element paramElement) throws Exception {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setValidating(false);
        newInstance.setIgnoringComments(true);
        try {
            InputSource inputSource = new InputSource(new InputStreamReader((InputStream)new FileInputStream(this.getXMLFile()), "UTF-8"));
            inputSource.setEncoding("UTF-8");
            Node firstChild = newInstance.newDocumentBuilder().parse(inputSource).getFirstChild();
            while (firstChild != null) {
                if (firstChild.getNodeName().equalsIgnoreCase("list")) {
                    Node firstChild2 = firstChild.getFirstChild();
                    while (firstChild2 != null) {
                        if (firstChild2.getNodeName().equalsIgnoreCase("weapon")) {
                            int parseInt = Integer.parseInt(firstChild2.getAttributes().getNamedItem("id").getNodeValue());
                            if (ArrayUtils.valid((Object[])this.allT, (int)parseInt) == null) {
                                System.out.println("Template not exist: " + parseInt);
                            } else {
                                A a;
                                String nodeValue = firstChild2.getAttributes().getNamedItem("name").getNodeValue();
                                String nodeValue2 = firstChild2.getAttributes().getNamedItem("type").getNodeValue();
                                Node namedItem = firstChild2.getAttributes().getNamedItem("isBig");
                                Boolean valueOf = namedItem != null && Boolean.parseBoolean(namedItem.getNodeValue());
                                Node namedItem2 = firstChild2.getAttributes().getNamedItem("isMagic");
                                Boolean valueOf2 = namedItem2 != null && Boolean.parseBoolean(namedItem2.getNodeValue());
                                int i = 0;
                                long j = 0L;
                                A a2 = dressmeEngine.util.A.NONE;
                                try {
                                    a = dressmeEngine.util.A.valueOf(firstChild2.getAttributes().getNamedItem("rarity").getNodeValue());
                                }
                                catch (Exception e) {
                                    a = dressmeEngine.util.A.NONE;
                                }
                                Node firstChild3 = firstChild2.getFirstChild();
                                while (firstChild3 != null) {
                                    if ("price".equalsIgnoreCase(firstChild3.getNodeName())) {
                                        i = Integer.parseInt(firstChild3.getAttributes().getNamedItem("id").getNodeValue());
                                        j = Long.parseLong(firstChild3.getAttributes().getNamedItem("count").getNodeValue());
                                    }
                                    firstChild3 = firstChild3.getNextSibling();
                                }
                                ((DressMeWeaponHolder)this.getHolder()).addWeapon(new DressMeWeaponData(parseInt, nodeValue, nodeValue2, valueOf, valueOf2, i, j, a));
                            }
                        }
                        firstChild2 = firstChild2.getNextSibling();
                    }
                }
                firstChild = firstChild.getNextSibling();
            }
        }
        catch (Exception e2) {
            this._log.warn(String.valueOf(((Object)((Object)this)).getClass().getSimpleName()) + ": Error: " + e2);
        }
    }

    public String getDTDFileName() {
        return null;
    }
}
