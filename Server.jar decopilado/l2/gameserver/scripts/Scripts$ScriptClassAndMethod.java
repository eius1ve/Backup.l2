/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.scripts;

public static class Scripts.ScriptClassAndMethod {
    public final String className;
    public final String methodName;

    public Scripts.ScriptClassAndMethod(String string, String string2) {
        this.className = string;
        this.methodName = string2;
    }

    public String toString() {
        return this.className + ":" + this.methodName;
    }
}
