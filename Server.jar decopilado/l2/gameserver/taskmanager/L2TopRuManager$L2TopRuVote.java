/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.gameserver.taskmanager.L2TopRuManager;

private class L2TopRuManager.L2TopRuVote {
    public long datetime;
    public String charname;
    public int count;
    public int char_obj_id = -1;
    public L2TopRuManager.L2TopRuVoteType type;

    public L2TopRuManager.L2TopRuVote(String string, String string2, String string3) throws Exception {
        this.datetime = b.parse(string).getTime() / 1000L;
        this.count = Byte.parseByte(string3);
        this.charname = string2;
        this.type = L2TopRuManager.L2TopRuVoteType.SMS;
    }

    public L2TopRuManager.L2TopRuVote(String string, String string2) throws Exception {
        this.datetime = b.parse(string).getTime() / 1000L;
        this.charname = string2;
        this.count = 1;
        this.type = L2TopRuManager.L2TopRuVoteType.WEB;
    }

    public String toString() {
        return this.charname + "-" + this.count + "[" + this.char_obj_id + "(" + this.datetime + "|" + this.type.name() + ")]";
    }
}
