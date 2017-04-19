package com.lixin.qiaoqixinyuan.app.util;

import android.support.v4.app.Fragment;

import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.app.fragment.HomeFragment;
import com.lixin.qiaoqixinyuan.app.fragment.MeFragment;
import com.lixin.qiaoqixinyuan.app.fragment.PinFragment;
import com.lixin.qiaoqixinyuan.app.fragment.SendFragment;

/**
 * Created by Administrator on 2016/12/17 0017.
 */

public class FragmentCheck {

    public static Fragment getFragmentById(int checkedID) {
        Fragment fragment = null;
        switch (checkedID) {
            case R.id.rb_main_shouye:
                fragment = new HomeFragment();
                break;
            case R.id.rb_main_pinche:
                fragment = new PinFragment();
                break;
            case R.id.rb_main_fabu:
                fragment = new SendFragment();
                break;
            case R.id.rb_main_geren:
                fragment = new MeFragment();
                break;
        }
        return fragment;
    }
}
