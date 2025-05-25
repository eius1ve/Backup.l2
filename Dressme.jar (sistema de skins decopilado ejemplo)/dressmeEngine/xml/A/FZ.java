/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractFileParser
 *  l2.commons.data.xml.AbstractHolder
 *  l2.gameserver.Config
 *  org.dom4j.Element
 */
package dressmeEngine.xml.A;

import dressmeEngine.data.DressMeCloakData;
import dressmeEngine.util.A;
import dressmeEngine.xml.dataHolder.DressMeCloakHolder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.data.xml.AbstractFileParser;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.Config;
import org.dom4j.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public final class FZ
extends AbstractFileParser<DressMeCloakHolder> {
    private final String B = Config.DATAPACK_ROOT + "/data/xml/wardrobe/dressme/cloak.xml";
    private static final FZ A = new FZ();

    public static FZ getInstance() {
        return A;
    }

    private FZ() {
        super((AbstractHolder)DressMeCloakHolder.getInstance());
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
                        if (firstChild2.getNodeName().equalsIgnoreCase("cloak")) {
                            A a;
                            int parseInt = Integer.parseInt(firstChild2.getAttributes().getNamedItem("number").getNodeValue());
                            int parseInt2 = Integer.parseInt(firstChild2.getAttributes().getNamedItem("id").getNodeValue());
                            String nodeValue = firstChild2.getAttributes().getNamedItem("name").getNodeValue();
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
                            ((DressMeCloakHolder)this.getHolder()).addCloak(new DressMeCloakData(parseInt, parseInt2, nodeValue, i, j, a));
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
