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
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };

    public static String[] getRandomizedAndTrimmedArray(String[] sourceArray) {
        if (sourceArray.length < 20) {
            throw new IllegalArgumentException("Source array must contain at least 20 elements.");
        }

        String[] arrayCopy = Arrays.copyOf(sourceArray, sourceArray.length);
        Collections.shuffle(Arrays.asList(arrayCopy), new Random());

        // Decide how many elements to delete, ensuring we don't go below 20
        int elementsToDelete = new Random().nextInt(Math.max(0, arrayCopy.length - 20));

        // Perform deletion while maintaining the constraint
        List<String> tempList = new ArrayList<>(Arrays.asList(arrayCopy));
        while (tempList.size() > 20 && !tempList.isEmpty()) {
            tempList.remove(new Random().nextInt(tempList.size()));
        }

        return tempList.subList(0, Math.min(tempList.size(), 20)).toArray(new String[0]);
    }

    private static String[] mAToZ = getRandomizedAndTrimmedArray(oldAToZ);
    private static String[] mAToAll = getRandomizedAndTrimmedArray(oldAToAll);

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
                if (!Utils.match(str, blacklistPatterns)) {
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