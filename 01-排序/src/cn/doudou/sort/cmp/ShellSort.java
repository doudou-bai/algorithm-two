package cn.doudou.sort.cmp;

import cn.doudou.sort.Sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Create By 王嘉浩
 * Time 2022-12-26 13:48
 */
public class ShellSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        List<Integer> shellStep = sedgewickStepSequence();
        for (Integer step : shellStep) {
            sort(step);
        }
    }

    /**
     * 分成step列进行排序
     *
     * @param step
     */
    private void sort(int step) {
        // col: 第几列, column的简称
        for (int col = 0; col < step; col++) {
            // 插入排序
            for (int begin = col + step; begin < array.length; begin += step) {
                // col、col+step、col+2*step、col+3*step
                int cur = begin;
                while (cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }

            }
        }
    }

    /**
     * 生成序列步长
     *
     * @return
     */
    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {
            stepSequence.add(step);
        }
        return stepSequence;
    }

    /**
     * 目前效率最高的步长序列
     */
    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }
}
