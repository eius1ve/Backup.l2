/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.text;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
private class PrintfFormat.ConversionSpecification {
    private boolean aB = false;
    private boolean aC = false;
    private boolean aD = false;
    private boolean aE = false;
    private boolean aF = false;
    private boolean aG = false;
    private boolean aH = false;
    private int ey = 0;
    private boolean aI = false;
    private int ez = 0;
    private static final int eA = 6;
    private boolean aJ = false;
    private boolean aK = false;
    private boolean aL = false;
    private int eB = 0;
    private boolean aM = false;
    private int eC = 0;
    private boolean aN;
    private int eD = 0;
    private boolean aO = false;
    private boolean aP = false;
    private boolean aQ = false;
    private char a = '\u0000';
    private int eE = 0;
    private String aN = false;

    PrintfFormat.ConversionSpecification() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    PrintfFormat.ConversionSpecification(String string) throws IllegalArgumentException {
        if (string == null) {
            throw new NullPointerException();
        }
        if (string.length() == 0) {
            throw new IllegalArgumentException("Control strings must have positive lengths.");
        }
        if (string.charAt(0) != '%') throw new IllegalArgumentException("Control strings must begin with %.");
        this.aN = string;
        this.eE = 1;
        this.af();
        this.ag();
        this.ae();
        this.ad();
        this.ac();
        if (!this.m()) throw new IllegalArgumentException("Malformed conversion specification=" + string);
        if (this.eE != string.length()) throw new IllegalArgumentException("Malformed conversion specification=" + string);
        if (this.aG && this.aC) {
            this.aG = false;
        }
        if (!this.aK || !this.aG || this.a != 'd' && this.a != 'i' && this.a != 'o' && this.a != 'x') return;
        this.aG = false;
    }

    void setLiteral(String string) {
        this.aN = string;
    }

