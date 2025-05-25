/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;

public static class PartySmallWindowAll.PartySmallWindowMemberInfo {
    public String _name;
    public String pet_Name;
    public int _id;
    public int curCp;
    public int maxCp;
    public int curHp;
    public int maxHp;
    public int curMp;
    public int maxMp;
    public int level;
    public int class_id;
    public int race_id;
    public int vitality;
    public int pet_id;
    public int pet_type;
    public int pet_NpcId;
    public int pet_curHp;
    public int pet_maxHp;
    public int pet_curMp;
    public int pet_maxMp;
    public int pet_level;

    public PartySmallWindowAll.PartySmallWindowMemberInfo(Player player) {
        this._name = player.getName();
        this._id = player.getObjectId();
        this.curCp = (int)player.getCurrentCp();
        this.maxCp = player.getMaxCp();
        this.curHp = (int)player.getCurrentHp();
        this.maxHp = player.getMaxHp();
        this.curMp = (int)player.getCurrentMp();
        this.maxMp = player.getMaxMp();
        this.level = player.getLevel();
        this.class_id = player.getClassId().getId();
        this.race_id = player.getRace().ordinal();
        this.vitality = (int)player.getVitality();
        Summon summon = player.getPet();
        if (summon != null) {
            this.pet_id = summon.getObjectId();
            this.pet_type = summon.getSummonType();
            this.pet_NpcId = summon.getNpcId() + 1000000;
            this.pet_Name = summon.getName();
            this.pet_curHp = (int)summon.getCurrentHp();
            this.pet_maxHp = summon.getMaxHp();
            this.pet_curMp = (int)summon.getCurrentMp();
            this.pet_maxMp = summon.getMaxMp();
            this.pet_level = summon.getLevel();
        } else {
            this.pet_id = 0;
        }
    }
}
