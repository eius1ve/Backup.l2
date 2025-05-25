/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractDirParser
 *  l2.commons.data.xml.AbstractHolder
 *  l2.gameserver.Config
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.moveroute.MoveNode
 *  l2.gameserver.templates.moveroute.MoveRoute
 *  l2.gameserver.templates.moveroute.MoveRouteType
 *  org.dom4j.Element
 */
package parsers;

import java.io.File;
import l2.commons.data.xml.AbstractDirParser;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.Config;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.moveroute.MoveNode;
import l2.gameserver.templates.moveroute.MoveRoute;
import l2.gameserver.templates.moveroute.MoveRouteType;
import org.dom4j.Element;
import parsers.MoveRouteHolder;

public class MoveRouteParser
extends AbstractDirParser<MoveRouteHolder>
implements ScriptFile {
    private static MoveRouteParser a;

    public static MoveRouteParser getInstance() {
        if (a == null) {
            a = new MoveRouteParser();
            a.load();
        }
        return a;
    }

    public MoveRouteParser() {
        super((AbstractHolder)MoveRouteHolder.getInstance());
    }

    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "./data/superpointinfo");
    }

    public boolean isIgnored(File file) {
        return false;
    }

    public String getDTDFileName() {
        return "superpointinfo.dtd";
    }

    protected void readData(Element element) throws Exception {
        for (Element element2 : element.elements()) {
            String string = element2.attributeValue("name");
            MoveRouteType moveRouteType = MoveRouteType.valueOf((String)element2.attributeValue("type"));
            String string2 = element2.attributeValue("is_running");
            MoveRoute moveRoute = new MoveRoute(string, moveRouteType, string2 != null ? Boolean.parseBoolean(string2) : false);
            for (Element element3 : element2.elements()) {
                int n = Integer.parseInt(element3.attributeValue("x"));
                int n2 = Integer.parseInt(element3.attributeValue("y"));
                int n3 = Integer.parseInt(element3.attributeValue("z"));
                int n4 = Integer.parseInt(element3.attributeValue("social", "0"));
                long l = Long.parseLong(element3.attributeValue("delay", "0"));
                String string3 = element3.attributeValue("msg_addr");
                ChatType chatType = null;
                if (string3 != null) {
                    chatType = ChatType.valueOf((String)element3.attributeValue("chat_type"));
                }
                MoveNode moveNode = new MoveNode(n, n2, n3, string3, n4, l, chatType);
                moveRoute.getNodes().add(moveNode);
            }
            ((MoveRouteHolder)this.getHolder()).addRoute(moveRoute);
        }
    }

    public void onLoad() {
        MoveRouteParser.getInstance();
    }

    public void onReload() {
        MoveRouteParser.getInstance().reload();
    }

    public void onShutdown() {
    }
}
