package com.bytedance.android.aabresguard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomArraySelectorTest {

    private String[] mAToZ = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z"
    };
    private String[] mAToAll = {
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

    public static void main(String[] args) {
        RandomArraySelectorTest selector = new RandomArraySelectorTest();

        // Get a randomized array from mAToZ with at least 20 elements
        String[] randomMAToZ = selector.getRandomizedAndTrimmedArray(selector.mAToZ);
        System.out.println("Random mAToZ: " + Arrays.toString(randomMAToZ));

        // Get a randomized array from mAToAll with at least 20 elements
        String[] randomMAToAll = selector.getRandomizedAndTrimmedArray(selector.mAToAll);
        System.out.println("Random mAToAll: " + Arrays.toString(randomMAToAll));
    }
}