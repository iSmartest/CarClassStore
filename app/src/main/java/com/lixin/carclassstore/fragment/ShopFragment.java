package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixin.carclassstore.R;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshBase;
import com.xfb.user.custom.view.pulltofresh.library.PullToRefreshListView;

/**
 * Created by 小火
 * Create time on  2017/4/19
 * My mailbox is 1403241630@qq.com
 * 商品
 */

public class ShopFragment extends BaseFragment{
    private View view;
    private PullToRefreshListView list_shop;
    private View head;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop,null);
        initView();
        return view;
    }

    private void initView() {
        list_shop = (PullToRefreshListView) view.findViewById(R.id.list_shop);
        list_shop.setMode(PullToRefreshBase.Mode.BOTH);
        head = LayoutInflater.from(getActivity()).inflate(R.layout.shop_head_item,null);
    }
}
