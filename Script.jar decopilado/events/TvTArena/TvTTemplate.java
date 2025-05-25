/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.entity.oly.HeroController
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.Revive
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 */
package events.TvTArena;

import java.util.ArrayList;
import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.Revive;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class TvTTemplate
extends Functions {
    private static int ct = 4357;
    private static String ag = "Silver Shilen";
    private static int cu = 12;
    private static boolean Z = false;
    private static boolean aa = true;
    private static boolean ab = false;
    protected int _managerId;
    protected String _className;
    protected Long _creatorId;
    protected NpcInstance _manager;
    protected int _status = 0;
    protected int _CharacterFound = 0;
    protected int _price = 10000;
    protected int _team1count = 1;
    protected int _team2count = 1;
    protected int _team1min = 1;
    protected int _team1max = 85;
    protected int _team2min = 1;
    protected int _team2max = 85;
    protected int _timeToStart = 10;
    protected boolean _timeOutTask;
    protected List<Location> _team1points;
    protected List<Location> _team2points;
    protected List<Long> _team1list;
    protected List<Long> _team2list;
    protected List<Long> _team1live;
    protected List<Long> _team2live;
    protected Zone _zone;
    protected ZoneListener _zoneListener;

    protected abstract void onLoad();

    protected abstract void onReload();

    public void template_stop() {
        if (this._status <= 0) {
            return;
        }
        this.sayToAll("\u0411\u043e\u0439 \u043f\u0440\u0435\u0440\u0432\u0430\u043d \u043f\u043e \u0442\u0435\u0445\u043d\u0438\u0447\u0435\u0441\u043a\u0438\u043c \u043f\u0440\u0438\u0447\u0438\u043d\u0430\u043c, \u0441\u0442\u0430\u0432\u043a\u0438 \u0432\u043e\u0437\u0432\u0440\u0430\u0449\u0435\u043d\u044b");
        this.unParalyzeTeams();
        this.ressurectPlayers();
        this.returnItemToTeams();
        this.healPlayers();
        this.removeBuff();
        this.teleportPlayersToSavedCoords();
        this.clearTeams();
        this._status = 0;
        this._timeOutTask = false;
    }

    public void template_create1(Player player) {
        if (this._status > 0) {
            this.show("\u0414\u043e\u0436\u0434\u0438\u0442\u0435\u0441\u044c \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0431\u043e\u044f", player);
            return;
        }
        if (player.getTeam() != TeamType.NONE) {
            this.show("\u0412\u044b \u0443\u0436\u0435 \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u044b", player);
            return;
        }
        this.show("scripts/events/TvTArena/" + this._managerId + "-1.htm", player);
    }

    public void template_register(Player player) {
        if (this._status == 0) {
            this.show("\u0411\u043e\u0439 \u043d\u0430 \u0434\u0430\u043d\u043d\u044b\u0439 \u043c\u043e\u043c\u0435\u043d\u0442 \u043d\u0435 \u0441\u043e\u0437\u0434\u0430\u043d", player);
            return;
        }
        if (this._status > 1) {
            this.show("\u0414\u043e\u0436\u0434\u0438\u0442\u0435\u0441\u044c \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0431\u043e\u044f", player);
            return;
        }
        if (player.getTeam() != TeamType.NONE) {
            this.show("\u0412\u044b \u0443\u0436\u0435 \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u044b", player);
            return;
        }
        this.show("scripts/events/TvTArena/" + this._managerId + "-3.htm", player);
    }

    public void template_check1(Player player, NpcInstance npcInstance, String[] stringArray) {
        if (stringArray.length != 8) {
            this.show("\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", player);
            return;
        }
        if (this._status > 0) {
            this.show("\u0414\u043e\u0436\u0434\u0438\u0442\u0435\u0441\u044c \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0431\u043e\u044f", player);
            return;
        }
        if (npcInstance == null || !npcInstance.isNpc()) {
            this.show("Hacker? :) " + npcInstance, player);
            return;
        }
        this._manager = npcInstance;
        try {
            this._price = Integer.valueOf(stringArray[0]);
            this._team1count = Integer.valueOf(stringArray[1]);
            this._team2count = Integer.valueOf(stringArray[2]);
            this._team1min = Integer.valueOf(stringArray[3]);
            this._team1max = Integer.valueOf(stringArray[4]);
            this._team2min = Integer.valueOf(stringArray[5]);
            this._team2max = Integer.valueOf(stringArray[6]);
            this._timeToStart = Integer.valueOf(stringArray[7]);
        }
        catch (Exception exception) {
            this.show("\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435", player);
            return;
        }
        if (this._price < 1 || this._price > 500) {
            this.show("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u0430\u044f \u0441\u0442\u0430\u0432\u043a\u0430", player);
            return;
        }
        if (this._team1count < 1 || this._team1count > cu || this._team2count < 1 || this._team2count > cu) {
            this.show("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439 \u0440\u0430\u0437\u043c\u0435\u0440 \u043a\u043e\u043c\u0430\u043d\u0434\u044b", player);
            return;
        }
        if (this._team1min < 1 || this._team1min > 86 || this._team2min < 1 || this._team2min > 86 || this._team1max < 1 || this._team1max > 86 || this._team2max < 1 || this._team2max > 86 || this._team1min > this._team1max || this._team2min > this._team2max) {
            this.show("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439 \u0443\u0440\u043e\u0432\u0435\u043d\u044c", player);
            return;
        }
        if (player.getLevel() < this._team1min || player.getLevel() > this._team1max) {
            this.show("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439 \u0443\u0440\u043e\u0432\u0435\u043d\u044c", player);
            return;
        }
        if (this._timeToStart < 1 || this._timeToStart > 10) {
            this.show("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e\u0435 \u0432\u0440\u0435\u043c\u044f", player);
            return;
        }
        if (TvTTemplate.getItemCount((Playable)player, (int)ct) < (long)this._price) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        this._creatorId = player.getStoredId();
        TvTTemplate.removeItem((Playable)player, (int)ct, (long)this._price);
        player.setTeam(TeamType.BLUE);
        this._status = 1;
        this._team1list.clear();
        this._team2list.clear();
        this._team1live.clear();
        this._team2live.clear();
        this._team1list.add(player.getStoredId());
        this.sayToAll(player.getName() + " \u0441\u043e\u0437\u0434\u0430\u043b \u0431\u043e\u0439 " + this._team1count + "\u0445" + this._team2count + ", " + this._team1min + "-" + this._team1max + "lv vs " + this._team2min + "-" + this._team2max + "lv, \u0441\u0442\u0430\u0432\u043a\u0430 " + this._price + " " + ag + ", \u043d\u0430\u0447\u0430\u043b\u043e \u0447\u0435\u0440\u0435\u0437 " + this._timeToStart + " \u043c\u0438\u043d");
        TvTTemplate.executeTask((String)("events.TvTArena." + this._className), (String)"announce", (Object[])new Object[0], (long)60000L);
    }

    public void template_register_check(Player player) {
        int n;
        if (this._status == 0) {
            this.show("\u0411\u043e\u0439 \u043d\u0430 \u0434\u0430\u043d\u043d\u044b\u0439 \u043c\u043e\u043c\u0435\u043d\u0442 \u043d\u0435 \u0441\u043e\u0437\u0434\u0430\u043d", player);
            return;
        }
        if (this._status > 1) {
            this.show("\u0414\u043e\u0436\u0434\u0438\u0442\u0435\u0441\u044c \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f \u0431\u043e\u044f", player);
            return;
        }
        if (this._team1list.contains(player.getStoredId()) || this._team2list.contains(player.getStoredId())) {
            this.show("\u0412\u044b \u0443\u0436\u0435 \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u044b", player);
            return;
        }
        if (player.getTeam() != TeamType.NONE) {
            this.show("\u0412\u044b \u0443\u0436\u0435 \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u044b", player);
            return;
        }
        if (TvTTemplate.getItemCount((Playable)player, (int)ct) < (long)this._price) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        int n2 = this._team1list.size();
        if (n2 > (n = this._team2list.size())) {
            String string = null;
            if (this.a(2, player) != null && (string = this.a(1, player)) != null) {
                this.show(string, player);
            }
        } else if (n2 < n) {
            String string = null;
            if (this.a(1, player) != null && (string = this.a(2, player)) != null) {
                this.show(string, player);
            }
        } else {
            int n3 = Rnd.get((int)1, (int)2);
            String string = null;
            if (this.a(n3, player) != null && (string = this.a(n3 == 1 ? 2 : 1, player)) != null) {
                this.show(string, player);
            }
        }
    }

    private String a(int n, Player player) {
        if (n == 1) {
            if (player.getLevel() < this._team1min || player.getLevel() > this._team1max) {
                return "\u0412\u044b \u043d\u0435 \u043f\u043e\u0434\u0445\u043e\u0434\u0438\u0442\u0435 \u043f\u043e \u0443\u0440\u043e\u0432\u043d\u044e";
            }
            if (this._team1list.size() >= this._team1count) {
                return "\u041a\u043e\u043c\u0430\u043d\u0434\u0430 1 \u043f\u0435\u0440\u0435\u043f\u043e\u043b\u043d\u0435\u043d\u0430";
            }
            this.a(1, player);
            return null;
        }
        if (player.getLevel() < this._team2min || player.getLevel() > this._team2max) {
            return "\u0412\u044b \u043d\u0435 \u043f\u043e\u0434\u0445\u043e\u0434\u0438\u0442\u0435 \u043f\u043e \u0443\u0440\u043e\u0432\u043d\u044e";
        }
        if (this._team2list.size() >= this._team2count) {
            return "\u041a\u043e\u043c\u0430\u043d\u0434\u0430 2 \u043f\u0435\u0440\u0435\u043f\u043e\u043b\u043d\u0435\u043d\u0430";
        }
        this.a(2, player);
        return null;
    }

    private void a(int n, Player player) {
        TvTTemplate.removeItem((Playable)player, (int)ct, (long)this._price);
        if (n == 1) {
            this._team1list.add(player.getStoredId());
            player.setTeam(TeamType.BLUE);
            this.sayToAll(player.getName() + " \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043b\u0441\u044f \u0437\u0430 1 \u043a\u043e\u043c\u0430\u043d\u0434\u0443");
        } else {
            this._team2list.add(player.getStoredId());
            player.setTeam(TeamType.RED);
            this.sayToAll(player.getName() + " \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043b\u0441\u044f \u0437\u0430 2 \u043a\u043e\u043c\u0430\u043d\u0434\u0443");
        }
        if (this._team1list.size() >= this._team1count && this._team2list.size() >= this._team2count) {
            this.sayToAll("\u041a\u043e\u043c\u0430\u043d\u0434\u044b \u0433\u043e\u0442\u043e\u0432\u044b, \u0441\u0442\u0430\u0440\u0442 \u0447\u0435\u0440\u0435\u0437 1 \u043c\u0438\u043d\u0443\u0442\u0443.");
            this._timeToStart = 1;
        }
    }

    public void template_announce() {
        Player player = GameObjectsStorage.getAsPlayer((long)this._creatorId);
        if (this._status != 1 || player == null) {
            return;
        }
        if (this._timeToStart > 1) {
            --this._timeToStart;
            this.sayToAll(player.getName() + " \u0441\u043e\u0437\u0434\u0430\u043b \u0431\u043e\u0439 " + this._team1count + "\u0445" + this._team2count + ", " + this._team1min + "-" + this._team1max + "lv vs " + this._team2min + "-" + this._team2max + "lv, \u0441\u0442\u0430\u0432\u043a\u0430 " + this._price + " " + ag + ", \u043d\u0430\u0447\u0430\u043b\u043e \u0447\u0435\u0440\u0435\u0437 " + this._timeToStart + " \u043c\u0438\u043d");
            TvTTemplate.executeTask((String)("events.TvTArena." + this._className), (String)"announce", (Object[])new Object[0], (long)60000L);
        } else if (this._team2list.size() > 0) {
            this.sayToAll("\u041f\u043e\u0434\u0433\u043e\u0442\u043e\u0432\u043a\u0430 \u043a \u0431\u043e\u044e");
            TvTTemplate.executeTask((String)("events.TvTArena." + this._className), (String)"prepare", (Object[])new Object[0], (long)5000L);
        } else {
            this.sayToAll("\u0411\u043e\u0439 \u043d\u0435 \u0441\u043e\u0441\u0442\u043e\u044f\u043b\u0441\u044f, \u043d\u0435\u0442 \u043f\u0440\u043e\u0442\u0438\u0432\u043d\u0438\u043a\u043e\u0432");
            this._status = 0;
            this.returnItemToTeams();
            this.clearTeams();
        }
    }

    public void template_prepare() {
        if (this._status != 1) {
            return;
        }
        this._status = 2;
        for (Player player : this.c(this._team1list)) {
            if (player.isDead()) continue;
            this._team1live.add(player.getStoredId());
        }
        for (Player player : this.c(this._team2list)) {
            if (player.isDead()) continue;
            this._team2live.add(player.getStoredId());
        }
        if (!this.checkTeams()) {
            return;
        }
        this.saveBackCoords();
        this.clearArena();
        this.ressurectPlayers();
        this.removeBuff();
        this.healPlayers();
        this.paralyzeTeams();
        this.teleportTeamsToArena();
        this.sayToAll("\u0411\u043e\u0439 \u043d\u0430\u0447\u043d\u0435\u0442\u0441\u044f \u0447\u0435\u0440\u0435\u0437 30 \u0441\u0435\u043a\u0443\u043d\u0434");
        TvTTemplate.executeTask((String)("events.TvTArena." + this._className), (String)"start", (Object[])new Object[0], (long)30000L);
    }

    public void template_start() {
        if (this._status != 2) {
            return;
        }
        if (!this.checkTeams()) {
            return;
        }
        this.sayToAll("Go!!!");
        this.unParalyzeTeams();
        this._status = 3;
        TvTTemplate.executeTask((String)("events.TvTArena." + this._className), (String)"timeOut", (Object[])new Object[0], (long)180000L);
        this._timeOutTask = true;
    }

    public void clearArena() {
        for (Creature creature : this._zone.getObjects()) {
            if (creature == null || !creature.isPlayable()) continue;
            ((Playable)creature).teleToLocation(this._zone.getSpawn());
        }
    }

    public boolean checkTeams() {
        if (this._team1live.isEmpty()) {
            this.teamHasLost(1);
            return false;
        }
        if (this._team2live.isEmpty()) {
            this.teamHasLost(2);
            return false;
        }
        return true;
    }

    public void saveBackCoords() {
        for (Player player : this.c(this._team1list)) {
            player.setVar("TvTArena_backCoords", player.getX() + " " + player.getY() + " " + player.getZ() + " " + player.getReflectionId(), -1L);
        }
        for (Player player : this.c(this._team2list)) {
            player.setVar("TvTArena_backCoords", player.getX() + " " + player.getY() + " " + player.getZ() + " " + player.getReflectionId(), -1L);
        }
    }

    public void teleportPlayersToSavedCoords() {
        String[] stringArray;
        String string;
        for (Player player : this.c(this._team1list)) {
            try {
                string = player.getVar("TvTArena_backCoords");
                if (string == null || string.equals("") || (stringArray = string.split(" ")).length != 4) continue;
                player.teleToLocation(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]));
                player.unsetVar("TvTArena_backCoords");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        for (Player player : this.c(this._team2list)) {
            try {
                string = player.getVar("TvTArena_backCoords");
                if (string == null || string.equals("") || (stringArray = string.split(" ")).length != 4) continue;
                player.teleToLocation(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]));
                player.unsetVar("TvTArena_backCoords");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void healPlayers() {
        for (Player player : this.c(this._team1list)) {
            player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
        }
        for (Player player : this.c(this._team2list)) {
            player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
        }
    }

    public void ressurectPlayers() {
        for (Player player : this.c(this._team1list)) {
            if (!player.isDead()) continue;
            player.restoreExp();
            player.setCurrentHp((double)player.getMaxHp(), true);
            player.setCurrentMp((double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
            player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
        }
        for (Player player : this.c(this._team2list)) {
            if (!player.isDead()) continue;
            player.restoreExp();
            player.setCurrentHp((double)player.getMaxHp(), true);
            player.setCurrentMp((double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
            player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
        }
    }

    public void removeBuff() {
        Summon summon;
        for (Player player : this.c(this._team1list)) {
            if (player == null) continue;
            try {
                if (player.isCastingNow()) {
                    player.abortCast(true, true);
                }
                if (!aa && player.getClan() != null) {
                    for (Skill skill : player.getClan().getAllSkills()) {
                        player.removeSkill(skill, false);
                    }
                }
                if (!ab && player.isHero()) {
                    HeroController.removeSkills((Player)player);
                }
                if (!Z) {
                    player.getEffectList().stopAllEffects();
                    if (player.getPet() != null) {
                        summon = player.getPet();
                        summon.getEffectList().stopAllEffects();
                        if (summon.isPet()) {
                            summon.unSummon();
                        }
                    }
                    if (player.getAgathionId() > 0) {
                        player.setAgathion(0);
                    }
                }
                player.sendSkillList();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        for (Player player : this.c(this._team2list)) {
            if (player == null) continue;
            try {
                if (player.isCastingNow()) {
                    player.abortCast(true, true);
                }
                if (!aa && player.getClan() != null) {
                    for (Skill skill : player.getClan().getAllSkills()) {
                        player.removeSkill(skill, false);
                    }
                }
                if (!ab && player.isHero()) {
                    HeroController.removeSkills((Player)player);
                }
                if (!Z) {
                    player.getEffectList().stopAllEffects();
                    if (player.getPet() != null) {
                        summon = player.getPet();
                        summon.getEffectList().stopAllEffects();
                        if (summon.isPet()) {
                            summon.unSummon();
                        }
                    }
                    if (player.getAgathionId() > 0) {
                        player.setAgathion(0);
                    }
                }
                player.sendSkillList();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void backBuff() {
        for (Player player : this.c(this._team1list)) {
            if (player == null) continue;
            try {
                player.getEffectList().stopAllEffects();
                if (!aa && player.getClan() != null) {
                    for (Skill skill : player.getClan().getAllSkills()) {
                        if (skill.getMinPledgeClass() > player.getPledgeClass()) continue;
                        player.addSkill(skill, false);
                    }
                }
                if (!ab && player.isHero()) {
                    HeroController.addSkills((Player)player);
                }
                player.sendSkillList();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        for (Player player : this.c(this._team2list)) {
            if (player == null) continue;
            try {
                player.getEffectList().stopAllEffects();
                if (!aa && player.getClan() != null) {
                    for (Skill skill : player.getClan().getAllSkills()) {
                        if (skill.getMinPledgeClass() > player.getPledgeClass()) continue;
                        player.addSkill(skill, false);
                    }
                }
                if (!ab && player.isHero()) {
                    HeroController.addSkills((Player)player);
                }
                player.sendSkillList();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void paralyzeTeams() {
        Skill skill = SkillTable.getInstance().getInfo(4515, 1);
        for (Player player : this.c(this._team1list)) {
            player.getEffectList().stopEffect(1411);
            skill.getEffects((Creature)player, (Creature)player, false, false);
            if (player.getPet() == null) continue;
            skill.getEffects((Creature)player, (Creature)player.getPet(), false, false);
        }
        for (Player player : this.c(this._team2list)) {
            player.getEffectList().stopEffect(1411);
            skill.getEffects((Creature)player, (Creature)player, false, false);
            if (player.getPet() == null) continue;
            skill.getEffects((Creature)player, (Creature)player.getPet(), false, false);
        }
    }

    public void unParalyzeTeams() {
        for (Player player : this.c(this._team1list)) {
            player.getEffectList().stopEffect(4515);
            if (player.getPet() != null) {
                player.getPet().getEffectList().stopEffect(4515);
            }
            player.leaveParty();
        }
        for (Player player : this.c(this._team2list)) {
            player.getEffectList().stopEffect(4515);
            if (player.getPet() != null) {
                player.getPet().getEffectList().stopEffect(4515);
            }
            player.leaveParty();
        }
    }

    public void teleportTeamsToArena() {
        Integer n;
        Integer n2 = 0;
        for (Player player : this.c(this._team1live)) {
            TvTTemplate.unRide((Player)player);
            TvTTemplate.unSummonPet((Player)player, (boolean)true);
            player.teleToLocation(this._team1points.get(n2), ReflectionManager.DEFAULT);
            n = n2;
            n2 = n2 + 1;
        }
        n2 = 0;
        for (Player player : this.c(this._team2live)) {
            TvTTemplate.unRide((Player)player);
            TvTTemplate.unSummonPet((Player)player, (boolean)true);
            player.teleToLocation(this._team2points.get(n2), ReflectionManager.DEFAULT);
            n = n2;
            n2 = n2 + 1;
        }
    }

    public boolean playerHasLost(Player player) {
        if (player.getTeam() == TeamType.BLUE) {
            this._team1live.remove(player.getStoredId());
        } else {
            this._team2live.remove(player.getStoredId());
        }
        Skill skill = SkillTable.getInstance().getInfo(4515, 1);
        skill.getEffects((Creature)player, (Creature)player, false, false);
        return !this.checkTeams();
    }

    public void teamHasLost(Integer n) {
        if (n == 1) {
            this.sayToAll("\u041a\u043e\u043c\u0430\u043d\u0434\u0430 2 \u043f\u043e\u0431\u0435\u0434\u0438\u043b\u0430");
            this.payItemToTeam(2);
        } else {
            this.sayToAll("\u041a\u043e\u043c\u0430\u043d\u0434\u0430 1 \u043f\u043e\u0431\u0435\u0434\u0438\u043b\u0430");
            this.payItemToTeam(1);
        }
        this.unParalyzeTeams();
        this.backBuff();
        this.teleportPlayersToSavedCoords();
        this.ressurectPlayers();
        this.healPlayers();
        this.clearTeams();
        this._status = 0;
        this._timeOutTask = false;
    }

    public void template_timeOut() {
        if (this._timeOutTask && this._status == 3) {
            this.sayToAll("\u0412\u0440\u0435\u043c\u044f \u0438\u0441\u0442\u0435\u043a\u043b\u043e, \u043d\u0438\u0447\u044c\u044f!");
            this.returnItemToTeams();
            this.unParalyzeTeams();
            this.backBuff();
            this.teleportPlayersToSavedCoords();
            this.ressurectPlayers();
            this.healPlayers();
            this.clearTeams();
            this._status = 0;
            this._timeOutTask = false;
        }
    }

    public void payItemToTeam(Integer n) {
        if (n == 1) {
            for (Player player : this.c(this._team1list)) {
                TvTTemplate.addItem((Playable)player, (int)ct, (long)(this._price + this._team2list.size() * this._price / this._team1list.size()));
            }
        } else {
            for (Player player : this.c(this._team2list)) {
                TvTTemplate.addItem((Playable)player, (int)ct, (long)(this._price + this._team2list.size() * this._price / this._team1list.size()));
            }
        }
    }

    public void returnItemToTeams() {
        for (Player player : this.c(this._team1list)) {
            TvTTemplate.addItem((Playable)player, (int)ct, (long)this._price);
        }
        for (Player player : this.c(this._team2list)) {
            TvTTemplate.addItem((Playable)player, (int)ct, (long)this._price);
        }
    }

    public void clearTeams() {
        for (Player player : this.c(this._team1list)) {
            player.setTeam(TeamType.NONE);
        }
        for (Player player : this.c(this._team2list)) {
            player.setTeam(TeamType.NONE);
        }
        this._team1list.clear();
        this._team2list.clear();
        this._team1live.clear();
        this._team2live.clear();
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (this._status >= 2 && creature.isPlayer() && creature.getTeam() != TeamType.NONE && (this._team1list.contains(creature.getStoredId()) || this._team2list.contains(creature.getStoredId()))) {
            Player player = creature.getPlayer();
            Player player2 = creature2.getPlayer();
            if (player2 != null) {
                this.sayToAll(player2.getName() + " \u0443\u0431\u0438\u043b " + player.getName());
                if (player.getTeam() == player2.getTeam() || !this._team1list.contains(player2.getStoredId()) && !this._team2list.contains(player2.getStoredId())) {
                    this.sayToAll("\u041d\u0430\u0440\u0443\u0448\u0435\u043d\u0438\u0435 \u043f\u0440\u0430\u0432\u0438\u043b, \u0438\u0433\u0440\u043e\u043a " + player2.getName() + " \u043e\u0448\u0442\u0440\u0430\u0444\u043e\u0432\u0430\u043d \u043d\u0430 " + this._price + " " + ag);
                    TvTTemplate.removeItem((Playable)player2, (int)ct, (long)this._price);
                }
                this.playerHasLost(player);
            } else {
                this.sayToAll(player.getName() + " \u0443\u0431\u0438\u0442");
                this.playerHasLost(player);
            }
        }
    }

    public void onPlayerExit(Player player) {
        if (player != null && this._status > 0 && player.getTeam() != TeamType.NONE && (this._team1list.contains(player.getStoredId()) || this._team2list.contains(player.getStoredId()))) {
            switch (this._status) {
                case 1: {
                    this.removePlayer(player);
                    this.sayToAll(player.getName() + " \u0434\u0438\u0441\u043a\u0432\u0430\u043b\u0438\u0444\u0438\u0446\u0438\u0440\u043e\u0432\u0430\u043d");
                    if (player.getStoredId() != this._creatorId) break;
                    this.sayToAll("\u0411\u043e\u0439 \u043f\u0440\u0435\u0440\u0432\u0430\u043d, \u0441\u0442\u0430\u0432\u043a\u0438 \u0432\u043e\u0437\u0432\u0440\u0430\u0449\u0435\u043d\u044b");
                    this.returnItemToTeams();
                    this.backBuff();
                    this.teleportPlayersToSavedCoords();
                    this.unParalyzeTeams();
                    this.ressurectPlayers();
                    this.healPlayers();
                    this.clearTeams();
                    this.unParalyzeTeams();
                    this.clearTeams();
                    this._status = 0;
                    this._timeOutTask = false;
                    break;
                }
                case 2: {
                    this.removePlayer(player);
                    this.sayToAll(player.getName() + " \u0434\u0438\u0441\u043a\u0432\u0430\u043b\u0438\u0444\u0438\u0446\u0438\u0440\u043e\u0432\u0430\u043d");
                    this.checkTeams();
                    break;
                }
                case 3: {
                    this.removePlayer(player);
                    this.sayToAll(player.getName() + " \u0434\u0438\u0441\u043a\u0432\u0430\u043b\u0438\u0444\u0438\u0446\u0438\u0440\u043e\u0432\u0430\u043d");
                    this.checkTeams();
                }
            }
        }
    }

    public void onTeleport(Player player) {
        if (player != null && this._status > 1 && player.getTeam() != TeamType.NONE && player.isInZone(this._zone)) {
            this.onPlayerExit(player);
        }
    }

    private void removePlayer(Player player) {
        if (player != null) {
            this._team1list.remove(player.getStoredId());
            this._team2list.remove(player.getStoredId());
            this._team1live.remove(player.getStoredId());
            this._team2live.remove(player.getStoredId());
            player.setTeam(TeamType.NONE);
        }
    }

    private List<Player> c(List<Long> list) {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Long l : list) {
            Player player = GameObjectsStorage.getAsPlayer((long)l);
            if (player == null) continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    public void sayToAll(String string) {
        Announcements.getInstance().announceToAll(this._manager.getName() + ": " + string, ChatType.CRITICAL_ANNOUNCE);
    }

    public class TeleportTask
    extends RunnableImpl {
        Location loc;
        Creature target;

        public TeleportTask(Creature creature, Location location) {
            this.target = creature;
            this.loc = location;
            creature.block();
        }

        public void runImpl() throws Exception {
            this.target.unblock();
            this.target.teleToLocation(this.loc);
        }
    }

    public class ZoneListener
    implements OnZoneEnterLeaveListener {
        public void onZoneEnter(Zone zone, Creature creature) {
            Player player = creature.getPlayer();
            if (TvTTemplate.this._status >= 2 && player != null && !TvTTemplate.this._team1list.contains(player.getStoredId()) && !TvTTemplate.this._team2list.contains(player.getStoredId())) {
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TeleportTask(creature, TvTTemplate.this._zone.getSpawn())), 3000L);
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            Player player = creature.getPlayer();
            if (TvTTemplate.this._status >= 2 && player != null && player.getTeam() != TeamType.NONE && (TvTTemplate.this._team1list.contains(player.getStoredId()) || TvTTemplate.this._team2list.contains(player.getStoredId()))) {
                double d = PositionUtils.convertHeadingToDegree((int)creature.getHeading());
                double d2 = Math.toRadians(d - 90.0);
                int n = (int)((double)creature.getX() + 50.0 * Math.sin(d2));
                int n2 = (int)((double)creature.getY() - 50.0 * Math.cos(d2));
                int n3 = creature.getZ();
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TeleportTask(creature, new Location(n, n2, n3))), 3000L);
            }
        }
    }
}
