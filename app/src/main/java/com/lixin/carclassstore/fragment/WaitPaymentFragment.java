package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixin.carclassstore.R;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 */

public class WaitPaymentFragment extends BaseFragment{
    private PullToRefreshListView list_collection;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_content,null);
        list_collection = (PullToRefreshListView) view.findViewById(R.id.list_collection);

        return view;
    }
}
