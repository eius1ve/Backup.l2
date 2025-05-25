/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.geodata.GeoOptimizer;
import l2.gameserver.model.World;

public static class GeoOptimizer.GeoBlocksMatchFinder
extends RunnableImpl {
    private final int fw;
    private final int fx;
    private final int fy;
    private final int fz;
    private final int fA;
    private final String bO;

    public GeoOptimizer.GeoBlocksMatchFinder(int n, int n2, int n3) {
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

    private void a(GeoOptimizer.BlockLink[] blockLinkArray) {
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

    private void a(int[] nArray, int n, int n2, List<GeoOptimizer.BlockLink> list, boolean[] blArray) {
        int[] nArray2 = checkSums[n][n2];
        if (nArray2 == null) {
            return;
        }
        block0: for (int i = 0; i < 65536; ++i) {
            int n3;
            if (!blArray[i]) continue;
            for (int j = n3 = nArray2 == nArray ? i + 1 : 0; j < 65536; ++j) {
                if (nArray[i] != nArray2[j] || !GeoEngine.compareGeoBlocks(this.fw, this.fx, i, n, n2, j)) continue;
                list.add(new GeoOptimizer.BlockLink(i, (byte)n, (byte)n2, j));
                blArray[i] = false;
                continue block0;
            }
        }
    }

    private GeoOptimizer.BlockLink[] a() {
        bd.info("Searching matches for " + this.fy + "_" + this.fz);
        long l = System.currentTimeMillis();
        boolean[] blArray = new boolean[65536];
        for (int i = 0; i < 65536; ++i) {
            blArray[i] = true;
        }
        ArrayList<GeoOptimizer.BlockLink> arrayList = new ArrayList<GeoOptimizer.BlockLink>();
        int[] nArray = checkSums[this.fw][this.fx];
        int n = 0;
        for (int i = this.fw; i < World.WORLD_SIZE_X; ++i) {
            int n2;
            for (int j = n2 = i == this.fw ? this.fx : 0; j < World.WORLD_SIZE_Y; ++j) {
                this.a(nArray, i, j, arrayList, blArray);
                if (this.fA <= 0 || this.fA != ++n) continue;
                return arrayList.toArray(new GeoOptimizer.BlockLink[arrayList.size()]);
            }
        }
        l = System.currentTimeMillis() - l;
        bd.info("Founded " + arrayList.size() + " matches for " + this.fy + "_" + this.fz + " in " + (float)l / 1000.0f + "s");
        return arrayList.toArray(new GeoOptimizer.BlockLink[arrayList.size()]);
    }

    @Override
    public void runImpl() throws Exception {
        if (!this.q()) {
            GeoOptimizer.BlockLink[] blockLinkArray = this.a();
            this.a(blockLinkArray);
        }
    }
}
