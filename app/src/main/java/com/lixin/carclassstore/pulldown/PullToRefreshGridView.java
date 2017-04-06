package com.lixin.carclassstore.pulldown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.GridView;

import com.lixin.chepinstore.R;


public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView>
{
    class InternalGridView extends GridView implements EmptyViewMethodAccessor
    {
        public InternalGridView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView)
        {
            PullToRefreshGridView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView)
        {
            super.setEmptyView(emptyView);
        }

        public ContextMenuInfo getContextMenuInfo()
        {
            return super.getContextMenuInfo();
        }
    }

    public int getRefreshType()
    {
        return getCurrentMode();
    }

    public PullToRefreshGridView(Context context)
    {
        super(context);
        this.setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setDisableScrollingWhileRefreshing(false);
    }

    @Override
    public ContextMenuInfo getContextMenuInfo()
    {
        return ((InternalGridView) getRefreshableView()).getContextMenuInfo();
    }

    @Override
    protected final GridView createRefreshableView(Context context, AttributeSet attrs)
    {
        GridView lv = new InternalGridView(context, attrs);
        lv.setId(R.id.gridview);

        lv.setSelector(android.R.color.transparent);
        lv.setCacheColorHint(getResources().getColor(android.R.color.transparent));

        return lv;
    }
}
