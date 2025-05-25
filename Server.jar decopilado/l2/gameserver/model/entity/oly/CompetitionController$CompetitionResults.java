/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.data.StringHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.utils.TimeUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class CompetitionController.CompetitionResults {
    int char_id;
    int rival_id;
    String char_name;
    String rival_name;
    byte result;
    int char_class_id;
    int rival_class_id;
    int elapsed_time;
    CompetitionType type;
    long mtime;

    private CompetitionController.CompetitionResults(int n, int n2, String string, int n3, int n4, String string2, CompetitionType competitionType, byte by, int n5, long l) {
        this.char_id = n;
        this.char_class_id = n2;
        this.char_name = string;
        this.rival_id = n3;
        this.rival_name = string2;
        this.rival_class_id = n4;
        this.type = competitionType;
        this.result = by;
        this.elapsed_time = n5;
        this.mtime = l;
    }

    public String toString(Player player, MutableInt mutableInt, MutableInt mutableInt2, MutableInt mutableInt3) {
        String string = null;
        if (this.result == 0) {
            string = StringHolder.getInstance().getNotNull(player, "hero.history.tie");
        } else if (this.result > 0) {
            string = StringHolder.getInstance().getNotNull(player, "hero.history.win");
        } else if (this.result < 2) {
            string = StringHolder.getInstance().getNotNull(player, "hero.history.loss");
        }
        if (this.result > 0) {
            mutableInt.increment();
        } else if (this.result == 0) {
            mutableInt3.increment();
        } else if (this.result < 0) {
            mutableInt2.increment();
        }
        string = string.replace("%classId%", String.valueOf(this.rival_class_id));
        string = string.replace("%name%", this.rival_name);
        string = string.replace("%date%", TimeUtils.toHeroRecordFormat(this.mtime));
        string = string.replace("%time%", String.format("%02d:%02d", this.elapsed_time / 60, this.elapsed_time % 60));
        string = string.replace("%victory_count%", mutableInt.toString());
        string = string.replace("%tie_count%", mutableInt3.toString());
        string = string.replace("%loss_count%", mutableInt2.toString());
        return string;
    }
}
