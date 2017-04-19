package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixin.carclassstore.R;

/**
 * Created by 小火
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 * 详情
 */

public class DecFragment extends BaseFragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dec,null);
        return view;
    }
}
