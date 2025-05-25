/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.utils;

public class Net {
    private final int ev;
    private final int ew;

    public Net(int n, int n2) {
        this.ev = n;
        this.ew = n2;
    }

    public int address() {
        return this.ev;
    }

    public int netmask() {
        return this.ew;
    }

    public boolean isInRange(int n) {
        return (n & this.ew) == this.ev;
    }

    public boolean isInRange(String string) {
        return this.isInRange(Net.parseAddress(string));
    }

    public static Net valueOf(String string) {
        int n = 0;
        int n2 = 0;
        String[] stringArray = string.trim().split("\\b\\/\\b");
        if (stringArray.length < 1 || stringArray.length > 2) {
            throw new IllegalArgumentException("For input string: \"" + string + "\"");
        }
        if (stringArray.length == 1) {
            String[] stringArray2 = stringArray[0].split("\\.");
            if (stringArray2.length < 1 || stringArray2.length > 4) {
                throw new IllegalArgumentException("For input string: \"" + string + "\"");
            }
            for (int i = 1; i <= stringArray2.length; ++i) {
                if (stringArray2[i - 1].equals("*")) continue;
                n |= Integer.parseInt(stringArray2[i - 1]) << 32 - i * 8;
                n2 |= 255 << 32 - i * 8;
            }
        } else {
            n = Net.parseAddress(stringArray[0]);
            n2 = Net.parseNetmask(stringArray[1]);
        }
        return new Net(n, n2);
    }

    public static int parseAddress(String string) throws IllegalArgumentException {
        int n = 0;
        String[] stringArray = string.split("\\.");
        if (stringArray.length != 4) {
            throw new IllegalArgumentException("For input string: \"" + string + "\"");
        }
        for (int i = 1; i <= stringArray.length; ++i) {
            n |= Integer.parseInt(stringArray[i - 1]) << 32 - i * 8;
        }
        return n;
    }

    public static int parseNetmask(String string) throws IllegalArgumentException {
        int n = 0;
        String[] stringArray = string.split("\\.");
        if (stringArray.length == 1) {
            int n2 = Integer.parseInt(stringArray[0]);
            if (n2 < 0 || n2 > 32) {
                throw new IllegalArgumentException("For input string: \"" + string + "\"");
            }
            n = -1 << 32 - n2;
        } else {
            for (int i = 1; i <= stringArray.length; ++i) {
                n |= Integer.parseInt(stringArray[i - 1]) << 32 - i * 8;
            }
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object instanceof Net) {
            return ((Net)object).address() == this.ev && ((Net)object).netmask() == this.ew;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.ev >>> 24).append(".");
        stringBuilder.append(this.ev << 8 >>> 24).append(".");
        stringBuilder.append(this.ev << 16 >>> 24).append(".");
        stringBuilder.append(this.ev << 24 >>> 24);
        stringBuilder.append("/");
        stringBuilder.append(this.ew >>> 24).append(".");
        stringBuilder.append(this.ew << 8 >>> 24).append(".");
        stringBuilder.append(this.ew << 16 >>> 24).append(".");
        stringBuilder.append(this.ew << 24 >>> 24);
        return stringBuilder.toString();
    }
}
