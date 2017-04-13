package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixin.carclassstore.R;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class ForumFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private LinearLayout[] mLinearLayout;
    private Fragment[] mFragments;
    private FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forum,container,false);
        initView();
        initFragment();
        refreshView();
        return view;
    }
    private void initView() {
        mLinearLayout = new LinearLayout[4];
        mLinearLayout[0] = (LinearLayout) view.findViewById(R.id.ll_first_news);
        mLinearLayout[1] = (LinearLayout) view.findViewById(R.id.ll_focus);
        mLinearLayout[2] = (LinearLayout) view.findViewById(R.id.ll_question_answer);
        mLinearLayout[3] = (LinearLayout) view.findViewById(R.id.ll_section);
    }
    private void initFragment() {
        mFragments = new Fragment[4];
        mFragments[0] = new NewsFragment();
        mFragments[1] = new FocusFragment();
        mFragments[2] = new QuestionAnswerFragment();
        mFragments[3] = new SectionFragment();
        setCurrent(0);
    }

    private void setCurrent(int position) {
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_forum_layout_content, mFragments[position]);
        transaction.commitAllowingStateLoss();
        mLinearLayout[position].setSelected(true);
    }
    private void refreshView() {
        for (int i = 0; i < mLinearLayout.length; i++) {
            mLinearLayout[i].setId(i);
            mLinearLayout[i].setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 0:
                setCurrent(0);
                break;
            case 1:
                setCurrent(1);
                break;
            case 2:
                setCurrent(2);
                break;
            case 3:
                setCurrent(3);
                break;
            default:
                break;
        }
    }
}
