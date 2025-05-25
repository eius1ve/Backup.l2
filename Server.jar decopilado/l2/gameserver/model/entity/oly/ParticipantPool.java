/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.oly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParticipantPool {
    private static final Logger ca = LoggerFactory.getLogger(ParticipantPool.class);
    private static ParticipantPool a;
    private Map<CompetitionType, ArrayList<EntryRec>> bc;

    public static final ParticipantPool getInstance() {
        if (a == null) {
            a = new ParticipantPool();
        }
        return a;
    }

    private ParticipantPool() {
    }

    public void AllocatePools() {
        this.bc = new HashMap<CompetitionType, ArrayList<EntryRec>>();
        for (CompetitionType competitionType : CompetitionType.values()) {
            this.bc.put(competitionType, new ArrayList());
        }
        ca.info("OlyParticipantPool: Allocated " + this.bc.size() + " particiant pools.");
    }

    public void FreePools() {
        if (this.bc != null) {
            this.bc.clear();
        }
        ca.info("OlyParticipantPool: pools cleared.");
    }

    public boolean isEnough(CompetitionType competitionType, int n, boolean bl) {
        switch (competitionType) {
            case CLASS_FREE: {
                return this.bc.get((Object)competitionType).size() >= Config.OLY_MIN_CF_START;
            }
            case TEAM_CLASS_FREE: {
                return this.bc.get((Object)competitionType).size() >= Config.OLY_MIN_TB_START;
            }
            case CLASS_INDIVIDUAL: {
                int n2 = 0;
                for (EntryRec entryRec : this.bc.get((Object)competitionType)) {
                    if (entryRec.cls_id != n) continue;
                    ++n2;
                }
                return n2 >= Config.OLY_MIN_CB_START;
            }
            case CLASS_TYPE_INDIVIDUAL: {
                int n3 = 0;
                for (EntryRec entryRec : this.bc.get((Object)competitionType)) {
                    ClassId classId = ClassId.getClassById(entryRec.cls_id);
                    if (classId == null || classId.isMage() != bl) continue;
                    ++n3;
                }
                return n3 >= Config.OLY_MIN_CB_START;
            }
        }
        return false;
    }

    public int getRandomIndex(CompetitionType competitionType, int n, int n2, boolean bl) {
        ArrayList<EntryRec> arrayList = this.bc.get((Object)competitionType);
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>(arrayList.size() / 2);
        EntryRec entryRec = arrayList.get(n);
        if (entryRec == null) {
            return -1;
        }
        for (int i = 0; i < arrayList.size(); ++i) {
            ClassId classId;
            EntryRec entryRec2 = arrayList.get(i);
            if (entryRec2 == null || entryRec2 == entryRec || competitionType == CompetitionType.CLASS_INDIVIDUAL && n2 > 0 && n2 != entryRec2.cls_id || competitionType == CompetitionType.CLASS_TYPE_INDIVIDUAL && ((classId = ClassId.getClassById(entryRec2.cls_id)) == null || classId.isMage() != bl)) continue;
            arrayList2.add(i);
        }
        if (arrayList2.isEmpty()) {
            return -1;
        }
        return (Integer)Rnd.get(arrayList2);
    }

    public int getNearestIndex(CompetitionType competitionType, int n, int n2, boolean bl) {
        EntryRec entryRec;
        int n3;
        ArrayList<EntryRec> arrayList = this.bc.get((Object)competitionType);
        EntryRec entryRec2 = arrayList.get(n);
        if (entryRec2 == null) {
            return -1;
        }
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MIN_VALUE;
        for (n3 = 0; n3 < n; ++n3) {
            int n6;
            ClassId classId;
            entryRec = arrayList.get(n3);
            if (entryRec == null || competitionType == CompetitionType.CLASS_INDIVIDUAL && n2 > 0 && n2 != entryRec.cls_id || competitionType == CompetitionType.CLASS_TYPE_INDIVIDUAL && ((classId = ClassId.getClassById(entryRec.cls_id)) == null || classId.isMage() != bl) || (n6 = Math.abs(entryRec2.average - entryRec.average)) >= n4) continue;
            n5 = n3;
            n4 = n6;
        }
        for (n3 = n + 1; n3 < arrayList.size(); ++n3) {
            int n7;
            ClassId classId;
            entryRec = arrayList.get(n3);
            if (entryRec == null || competitionType == CompetitionType.CLASS_INDIVIDUAL && n2 > 0 && n2 != entryRec.cls_id || competitionType == CompetitionType.CLASS_TYPE_INDIVIDUAL && ((classId = ClassId.getClassById(entryRec.cls_id)) == null || classId.isMage() != bl) || (n7 = Math.abs(entryRec2.average - entryRec.average)) >= n4) continue;
            n5 = n3;
            n4 = n7;
        }
        return n5;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createEntry(CompetitionType competitionType, Player[] playerArray) {
        ArrayList<EntryRec> arrayList;
        if (playerArray == null || playerArray.length == 0) {
            return;
        }
        ArrayList<EntryRec> arrayList2 = arrayList = this.bc.get((Object)competitionType);
        synchronized (arrayList2) {
            this.bc.get((Object)competitionType).add(new EntryRec(playerArray));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Player[][] retrieveEntrys(CompetitionType competitionType, int n, boolean bl) {
        this.a(competitionType);
        ArrayList<EntryRec> arrayList = this.bc.get((Object)competitionType);
        int n2 = -1;
        long l = Long.MIN_VALUE;
        int n3 = -1;
        Player[][] playerArray = null;
        ArrayList<EntryRec> arrayList2 = arrayList;
        synchronized (arrayList2) {
            for (int i = 0; i < arrayList.size(); ++i) {
                ClassId classId;
                EntryRec entryRec = arrayList.get(i);
                if (entryRec == null || competitionType == CompetitionType.CLASS_INDIVIDUAL && n > 0 && n != entryRec.cls_id || competitionType == CompetitionType.CLASS_TYPE_INDIVIDUAL && ((classId = ClassId.getClassById(entryRec.cls_id)) == null || classId.isMage() != bl) || entryRec.reg_time <= l) continue;
                n2 = i;
                l = entryRec.reg_time;
            }
            if (n2 < 0) {
                return null;
            }
            int n4 = n3 = Config.OLY_PARTICIPANT_TYPE_SELECTION ? this.getRandomIndex(competitionType, n2, n, bl) : this.getNearestIndex(competitionType, n2, n, bl);
            if (n3 < 0) {
                return null;
            }
            playerArray = new Player[][]{Util.GetPlayersFromStoredIds(arrayList.remove((int)n2).sids), Util.GetPlayersFromStoredIds(arrayList.remove((int)n3).sids)};
            arrayList.trimToSize();
        }
        return playerArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeEntryByPlayer(CompetitionType competitionType, Player player) {
        ArrayList<EntryRec> arrayList;
        long l = player.getStoredId();
        ArrayList<EntryRec> arrayList2 = arrayList = this.bc.get((Object)competitionType);
        synchronized (arrayList2) {
            for (int i = 0; i < arrayList.size(); ++i) {
                EntryRec entryRec = arrayList.get(i);
                if (entryRec == null) continue;
                for (long l2 : entryRec.sids) {
                    if (l2 != l) continue;
                    arrayList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public CompetitionType getCompTypeOf(Player player) {
        long l = player.getStoredId();
        for (Map.Entry<CompetitionType, ArrayList<EntryRec>> entry : this.bc.entrySet()) {
            ArrayList<EntryRec> arrayList = entry.getValue();
            for (int i = 0; i < arrayList.size(); ++i) {
                EntryRec entryRec = arrayList.get(i);
                if (entryRec == null) continue;
                for (long l2 : entryRec.sids) {
                    if (l2 != l) continue;
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public boolean isRegistred(Player player) {
        if (!OlyController.getInstance().isRegAllowed()) {
            return false;
        }
        for (CompetitionType competitionType : this.bc.keySet()) {
            if (!this.isRegistred(competitionType, player)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isHWIDRegistred(String string) {
        Iterable<EntryRec> iterable;
        if (!OlyController.getInstance().isRegAllowed()) {
            return false;
        }
        LinkedList<EntryRec> linkedList = new LinkedList<EntryRec>();
        for (Map.Entry<CompetitionType, ArrayList<EntryRec>> object : this.bc.entrySet()) {
            ArrayList<EntryRec> arrayList = object.getValue();
            iterable = arrayList;
            synchronized (iterable) {
                linkedList.addAll(arrayList);
            }
        }
        for (EntryRec entryRec : linkedList) {
            for (int i = 0; i < entryRec.sids.length; ++i) {
                iterable = GameObjectsStorage.getAsPlayer(entryRec.sids[i]);
                if (iterable == null || ((Player)iterable).getNetConnection() == null || ((Player)iterable).getNetConnection().getHwid() == null || !string.equalsIgnoreCase(((Player)iterable).getNetConnection().getHwid())) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isIPRegistred(String string) {
        Iterable<EntryRec> iterable;
        if (!OlyController.getInstance().isRegAllowed()) {
            return false;
        }
        LinkedList<EntryRec> linkedList = new LinkedList<EntryRec>();
        for (Map.Entry<CompetitionType, ArrayList<EntryRec>> object : this.bc.entrySet()) {
            ArrayList<EntryRec> arrayList = object.getValue();
            iterable = arrayList;
            synchronized (iterable) {
                linkedList.addAll(arrayList);
            }
        }
        for (EntryRec entryRec : linkedList) {
            for (int i = 0; i < entryRec.sids.length; ++i) {
                iterable = GameObjectsStorage.getAsPlayer(entryRec.sids[i]);
                if (iterable == null || ((Player)iterable).getNetConnection() == null || ((Player)iterable).getNetConnection().getIpAddr() == null || ((Player)iterable).getNetConnection().getIpAddr() == "?.?.?.?" || !string.equalsIgnoreCase(((Player)iterable).getNetConnection().getIpAddr())) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isRegistred(CompetitionType competitionType, Player player) {
        if (!OlyController.getInstance().isRegAllowed()) {
            return false;
        }
        long l = player.getStoredId();
        ArrayList<EntryRec> arrayList = this.bc.get((Object)competitionType);
        for (EntryRec entryRec : arrayList) {
            if (entryRec == null) continue;
            for (long l2 : entryRec.sids) {
                if (l2 != l) continue;
                return true;
            }
        }
        return false;
    }

    public void broadcastToEntrys(CompetitionType competitionType, IStaticPacket iStaticPacket, int n) {
        ArrayList<EntryRec> arrayList = this.bc.get((Object)competitionType);
        for (EntryRec entryRec : arrayList) {
            if (entryRec == null) continue;
            for (long l : entryRec.sids) {
                Player player = GameObjectsStorage.getAsPlayer(l);
                if (player == null || n > 0 && player.getClassId().getId() != n) continue;
                player.sendPacket(iStaticPacket);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(CompetitionType competitionType) {
        ArrayList<EntryRec> arrayList;
        ArrayList<EntryRec> arrayList2 = arrayList = this.bc.get((Object)competitionType);
        synchronized (arrayList2) {
            ArrayList<Integer> arrayList3 = new ArrayList<Integer>();
            for (int i = 0; i < arrayList.size(); ++i) {
                if (this.a(arrayList.get(i))) continue;
                arrayList3.add(i);
            }
            Iterator iterator = arrayList3.iterator();
            while (iterator.hasNext()) {
                int n = (Integer)iterator.next();
                arrayList.remove(n);
            }
        }
    }

    public void onLogout(Player player) {
        if (!OlyController.getInstance().isRegAllowed()) {
            return;
        }
        CompetitionType competitionType = ParticipantPool.getInstance().getCompTypeOf(player);
        if (competitionType != null) {
            this.removeEntryByPlayer(competitionType, player);
        }
    }

    private boolean a(EntryRec entryRec) {
        return true;
    }

    public int getParticipantCount() {
        int n = 0;
        for (Map.Entry<CompetitionType, ArrayList<EntryRec>> entry : this.bc.entrySet()) {
            ArrayList<EntryRec> arrayList = entry.getValue();
            for (int i = 0; i < arrayList.size(); ++i) {
                EntryRec entryRec = arrayList.get(i);
                if (entryRec == null) continue;
                n += entryRec.sids.length;
            }
        }
        return n;
    }

    private class EntryRec {
        long[] sids;
        int average;
        long reg_time;
        int cls_id;

        public EntryRec(Player[] playerArray) {
            this.sids = new long[playerArray.length];
            this.cls_id = playerArray[0].getClassId().getId();
            int n = 0;
            for (int i = 0; i < playerArray.length; ++i) {
                this.sids[i] = playerArray[i].getStoredId();
                n += Math.max(0, NoblesController.getInstance().getPointsOf(playerArray[i].getObjectId()));
                OlyController.getInstance().incPartCount();
            }
            this.average = n / playerArray.length;
            this.reg_time = System.currentTimeMillis();
        }
    }
}
