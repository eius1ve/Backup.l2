/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.World;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
private static abstract class GeoEngine.GeoFileLoader
extends Enum<GeoEngine.GeoFileLoader> {
    public static final /* enum */ GeoEngine.GeoFileLoader L2J = new GeoEngine.GeoFileLoader(){

        @Override
        public boolean matches(File file) {
            return file.getName().endsWith(".l2j");
        }

        /*
         * Enabled aggressive exception aggregation
         */
        @Override
        public ByteBuffer read(File file, int n, int n2) {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");){
                MappedByteBuffer mappedByteBuffer;
                block14: {
                    FileChannel fileChannel = randomAccessFile.getChannel();
                    try {
                        int n3 = (int)fileChannel.size();
                        if (n3 < 196608) {
                            throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
                        }
                        MappedByteBuffer mappedByteBuffer2 = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, n3);
                        mappedByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
                        mappedByteBuffer = mappedByteBuffer2;
                        if (fileChannel == null) break block14;
                    }
                    catch (Throwable throwable) {
                        if (fileChannel != null) {
                            try {
                                fileChannel.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    fileChannel.close();
                }
                return mappedByteBuffer;
            }
            catch (IOException iOException) {
                bc.error("", (Throwable)iOException);
                return null;
            }
        }
    };
    public static final /* enum */ GeoEngine.GeoFileLoader L2G = new GeoEngine.GeoFileLoader(){

        @Override
        public boolean matches(File file) {
            return file.getName().endsWith(".l2g");
        }

        @Override
        public ByteBuffer read(File file, int n, int n2) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                FileChannel fileChannel = randomAccessFile.getChannel();
                int n3 = 0;
                int n4 = Math.max(4, (int)fileChannel.size() - 4);
                ByteBuffer byteBuffer = ByteBuffer.allocate(n4).order(ByteOrder.LITTLE_ENDIAN);
                byteBuffer.limit(4);
                fileChannel.read(byteBuffer);
                byteBuffer.flip();
                n3 = 0x814141AB ^ byteBuffer.getInt();
                byteBuffer.clear();
                fileChannel.read(byteBuffer);
                byteBuffer.rewind();
                byte by = (byte)(n3 >> 24 & 0xFF ^ n3 >> 16 & 0xFF ^ n3 >> 8 & 0xFF ^ n3 >> 0 & 0xFF);
                while (byteBuffer.hasRemaining()) {
                    byteBuffer.put(byteBuffer.position(), (byte)(byteBuffer.get() ^ by));
                    by = byteBuffer.get(byteBuffer.position() - 1);
                    n3 -= by;
                }
                byteBuffer.rewind();
                if (n3 != 0) {
                    throw new RuntimeException("Test failed!");
                }
                if (n4 < 196608 || n3 != 0) {
                    throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
                }
                return byteBuffer;
            }
            catch (IOException iOException) {
                bc.error("", (Throwable)iOException);
                return null;
            }
        }
    };
    public static final /* enum */ GeoEngine.GeoFileLoader DAT = new GeoEngine.GeoFileLoader(){

        @Override
        public boolean matches(File file) {
            return file.getName().endsWith(".dat");
        }

        @Override
        public ByteBuffer read(File file, int n, int n2) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                FileChannel fileChannel = randomAccessFile.getChannel();
                int n3 = (int)fileChannel.size();
                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, n3);
                mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                byte by = mappedByteBuffer.get();
                byte by2 = mappedByteBuffer.get();
                if (n3 < 393234 || by != n || by2 != n2) {
                    fileChannel.close();
                    randomAccessFile.close();
                    throw new RuntimeException("Invalid geodata : " + file.getName() + "!");
                }
                mappedByteBuffer.rewind();
                return mappedByteBuffer;
            }
            catch (IOException iOException) {
                bc.error("", (Throwable)iOException);
                return null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean load(byte[][][] byArray, ByteBuffer byteBuffer, int n) {
            int n2 = 18;
            int n3 = 0;
            byte[][][] byArray2 = byArray;
            synchronized (byArray) {
                Object object = byArray[n];
                if (object == null) {
                    byte[][] byArrayArray = new byte[65536][];
                    object = byArrayArray;
                    byArray[n] = byArrayArray;
                }
                // ** MonitorExit[var7_6] (shouldn't be in output)
                byteBuffer.rewind();
                block10: for (n3 = 0; n3 < 65536; ++n3) {
                    short s = byteBuffer.getShort(n2);
                    n2 += 2;
                    switch (s) {
                        case 0: {
                            byte[] byArray3 = new byte[]{0, byteBuffer.get(n2), byteBuffer.get(n2 + 1)};
                            n2 += 4;
                            object[n3] = byArray3;
                            continue block10;
                        }
                        case 64: {
                            byte[] byArray3 = new byte[129];
                            byArray3[0] = 1;
                            byteBuffer.position(n2);
                            byteBuffer.get(byArray3, 1, 128);
                            n2 += 128;
                            object[n3] = byArray3;
                            continue block10;
                        }
                        default: {
                            byte[] byArray3;
                            ByteBuffer byteBuffer2 = tempBuff;
                            synchronized (byteBuffer2) {
                                tempBuff.clear();
                                tempBuff.put((byte)2);
                                for (int i = 0; i < 64; ++i) {
                                    byte by = (byte)byteBuffer.getShort(n2);
                                    if (by <= 0 || by > 127) {
                                        throw new RuntimeException("Invalid layer count for MultilayerBlock");
                                    }
                                    MAX_LAYERS = Math.max(MAX_LAYERS, by);
                                    n2 += 2;
                                    tempBuff.put(by);
                                    for (int j = 0; j < by << 1; ++j) {
                                        tempBuff.put(byteBuffer.get(n2++));
                                    }
                                }
                                tempBuff.flip();
                                byArray3 = Arrays.copyOf(tempBuff.array(), tempBuff.remaining());
                                tempBuff.rewind();
                            }
                            object[n3] = byArray3;
                        }
                    }
                }
                byteBuffer.position(0);
                return true;
            }
        }
    };
    protected static final ByteBuffer tempBuff;
    private static final /* synthetic */ GeoEngine.GeoFileLoader[] a;

    public static GeoEngine.GeoFileLoader[] values() {
        return (GeoEngine.GeoFileLoader[])a.clone();
    }

    public static GeoEngine.GeoFileLoader valueOf(String string) {
        return Enum.valueOf(GeoEngine.GeoFileLoader.class, string);
    }

    public abstract boolean matches(File var1);

    public abstract ByteBuffer read(File var1, int var2, int var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean load(byte[][][] byArray, ByteBuffer byteBuffer, int n) {
        int n2 = 0;
        int n3 = 0;
        byte by = 0;
        byte[][][] byArray2 = byArray;
        synchronized (byArray) {
            Object object = byArray[n];
            if (object == null) {
                byte[][] byArrayArray = new byte[65536][];
                object = byArrayArray;
                byArray[n] = byArrayArray;
            }
            // ** MonitorExit[var9_7] (shouldn't be in output)
            block8: for (n3 = 0; n3 < 65536; ++n3) {
                byte by2 = byteBuffer.get(n2);
                ++n2;
                switch (by2) {
                    case 0: {
                        byte[] byArray3 = new byte[]{by2, byteBuffer.get(n2), byteBuffer.get(n2 + 1)};
                        n2 += 2;
                        object[n3] = byArray3;
                        continue block8;
                    }
                    case 1: {
                        byte[] byArray3 = new byte[129];
                        byArray3[0] = by2;
                        byteBuffer.position(n2);
                        byteBuffer.get(byArray3, 1, 128);
                        n2 += 128;
                        object[n3] = byArray3;
                        continue block8;
                    }
                    case 2: {
                        int n4;
                        int n5 = n2;
                        for (n4 = 0; n4 < 64; ++n4) {
                            byte by3 = byteBuffer.get(n2);
                            MAX_LAYERS = Math.max(MAX_LAYERS, by3);
                            n2 += (by3 << 1) + 1;
                            if (by3 <= by) continue;
                            by = by3;
                        }
                        n4 = n2 - n5;
                        byte[] byArray3 = new byte[n4 + 1];
                        byArray3[0] = by2;
                        byteBuffer.position(n5);
                        byteBuffer.get(byArray3, 1, n4);
                        object[n3] = byArray3;
                        continue block8;
                    }
                    default: {
                        throw new RuntimeException("Invalid geodata!");
                    }
                }
            }
            return true;
        }
    }

    public static GeoEngine.GeoFileLoader LoadGeodataFile(File file, int n, int n2) {
        String string = file.getName();
        if (n < 0 || n2 < 0 || n > (World.MAP_MAX_X >> 15) + Math.abs(World.MAP_MIN_X >> 15) || n2 > (World.MAP_MAX_Y >> 15) + Math.abs(World.MAP_MIN_Y >> 15)) {
            bc.info("GeoEngine: File " + string + " was not loaded!!! ");
            return null;
        }
        bc.info("GeoEngine: Loading: " + string);
        GeoEngine.GeoFileLoader geoFileLoader = null;
        for (GeoEngine.GeoFileLoader geoFileLoader2 : GeoEngine.GeoFileLoader.values()) {
            if (!geoFileLoader2.matches(file)) continue;
            geoFileLoader = geoFileLoader2;
            break;
        }
        if (geoFileLoader == null) {
            bc.error("GeoEngine: Unknown file format: " + string);
            return null;
        }
        ByteBuffer byteBuffer = geoFileLoader.read(file, n + Config.GEO_X_FIRST, n2 + Config.GEO_Y_FIRST);
        if (byteBuffer == null) {
            bc.error("GeoEngine: Can't read " + string);
            return null;
        }
        GeoEngine.a[n][n2] = byteBuffer;
        return geoFileLoader;
    }

    private static /* synthetic */ GeoEngine.GeoFileLoader[] a() {
        return new GeoEngine.GeoFileLoader[]{L2J, L2G, DAT};
    }

    static {
        a = GeoEngine.GeoFileLoader.a();
        tempBuff = ByteBuffer.allocate(24384).order(ByteOrder.LITTLE_ENDIAN);
    }
}
