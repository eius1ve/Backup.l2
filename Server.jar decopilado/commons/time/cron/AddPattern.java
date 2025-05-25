/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import l2.commons.time.cron.NextTime;

public class AddPattern
implements NextTime {
    private int eJ = -1;
    private int eK = -1;
    private int eL = -1;
    private int eM = -1;
    private int eN = -1;
    private int eO = -1;
    private int eP = -1;
    private int eQ = -1;

    public AddPattern(String string) {
        String[] stringArray;
        String[] stringArray2 = string.split("\\s+");
        if (stringArray2.length == 2) {
            String string2;
            stringArray = stringArray2[0];
            String[] stringArray3 = stringArray.split(":");
            if (stringArray3.length == 2) {
                if (stringArray3[0].startsWith("+")) {
                    this.eJ = Integer.parseInt(stringArray3[0].substring(1));
                } else {
                    this.eK = Integer.parseInt(stringArray3[0]) - 1;
                }
            }
            if ((string2 = stringArray3[stringArray3.length - 1]).startsWith("+")) {
                this.eL = Integer.parseInt(string2.substring(1));
            } else {
                this.eM = Integer.parseInt(string2);
            }
        }
        if ((stringArray = stringArray2[stringArray2.length - 1].split(":"))[0].startsWith("+")) {
            this.eN = Integer.parseInt(stringArray[0].substring(1));
        } else {
            this.eO = Integer.parseInt(stringArray[0]);
        }
        if (stringArray[1].startsWith("+")) {
            this.eP = Integer.parseInt(stringArray[1].substring(1));
        } else {
            this.eQ = Integer.parseInt(stringArray[1]);
        }
    }

    @Override
    public long next(long l) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
        gregorianCalendar.setTimeInMillis(l);
        if (this.eJ >= 0) {
            gregorianCalendar.add(2, this.eJ);
        }
        if (this.eK >= 0) {
            gregorianCalendar.set(2, this.eK);
        }
        if (this.eL >= 0) {
            gregorianCalendar.add(5, this.eL);
        }
        if (this.eM >= 0) {
            gregorianCalendar.set(5, this.eM);
        }
        if (this.eN >= 0) {
            gregorianCalendar.add(11, this.eN);
        }
        if (this.eO >= 0) {
            gregorianCalendar.set(11, this.eO);
        }
        if (this.eP >= 0) {
            gregorianCalendar.add(12, this.eP);
        }
        if (this.eQ >= 0) {
            gregorianCalendar.set(12, this.eQ);
        }
        return gregorianCalendar.getTimeInMillis();
    }
}
