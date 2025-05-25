/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.io.filefilter.FileFilterUtils
 *  org.apache.commons.io.filefilter.IOFileFilter
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.ClassUtils
 *  org.apache.commons.lang3.reflect.FieldUtils
 *  org.apache.commons.lang3.reflect.MethodUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.scripts;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import l2.commons.compiler.Compiler;
import l2.commons.compiler.MemoryClassLoader;
import l2.gameserver.Config;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Scripts {
    private static final Logger di = LoggerFactory.getLogger(Scripts.class);
    private static final Scripts a = new Scripts();
    public static final Map<Integer, List<ScriptClassAndMethod>> dialogAppends = new HashMap<Integer, List<ScriptClassAndMethod>>();
    public static final Map<Integer, List<INpcHtmlAppendHandler>> npcHtmlAppends = new HashMap<Integer, List<INpcHtmlAppendHandler>>();
    public static final Map<String, ScriptClassAndMethod> onAction = new HashMap<String, ScriptClassAndMethod>();
    public static final Map<String, ScriptClassAndMethod> onActionShift = new HashMap<String, ScriptClassAndMethod>();
    public static final Map<Integer, List<ScriptClassAndMethod>> onScriptPacket = new HashMap<Integer, List<ScriptClassAndMethod>>();
    public static final Map<Integer, List<ScriptClassAndMethod>> onScriptExPacket = new HashMap<Integer, List<ScriptClassAndMethod>>();
    private final Compiler a = new Compiler();
    private final Map<String, Class<?>> bF = new TreeMap();

    public static final Scripts getInstance() {
        return a;
    }

    private Scripts() {
        this.load();
        this.bU();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void load() {
        Object object;
        di.info("Scripts: Loading...");
        ArrayList arrayList = new ArrayList();
        boolean bl = false;
        File file = new File("scripts.jar");
        if (file.exists()) {
            object = null;
            MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
            try {
                object = new JarInputStream(new FileInputStream(file));
                JarEntry jarEntry = null;
                while ((jarEntry = ((JarInputStream)object).getNextJarEntry()) != null) {
                    String string;
                    Class<?> clazz;
                    if (jarEntry.getName().equals("module-info.class") || jarEntry.getName().contains(ClassUtils.INNER_CLASS_SEPARATOR) || !jarEntry.getName().endsWith(".class") || Modifier.isAbstract((clazz = memoryClassLoader.loadClass(string = jarEntry.getName().replace(".class", "").replace("/", "."))).getModifiers())) continue;
                    arrayList.add(clazz);
                }
                bl = true;
            }
            catch (Exception exception) {
                try {
                    di.error("Fail to load scripts.jar!", (Throwable)exception);
                    arrayList.clear();
                }
                catch (Throwable throwable) {
                    IOUtils.closeQuietly(object);
                    throw throwable;
                }
                IOUtils.closeQuietly((InputStream)object);
            }
            IOUtils.closeQuietly((InputStream)object);
        }
        if (!bl) {
            bl = this.a(arrayList, "");
        }
        if (!bl) {
            di.error("Scripts: Failed loading scripts!");
            Runtime.getRuntime().exit(0);
            return;
        }
        di.info("Scripts: Loaded " + arrayList.size() + " classes.");
        for (int i = 0; i < arrayList.size(); ++i) {
            object = (Class)arrayList.get(i);
            this.bF.put(((Class)object).getName(), (Class<?>)object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bU() {
        di.info("Extensions: Loading...");
        ArrayList arrayList = new ArrayList();
        boolean bl = false;
        File[] fileArray = new File(".").listFiles(new FileFilter(){

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".ext.jar");
            }
        });
        Object object = fileArray;
        int n = ((File[])object).length;
        for (int i = 0; i < n; ++i) {
            File file = object[i];
            if (!file.exists()) continue;
            JarInputStream jarInputStream = null;
            MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
            try {
                jarInputStream = new JarInputStream(new FileInputStream(file));
                JarEntry jarEntry = null;
                while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                    String string;
                    Class<?> clazz;
                    if (jarEntry.getName().equals("module-info.class") || jarEntry.getName().startsWith("java/") || jarEntry.getName().startsWith("sun/") || jarEntry.getName().startsWith("l2/authserver") || jarEntry.getName().startsWith("l2/commons") || jarEntry.getName().startsWith("l2/gameserver") || jarEntry.getName().contains(ClassUtils.INNER_CLASS_SEPARATOR) || !jarEntry.getName().endsWith(".class") || Modifier.isAbstract((clazz = memoryClassLoader.loadClass(string = jarEntry.getName().replace(".class", "").replace("/", "."))).getModifiers())) continue;
                    arrayList.add(clazz);
                }
                bl = true;
            }
            catch (Exception exception) {
                try {
                    di.error("Failed to load \"" + file + "\"!", (Throwable)exception);
                    arrayList.clear();
                }
                catch (Throwable throwable) {
                    IOUtils.closeQuietly(jarInputStream);
                    throw throwable;
                }
                IOUtils.closeQuietly((InputStream)jarInputStream);
                continue;
            }
            IOUtils.closeQuietly((InputStream)jarInputStream);
            continue;
        }
        if (!bl) {
            bl = this.a(arrayList, "");
        }
        di.info("Extensions: Loaded " + arrayList.size() + " extension classes.");
        for (n = 0; n < arrayList.size(); ++n) {
            object = (Class)arrayList.get(n);
            this.bF.put(((Class)object).getName(), (Class<?>)object);
        }
    }

    public void init() {
        for (Class<?> clazz : this.bF.values()) {
            int n;
            int n2;
            Class<?> clazz2;
            String string;
            this.a(clazz);
            if (Config.DONTLOADQUEST && ClassUtils.isAssignable(clazz, Quest.class) || !ArrayUtils.isEmpty((int[])Config.IGNORE_QUESTS) && ClassUtils.isAssignable(clazz, Quest.class) && (string = (clazz2 = clazz).getSimpleName()).charAt(0) == '_' && (n2 = string.indexOf(95, 1)) > 0 && ArrayUtils.contains((int[])Config.IGNORE_QUESTS, (int)(n = Integer.parseInt(string.substring(1, n2)))) || !ClassUtils.isAssignable(clazz, ScriptFile.class)) continue;
            try {
                ((ScriptFile)clazz.newInstance()).onLoad();
            }
            catch (Exception exception) {
                di.error("Scripts: Failed running " + clazz.getName() + ".onLoad()", (Throwable)exception);
            }
        }
    }

    public boolean reload() {
        di.info("Scripts: Reloading...");
        return this.reload("");
    }

    public boolean reload(String string) {
        ArrayList arrayList = new ArrayList();
        if (!this.a(arrayList, string)) {
            di.error("Scripts: Failed reloading script(s): " + string + "!");
            return false;
        }
        di.info("Scripts: Reloaded " + arrayList.size() + " classes.");
        for (int i = 0; i < arrayList.size(); ++i) {
            Class clazz = (Class)arrayList.get(i);
            Class clazz2 = this.bF.put(clazz.getName(), clazz);
            if (clazz2 != null) {
                if (ClassUtils.isAssignable((Class)clazz2, ScriptFile.class)) {
                    try {
                        ((ScriptFile)clazz2.newInstance()).onReload();
                    }
                    catch (Exception exception) {
                        di.error("Scripts: Failed running " + clazz2.getName() + ".onReload()", (Throwable)exception);
                    }
                }
                this.b(clazz2);
            }
            if (Config.DONTLOADQUEST && ClassUtils.isAssignable((Class)clazz, Quest.class)) continue;
            if (ClassUtils.isAssignable((Class)clazz, ScriptFile.class)) {
                try {
                    ((ScriptFile)clazz.newInstance()).onLoad();
                }
                catch (Exception exception) {
                    di.error("Scripts: Failed running " + clazz.getName() + ".onLoad()", (Throwable)exception);
                }
            }
            this.a(clazz);
        }
        return true;
    }

    public void shutdown() {
        for (Class<?> clazz : this.bF.values()) {
            if (ClassUtils.isAssignable(clazz, Quest.class) || !ClassUtils.isAssignable(clazz, ScriptFile.class)) continue;
            try {
                ((ScriptFile)clazz.newInstance()).onShutdown();
            }
            catch (Exception exception) {
                di.error("Scripts: Failed running " + clazz.getName() + ".onShutdown()", (Throwable)exception);
            }
        }
    }

    private boolean a(List<Class<?>> list, String string) {
        Collection<File> collection = Collections.emptyList();
        File file = new File(Config.DATAPACK_ROOT, "data/scripts/" + string.replace(".", "/") + ".java");
        if (file.isFile()) {
            collection = new ArrayList(1);
            collection.add(file);
        } else {
            file = new File(Config.DATAPACK_ROOT, "data/scripts/" + string);
            if (file.isDirectory()) {
                collection = FileUtils.listFiles((File)file, (IOFileFilter)FileFilterUtils.suffixFileFilter((String)".java"), (IOFileFilter)FileFilterUtils.directoryFileFilter());
            }
        }
        if (collection.isEmpty()) {
            return false;
        }
        boolean bl = this.a.compile(collection);
        if (bl) {
            MemoryClassLoader memoryClassLoader = this.a.getClassLoader();
            for (String string2 : memoryClassLoader.getLoadedClasses()) {
                if (string2.contains(ClassUtils.INNER_CLASS_SEPARATOR)) continue;
                try {
                    Class<?> clazz = memoryClassLoader.loadClass(string2);
                    if (Modifier.isAbstract(clazz.getModifiers())) continue;
                    list.add(clazz);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    bl = false;
                    di.error("Scripts: Can't load script class: " + string2, (Throwable)classNotFoundException);
                }
            }
            memoryClassLoader.clear();
        }
        return bl;
    }

    private void a(Class<?> clazz) {
        try {
            if (INpcHtmlAppendHandler.class.isAssignableFrom(clazz)) {
                Object object = null;
                try {
                    int[] nArray;
                    object = (INpcHtmlAppendHandler)clazz.newInstance();
                    for (int n : nArray = object.getNpcIds()) {
                        List<INpcHtmlAppendHandler> list = npcHtmlAppends.get(n);
                        if (list == null) {
                            list = new ArrayList<INpcHtmlAppendHandler>();
                            npcHtmlAppends.put(n, list);
                        }
                        list.add((INpcHtmlAppendHandler)object);
                    }
                }
                catch (Exception exception) {
                    di.error("Scripts: Failed creating instance of " + clazz.getName(), (Throwable)exception);
                }
            }
            for (Method method : clazz.getMethods()) {
                int n;
                List<ScriptClassAndMethod> list;
                if (method.getName().contains("DialogAppend_")) {
                    Integer n2 = Integer.parseInt(method.getName().substring(13));
                    list = dialogAppends.get(n2);
                    if (list == null) {
                        list = new ArrayList<ScriptClassAndMethod>();
                        dialogAppends.put(n2, list);
                    }
                    list.add(new ScriptClassAndMethod(clazz.getName(), method.getName()));
                    continue;
                }
                if (method.getName().contains("OnAction_")) {
                    String string = method.getName().substring(9);
                    onAction.put(string, new ScriptClassAndMethod(clazz.getName(), method.getName()));
                    continue;
                }
                if (method.getName().contains("OnActionShift_")) {
                    String string = method.getName().substring(14);
                    onActionShift.put(string, new ScriptClassAndMethod(clazz.getName(), method.getName()));
                    continue;
                }
                if (method.getName().contains("OnReceivePacket_0x")) {
                    n = Integer.parseInt(method.getName().substring(18), 16);
                    list = onScriptPacket.get(n);
                    if (list == null) {
                        list = new ArrayList<ScriptClassAndMethod>();
                        onScriptPacket.put(n, list);
                    }
                    list.add(new ScriptClassAndMethod(clazz.getName(), method.getName()));
                    continue;
                }
                if (!method.getName().contains("OnReceiveExPacket_0x")) continue;
                n = Integer.parseInt(method.getName().substring(20), 16);
                list = onScriptExPacket.get(n);
                if (list == null) {
                    list = new ArrayList<ScriptClassAndMethod>();
                    onScriptExPacket.put(n, list);
                }
                list.add(new ScriptClassAndMethod(clazz.getName(), method.getName()));
            }
        }
        catch (Exception exception) {
            di.error("", (Throwable)exception);
        }
    }

    private void b(Class<?> clazz) {
        try {
            ArrayList<ScriptClassAndMethod> arrayList;
            for (List<ScriptClassAndMethod> iterator2 : dialogAppends.values()) {
                ArrayList<ScriptClassAndMethod> arrayList2 = new ArrayList<ScriptClassAndMethod>();
                for (Iterator<Object> iterator : iterator2) {
                    if (!((ScriptClassAndMethod)((Object)iterator)).className.equals(clazz.getName())) continue;
                    arrayList2.add((ScriptClassAndMethod)((Object)iterator));
                }
                for (Iterator<Object> iterator : arrayList2) {
                    iterator2.remove(iterator);
                }
            }
            ArrayList<String> arrayList2 = new ArrayList();
            for (Map.Entry<String, ScriptClassAndMethod> entry : onAction.entrySet()) {
                if (!entry.getValue().className.equals(clazz.getName())) continue;
                arrayList2.add(entry.getKey());
            }
            for (String string : arrayList2) {
                onAction.remove(string);
            }
            arrayList2 = new ArrayList<String>();
            for (Map.Entry<String, ScriptClassAndMethod> entry : onActionShift.entrySet()) {
                if (!entry.getValue().className.equals(clazz.getName())) continue;
                arrayList2.add(entry.getKey());
            }
            for (String string : arrayList2) {
                onActionShift.remove(string);
            }
            for (List<ScriptClassAndMethod> list : onScriptPacket.values()) {
                arrayList = new ArrayList();
                for (ScriptClassAndMethod scriptClassAndMethod : list) {
                    if (!scriptClassAndMethod.className.equals(clazz.getName())) continue;
                    arrayList.add(scriptClassAndMethod);
                }
                for (ScriptClassAndMethod scriptClassAndMethod : arrayList) {
                    list.remove(scriptClassAndMethod);
                }
            }
            for (List<ScriptClassAndMethod> list : onScriptExPacket.values()) {
                arrayList = new ArrayList<ScriptClassAndMethod>();
                for (ScriptClassAndMethod scriptClassAndMethod : list) {
                    if (!scriptClassAndMethod.className.equals(clazz.getName())) continue;
                    arrayList.add(scriptClassAndMethod);
                }
                for (ScriptClassAndMethod scriptClassAndMethod : arrayList) {
                    list.remove(scriptClassAndMethod);
                }
            }
        }
        catch (Exception exception) {
            di.error("", (Throwable)exception);
        }
    }

    public Object callScripts(String string, String string2) {
        return this.callScripts(null, string, string2, null, null);
    }

    public Object callScripts(String string, String string2, Object[] objectArray) {
        return this.callScripts(null, string, string2, objectArray, null);
    }

    public Object callScripts(String string, String string2, Map<String, Object> map) {
        return this.callScripts(null, string, string2, ArrayUtils.EMPTY_OBJECT_ARRAY, map);
    }

    public Object callScripts(String string, String string2, Object[] objectArray, Map<String, Object> map) {
        return this.callScripts(null, string, string2, objectArray, map);
    }

    public Object callScripts(Player player, String string, String string2) {
        return this.callScripts(player, string, string2, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public Object callScripts(Player player, String string, String string2, Object[] objectArray) {
        return this.callScripts(player, string, string2, objectArray, null);
    }

    public Object callScripts(Player player, String string, String string2, Map<String, Object> map) {
        return this.callScripts(player, string, string2, ArrayUtils.EMPTY_OBJECT_ARRAY, map);
    }

    public Object callScripts(Player player, String string, String string2, Object[] objectArray, Map<String, Object> map) {
        Object object;
        Object obj;
        Class<?> clazz = this.bF.get(string);
        if (clazz == null) {
            di.error("Script class " + string + " not found!");
            return null;
        }
        try {
            obj = clazz.newInstance();
        }
        catch (Exception exception) {
            di.error("Scripts: Failed creating instance of " + clazz.getName(), (Throwable)exception);
            return null;
        }
        if (map != null && !map.isEmpty()) {
            for (Map.Entry object2 : map.entrySet()) {
                try {
                    FieldUtils.writeField(obj, (String)((String)object2.getKey()), object2.getValue());
                }
                catch (Exception i) {
                    di.error("Scripts: Failed setting fields for " + clazz.getName(), (Throwable)i);
                }
            }
        }
        if (player != null) {
            try {
                object = null;
                object = FieldUtils.getField(clazz, (String)"self");
                if (object != null) {
                    FieldUtils.writeField((Field)object, obj, player.getRef());
                }
            }
            catch (Exception exception) {
                di.error("Scripts: Failed setting field for " + clazz.getName(), (Throwable)exception);
            }
        }
        object = null;
        try {
            Class[] noSuchMethodException = new Class[objectArray.length];
            for (int i = 0; i < objectArray.length; ++i) {
                noSuchMethodException[i] = objectArray[i] != null ? objectArray[i].getClass() : null;
            }
            object = MethodUtils.invokeMethod(obj, (String)string2, (Object[])objectArray, (Class[])noSuchMethodException);
        }
        catch (NoSuchMethodException invocationTargetException) {
            di.error("Scripts: No such method " + clazz.getName() + "." + string2 + "()!");
        }
        catch (InvocationTargetException exception) {
            di.error("Scripts: Error while calling " + clazz.getName() + "." + string2 + "()", exception.getTargetException());
        }
        catch (Exception exception) {
            di.error("Scripts: Failed calling " + clazz.getName() + "." + string2 + "()", (Throwable)exception);
        }
        return object;
    }

    public Map<String, Class<?>> getClasses() {
        return this.bF;
    }

    public static class ScriptClassAndMethod {
        public final String className;
        public final String methodName;

        public ScriptClassAndMethod(String string, String string2) {
            this.className = string;
            this.methodName = string2;
        }

        public String toString() {
            return this.className + ":" + this.methodName;
        }
    }
}
