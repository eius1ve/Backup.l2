/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.ArrayList;
import java.util.Collection;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.ExRedSky;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;

public class CursedWeapon {
    private final String cK;
    private String cL;
    private final int gB;
    private final int gC;
    private final int gD;
    private int gE;
    private int gF;
    private int gG;
    private int gH;
    private int gI;
    private int gJ;
    private int gK;
    private int gL;
    private int gM = 0;
    private int gN = 0;
    private int gO = 0;
    private CursedWeaponState a = CursedWeaponState.NONE;
    private Location _loc = null;
    private long aR = 0L;
    private ItemInstance b = null;
    private int gP = 0;
    private Player _player = null;

    public CursedWeapon(int n, int n2, String string) {
        this.cK = string;
        this.gB = n;
        this.gD = n2;
        this.gC = SkillTable.getInstance().getMaxLevel(this.gD);
    }

    public void initWeapon() {
        this.aV();
        this.setState(CursedWeaponState.NONE);
        this.aR = 0L;
        this.b = null;
        this.gM = 0;
    }

    public void create(NpcInstance npcInstance, Player player) {
        this.b = ItemFunctions.createItem(this.gB);
        if (this.b != null) {
            this.aV();
            this.setState(CursedWeaponState.DROPPED);
            if (this.aR == 0L) {
                this.aR = System.currentTimeMillis() + (long)(this.getRndDuration() * 60000);
            }
            this.b.dropToTheGround(npcInstance, Location.findPointToStay(npcInstance, 100));
            this._loc = this.b.getLoc();
            this.b.setDropTime(0L);
            ExRedSky exRedSky = new ExRedSky(10);
            Earthquake earthquake = new Earthquake(player.getLoc(), 30, 12);
            for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                player2.sendPacket(exRedSky, earthquake);
            }
        }
    }

    public boolean dropIt(NpcInstance npcInstance, Player player, Player player2) {
        ItemInstance itemInstance;
        if (Rnd.chance(this.gF)) {
            return false;
        }
        Player player3 = this.getOnlineOwner();
        if (player3 == null) {
            if (player2 == null) {
                return false;
            }
            player3 = player2;
        }
        if ((itemInstance = player3.getInventory().removeItemByItemId(this.gB, 1L)) == null) {
            return false;
        }
        player3.setKarma(this.gN, true);
        player3.setPkKills(this.gO);
        player3.setCursedWeaponEquippedId(0);
        player3.setTransformation(0);
        player3.setTransformationName(null);
        player3.validateLocation(0);
        Skill skill = SkillTable.getInstance().getInfo(this.gD, player3.getSkillLevel(this.gD));
        if (skill != null) {
            for (Skill.AddedSkill addedSkill : skill.getAddedSkills()) {
                player3.removeSkillById(addedSkill.id);
            }
        }
        player3.removeSkillById(this.gD);
        player3.abortAttack(true, false);
        this.aV();
        this.setState(CursedWeaponState.DROPPED);
        itemInstance.dropToTheGround((Playable)player3, Location.findPointToStay(player3, 100));
        this._loc = itemInstance.getLoc();
        itemInstance.setDropTime(0L);
        this.b = itemInstance;
        player3.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_DROPPED_S1).addItemName(itemInstance.getItemId()));
        player3.broadcastUserInfo(true, new UserInfoType[0]);
        player3.broadcastPacket(new Earthquake(player3.getLoc(), 30, 12));
        return true;
    }

    private void R(Player player) {
        for (Skill skill : this.a(player)) {
            player.addSkill(skill, false);
            player._transformationSkills.put(skill.getId(), skill);
        }
        player.sendSkillList();
    }

    private Collection<Skill> a(Player player) {
        int n = 1 + this.gM / this.gL;
        if (n > this.gC) {
            n = this.gC;
        }
        Skill skill = SkillTable.getInstance().getInfo(this.gD, n);
        ArrayList<Skill> arrayList = new ArrayList<Skill>();
        arrayList.add(skill);
        for (Skill.AddedSkill addedSkill : skill.getAddedSkills()) {
            arrayList.add(SkillTable.getInstance().getInfo(addedSkill.id, addedSkill.level));
        }
        return arrayList;
    }

    public boolean reActivate() {
        if (this.getTimeLeft() <= 0L) {
            if (this.getPlayerId() != 0) {
                this.setState(CursedWeaponState.ACTIVATED);
            }
            return false;
        }
        if (this.getPlayerId() == 0) {
            if (this._loc == null || (this.b = ItemFunctions.createItem(this.gB)) == null) {
                return false;
            }
            this.b.dropMe(null, this._loc);
            this.b.setDropTime(0L);
            this.setState(CursedWeaponState.DROPPED);
        } else {
            this.setState(CursedWeaponState.ACTIVATED);
        }
        return true;
    }

    public void activate(Player player, ItemInstance itemInstance) {
        if (this.isDropped() || this.getPlayerId() != player.getObjectId()) {
            this.setPlayerId(player.getObjectId());
            this.setPlayerKarma(player.getKarma());
            this.setPlayerPkKills(player.getPkKills());
        }
        this.setPlayer(player);
        this.setState(CursedWeaponState.ACTIVATED);
        player.leaveParty();
        if (player.isMounted()) {
            player.setMount(0, 0, 0);
        }
        this.b = itemInstance;
        player.getInventory().setPaperdollItem(7, null);
        player.getInventory().setPaperdollItem(5, null);
        player.getInventory().setPaperdollItem(5, this.b);
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EQUIPPED_YOUR_S1).addItemName(this.b.getItemId()));
        player.setTransformation(0);
        if (!Config.AUTO_FARM_ALLOW_FOR_CURSED_WEAPON) {
            player.getFarmSystem().stopFarmTask();
        }
        player.setCursedWeaponEquippedId(this.gB);
        player.setTransformation(this.gJ);
        player.setTransformationName(this.cL);
        player.setTransformationTemplate(this.gK);
        player.setKarma(9999999, true);
        player.setPkKills(this.gM);
        if (this.aR == 0L) {
            this.aR = System.currentTimeMillis() + (long)(this.getRndDuration() * 60000);
        }
        this.R(player);
        player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
        player.setCurrentCp(player.getMaxCp());
        player.broadcastUserInfo(true, new UserInfoType[0]);
    }

    public void increaseKills() {
        Player player = this.getOnlineOwner();
        if (player == null) {
            return;
        }
        ++this.gM;
        player.setPkKills(this.gM);
        player.updateStats();
        if (this.gM % this.gL == 0 && this.gM <= this.gL * (this.gC - 1)) {
            this.R(player);
        }
        this.aR -= (long)(this.gI * 60000);
    }

    public void setDisapearChance(int n) {
        this.gF = n;
    }

    public void giveSkillAndUpdateStats() {
        Player player = this.getOnlineOwner();
        if (player == null) {
            return;
        }
        if (this.gM <= this.gL * (this.gC - 1)) {
            this.R(player);
        }
        player.updateStats();
    }

    public void setDropRate(int n) {
        this.gE = n;
    }

    public void setDurationMin(int n) {
        this.gG = n;
    }

    public void setDurationMax(int n) {
        this.gH = n;
    }

    public void setDurationLost(int n) {
        this.gI = n;
    }

    public void setStageKills(int n) {
        this.gL = n;
    }

    public void setTransformationId(int n) {
        this.gJ = n;
    }

    public int getTransformationId() {
        return this.gJ;
    }

    public void setTransformationTemplateId(int n) {
        this.gK = n;
    }

    public void setTransformationName(String string) {
        this.cL = string;
    }

    public void setNbKills(int n) {
        this.gM = n;
    }

    public void setPlayerId(int n) {
        this.gP = n;
    }

    public void setPlayerKarma(int n) {
        this.gN = n;
    }

    public void setPlayerPkKills(int n) {
        this.gO = n;
    }

    public void setState(CursedWeaponState cursedWeaponState) {
        this.a = cursedWeaponState;
    }

    public void setEndTime(long l) {
        this.aR = l;
    }

    public void setPlayer(Player player) {
        this._player = player;
    }

    private void aV() {
        this._player = null;
        this.gP = 0;
        this.gN = 0;
        this.gO = 0;
    }

    public void setItem(ItemInstance itemInstance) {
        this.b = itemInstance;
    }

    public void setLoc(Location location) {
        this._loc = location;
    }

    public CursedWeaponState getState() {
        return this.a;
    }

    public boolean isActivated() {
        return this.getState() == CursedWeaponState.ACTIVATED;
    }

    public boolean isDropped() {
        return this.getState() == CursedWeaponState.DROPPED;
    }

    public long getEndTime() {
        return this.aR;
    }

    public String getName() {
        return this.cK;
    }

    public int getItemId() {
        return this.gB;
    }

    public ItemInstance getItem() {
        return this.b;
    }

    public int getSkillId() {
        return this.gD;
    }

    public int getDropRate() {
        return this.gE;
    }

    public int getPlayerId() {
        return this.gP;
    }

    public Player getPlayer() {
        return this._player;
    }

    public int getPlayerKarma() {
        return this.gN;
    }

    public int getPlayerPkKills() {
        return this.gO;
    }

    public int getNbKills() {
        return this.gM;
    }

    public int getStageKills() {
        return this.gL;
    }

    public Location getLoc() {
        return this._loc;
    }

    public int getRndDuration() {
        if (this.gG > this.gH) {
            this.gH = 2 * this.gG;
        }
        return Rnd.get(this.gG, this.gH);
    }

    public boolean isActive() {
        return this.isActivated() || this.isDropped();
    }

    public int getLevel() {
        return Math.min(1 + this.gM / this.gL, this.gC);
    }

    public long getTimeLeft() {
        return this.aR - System.currentTimeMillis();
    }

    public Location getWorldPosition() {
        if (this.isActivated()) {
            Player player = this.getOnlineOwner();
            if (player != null) {
                return player.getLoc();
            }
        } else if (this.isDropped() && this.b != null) {
            return this.b.getLoc();
        }
        return null;
    }

    public Player getOnlineOwner() {
        Player player = this.getPlayer();
        return player != null && player.isOnline() ? player : null;
    }

    public static final class CursedWeaponState
    extends Enum<CursedWeaponState> {
        public static final /* enum */ CursedWeaponState NONE = new CursedWeaponState();
        public static final /* enum */ CursedWeaponState ACTIVATED = new CursedWeaponState();
        public static final /* enum */ CursedWeaponState DROPPED = new CursedWeaponState();
        private static final /* synthetic */ CursedWeaponState[] a;

        public static CursedWeaponState[] values() {
            return (CursedWeaponState[])a.clone();
        }

        public static CursedWeaponState valueOf(String string) {
            return Enum.valueOf(CursedWeaponState.class, string);
        }

        private static /* synthetic */ CursedWeaponState[] a() {
            return new CursedWeaponState[]{NONE, ACTIVATED, DROPPED};
        }

        static {
            a = CursedWeaponState.a();
        }
    }
}