    String getLiteral() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < this.aN.length()) {
            if (this.aN.charAt(n) == '\\') {
                if (++n < this.aN.length()) {
                    char c = this.aN.charAt(n);
                    switch (c) {
                        case 'a': {
                            stringBuilder.append('\u0007');
                            break;
                        }
                        case 'b': {
                            stringBuilder.append('\b');
                            break;
                        }
                        case 'f': {
                            stringBuilder.append('\f');
                            break;
                        }
                        case 'n': {
                            stringBuilder.append(System.getProperty("line.separator"));
                            break;
                        }
                        case 'r': {
                            stringBuilder.append('\r');
                            break;
                        }
                        case 't': {
                            stringBuilder.append('\t');
                            break;
                        }
                        case 'v': {
                            stringBuilder.append('\u000b');
                            break;
                        }
                        case '\\': {
                            stringBuilder.append('\\');
                        }
                    }
                    ++n;
                    continue;
                }
                stringBuilder.append('\\');
                continue;
            }
            ++n;
        }
        return this.aN;
    }

    char getConversionCharacter() {
        return this.a;
    }

    boolean isVariableFieldWidth() {
        return this.aH;
    }

    void setFieldWidthWithArg(int n) {
        if (n < 0) {
            this.aC = true;
        }
        this.aI = true;
        this.ey = Math.abs(n);
    }

    boolean isVariablePrecision() {
        return this.aJ;
    }

    void setPrecisionWithArg(int n) {
        this.aK = true;
        this.ez = Math.max(n, 0);
    }

    String internalsprintf(int n) throws IllegalArgumentException {
        String string = "";
        switch (this.a) {
            case 'd': 
            case 'i': {
                if (this.aO) {
                    string = this.a((short)n);
                    break;
                }
                if (this.aP) {
                    string = this.a((long)n);
                    break;
                }
                string = this.a(n);
                break;
            }
            case 'X': 
            case 'x': {
                if (this.aO) {
                    string = this.b((short)n);
                    break;
                }
                if (this.aP) {
                    string = this.b((long)n);
                    break;
                }
                string = this.b(n);
                break;
            }
            case 'o': {
                if (this.aO) {
                    string = this.c((short)n);
                    break;
                }
                if (this.aP) {
                    string = this.c((long)n);
                    break;
                }
                string = this.c(n);
                break;
            }
            case 'C': 
            case 'c': {
                string = this.a((char)n);
                break;
            }
            default: {
                throw new IllegalArgumentException("Cannot format a int with a format using a " + this.a + " conversion character.");
            }
        }
        return string;
    }

    String internalsprintf(long l) throws IllegalArgumentException {
        String string = "";
        switch (this.a) {
            case 'd': 
            case 'i': {
                if (this.aO) {
                    string = this.a((short)l);
                    break;
                }
                if (this.aP) {
                    string = this.a(l);
                    break;
                }
                string = this.a((int)l);
                break;
            }
            case 'X': 
            case 'x': {
                if (this.aO) {
                    string = this.b((short)l);
                    break;
                }
                if (this.aP) {
                    string = this.b(l);
                    break;
                }
                string = this.b((int)l);
                break;
            }
            case 'o': {
                if (this.aO) {
                    string = this.c((short)l);
                    break;
                }
                if (this.aP) {
                    string = this.c(l);
                    break;
                }
                string = this.c((int)l);
                break;
            }
            case 'C': 
            case 'c': {
                string = this.a((char)l);
                break;
            }
            default: {
                throw new IllegalArgumentException("Cannot format a long with a format using a " + this.a + " conversion character.");
            }
        }
        return string;
    }

    String internalsprintf(double d) throws IllegalArgumentException {
        String string = "";
        switch (this.a) {
            case 'f': {
                string = this.b(d);
                break;
            }
            case 'E': 
            case 'e': {
                string = this.c(d);
                break;
            }
            case 'G': 
            case 'g': {
                string = this.d(d);
                break;
            }
            default: {
                throw new IllegalArgumentException("Cannot format a double with a format using a " + this.a + " conversion character.");
            }
        }
        return string;
    }

    String internalsprintf(String string) throws IllegalArgumentException {
        String string2 = "";
        if (this.a != 's' && this.a != 'S') {
            throw new IllegalArgumentException("Cannot format a String with a format using a " + this.a + " conversion character.");
        }
        string2 = this.d(string);
        return string2;
    }

    String internalsprintf(Object object) {
        String string = "";
        if (this.a != 's' && this.a != 'S') {
            throw new IllegalArgumentException("Cannot format a String with a format using a " + this.a + " conversion character.");
        }
        string = this.d(object.toString());
        return string;
    }

    private char[] a(double d) {
        char[] cArray;
        int n;
        int n2;
        int n3;
        char[] cArray2;
        char[] cArray3;
        int n4;
        int n5;
        int n6;
        String string;
        int n7 = 0;
        boolean bl = false;
        if (d > 0.0) {
            string = Double.toString(d);
        } else if (d < 0.0) {
            string = Double.toString(-d);
            bl = true;
        } else {
            string = Double.toString(d);
            if (string.charAt(0) == '-') {
                bl = true;
                string = string.substring(1);
            }
        }
        int n8 = string.indexOf(69);
        int n9 = string.indexOf(46);
        int n10 = n9 != -1 ? n9 : (n8 != -1 ? n8 : string.length());
        int n11 = n9 != -1 ? (n8 != -1 ? n8 - n9 - 1 : string.length() - n9 - 1) : 0;
        if (n8 != -1) {
            n6 = n8 + 1;
            n7 = 0;
            if (string.charAt(n6) == '-') {
                ++n6;
                while (n6 < string.length() && string.charAt(n6) == '0') {
                    ++n6;
                }
                if (n6 < string.length()) {
                    n7 = -Integer.parseInt(string.substring(n6));
                }
            } else {
                if (string.charAt(n6) == '+') {
                    ++n6;
                }
                while (n6 < string.length() && string.charAt(n6) == '0') {
                    ++n6;
                }
                if (n6 < string.length()) {
                    n7 = Integer.parseInt(string.substring(n6));
                }
            }
        }
        n6 = this.aK ? this.ez : 5;
        char[] cArray4 = string.toCharArray();
        char[] cArray5 = new char[n10 + n11];
        for (n5 = 0; n5 < n10; ++n5) {
            cArray5[n5] = cArray4[n5];
        }
        int n12 = n5 + 1;
        for (n4 = 0; n4 < n11; ++n4) {
            cArray5[n5] = cArray4[n12];
            ++n5;
            ++n12;
        }
        if (n10 + n7 <= 0) {
            cArray3 = new char[-n7 + n11];
            n5 = 0;
            n4 = 0;
            while (n4 < -n10 - n7) {
                cArray3[n5] = 48;
                ++n4;
                ++n5;
            }
            n12 = 0;
            while (n12 < n10 + n11) {
                cArray3[n5] = cArray5[n12];
                ++n12;
                ++n5;
            }
        } else {
            cArray3 = cArray5;
        }
        boolean bl2 = false;
        if (n6 < -n7 + n11 && (bl2 = this.a(cArray3, n12 = n7 < 0 ? n6 : n6 + n10))) {
            bl2 = this.a(cArray3, n12 - 1, 0);
        }
        if (n10 + n7 <= 0) {
            cArray2 = new char[2 + n6];
            cArray2[0] = !bl2 ? 48 : 49;
            if (this.aF || !this.aK || this.ez != 0) {
                cArray2[1] = 46;
                n12 = 0;
                n5 = 2;
                while (n12 < Math.min(n6, cArray3.length)) {
                    cArray2[n5] = cArray3[n12];
                    ++n12;
                    ++n5;
                }
                while (n5 < cArray2.length) {
                    cArray2[n5] = 48;
                    ++n5;
                }
            }
        } else {
            if (!bl2) {
                cArray2 = this.aF || !this.aK || this.ez != 0 ? new char[n10 + n7 + n6 + 1] : new char[n10 + n7];
                n5 = 0;
            } else {
                cArray2 = this.aF || !this.aK || this.ez != 0 ? new char[n10 + n7 + n6 + 2] : new char[n10 + n7 + 1];
                cArray2[0] = 49;
                n5 = 1;
            }
            n12 = 0;
            while (n12 < Math.min(n10 + n7, cArray3.length)) {
                cArray2[n5] = cArray3[n12];
                ++n12;
                ++n5;
            }
            while (n12 < n10 + n7) {
                cArray2[n5] = 48;
                ++n12;
                ++n5;
            }
            if (this.aF || !this.aK || this.ez != 0) {
                cArray2[n5] = 46;
                ++n5;
                for (n4 = 0; n12 < cArray3.length && n4 < n6; ++n4) {
                    cArray2[n5] = cArray3[n12];
                    ++n12;
                    ++n5;
                }
                while (n5 < cArray2.length) {
                    cArray2[n5] = 48;
                    ++n5;
                }
            }
        }
        int n13 = 0;
        if (!this.aC && this.aG) {
            n3 = 0;
            if (this.aB) {
                n2 = 0;
                if (cArray2[0] == '+' || cArray2[0] == '-' || cArray2[0] == ' ') {
                    n2 = 1;
                }
                for (n = n2; n < cArray2.length && cArray2[n] != '.'; ++n) {
                }
                n3 = (n - n2) / 3;
            }
            if (this.aI) {
                n13 = this.ey - cArray2.length;
            }
            if (!bl && (this.aD || this.aE) || bl) {
                --n13;
            }
            if ((n13 -= n3) < 0) {
                n13 = 0;
            }
        }
        n5 = 0;
        if (!bl && (this.aD || this.aE) || bl) {
            cArray = new char[cArray2.length + n13 + 1];
            ++n5;
        } else {
            cArray = new char[cArray2.length + n13];
        }
        if (!bl) {
            if (this.aD) {
                cArray[0] = 43;
            }
            if (this.aE) {
                cArray[0] = 32;
            }
        } else {
            cArray[0] = 45;
        }
        n12 = 0;
        while (n12 < n13) {
            cArray[n5] = 48;
            ++n12;
            ++n5;
        }
        n12 = 0;
        while (n12 < cArray2.length) {
            cArray[n5] = cArray2[n12];
            ++n12;
            ++n5;
        }
        n3 = 0;
        if (cArray[0] == '+' || cArray[0] == '-' || cArray[0] == ' ') {
            n3 = 1;
        }
        for (n2 = n3; n2 < cArray.length && cArray[n2] != '.'; ++n2) {
        }
        n = (n2 - n3) / 3;
        if (n2 < cArray.length) {
            cArray[n2] = PrintfFormat.this.a.getDecimalSeparator();
        }
        char[] cArray6 = cArray;
        if (this.aB && n > 0) {
            cArray6 = new char[cArray.length + n + n3];
            cArray6[0] = cArray[0];
            n4 = n3;
            for (n12 = n3; n12 < n2; ++n12) {
                if (n12 > 0 && (n2 - n12) % 3 == 0) {
                    cArray6[n4] = PrintfFormat.this.a.getGroupingSeparator();
                    cArray6[n4 + 1] = cArray[n12];
                    n4 += 2;
                    continue;
                }
                cArray6[n4] = cArray[n12];
                ++n4;
            }
            while (n12 < cArray.length) {
                cArray6[n4] = cArray[n12];
                ++n12;
                ++n4;
            }
        }
        return cArray6;
    }

    private String a(double d) {
        char[] cArray = Double.isInfinite(d) ? (d == Double.POSITIVE_INFINITY ? (this.aD ? "+Inf".toCharArray() : (this.aE ? " Inf".toCharArray() : "Inf".toCharArray())) : "-Inf".toCharArray()) : (Double.isNaN(d) ? (this.aD ? "+NaN".toCharArray() : (this.aE ? " NaN".toCharArray() : "NaN".toCharArray())) : this.a(d));
        char[] cArray2 = this.a(cArray, false);
        return new String(cArray2);
    }

    private char[] a(double d, char c) {
        char[] cArray;
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        char[] cArray2;
        int n7;
        String string;
        int n8 = 0;
        boolean bl = false;
        if (d > 0.0) {
            string = Double.toString(d);
        } else if (d < 0.0) {
            string = Double.toString(-d);
            bl = true;
        } else {
            string = Double.toString(d);
            if (string.charAt(0) == '-') {
                bl = true;
                string = string.substring(1);
            }
        }
        int n9 = string.indexOf(69);
        if (n9 == -1) {
            n9 = string.indexOf(101);
        }
        int n10 = string.indexOf(46);
        if (n9 != -1) {
            n7 = n9 + 1;
            n8 = 0;
            if (string.charAt(n7) == '-') {
                ++n7;
                while (n7 < string.length() && string.charAt(n7) == '0') {
                    ++n7;
                }
                if (n7 < string.length()) {
                    n8 = -Integer.parseInt(string.substring(n7));
                }
            } else {
                if (string.charAt(n7) == '+') {
                    ++n7;
                }
                while (n7 < string.length() && string.charAt(n7) == '0') {
                    ++n7;
                }
                if (n7 < string.length()) {
                    n8 = Integer.parseInt(string.substring(n7));
                }
            }
        }
        if (n10 != -1) {
            n8 += n10 - 1;
        }
        int n11 = this.aK ? this.ez : 5;
        char[] cArray3 = n10 != -1 && n9 != -1 ? (string.substring(0, n10) + string.substring(n10 + 1, n9)).toCharArray() : (n10 != -1 ? (string.substring(0, n10) + string.substring(n10 + 1)).toCharArray() : (n9 != -1 ? string.substring(0, n9).toCharArray() : string.toCharArray()));
        n7 = 0;
        int n12 = 0;
        if (cArray3[0] != '0') {
            n12 = 0;
        } else {
            for (n12 = 0; n12 < cArray3.length && cArray3[n12] == '0'; ++n12) {
            }
        }
        if (n12 + n11 < cArray3.length - 1) {
            n7 = this.a(cArray3, n12 + n11 + 1) ? 1 : 0;
            if (n7 != 0) {
                n7 = this.a(cArray3, n12 + n11, n12) ? 1 : 0;
            }
            if (n7 != 0) {
                cArray2 = new char[n12 + n11 + 1];
                cArray2[n12] = 49;
                for (n6 = 0; n6 < n12; ++n6) {
                    cArray2[n6] = 48;
                }
                n5 = n12;
                for (n6 = n12 + 1; n6 < n11 + 1; ++n6) {
                    cArray2[n6] = cArray3[n5];
                    ++n5;
                }
                ++n8;
                cArray3 = cArray2;
            }
        }
        int n13 = Math.abs(n8) < 100 && !this.aQ ? 4 : 5;
        cArray2 = this.aF || !this.aK || this.ez != 0 ? new char[2 + n11 + n13] : new char[1 + n13];
        if (cArray3[0] != '0') {
            cArray2[0] = cArray3[0];
            n6 = 1;
        } else {
            for (n6 = 1; n6 < (n9 == -1 ? cArray3.length : n9) && cArray3[n6] == '0'; ++n6) {
            }
            if (n9 != -1 && n6 < n9 || n9 == -1 && n6 < cArray3.length) {
                cArray2[0] = cArray3[n6];
                n8 -= n6;
                ++n6;
            } else {
                cArray2[0] = 48;
                n6 = 2;
            }
        }
        if (this.aF || !this.aK || this.ez != 0) {
            cArray2[1] = 46;
            n5 = 2;
        } else {
            n5 = 1;
        }
        for (n4 = 0; n4 < n11 && n6 < cArray3.length; ++n4) {
            cArray2[n5] = cArray3[n6];
            ++n6;
            ++n5;
        }
        while (n5 < cArray2.length - n13) {
            cArray2[n5] = 48;
            ++n5;
        }
        cArray2[n5++] = c;
        cArray2[n5++] = n8 < 0 ? 45 : 43;
        if ((n8 = Math.abs(n8)) >= 100) {
            switch (n8 / 100) {
                case 1: {
                    cArray2[n5] = 49;
                    break;
                }
                case 2: {
                    cArray2[n5] = 50;
                    break;
                }
                case 3: {
                    cArray2[n5] = 51;
                    break;
                }
                case 4: {
                    cArray2[n5] = 52;
                    break;
                }
                case 5: {
                    cArray2[n5] = 53;
                    break;
                }
                case 6: {
                    cArray2[n5] = 54;
                    break;
                }
                case 7: {
                    cArray2[n5] = 55;
                    break;
                }
                case 8: {
                    cArray2[n5] = 56;
                    break;
                }
                case 9: {
                    cArray2[n5] = 57;
                }
            }
            ++n5;
        }
        switch (n8 % 100 / 10) {
            case 0: {
                cArray2[n5] = 48;
                break;
            }
            case 1: {
                cArray2[n5] = 49;
                break;
            }
            case 2: {
                cArray2[n5] = 50;
                break;
            }
            case 3: {
                cArray2[n5] = 51;
                break;
            }
            case 4: {
                cArray2[n5] = 52;
                break;
            }
            case 5: {
                cArray2[n5] = 53;
                break;
            }
            case 6: {
                cArray2[n5] = 54;
                break;
            }
            case 7: {
                cArray2[n5] = 55;
                break;
            }
            case 8: {
                cArray2[n5] = 56;
                break;
            }
            case 9: {
                cArray2[n5] = 57;
            }
        }
        ++n5;
        switch (n8 % 10) {
            case 0: {
                cArray2[n5] = 48;
                break;
            }
            case 1: {
                cArray2[n5] = 49;
                break;
            }
            case 2: {
                cArray2[n5] = 50;
                break;
            }
            case 3: {
                cArray2[n5] = 51;
                break;
            }
            case 4: {
                cArray2[n5] = 52;
                break;
            }
            case 5: {
                cArray2[n5] = 53;
                break;
            }
            case 6: {
                cArray2[n5] = 54;
                break;
            }
            case 7: {
                cArray2[n5] = 55;
                break;
            }
            case 8: {
                cArray2[n5] = 56;
                break;
            }
            case 9: {
                cArray2[n5] = 57;
            }
        }
        int n14 = 0;
        if (!this.aC && this.aG) {
            n3 = 0;
            if (this.aB) {
                n2 = 0;
                if (cArray2[0] == '+' || cArray2[0] == '-' || cArray2[0] == ' ') {
                    n2 = 1;
                }
                for (n = n2; n < cArray2.length && cArray2[n] != '.'; ++n) {
                }
                n3 = (n - n2) / 3;
            }
            if (this.aI) {
                n14 = this.ey - cArray2.length;
            }
            if (!bl && (this.aD || this.aE) || bl) {
                --n14;
            }
            if ((n14 -= n3) < 0) {
                n14 = 0;
            }
        }
        n6 = 0;
        if (!bl && (this.aD || this.aE) || bl) {
            cArray = new char[cArray2.length + n14 + 1];
            ++n6;
        } else {
            cArray = new char[cArray2.length + n14];
        }
        if (!bl) {
            if (this.aD) {
                cArray[0] = 43;
            }
            if (this.aE) {
                cArray[0] = 32;
            }
        } else {
            cArray[0] = 45;
        }
        for (n4 = 0; n4 < n14; ++n4) {
            cArray[n6] = 48;
            ++n6;
        }
        for (n5 = 0; n5 < cArray2.length && n6 < cArray.length; ++n5, ++n6) {
            cArray[n6] = cArray2[n5];
        }
        n3 = 0;
        if (cArray[0] == '+' || cArray[0] == '-' || cArray[0] == ' ') {
            n3 = 1;
        }
        for (n2 = n3; n2 < cArray.length && cArray[n2] != '.'; ++n2) {
        }
        n = n2 / 3;
        if (n2 < cArray.length) {
            cArray[n2] = PrintfFormat.this.a.getDecimalSeparator();
        }
        char[] cArray4 = cArray;
        if (this.aB && n > 0) {
            cArray4 = new char[cArray.length + n + n3];
            cArray4[0] = cArray[0];
            n4 = n3;
            for (n5 = n3; n5 < n2; ++n5) {
                if (n5 > 0 && (n2 - n5) % 3 == 0) {
                    cArray4[n4] = PrintfFormat.this.a.getGroupingSeparator();
                    cArray4[n4 + 1] = cArray[n5];
                    n4 += 2;
                    continue;
                }
                cArray4[n4] = cArray[n5];
                ++n4;
            }
            while (n5 < cArray.length) {
                cArray4[n4] = cArray[n5];
                ++n5;
                ++n4;
            }
        }
        return cArray4;
    }

    private boolean a(char[] cArray, int n) {
        boolean bl = false;
        if (n < cArray.length) {
            if (cArray[n] == '6' || cArray[n] == '7' || cArray[n] == '8' || cArray[n] == '9') {
                bl = true;
            } else if (cArray[n] == '5') {
                int n2;
                for (n2 = n + 1; n2 < cArray.length && cArray[n2] == '0'; ++n2) {
                }
                boolean bl2 = bl = n2 < cArray.length;
                if (!bl && n > 0) {
                    bl = cArray[n - 1] == '1' || cArray[n - 1] == '3' || cArray[n - 1] == '5' || cArray[n - 1] == '7' || cArray[n - 1] == '9';
                }
            }
        }
        return bl;
    }

    private boolean a(char[] cArray, int n, int n2) {
        boolean bl = true;
        block12: for (int i = n; bl && i >= n2; --i) {
            bl = false;
            switch (cArray[i]) {
                case '0': {
                    cArray[i] = 49;
                    continue block12;
                }
                case '1': {
                    cArray[i] = 50;
                    continue block12;
                }
                case '2': {
                    cArray[i] = 51;
                    continue block12;
                }
                case '3': {
                    cArray[i] = 52;
                    continue block12;
                }
                case '4': {
                    cArray[i] = 53;
                    continue block12;
                }
                case '5': {
                    cArray[i] = 54;
                    continue block12;
                }
                case '6': {
                    cArray[i] = 55;
                    continue block12;
                }
                case '7': {
                    cArray[i] = 56;
                    continue block12;
                }
                case '8': {
                    cArray[i] = 57;
                    continue block12;
                }
                case '9': {
                    cArray[i] = 48;
                    bl = true;
                }
            }
        }
        return bl;
    }

    private String a(double d, char c) {
        char[] cArray = Double.isInfinite(d) ? (d == Double.POSITIVE_INFINITY ? (this.aD ? "+Inf".toCharArray() : (this.aE ? " Inf".toCharArray() : "Inf".toCharArray())) : "-Inf".toCharArray()) : (Double.isNaN(d) ? (this.aD ? "+NaN".toCharArray() : (this.aE ? " NaN".toCharArray() : "NaN".toCharArray())) : this.a(d, c));
        char[] cArray2 = this.a(cArray, false);
        return new String(cArray2);
    }

    private char[] a(char[] cArray, boolean bl) {
        char[] cArray2;
        block8: {
            int n;
            block10: {
                int n2;
                block9: {
                    int n3;
                    cArray2 = cArray;
                    if (!this.aI) break block8;
                    if (!this.aC) break block9;
                    int n4 = this.ey - cArray.length;
                    if (n4 <= 0) break block8;
                    cArray2 = new char[cArray.length + n4];
                    for (n3 = 0; n3 < cArray.length; ++n3) {
                        cArray2[n3] = cArray[n3];
                    }
                    int n5 = 0;
                    while (n5 < n4) {
                        cArray2[n3] = 32;
                        ++n5;
                        ++n3;
                    }
                    break block8;
                }
                if (this.aG && !bl) break block10;
                int n6 = this.ey - cArray.length;
                if (n6 <= 0) break block8;
                cArray2 = new char[cArray.length + n6];
                for (n2 = 0; n2 < n6; ++n2) {
                    cArray2[n2] = 32;
                }
                for (int i = 0; i < cArray.length; ++i) {
                    cArray2[n2] = cArray[i];
                    ++n2;
                }
                break block8;
            }
            if (this.aG && (n = this.ey - cArray.length) > 0) {
                cArray2 = new char[cArray.length + n];
                int n7 = 0;
                int n8 = 0;
                if (cArray[0] == '-') {
                    cArray2[0] = 45;
                    ++n7;
                    ++n8;
                }
                for (int i = 0; i < n; ++i) {
                    cArray2[n7] = 48;
                    ++n7;
                }
                while (n8 < cArray.length) {
                    cArray2[n7] = cArray[n8];
                    ++n7;
                    ++n8;
                }
            }
        }
        return cArray2;
    }

    private String b(double d) {
        return this.a(d);
    }

    private String c(double d) {
        if (this.a == 'e') {
            return this.a(d, 'e');
        }
        return this.a(d, 'E');
    }

    private String d(double d) {
        char[] cArray;
        int n = this.ez;
        if (Double.isInfinite(d)) {
            cArray = d == Double.POSITIVE_INFINITY ? (this.aD ? "+Inf".toCharArray() : (this.aE ? " Inf".toCharArray() : "Inf".toCharArray())) : "-Inf".toCharArray();
        } else if (Double.isNaN(d)) {
            cArray = this.aD ? "+NaN".toCharArray() : (this.aE ? " NaN".toCharArray() : "NaN".toCharArray());
        } else {
            Object object;
            String string;
            if (!this.aK) {
                this.ez = 6;
            }
            if (this.ez == 0) {
                this.ez = 1;
            }
            int n2 = -1;
            if (this.a == 'g') {
                string = this.a(d, 'e').trim();
                n2 = string.indexOf(101);
            } else {
                string = this.a(d, 'E').trim();
                n2 = string.indexOf(69);
            }
            int n3 = n2 + 1;
            int n4 = 0;
            if (string.charAt(n3) == '-') {
                ++n3;
                while (n3 < string.length() && string.charAt(n3) == '0') {
                    ++n3;
                }
                if (n3 < string.length()) {
                    n4 = -Integer.parseInt(string.substring(n3));
                }
            } else {
                if (string.charAt(n3) == '+') {
                    ++n3;
                }
                while (n3 < string.length() && string.charAt(n3) == '0') {
                    ++n3;
                }
                if (n3 < string.length()) {
                    n4 = Integer.parseInt(string.substring(n3));
                }
            }
            if (!this.aF) {
                String string2 = n4 >= -4 && n4 < this.ez ? this.a(d).trim() : string.substring(0, n2);
                for (n3 = string2.length() - 1; n3 >= 0 && string2.charAt(n3) == '0'; --n3) {
                }
                if (n3 >= 0 && string2.charAt(n3) == '.') {
                    --n3;
                }
                Object object2 = n3 == -1 ? "0" : (!Character.isDigit(string2.charAt(n3)) ? string2.substring(0, n3 + 1) + "0" : string2.substring(0, n3 + 1));
                object = n4 >= -4 && n4 < this.ez ? object2 : (String)object2 + string.substring(n2);
            } else {
                object = n4 >= -4 && n4 < this.ez ? this.a(d).trim() : string;
            }
            if (this.aE && d >= 0.0) {
                object = " " + (String)object;
            }
            cArray = ((String)object).toCharArray();
        }
        char[] cArray2 = this.a(cArray, false);
        this.ez = n;
        return new String(cArray2);
    }

    private String a(short s) {
        return this.a(Short.toString(s));
    }

    private String a(long l) {
        return this.a(Long.toString(l));
    }

    private String a(int n) {
        return this.a(Integer.toString(n));
    }

    private String a(String string) {
        char[] cArray;
        block30: {
            int n;
            boolean bl;
            int n2;
            int n3;
            int n4;
            block33: {
                int n5;
                block31: {
                    block34: {
                        block32: {
                            block29: {
                                int n6;
                                n4 = 0;
                                n5 = 0;
                                int n7 = 0;
                                n3 = 0;
                                n2 = 0;
                                boolean bl2 = bl = string.charAt(0) == '-';
                                if (string.equals("0") && this.aK && this.ez == 0) {
                                    string = "";
                                }
                                if (!bl) {
                                    if (this.aK && string.length() < this.ez) {
                                        n4 = this.ez - string.length();
                                    }
                                } else if (this.aK && string.length() - 1 < this.ez) {
                                    n4 = this.ez - string.length() + 1;
                                }
                                if (n4 < 0) {
                                    n4 = 0;
                                }
                                if (this.aI) {
                                    n5 = this.ey - n4 - string.length();
                                    if (!bl && (this.aD || this.aE)) {
                                        --n5;
                                    }
                                }
                                if (n5 < 0) {
                                    n5 = 0;
                                }
                                if (this.aD) {
                                    ++n7;
                                } else if (this.aE) {
                                    ++n7;
                                }
                                n7 += n5;
                                n7 += n4;
                                cArray = new char[n7 += string.length()];
                                if (!this.aC) break block29;
                                if (bl) {
                                    cArray[n3++] = 45;
                                } else if (this.aD) {
                                    cArray[n3++] = 43;
                                } else if (this.aE) {
                                    cArray[n3++] = 32;
                                }
                                char[] cArray2 = string.toCharArray();
                                n2 = bl ? 1 : 0;
                                for (n6 = 0; n6 < n4; ++n6) {
                                    cArray[n3] = 48;
                                    ++n3;
                                }
                                n6 = n2;
                                while (n6 < cArray2.length) {
                                    cArray[n3] = cArray2[n6];
                                    ++n6;
                                    ++n3;
                                }
                                for (n6 = 0; n6 < n5; ++n6) {
                                    cArray[n3] = 32;
                                    ++n3;
                                }
                                break block30;
                            }
                            if (this.aG) break block31;
                            for (n3 = 0; n3 < n5; ++n3) {
                                cArray[n3] = 32;
                            }
                            if (!bl) break block32;
                            cArray[n3++] = 45;
                            break block33;
                        }
                        if (!this.aD) break block34;
                        cArray[n3++] = 43;
                        break block33;
                    }
                    if (!this.aE) break block33;
                    cArray[n3++] = 32;
                    break block33;
                }
                if (bl) {
                    cArray[n3++] = 45;
                } else if (this.aD) {
                    cArray[n3++] = 43;
                } else if (this.aE) {
                    cArray[n3++] = 32;
                }
                n = 0;
                while (n < n5) {
                    cArray[n3] = 48;
                    ++n;
                    ++n3;
                }
            }
            n = 0;
            while (n < n4) {
                cArray[n3] = 48;
                ++n;
                ++n3;
            }
            char[] cArray3 = string.toCharArray();
            int n8 = n2 = bl ? 1 : 0;
            while (n8 < cArray3.length) {
                cArray[n3] = cArray3[n8];
                ++n8;
                ++n3;
            }
        }
        return new String(cArray);
    }

    private String b(short s) {
        Object object = null;
        if (s == Short.MIN_VALUE) {
            object = "8000";
        } else if (s < 0) {
            String string;
            if (s == Short.MIN_VALUE) {
                string = "0";
            } else {
                string = Integer.toString(~(-s - 1) ^ Short.MIN_VALUE, 16);
                if (string.charAt(0) == 'F' || string.charAt(0) == 'f') {
                    string = string.substring(16, 32);
                }
            }
            block0 : switch (string.length()) {
                case 1: {
                    object = "800" + string;
                    break;
                }
                case 2: {
                    object = "80" + string;
                    break;
                }
                case 3: {
                    object = "8" + string;
                    break;
                }
                case 4: {
                    switch (string.charAt(0)) {
                        case '1': {
                            object = "9" + string.substring(1, 4);
                            break block0;
                        }
                        case '2': {
                            object = "a" + string.substring(1, 4);
                            break block0;
                        }
                        case '3': {
                            object = "b" + string.substring(1, 4);
                            break block0;
                        }
                        case '4': {
                            object = "c" + string.substring(1, 4);
                            break block0;
                        }
                        case '5': {
                            object = "d" + string.substring(1, 4);
                            break block0;
                        }
                        case '6': {
                            object = "e" + string.substring(1, 4);
                            break block0;
                        }
                        case '7': {
                            object = "f" + string.substring(1, 4);
                        }
                    }
                }
            }
        } else {
            object = Integer.toString(s, 16);
        }
        return this.b((String)object);
    }

    private String b(long l) {
        Object object = null;
        if (l == Long.MIN_VALUE) {
            object = "8000000000000000";
        } else if (l < 0L) {
            String string = Long.toString(-l - 1L ^ 0xFFFFFFFFFFFFFFFFL ^ Long.MIN_VALUE, 16);
            block0 : switch (string.length()) {
                case 1: {
                    object = "800000000000000" + string;
                    break;
                }
                case 2: {
                    object = "80000000000000" + string;
                    break;
                }
                case 3: {
                    object = "8000000000000" + string;
                    break;
                }
                case 4: {
                    object = "800000000000" + string;
                    break;
                }
                case 5: {
                    object = "80000000000" + string;
                    break;
                }
                case 6: {
                    object = "8000000000" + string;
                    break;
                }
                case 7: {
                    object = "800000000" + string;
                    break;
                }
                case 8: {
                    object = "80000000" + string;
                    break;
                }
                case 9: {
                    object = "8000000" + string;
                    break;
                }
                case 10: {
                    object = "800000" + string;
                    break;
                }
                case 11: {
                    object = "80000" + string;
                    break;
                }
                case 12: {
                    object = "8000" + string;
                    break;
                }
                case 13: {
                    object = "800" + string;
                    break;
                }
                case 14: {
                    object = "80" + string;
                    break;
                }
                case 15: {
                    object = "8" + string;
                    break;
                }
                case 16: {
                    switch (string.charAt(0)) {
                        case '1': {
                            object = "9" + string.substring(1, 16);
                            break block0;
                        }
                        case '2': {
                            object = "a" + string.substring(1, 16);
                            break block0;
                        }
                        case '3': {
                            object = "b" + string.substring(1, 16);
                            break block0;
                        }
                        case '4': {
                            object = "c" + string.substring(1, 16);
                            break block0;
                        }
                        case '5': {
                            object = "d" + string.substring(1, 16);
                            break block0;
                        }
                        case '6': {
                            object = "e" + string.substring(1, 16);
                            break block0;
                        }
                        case '7': {
                            object = "f" + string.substring(1, 16);
                        }
                    }
                }
            }
        } else {
            object = Long.toString(l, 16);
        }
        return this.b((String)object);
    }

    private String b(int n) {
        Object object = null;
        if (n == Integer.MIN_VALUE) {
            object = "80000000";
        } else if (n < 0) {
            String string = Integer.toString(~(-n - 1) ^ Integer.MIN_VALUE, 16);
            block0 : switch (string.length()) {
                case 1: {
                    object = "8000000" + string;
                    break;
                }
                case 2: {
                    object = "800000" + string;
                    break;
                }
                case 3: {
                    object = "80000" + string;
                    break;
                }
                case 4: {
                    object = "8000" + string;
                    break;
                }
                case 5: {
                    object = "800" + string;
                    break;
                }
                case 6: {
                    object = "80" + string;
                    break;
                }
                case 7: {
                    object = "8" + string;
                    break;
                }
                case 8: {
                    switch (string.charAt(0)) {
                        case '1': {
                            object = "9" + string.substring(1, 8);
                            break block0;
                        }
                        case '2': {
                            object = "a" + string.substring(1, 8);
                            break block0;
                        }
                        case '3': {
                            object = "b" + string.substring(1, 8);
                            break block0;
                        }
                        case '4': {
                            object = "c" + string.substring(1, 8);
                            break block0;
                        }
                        case '5': {
                            object = "d" + string.substring(1, 8);
                            break block0;
                        }
                        case '6': {
                            object = "e" + string.substring(1, 8);
                            break block0;
                        }
                        case '7': {
                            object = "f" + string.substring(1, 8);
                        }
                    }
                }
            }
        } else {
            object = Integer.toString(n, 16);
        }
        return this.b((String)object);
    }

    private String b(String string) {
        Object object;
        int n = 0;
        int n2 = 0;
        if (string.equals("0") && this.aK && this.ez == 0) {
            string = "";
        }
        if (this.aK) {
            n = this.ez - string.length();
        }
        if (n < 0) {
            n = 0;
        }
        if (this.aI) {
            n2 = this.ey - n - string.length();
            if (this.aF) {
                n2 -= 2;
            }
        }
        if (n2 < 0) {
            n2 = 0;
        }
        int n3 = 0;
        if (this.aF) {
            n3 += 2;
        }
        n3 += n;
        n3 += string.length();
        char[] cArray = new char[n3 += n2];
        int n4 = 0;
        if (this.aC) {
            if (this.aF) {
                cArray[n4++] = 48;
                cArray[n4++] = 120;
            }
            int n5 = 0;
            while (n5 < n) {
                cArray[n4] = 48;
                ++n5;
                ++n4;
            }
            object = string.toCharArray();
            int n6 = 0;
            while (n6 < ((char[])object).length) {
                cArray[n4] = object[n6];
                ++n6;
                ++n4;
            }
            n6 = 0;
            while (n6 < n2) {
                cArray[n4] = 32;
                ++n6;
                ++n4;
            }
        } else {
            int n7;
            if (!this.aG) {
                n7 = 0;
                while (n7 < n2) {
                    cArray[n4] = 32;
                    ++n7;
                    ++n4;
                }
            }
            if (this.aF) {
                cArray[n4++] = 48;
                cArray[n4++] = 120;
            }
            if (this.aG) {
                n7 = 0;
                while (n7 < n2) {
                    cArray[n4] = 48;
                    ++n7;
                    ++n4;
                }
            }
            n7 = 0;
            while (n7 < n) {
                cArray[n4] = 48;
                ++n7;
                ++n4;
            }
            object = string.toCharArray();
            int n8 = 0;
            while (n8 < ((char[])object).length) {
                cArray[n4] = object[n8];
                ++n8;
                ++n4;
            }
        }
        object = new String(cArray);
        if (this.a == 'X') {
            object = ((String)object).toUpperCase();
        }
        return object;
    }

    private String c(short s) {
        Object object = null;
        if (s == Short.MIN_VALUE) {
            object = "100000";
        } else if (s < 0) {
            String string = Integer.toString(~(-s - 1) ^ Short.MIN_VALUE, 8);
            switch (string.length()) {
                case 1: {
                    object = "10000" + string;
                    break;
                }
                case 2: {
                    object = "1000" + string;
                    break;
                }
                case 3: {
                    object = "100" + string;
                    break;
                }
                case 4: {
                    object = "10" + string;
                    break;
                }
                case 5: {
                    object = "1" + string;
                }
            }
        } else {
            object = Integer.toString(s, 8);
        }
        return this.c((String)object);
    }

    private String c(long l) {
        Object object = null;
        if (l == Long.MIN_VALUE) {
            object = "1000000000000000000000";
        } else if (l < 0L) {
            String string = Long.toString(-l - 1L ^ 0xFFFFFFFFFFFFFFFFL ^ Long.MIN_VALUE, 8);
            switch (string.length()) {
                case 1: {
                    object = "100000000000000000000" + string;
                    break;
                }
                case 2: {
                    object = "10000000000000000000" + string;
                    break;
                }
                case 3: {
                    object = "1000000000000000000" + string;
                    break;
                }
                case 4: {
                    object = "100000000000000000" + string;
                    break;
                }
                case 5: {
                    object = "10000000000000000" + string;
                    break;
                }
                case 6: {
                    object = "1000000000000000" + string;
                    break;
                }
                case 7: {
                    object = "100000000000000" + string;
                    break;
                }
                case 8: {
                    object = "10000000000000" + string;
                    break;
                }
                case 9: {
                    object = "1000000000000" + string;
                    break;
                }
                case 10: {
                    object = "100000000000" + string;
                    break;
                }
                case 11: {
                    object = "10000000000" + string;
                    break;
                }
                case 12: {
                    object = "1000000000" + string;
                    break;
                }
                case 13: {
                    object = "100000000" + string;
                    break;
                }
                case 14: {
                    object = "10000000" + string;
                    break;
                }
                case 15: {
                    object = "1000000" + string;
                    break;
                }
                case 16: {
                    object = "100000" + string;
                    break;
                }
                case 17: {
                    object = "10000" + string;
                    break;
                }
                case 18: {
                    object = "1000" + string;
                    break;
                }
                case 19: {
                    object = "100" + string;
                    break;
                }
                case 20: {
                    object = "10" + string;
                    break;
                }
                case 21: {
                    object = "1" + string;
                }
            }
        } else {
            object = Long.toString(l, 8);
        }
        return this.c((String)object);
    }

    private String c(int n) {
        Object object = null;
        if (n == Integer.MIN_VALUE) {
            object = "20000000000";
        } else if (n < 0) {
            String string = Integer.toString(~(-n - 1) ^ Integer.MIN_VALUE, 8);
            switch (string.length()) {
                case 1: {
                    object = "2000000000" + string;
                    break;
                }
                case 2: {
                    object = "200000000" + string;
                    break;
                }
                case 3: {
                    object = "20000000" + string;
                    break;
                }
                case 4: {
                    object = "2000000" + string;
                    break;
                }
                case 5: {
                    object = "200000" + string;
                    break;
                }
                case 6: {
                    object = "20000" + string;
                    break;
                }
                case 7: {
                    object = "2000" + string;
                    break;
                }
                case 8: {
                    object = "200" + string;
                    break;
                }
                case 9: {
                    object = "20" + string;
                    break;
                }
                case 10: {
                    object = "2" + string;
                    break;
                }
                case 11: {
                    object = "3" + string.substring(1);
                }
            }
        } else {
            object = Integer.toString(n, 8);
        }
        return this.c((String)object);
    }

    private String c(String string) {
        int n = 0;
        int n2 = 0;
        if (string.equals("0") && this.aK && this.ez == 0) {
            string = "";
        }
        if (this.aK) {
            n = this.ez - string.length();
        }
        if (this.aF) {
            ++n;
        }
        if (n < 0) {
            n = 0;
        }
        if (this.aI) {
            n2 = this.ey - n - string.length();
        }
        if (n2 < 0) {
            n2 = 0;
        }
        int n3 = n + string.length() + n2;
        char[] cArray = new char[n3];
        if (this.aC) {
            int n4;
            for (n4 = 0; n4 < n; ++n4) {
                cArray[n4] = 48;
            }
            char[] cArray2 = string.toCharArray();
            int n5 = 0;
            while (n5 < cArray2.length) {
                cArray[n4] = cArray2[n5];
                ++n5;
                ++n4;
            }
            n5 = 0;
            while (n5 < n2) {
                cArray[n4] = 32;
                ++n5;
                ++n4;
            }
        } else {
            int n6;
            if (this.aG) {
                for (n6 = 0; n6 < n2; ++n6) {
                    cArray[n6] = 48;
                }
            } else {
                for (n6 = 0; n6 < n2; ++n6) {
                    cArray[n6] = 32;
                }
            }
            int n7 = 0;
            while (n7 < n) {
                cArray[n6] = 48;
                ++n7;
                ++n6;
            }
            char[] cArray3 = string.toCharArray();
            int n8 = 0;
            while (n8 < cArray3.length) {
                cArray[n6] = cArray3[n8];
                ++n8;
                ++n6;
            }
        }
        return new String(cArray);
    }

    private String a(char c) {
        int n = 1;
        int n2 = this.ey;
        if (!this.aI) {
            n2 = n;
        }
        char[] cArray = new char[n2];
        int n3 = 0;
        if (this.aC) {
            cArray[0] = c;
            for (n3 = 1; n3 <= n2 - n; ++n3) {
                cArray[n3] = 32;
            }
        } else {
            for (n3 = 0; n3 < n2 - n; ++n3) {
                cArray[n3] = 32;
            }
            cArray[n3] = c;
        }
        return new String(cArray);
    }

    private String d(String string) {
        int n = string.length();
        int n2 = this.ey;
        if (this.aK && n > this.ez) {
            n = this.ez;
        }
        if (!this.aI) {
            n2 = n;
        }
        int n3 = 0;
        if (n2 > n) {
            n3 += n2 - n;
        }
        n3 = n >= string.length() ? (n3 += string.length()) : (n3 += n);
        char[] cArray = new char[n3];
        int n4 = 0;
        if (this.aC) {
            if (n >= string.length()) {
                var7_7 = string.toCharArray();
                for (n4 = 0; n4 < string.length(); ++n4) {
                    cArray[n4] = var7_7[n4];
                }
            } else {
                var7_7 = string.substring(0, n).toCharArray();
                for (n4 = 0; n4 < n; ++n4) {
                    cArray[n4] = var7_7[n4];
                }
            }
            int n5 = 0;
            while (n5 < n2 - n) {
                cArray[n4] = 32;
                ++n5;
                ++n4;
            }
        } else {
            for (n4 = 0; n4 < n2 - n; ++n4) {
                cArray[n4] = 32;
            }
            if (n >= string.length()) {
                char[] cArray2 = string.toCharArray();
                for (int i = 0; i < string.length(); ++i) {
                    cArray[n4] = cArray2[i];
                    ++n4;
                }
            } else {
                char[] cArray3 = string.substring(0, n).toCharArray();
                for (int i = 0; i < n; ++i) {
                    cArray[n4] = cArray3[i];
                    ++n4;
                }
            }
        }
        return new String(cArray);
    }

    private boolean m() {
        char c;
        boolean bl = false;
        this.a = '\u0000';
        if (this.eE < this.aN.length() && ((c = this.aN.charAt(this.eE)) == 'i' || c == 'd' || c == 'f' || c == 'g' || c == 'G' || c == 'o' || c == 'x' || c == 'X' || c == 'e' || c == 'E' || c == 'c' || c == 's' || c == '%')) {
            this.a = c;
            ++this.eE;
            bl = true;
        }
        return bl;
    }

    private void ac() {
        this.aO = false;
        this.aP = false;
        this.aQ = false;
        if (this.eE < this.aN.length()) {
            char c = this.aN.charAt(this.eE);
            if (c == 'h') {
                this.aO = true;
                ++this.eE;
            } else if (c == 'l') {
                this.aP = true;
                ++this.eE;
            } else if (c == 'L') {
                this.aQ = true;
                ++this.eE;
            }
        }
    }

    private void ad() {
        int n = this.eE;
        this.aK = false;
        if (this.eE < this.aN.length() && this.aN.charAt(this.eE) == '.') {
            char c;
            ++this.eE;
            if (this.eE < this.aN.length() && this.aN.charAt(this.eE) == '*') {
                ++this.eE;
                if (!this.o()) {
                    this.aJ = true;
                    this.aK = true;
                }
                return;
            }
            while (this.eE < this.aN.length() && Character.isDigit(c = this.aN.charAt(this.eE))) {
                ++this.eE;
            }
            if (this.eE > n + 1) {
                String string = this.aN.substring(n + 1, this.eE);
                this.ez = Integer.parseInt(string);
                this.aK = true;
            }
        }
    }

    private void ae() {
        int n = this.eE;
        this.ey = 0;
        this.aI = false;
        if (this.eE < this.aN.length() && this.aN.charAt(this.eE) == '*') {
            ++this.eE;
            if (!this.n()) {
                this.aH = true;
                this.aI = true;
            }
        } else {
            char c;
            while (this.eE < this.aN.length() && Character.isDigit(c = this.aN.charAt(this.eE))) {
                ++this.eE;
            }
            if (n < this.eE && n < this.aN.length()) {
                String string = this.aN.substring(n, this.eE);
                this.ey = Integer.parseInt(string);
                this.aI = true;
            }
        }
    }

    private void af() {
        int n;
        for (n = this.eE; n < this.aN.length() && Character.isDigit(this.aN.charAt(n)); ++n) {
        }
        if (n > this.eE && n < this.aN.length() && this.aN.charAt(n) == '$') {
            this.aL = true;
            this.eB = Integer.parseInt(this.aN.substring(this.eE, n));
            this.eE = n + 1;
        }
    }

    private boolean n() {
        int n;
        boolean bl = false;
        for (n = this.eE; n < this.aN.length() && Character.isDigit(this.aN.charAt(n)); ++n) {
        }
        if (n > this.eE && n < this.aN.length() && this.aN.charAt(n) == '$') {
            this.aM = true;
            this.eC = Integer.parseInt(this.aN.substring(this.eE, n));
            this.eE = n + 1;
            bl = true;
        }
        return bl;
    }

    private boolean o() {
        int n;
        boolean bl = false;
        for (n = this.eE; n < this.aN.length() && Character.isDigit(this.aN.charAt(n)); ++n) {
        }
        if (n > this.eE && n < this.aN.length() && this.aN.charAt(n) == '$') {
            this.aN = true;
            this.eD = Integer.parseInt(this.aN.substring(this.eE, n));
            this.eE = n + 1;
            bl = true;
        }
        return bl;
    }

    boolean isPositionalSpecification() {
        return this.aL;
    }

    int getArgumentPosition() {
        return this.eB;
    }

    boolean isPositionalFieldWidth() {
        return this.aM;
    }

    int getArgumentPositionForFieldWidth() {
        return this.eC;
    }

    boolean isPositionalPrecision() {
        return this.aN;
    }

    int getArgumentPositionForPrecision() {
        return this.eD;
    }

    private void ag() {
        this.aB = false;
        this.aC = false;
        this.aD = false;
        this.aE = false;
        this.aF = false;
        this.aG = false;
        while (this.eE < this.aN.length()) {
            char c = this.aN.charAt(this.eE);
            if (c == '\'') {
                this.aB = true;
            } else if (c == '-') {
                this.aC = true;
                this.aG = false;
            } else if (c == '+') {
                this.aD = true;
                this.aE = false;
            } else if (c == ' ') {
                if (!this.aD) {
                    this.aE = true;
                }
            } else if (c == '#') {
                this.aF = true;
            } else {
                if (c != '0') break;
                if (!this.aC) {
                    this.aG = true;
                }
            }
            ++this.eE;
        }
    }
}
