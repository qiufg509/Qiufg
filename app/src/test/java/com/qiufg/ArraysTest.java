package com.qiufg;

import com.qiufg.util.Arrays;

import org.junit.Test;

/**
 * Author qiufg
 * Date 2016/7/23
 */
public class ArraysTest {

    private int[] array = {86, 85, 0, 78, 26, 94, 24, 125, 7, 66, 79, 24, 64};

    @Test
    public void testSelectionSort() throws Exception {
        Arrays.selectionSort(array);
        System.out.print("selectionSort:");
        print(array);
    }

    @Test
    public void testPopuSort() throws Exception {
        Arrays.popuSort(array);
        System.out.print("popuSort:     ");
        print(array);
    }

    @Test
    public void testQuickSort() throws Exception {
        Arrays.quickSort(array);
        System.out.print("quickSort:    ");
        print(array);
    }

    private void print(int[] array) {
        for (int num : array)
            System.out.print(num + "——");
        System.out.println();
    }
}