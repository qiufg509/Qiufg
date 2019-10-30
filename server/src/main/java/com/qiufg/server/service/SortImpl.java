package com.qiufg.server.service;

import com.qiufg.server.ISort;
import com.qiufg.server.util.Arrays;

/**
 * Created by qiufg on 2019/10/30 14:13.
 * <p>
 * Desc：Binder排序
 */
public class SortImpl extends ISort.Stub {

    @Override
    public int[] sort(int type, int[] array) {
        int[] ints;
        switch (type) {
            case 0:
                ints = Arrays.selectionSort(array);
                break;
            case 1:
                ints = Arrays.popuSort(array);
                break;
            default:
                ints = Arrays.quickSort(array);
                break;
        }
        return ints;
    }
}
