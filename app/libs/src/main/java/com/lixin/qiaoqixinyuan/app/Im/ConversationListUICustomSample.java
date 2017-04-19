package com.lixin.qiaoqixinyuan.app.Im;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMContactsOperation;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class ConversationListUICustomSample extends IMConversationListUI {
    public ConversationListUICustomSample(Pointcut pointcut) {
        super(pointcut);
    }
    //不显示标题栏
    @Override
    public boolean needHideTitleView(Fragment fragment) {
        return true;
    }
}
