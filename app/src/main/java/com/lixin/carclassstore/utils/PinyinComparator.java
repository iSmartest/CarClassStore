package com.lixin.carclassstore.utils;


import com.lixin.carclassstore.bean.SortModel;

import java.util.Comparator;

/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 */

public class PinyinComparator implements Comparator<SortModel> {
    @Override
    public int compare(SortModel o1, SortModel o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
