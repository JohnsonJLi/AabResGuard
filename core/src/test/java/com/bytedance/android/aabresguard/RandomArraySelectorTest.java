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
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","f1t","v","re","c7","qc","pf","se","p","bb","s","_","j9","8s","sb","g","_jj","pag","84","s4i","8r","4mr","e","3m","9","nnb","8g","yd3","si6","ce","hoe","5","po","hgg","r2","su","mwu","8","ufn","0","icw","of","i","x","ld3","u","q","1","64","71","iw","q3o","xf","ju","p4","j","5i","q4","4ae","f","meh","m","a","cce","w","h2u","kev","2u","zv","hb","dum","6","05_","uk","j7","4w","b3","_8","0f","o","a_","wl","7i","b","y4d","t","ipp","otv","78p","2p","k","87","9d","jr","e04","axk","q3","zah","4hf","s_","d","2","pr","ecf","2e","c4w","h","9j","de","d0","sp","1ms","9s","xc","04u","nk","kc","a8","mj","op","f6","fo","cy","fc","g7","ra","ihi","6c","0gw","s6w","u_","feu","95p","zci","pb5","mkf","wf4","r","yxk","j4o","c80","14","uo","_qk","qu","7y","xi0","tk","kq","y","0a","2v","fh","tq9","v46","3","58","as4","km","4d7","ai","nz","a32","noa","bm","ywz","v9s","ni","b5","x3","o5o","yj","cc","p0e","ctz","jgn","k2","7h","n","9hm","9mi","zs","v3b","fd","4wx","7aj","xhf","ox5","bqd","n1","vq","7uw","2o","sx","z","c","4cm","k4","ff","r1a","4q4","c6","hgu","04l","ofg","35","tt","wr","ihb","o8w","uf","j5p","oj","hn","_7","6i","ip_","k3","7g4","k7h","u9z","6j","bw","4f","16b","_te","pa","g5j","uh","4y","0f5","d4t","4s","mib","csz","c4y","bv","169","fr","smn","fzr","nv","31","yt2","fj","7","h4u","9zo","78","zpt","l1z","s85","8z","m5","aeu","ey","aj8","if","nt","rp4","qhi","fe","fc5","4","np","r7d","bq6","qwa","9uv","8o","ef","orx","ia","cox","5u","hr1","j_n","gwp","hcl","i1","7fl","6ia","b6","9h","jd","2ks","8m","ib4","xna","ofh","tau","3a","dw","kce","79n","8og","8d","wm_","l","e1u","qum","yd5","yap","pq","r6","hfm","3vj","mo","d28","be","css","2c5","_8s","d9_","ns","j_","ep","jfy","bp","vj","s8","ml","jn","atr","xa","1wn","ok","es","91s","fm","vbo","dc3","qr","v8","k7","oc","g8f","wdy","4z3","on","bl","pck","zu","g4","6p","3ap","5w","5tm","1ct","k0","o_","xrd","7q","p9","08p","se5","mr","a4c","h8","kt","dy","mq","l6","mf","19e","syd","t3","av","pcx","o3","nco","b2l","on2","61v","e0","51v","4b","o9","a7","cx","2oo","98l","4t","9wd","pp9","9_","8w1","18y","1o","2y","amn","80_","ewa","p9p","70u","qf3","m1","7jg","20","oyc","wdq","ic7","79","i95","2s","18t","t1","jrq","gpy","rd","360","zlq","avp","7p","7e","ty_","e9l","p6","a3","qv","hoz","6_t","2h","_f9","gx","kj","1nq","hv","au","nbs","u89","e5","z9d","o9x","lt5","fn","0k","q9","3s","w_","49i","_xj","yfc","omx","yhn","093","4n","mcd","q68","ci","0yh","sfn","4l","ih","55","7le","_dl","3d","swn","_d","z75","99k","jq6","mho","_w5","2f3","lg","2k","tco","ty","0r","yh2","k8i","_u","rks","0s","c90","gv","va","l4r","bt","hys","ca","a99","7g","dz","me1","fv6","i2_","f2","ix","oze","s9a","n5","24","ta","yi","eu","vn","ywt","rvp"
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