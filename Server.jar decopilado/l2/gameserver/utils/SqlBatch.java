/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

public class SqlBatch {
    private String gE;
    private String gF;
    private StringBuilder a;
    private StringBuilder b;
    private long eh = Long.MAX_VALUE;
    private boolean ht = true;

    public SqlBatch(String string, String string2) {
        this.gE = string + "\n";
        this.gF = string2 != null && string2.length() > 0 ? " " + string2 + ";\n" : ";\n";
        this.a = new StringBuilder(this.gE);
        this.b = new StringBuilder();
    }

    public SqlBatch(String string) {
        this(string, null);
    }

    public void writeStructure(String string) {
        this.b.append(string);
    }

    public void write(String string) {
        this.ht = false;
        if ((long)(this.a.length() + string.length()) < this.eh - (long)this.gF.length()) {
            this.a.append(string + ",\n");
        } else {
            this.a.append(string + this.gF);
            this.b.append(this.a.toString());
            this.a = new StringBuilder(this.gE);
        }
    }

    public void writeBuffer() {
        String string = this.a.toString();
        if (string.length() > 0) {
            this.b.append(string.substring(0, string.length() - 2) + this.gF);
        }
        this.a = new StringBuilder(this.gE);
    }

    public String close() {
        if (this.a.length() > this.gE.length()) {
            this.writeBuffer();
        }
        return this.b.toString();
    }

    public void setLimit(long l) {
        this.eh = l;
    }

    public boolean isEmpty() {
        return this.ht;
    }
}
