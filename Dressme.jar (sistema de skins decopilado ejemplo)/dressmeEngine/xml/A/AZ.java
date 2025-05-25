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

import dressmeEngine.data.DressMeArmorData;
import dressmeEngine.util.A;
import dressmeEngine.xml.dataHolder.DressMeArmorHolder;
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

public final class AZ
extends AbstractFileParser<DressMeArmorHolder> {
    private static final AZ APARSER = new AZ();
    ItemTemplate[] allT = ItemHolder.getInstance().getAllTemplates();

    public static AZ getInstance() {
        return APARSER;
    }

    private AZ() {
        super((AbstractHolder)DressMeArmorHolder.getInstance());
    }

    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT + "/data/xml/wardrobe/dressme/armor.xml");
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
                        int parseInt;
                        if (firstChild2.getNodeName().equalsIgnoreCase("dress") && ArrayUtils.valid((Object[])this.allT, (int)(parseInt = Integer.parseInt(firstChild2.getAttributes().getNamedItem("id").getNodeValue()))) != null) {
                            A a;
                            String nodeValue = firstChild2.getAttributes().getNamedItem("name").getNodeValue();
                            String nodeValue2 = firstChild2.getAttributes().getNamedItem("type").getNodeValue();
                            int i = 0;
                            long j = 0L;
                            int i2 = 0;
                            int i3 = 0;
                            int i4 = 0;
                            int i5 = 0;
                            Boolean bool = false;
                            A a2 = A.NONE;
                            try {
                                a = A.valueOf(firstChild2.getAttributes().getNamedItem("rarity").getNodeValue());
                            }
                            catch (Exception e) {
                                a = A.NONE;
                            }
                            Node firstChild3 = firstChild2.getFirstChild();
                            while (firstChild3 != null) {
                                if ("set".equalsIgnoreCase(firstChild3.getNodeName())) {
                                    i2 = Integer.parseInt(firstChild3.getAttributes().getNamedItem("chest").getNodeValue());
                                    i3 = Integer.parseInt(firstChild3.getAttributes().getNamedItem("legs").getNodeValue());
                                    i4 = Integer.parseInt(firstChild3.getAttributes().getNamedItem("gloves").getNodeValue());
                                    i5 = Integer.parseInt(firstChild3.getAttributes().getNamedItem("feet").getNodeValue());
                                    Node namedItem = firstChild3.getAttributes().getNamedItem("isSuit");
                                    bool = namedItem != null && Boolean.parseBoolean(namedItem.getNodeValue());
                                }
                                if ("price".equalsIgnoreCase(firstChild3.getNodeName())) {
                                    i = Integer.parseInt(firstChild3.getAttributes().getNamedItem("id").getNodeValue());
                                    j = Long.parseLong(firstChild3.getAttributes().getNamedItem("count").getNodeValue());
                                }
                                firstChild3 = firstChild3.getNextSibling();
                            }
                            ((DressMeArmorHolder)this.getHolder()).addDress(new DressMeArmorData(parseInt, nodeValue, nodeValue2, i2, i3, i4, i5, i, j, bool, a));
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
