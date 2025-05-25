/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.model.actor.instances.player;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterBlockDAO;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.actor.instances.player.CharacterBlockListEntry;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExBlockAddResult;
import l2.gameserver.network.l2.s2c.ExBlockDetailInfo;
import l2.gameserver.network.l2.s2c.ExBlockRemoveResult;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.apache.commons.lang3.StringUtils;

public class CharacterBlockList {
    private final Player g;
    private Map<Integer, CharacterBlockListEntry> aO = Collections.emptyMap();

    public CharacterBlockList(Player player) {
        this.g = player;
    }

    public void restore() {
        Map<Integer, CharacterBlockListEntry> map = CharacterBlockDAO.getInstance().select(this.g);
        this.aO = map;
    }

    public static boolean isInBlockList(String string, Player player) {
        Player player2 = World.getPlayer(string);
        if (player2 != null) {
            return player2.getBlockList().isBlocked(player);
        }
        int n = CharacterDAO.getInstance().getObjectIdByName(string);
        return n > 0 && CharacterBlockDAO.getInstance().fetchBlockListEntry(n, player.getObjectId()) != null;
    }

    public void addToBlockList(CharacterBlockListEntry characterBlockListEntry) {
        Collection<CharacterBlockListEntry> collection = this.getBlockList();
        if (characterBlockListEntry == null || this.g.getName().equalsIgnoreCase(characterBlockListEntry.getName()) || this.isInBlockList(characterBlockListEntry.getName()) || collection.size() > 511) {
            this.g.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_REGISTER_THE_USER_TO_YOUR_IGNORE_LIST);
            return;
        }
        this.aO.put(characterBlockListEntry.getTargetObjId(), characterBlockListEntry);
        CharacterBlockDAO.getInstance().insert(this.g, characterBlockListEntry);
        this.g.sendPacket(new IStaticPacket[]{new SystemMessage(SystemMsg.S1_HAS_BEEN_ADDED_TO_YOUR_IGNORE_LIST).addString(characterBlockListEntry.getName()), new ExBlockAddResult(characterBlockListEntry.getName())});
    }

    public void addToBlockList(String string) {
        CharacterBlockListEntry characterBlockListEntry;
        if (string == null || string.equalsIgnoreCase(this.g.getName()) || this.isInBlockList(string)) {
            this.g.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_REGISTER_THE_USER_TO_YOUR_IGNORE_LIST);
            return;
        }
        Player player = World.getPlayer(string);
        if (player != null) {
            if (player.isGM()) {
                this.g.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_IMPOSE_A_BLOCK_ON_A_GM);
                return;
            }
            characterBlockListEntry = new CharacterBlockListEntry(this.g.getObjectId(), player.getObjectId(), player.getName());
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_PLACED_YOU_ON_HISHER_IGNORE_LIST).addString(this.g.getName()));
        } else {
            int n = CharacterDAO.getInstance().getObjectIdByName(string);
            if (n == 0) {
                this.g.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_REGISTER_THE_USER_TO_YOUR_IGNORE_LIST);
                return;
            }
            if (Config.gmlist.containsKey(n) && Config.gmlist.get((Object)Integer.valueOf((int)n)).IsGM) {
                this.g.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_IMPOSE_A_BLOCK_ON_A_GM);
                return;
            }
            characterBlockListEntry = new CharacterBlockListEntry(this.g.getObjectId(), n, string);
        }
        this.addToBlockList(characterBlockListEntry);
    }

    public void removeFromBlockList(String string) {
        CharacterBlockListEntry characterBlockListEntry = this.getEntryByName(string);
        if (characterBlockListEntry == null || characterBlockListEntry.getTargetObjId() == 0) {
            this.g.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_FAILED_TO_DELETE_THE_CHARACTER_FROM_YOUR_IGNORE_LIST);
            return;
        }
        this.aO.remove(characterBlockListEntry.getTargetObjId());
        this.g.sendPacket(new IStaticPacket[]{new SystemMessage(SystemMsg.S1_HAS_BEEN_REMOVED_FROM_YOUR_IGNORE_LIST).addString(characterBlockListEntry.getName()), new ExBlockRemoveResult(true, characterBlockListEntry.getName())});
        CharacterBlockDAO.getInstance().delete(this.g, characterBlockListEntry.getTargetObjId());
    }

    public boolean isBlocked(Player player) {
        return this.g.isBlockAll() || this.isInBlockList(player.getObjectId());
    }

    public boolean isInBlockList(Player player) {
        return this.isInBlockList(player.getObjectId());
    }

    public boolean isInBlockList(int n) {
        return this.aO.containsKey(n);
    }

    public boolean isInBlockList(String string) {
        return this.getEntryByName(string) != null;
    }

    public Collection<CharacterBlockListEntry> getBlockList() {
        return this.aO.values();
    }

    public CharacterBlockListEntry getEntryByName(String string) {
        for (CharacterBlockListEntry characterBlockListEntry : this.aO.values()) {
            if (!StringUtils.equalsIgnoreCase((CharSequence)string, (CharSequence)characterBlockListEntry.getName())) continue;
            return characterBlockListEntry;
        }
        return null;
    }

    public void updateMemo(String string, String string2) {
        string2 = StringUtils.truncate((String)StringUtils.trimToEmpty((String)string2), (int)50);
        CharacterBlockListEntry characterBlockListEntry = this.getEntryByName(string);
        if (characterBlockListEntry == null) {
            return;
        }
        if (StringUtils.equals((CharSequence)string2, (CharSequence)characterBlockListEntry.getMemo())) {
            return;
        }
        characterBlockListEntry.setMemo(string2);
        this.g.sendPacket((IStaticPacket)new ExBlockDetailInfo(characterBlockListEntry));
    }
}
