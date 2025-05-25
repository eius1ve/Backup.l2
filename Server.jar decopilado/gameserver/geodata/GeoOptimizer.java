/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoOptimizer {
    private static final Logger bd = LoggerFactory.getLogger(GeoOptimizer.class);
    public static int[][][] checkSums;
    private static final byte a = 1;

    public static BlockLink[] loadBlockMatches(String string) {
        File file = new File(Config.DATAPACK_ROOT, string);
        if (!file.exists()) {
            return null;
        }
        try {
            FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
            int n = (int)((fileChannel.size() - 1L) / 6L);
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            fileChannel.close();
            mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            if (mappedByteBuffer.get() != 1) {
                return null;
            }
            BlockLink[] blockLinkArray = new BlockLink[n];
            for (int i = 0; i < blockLinkArray.length; ++i) {
                blockLinkArray[i] = new BlockLink(mappedByteBuffer.getShort(), mappedByteBuffer.get(), mappedByteBuffer.get(), mappedByteBuffer.getShort());
            }
            return blockLinkArray;
        }
        catch (Exception exception) {
            bd.error("", (Throwable)exception);
            return null;
        }
    }

    public static class BlockLink {
        public final int blockIndex;
        public final int linkBlockIndex;
        public final byte linkMapX;
        public final byte linkMapY;

        public BlockLink(short s, byte by, byte by2, short s2) {
            this.blockIndex = s & 0xFFFF;
            this.linkMapX = by;
            this.linkMapY = by2;
            this.linkBlockIndex = s2 & 0xFFFF;
        }

        public BlockLink(int n, byte by, byte by2, int n2) {
            this.blockIndex = n & 0xFFFF;
            this.linkMapX = by;
            this.linkMapY = by2;
            this.linkBlockIndex = n2 & 0xFFFF;
        }
    }

    public static class CheckSumLoader
    extends RunnableImpl {
        private final int fs;
        private final int ft;
        private final int fu;
        private final int fv;
        private final byte[][][] a;
        private final String bN;

        public CheckSumLoader(int n, int n2, byte[][][] byArray) {
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

    public static class GeoBlocksMatchFinder
    extends RunnableImpl {
        private final int fw;
        private final int fx;
        private final int fy;
        private final int fz;
        private final int fA;
        private final String bO;

        public GeoBlocksMatchFinder(int n, int n2, int n3) {
            this.fw = n;
            this.fx = n2;
            this.fy = this.fw + Config.GEO_X_FIRST;
            this.fz = this.fx + Config.GEO_Y_FIRST;
            this.fA = n3;
            this.bO = "geodata/matches/" + this.fy + "_" + this.fz + ".matches";
        }

        private boolean q() {
            return new File(Config.DATAPACK_ROOT, this.bO).exists();
        }

        private void a(BlockLink[] blockLinkArray) {
            bd.info("Saving matches to: " + this.bO);
            try {
                File file = new File(Config.DATAPACK_ROOT, this.bO);
                if (file.exists()) {
                    file.delete();
                }
                FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0L, blockLinkArray.length * 6 + 1);
                mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                mappedByteBuffer.put((byte)1);
                for (int i = 0; i < blockLinkArray.length; ++i) {
                    mappedByteBuffer.putShort((short)blockLinkArray[i].blockIndex);
                    mappedByteBuffer.put(blockLinkArray[i].linkMapX);
                    mappedByteBuffer.put(blockLinkArray[i].linkMapY);
                    mappedByteBuffer.putShort((short)blockLinkArray[i].linkBlockIndex);
                }
                fileChannel.close();
            }
            catch (Exception exception) {
                bd.error("", (Throwable)exception);
            }
        }

        private void a(int[] nArray, int n, int n2, List<BlockLink> list, boolean[] blArray) {
            int[] nArray2 = checkSums[n][n2];
            if (nArray2 == null) {
                return;
            }
            block0: for (int i = 0; i < 65536; ++i) {
                int n3;
                if (!blArray[i]) continue;
                for (int j = n3 = nArray2 == nArray ? i + 1 : 0; j < 65536; ++j) {
                    if (nArray[i] != nArray2[j] || !GeoEngine.compareGeoBlocks(this.fw, this.fx, i, n, n2, j)) continue;
                    list.add(new BlockLink(i, (byte)n, (byte)n2, j));
                    blArray[i] = false;
                    continue block0;
                }
            }
        }

        private BlockLink[] a() {
            bd.info("Searching matches for " + this.fy + "_" + this.fz);
            long l = System.currentTimeMillis();
            boolean[] blArray = new boolean[65536];
            for (int i = 0; i < 65536; ++i) {
                blArray[i] = true;
            }
            ArrayList<BlockLink> arrayList = new ArrayList<BlockLink>();
            int[] nArray = checkSums[this.fw][this.fx];
            int n = 0;
            for (int i = this.fw; i < World.WORLD_SIZE_X; ++i) {
                int n2;
                for (int j = n2 = i == this.fw ? this.fx : 0; j < World.WORLD_SIZE_Y; ++j) {
                    this.a(nArray, i, j, arrayList, blArray);
                    if (this.fA <= 0 || this.fA != ++n) continue;
                    return arrayList.toArray(new BlockLink[arrayList.size()]);
                }
            }
            l = System.currentTimeMillis() - l;
            bd.info("Founded " + arrayList.size() + " matches for " + this.fy + "_" + this.fz + " in " + (float)l / 1000.0f + "s");
            return arrayList.toArray(new BlockLink[arrayList.size()]);
        }

        @Override
        public void runImpl() throws Exception {
            if (!this.q()) {
                BlockLink[] blockLinkArray = this.a();
                this.a(blockLinkArray);
            }
        }
    }
}
