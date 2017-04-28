package com.lixin.carclassstore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixin.carclassstore.R;

/**
 * Created by 小火
 * Create time on  2017/4/1
 * My mailbox is 1403241630@qq.com
 */
public class ForumFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private TextView[] mLinearLayout;
    private Fragment[] mFragments;
    private FragmentTransaction transaction;
    private ImageView mCursor,mCursor01,mCursor02,mCursor03;
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
        mLinearLayout = new TextView[4];
        mLinearLayout[0] = (TextView) view.findViewById(R.id.ll_first_news);
        mLinearLayout[1] = (TextView) view.findViewById(R.id.ll_focus);
        mLinearLayout[2] = (TextView) view.findViewById(R.id.ll_question_answer);
        mLinearLayout[3] = (TextView) view.findViewById(R.id.ll_section);
        mCursor = (ImageView) view.findViewById(R.id.iv_cursor);
        mCursor01 = (ImageView) view.findViewById(R.id.iv_cursor01);
        mCursor02 = (ImageView) view.findViewById(R.id.iv_cursor02);
        mCursor03 = (ImageView) view.findViewById(R.id.iv_cursor03);
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
                mLinearLayout[0].setTextColor(getResources().getColor(R.color.btn_login_color));
                mLinearLayout[1].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[2].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[3].setTextColor(getResources().getColor(R.color.black));
                mCursor.setVisibility(View.VISIBLE);
                mCursor01.setVisibility(View.INVISIBLE);
                mCursor02.setVisibility(View.INVISIBLE);
                mCursor03.setVisibility(View.INVISIBLE);
                break;
            case 1:
                setCurrent(1);
                mLinearLayout[0].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[1].setTextColor(getResources().getColor(R.color.btn_login_color));
                mLinearLayout[2].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[3].setTextColor(getResources().getColor(R.color.black));
                mCursor.setVisibility(View.INVISIBLE);
                mCursor01.setVisibility(View.VISIBLE);
                mCursor02.setVisibility(View.INVISIBLE);
                mCursor03.setVisibility(View.INVISIBLE);
                break;
            case 2:
                setCurrent(2);
                mLinearLayout[0].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[1].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[2].setTextColor(getResources().getColor(R.color.btn_login_color));
                mLinearLayout[3].setTextColor(getResources().getColor(R.color.black));
                mCursor.setVisibility(View.INVISIBLE);
                mCursor01.setVisibility(View.INVISIBLE);
                mCursor02.setVisibility(View.VISIBLE);
                mCursor03.setVisibility(View.INVISIBLE);
                break;
            case 3:
                setCurrent(3);
                mLinearLayout[0].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[1].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[2].setTextColor(getResources().getColor(R.color.black));
                mLinearLayout[3].setTextColor(getResources().getColor(R.color.btn_login_color));
                mCursor.setVisibility(View.INVISIBLE);
                mCursor01.setVisibility(View.INVISIBLE);
                mCursor02.setVisibility(View.INVISIBLE);
                mCursor03.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
