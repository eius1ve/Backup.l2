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
import l2.gameserver.geodata.GeoEngine;

final class GeoEngine.GeoFileLoader.1
extends GeoEngine.GeoFileLoader {
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
}
