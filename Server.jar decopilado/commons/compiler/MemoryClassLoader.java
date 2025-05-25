/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.compiler;

import java.util.HashMap;
import java.util.Map;
import l2.commons.compiler.MemoryByteCode;

public class MemoryClassLoader
extends ClassLoader {
    private final Map<String, MemoryByteCode> u = new HashMap<String, MemoryByteCode>();
    private final Map<String, MemoryByteCode> v = new HashMap<String, MemoryByteCode>();

    @Override
    protected Class<?> findClass(String string) throws ClassNotFoundException {
        MemoryByteCode memoryByteCode = this.u.get(string);
        if (memoryByteCode == null && (memoryByteCode = this.u.get(string)) == null) {
            return super.findClass(string);
        }
        return this.defineClass(string, memoryByteCode.getBytes(), 0, memoryByteCode.getBytes().length);
    }

    public void addClass(MemoryByteCode memoryByteCode) {
        this.u.put(memoryByteCode.getName(), memoryByteCode);
        this.v.put(memoryByteCode.getName(), memoryByteCode);
    }

    public MemoryByteCode getClass(String string) {
        return this.u.get(string);
    }

    public String[] getLoadedClasses() {
        return this.v.keySet().toArray(new String[this.v.size()]);
    }

    public void clear() {
        this.v.clear();
    }
}
