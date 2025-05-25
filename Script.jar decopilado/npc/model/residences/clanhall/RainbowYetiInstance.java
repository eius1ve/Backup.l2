/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.events.GlobalEvent
 *  l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent
 *  l2.gameserver.model.entity.events.objects.CMGSiegeClanObject
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.NpcString
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 */
package npc.model.residences.clanhall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent;
import l2.gameserver.model.entity.events.objects.CMGSiegeClanObject;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;

public class RainbowYetiInstance
extends NpcInstance {
    private static final int Ip = 8035;
    private static final int Iq = 8036;
    private static final int Ir = 8037;
    private static final int Is = 8038;
    private static final int It = 8039;
    private static final int Iu = 8040;
    private static final int Iv = 8041;
    private static final int Iw = 8042;
    private static final int Ix = 8043;
    private static final int Iy = 8045;
    private static final int Iz = 8046;
    private static final int IA = 8047;
    private static final int IB = 8048;
    private static final int IC = 8049;
    private static final int ID = 8050;
    private static final int IE = 8051;
    private static final int IF = 8052;
    private static final int IG = 8053;
    private static final int IH = 8054;
    private static final int II = 8055;
    private static final Word[] a = new Word[8];
    private List<GameObject> dG = new ArrayList<GameObject>();
    private int IJ = -1;
    private Future<?> e = null;

    public RainbowYetiInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this._hasRandomWalk = false;
    }

    public void onSpawn() {
        super.onSpawn();
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)this.getEvent(ClanHallMiniGameEvent.class);
        if (clanHallMiniGameEvent == null) {
            return;
        }
        List list = World.getAroundPlayers((GameObject)this, (int)750, (int)100);
        for (Player player : list) {
            CMGSiegeClanObject cMGSiegeClanObject = (CMGSiegeClanObject)clanHallMiniGameEvent.getSiegeClan("attackers", player.getClan());
            if (cMGSiegeClanObject != null && cMGSiegeClanObject.getPlayers().contains(player.getObjectId())) continue;
            player.teleToLocation(((ClanHall)clanHallMiniGameEvent.getResidence()).getOtherRestartPoint());
        }
        this.e = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)new GenerateTask()), 10000L, 300000L);
    }

    public void onDelete() {
        super.onDelete();
        if (this.e != null) {
            this.e.cancel(false);
            this.e = null;
        }
        for (GameObject gameObject : this.dG) {
            gameObject.deleteMe();
        }
        this.dG.clear();
    }

    public void teleportFromArena() {
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)this.getEvent(ClanHallMiniGameEvent.class);
        if (clanHallMiniGameEvent == null) {
            return;
        }
        List list = World.getAroundPlayers((GameObject)this, (int)750, (int)100);
        for (Player player : list) {
            player.teleToLocation(((ClanHall)clanHallMiniGameEvent.getResidence()).getOtherRestartPoint());
        }
    }

    public void onBypassFeedback(Player player, String string) {
        if (!RainbowYetiInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equalsIgnoreCase("get")) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            boolean bl = true;
            if (this.IJ == -1) {
                bl = false;
            } else {
                Word word = a[this.IJ];
                for (int[] nArray : word.getItems()) {
                    if (player.getInventory().getCountOf(nArray[0]) >= (long)nArray[1]) continue;
                    bl = false;
                }
                if (bl) {
                    for (int[] nArray : word.getItems()) {
                        if (player.consumeItem(nArray[0], (long)nArray[1])) continue;
                        return;
                    }
                    int n = Rnd.get((int)100);
                    if (this.IJ >= 0 && this.IJ <= 5) {
                        if (n < 70) {
                            this.q(player, 8030);
                        } else if (n < 80) {
                            this.q(player, 8031);
                        } else if (n < 90) {
                            this.q(player, 8032);
                        } else {
                            this.q(player, 8033);
                        }
                    } else if (n < 10) {
                        this.q(player, 8030);
                    } else if (n < 40) {
                        this.q(player, 8031);
                    } else if (n < 70) {
                        this.q(player, 8032);
                    } else {
                        this.q(player, 8033);
                    }
                }
            }
            if (!bl) {
                npcHtmlMessage.setFile("residence2/clanhall/watering_manager002.htm");
            } else {
                npcHtmlMessage.setFile("residence2/clanhall/watering_manager004.htm");
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string.equalsIgnoreCase("see")) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/clanhall/watering_manager005.htm");
            if (this.IJ == -1) {
                npcHtmlMessage.replaceNpcString("%word%", NpcString.UNDECIDED, new Object[0]);
            } else {
                npcHtmlMessage.replace("%word%", a[this.IJ].getName());
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    private void q(Player player, int n) {
        ClanHallMiniGameEvent clanHallMiniGameEvent = (ClanHallMiniGameEvent)this.getEvent(ClanHallMiniGameEvent.class);
        if (clanHallMiniGameEvent == null) {
            return;
        }
        ItemInstance itemInstance = ItemFunctions.createItem((int)n);
        itemInstance.addEvent((GlobalEvent)clanHallMiniGameEvent);
        player.getInventory().addItem(itemInstance);
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems((ItemInstance)itemInstance));
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, "residence2/clanhall/watering_manager001.htm", new Object[0]);
    }

    public void addMob(GameObject gameObject) {
        this.dG.add(gameObject);
    }

    static {
        RainbowYetiInstance.a[0] = new Word("BABYDUCK", {8036, 2}, {8035, 1}, {8055, 1}, {8038, 1}, {8053, 1}, {8037, 1}, {8045, 1});
        RainbowYetiInstance.a[1] = new Word("ALBATROS", {8035, 2}, {8046, 1}, {8036, 1}, {8052, 1}, {8050, 1}, {8048, 1}, {8051, 1});
        RainbowYetiInstance.a[2] = new Word("PELICAN", {8049, 1}, {8039, 1}, {8046, 1}, {8043, 1}, {8037, 1}, {8035, 1}, {8047, 1});
        RainbowYetiInstance.a[3] = new Word("KINGFISHER", {8045, 1}, {8043, 1}, {8047, 1}, {8041, 1}, {8040, 1}, {8043, 1}, {8051, 1}, {8042, 1}, {8039, 1}, {8050, 1});
        RainbowYetiInstance.a[4] = new Word("CYGNUS", {8037, 1}, {8055, 1}, {8041, 1}, {8047, 1}, {8053, 1}, {8051, 1});
        RainbowYetiInstance.a[5] = new Word("TRITON", {8052, 2}, {8050, 1}, {8043, 1}, {8047, 1});
        RainbowYetiInstance.a[6] = new Word("RAINBOW", {8050, 1}, {8035, 1}, {8043, 1}, {8047, 1}, {8036, 1}, {8048, 1}, {8054, 1});
        RainbowYetiInstance.a[7] = new Word("SPRING", {8051, 1}, {8049, 1}, {8050, 1}, {8043, 1}, {8047, 1}, {8041, 1});
    }

    private class GenerateTask
    extends RunnableImpl {
        private GenerateTask() {
        }

        public void runImpl() throws Exception {
            RainbowYetiInstance.this.IJ = Rnd.get((int)a.length);
            Word word = a[RainbowYetiInstance.this.IJ];
            List list = World.getAroundPlayers((GameObject)RainbowYetiInstance.this, (int)750, (int)100);
            ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(word.getName(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
            for (Player player : list) {
                player.sendPacket((IStaticPacket)exShowScreenMessage);
            }
        }
    }

    private static class Word {
        private final String hg;
        private final int[][] m;

        public Word(String string, int[] ... nArray) {
            this.hg = string;
            this.m = nArray;
        }

        public String getName() {
            return this.hg;
        }

        public int[][] getItems() {
            return this.m;
        }
    }
}
