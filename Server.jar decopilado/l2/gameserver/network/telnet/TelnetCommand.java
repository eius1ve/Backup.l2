/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.telnet;

import org.apache.commons.lang3.ArrayUtils;

public abstract class TelnetCommand
implements Comparable<TelnetCommand> {
    private final String fR;
    private final String[] aO;

    public TelnetCommand(String string) {
        this(string, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public TelnetCommand(String string, String ... stringArray) {
        this.fR = string;
        this.aO = stringArray;
    }

    public String getCommand() {
        return this.fR;
    }

    public String[] getAcronyms() {
        return this.aO;
    }

    public abstract String getUsage();

    public abstract String handle(String[] var1);

    public boolean equals(String string) {
        for (String string2 : this.aO) {
            if (!string.equals(string2)) continue;
            return true;
        }
        return this.fR.equalsIgnoreCase(string);
    }

    public String toString() {
        return this.fR;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return true;
        }
        if (object instanceof TelnetCommand) {
            return this.fR.equals(((TelnetCommand)object).fR);
        }
        return false;
    }

    @Override
    public int compareTo(TelnetCommand telnetCommand) {
        return this.fR.compareTo(telnetCommand.fR);
    }
}
