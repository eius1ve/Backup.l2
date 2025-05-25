/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.base;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public class BaseStats
extends Enum<BaseStats> {
    public static final /* enum */ BaseStats STR = new BaseStats(){

        @Override
        public final int getStat(Creature creature) {
            return creature == null ? 1 : creature.getSTR();
        }

        @Override
        public final double calcBonus(Creature creature) {
            return creature == null ? 1.0 : b[creature.getSTR()];
        }

        @Override
        public final double calcChanceMod(Creature creature) {
            return Math.min(2.0 - Math.sqrt(this.calcBonus(creature)), 1.0);
        }
    };
    public static final /* enum */ BaseStats INT = new BaseStats(){

        @Override
        public final int getStat(Creature creature) {
            return creature == null ? 1 : creature.getINT();
        }

        @Override
        public final double calcBonus(Creature creature) {
            return creature == null ? 1.0 : c[creature.getINT()];
        }
    };
    public static final /* enum */ BaseStats DEX = new BaseStats(){

        @Override
        public final int getStat(Creature creature) {
            return creature == null ? 1 : creature.getDEX();
        }

        @Override
        public final double calcBonus(Creature creature) {
            return creature == null ? 1.0 : d[creature.getDEX()];
        }
    };
    public static final /* enum */ BaseStats WIT = new BaseStats(){

        @Override
        public final int getStat(Creature creature) {
            return creature == null ? 1 : creature.getWIT();
        }

        @Override
        public final double calcBonus(Creature creature) {
            return creature == null ? 1.0 : e[creature.getWIT()];
        }
    };
    public static final /* enum */ BaseStats CON = new BaseStats(){

        @Override
        public final int getStat(Creature creature) {
            return creature == null ? 1 : creature.getCON();
        }

        @Override
        public final double calcBonus(Creature creature) {
            return creature == null ? 1.0 : f[creature.getCON()];
        }
    };
    public static final /* enum */ BaseStats MEN = new BaseStats(){

        @Override
        public final int getStat(Creature creature) {
            return creature == null ? 1 : creature.getMEN();
        }

        @Override
        public final double calcBonus(Creature creature) {
            return creature == null ? 1.0 : g[creature.getMEN()];
        }
    };
    public static final /* enum */ BaseStats NONE = new BaseStats();
    public static final BaseStats[] VALUES;
    protected static final Logger _log;
    private static final int kB = 100;
    private static final double[] b;
    private static final double[] c;
    private static final double[] d;
    private static final double[] e;
    private static final double[] f;
    private static final double[] g;
    private static final /* synthetic */ BaseStats[] a;

    public static BaseStats[] values() {
        return (BaseStats[])a.clone();
    }

    public static BaseStats valueOf(String string) {
        return Enum.valueOf(BaseStats.class, string);
    }

    public int getStat(Creature creature) {
        return 1;
    }

    public double calcBonus(Creature creature) {
        return 1.0;
    }

    public double calcChanceMod(Creature creature) {
        return 2.0 - Math.sqrt(this.calcBonus(creature));
    }

    public static final BaseStats valueOfXml(String string) {
        string = string.intern();
        for (BaseStats baseStats : VALUES) {
            if (!baseStats.toString().equalsIgnoreCase(string)) continue;
            if (baseStats == NONE) {
                return null;
            }
            return baseStats;
        }
        throw new NoSuchElementException("Unknown name '" + string + "' for enum BaseStats");
    }

    private static /* synthetic */ BaseStats[] a() {
        return new BaseStats[]{STR, INT, DEX, WIT, CON, MEN, NONE};
    }

    static {
        a = BaseStats.a();
        VALUES = BaseStats.values();
        _log = LoggerFactory.getLogger(BaseStats.class);
        b = new double[100];
        c = new double[100];
        d = new double[100];
        e = new double[100];
        f = new double[100];
        g = new double[100];
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setIgnoringComments(true);
        File file = new File(Config.DATAPACK_ROOT, "data/attribute_bonus.xml");
        Document document = null;
        try {
            document = documentBuilderFactory.newDocumentBuilder().parse(file);
        }
        catch (SAXException sAXException) {
            _log.error("", (Throwable)sAXException);
        }
        catch (IOException iOException) {
            _log.error("", (Throwable)iOException);
        }
        catch (ParserConfigurationException parserConfigurationException) {
            _log.error("", (Throwable)parserConfigurationException);
        }
        if (document != null) {
            for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    double d;
                    String string;
                    Node node3;
                    if (node2.getNodeName().equalsIgnoreCase("str_bonus")) {
                        for (node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            string = node3.getNodeName();
                            if (!string.equalsIgnoreCase("set")) continue;
                            int n = Integer.valueOf(node3.getAttributes().getNamedItem("attribute").getNodeValue());
                            d = Integer.valueOf(node3.getAttributes().getNamedItem("val").getNodeValue()).intValue();
                            BaseStats.b[n] = (100.0 + d) / 100.0;
                        }
                    }
                    if (node2.getNodeName().equalsIgnoreCase("int_bonus")) {
                        for (node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            string = node3.getNodeName();
                            if (!string.equalsIgnoreCase("set")) continue;
                            int n = Integer.valueOf(node3.getAttributes().getNamedItem("attribute").getNodeValue());
                            d = Integer.valueOf(node3.getAttributes().getNamedItem("val").getNodeValue()).intValue();
                            BaseStats.c[n] = (100.0 + d) / 100.0;
                        }
                    }
                    if (node2.getNodeName().equalsIgnoreCase("con_bonus")) {
                        for (node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            string = node3.getNodeName();
                            if (!string.equalsIgnoreCase("set")) continue;
                            int n = Integer.valueOf(node3.getAttributes().getNamedItem("attribute").getNodeValue());
                            d = Integer.valueOf(node3.getAttributes().getNamedItem("val").getNodeValue()).intValue();
                            BaseStats.f[n] = (100.0 + d) / 100.0;
                        }
                    }
                    if (node2.getNodeName().equalsIgnoreCase("men_bonus")) {
                        for (node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            string = node3.getNodeName();
                            if (!string.equalsIgnoreCase("set")) continue;
                            int n = Integer.valueOf(node3.getAttributes().getNamedItem("attribute").getNodeValue());
                            d = Integer.valueOf(node3.getAttributes().getNamedItem("val").getNodeValue()).intValue();
                            BaseStats.g[n] = (100.0 + d) / 100.0;
                        }
                    }
                    if (node2.getNodeName().equalsIgnoreCase("dex_bonus")) {
                        for (node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            string = node3.getNodeName();
                            if (!string.equalsIgnoreCase("set")) continue;
                            int n = Integer.valueOf(node3.getAttributes().getNamedItem("attribute").getNodeValue());
                            d = Integer.valueOf(node3.getAttributes().getNamedItem("val").getNodeValue()).intValue();
                            BaseStats.d[n] = (100.0 + d) / 100.0;
                        }
                    }
                    if (!node2.getNodeName().equalsIgnoreCase("wit_bonus")) continue;
                    for (node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                        string = node3.getNodeName();
                        if (!string.equalsIgnoreCase("set")) continue;
                        int n = Integer.valueOf(node3.getAttributes().getNamedItem("attribute").getNodeValue());
                        d = Integer.valueOf(node3.getAttributes().getNamedItem("val").getNodeValue()).intValue();
                        BaseStats.e[n] = (100.0 + d) / 100.0;
                    }
                }
            }
        }
    }
}
