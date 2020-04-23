package com.fossilia.visuafiles.util;

import com.fossilia.visuafiles.group.Group;

import java.io.File;
import java.util.ArrayList;

public final class Sorter {

    /** Merge two groups**/
    public static void groupMerge(ArrayList<Group> S1, ArrayList<Group> S2, ArrayList<Group> S) {
        int i = 0, j = 0;
        while (i + j < S.size()) {
            if (j == S2.size() || (i < S1.size() && (S1.get(i).getSize()-S2.get(j).getSize())>0))
                S.set(i + j, S1.get(i++));
            else
                S.set(i + j, S2.get(j++));
        }
    }

    /**sorts groups*/
    public static void sortGroups(ArrayList<Group> S) {
        int n = S.size();
        if (n < 2)
            return;
        int mid = n / 2;
        // partition the string into two strings
        ArrayList<Group> S1 = new ArrayList<Group>(S.subList(0, mid));
        ArrayList<Group> S2 = new ArrayList<Group>(S.subList(mid, n));
        sortGroups(S1);
        sortGroups(S2);
        groupMerge(S1, S2, S);
    }

    public static void fileMerge(ArrayList<File> S1, ArrayList<File> S2, ArrayList<File> S) {
        int i = 0, j = 0;
        while (i + j < S.size()) {
            if (j == S2.size() || (i < S1.size() && (S1.get(i).length()-S2.get(j).length())>0))
                S.set(i + j, S1.get(i++));
            else
                S.set(i + j, S2.get(j++));
        }
    }

    public static void sortFiles(ArrayList<File> S) {
        int n = S.size();
        if (n < 2)
            return;
        int mid = n / 2;
        // partition the string into two strings
        ArrayList<File> S1 = new ArrayList<File>(S.subList(0, mid));
        ArrayList<File> S2 = new ArrayList<File>(S.subList(mid, n));
        sortFiles(S1);
        sortFiles(S2);
        fileMerge(S1, S2, S);
    }

    public static int binarySearch(ArrayList<String> list, String word) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int res = word.compareTo(list.get(m));
            // Check if x is present at mid
            if (res == 0)
                return m;
            // If x greater, ignore left half
            if (res > 0)
                l = m + 1;
                // If x is smaller, ignore right half
            else
                r = m - 1;
        }
        return -1;
    }
}
