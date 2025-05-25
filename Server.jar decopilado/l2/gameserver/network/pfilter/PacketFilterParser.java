/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.pfilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.network.l2.c2s.ValidatePosition;
import l2.gameserver.network.pfilter.Limit;
import l2.gameserver.network.pfilter.LimitAction;
import l2.gameserver.network.pfilter.PacketFilterHolder;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketFilterParser
extends AbstractFileParser<PacketFilterHolder> {
    private static final Logger df = LoggerFactory.getLogger(PacketFilterParser.class);
    private static final PacketFilterParser a = new PacketFilterParser();

    private PacketFilterParser() {
        super(PacketFilterHolder.getInstance());
    }

    public static PacketFilterParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File("config/packetfilter.xml");
    }

    @Override
    public String getDTDFileName() {
        return null;
    }

    private Limit a(Element element) throws Exception {
        int n = Integer.parseInt(element.attributeValue("count"));
        if (n < 0 || n > 15000) {
            throw new IllegalArgumentException("Field \"count\"=" + n + " out of [0 - 15000] range.");
        }
        int n2 = Integer.parseInt(element.attributeValue("perMs"));
        if (n2 < 10 || n2 > 250000) {
            throw new IllegalArgumentException("Field \"perMs\"=" + n2 + " out of [10 - 250000] range.");
        }
        ArrayList<LimitAction> arrayList = new ArrayList<LimitAction>();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if ("actionFailed".equalsIgnoreCase(element2.getName())) {
                arrayList.add(LimitAction.doActionFail());
                continue;
            }
            if ("kick".equalsIgnoreCase(element2.getName())) {
                arrayList.add(LimitAction.doKick());
                continue;
            }
            if ("drop".equalsIgnoreCase(element2.getName())) {
                arrayList.add(LimitAction.doDrop());
                continue;
            }
            if ("log".equalsIgnoreCase(element2.getName())) {
                String string = element2.attributeValue("format");
                arrayList.add(LimitAction.doLog(Objects.requireNonNull(string, "\"format\" is null")));
                continue;
            }
            if (!"msg".equalsIgnoreCase(element2.getName())) continue;
            String string = element2.attributeValue("text");
            arrayList.add(LimitAction.doMsg(Objects.requireNonNull(string, "\"text\" is null")));
        }
        boolean bl = false;
        for (LimitAction limitAction : arrayList) {
            if (!limitAction.dropPacket()) continue;
            bl = true;
        }
        return new Limit(arrayList, bl, n, n2);
    }

    private Limit b(Element element) throws Exception {
        Limit limit = null;
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"limit".equalsIgnoreCase(element2.getName())) continue;
            if (limit != null) {
                this._log.warn("PacketFilter: Limit redefined.");
            }
            limit = this.a(element2);
        }
        return limit;
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object;
            String string;
            Element element2 = (Element)iterator.next();
            if ("packetByOpcode".equalsIgnoreCase(element2.getName())) {
                int n = Integer.decode(element2.attributeValue("op"));
                if (n < 0 || n > 255) {
                    throw new IllegalArgumentException("Field \"op\"=" + n + " out of [0x00 - 0xff] range.");
                }
                String string2 = string = element2.attributeValue("exOp") != null ? Integer.decode(element2.attributeValue("exOp")) : null;
                if (string != null && ((Integer)((Object)string) < 0 || (Integer)((Object)string) > 65535)) {
                    throw new IllegalArgumentException("Field \"exOp\"=" + (Integer)((Object)string) + " out of [0x0000 - 0xffff] range.");
                }
                object = this.b(element2);
                if (object == null) {
                    throw string == null ? new IllegalStateException(String.format("Limit for opcode <0x%02x> undefined.", n)) : new IllegalStateException(String.format("Limit for opcodeEx <0x%02x:0x%04x> undefined.", n, string));
                }
                if (string == null) {
                    ((PacketFilterHolder)this.getHolder()).addByOpcodeLimit(n, (Limit)object);
                    continue;
                }
                ((PacketFilterHolder)this.getHolder()).addByOpcodeExLimit(n, (Integer)((Object)string), (Limit)object);
                continue;
            }
            if (!"packetByName".equalsIgnoreCase(element2.getName())) continue;
            String string3 = element2.attributeValue("name");
            string = ValidatePosition.class.getPackage().getName();
            object = string + "." + string3;
            Class<?> clazz = null;
            try {
                clazz = Class.forName((String)object);
            }
            catch (Exception exception) {
                throw new IllegalArgumentException("Field \"name\"=" + string3 + " unprocessable.", exception);
            }
            Limit limit = this.b(element2);
            if (limit == null) {
                throw new IllegalStateException("Limit for packet \"" + string3 + "\" undefined.");
            }
            ((PacketFilterHolder)this.getHolder()).addByPacketNameLimit(clazz, limit);
        }
    }
}
