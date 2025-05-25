/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.geometry;

import java.util.Arrays;
import java.util.List;
import l2.commons.geometry.AbstractShape;
import l2.commons.geometry.GeometryUtils;
import l2.commons.geometry.Point2D;
import l2.commons.lang.ArrayUtils;

public class Polygon
extends AbstractShape {
    protected Point2D[] points = Point2D.EMPTY_ARRAY;

    public Polygon add(int n, int n2) {
        this.add(new Point2D(n, n2));
        return this;
    }

    public Polygon add(Point2D point2D) {
        if (this.points.length == 0) {
            this.min.y = point2D.y;
            this.min.x = point2D.x;
            this.max.x = point2D.x;
            this.max.y = point2D.y;
        } else {
            this.min.y = Math.min(this.min.y, point2D.y);
            this.min.x = Math.min(this.min.x, point2D.x);
            this.max.x = Math.max(this.max.x, point2D.x);
            this.max.y = Math.max(this.max.y, point2D.y);
        }
        this.points = ArrayUtils.add(this.points, point2D);
        return this;
    }

    public List<Point2D> getPoints() {
        return Arrays.asList(this.points);
    }

    @Override
    public Polygon setZmax(int n) {
        this.max.z = n;
        return this;
    }

    @Override
    public Polygon setZmin(int n) {
        this.min.z = n;
        return this;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean isInside(int var1_1, int var2_2) {
        if (var1_1 < this.min.x || var1_1 > this.max.x || var2_2 < this.min.y || var2_2 > this.max.y) {
            return false;
        }
        var3_3 = 0;
        var4_4 = this.points.length;
        var5_5 = this.points[var4_4 - 1];
        for (var7_6 = 0; var7_6 < var4_4; ++var7_6) {
            block8: {
                block11: {
                    block12: {
                        block10: {
                            block9: {
                                var6_7 = this.points[var7_6];
                                if (var6_7.y == var5_5.y) break block8;
                                if (var6_7.x >= var5_5.x) break block9;
                                if (var1_1 >= var5_5.x) break block8;
                                var8_8 = var6_7.x;
                                break block10;
                            }
                            if (var1_1 >= var6_7.x) break block8;
                            var8_8 = var5_5.x;
                        }
                        if (var6_7.y >= var5_5.y) break block11;
                        if (var2_2 < var6_7.y || var2_2 >= var5_5.y) break block8;
                        if (var1_1 >= var8_8) break block12;
                        ++var3_3;
                        break block8;
                    }
                    var9_9 = var1_1 - var6_7.x;
                    var11_10 = var2_2 - var6_7.y;
                    ** GOTO lbl33
                }
                if (var2_2 < var5_5.y || var2_2 >= var6_7.y) break block8;
                if (var1_1 < var8_8) {
                    ++var3_3;
                } else {
                    var9_9 = var1_1 - var5_5.x;
                    var11_10 = var2_2 - var5_5.y;
lbl33:
                    // 2 sources

                    if (var9_9 < var11_10 / (double)(var5_5.y - var6_7.y) * (double)(var5_5.x - var6_7.x)) {
                        ++var3_3;
                    }
                }
            }
            var5_5 = var6_7;
        }
        return (var3_3 & true) != false;
    }

    public boolean validate() {
        if (this.points.length < 3) {
            return false;
        }
        if (this.points.length > 3) {
            for (int i = 1; i < this.points.length; ++i) {
                int n = i + 1 < this.points.length ? i + 1 : 0;
                for (int j = i; j < this.points.length; ++j) {
                    int n2;
                    if (Math.abs(j - i) <= 1) continue;
                    int n3 = n2 = j + 1 < this.points.length ? j + 1 : 0;
                    if (!GeometryUtils.checkIfLineSegementsIntersects(this.points[i], this.points[n], this.points[j], this.points[n2])) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < this.points.length; ++i) {
            stringBuilder.append(this.points[i]);
            if (i >= this.points.length - 1) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append(";[").append(this.getZmin()).append("-").append(this.getZmax()).append("]");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
