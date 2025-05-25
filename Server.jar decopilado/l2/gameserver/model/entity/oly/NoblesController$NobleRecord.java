/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

public class NoblesController.NobleRecord {
    public int char_id;
    public int class_id;
    public String char_name;
    public int points_current;
    public int points_past;
    public int points_pre_past;
    public int class_free_cnt;
    public int class_based_cnt;
    public int team_cnt;
    public int comp_win;
    public int comp_loose;
    public int comp_done;
    public int comp_done_past;
    public int rank;

    private NoblesController.NobleRecord(int n, int n2, String string, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        this.char_id = n;
        this.class_id = n2;
        this.char_name = string;
        this.points_current = n3;
        this.points_past = n4;
        this.points_pre_past = n5;
        this.class_free_cnt = n6;
        this.class_based_cnt = n7;
        this.team_cnt = n8;
        this.comp_win = n9;
        this.comp_loose = n10;
        this.comp_done = n11;
        this.comp_done_past = n12;
        this.rank = 0;
    }
}
