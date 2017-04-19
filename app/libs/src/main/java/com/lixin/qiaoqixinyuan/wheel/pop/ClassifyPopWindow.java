package com.lixin.qiaoqixinyuan.wheel.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.lixin.qiaoqixinyuan.R;
import com.lixin.qiaoqixinyuan.wheel.adapter.ListWheelAdapter;
import com.lixin.qiaoqixinyuan.wheel.wheelview.OnWheelChangedListener;
import com.lixin.qiaoqixinyuan.wheel.wheelview.WheelView;
import java.util.List;


/**
 * 发布页面选择类别pop
 * Created by Administrator on 2016/11/9 0009.
 */

public class ClassifyPopWindow extends PopupWindow implements OnWheelChangedListener {

    private Context context;
    private LayoutInflater layoutInflater;
    private WheelView wheel_classify;

    private List<String> list;


    public ClassifyPopWindow(Context context, List<String> list) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.pop_classifywheel, null);

        this.context = context;
        this.list = list;

        this.setContentView(v);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setAnimationStyle(android.R.style.Animation_InputMethod);
        this.setFocusable(true);
//		this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initPop(v, list);
    }


    //初始化Pop
    private void initPop(View viewGroup,List<String> list) {
        wheel_classify = (WheelView) viewGroup.findViewById(R.id.wheel_classify);
        viewGroup.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcw.saveVycle("");
                dismiss();
            }
        });
        viewGroup.findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        wheel_classify.setViewAdapter(new ListWheelAdapter(context, list));
        wheel_classify.setCurrentItem(0);
        wheel_classify.setCyclic(false);// 可循环滚动
        wheel_classify.addChangingListener(this);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
            pcw.saveVycle(list.get(wheel_classify.getCurrentItem())+"");
    }

    private PopInterface pcw;

    public void setOnCycleListener(PopInterface pcw) {
        this.pcw = pcw;
    }

    public interface PopInterface {
        void saveVycle(String birthday);
    }

}
