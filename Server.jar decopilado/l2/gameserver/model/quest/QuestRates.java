/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.quest;

import java.util.Collections;
import java.util.List;
import l2.gameserver.utils.Strings;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestRates {
    private static final Logger cv = LoggerFactory.getLogger(QuestRates.class);
    private final int pq;
    private double M;
    private double N;
    private double O;
    private double P;
    private long cK;
    private double Q;
    private long cL;
    private List<Pair<Integer, Long>> bL;

    public QuestRates(int n) {
        this.pq = n;
        this.M = 1.0;
        this.N = 1.0;
        this.O = 1.0;
        this.P = 1.0;
        this.Q = 1.0;
        this.cK = 0L;
        this.cL = 0L;
        this.bL = Collections.emptyList();
    }

    public void updateParam(String string, String string2) {
        if (string.equalsIgnoreCase("Drop") || string.equalsIgnoreCase("DropRate")) {
            this.setDropRate(Double.parseDouble(string2));
        } else if (string.equalsIgnoreCase("Reward") || string.equalsIgnoreCase("RewardRate")) {
            this.setRewardRate(Double.parseDouble(string2));
        } else if (string.equalsIgnoreCase("RewardAdena") || string.equalsIgnoreCase("RewardAdenaRate")) {
            this.setRewardAdenaRate(Double.parseDouble(string2));
        } else if (string.equalsIgnoreCase("Exp") || string.equalsIgnoreCase("ExpRate")) {
            this.setExpRate(Double.parseDouble(string2));
        } else if (string.equalsIgnoreCase("Sp") || string.equalsIgnoreCase("SpRate")) {
            this.setSpRate(Double.parseDouble(string2));
        } else if (string.equalsIgnoreCase("ExtraReward")) {
            this.setExtraReward(Strings.parseIntLongPairs(string2));
        } else if (string.equalsIgnoreCase("ExpAdd")) {
            this.setExpAdd(Long.parseLong(string2));
        } else if (string.equalsIgnoreCase("SpAdd")) {
            this.setSpAdd(Long.parseLong(string2));
        } else {
            throw new IllegalArgumentException("Unknown param \"" + string + "\"");
        }
    }

    public double getDropRate() {
        return this.M;
    }

    public void setDropRate(double d) {
        this.M = d;
    }

    public void setRewardAdenaRate(double d) {
        this.O = d;
    }

    public double getRewardAdenaRate() {
        return this.O;
    }

    public double getRewardRate() {
        return this.N;
    }

    public void setRewardRate(double d) {
        this.N = d;
    }

    public double getExpRate() {
        return this.P;
    }

    public void setExpRate(double d) {
        this.P = d;
    }

    public double getSpRate() {
        return this.Q;
    }

    public void setSpRate(double d) {
        this.Q = d;
    }

    public List<Pair<Integer, Long>> getExtraReward() {
        return this.bL;
    }

    public long getExpAdd() {
        return this.cK;
    }

    public void setExpAdd(long l) {
        this.cK = l;
    }

    public long getSpAdd() {
        return this.cL;
    }

    public void setSpAdd(long l) {
        this.cL = l;
    }

    public void setExtraReward(List<Pair<Integer, Long>> list) {
        this.bL = list;
    }
}
