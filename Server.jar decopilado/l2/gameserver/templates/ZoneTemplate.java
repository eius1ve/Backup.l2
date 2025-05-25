/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.templates;

import java.util.List;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Territory;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.Race;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.StringUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ZoneTemplate {
    private final String go;
    private final Zone.ZoneType c;
    private final Territory k;
    private final boolean hc;
    private final boolean hd;
    private final List<Location> du;
    private final List<Location> dv;
    private final long dS;
    private final String gp;
    private final SystemMsg b;
    private final SystemMsg c;
    private final String gq;
    private final String gr;
    private final Race d;
    private final Zone.ZoneTarget a;
    private final Skill v;
    private final int FQ;
    private final int FR;
    private final int FS;
    private final int FT;
    private final SystemMsg d;
    private final int FU;
    private final int FV;
    private final double aC;
    private final double aD;
    private final double aE;
    private final int FW;
    private final String[] aQ;
    private final int FX;
    private final int FY;
    private final StatsSet g;

    public ZoneTemplate(StatsSet statsSet) {
        this.go = statsSet.getString("name");
        this.c = Zone.ZoneType.valueOf(statsSet.getString("type"));
        this.k = (Territory)statsSet.get("territory");
        int n = statsSet.getInteger("entering_message_no", -1);
        this.b = n == -1 ? null : SystemMsg.valueOf(n);
        n = statsSet.getInteger("leaving_message_no", -1);
        this.c = n == -1 ? null : SystemMsg.valueOf(n);
        this.gq = (String)StringUtils.defaultIfBlank((CharSequence)statsSet.getString("entering_custom_message", ""), (CharSequence)"");
        this.gr = (String)StringUtils.defaultIfBlank((CharSequence)statsSet.getString("leaving_custom_message", ""), (CharSequence)"");
        this.a = Zone.ZoneTarget.valueOf(statsSet.getString("target", "pc"));
        this.d = statsSet.getString("affect_race", "all").equals("all") ? null : Race.valueOf(statsSet.getString("affect_race"));
        String string = statsSet.getString("skill_name", null);
        Skill skill = null;
        if (string != null) {
            String[] stringArray = string.split("[\\s,;]+");
            skill = SkillTable.getInstance().getInfo(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]));
        }
        this.v = skill;
        this.FQ = statsSet.getInteger("skill_prob", 100);
        this.FR = statsSet.getInteger("initial_delay", 1);
        this.FS = statsSet.getInteger("unit_tick", 1);
        this.FT = statsSet.getInteger("random_time", 0);
        this.aC = statsSet.getDouble("move_bonus", 0.0);
        this.aD = statsSet.getDouble("hp_regen_bonus", 0.0);
        this.aE = statsSet.getDouble("mp_regen_bonus", 0.0);
        this.FU = statsSet.getInteger("damage_on_hp", 0);
        this.FV = statsSet.getInteger("damage_on_mp", 0);
        n = statsSet.getInteger("message_no", -1);
        this.d = n == -1 ? null : SystemMsg.valueOf(n);
        this.FW = statsSet.getInteger("eventId", 0);
        this.hc = statsSet.getBool("enabled", true);
        this.hd = statsSet.getBool("default", true);
        this.du = (List)statsSet.get("restart_points");
        this.dv = (List)statsSet.get("PKrestart_points");
        this.dS = statsSet.getLong("restart_time", 0L);
        this.gp = statsSet.getString("restart_allowed_time", "");
        string = (String)statsSet.get("blocked_actions");
        this.aQ = string != null ? string.split("[\\s,;]+") : null;
        this.FX = statsSet.getInteger("index", 0);
        this.FY = statsSet.getInteger("taxById", 0);
        this.g = statsSet;
    }

    public boolean isEnabled() {
        return this.hc;
    }

    public boolean isDefault() {
        return this.hd;
    }

    public String getName() {
        return this.go;
    }

    public Zone.ZoneType getType() {
        return this.c;
    }

    public Territory getTerritory() {
        return this.k;
    }

    public SystemMsg getEnteringMessage() {
        return this.b;
    }

    public SystemMsg getLeavingMessage() {
        return this.c;
    }

    public String getEnteringCustomMessage() {
        return this.gq;
    }

    public String getLeavingCustomMessage() {
        return this.gr;
    }

    public Skill getZoneSkill() {
        return this.v;
    }

    public int getSkillProb() {
        return this.FQ;
    }

    public int getInitialDelay() {
        return this.FR;
    }

    public int getUnitTick() {
        return this.FS;
    }

    public int getRandomTick() {
        return this.FT;
    }

    public Zone.ZoneTarget getZoneTarget() {
        return this.a;
    }

    public Race getAffectRace() {
        return this.d;
    }

    public String[] getBlockedActions() {
        return this.aQ;
    }

    public SystemMsg getDamageMessage() {
        return this.d;
    }

    public int getDamageOnHP() {
        return this.FU;
    }

    public int getDamageOnMP() {
        return this.FV;
    }

    public double getMoveBonus() {
        return this.aC;
    }

    public double getRegenBonusHP() {
        return this.aD;
    }

    public double getRegenBonusMP() {
        return this.aE;
    }

    public long getRestartTime() {
        return this.dS;
    }

    public String getRestartAllowedTime() {
        return this.gp;
    }

    public List<Location> getRestartPoints() {
        return this.du;
    }

    public List<Location> getPKRestartPoints() {
        return this.dv;
    }

    public int getIndex() {
        return this.FX;
    }

    public int getTaxById() {
        return this.FY;
    }

    public int getEventId() {
        return this.FW;
    }

    public MultiValueSet<String> getParams() {
        return this.g.clone();
    }
}
