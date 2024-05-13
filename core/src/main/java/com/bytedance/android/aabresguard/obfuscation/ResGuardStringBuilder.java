package com.bytedance.android.aabresguard.obfuscation;

import com.bytedance.android.aabresguard.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 混淆字典.
 * <p>
 * Copied from: https://github.com/shwenzhang/AndResGuard
 */
public class ResGuardStringBuilder {

    private final List<String> mReplaceStringBuffer;
    private final Set<Integer> mIsReplaced;
    private final Set<Integer> mIsWhiteList;

    private static String[] oldAToZ = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z"
    };
    private static String[] oldAToAll = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "_", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "g8", "f4", "2x", "wi", "u6", "yz", "t20", "_50", "lw", "wdz", "nr", "lqe", "7d", "faf", "rzs", "boa", "rr", "ur", "r4m", "9h", "_7", "ia", "_q", "a2", "zy", "ge", "bp", "upa", "69d", "5w3", "foz", "fxx", "_rg", "h1", "9y", "xn5", "i8", "ft", "q4", "97", "l69", "k6", "af0", "dw", "0j0", "0a", "80", "c7", "_4", "30q", "qt", "wxd", "h3s", "sap", "3d", "pbx", "39l", "cq", "3q", "_r5", "xw8", "5lo", "cu", "xd4", "gon", "sd", "rg", "9u", "m4", "crw", "o8", "1n", "23", "nt", "ok", "pg", "xoj", "7_f", "gb6", "7l", "9p", "rl", "r9", "c_c", "12", "yb", "rlr", "eh0", "p5", "z8", "3k", "t8", "ho", "br", "r3", "bw", "ib", "6oy", "ucb", "kw", "aaz", "os7", "1ko", "var", "iee", "lc3", "dd", "xk", "2fv", "8s", "b5y", "md", "vi", "0ky", "cx8", "cm", "xuo", "px", "jz", "oe", "4q2", "eny", "v77", "f1", "5_n", "d6", "etc", "spz", "gp", "6m", "g_c", "nw", "vu5", "adv", "hi", "j18", "65", "t2", "uz", "ig_", "hyq", "4um", "xzf", "yt", "jv5", "di", "54", "ahb", "6a", "ick", "8bz", "7x", "uo", "d1t", "_sh", "y6", "hd", "zu", "u9n", "n_g", "kj", "hkp", "cf", "1r1", "6bd", "gx", "x9j", "996", "5id", "4hz", "6g", "p66", "4t", "3e", "ps", "t7g", "z7q", "xe", "4p", "8aq", "_c", "ya", "gu", "5xx", "44", "l7", "b0", "iw", "uvm", "24m", "r5", "jr", "skb", "_w", "4z", "bf", "4yr", "i9", "ol", "c2", "p9", "7k", "ne6", "0va", "2sd", "6az", "t9s", "po", "l8h", "no", "v0", "dbx", "pi", "bh9", "i_", "r0", "e5d", "37", "v0g", "zur", "ij", "bl6", "gf8", "co", "63o", "p6", "es", "6c", "10u", "4fv", "zw", "cza", "0r", "fh1", "l4p", "99w", "ig", "p3f", "ri", "ert", "pfo", "6zc", "nba", "p9e", "fs", "ug", "j1", "o12", "d5", "qa", "axv", "18", "ul2", "q_", "cx", "2y", "l82", "3b0", "p_u", "p2", "sz9", "mgb", "syk", "b9h", "ei5", "sp", "h14", "86m", "_ls", "j4x", "69s", "9rw", "x6s", "3v_", "gv", "s37", "xc8", "88", "sut", "eod", "5f7", "b19", "1z", "2k", "co0", "gey", "lj8", "jg", "hc", "sf", "85", "fx", "6n6", "0o", "1f", "lf", "zqm", "pxg", "1t", "mu", "4c4", "jw", "op", "5ai", "4a_", "p_", "qxz", "yhs", "q1", "b8x", "hjf", "1nl", "4r", "k5", "9fg", "je6", "62", "fv1", "_wd", "kz", "xa", "mc", "bt", "p3", "kl", "o6q", "0m", "c0", "zp", "tu", "u0k", "bq", "69", "zv7", "xf0", "0k2", "cyp", "n40", "7p7", "nvf", "hvy", "vhe", "ie0", "cen", "g1t", "16j", "9x", "f1x", "4w", "njt", "2p", "tp", "g1l", "546", "ahy", "0u", "8_k", "itb", "kn", "in", "bxt", "lba", "x5", "w0x", "dax", "ns", "n2o", "x8", "w1l", "a68", "tv6", "k3u", "ki9", "pk", "nso", "ply", "vh", "_7b", "z7", "60", "7pd", "c60", "h7", "zj", "o4r", "cmj", "0_", "v3", "y5p", "c8", "zbo", "ck", "vt", "fr", "zd", "udi", "jsz", "uek", "lb", "ykb", "ntc", "ky", "5vt", "a9", "icu", "z5", "_6", "8t", "s0z", "ub", "418", "d9", "by5", "bv", "2h", "e8", "dm", "qlu", "2c", "_qn", "nj6", "6jd", "bg", "zk3", "vz", "m2", "382", "yl", "au", "kh", "wr", "ubj", "b06", "18q", "wxc", "15o", "a5", "dsp", "7eo", "ui", "1f6", "jm", "q5", "yr", "bn", "8im", "fl", "ii", "kmv", "w8", "29", "82d", "b2", "wp8", "pt", "430", "v7j", "bk", "22", "rx", "ld2", "p9m", "h_6", "djy", "7vd", "tn", "mit", "kp", "il", "inc", "lwl", "421", "jte", "5sc", "3r", "tuu", "4go", "ed", "pyh", "9m_", "17", "is", "am", "kr", "rh7", "aw", "tf_", "xtm", "_92", "u4", "hh", "s5", "3we", "re2", "7e", "4_", "soa", "t9j", "4zv", "rd5"
    };

    public static String[] getRandomizedAndTrimmedArray(String[] sourceArray, int limit) {
        if (sourceArray.length < 20) {
            throw new IllegalArgumentException("Source array must contain at least 20 elements.");
        }

        String[] arrayCopy = Arrays.copyOf(sourceArray, sourceArray.length);
        Collections.shuffle(Arrays.asList(arrayCopy), new Random());

        // Decide how many elements to delete, ensuring we don't go below 20
        int elementsToDelete = new Random().nextInt(Math.max(0, arrayCopy.length - 20));

        // Perform deletion while maintaining the constraint
        List<String> tempList = new ArrayList<>(Arrays.asList(arrayCopy));
        while (tempList.size() > limit && !tempList.isEmpty()) {
            tempList.remove(new Random().nextInt(tempList.size()));
        }

        String[] array = tempList.subList(0, Math.min(tempList.size(), limit)).toArray(new String[0]);
        System.out.println("Random arg : " + Arrays.toString(array));
        return array;
    }

    private static String[] mAToZ = getRandomizedAndTrimmedArray(oldAToZ, 20);
    private static String[] mAToAll = getRandomizedAndTrimmedArray(oldAToAll, 80);

    /**
     * 在window上面有些关键字是不能作为文件名的
     * CON, PRN, AUX, CLOCK$, NUL
     * COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8, COM9
     * LPT1, LPT2, LPT3, LPT4, LPT5, LPT6, LPT7, LPT8, and LPT9.
     */
    private HashSet<String> mFileNameBlackList;

    public ResGuardStringBuilder() {
        mFileNameBlackList = new HashSet<>();
        mFileNameBlackList.add("con");
        mFileNameBlackList.add("prn");
        mFileNameBlackList.add("aux");
        mFileNameBlackList.add("nul");
        mReplaceStringBuffer = new ArrayList<>();
        mIsReplaced = new HashSet<>();
        mIsWhiteList = new HashSet<>();
    }

    public void reset(HashSet<Pattern> blacklistPatterns) {
        mReplaceStringBuffer.clear();
        mIsReplaced.clear();
        mIsWhiteList.clear();

        for (String str : mAToZ) {
            if (!Utils.match(str, blacklistPatterns)) {
                mReplaceStringBuffer.add(str);
            }
        }

        for (String first : mAToZ) {
            for (String aMAToAll : mAToAll) {
                String str = first + aMAToAll;
                if (!mFileNameBlackList.contains(str) | !Utils.match(str, blacklistPatterns)) {
                    mReplaceStringBuffer.add(str);
                }
            }
        }

        for (String first : mAToZ) {
            for (String second : mAToAll) {
                for (String third : mAToAll) {
                    String str = first + second + third;
                    if (!mFileNameBlackList.contains(str) && !Utils.match(str, blacklistPatterns)) {
                        mReplaceStringBuffer.add(str);
                    }
                }
            }
        }
    }

    // 对于某种类型用过的mapping，全部不能再用了
    public void removeStrings(Collection<String> collection) {
        if (collection == null) return;
        mReplaceStringBuffer.removeAll(collection);
    }

    public boolean isReplaced(int id) {
        return mIsReplaced.contains(id);
    }

    public boolean isInWhiteList(int id) {
        return mIsWhiteList.contains(id);
    }

    public void setInWhiteList(int id) {
        mIsWhiteList.add(id);
    }

    public void setInReplaceList(int id) {
        mIsReplaced.add(id);
    }

    public String getReplaceString(Collection<String> names) throws IllegalArgumentException {
        if (mReplaceStringBuffer.isEmpty()) {
            throw new IllegalArgumentException("now can only obfuscation less than 35594 in a single type\n");
        }
        if (names != null) {
            for (int i = 0; i < mReplaceStringBuffer.size(); i++) {
                String name = mReplaceStringBuffer.get(i);
                if (names.contains(name)) {
                    continue;
                }
                return mReplaceStringBuffer.remove(i);
            }
        }
        return mReplaceStringBuffer.remove(0);
    }

    public String getReplaceString() {
        return getReplaceString(null);
    }
}