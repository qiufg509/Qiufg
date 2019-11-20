package com.qiufg.server.util;

/**
 * Author qiufg
 * Date 2016/7/23
 */
public class Arrays {

    /**
     * 选择排序
     *
     * @param array 要排序数组
     * @return 排序后数组
     */
    public static int[] selectionSort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                switchNum(array, i, j);
            }
        }
        return array;
    }

    /**
     * 冒泡排序
     *
     * @param array 要排序数组
     * @return 排序后数组
     */
    public static int[] popuSort(int[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                switchNum(array, j, j + 1);
            }
        }
        return array;
    }

    /**
     * 快速排序
     *
     * @param array 要排序数组
     * @return 排序后数组
     */
    public static int[] quickSort(int[] array) {
        if (array != null && array.length > 1) {
            _quickSort(array, 0, array.length - 1);
        }
        return array;
    }

    /**
     * 快速排序
     *
     * @param array 数组
     * @param left  左区间
     * @param right 右区间
     */
    private static void _quickSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = partition(array, left, right);
            _quickSort(array, left, mid - 1);
            _quickSort(array, mid + 1, right);
        }
    }

    /**
     * 将数组按基准点分隔，同时获取分割后基准点所在位置下标
     *
     * @param array 数组
     * @param left  要分隔左区间
     * @param right 要分隔右区间
     * @return 分割后基准点所在位置下标
     */
    private static int partition(int[] array, int left, int right) {
        int temp = array[left];
        while (left < right) {
            while (left < right && array[right] >= temp)
                right--;
            array[left] = array[right];
            while (left < right && array[left] <= temp)
                left++;
            array[right] = array[left];
        }
        array[left] = temp;
        return left;
    }

    /**
     * 如果数组array第x位大于第y位，交换数组这两个位置上的数
     *
     * @param array 数组
     * @param x     array第x位
     * @param y     array第y位
     */
    private static void switchNum(int[] array, int x, int y) {
        if (array[x] > array[y]) {
            int temp = array[x];
            array[x] = array[y];
            array[y] = temp;
        }
    }
}
