/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.model.GWAutoAnnounce;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class AutoAnnounce
implements Runnable {
    private static final Logger bm = LoggerFactory.getLogger((String)AutoAnnounce.class.getName());
    private static AutoAnnounce a;
    static Map<Integer, GWAutoAnnounce> _lists;

    public static AutoAnnounce getInstance() {
        if (a == null) {
            a = new AutoAnnounce();
        }
        return a;
    }

    public void reload() {
        a = new AutoAnnounce();
    }

    public AutoAnnounce() {
        _lists = new HashMap<Integer, GWAutoAnnounce>();
        bm.info("AutoAnnounce: Initializing");
        this.load();
        bm.info("AutoAnnounce: Loaded " + (_lists.size() - 1) + " announce.");
    }

    private void load() {
        try {
            Object object;
            Object object2;
            Object object3;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            File file = new File("./config/autoannounce.xml");
            if (!file.exists()) {
                if (Config.DEBUG) {
                    System.out.println("AutoAnnounce: NO FILE");
                }
                return;
            }
            Document document = documentBuilderFactory.newDocumentBuilder().parse(file);
            int n = 0;
            if (n == 0) {
                object3 = new ArrayList();
                object2 = new GWAutoAnnounce(n);
                int n2 = 0;
                ((ArrayList)object3).add("" + n2);
                object = "Own1";
                String string = "Own2";
                ((ArrayList)object3).add((String)object + string);
                ((GWAutoAnnounce)object2).setAnnounce(0, 0, (ArrayList<String>)object3);
                _lists.put(n, (GWAutoAnnounce)object2);
                ++n;
            }
            for (object3 = document.getFirstChild(); object3 != null; object3 = object3.getNextSibling()) {
                if (!"list".equalsIgnoreCase(object3.getNodeName())) continue;
                for (object2 = object3.getFirstChild(); object2 != null; object2 = object2.getNextSibling()) {
                    boolean bl;
                    if (!"announce".equalsIgnoreCase(object2.getNodeName())) continue;
                    ArrayList<String> arrayList = new ArrayList<String>();
                    object = object2.getAttributes();
                    int n3 = Integer.parseInt(object.getNamedItem("delay").getNodeValue());
                    int n4 = Integer.parseInt(object.getNamedItem("repeat").getNodeValue());
                    try {
                        bl = Boolean.parseBoolean(object.getNamedItem("is_screen_message").getNodeValue());
                    }
                    catch (Exception exception) {
                        bl = false;
                    }
                    GWAutoAnnounce gWAutoAnnounce = new GWAutoAnnounce(n);
                    gWAutoAnnounce.setScreenAnnounce(bl);
                    for (Node node = object2.getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (!"message".equalsIgnoreCase(node.getNodeName())) continue;
                        arrayList.add(String.valueOf(node.getAttributes().getNamedItem("text").getNodeValue()));
                    }
                    gWAutoAnnounce.setAnnounce(n3, n4, arrayList);
                    _lists.put(n, gWAutoAnnounce);
                    ++n;
                }
            }
            if (Config.DEBUG) {
                System.out.println("AutoAnnounce: OK");
            }
        }
        catch (Exception exception) {
            bm.error("AutoAnnounce: Error parsing autoannounce.xml file. " + exception);
        }
    }

    @Override
    public void run() {
        if (_lists.size() <= 1) {
            return;
        }
        for (int i = 1; i < _lists.size(); ++i) {
            GWAutoAnnounce gWAutoAnnounce = _lists.get(i);
            if (!gWAutoAnnounce.canAnnounce()) continue;
            ArrayList<String> arrayList = gWAutoAnnounce.getMessage();
            for (String string : arrayList) {
                if (!gWAutoAnnounce.isScreenAnnounce()) {
                    Announcements.getInstance().announceToAll(string);
                    continue;
                }
                int n = 3000 + string.length() * 100;
                ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(string, n, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false);
                for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                    player.sendPacket((IStaticPacket)exShowScreenMessage);
                }
            }
            _lists.get(i).updateRepeat();
        }
    }

    public static String getRevision() {
        if (_lists.size() == 0) {
            return "";
        }
        return _lists.get(0).getMessage().get(0);
    }

    public static String getOwnerName() {
        if (_lists.size() == 0) {
            return "";
        }
        return _lists.get(0).getMessage().get(1);
    }
}
