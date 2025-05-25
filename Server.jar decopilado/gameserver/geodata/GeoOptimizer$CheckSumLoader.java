/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoOptimizer;

public static class GeoOptimizer.CheckSumLoader
extends RunnableImpl {
    private final int fs;
    private final int ft;
    private final int fu;
    private final int fv;
    private final byte[][][] a;
    private final String bN;

    public GeoOptimizer.CheckSumLoader(int n, int n2, byte[][][] byArray) {
        this.fs = n;
        this.ft = n2;
        this.fu = this.fs + Config.GEO_X_FIRST;
        this.fv = n2 + Config.GEO_Y_FIRST;
        this.a = byArray;
        this.bN = "geodata/checksum/" + this.fu + "_" + this.fv + ".crc";
    }

    private boolean p() {
        File file = new File(Config.DATAPACK_ROOT, this.bN);
        if (!file.exists()) {
            return false;
        }
        try {
            FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
            if (fileChannel.size() != 262144L) {
                fileChannel.close();
                return false;
            }
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            fileChannel.close();
            mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            int[] nArray = new int[65536];
            for (int i = 0; i < 65536; ++i) {
                nArray[i] = mappedByteBuffer.getInt();
            }
            GeoOptimizer.checkSums[this.fs][this.ft] = nArray;
            return true;
        }
        catch (Exception exception) {
            bd.error("", (Throwable)exception);
            return false;
        }
    }

    private void at() {
        bd.info("Saving checksums to: " + this.bN);
        try {
            File file = new File(Config.DATAPACK_ROOT, this.bN);
            if (file.exists()) {
                file.delete();
            }
            FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0L, 262144L);
            mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            int[] nArray = checkSums[this.fs][this.ft];
            for (int i = 0; i < 65536; ++i) {
                mappedByteBuffer.putInt(nArray[i]);
            }
            fileChannel.close();
        }
        catch (Exception exception) {
            bd.error("", (Throwable)exception);
        }
    }

    private void au() {
        bd.info("Generating checksums for " + this.fu + "_" + this.fv);
        int[] nArray = new int[65536];
        CRC32 cRC32 = new CRC32();
        for (int i = 0; i < 65536; ++i) {
            cRC32.update(this.a[i][0]);
            nArray[i] = (int)(cRC32.getValue() ^ 0xFFFFFFFFFFFFFFFFL);
            cRC32.reset();
        }
        GeoOptimizer.checkSums[this.fs][this.ft] = nArray;
    }

    @Override
    public void runImpl() throws Exception {
        if (!this.p()) {
            this.au();
            this.at();
        }
    }
}
